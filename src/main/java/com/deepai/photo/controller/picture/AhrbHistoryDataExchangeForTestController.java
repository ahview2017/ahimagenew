package com.deepai.photo.controller.picture;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.deepai.photo.bean.CpPicGroup;
import com.deepai.photo.bean.CpPicGroupCategory;
import com.deepai.photo.bean.CpPicture;
import com.deepai.photo.bean.CpUser;
import com.deepai.photo.common.annotation.SkipAuthCheck;
import com.deepai.photo.common.annotation.SkipLoginCheck;
import com.deepai.photo.common.constant.CommonConstant;
import com.deepai.photo.common.pojo.ResponseMessage;
import com.deepai.photo.common.util.date.DateUtil;
import com.deepai.photo.common.util.date.DateUtils;
import com.deepai.photo.mapper.CpPicGroupMapper;
import com.deepai.photo.mapper.CpUserMapper;
import com.deepai.photo.service.picture.FlowService;
import com.deepai.photo.service.picture.PictureDataExchangeService;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;


/**
 * 安徽日报旧图片库数据迁移接口
 * @author xiayunan
 * @date   2017年9月21日
 *
 */
@Controller
@RequestMapping("/ahrbHistoryDataExchangeCtro")
public class AhrbHistoryDataExchangeForTestController {
	private Logger log=Logger.getLogger(AhrbHistoryDataExchangeForTestController.class);
	@Autowired
	private PictureDataExchangeService pictureService;
	@Autowired
	private FlowService flowService;
	@Autowired
	private CpUserMapper cpUserMapper;
	@Autowired
	private CpPicGroupMapper cpPicGroupMapper;
	public static final String SESSION_LANGTYPE = "session_langType";
	private static final int FIRST_EDIT_ID = 339;
	private static final int SECOND_EDIT_ID = 340;
	private static final int THIRD_EDIT_ID = 341;
	private static final String AUTHOR_NAME = "第一审核人";
	private static final int AUTHOR_ID = 339;
	private static final int DATA_EXCHANGE_CHNL_ID = 3066;
	private static final String FILE_SEP = File.separator;
	private static int SUCCESS_PIC_NUM = 0;
	private static int FAILED_PIC_NUM = 0;
	private static Map<String,Integer> categoryMap = null;
	private static Map<String,Integer> oldCategoryMap = null;
	private static String driverName="com.microsoft.sqlserver.jdbc.SQLServerDriver";
	//private static String dbURL="jdbc:sqlserver://192.168.17.73:1433;DatabaseName=uniphoto";
	private static String dbURL="jdbc:sqlserver://192.168.2.11:1433;DatabaseName=uniphoto";
	private static String userName="trstest";
	private static String userPwd="trsadmin";
	static{  
		 categoryMap = new HashMap<String, Integer>();  
		 categoryMap.put("政治", 1761);
		 categoryMap.put("经济", 1762);
		 categoryMap.put("文化", 1764);
		 categoryMap.put("体育", 1765);
		 categoryMap.put("农业", 100182646);
		 categoryMap.put("科技", 1763);
		 categoryMap.put("教育", 100182645);
		 categoryMap.put("医卫", 100182647);
		 categoryMap.put("军事", 1768);
		 categoryMap.put("法制", 1776);
		 categoryMap.put("生态", 1769);
		 categoryMap.put("社会", 1766);
		 categoryMap.put("典型人物", 1773);
		 categoryMap.put("重大事件", 1774);
		 categoryMap.put("中央领导", 1775);
		 categoryMap.put("省领导", 1777);
		 categoryMap.put("外交", 100182648);
		 categoryMap.put("旅游", 100182649);
		 categoryMap.put("民族宗教", 1767);
		 categoryMap.put("美术", 100182650);
		 categoryMap.put("世界各地", 1770);
		 categoryMap.put("新华社", 1771);
		 categoryMap.put("历史资料", 1772);
		 categoryMap.put("历史版面", 1773);
		 //老系统栏目分类
//		 oldCategoryMap = new HashMap<String, String>();
//		 oldCategoryMap.put("ahrb", "安徽日报");
//		 oldCategoryMap.put("ahrb_anhuird", "安徽人大");
//		 oldCategoryMap.put("ahrb_anhuizx", "安徽政协");
//		 oldCategoryMap.put("ahrb_dianxrw", "典型人物");
//		 oldCategoryMap.put("ahrb_guojiard", "国家人大");
//		 oldCategoryMap.put("ahrb_guojiazx", "国家政协");
//		 oldCategoryMap.put("ahrb_jinji", "经济");
//		 oldCategoryMap.put("ahrb_junshi", "军事");
//		 oldCategoryMap.put("ahrb_kejiao", "科教卫");
//		 oldCategoryMap.put("ahrb_qitq", "其他");
//		 oldCategoryMap.put("ahrb_shehui", "社会");
//		 oldCategoryMap.put("ahrb_shengld", "省领导");
//		 oldCategoryMap.put("ahrb_tiyu", "体育");
//		 oldCategoryMap.put("ahrb_tuhua", "图画");
//		 oldCategoryMap.put("ahrb_waijiao", "外交");
//		 oldCategoryMap.put("ahrb_wenhua", "文化");
//		 oldCategoryMap.put("ahrb_zhengzhi", "政治");
//		 oldCategoryMap.put("ahrb_zhongdsj", "重大事件");
//		 oldCategoryMap.put("ahrb_zhongyld", "中央领导");
		 
		 oldCategoryMap = new HashMap<String, Integer>();
		 oldCategoryMap.put("ahrb", 1772);
		 oldCategoryMap.put("ahrb_anhuird", 1772);
		 oldCategoryMap.put("ahrb_anhuizx", 1772);
		 oldCategoryMap.put("ahrb_dianxrw", 1773);
		 oldCategoryMap.put("ahrb_guojiard", 1775);
		 oldCategoryMap.put("ahrb_guojiazx", 1775);
		 oldCategoryMap.put("ahrb_jinji", 1762);
		 oldCategoryMap.put("ahrb_junshi", 1768);
		 oldCategoryMap.put("ahrb_kejiao", 1763);
		 oldCategoryMap.put("ahrb_qitq", 1772);
		 oldCategoryMap.put("ahrb_shehui", 1766);
		 oldCategoryMap.put("ahrb_shengld", 1777);
		 oldCategoryMap.put("ahrb_tiyu", 1765);
		 oldCategoryMap.put("ahrb_tuhua", 100182650);
		 oldCategoryMap.put("ahrb_waijiao", 100182648);
		 oldCategoryMap.put("ahrb_wenhua", 1764);
		 oldCategoryMap.put("ahrb_zhengzhi", 1761);
		 oldCategoryMap.put("ahrb_zhongdsj",1774);
		 oldCategoryMap.put("ahrb_zhongyld", 1775);
		 
	}  
	
	
	
