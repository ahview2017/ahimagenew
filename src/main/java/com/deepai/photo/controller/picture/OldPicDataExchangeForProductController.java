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
 * 历史数据迁移接口
 * @author xiayunan
 * @date   2017年8月21日
 *
 */
@Controller
@RequestMapping("/oldPicDataForProductExchange")
public class OldPicDataExchangeForProductController {
	private  Logger log=Logger.getLogger(OldPicDataExchangeForProductController.class);
	@Autowired
	private  PictureDataExchangeService pictureService;
	@Autowired
	private  FlowService flowService;
	@Autowired
	private CpUserMapper cpUserMapper;
	@Autowired
	private CpPicGroupMapper cpPicGroupMapper;
	public static final String SESSION_LANGTYPE = "session_langType";
	private static final int FIRST_EDIT_ID = 409;
	private static final int SECOND_EDIT_ID = 406;
	private static final int THIRD_EDIT_ID = 352;
	private static final String AUTHOR_NAME = "吴文兵";
	private static final int AUTHOR_ID = 409;
	private static final int DATA_EXCHANGE_CHNL_ID = 3063;
	private static final String FILE_SEP = File.separator;
	private static final int HISTORY_LAYOUT_COLUMN_ID = 100182646;//老照片分类ID   农业：100182646  政治：1761	 工业：
	private static int SUCCESS_PIC_NUM = 0;
	private static int FAILED_PIC_NUM = 0;
	
	/**
	 * 显示签发专题
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@SkipLoginCheck
	@RequestMapping("/upPic")
	public Object showQianFaTopic(HttpServletRequest request, HttpServletResponse response) {
		ResponseMessage result=new ResponseMessage();
		log.info("============================老照片迁移开始！===========================");
		String rootPicPath = FILE_SEP+"trsphoto"+FILE_SEP+"dataexchange"+FILE_SEP+"oldpic"+FILE_SEP+"zyld";
		result = traverseFolder(rootPicPath,result);
		log.info("============================老照片迁移结束！===========================");
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
                    	String author = "视觉新闻中心";//作者
    					String dateStr = "";//日期
    					String title = "";//标题
    					String keyWordsStr = "";//关键词
    					String content = "";//内容
    					String cateIdsStr = HISTORY_LAYOUT_COLUMN_ID+"";
    					try {
    						String fileName = file2.getName();
    						System.out.println("<<<<<<<<<<<<<<<<<<<<<<fileName:"+fileName);
//    						layoutIndex = fileName.substring(8,10);
//    						System.out.println("<<<<<<<<<<<<<<<<<<layoutIndex:"+layoutIndex);
//    						dateStr = fileName.substring(0,8);
    						dateStr = "2017-09-29";
    						title = fileName;
    						keyWordsStr = fileName;
    						content = title;
    						Map<String,String> map = new HashMap<String,String>();
    						map.put("title", title);
    						map.put("keywords", keyWordsStr);
    						map.put("place", "");
    						map.put("strMemo", content);
    						map.put("authorName", author);
    						int siteid = 1;
    						List<CpPicture> pics = pictureService.uploadMorePicByPicFiles(file2, siteid,map);
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
//    						String res=flowService.checkAndEditGroup(picData, group, firstEditUser ,DateUtil.getDate(new Date()), siteid, 1,cateIdsStr,type);
//    						if(res!=null){
//    							result.setCode(CommonConstant.SUCCESSCODE212);
//    							result.setMsg("存在敏感词："+res);
//    						}else{
    							flowService.examByProofread(cpPicGroupMapper.selectByPrimaryKey(group.getId()),firstEditUser , 1,null);//一审
    							flowService.examByProofread(cpPicGroupMapper.selectByPrimaryKey(group.getId()),secondEditUser , 2,null);//二审
    							
    							CpPicGroupCategory cpPicGroupCategory = new CpPicGroupCategory();
    							cpPicGroupCategory.setType(0);
    							cpPicGroupCategory.setPosition(0);
    							cpPicGroupCategory.setCategoryId(DATA_EXCHANGE_CHNL_ID);
    							
//    							CpPicGroupCategory cpPicGroupCategory1 = new CpPicGroupCategory();
//    							cpPicGroupCategory1.setType(0);
//    							cpPicGroupCategory1.setCategoryId(HISTORY_LAYOUT_COLUMN_ID);
    							List<CpPicGroupCategory> list = new ArrayList<CpPicGroupCategory>();
    							list.add(cpPicGroupCategory);
//    							list.add(cpPicGroupCategory1);
    							String cateData = gson.toJson(list);
    							System.out.println("cateData:"+cateData);
    							List<Map<String,Object>> cates = gson.fromJson(cateData, new TypeToken<List<Map<String,Object>>>(){}.getType());
    							//稿件签发
    							CpPicGroup cpPicGroup = cpPicGroupMapper.selectByPrimaryKey(group.getId());
    							cpPicGroup.setProperties((byte)2);//设置组图新闻性为老照片
//    							flowService.examByProofread(cpPicGroup,thirdEditUser, 3,cates);
    							flowService.examByProofreadForHistory(cpPicGroup,thirdEditUser, 3,cates);
    							result.setCode(CommonConstant.SUCCESSCODE);
    							result.setMsg(CommonConstant.SUCCESSSTRING);
    							result.setOther(String.format("三审审核提交稿件groupid=【%s】",group.getId()));
    							log.info("第"+(++SUCCESS_PIC_NUM)+"篇稿件迁移成功,稿件标题："+title+"！");
//    						}
    					} catch (Exception e) {
    						System.out.println("==================出错啦=================");
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
	
//	public  String formatStr(String dateStr){
//		StringBuilder sb = new StringBuilder();
//		System.out.println(dateStr.length());
//		if(dateStr.length()>=8){
//			sb.append(dateStr.substring(0, 4));
//			sb.append("-");
//			sb.append(dateStr.substring(4,6));
//			sb.append("-");
//			sb.append(dateStr.substring(6,8));
//		}
//		return sb.toString();
//	}
	
	
}
