package com.deepai.photo.controller.picture;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
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
import com.deepai.photo.common.annotation.SkipLoginCheck;
import com.deepai.photo.common.constant.CommonConstant;
import com.deepai.photo.common.pojo.ResponseMessage;
import com.deepai.photo.common.util.date.DateUtil;
import com.deepai.photo.mapper.CpPicGroupMapper;
import com.deepai.photo.mapper.CpUserMapper;
import com.deepai.photo.service.picture.FlowService;
import com.deepai.photo.service.picture.PictureDataExchangeService;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import cnml.apifactory.CNMLAPIImpl;
import cnml.node.AdministrationMetaGroup;
import cnml.node.CNML;
import cnml.node.ContentItem;
import cnml.node.Creators;
import cnml.node.DescriptionMetaGroup;
import cnml.node.FirstCreateTime;
import cnml.node.HeadLine;
import cnml.node.Item;
import cnml.node.Items;
import cnml.node.Keywords;
import cnml.node.MetaInfo;
import cnml.node.Name;
import cnml.node.Titles;

/**
 * 新华社数据迁移接口
 * @author xiayunan
 * @date   2017年8月21日
 *
 */
@Controller
@RequestMapping("/xhDataMigrationCtro")
public class XHDataMigrationController {
	private Logger log=Logger.getLogger(XHDataMigrationController.class);
	@Autowired
	private PictureDataExchangeService pictureService;
	@Autowired
	private FlowService flowService;
	@Autowired
	private CpUserMapper cpUserMapper;
	@Autowired
	private CpPicGroupMapper cpPicGroupMapper;
	public static final String SESSION_LANGTYPE = "session_langType";
	private static final int FIRST_EDIT_ID = 409;
	private static final int SECOND_EDIT_ID = 409;
	private static final int THIRD_EDIT_ID = 352;
	private static final String AUTHOR_NAME = "新华社图片";
	private static final int AUTHOR_ID = 409;
	private static final int DATA_EXCHANGE_CHNL_ID = 3131;//栏目管理-->数据迁移专用
	private static final String FILE_SEP = File.separator;
	private static int SUCCESS_PIC_NUM = 0;
	private static int FAILED_PIC_NUM = 0;
	private static Map<String,Integer> categoryMap = null;
	private static String baseLogPath = "/trsphoto/xinhuashe/logs/";
	//private static String logFileName = new SimpleDateFormat("yyyyMMdd").format(new Date())+".log";
	static{  
		 categoryMap = new HashMap<String, Integer>();  
		 categoryMap.put("社会", 100182668);
		 categoryMap.put("科技", 100182670);
		 categoryMap.put("教育", 100182678);
		 categoryMap.put("体育", 100182667);
		 categoryMap.put("军事", 100182675);
		 categoryMap.put("其它", 100182669);
		 categoryMap.put("政治法律", 100182665);
		 categoryMap.put("行业经济", 100182666);
		 categoryMap.put("宏观经济", 100182671);
		 categoryMap.put("生态环境", 100182673);
		 categoryMap.put("组织指挥", 100182674);
		 categoryMap.put("文化娱乐", 100182676);
		 categoryMap.put("中央领导人", 100182677);
		 categoryMap.put("医药卫生", 100182679);
		 categoryMap.put("奥运会", 100182685);
		 categoryMap.put("突发事件", 100182664);
	}  
	
	
	/**
	 * 数据迁移
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/dataMigration")
	@SkipLoginCheck
	public Object dataMigration(HttpServletRequest request, HttpServletResponse response) {
		ResponseMessage result=new ResponseMessage();
		log.info("============================新华社图片迁移开始！===========================");
		
		Calendar cad = Calendar.getInstance();  
        String month=String.valueOf(cad.get(Calendar.MONTH) + 1);  
        if(month.length()<2){  
            month=0+month;  
        }  
        String savePath = baseLogPath+String.valueOf(cad.get(Calendar.YEAR))+File.separator+month; 
        createYearMonthDir(savePath);//生成年月目录
        String logTimeStamp =  new SimpleDateFormat("yyyyMMdd").format(new Date())+"";
        String logFileName =logTimeStamp+".log";
		String logPath = savePath+File.separator+logFileName;
		String rootPicPath = FILE_SEP+"mnt"+FILE_SEP+"input"+FILE_SEP+"photo"+FILE_SEP+logTimeStamp;//新华社共享目录路径
		result = traverseFolder(rootPicPath,result,logPath);
		log.info("============================新华社图片迁移结束！===========================");
		log.info("信件迁移结束！成功数："+SUCCESS_PIC_NUM+"失败数："+FAILED_PIC_NUM+"!");
		return result;
	}
	
	
	/**
	 * 生成年月文件夹
	 * @auhtor xiayunan
	 * @date 20171228
	 * @return
	 */
	public void createYearMonthDir(String savePath){
        File dirFile = new File(savePath);  
        boolean bFile = dirFile.exists(); 
        if(!bFile){
        	dirFile.mkdirs();
        }
	}
	
	
	/**
	 * 判断文件是否包含某个字符串
	 * @param filePath
	 * @param sContens
	 * @return
	 * @throws IOException
	 */
	public static boolean IsFileContensStr(String filePath,String sContens) throws IOException{
		BufferedReader br = null;
		try {
			br = new BufferedReader(
					new InputStreamReader(
							new FileInputStream(filePath),"UTF-8"));
			String line = null;
			while(( line=br.readLine())!=null){
				if(line.contains(sContens)){
					return true;
				}
			}
			return false;
		} finally{
			if(br!=null){
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	/**
	 * 写日志文件
	 * @author xiayunan
	 * @param contents
	 */
	public void writeXHLogs(String contents,String  logPath){
		FileOutputStream fos = null;
		OutputStreamWriter osw = null;
        PrintWriter pw = null;
        SimpleDateFormat sdf = null;
		try {
			//sdf = new SimpleDateFormat("yyyyMMdd");
			fos = new FileOutputStream(logPath,true);
			osw = new OutputStreamWriter(fos,"UTF-8");
			pw =  new PrintWriter(osw);
			sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String timeStr = sdf.format(new Date());
	        pw.write(timeStr+": "+contents);
	        pw.println();
		} catch (IOException e) {
			e.printStackTrace();
		} finally{
			if(pw!=null){
				pw.close();	
			}
		}
	}
	
	
	/**
	 * 递归获取文件夹内所有文件
	 * @param path 要遍历的文件夹根目录
	 * @param path 迁移日志目录
	 */
	public ResponseMessage traverseFolder(String path,ResponseMessage result,String logPath) {
		writeXHLogs("",logPath);
		File file = new File(path);
        if (file.exists()) {
            File[] files = file.listFiles();
            if (files.length == 0) {
            	log.error("文件夹为空！");
            	result.setCode(999);
				result.setMsg("待迁移的文件夹为空！");
				return result;
            } else {
                for (File file2 : files) {
                    if (file2.isDirectory()) {
                        log.info("开始处理文件夹:" + file2.getName()+"内图片");
                        traverseFolder(file2.getAbsolutePath(),result,logPath);
                    }else {
                    	
                    	log.info("迁移文件:" + file2.getName());
                    	
    					try {
    						/**
        					 * 1.解析新华CNML文件
        					 */
        					String dateStr = "";//日期
        					String title = "";//标题
        					String author = "";//作者
        					String keyWordsStr = "";//关键词
        					String content = "";//内容
        					String cateIdsStr = "";
        					
        					boolean flag = IsFileContensStr(logPath,file2.getName());//判断当前文件是否已经迁移成功
        					if(!flag){//当前文件没有迁移过
        						if(file2.getName().endsWith("xml")){
        							CNMLAPIImpl cnmlImpl = new CNMLAPIImpl();
        							CNML cnml = cnmlImpl.parse(file2.getPath());
        							Items items = cnml.getItems();
        							Item item = items.getItem(0);
        							MetaInfo metaInfo = item.getMetaInfo();
        							AdministrationMetaGroup administrationMetaGroup =  metaInfo.getAdministrationMetaGroup();
        							FirstCreateTime firstCreateTime = administrationMetaGroup.getFirstCreateTime();
        							dateStr = firstCreateTime.getText();//日期
        							if(!"".equals(dateStr)&&dateStr!=null){
        								dateStr = dateStr.substring(0, 10);
        							}
        							
        							DescriptionMetaGroup descriptionMetaGroup = metaInfo.getDescriptionMetaGroup();
        							Titles titles = descriptionMetaGroup.getTitles();
        							HeadLine HeadLine = titles.getHeadLine(0);
        							title = HeadLine.getText();//标题
        							Creators creators = descriptionMetaGroup.getCreators();
        							Name name = creators.getCreator(0).getName(0);
        							author = name.getFullNameText(0);//作者
        							Keywords keyWords = descriptionMetaGroup.getKeywords();
        							if(keyWords!=null){
        								keyWordsStr = keyWords.getKeywordText(0);//关键词
        							}
        							String categoryStr = descriptionMetaGroup.getSubjectCodes().getSubjectCode(1).getMainCode().getNameText(0);
        							if(categoryStr.indexOf("社会")!=-1){
        								categoryStr = "社会";
        							}else if(categoryStr.indexOf("科技")!=-1){
        								categoryStr = "科技";
        							}else if(categoryStr.indexOf("教育")!=-1){
        								categoryStr = "教育";
        							}else if(categoryStr.indexOf("体育")!=-1){
        								categoryStr = "体育";
        							}else if(categoryStr.indexOf("军事")!=-1){
        								categoryStr = "军事";
        							}else if(categoryStr.indexOf("政治")!=-1){
        								categoryStr = "政治法律";
        							}else if(categoryStr.indexOf("行业经济")!=-1){
        								categoryStr = "行业经济";
        							}else if(categoryStr.indexOf("宏观经济")!=-1){
        								categoryStr = "宏观经济";
        							}else if(categoryStr.indexOf("生态环境")!=-1){
        								categoryStr = "生态环境";
        							}else if(categoryStr.indexOf("组织指挥")!=-1){
        								categoryStr = "组织指挥";
        							}else if(categoryStr.indexOf("文化娱乐")!=-1){
        								categoryStr = "文化娱乐";
        							}else if(categoryStr.indexOf("中央领导人")!=-1){
        								categoryStr = "中央领导人";
        							}else if(categoryStr.indexOf("宏观经济")!=-1){
        								categoryStr = "宏观经济";
        							}else if(categoryStr.indexOf("医药卫生")!=-1){
        								categoryStr = "医药卫生";
        							}else if(categoryStr.indexOf("奥运会")!=-1){
        								categoryStr = "奥运会";
        							}else if(categoryStr.indexOf("突发事件")!=-1){
        								categoryStr = "突发事件";
        							}else{
        								categoryStr = "其它";
        							}
        							cateIdsStr = categoryMap.get(categoryStr).toString();
        							
        							
        							ContentItem ContentItem = item.getContents().getContentItem(0);
        							if(ContentItem!=null&&ContentItem.getDataContent()!=null){
        								content = ContentItem.getDataContent().getText();
        							}
        							
        							/**
        							 * 2.上传稿件
        							 */
        							File picFile =  new File(file2.getPath().substring(0, file2.getPath().indexOf("."))+"A001.jpg");
        							Map<String,String> map = new HashMap<String,String>();
        							map.put("title", title);
        							map.put("keywords", keyWordsStr);
        							map.put("place", "");
        							map.put("strMemo", content);
        							map.put("authorName", author);
        							int siteid = 1;
        							List<CpPicture> pics = pictureService.uploadMorePicByPicFiles(picFile, siteid,map);
//        							result.setCode(CommonConstant.SUCCESSCODE);
//        							result.setMsg(CommonConstant.SUCCESSSTRING);
//        							result.setData(pics);
//        							result.setOther(String.format("上传图片=%s", pics));
        							
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
        							group.setProperties((byte)3);
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
        							CpUser firstEditUser = cpUserMapper.selectByPrimaryKey(FIRST_EDIT_ID);
        							CpUser secondEditUser = cpUserMapper.selectByPrimaryKey(SECOND_EDIT_ID);
        							CpUser thirdEditUser = cpUserMapper.selectByPrimaryKey(THIRD_EDIT_ID);
        							flowService.addCategoryForGroup(group.getId(),cateIdsStr);
    								flowService.examByProofread(cpPicGroupMapper.selectByPrimaryKey(group.getId()),firstEditUser , 1,null);//一审
    								flowService.examByProofread(cpPicGroupMapper.selectByPrimaryKey(group.getId()),secondEditUser , 2,null);//二审
    								
    								CpPicGroupCategory cpPicGroupCategory = new CpPicGroupCategory();
    								cpPicGroupCategory.setType(0);
    								cpPicGroupCategory.setPosition(0);
    								cpPicGroupCategory.setCategoryId(DATA_EXCHANGE_CHNL_ID);
    								List<CpPicGroupCategory> list = new ArrayList<CpPicGroupCategory>();
    								list.add(cpPicGroupCategory);
    								String cateData = gson.toJson(list);
    								List<Map<String,Object>> cates = gson.fromJson(cateData, new TypeToken<List<Map<String,Object>>>(){}.getType());
    								//稿件签发
    								
    								flowService.examByProofread(cpPicGroupMapper.selectByPrimaryKey(group.getId()),thirdEditUser, 3,cates);
    								result.setCode(CommonConstant.SUCCESSCODE);
    								result.setMsg(CommonConstant.SUCCESSSTRING);
//    								result.setOther(String.format("三审审核提交稿件groupid=【%s】",group.getId()));
    								log.info("第"+(++SUCCESS_PIC_NUM)+"篇稿件迁移成功,稿件标题："+title+"！");
    								writeXHLogs("第"+(SUCCESS_PIC_NUM)+"篇稿件迁移成功,稿件文件名："+file2.getName()+"！",logPath);
        						}
                        	}
    						
    					} catch (Exception e) {
    						log.error("==================出错啦=================");
    						++FAILED_PIC_NUM;
    						e.printStackTrace();
    						log.error("迁移稿件失败，"+e.getMessage());
    						result.setCode(CommonConstant.EXCEPTIONCODE);
    						result.setMsg(CommonConstant.EXCEPTIONMSG);
    					}
    					
                    	
                    }
                }
            }
        } else {
            log.error("文件不存在!");
            result.setCode(998);
			result.setMsg("文件不存在！");
			return result;
        }
        return result;
	}
	
	
	
}