	/**
	 * 显示签发专题
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/dataExchange")
	@SkipLoginCheck
	@SkipAuthCheck
	public Object showQianFaTopic(HttpServletRequest request, HttpServletResponse response) {
		ResponseMessage result=new ResponseMessage();
		PreparedStatement pstmt = null;
		Connection conn = null;
		ResultSet rs = null;
		try {
			log.info("============================信件迁移开始！===========================");
			conn =  getConnection();
			String sql = "SELECT * FROM uninews_Product a WHERE UP_SignDate_DT BETWEEN '2017-09-01' AND '2017-09-02'";
	        pstmt = (PreparedStatement)conn.prepareStatement(sql);
	        log.info("获取数据库连接成功！");
	        rs = pstmt.executeQuery();
	        int col = rs.getMetaData().getColumnCount();
	        log.info("<<<<<<<<<总记录数：");
	        while (rs.next()) {
	        	String dateStr = "";//日期
				String title = "";//标题
				String author = "";//作者
				String keyWordsStr = "";//关键词
				String content = "";//内容
				String cateIdsStr = "";
	        	try {
	        		String fileName = rs.getString("UP_File_Vc");
		        	String filePath = rs.getString("UP_PhotoPath_Vc");
		        	if(filePath!=null&&!"".equals(filePath)){
		        		filePath = "/trsphoto/dataexchange"+filePath.replaceAll("z:", "");
		        	}
		        	log.info("<<<<<<<filePath:"+filePath);
		        	String id = rs.getString("UP_ID_N");
		        	String uploadUser = rs.getString("UP_UploadUser_Vc");
		        	
		        	String picType = fileName.substring(fileName.lastIndexOf("."),fileName.length());
		        	if(picType.indexOf("jpg")==-1&&picType.indexOf("JPG")==-1){
		        		picType = ".jpg";
		        	}
		        	String fileFullPath = filePath+uploadUser+"_"+"O"+id+picType;
		        	log.info("<<<<<<<<<<fileFullPath:"+fileFullPath);
//		        	String filePath = "D:\\xinhuaphoto\\20170820\\XxjpsgC000668_20170820_TPPFN1A001.jpg";
		        	
					dateStr = rs.getString("UP_PhotoDate_Dt");//日期
		        	if(dateStr!=null && dateStr.length()>=19 ){
		        		dateStr = dateStr.substring(0, 19);
		        	}
		        	log.info("<<<<<<<<<<dateStr:"+dateStr);
		        	title = rs.getString("UP_Title_Vc");
		        	author = rs.getString("UP_Author_Vc");
		        	keyWordsStr = rs.getString("UP_KeyWord_Vc");
		        	content = rs.getString("UP_Content_T");
		        	cateIdsStr = oldCategoryMap.get(rs.getString("UP_Code_Vc")).toString()==""?"1772":oldCategoryMap.get(rs.getString("UP_Code_Vc")).toString();
		        	log.info("<<<<<<<<<<<<<<cateIdsStr:"+cateIdsStr);
					/**
					 * 2.上传稿件
					 */
					File picFile =  new File(fileFullPath);
					Map<String,String> map = new HashMap<String,String>();
					map.put("title", title);
					map.put("keywords", keyWordsStr);
					map.put("place", "");
					map.put("strMemo", content);
					map.put("authorName", author);
					int siteid = 1;
					List<CpPicture> pics = pictureService.uploadMorePicForAhrbHistoyData(picFile, siteid,map,dateStr);
					log.info("====================上传 图片成功======================");
					result.setCode(CommonConstant.SUCCESSCODE);
					result.setMsg(CommonConstant.SUCCESSSTRING);
					result.setData(pics);
					result.setOther(String.format("上传图片=%s", pics));
					
