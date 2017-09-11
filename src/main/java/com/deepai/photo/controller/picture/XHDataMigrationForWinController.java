package com.deepai.photo.controller.picture;

import java.io.File;
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
@RequestMapping("/xhDataMigrationForWinCtro")
public class XHDataMigrationForWinController {
	private Logger log=Logger.getLogger(XHDataMigrationForWinController.class);
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
	private static final int DATA_EXCHANGE_CHNL_ID = 3063;//栏目管理-->数据迁移专用
	private static final String FILE_SEP = File.separator;
	private static int SUCCESS_PIC_NUM = 0;
	private static int FAILED_PIC_NUM = 0;
	private static Map<String,Integer> categoryMap = null;
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
	 * 数据迁移
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/dataMigration")
	public Object dataMigration(HttpServletRequest request, HttpServletResponse response) {
		ResponseMessage result=new ResponseMessage();
		try {
			log.info("============================信件迁移开始！===========================");
			//String dir = FILE_SEP+"home"+FILE_SEP+"temp"+FILE_SEP+"xinhuapic"+FILE_SEP;
			String dir = "D:\\xinhuaphoto\\20170822\\";
			File dirFile = new File(dir);
			if(dirFile.isDirectory()){
				File[] files = dirFile.listFiles();
				for(File file:files){
					/**
					 * 1.解析新华CNML文件
					 */
					String dateStr = "";//日期
					String title = "";//标题
					String author = "";//作者
					String keyWordsStr = "";//关键词
					String content = "";//内容
					String cateIdsStr = "";
					try {
						if(file.getName().endsWith("xml")){
							System.out.println("========文件名：==========："+file.getName());
							CNMLAPIImpl cnmlImpl = new CNMLAPIImpl();
							CNML cnml = cnmlImpl.parse(file.getPath());
							Items items = cnml.getItems();
							Item item = items.getItem(0);
							MetaInfo metaInfo = item.getMetaInfo();
							AdministrationMetaGroup administrationMetaGroup =  metaInfo.getAdministrationMetaGroup();
							FirstCreateTime firstCreateTime = administrationMetaGroup.getFirstCreateTime();
							dateStr = firstCreateTime.getText();//日期
							if(!"".equals(dateStr)&&dateStr!=null){
								dateStr = dateStr.substring(0, 10);
							}
							System.out.println("dateStr:"+dateStr);
							
							DescriptionMetaGroup descriptionMetaGroup = metaInfo.getDescriptionMetaGroup();
							Titles titles = descriptionMetaGroup.getTitles();
							HeadLine HeadLine = titles.getHeadLine(0);
							title = HeadLine.getText();//标题
							System.out.println("title:"+title);
							Creators creators = descriptionMetaGroup.getCreators();
							Name name = creators.getCreator(0).getName(0);
							author = name.getFullNameText(0);//作者
							System.out.println("author:"+author);
							Keywords keyWords = descriptionMetaGroup.getKeywords();
							if(keyWords!=null){
								keyWordsStr = keyWords.getKeywordText(0);//关键词
							}
							System.out.println("keyWordsStr:"+keyWordsStr);
							//栏目分类
							String categoryStr = descriptionMetaGroup.getSubjectCodes().getSubjectCode(1).getMainCode().getNameText(0);
							if(categoryStr.indexOf("体育")!=-1){
								categoryStr = "体育";
							}else if(categoryStr.indexOf("政治法律")!=-1){
								categoryStr = "政治";
							}else if(categoryStr.indexOf("社会")!=-1){
								categoryStr = "社会生活";
							}else if(categoryStr.indexOf("科技")!=-1){
								categoryStr = "科技教育卫生";
							}else if(categoryStr.indexOf("军事")!=-1){
								categoryStr = "法制与军事";
							}else if(categoryStr.indexOf("经济")!=-1){
								categoryStr = "经济";
							}else if(categoryStr.indexOf("经济")!=-1){
								categoryStr = "经济";
							}else if(categoryStr.indexOf("文化娱乐")!=-1){
								categoryStr = "文化艺术";
							}else if(categoryStr.indexOf("环境")!=-1){
								categoryStr = "自然环境";
							}else{
								categoryStr = "精品图片";
							}
							cateIdsStr = categoryMap.get(categoryStr)+"";
							
							
							ContentItem ContentItem = item.getContents().getContentItem(0);
							if(ContentItem!=null&&ContentItem.getDataContent()!=null){
								content = ContentItem.getDataContent().getText();
							}
							System.out.println("content:"+content);
							
							/**
							 * 2.上传稿件
							 */
							File picFile =  new File(file.getPath().substring(0, file.getPath().indexOf("."))+"A001.jpg");
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
								//稿件签发
								flowService.examByProofread(cpPicGroupMapper.selectByPrimaryKey(group.getId()),thirdEditUser, 3,cates);
								result.setCode(CommonConstant.SUCCESSCODE);
								result.setMsg(CommonConstant.SUCCESSSTRING);
								result.setOther(String.format("三审审核提交稿件groupid=【%s】",group.getId()));
							}
						}
					} catch (Exception e) {
						System.out.println("==================出错啦=================");
						++FAILED_PIC_NUM;
						e.printStackTrace();
						log.error("迁移稿件失败，"+e.getMessage());
						result.setCode(CommonConstant.EXCEPTIONCODE);
						result.setMsg(CommonConstant.EXCEPTIONMSG);
					}
					log.info("第"+(++SUCCESS_PIC_NUM)+"篇稿件迁移成功,稿件标题："+title+"！");
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		log.info("============================信件迁移结束！===========================");
		log.info("信件迁移结束！成功数："+SUCCESS_PIC_NUM+"失败数："+FAILED_PIC_NUM+"!");
		return result;
	}
	
	
	
	/**
	 * 递归获取文件夹内所有文件
	 * @param path 要遍历的文件夹根目录
	 */
	public ResponseMessage traverseFolder(String path,ResponseMessage result) {
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
                        traverseFolder(file2.getAbsolutePath(),result);
                    } else {
                    	log.info("迁移文件:" + file2.getName());
                    	/**
    					 * 1.解析新华CNML文件
    					 */
    					String dateStr = "";//日期
    					String title = "";//标题
    					String author = "";//作者
    					String keyWordsStr = "";//关键词
    					String content = "";//内容
    					String cateIdsStr = "";
    					try {
    						if(file.getName().endsWith("xml")){
    							System.out.println("========文件名：==========："+file.getName());
    							CNMLAPIImpl cnmlImpl = new CNMLAPIImpl();
    							CNML cnml = cnmlImpl.parse(file.getPath());
    							Items items = cnml.getItems();
    							Item item = items.getItem(0);
    							MetaInfo metaInfo = item.getMetaInfo();
    							AdministrationMetaGroup administrationMetaGroup =  metaInfo.getAdministrationMetaGroup();
    							FirstCreateTime firstCreateTime = administrationMetaGroup.getFirstCreateTime();
    							dateStr = firstCreateTime.getText();//日期
    							if(!"".equals(dateStr)&&dateStr!=null){
    								dateStr = dateStr.substring(0, 10);
    							}
    							System.out.println("dateStr:"+dateStr);
    							
    							DescriptionMetaGroup descriptionMetaGroup = metaInfo.getDescriptionMetaGroup();
    							Titles titles = descriptionMetaGroup.getTitles();
    							HeadLine HeadLine = titles.getHeadLine(0);
    							title = HeadLine.getText();//标题
    							System.out.println("title:"+title);
    							Creators creators = descriptionMetaGroup.getCreators();
    							Name name = creators.getCreator(0).getName(0);
    							author = name.getFullNameText(0);//作者
    							System.out.println("author:"+author);
    							Keywords keyWords = descriptionMetaGroup.getKeywords();
    							if(keyWords!=null){
    								keyWordsStr = keyWords.getKeywordText(0);//关键词
    							}
    							System.out.println("keyWordsStr:"+keyWordsStr);
    							//栏目分类
    							String categoryStr = descriptionMetaGroup.getSubjectCodes().getSubjectCode(1).getMainCode().getNameText(0);
    							if(categoryStr.indexOf("体育")!=-1){
    								categoryStr = "体育";
    							}else if(categoryStr.indexOf("政治法律")!=-1){
    								categoryStr = "政治";
    							}else if(categoryStr.indexOf("社会")!=-1){
    								categoryStr = "社会生活";
    							}else if(categoryStr.indexOf("科技")!=-1){
    								categoryStr = "科技教育卫生";
    							}else if(categoryStr.indexOf("军事")!=-1){
    								categoryStr = "法制与军事";
    							}else if(categoryStr.indexOf("经济")!=-1){
    								categoryStr = "经济";
    							}else if(categoryStr.indexOf("经济")!=-1){
    								categoryStr = "经济";
    							}else if(categoryStr.indexOf("文化娱乐")!=-1){
    								categoryStr = "文化艺术";
    							}else if(categoryStr.indexOf("环境")!=-1){
    								categoryStr = "自然环境";
    							}else{
    								categoryStr = "精品图片";
    							}
    							cateIdsStr = categoryMap.get(categoryStr)+"";
    							
    							
    							ContentItem ContentItem = item.getContents().getContentItem(0);
    							if(ContentItem!=null&&ContentItem.getDataContent()!=null){
    								content = ContentItem.getDataContent().getText();
    							}
    							System.out.println("content:"+content);
    							
    							/**
    							 * 2.上传稿件
    							 */
    							File picFile =  new File(file.getPath().substring(0, file.getPath().indexOf("."))+"A001.jpg");
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
    								//稿件签发
    								flowService.examByProofread(cpPicGroupMapper.selectByPrimaryKey(group.getId()),thirdEditUser, 3,cates);
    								result.setCode(CommonConstant.SUCCESSCODE);
    								result.setMsg(CommonConstant.SUCCESSSTRING);
    								result.setOther(String.format("三审审核提交稿件groupid=【%s】",group.getId()));
    								log.info("第"+(++SUCCESS_PIC_NUM)+"篇稿件迁移成功,稿件标题："+title+"！");
    							}
    						}
    					} catch (Exception e) {
    						System.out.println("==================出错啦=================");
    						++FAILED_PIC_NUM;
    						e.printStackTrace();
    						log.error("迁移稿件失败，"+e.getMessage());
    						result.setCode(CommonConstant.EXCEPTIONCODE);
    						result.setMsg(CommonConstant.EXCEPTIONMSG);
    					}
    					log.info("第"+(++SUCCESS_PIC_NUM)+"篇稿件迁移成功,稿件标题："+title+"！");
                    	
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
