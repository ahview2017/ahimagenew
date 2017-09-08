package com.deepai.photo.controller.picture;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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
import com.deepai.photo.common.constant.CommonConstant;
import com.deepai.photo.common.pojo.ResponseMessage;
import com.deepai.photo.common.util.date.DateUtil;
import com.deepai.photo.mapper.CpPicGroupMapper;
import com.deepai.photo.mapper.CpUserMapper;
import com.deepai.photo.service.picture.FlowService;
import com.deepai.photo.service.picture.PictureDataExchangeService;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;


/**
 * 安徽日报旧图片库数据迁移接口
 * @author xiayunan
 * @date   2017年8月30日
 *
 */
@Controller
@RequestMapping("/ahrbHistoryDataExchangeCtro")
public class AhrbHistoryDataExchangeController {
	private Logger log=Logger.getLogger(AhrbHistoryDataExchangeController.class);
	@Autowired
	private PictureDataExchangeService pictureService;
	@Autowired
	private FlowService flowService;
	@Autowired
	private CpUserMapper cpUserMapper;
	@Autowired
	private CpPicGroupMapper cpPicGroupMapper;
	public static final String SESSION_LANGTYPE = "session_langType";
	private static final int FIRST_EDIT_ID = 338;
	private static final int SECOND_EDIT_ID = 339;
	private static final int THIRD_EDIT_ID = 340;
	private static final String AUTHOR_NAME = "秋天";
	private static final int AUTHOR_ID = 338;
	private static final int DATA_EXCHANGE_CHNL_ID = 3063;
	private static final String FILE_SEP = File.separator;
	private static int SUCCESS_PIC_NUM = 0;
	private static int FAILED_PIC_NUM = 0;
	private static Map<String,Integer> categoryMap = null;
	private static String driverName="com.microsoft.sqlserver.jdbc.SQLServerDriver";
	private static String dbURL="jdbc:sqlserver://192.168.17.73:1433;DatabaseName=uniphoto";
	private static String userName="sa";
	private static String userPwd="sasa";
	static{  
		 categoryMap = new HashMap<String, Integer>();  
		 categoryMap.put("政治", 1761);
		 categoryMap.put("经济", 1762);
		 categoryMap.put("科技教育卫生", 1763);
		 categoryMap.put("文化艺术", 1764);
		 categoryMap.put("体育", 1765);
		 categoryMap.put("社会生活", 1766);
		 categoryMap.put("民族与宗教", 1767);
		 categoryMap.put("法治与军事", 1768);
		 categoryMap.put("自然环境", 1769);
		 categoryMap.put("世界各地", 1770);
		 categoryMap.put("中国新闻社", 1771);
		 categoryMap.put("历史资料", 1772);
		 categoryMap.put("人物图库", 1773);
		 categoryMap.put("漫画、图表", 1774);
		 categoryMap.put("台港澳", 1775);
		 categoryMap.put("创意图片", 1776);
		 categoryMap.put("精品图片", 1777);
	}  
	
	/**
	 * 显示签发专题
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/upPic")
	public Object showQianFaTopic(HttpServletRequest request, HttpServletResponse response) {
		ResponseMessage result=new ResponseMessage();
		PreparedStatement pstmt = null;
		Connection conn = null;
		ResultSet rs = null;
		try {
			log.info("============================信件迁移开始！===========================");
			conn =  getConnection();
			String sql = "select * from uninews_Photo where UP_ID_N  > 64500000";
	        pstmt = (PreparedStatement)conn.prepareStatement(sql);
	        rs = pstmt.executeQuery();
	        int col = rs.getMetaData().getColumnCount();
	        System.out.println("col:"+col);
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
		        		filePath = filePath.replaceAll("z:", "c:");
		        	}
		        	String id = rs.getString("UP_ID_N");
		        	String uploadUser = rs.getString("UP_UploadUser_Vc");
		        	
		        	
		        	String fileFullPath = filePath+uploadUser+"_"+"O"+id+".jpg";
//		        	String filePath = "D:\\xinhuaphoto\\20170820\\XxjpsgC000668_20170820_TPPFN1A001.jpg";
		        	
					dateStr = rs.getString("UP_PhotoDate_Dt");//日期
		        	System.out.println(dateStr.length());
		        	if(dateStr!=null && dateStr.length()>20 ){
		        		dateStr = dateStr.substring(0, dateStr.length()-2);
		        	}
		        	title = rs.getString("UP_Title_Vc");
		        	author = rs.getString("UP_Author_Vc");
		        	keyWordsStr = rs.getString("UP_KeyWord_Vc");
		        	content = rs.getString("UP_Content_T");
		        	
		        	cateIdsStr = categoryMap.get("精品图片")+"";
		        
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
					List<CpPicture> pics = pictureService.uploadMorePicByPicFiles(picFile, siteid,map);
					System.out.println("上传成功");
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
					group.setFileTime(DateUtil.convertStringToDate(dateStr));
					
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
				e.printStackTrace();
			}
	        
	      }  
		}catch(Exception e1){
			System.out.println("==================出错啦=================");
			++FAILED_PIC_NUM;
			e1.printStackTrace();
			log.error("迁移稿件失败，"+e1.getMessage());
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
		
	
	
}