					CpPicGroup group = new CpPicGroup();
					group.setAuthor(AUTHOR_NAME);
					group.setAuthorId(AUTHOR_ID);
					group.setKeywords(keyWordsStr);
					group.setRemark(content);
					group.setLangType(0);
					group.setLocationType(0);
					group.setPlace("");
					group.setMemo(content);
					group.setPeople("");
					group.setTitle(title);
					group.setType((byte)1);
					group.setProperties((byte)0);
					group.setFileTime(DateUtils.sdfLongTimePlus.parse(dateStr));
					
					boolean isIpTc = true;
					CpUser user = cpUserMapper.selectByPrimaryKey(1);
					int type = 1;//1是提交，0是保存
					int roleid = 1;
					int a = flowService.makePicGroupForDataExchange(pics, group, isIpTc, user, siteid, type, roleid);
					StringBuffer ids= new StringBuffer();
					String typeName=type==0?"保存":"提交";
					for (CpPicture pic : pics) {
						ids.append(pic.getId()).append(",");
					}
					if(a>0){
						result.setCode(CommonConstant.SUCCESSCODE);
						result.setMsg(CommonConstant.SUCCESSSTRING);
						result.setOther(String.format("%s稿件groupId=%s成功，包含图片picIds=%s",typeName,group.getId(), ids));
					}else{
						result.setCode(CommonConstant.FAILURECODE);
						result.setMsg(CommonConstant.FILEERRORMSG);
					}
					
					//一审编辑稿件					
					Gson gson = new Gson();
					String picData = gson.toJson(pics);
					CpUser firstEditUser = cpUserMapper.selectByPrimaryKey(FIRST_EDIT_ID);
					CpUser secondEditUser = cpUserMapper.selectByPrimaryKey(SECOND_EDIT_ID);
					CpUser thirdEditUser = cpUserMapper.selectByPrimaryKey(THIRD_EDIT_ID);
					String res=flowService.checkAndEditGroup(picData, group, firstEditUser ,DateUtil.getDate(new Date()), siteid, 1,cateIdsStr,type);
					if(res!=null){
						result.setCode(CommonConstant.SUCCESSCODE212);
						result.setMsg("存在敏感词："+res);
					}else{
						flowService.examByProofread(cpPicGroupMapper.selectByPrimaryKey(group.getId()),firstEditUser , 1,null);//一审
						flowService.examByProofread(cpPicGroupMapper.selectByPrimaryKey(group.getId()),secondEditUser , 2,null);//二审
						CpPicGroupCategory cpPicGroupCategory = new CpPicGroupCategory();
						cpPicGroupCategory.setType(0);
						cpPicGroupCategory.setPosition(0);
						cpPicGroupCategory.setCategoryId(DATA_EXCHANGE_CHNL_ID);
						
						CpPicGroupCategory cpPicGroupCategory1 = new CpPicGroupCategory();
						cpPicGroupCategory1.setType(0);
						cpPicGroupCategory1.setCategoryId(473);
						List<CpPicGroupCategory> list = new ArrayList<CpPicGroupCategory>();
						list.add(cpPicGroupCategory);
						list.add(cpPicGroupCategory1);
						String cateData = gson.toJson(list);
						System.out.println("cateData:"+cateData);
						List<Map<String,Object>> cates = gson.fromJson(cateData, new TypeToken<List<Map<String,Object>>>(){}.getType());
						flowService.examByProofread(cpPicGroupMapper.selectByPrimaryKey(group.getId()),thirdEditUser, 3,cates);
						result.setCode(CommonConstant.SUCCESSCODE);
						result.setMsg(CommonConstant.SUCCESSSTRING);
						result.setOther(String.format("三审审核提交稿件groupid=【%s】",group.getId()));
					}
					log.info("第"+(++SUCCESS_PIC_NUM)+"篇稿件迁移成功,稿件标题："+title+"！");
			} catch (Exception e) {
				log.error("==================出错啦=================");
				++FAILED_PIC_NUM;
				e.printStackTrace();
				log.error("迁移稿件失败，"+e.getMessage());
			}
	        
	      }  
		}catch(Exception e1){
			e1.printStackTrace();
			result.setCode(CommonConstant.EXCEPTIONCODE);
			result.setMsg(CommonConstant.EXCEPTIONMSG);
		}finally{
			closeAll(rs,pstmt,conn);
		}
		log.info("信件迁移结束！成功数："+SUCCESS_PIC_NUM+"失败数："+FAILED_PIC_NUM+"!");
		return result;
	}
	
	
	
	/**
	 * 获取数据库连接
	 * @return
	 */
    public static Connection getConnection() {
        Connection conn = null;
        try {
            Class.forName(driverName);
            conn = DriverManager.getConnection(dbURL, userName, userPwd);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return conn;
    }
    
    
    /**
     * 释放数据库连接
     * @param rs
     * @param stmt
     * @param conn
     */
    public static void closeAll(ResultSet rs, Statement stmt, Connection conn) {
        try {
            if (rs != null)
                rs.close();
            if (stmt != null)
                stmt.close();
            if (conn != null) {
                conn.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            rs = null;
            stmt = null;
            conn = null;
        }
    }
		
	
	public static void main(String[] args) {
//		String str = "既要“站起来”，还要“走得远”.jpg";
//		str = str.substring(str.lastIndexOf("."),str.length());
//		System.out.println(str);
		
		String dateStr = "2017-09-01 15:33:06990";
		if(dateStr.length()>19){
			dateStr = dateStr.substring(0, 19);
		}
		System.out.println(dateStr);
//		dateStr = dateStr.substring(0, dateStr.length()-2);
//		System.out.println("dateStr:"+dateStr);
//		
//		try {
//			System.out.println(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("2017-9-8 23:12:12").toString());
//		} catch (ParseException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
	}
}
