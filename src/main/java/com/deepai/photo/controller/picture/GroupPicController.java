package com.deepai.photo.controller.picture;

import java.io.File;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanMap;
import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.deepai.photo.bean.CpColumn;
import com.deepai.photo.bean.CpGroupPush;
import com.deepai.photo.bean.CpPicAllpath;
import com.deepai.photo.bean.CpPicGroup;
import com.deepai.photo.bean.CpPicGroupCategory;
import com.deepai.photo.bean.CpPicGroupCategoryExample;
import com.deepai.photo.bean.CpPicGroupProcess;
import com.deepai.photo.bean.CpPicGroupProcessExample;
import com.deepai.photo.bean.CpPicGroupProcessExample.Criteria;
import com.deepai.photo.bean.CpPicture;
import com.deepai.photo.bean.CpPictureExample;
import com.deepai.photo.bean.CpTopic;
import com.deepai.photo.bean.CpUser;
import com.deepai.photo.bean.GroupQuery;
import com.deepai.photo.common.StringUtil;
import com.deepai.photo.common.annotation.LogInfo;
import com.deepai.photo.common.annotation.SkipAuthCheck;
import com.deepai.photo.common.annotation.SkipLoginCheck;
import com.deepai.photo.common.constant.CommonConstant;
import com.deepai.photo.common.constant.SysConfigConstant;
import com.deepai.photo.common.exception.InvalidHttpArgumentException;
import com.deepai.photo.common.pagehelper.PageHelper;
import com.deepai.photo.common.pojo.ResponseMessage;
import com.deepai.photo.common.redis.RedisClientTemplate;
import com.deepai.photo.common.util.SessionUtils;
import com.deepai.photo.common.util.XMLUtils;
import com.deepai.photo.common.util.date.DateUtils;
import com.deepai.photo.common.util.image.ImageConfig;
import com.deepai.photo.common.util.image.ImgFileUtils;
import com.deepai.photo.common.util.json.JsonUtil;
import com.deepai.photo.common.validation.CommonValidation;
import com.deepai.photo.mapper.AboutPictureMapper;
import com.deepai.photo.mapper.ClientPictureMapper;
import com.deepai.photo.mapper.CpColumnMapper;
import com.deepai.photo.mapper.CpPicAllpathMapper;
import com.deepai.photo.mapper.CpPicGroupCategoryMapper;
import com.deepai.photo.mapper.CpPicGroupColumnMapper;
import com.deepai.photo.mapper.CpPicGroupMapper;
import com.deepai.photo.mapper.CpPicGroupProcessMapper;
import com.deepai.photo.mapper.CpPictureMapper;
import com.deepai.photo.mapper.CpUserMapper;
import com.deepai.photo.mapper.OtherMapper;
import com.deepai.photo.service.admin.SysConfigService;
import com.deepai.photo.service.admin.UserRoleRightService;
import com.deepai.photo.service.enColumn.EnSignColumnService;
import com.deepai.photo.service.picture.FlowService;
import com.deepai.photo.service.picture.PictureService;
import com.deepai.photo.service.topic.TopicService;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;


/**
 * @author guoyanhong
 * 稿件
 */
@Controller
@RequestMapping("/groupPicCtro")
public class GroupPicController {
	private Logger log=Logger.getLogger(GroupPicController.class);
	
	@Autowired
	private SysConfigService sysConfigService;
	@Autowired
	private PictureService pictureService;
	@Autowired
	private FlowService flowService;
	@Autowired
	private AboutPictureMapper aboutPictureMapper;
	@Autowired
	private CpPicGroupMapper cpPicGroupMapper;
	@Autowired
	private RedisClientTemplate redisClientTemplate;
	@Autowired
	private CpPicGroupCategoryMapper categoryMapper;
	@Autowired
	private CpPicGroupProcessMapper processMapper;
	@Autowired
	private ClientPictureMapper clientPictureMapper;
	@Autowired
	private OtherMapper otherMapper;
	@Autowired
	private TopicService  topicService;
	@Autowired
	private CpPicGroupProcessMapper groupProcessMapper;
	@Autowired
	private CpPicGroupMapper groupMapper;
	@Autowired
	private CpPictureMapper pictureMapper;
	@Autowired
	private CpPicAllpathMapper allPathMapper;
	@Autowired
	private EnSignColumnService enSignColumnService;
	@Autowired
	private CpUserMapper cpUserMapper;
	@Autowired
	private CpPicGroupColumnMapper cpPicGroupColumnMapper;
	@Autowired
	private UserRoleRightService userRoleRightService;
	@Autowired
	private CpColumnMapper columnMapper;
	public static final String SESSION_LANGTYPE = "session_langType";
	/** 
	 * 上传稿件图片，显示缩略图
	 * @param request
	 * @param Integer groupId
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/upPic")
	@LogInfo(content="上传图片",opeType=0,logTypeCode=CommonConstant.Picture)
	public ResponseMessage upPicForGroup(HttpServletRequest request,HttpServletResponse response,@RequestParam(required = false, value = "picFiles")MultipartFile[]  picFiles,Integer langType){
		ResponseMessage result=new ResponseMessage();
		try {
			if(picFiles==null||picFiles.length==0){
				result.setCode(CommonConstant.FAILURECODE);
				result.setMsg("请上传图片");
				return result;
			}
			
			CpUser user = SessionUtils.getUser(request);
			List<CpPicture> resData=pictureService.uploadMorePic(picFiles,  SessionUtils.getSiteId(request), user.getId());
			
			result.setCode(CommonConstant.SUCCESSCODE);
			result.setMsg(CommonConstant.SUCCESSSTRING);
			result.setData(resData);
			result.setOther(String.format("上传图片=%s", resData));
		} catch (InvalidHttpArgumentException e) {
			result.setCode(e.getCode());
			result.setMsg(e.getMsg());
		}catch(Exception e1){
			e1.printStackTrace();
			log.error("上传图片出错，"+e1.getMessage());
			result.setCode(CommonConstant.EXCEPTIONCODE);
			result.setMsg(CommonConstant.EXCEPTIONMSG);
		}
		return result;
	}

	/**
	 * 保存或提交稿件：稿件不存在
	 * @param request
	 * @param picData 多张图片信息；json
	 * @param group 稿件信息
	 * @param isIpTc 是否为iptc图
	 * @param isFlash 是否为flash上传
	 * @param fTime 拍摄时间
	 * @param type 0保存，1提交
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/saveGroupPic")
	@LogInfo(content="保存或提交中文稿件",opeType=2,logTypeCode=CommonConstant.PicGroupOperation)
	public Object saveGroupPic(HttpServletRequest request,String picData,CpPicGroup group,Integer isIpTc,Integer isFlash,String fTime,Integer type,Integer roleId,String cateIds){
		ResponseMessage result=new ResponseMessage();
		try {
			CommonValidation.checkParamBlank(group.getTitle(), "稿件标题");
			CommonValidation.checkParamBlank(group.getKeywords(), "关键字");
			CommonValidation.checkParamBlank(group.getAuthor(), "稿件作者");
			CommonValidation.checkParamBlank(group.getAuthorId()+"", "稿件作者Id");
			CommonValidation.checkParamBlank(group.getMemo(), "稿件说明");
			CommonValidation.checkParamBlank(fTime, "拍摄时间");
			CommonValidation.checkParamBlank(roleId+"", "用户角色ID");
			CommonValidation.checkParamBlank(Integer.toString(group.getLangType()), "稿件语种");
			if(StringUtil.isEmpty(picData)){
				result.setCode(CommonConstant.FAILURECODE);
				result.setMsg("请上传图片");
				return result;
			}
			Gson gson = new Gson();  
			List<CpPicture> pics = gson.fromJson(picData, new TypeToken<List<CpPicture>>(){}.getType());
			if(pics==null||pics.size()==0){
				result.setCode(CommonConstant.FAILURECODE);
				result.setMsg("请上传图片");
				return result;
			}
			if(StringUtil.isNotBlank(fTime)){
				group.setFileTime(DateUtils.sdfLong.parse(fTime));
			}
//			boolean isIpTcB=isIpTc==null||isIpTc==0?false:true;//默认不iptc
			boolean isIpTcB=true;
			boolean isFlashB=isFlash==null||isFlash==0?false:true;//默认不是flash组件上传
			type=type==null?0:type;
			String typeName=type==0?"保存":"提交";
			group.setGoodsType(0);//普通图
			if(group.getLangType() == null){
				group.setLangType(0);//中文稿件
			}
			int a=flowService.makePicGroup(pics, group, isIpTcB,SessionUtils.getUser(request),SessionUtils.getSiteId(request),type,roleId,cateIds);
			StringBuffer ids= new StringBuffer();
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
		} catch (InvalidHttpArgumentException e) {
			result.setCode(e.getCode());
			result.setMsg(e.getMsg());
		}catch(Exception e1){
			e1.printStackTrace();
			log.error("保存或提交稿件出错，"+e1.getMessage());
			result.setCode(CommonConstant.EXCEPTIONCODE);
			result.setMsg(CommonConstant.EXCEPTIONMSG+e1.getMessage());
		}
		return result;
	}
	/**
	 * 保存或提交稿件：稿件不存在
	 * @param request
	 * @param picData 多张图片信息；json
	 * @param group 稿件信息
	 * @param isIpTc 是否为iptc图
	 * @param isFlash 是否为flash上传
	 * @param fTime 拍摄时间
	 * @param type 0保存，1提交
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/saveGroupPicForEn")
	@LogInfo(content="保存或提交英文稿件",opeType=2,logTypeCode=CommonConstant.PicGroupOperation)
	public Object saveGroupPicForEn(HttpServletRequest request,String picData,CpPicGroup group,Integer isIpTc,Integer isFlash,String fTime,Integer type,Integer roleId){
		request.getSession(true).setAttribute("session_langType",group.getLangType());
		ResponseMessage result=new ResponseMessage();
		try {
			CommonValidation.checkParamBlank(group.getTitle(), "稿件标题");
			CommonValidation.checkParamBlank(group.getPlace(), "地点");
			CommonValidation.checkParamBlank(group.getAuthor(), "稿件作者");
			CommonValidation.checkParamBlank(group.getAuthorId()+"", "稿件作者Id");
			CommonValidation.checkParamBlank(group.getMemo(), "稿件说明");
			CommonValidation.checkParamBlank(fTime, "拍摄时间");
			CommonValidation.checkParamBlank(roleId+"", "用户角色ID");
			if(StringUtil.isEmpty(picData)){
				result.setCode(CommonConstant.FAILURECODE);
				result.setMsg("请上传图片");
				return result;
			}
			Gson gson = new Gson();  
			List<CpPicture> pics = gson.fromJson(picData, new TypeToken<List<CpPicture>>(){}.getType());
			if(pics==null||pics.size()==0){
				result.setCode(CommonConstant.FAILURECODE);
				result.setMsg("请上传图片");
				return result;
			}
			if(StringUtil.isNotBlank(fTime)){
				group.setFileTime(DateUtils.sdfLong.parse(fTime));
			}
//			boolean isIpTcB=isIpTc==null||isIpTc==0?false:true;//默认不iptc
			boolean isIpTcB=true;
			boolean isFlashB=isFlash==null||isFlash==0?false:true;//默认不是flash组件上传
			type=type==null?0:type;
			String typeName=type==0?"保存":"提交";
			group.setGoodsType(0);//普通图
			group.setLangType(1);//英文稿件
			int a=flowService.makePicGroup(pics, group, isIpTcB,SessionUtils.getUser(request),SessionUtils.getSiteId(request),type,roleId);
			StringBuffer ids= new StringBuffer();
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
		} catch (InvalidHttpArgumentException e) {
			result.setCode(e.getCode());
			result.setMsg(e.getMsg());
		}catch(Exception e1){
			e1.printStackTrace();
			log.error("保存或提交稿件出错，"+e1.getMessage());
			result.setCode(CommonConstant.EXCEPTIONCODE);
			result.setMsg(CommonConstant.EXCEPTIONMSG+e1.getMessage());
		}
		return result;
	}
	
	@ResponseBody
	@RequestMapping("/pushGroupPicForEn")
	@LogInfo(content="推送中文资料库稿件到英文稿件",opeType=2,logTypeCode=CommonConstant.PicGroupOperation)
	public Object pushGroupPicForEn(HttpServletRequest request, Integer isFlash,
			@RequestParam(value = "groupIds[]")String[] groupIds,Integer roleId){
		ResponseMessage result=new ResponseMessage();
		try {
			CpUser user = SessionUtils.getUser(request);
			Integer siteId = SessionUtils.getSiteId(request);
			CommonValidation.checkParamBlank(roleId+"", "用户角色ID");
			
			List<Integer> roles = userRoleRightService.getRoseId(user.getId());
			if(!roles.contains(63)){
				result.setCode(CommonConstant.FAILURECODE);
				result.setMsg("对不起，您没有英文版值班编辑的权限！");
				return result;
			}
			for (String groupId : groupIds) {
				Integer gId = Integer.parseInt(groupId);
				CpPictureExample example = new CpPictureExample();
				com.deepai.photo.bean.CpPictureExample.Criteria criteria = example.createCriteria();
				criteria.andGroupIdEqualTo(gId);
				List<CpPicture> pics = pictureMapper.selectByExample(example);
				if(pics==null||pics.size()==0){
					result.setCode(CommonConstant.FAILURECODE);
					result.setMsg("请上传图片");
					return result;
				}
				CpGroupPush gPush = groupMapper.selectPushByGroupId(gId);
				if(gPush!=null){
					String userName = cpUserMapper.findUserNameByUid(gPush.getUserId());
					result.setCode(CommonConstant.FAILURECODE);
					result.setMsg("该稿件数据已被推送！稿件Id="+gId+",推送人："+userName);
					return result;
				}
			}
			for (String groupId : groupIds) {
				Integer gId = Integer.parseInt(groupId);
				CpPicGroup group = cpPicGroupMapper.selectByPrimaryKey(gId);
				
				CpPictureExample example = new CpPictureExample();
				com.deepai.photo.bean.CpPictureExample.Criteria criteria = example.createCriteria();
				criteria.andGroupIdEqualTo(gId);
				List<CpPicture> pics = pictureMapper.selectByExample(example);
				
				group.setLangType(1);//英文稿件
				group.setGroupStatus(1);//保存状态
				group.setCreateTime(new Date());
				group.setSourceSign(1);//推送标志
				group.setApplyTime(new Date());
				group.setSginTime(null);
				group.setFristPfdUser(user.getUserName());
				
				group.setSiteId(siteId);
				//插入稿件信息
				cpPicGroupMapper.insertSelective(group);
				
				int coverPictureId=0;
				for (CpPicture cpPicture : pics) {
					//重新添加路径表信息
					List<CpPicAllpath> cpAllPaths = allPathMapper.selectByPictureId(cpPicture.getId());
					
					//图片名称
					String fileName = null;
					//通过原始图获取图片名(图片类型)
					for (CpPicAllpath cpPicAllpath : cpAllPaths) {
						if(cpPicAllpath.getAllPath().contains("classification")){
							fileName = pictureService.getPicFileName(cpPicAllpath.getAllPath());
						}
					}
					cpPicture.setGroupId(group.getId());
					cpPicture.setSiteId(siteId);
					cpPicture.setCreateTime(new Date());
					cpPicture.setFilename(fileName);
					cpPicture.setPublishTime(null);
					//插入图片表信息
					pictureMapper.insertSelective(cpPicture);
					for (CpPicAllpath cpPicAllpath : cpAllPaths) {
						
						String path = pictureService.uploadPushPic(fileName,cpPicAllpath, siteId);		
						cpPicAllpath.setTragetId(cpPicture.getId());	
						cpPicAllpath.setAllPath(path);
						allPathMapper.insertSelective(cpPicAllpath);
					}
					
					if(cpPicture.getIsCover()!=null&&cpPicture.getIsCover()==1){
						coverPictureId=cpPicture.getId();
					}
				}
				
				group.setCoverPictureId(coverPictureId);//封面图
				//修改稿件信息
				cpPicGroupMapper.updateByPrimaryKeySelective(group);
				//提交一审
				flowService.submitGruop(siteId, group.getId(), user, roleId, group.getLangType(), 0);
				//插入推送表信息
				CpGroupPush gPush = new CpGroupPush();
				gPush.setGroupId(gId);
				gPush.setPushSign(1);
				gPush.setUserId(user.getId());
				gPush.setCreateTime(new Date());
				groupMapper.insertGroupPush(gPush);
			}
			result.setCode(CommonConstant.SUCCESSCODE);
			result.setMsg(CommonConstant.SUCCESSSTRING);
			result.setOther(String.format("稿件groupIds=%s推送成功",groupIds));
		} catch (InvalidHttpArgumentException e) {
			result.setCode(e.getCode());
			result.setMsg(e.getMsg());
		}catch(Exception e1){
			e1.printStackTrace();
			log.error("保存或提交稿件出错，"+e1.getMessage());
			result.setCode(CommonConstant.EXCEPTIONCODE);
			result.setMsg(CommonConstant.EXCEPTIONMSG+e1.getMessage());
		}
		return result;
	}
	/**
	 * 开始编辑稿件
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/editBegin")
	public Object editBegin(HttpServletRequest request,Integer groupId){
		ResponseMessage result=new ResponseMessage();
		try {
			CommonValidation.checkParamBlank(groupId+"", "稿件ID");
			CpPicGroup oldGroup=cpPicGroupMapper.selectByPrimaryKey(groupId);
			//校验稿件是否正在编辑
			CommonValidation.checkGroupBeginEdit(oldGroup,groupId);
			CpPicGroup group=new CpPicGroup();
			group.setId(groupId);
			group.setIsLocked(1);
			group.setLockerName(SessionUtils.getUser(request).getUserName());
			cpPicGroupMapper.updateByPrimaryKeySelective(group);
			result.setCode(CommonConstant.SUCCESSCODE);
			result.setMsg(CommonConstant.SUCCESSSTRING);
		} catch (InvalidHttpArgumentException e) {
			result.setCode(e.getCode());
			result.setMsg(e.getMsg());
		}catch(Exception e1){
			e1.printStackTrace();
			log.error("开始编辑稿件出错，"+e1.getMessage());
			result.setCode(CommonConstant.EXCEPTIONCODE);
			result.setMsg(CommonConstant.EXCEPTIONMSG+e1.getMessage());
		}
		return result;
	}
	
	/**
	 * 结束编辑稿件:用户编辑不保存返回
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/editEnd")
	public Object editEnd(HttpServletRequest request,Integer groupId){
		ResponseMessage result=new ResponseMessage();
		try {
			CommonValidation.checkParamBlank(groupId+"", "稿件ID");
			CpPicGroup group=new CpPicGroup();
			group.setId(groupId);
			group.setIsLocked(0);
			group.setLockerName("");
			cpPicGroupMapper.updateByPrimaryKeySelective(group);
			result.setCode(CommonConstant.SUCCESSCODE);
			result.setMsg(CommonConstant.SUCCESSSTRING);
		} catch (InvalidHttpArgumentException e) {
			result.setCode(e.getCode());
			result.setMsg(e.getMsg());
		}catch(Exception e1){
			e1.printStackTrace();
			log.error("结束编辑稿件出错，"+e1.getMessage());
			result.setCode(CommonConstant.EXCEPTIONCODE);
			result.setMsg(CommonConstant.EXCEPTIONMSG+e1.getMessage());
		}
		return result;
	}
	
	/**
	 * 强制解锁编辑稿件:记录日志
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/unLockGroup")
	@LogInfo(content="强制解锁稿件",opeType=2,logTypeCode=CommonConstant.PicGroupOperation)
	public Object unLockGroup(HttpServletRequest request,Integer groupId){
		ResponseMessage result=new ResponseMessage();
		try {
			CommonValidation.checkParamBlank(groupId+"", "稿件ID");
			CpPicGroup oldGroup=cpPicGroupMapper.selectByPrimaryKey(groupId);
			if(oldGroup==null){
				throw new InvalidHttpArgumentException(CommonConstant.NULLCODE,String.format("不存在id为%s的稿件",groupId));
			}
			CpUser user=SessionUtils.getUser(request);
			if(oldGroup.getIsLocked()==1){
				flowService.addFlowLog(groupId, 16, null, null, user);
			}
			//强制解锁
			CpPicGroup group=new CpPicGroup();
			group.setId(groupId);
			group.setIsLocked(0);
			group.setLockerName("");
			group.setUnlockName(user.getUserName());
			cpPicGroupMapper.updateByPrimaryKeySelective(group);
			result.setCode(CommonConstant.SUCCESSCODE);
			result.setMsg(CommonConstant.SUCCESSSTRING);
		} catch (InvalidHttpArgumentException e) {
			result.setCode(e.getCode());
			result.setMsg(e.getMsg());
		}catch(Exception e1){
			e1.printStackTrace();
			log.error("强制解锁稿件出错，"+e1.getMessage());
			result.setCode(CommonConstant.EXCEPTIONCODE);
			result.setMsg(CommonConstant.EXCEPTIONMSG+e1.getMessage());
		}
		return result;
	}
	/**
	 * 用户编辑草稿箱稿件
	 * @param request
	 * @param picData 图片信息
	 * @param group 稿件信息
	 * @param fTime 拍摄时间
	 * @param cateIds 分类id，多个用逗号隔开
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/editGroupPic")
	@LogInfo(content="用户编辑草稿箱稿件",opeType=2,logTypeCode=CommonConstant.PicGroupOperation)
	public Object editGroupPic(HttpServletRequest request,String picData,CpPicGroup group,String fTime,String cateIds){
		ResponseMessage result=new ResponseMessage();
		try {
			CommonValidation.checkParamBlank(group.getId()+"", "稿件id");
			String res=flowService.checkAndEditGroup(picData, group, SessionUtils.getUser(request), fTime, SessionUtils.getSiteId(request), 0,cateIds,0);
			result.setCode(CommonConstant.SUCCESSCODE);
//			if(res!=null){
//				result.setMsg("存在敏感词："+res);
//			}else{
				result.setMsg(CommonConstant.SUCCESSSTRING);
//			}
			result.setOther(String.format("编辑稿件groupId=%s成功",group.getId()));
		} catch (InvalidHttpArgumentException e) {
			result.setCode(e.getCode());
			result.setMsg(e.getMsg());
		}catch(Exception e1){
			e1.printStackTrace();
			log.error("用户编辑草稿箱稿件出错，"+e1.getMessage());
			result.setCode(CommonConstant.EXCEPTIONCODE);
			result.setMsg(CommonConstant.EXCEPTIONMSG+e1.getMessage());
		}
		return result;
	}
	/**
	 * 一审编辑稿件
	 * @param request
	 * @param picData 图片信息
	 * @param group 稿件信息
	 * @param fTime 拍摄时间
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/editFristGroupPic")
	@LogInfo(content="一审编辑稿件",opeType=2,logTypeCode=CommonConstant.PicGroupOperation)
	public Object editFristGroupPic(HttpServletRequest request,String picData,CpPicGroup group,String fTime,String cateIds,Integer type){
		ResponseMessage result=new ResponseMessage();
		try {
			CommonValidation.checkParamBlank(group.getId()+"", "稿件id");
			String res=flowService.checkAndEditGroup(picData, group, SessionUtils.getUser(request), fTime, SessionUtils.getSiteId(request), 1,cateIds,type);
			if(res!=null){
				result.setCode(CommonConstant.SUCCESSCODE212);
				result.setMsg("存在敏感词："+res);
			}else{
				result.setCode(CommonConstant.SUCCESSCODE);
				result.setMsg(CommonConstant.SUCCESSSTRING);
			}
			result.setOther(String.format("编辑稿件groupId=%s成功",group.getId()));
		} catch (InvalidHttpArgumentException e) {
			result.setCode(e.getCode());
			result.setMsg(e.getMsg());
		}catch(Exception e1){
			e1.printStackTrace();
			log.error("一审编辑稿件出错，"+e1.getMessage());
			result.setCode(CommonConstant.EXCEPTIONCODE);
			result.setMsg(CommonConstant.EXCEPTIONMSG+e1.getMessage());
		}
		return result;
	}
	/**
	 * 二审编辑稿件
	 * @param request
	 * @param picData 图片信息
	 * @param group 稿件信息
	 * @param fTime 拍摄时间
	 * @param type 是否是强制解锁后编辑稿件
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/editSecondGroupPic")
	@LogInfo(content="二审编辑稿件",opeType=2,logTypeCode=CommonConstant.PicGroupOperation)
	public Object editSecondGroupPic(HttpServletRequest request,String picData,CpPicGroup group,String fTime,String cateIds, Integer type){
		ResponseMessage result=new ResponseMessage();
		try {
			CommonValidation.checkParamBlank(group.getId()+"", "稿件id");
			String res=flowService.checkAndEditGroup(picData, group, SessionUtils.getUser(request), fTime, SessionUtils.getSiteId(request), 2,cateIds, type);
			
			if(res!=null){
				result.setCode(CommonConstant.SUCCESSCODE212);
				result.setMsg("存在敏感词："+res);
			}else{
				result.setCode(CommonConstant.SUCCESSCODE);
				result.setMsg(CommonConstant.SUCCESSSTRING);
			}
			result.setOther(String.format("编辑稿件groupId=%s成功",group.getId()));
		} catch (InvalidHttpArgumentException e) {
			result.setCode(e.getCode());
			result.setMsg(e.getMsg());
		}catch(Exception e1){
			e1.printStackTrace();
			log.error("二审编辑稿件出错，"+e1.getMessage());
			result.setCode(CommonConstant.EXCEPTIONCODE);
			result.setMsg(CommonConstant.EXCEPTIONMSG+e1.getMessage());
		}
		return result;
	}
	/**
	 * 三审编辑稿件
	 * @param request
	 * @param picData 图片信息
	 * @param group 稿件信息
	 * @param fTime 拍摄时间
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/editThreeGroupPic")
	@LogInfo(content="三审编辑稿件",opeType=2,logTypeCode=CommonConstant.PicGroupOperation)
	public Object editThreeGroupPic(HttpServletRequest request,String picData,CpPicGroup group,String fTime,String cateIds, Integer type){
		ResponseMessage result=new ResponseMessage();
		try {
			CommonValidation.checkParamBlank(group.getId()+"", "稿件id");
			String res=flowService.checkAndEditGroup(picData, group, SessionUtils.getUser(request), fTime, SessionUtils.getSiteId(request), 3,cateIds, type);
			if(res!=null){
				result.setCode(CommonConstant.SUCCESSCODE212);
				result.setMsg("存在敏感词："+res);
			}else{
				result.setCode(CommonConstant.SUCCESSCODE);
				result.setMsg(CommonConstant.SUCCESSSTRING);
			}
			result.setOther(String.format("编辑稿件groupId=%s成功",group.getId()));
		} catch (InvalidHttpArgumentException e) {
			result.setCode(e.getCode());
			result.setMsg(e.getMsg());
		}catch(Exception e1){
			e1.printStackTrace();
			log.error("三审编辑稿件出错，"+e1.getMessage());
			result.setCode(CommonConstant.EXCEPTIONCODE);
			result.setMsg(CommonConstant.EXCEPTIONMSG+e1.getMessage());
		}
		return result;
	}
	
	/**
	 * 在线编辑稿件:针对已发布的稿件
	 * @param request
	 * @param picData 图片信息
	 * @param group 稿件信息
	 * @param fTime 拍摄时间
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/editOnLineGroupPic")
	@LogInfo(content="在线编辑稿件",opeType=2,logTypeCode=CommonConstant.PicGroupOperation)
	public Object editOnLineGroupPic(HttpServletRequest request,String picData,CpPicGroup group,String fTime,String cateIds){
		ResponseMessage result=new ResponseMessage();
		try {
			CommonValidation.checkParamBlank(group.getId()+"", "稿件id");
			CpPicGroup oldGroup=cpPicGroupMapper.selectByPrimaryKey(group.getId());
			CpUser user= SessionUtils.getUser(request);
			//校验稿件是否正在编辑
			CommonValidation.checkGroupSaveEdit(oldGroup,group.getId(),user);
			if(oldGroup.getGroupStatus()!=4){
				result.setCode(CommonConstant.FAILURECODE);
				result.setMsg("该稿件不在已签发状态，不可进行在线编辑");
				return result;
			}
			if(StringUtil.isBlank(picData)){
				throw new InvalidHttpArgumentException(CommonConstant.FAILURECODE,CommonConstant.NOFILEERRORMSG);
			}
			Gson gson = new Gson();  
			List<CpPicture> pics = gson.fromJson(picData, new TypeToken<List<CpPicture>>(){}.getType());
			if(pics==null||pics.size()==0){
				throw new InvalidHttpArgumentException(CommonConstant.FAILURECODE,CommonConstant.NOFILEERRORMSG);
			}
			if(StringUtil.isNotBlank(fTime)){
				group.setFileTime(DateUtils.sdfLong.parse(fTime));
			}
			//编辑稿件
			String res=flowService.editGroup(oldGroup, group, pics,user,cateIds, 0);
			if(res!=null){
				result.setCode(CommonConstant.SUCCESSCODE212);
				result.setMsg("存在敏感词："+res);
			}else{
				result.setCode(CommonConstant.SUCCESSCODE);
				result.setMsg(CommonConstant.SUCCESSSTRING);
			}
			result.setOther(String.format("在线编辑稿件groupId=%s成功",group.getId()));
		} catch (InvalidHttpArgumentException e) {
			result.setCode(e.getCode());
			result.setMsg(e.getMsg());
		}catch(Exception e1){
			e1.printStackTrace();
			log.error("在线编辑稿件出错，"+e1.getMessage());
			result.setCode(CommonConstant.EXCEPTIONCODE);
			result.setMsg(CommonConstant.EXCEPTIONMSG+e1.getMessage());
		}
		return result;
	}


	/**
	 * 提交稿件：稿件已存在，用于草稿箱稿件提交
	 * @param request
	 * @param groupIds 稿件IDs
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/submitGroups")
	@LogInfo(content="提交稿件",opeType=2,logTypeCode=CommonConstant.PicGroupOperation)
	public Object submitGroups(HttpServletRequest request,String groupIds,int roleId, Integer langType){
		ResponseMessage result=new ResponseMessage();
		try {
			CommonValidation.checkParamBlank("groupIds", "稿件id");
			CommonValidation.checkParamBlank(roleId+"", "用户角色ID");
			String[] ids=groupIds.split(",");
			for (int i = 0; i <ids.length ; i++) {
				//提交稿件，并自动分配给一审
				flowService.submitGruop(SessionUtils.getSiteId(request), Integer.valueOf(ids[i]), SessionUtils.getUser(request),roleId,langType,0);
			}
			result.setCode(CommonConstant.SUCCESSCODE);
			result.setMsg(CommonConstant.SUCCESSSTRING);
			result.setOther(String.format("提交稿件groupId=%s",groupIds));
		} catch (InvalidHttpArgumentException e) {
			result.setCode(e.getCode());
			result.setMsg(e.getMsg());
		}catch(Exception e1){
			e1.printStackTrace();
			log.error("提交稿件，"+e1.getMessage());
			result.setCode(CommonConstant.EXCEPTIONCODE);
			result.setMsg(CommonConstant.EXCEPTIONMSG);
		}
		return result;
	}
	
	/**
	 * 稿件合并
	 * @param request
	 * @param groupId 主稿稿件ID
	 * @param gIds 其他稿件ids,用英文逗号隔开
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/mergeGroups")
	@LogInfo(content="稿件合并",opeType=0,logTypeCode=CommonConstant.PicGroupOperation)
	public Object mergeGroups(HttpServletRequest request,Integer groupId,String gIds){
		ResponseMessage result=new ResponseMessage();
		try {
			CommonValidation.checkParamBlank("groupIds", "稿件id");
			CommonValidation.checkParamBlank("gIds", "其他稿件id");
			String[] ids=gIds.split(",");
			flowService.mergeGroups(groupId, gIds, SessionUtils.getUser(request));
			result.setCode(CommonConstant.SUCCESSCODE);
			result.setMsg(CommonConstant.SUCCESSSTRING);
			result.setOther(String.format("合并稿件groupId=%s",groupId));
		} catch (InvalidHttpArgumentException e) {
			result.setCode(e.getCode());
			result.setMsg(e.getMsg());
		}catch(Exception e1){
			e1.printStackTrace();
			log.error("合并稿件，"+e1.getMessage());
			result.setCode(CommonConstant.EXCEPTIONCODE);
			result.setMsg(CommonConstant.EXCEPTIONMSG);
		}
		return result;
	}
	
	/**
	 * 一审提交
	 * @param request
	 * @param groupId 稿件ID
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/fristSubmitGroupPic")
	@LogInfo(content="一审提交",opeType=2,logTypeCode=CommonConstant.PicGroupOperation)
	public Object fristSubmitGroupPic(HttpServletRequest request,Integer groupId){
		ResponseMessage result=new ResponseMessage();
		try {
			CommonValidation.checkParamBlank(groupId+"", "稿件ID");
			CpPicGroup oldGroup=cpPicGroupMapper.selectByPrimaryKey(groupId);
			//校验稿件是否正在编辑
			CommonValidation.checkGroupBeginEdit(oldGroup,groupId);
			if(oldGroup.getGroupStatus()!=1){
				result.setCode(CommonConstant.FAILURECODE);
				result.setMsg("该稿件不在待一审状态，不可进行一审提交");
				return result;
			}
			CpUser user=SessionUtils.getUser(request);
			flowService.examByProofread(oldGroup,user , 1,null);
			result.setCode(CommonConstant.SUCCESSCODE);
			result.setMsg(CommonConstant.SUCCESSSTRING);
			result.setOther(String.format("一审审核提交稿件groupid=【%s】",groupId));
		} catch (InvalidHttpArgumentException e) {
			result.setCode(e.getCode());
			result.setMsg(e.getMsg());
		}catch(Exception e1){
			e1.printStackTrace();
			log.error("一审提交稿件，"+e1.getMessage());
			result.setCode(CommonConstant.EXCEPTIONCODE);
			result.setMsg(CommonConstant.EXCEPTIONMSG);
		}
		return result;
	}
	/**
	 * 二审提交
	 * @param request
	 * @param groupId 稿件ID
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/secondSubmitGroupPic")
	@LogInfo(content="二审提交",opeType=2,logTypeCode=CommonConstant.PicGroupOperation)
	public Object secondSubmitGroupPic(HttpServletRequest request,Integer groupId){
		ResponseMessage result=new ResponseMessage();
		try {
			CommonValidation.checkParamBlank(groupId+"", "稿件ID");
			CpPicGroup oldGroup=cpPicGroupMapper.selectByPrimaryKey(groupId);
			//校验稿件是否正在编辑
			CommonValidation.checkGroupBeginEdit(oldGroup,groupId);
			if(oldGroup.getGroupStatus()!=2){
				result.setCode(CommonConstant.FAILURECODE);
				result.setMsg("该稿件不在待二审状态，不可进行二审提交");
				return result;
			}
			CpUser user=SessionUtils.getUser(request);
			flowService.examByProofread(oldGroup,user , 2,null);
			result.setCode(CommonConstant.SUCCESSCODE);
			result.setMsg(CommonConstant.SUCCESSSTRING);
			result.setOther(String.format("二审审核提交稿件groupid=【%s】",groupId));
		} catch (InvalidHttpArgumentException e) {
			result.setCode(e.getCode());
			result.setMsg(e.getMsg());
		}catch(Exception e1){
			e1.printStackTrace();
			log.error("二审提交稿件，"+e1.getMessage());
			result.setCode(CommonConstant.EXCEPTIONCODE);
			result.setMsg(CommonConstant.EXCEPTIONMSG);
		}
		return result;
	}
	
	/**
	 * 三审签发
	 * @param request
	 * @param groupId 稿件ID
	 * @param cateData 签发类型多选，格式：[{type:0,signId:1,position:null},{type=0,signId:2,position:1},{type:1,topicId:2}]，
	 * 		      其中type=0为签发类型，1为专题id，signId为签发类型，position为位置，topicId=专题id
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/threeSubmitGroupPic")
	@LogInfo(content="三审签发",opeType=2,logTypeCode=CommonConstant.PicGroupOperation)
	public Object threeSubmitGroupPic(HttpServletRequest request,Integer groupId,String cateData, String signColumn,
			Integer langType, String userName){
		ResponseMessage result=new ResponseMessage();
		try {
			CommonValidation.checkParamBlank(groupId+"", "稿件ID");
			CommonValidation.checkParamBlank(cateData, "签发类型");
			CpPicGroup oldGroup=cpPicGroupMapper.selectByPrimaryKey(groupId);
			if(langType != 0){
				Gson gson = new Gson();
				List<Map<String, Integer>> cates = gson.fromJson(signColumn, new TypeToken<List<Map<String, Integer>>>() {
				}.getType());
				//校验稿件是否正在编辑
				CommonValidation.checkGroupBeginEdit(oldGroup,groupId);
				if(oldGroup.getGroupStatus()!=3){
					result.setCode(CommonConstant.FAILURECODE);
					result.setMsg("该稿件不在待三审状态，不可进行三审提交");
					return result;
				}
				for (Map<String, Integer> map : cates) {
					Integer signId = map.get("signId");
					Integer columnId = map.get("columnId");
					int i = cpPicGroupColumnMapper.getContByColumnIdAndSignId(columnId, signId, langType);// 查询columnId和columnId的这条记录是否存在0表示不存在
					if (i == 0) {
						enSignColumnService.signColumn(groupId, signId, columnId, langType);// 签发
					} else {
						enSignColumnService.updateCpPicGroupColumn(signId, columnId, groupId);// 如果这条记录存在就修改稿件的位置为0
						enSignColumnService.signColumn(groupId, signId, columnId, langType);// 签发
					}

				}
				Timestamp date = new Timestamp((new Date()).getTime());
				enSignColumnService.updateGroupStatus(groupId, date, userName);// 修改稿件的状态为4表示以签发
			}else{
				Gson gson = new Gson();  
				List<Map<String,Object>> cates = gson.fromJson(cateData, new TypeToken<List<Map<String,Object>>>(){}.getType());
				//校验稿件是否正在编辑
				CommonValidation.checkGroupBeginEdit(oldGroup,groupId);
				if(oldGroup.getGroupStatus()!=3){
					result.setCode(CommonConstant.FAILURECODE);
					result.setMsg("该稿件不在待三审状态，不可进行三审提交");
					return result;
				}
				
				CpUser user=SessionUtils.getUser(request);
				flowService.examByProofread(oldGroup,user , 3,cates);
			}
			
			result.setCode(CommonConstant.SUCCESSCODE);
			result.setMsg(CommonConstant.SUCCESSSTRING);
			result.setOther(String.format("三审审核提交稿件groupid=【%s】",groupId));
		} catch (InvalidHttpArgumentException e) {
			result.setCode(e.getCode());
			result.setMsg(e.getMsg());
		}catch(Exception e1){
			e1.printStackTrace();
			log.error("三审提交稿件，"+e1.getMessage());
			result.setCode(CommonConstant.EXCEPTIONCODE);
			result.setMsg(CommonConstant.EXCEPTIONMSG);
		}
		return result;
	}
	
	/**
	 * 补签
	 * @param request
	 * @param groupId 稿件ID
	 * @param cateData 签发类型多选，格式：[{signId:1,position:null},{signId:2,position:1}]，其中signId为签发类型，position为位置
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/signAgainGroupPic")
	@LogInfo(content="补签",opeType=2,logTypeCode=CommonConstant.PicGroupOperation)
	public Object signAgainGroupPic(HttpServletRequest request,Integer groupId,String cateData, String signColumn,
			Integer langType, String userName){
		ResponseMessage result=new ResponseMessage();
		try {
			CommonValidation.checkParamBlank(groupId+"", "稿件ID");
			CommonValidation.checkParamBlank(cateData, "签发类型");
			CpPicGroup oldGroup=cpPicGroupMapper.selectByPrimaryKey(groupId);
			CpUser user=SessionUtils.getUser(request);
			//校验稿件是否正在编辑
			CommonValidation.checkGroupBeginEdit(oldGroup,groupId);
			if(langType != 0){
				Gson gson = new Gson();
				List<Map<String, Integer>> cates = gson.fromJson(signColumn, new TypeToken<List<Map<String, Integer>>>() {
				}.getType());
				//校验稿件是否正在编辑
				CommonValidation.checkGroupBeginEdit(oldGroup,groupId);
				if(oldGroup.getGroupStatus()!=4){
					result.setCode(CommonConstant.FAILURECODE);
					result.setMsg("该稿件不在待三审状态，不可进行三审提交");
					return result;
				}
				for (Map<String, Integer> map : cates) {
					Integer signId = map.get("signId");
					Integer columnId = map.get("columnId");
					int i = cpPicGroupColumnMapper.getContByColumnIdAndSignId(columnId, signId, langType);// 查询columnId和columnId的这条记录是否存在0表示不存在
					if (i == 0) {
						enSignColumnService.signColumn(groupId, signId, columnId, langType);// 签发
					} else {
						enSignColumnService.updateCpPicGroupColumn(signId, columnId, groupId);// 如果这条记录存在就修改稿件的位置为0
						enSignColumnService.signColumn(groupId, signId, columnId, langType);// 签发
					}

				}
				enSignColumnService.updateGroupStatus(groupId, null, userName);// 修改稿件的状态为4表示以签发
				//记录流程日志
				flowService.addFlowLog(oldGroup.getId(), 4, null, null, user);
			}else{
			Gson gson = new Gson();  
			List<Map<String,Object>> cates = gson.fromJson(cateData, new TypeToken<List<Map<String,Object>>>(){}.getType());
			if(oldGroup.getGroupStatus()!=4){
				result.setCode(CommonConstant.FAILURECODE);
				result.setMsg("该稿件不在已签发状态，不可进行补签");
				return result;
			}
			flowService.signAgainGroup(oldGroup, user, cates);
			}
			result.setCode(CommonConstant.SUCCESSCODE);
			result.setMsg(CommonConstant.SUCCESSSTRING);
			result.setOther(String.format("补签稿件【id=%s】",groupId));
		} catch (InvalidHttpArgumentException e) {
			result.setCode(e.getCode());
			result.setMsg(e.getMsg());
		}catch(Exception e1){
			e1.printStackTrace();
			log.error("补签稿件，"+e1.getMessage());
			result.setCode(CommonConstant.EXCEPTIONCODE);
			result.setMsg(CommonConstant.EXCEPTIONMSG);
		}
		return result;
	}
	/**
	 * 一二三审退稿并添加评语：逐级退稿，一审退至用户被退稿件
	 * @param request
	 * @param groupId 稿件ID
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/retreatGroupPic")
	@LogInfo(content="退稿",opeType=2,logTypeCode=CommonConstant.PicGroupOperation)
	public Object retreatGroupPic(HttpServletRequest request,Integer groupId,String content){
		ResponseMessage result=new ResponseMessage();
		try {
			CommonValidation.checkParamBlank(groupId+"", "稿件ID");
			CpPicGroup oldGroup=cpPicGroupMapper.selectByPrimaryKey(groupId);
			//校验稿件是否正在编辑
			CommonValidation.checkGroupBeginEdit(oldGroup,groupId);
			CpUser user=SessionUtils.getUser(request);
			//退稿：逐级退稿，一审退至用户被退稿件
			flowService.backGroup(oldGroup, user,content);
			result.setCode(CommonConstant.SUCCESSCODE);
			result.setMsg(CommonConstant.SUCCESSSTRING);
			result.setOther(String.format("退稿稿件【id=%s】",groupId));
		} catch (InvalidHttpArgumentException e) {
			result.setCode(e.getCode());
			result.setMsg(e.getMsg());
		}catch(Exception e1){
			e1.printStackTrace();
			log.error("退稿稿件，"+e1.getMessage());
			result.setCode(CommonConstant.EXCEPTIONCODE);
			result.setMsg(CommonConstant.EXCEPTIONMSG);
		}
		return result;
	}
	/**
	 * 内部留资
	 * @param request
	 * @param groupId 稿件ID
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/keepGroupPic")
	@LogInfo(content="内部留资",opeType=2,logTypeCode=CommonConstant.PicGroupOperation)
	public Object keepGroupPic(HttpServletRequest request,Integer groupId,String info,Integer langType){
		ResponseMessage result=new ResponseMessage();
		try {
			CommonValidation.checkParamBlank(groupId+"", "稿件ID");
			CpPicGroup oldGroup=cpPicGroupMapper.selectByPrimaryKey(groupId);
			//校验稿件是否正在编辑
			CommonValidation.checkGroupBeginEdit(oldGroup,groupId);
			CpUser user=SessionUtils.getUser(request);
			//内部留资
			flowService.changeGroupStatus(groupId, user, 5,5,info,null,langType);
			result.setCode(CommonConstant.SUCCESSCODE);
			result.setMsg(CommonConstant.SUCCESSSTRING);
			result.setOther(String.format("内部留资稿件【id=%s】",groupId)); 
		} catch (InvalidHttpArgumentException e) {
			result.setCode(e.getCode());
			result.setMsg(e.getMsg());
		}catch(Exception e1){
			e1.printStackTrace();
			log.error("内部留资稿件，"+e1.getMessage());
			result.setCode(CommonConstant.EXCEPTIONCODE);
			result.setMsg(CommonConstant.EXCEPTIONMSG);
		}
		return result;
	}
	/**
	 * 资料库撤稿
	 * @param request
	 * @param groupId 稿件ID
	 * @param content 撤稿原因
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/downGroupPic")
	@LogInfo(content="撤稿",opeType=2,logTypeCode=CommonConstant.PicGroupOperation)
	public Object downGroupPic(HttpServletRequest request,Integer groupId,String content,Integer categoryId,Integer langType){
		ResponseMessage result=new ResponseMessage();
		try {
			CommonValidation.checkParamBlank(groupId+"", "稿件ID");
			CpPicGroup oldGroup=cpPicGroupMapper.selectByPrimaryKey(groupId);
			//校验稿件是否正在编辑
			CommonValidation.checkGroupBeginEdit(oldGroup,groupId);
			if(oldGroup.getGroupStatus()!=4){
				result.setCode(CommonConstant.FAILURECODE);
				result.setMsg("该稿件不在已签发状态，不可进行撤稿");
				return result;
			}
			CpUser user=SessionUtils.getUser(request);
			//撤稿
			flowService.changeGroupStatus(groupId, user, 6,6,content,categoryId, langType);
			result.setCode(CommonConstant.SUCCESSCODE);
			result.setMsg(CommonConstant.SUCCESSSTRING);
			result.setOther(String.format("撤稿稿件【id=%s】",groupId));
		} catch (InvalidHttpArgumentException e) {
			result.setCode(e.getCode());
			result.setMsg(e.getMsg());
		}catch(Exception e1){
			e1.printStackTrace();
			log.error("撤稿稿件，"+e1.getMessage());
			result.setCode(CommonConstant.EXCEPTIONCODE);
			result.setMsg(CommonConstant.EXCEPTIONMSG);
		}
		return result;
	}
	
	
	
	/**
	 * 资料库一键撤稿
	 * @param request
	 * @param groupId 稿件ID
	 * @param content 撤稿原因
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/downAllGroupPic")
	@LogInfo(content="撤稿",opeType=2,logTypeCode=CommonConstant.PicGroupOperation)
	public Object downAllGroupPic(HttpServletRequest request,Integer groupId,String content,Integer langType){
		ResponseMessage result=new ResponseMessage();
		try {
			CommonValidation.checkParamBlank(groupId+"", "稿件ID");
			CpPicGroup oldGroup=cpPicGroupMapper.selectByPrimaryKey(groupId);
			//校验稿件是否正在编辑
			CommonValidation.checkGroupBeginEdit(oldGroup,groupId);
			if(oldGroup.getGroupStatus()!=4){
				result.setCode(CommonConstant.FAILURECODE);
				result.setMsg("该稿件不在已签发状态，不可进行撤稿");
				return result;
			}
			CpUser user=SessionUtils.getUser(request);
			
			//add by xiayunan@20171009
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("type", 1);
			map.put("groupId", groupId);
			List<CpPicGroupCategory> cates=categoryMapper.selectHasSignColumn(map);
			if(cates.size()>0){
				for(int i=0;i<cates.size();i++){
					CpPicGroupCategory cpCategory = (CpPicGroupCategory)cates.get(i);
					int categoryId = cpCategory.getCategoryId();
					//撤稿
					flowService.changeGroupStatus(groupId, user, 6,6,content,categoryId, langType);
				}
			}
			result.setCode(CommonConstant.SUCCESSCODE);
			result.setMsg(CommonConstant.SUCCESSSTRING);
			result.setOther(String.format("撤稿稿件【id=%s】",groupId));
		} catch (InvalidHttpArgumentException e) {
			result.setCode(e.getCode());
			result.setMsg(e.getMsg());
		}catch(Exception e1){
			e1.printStackTrace();
			log.error("撤稿稿件，"+e1.getMessage());
			result.setCode(CommonConstant.EXCEPTIONCODE);
			result.setMsg(CommonConstant.EXCEPTIONMSG);
		}
		return result;
	}
	
	
	/**
	 * 被撤稿件上架
	 * @param request
	 * @param groupId 稿件ID
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/upOnGroupPic")
	@LogInfo(content="上架",opeType=2,logTypeCode=CommonConstant.PicGroupOperation)
	public Object upOnGroupPic(HttpServletRequest request,Integer groupId,Integer langType){
		ResponseMessage result=new ResponseMessage();
		try {
			CommonValidation.checkParamBlank(groupId+"", "稿件ID");
			CpPicGroup oldGroup=cpPicGroupMapper.selectByPrimaryKey(groupId);
			//校验稿件是否正在编辑
			CommonValidation.checkGroupBeginEdit(oldGroup,groupId);
			if(oldGroup.getGroupStatus()!=6){
				result.setCode(CommonConstant.FAILURECODE);
				result.setMsg("该稿件不在被撤稿件状态，不可进行上架");
				return result;
			}
			CpUser user=SessionUtils.getUser(request);
			//上架
			flowService.changeGroupStatus(groupId, user, 4,7,null,null,langType);
			result.setCode(CommonConstant.SUCCESSCODE);
			result.setMsg(CommonConstant.SUCCESSSTRING);
			result.setOther(String.format("上架稿件【id=%s】",groupId));
		} catch (InvalidHttpArgumentException e) {
			result.setCode(e.getCode());
			result.setMsg(e.getMsg());
		}catch(Exception e1){
			e1.printStackTrace();
			log.error("上架稿件，"+e1.getMessage());
			result.setCode(CommonConstant.EXCEPTIONCODE);
			result.setMsg(CommonConstant.EXCEPTIONMSG);
		}
		return result;
	}
	/**
	 * 推送
	 * @param request
	 * @param groupId 稿件ID
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/pushGroupPic")
	@LogInfo(content="推送",opeType=2,logTypeCode=CommonConstant.PicGroupOperation)
	public Object pushGroupPic(HttpServletRequest request,Integer groupId, String content,Integer langType){
		ResponseMessage result=new ResponseMessage();
		try {
			CommonValidation.checkParamBlank(groupId+"", "稿件ID");
			CpPicGroup oldGroup=cpPicGroupMapper.selectByPrimaryKey(groupId);
			//校验稿件是否正在编辑
			CommonValidation.checkGroupBeginEdit(oldGroup,groupId);
			if(oldGroup.getGroupStatus()!=6){
				result.setCode(CommonConstant.FAILURECODE);
				result.setMsg("该稿件不在被撤稿件状态，不可进行推送");
				return result;
			}
			CpUser user=SessionUtils.getUser(request);
			//推送:变为被退稿件
			flowService.changeGroupStatus(groupId, user, 7,9,content,null,langType);
			result.setCode(CommonConstant.SUCCESSCODE);
			result.setMsg(CommonConstant.SUCCESSSTRING);
			result.setOther(String.format("推送稿件【id=%s】",groupId));
		} catch (InvalidHttpArgumentException e) {
			result.setCode(e.getCode());
			result.setMsg(e.getMsg());
		}catch(Exception e1){
			e1.printStackTrace();
			log.error("推送稿件，"+e1.getMessage());
			result.setCode(CommonConstant.EXCEPTIONCODE);
			result.setMsg(CommonConstant.EXCEPTIONMSG);
		}
		return result;
	}
	/**
	 * 批量删除草稿箱稿件
	 * @param request
	 * @param groupIds 稿件IDs,多用英文逗号隔开
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/delDraftsGroups")
	@LogInfo(content="删除稿件",opeType=2,logTypeCode=CommonConstant.PicGroupOperation)
	public Object delDraftsGroups(HttpServletRequest request,String groupIds){
		ResponseMessage result=new ResponseMessage();
		try {
			CommonValidation.checkParamBlank(groupIds, "稿件ID");
			//批量删除稿件，并记录日志
			flowService.delDraftsGroups(groupIds, SessionUtils.getUser(request));
			result.setCode(CommonConstant.SUCCESSCODE);
			result.setMsg(CommonConstant.SUCCESSSTRING);
			result.setOther(String.format("删除稿件groupId=%s",groupIds));
		} catch (InvalidHttpArgumentException e) {
			result.setCode(e.getCode());
			result.setMsg(e.getMsg());
		}catch(Exception e1){
			e1.printStackTrace();
			log.error("删除稿件，"+e1.getMessage());
			result.setCode(CommonConstant.EXCEPTIONCODE);
			result.setMsg(CommonConstant.EXCEPTIONMSG);
		}
		return result;
	}
	/**
	 * 批量恢复稿件：将已删稿件恢复
	 * @param request
	 * @param groupIds 稿件IDs,多用英文逗号隔开
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/reNewGroups")
	@LogInfo(content="恢复稿件",opeType=2,logTypeCode=CommonConstant.PicGroupOperation)
	public Object reNewGroups(HttpServletRequest request,String groupIds){
		ResponseMessage result=new ResponseMessage();
		try {
			CommonValidation.checkParamBlank(groupIds, "稿件ID");
			//批量删除稿件，并记录日志
			flowService.reNewGroups(groupIds, SessionUtils.getUser(request));
			result.setCode(CommonConstant.SUCCESSCODE);
			result.setMsg(CommonConstant.SUCCESSSTRING);
			result.setOther(String.format("恢复稿件groupId=%s",groupIds));
		} catch (InvalidHttpArgumentException e) {
			result.setCode(e.getCode());
			result.setMsg(e.getMsg());
		}catch(Exception e1){
			e1.printStackTrace();
			log.error("恢复稿件，"+e1.getMessage());
			result.setCode(CommonConstant.EXCEPTIONCODE);
			result.setMsg(CommonConstant.EXCEPTIONMSG);
		}
		return result;
	}
	/**
	 * 批量彻底删除稿件
	 * @param request
	 * @param groupIds 稿件IDs,多用英文逗号隔开
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/delGroups")
	@LogInfo(content="彻底删除稿件",opeType=2,logTypeCode=CommonConstant.PicGroupOperation)
	public Object delGroups(HttpServletRequest request,String groupIds){
		ResponseMessage result=new ResponseMessage();
		try {
			CommonValidation.checkParamBlank(groupIds, "稿件ID");
			//批量删除稿件，并记录日志
			flowService.delGroups(groupIds, SessionUtils.getUser(request));
			result.setCode(CommonConstant.SUCCESSCODE);
			result.setMsg(CommonConstant.SUCCESSSTRING);
			result.setOther(String.format("删除稿件groupId=%s",groupIds));
		} catch (InvalidHttpArgumentException e) {
			result.setCode(e.getCode());
			result.setMsg(e.getMsg());
		}catch(Exception e1){
			e1.printStackTrace();
			log.error("删除稿件，"+e1.getMessage());
			result.setCode(CommonConstant.EXCEPTIONCODE);
			result.setMsg(CommonConstant.EXCEPTIONMSG);
		}
		return result;
	}
	/**
	 * 删除稿件并添加评语
	 * @param request
	 * @param groupId 稿件ID
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/delGroupAndMemo")
	@LogInfo(content="删除稿件",opeType=2,logTypeCode=CommonConstant.PicGroupOperation)
	public Object delGroupAndMemo(HttpServletRequest request,String groupId,String memo){
		ResponseMessage result=new ResponseMessage();
		try {
			CommonValidation.checkParamBlank(groupId+"", "稿件ID");
			CpPicGroup oldGroup=cpPicGroupMapper.selectByPrimaryKey(Integer.valueOf(groupId));
			//校验稿件是否正在编辑
			CommonValidation.checkGroupBeginEdit(oldGroup,Integer.valueOf(groupId));
			//批量删除稿件，并记录日志
			flowService.delGroup(groupId, SessionUtils.getUser(request),memo);
			result.setCode(CommonConstant.SUCCESSCODE);
			result.setMsg(CommonConstant.SUCCESSSTRING);
			result.setOther(String.format("删除稿件groupId=%s",groupId));
		} catch (InvalidHttpArgumentException e) {
			result.setCode(e.getCode());
			result.setMsg(e.getMsg());
		}catch(Exception e1){
			e1.printStackTrace();
			log.error("删除稿件，"+e1.getMessage());
			result.setCode(CommonConstant.EXCEPTIONCODE);
			result.setMsg(CommonConstant.EXCEPTIONMSG);
		}
		return result;
	}
	
	/**
	 * 内部留资取回稿件：取回后状态变为=1（待一审）
	 * @param request
	 * @param groupIds  稿件IDs,多用英文逗号隔开
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/goBackGroups")
	@LogInfo(content="取回稿件",opeType=2,logTypeCode=CommonConstant.PicGroupOperation)
	public Object goBackGroups(HttpServletRequest request,String groupIds){
		ResponseMessage result=new ResponseMessage();
		try {
			CommonValidation.checkParamBlank(groupIds+"", "稿件ID");
			/*CpPicGroup oldGroup=cpPicGroupMapper.selectByPrimaryKey(groupId);
			//校验稿件是否正在编辑
			CommonValidation.checkGroupBeginEdit(oldGroup,groupId);
			if(!(oldGroup.getGroupStatus()==5||oldGroup.getGroupStatus()==6)){
				result.setCode(CommonConstant.FAILURECODE);
				result.setMsg("该稿件不在被内部留资状态，不可进行取回");
				return result;
			}*/
			CpUser user=SessionUtils.getUser(request);
			//取回稿件：状态变为=1（待一审）
			flowService.changeGroupStatusBatch(groupIds, user, 1,14);
			result.setCode(CommonConstant.SUCCESSCODE);
			result.setMsg(CommonConstant.SUCCESSSTRING);
			result.setOther(String.format("取回稿件groupId=%s",groupIds));
		} catch (InvalidHttpArgumentException e) {
			result.setCode(e.getCode());
			result.setMsg(e.getMsg());
		}catch(Exception e1){
			e1.printStackTrace();
			log.error("取回稿件，"+e1.getMessage());
			result.setCode(CommonConstant.EXCEPTIONCODE);
			result.setMsg(CommonConstant.EXCEPTIONMSG);
		}
		return result;
	}
	
	//TODO(查询稿件)
	/**
	 * 获取我的稿件：全部、已签、待签、已删、被退
	 * @param request
	 * @param gType:获取稿件类型：0全部、1已签、2待签、3被退、4已删
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/getMyGroups")
	public Object getMyGroups(HttpServletRequest request,Integer gType,GroupQuery query){
		ResponseMessage result=new ResponseMessage();
		try {
			CpUser user=SessionUtils.getUser(request);
			gType=gType==null?0:gType;//默认为0
			/*String url=request.getRequestURL().toString();//url
			String cnsphoto=request.getContextPath();
			String path=url.substring(0, url.indexOf(cnsphoto));*/
			Map<String,Object> param=new HashMap<String, Object>();
			int siteId=SessionUtils.getSiteId(request);
			if(siteId!=1){
				param.put("siteId", siteId);
			}
			param.put("userId", user.getId());
			param.put("query", query);
			param.put("orderby", " g.APPLY_TIME desc");
			
			param.put("deleteFlag", 0);
			switch (gType) {
			case 0://全部稿件
//				param.put("deleteFlag",null);
				param.put("notStatus", 0);
				break;
			case 1://已签稿件
				param.put("statusS", "4,6");
				break;
			case 2://待签稿件
				param.put("deleteFlag", 0);
				param.put("statusS", "1,2,3,5");
				break;
			case 3://被退稿件
				param.put("status", 7);
				break;
			case 4://已删稿件
				//param.put("statusS","0,7");
				param.put("statusS","0,1,2,3,7");
				param.put("deleteFlag",1);
				break;
			default:
				break;
			}
			PageHelper.startPage(request);
			List<Map<String,Object>> list=aboutPictureMapper.selectGroupsByQuery(param);
			for (Map<String,Object> map:list) {
				if(map.containsKey("FILENAME")){
					map.put("samllPath", CommonConstant.SMALLHTTPPath+ImgFileUtils.getSamllPathByName(map.get("FILENAME").toString(),request));
				}
				if(map.containsKey("FILENAME")){
					map.put("wmPath", CommonConstant.SMALLHTTPPath+ImgFileUtils.getWMPathByName(map.get("FILENAME").toString(),request));
				}
 			}
			PageHelper.addPages(result, list);
			result.setCode(CommonConstant.SUCCESSCODE);
			result.setMsg(CommonConstant.SUCCESSSTRING);
			result.setData(list);
			PageHelper.addPagesAndTotal(result, list);
		} catch (InvalidHttpArgumentException e) {
			result.setCode(e.getCode());
			result.setMsg(e.getMsg());
		}catch(Exception e1){
			e1.printStackTrace();
			log.error("查询我的稿件，"+e1.getMessage());
			result.setCode(CommonConstant.EXCEPTIONCODE);
			result.setMsg(CommonConstant.EXCEPTIONMSG);
		}
		return result;
	}
	/**
	 * 获取我的草稿箱稿件
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/getMyDraftsGroups")
	public Object getMyDraftsGroups(HttpServletRequest request,GroupQuery query){
		ResponseMessage result=new ResponseMessage();
		try {
			CpUser user=SessionUtils.getUser(request);
			Map<String,Object> param=new HashMap<String, Object>();
			int siteId=SessionUtils.getSiteId(request);
			if(siteId!=1){
				param.put("siteId", siteId);
			}
			param.put("creatUserId", user.getId());
			
			param.put("deleteFlag", 0);
			param.put("status", 0);
			param.put("query", query);
			param.put("orderby", " g.id desc");
			PageHelper.startPage(request);
			List<Map<String,Object>> list=aboutPictureMapper.selectGroupsByQuery(param);
			for (Map<String,Object> map:list) {
				if(map.containsKey("FILENAME")){
					map.put("samllPath", CommonConstant.SMALLHTTPPath+ImgFileUtils.getSamllPathByName(map.get("FILENAME").toString(),request));
				}
			}
			PageHelper.addPages(result, list);
			result.setCode(CommonConstant.SUCCESSCODE);
			result.setMsg(CommonConstant.SUCCESSSTRING);
			result.setData(list);
			PageHelper.addPagesAndTotal(result, list);
		} catch (InvalidHttpArgumentException e) {
			result.setCode(e.getCode());
			result.setMsg(e.getMsg());
		}catch(Exception e1){
			e1.printStackTrace();
			log.error("查询我的草稿箱稿件，"+e1.getMessage());
			result.setCode(CommonConstant.EXCEPTIONCODE);
			result.setMsg(CommonConstant.EXCEPTIONMSG);
		}
		return result;
	}
	
	/**
	 * 获取资料库稿件：所有已签稿件
	 * @param request
	 * @param gType  0全部，1已发布，2已撤稿件
	 * @param query  高级检索
	 * @param signId 签发分类id
	 * @param cateId 稿件分类id
	 * @return 
	 */
	/*@ResponseBody
	@RequestMapping("/getSginGroups")
	public Object getSginGroups(HttpServletRequest request,Integer gType,GroupQuery query,Integer signId,Integer cateId, Integer langType){
		ResponseMessage result=new ResponseMessage();
		try {
			Map<String,Object> param=new HashMap<String, Object>();
			int siteId=SessionUtils.getSiteId(request);
			if(siteId!=1){
				param.put("siteId", siteId);
			}
			if(langType!=null){
				param.put("langType", langType);
			}
			param.put("deleteFlag", 0);
			if(signId!=null){
				param.put("signId", signId);
			}
			if(cateId!=null){
				param.put("cateId", cateId);
			}
			gType=gType==null?0:gType;
			switch (gType) {
			case 0://全部稿件
				param.put("statusS", "4,6");
				break;
			case 1://已发布
				param.put("status", 4);
				break;
			case 2://被撤稿件
				param.put("status", 6);
				break;
			default:
				break;
			}
			param.put("orderBy", " g.SGIN_TIME desc");
			param.put("query", query);
			PageHelper.startPage(request);
			List<Map<String,Object>> list=aboutPictureMapper.selectGroupsByQuery(param);
			for (Map<String,Object> map:list) {
				if(map.containsKey("FILENAME")){
					map.put("samllPath", CommonConstant.SMALLHTTPPath+ImgFileUtils.getSamllPathByName(map.get("FILENAME").toString(),request));
				}
			}
			PageHelper.addPages(result, list);
			result.setCode(CommonConstant.SUCCESSCODE);
			result.setMsg(CommonConstant.SUCCESSSTRING);
			result.setData(list);
			PageHelper.addPagesAndTotal(result, list);
		} catch (InvalidHttpArgumentException e) {
			result.setCode(e.getCode());
			result.setMsg(e.getMsg());
		}catch(Exception e1){
			e1.printStackTrace();
			log.error("查询资料库稿件，"+e1.getMessage());
			result.setCode(CommonConstant.EXCEPTIONCODE);
			result.setMsg(CommonConstant.EXCEPTIONMSG);
		}
		return result;
	}*/
	/**
	 * 获取资料库稿件
	 * @param request
	 * @param gType  0全部，1已发布，2已撤稿件
	 * @param query  高级检索
	 * @param signId 签发分类id
	 * @param cateId 稿件分类id
	 * @return 
	 */
	@ResponseBody
	@RequestMapping("/getSginGroup")
	public Object getSginGroup(HttpServletRequest request,GroupQuery query, Integer gType,Integer signId,Integer cateId, Integer langType, Integer properties, Integer page, Integer rows){
		ResponseMessage result=new ResponseMessage();
		try {
			Map<String,Object> param=new HashMap<String, Object>();
			int siteId=SessionUtils.getSiteId(request);
			if(siteId!=1){
				param.put("siteId", siteId);
			}
			if(langType!=null){
				param.put("langType", langType);
			}
			if(properties != null){
				param.put("properties", properties);
			}
			param.put("deleteFlag", 0);
			if(signId!=null){
				param.put("signId", signId);
			}
			if(cateId!=null){
				param.put("cateId", cateId);
			}
			gType=gType==null?0:gType;
			switch (gType) {
			case 0://全部稿件
				param.put("statusS", "4,6");
				break;
			case 1://已发布
				param.put("status", 4);
				break;
			case 2://被撤稿件
				param.put("status", 6);
				break;
			default:
				break;
			}
			Integer pageNo = (page-1)*rows;//起始条数
			Integer pageSize = page*rows;//结束条数
			param.put("pageNo", pageNo);
			param.put("pageSize", pageSize);
			param.put("orderBy", " g.SGIN_TIME desc");
			param.put("query", query);
			int count = aboutPictureMapper.selectCountGroups(param);//总条数
			//add by xiayunan@2017-09-26
			int p = (count+rows-1)/rows;//总页数
//			int p = (int)Math.ceil(count/rows);//总页数
			List<Map<String,Object>> list=aboutPictureMapper.selectGroups(param);
			for (Map<String,Object> map:list) {
				if(map.containsKey("FILENAME")){
					map.put("samllPath", CommonConstant.SMALLHTTPPath+ImgFileUtils.getSamllPathByName(map.get("FILENAME").toString(),request));
				}
				if(map.containsKey("FILENAME")){
					map.put("watermarkedmediumPath",CommonConstant.SMALLHTTPPath + ImgFileUtils.getWMPathByName(map.get("FILENAME").toString(),request)); 
				}
			}
			result.setCode(CommonConstant.SUCCESSCODE);
			result.setMsg(CommonConstant.SUCCESSSTRING);
			result.setPage(p);
			result.setOther(count);
			result.setData(list);
		} catch (InvalidHttpArgumentException e) {
			result.setCode(e.getCode());
			result.setMsg(e.getMsg());
		}catch(Exception e1){
			e1.printStackTrace();
			log.error("查询资料库稿件，"+e1.getMessage());
			result.setCode(CommonConstant.EXCEPTIONCODE);
			result.setMsg(CommonConstant.EXCEPTIONMSG);
		}
		return result;
	}
	
	
	
	
	/**
	 * 获取内部留资稿件：留资
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/getKeepGroups")
	public Object getKeepGroups(HttpServletRequest request,GroupQuery query,Integer cateId){
		ResponseMessage result=new ResponseMessage();
		try {
//			gType=gType==null?0:gType;
			Map<String,Object> param=new HashMap<String, Object>();
			int siteId=SessionUtils.getSiteId(request);
			if(siteId!=1){
				param.put("siteId", siteId);
			}
			if(cateId!=null){
				param.put("cateId", cateId);
			}
			
			param.put("deleteFlag", 0);
			param.put("status", 5);
			/*switch (gType) {
			case 0://全部稿件
				param.put("statusS", "5,6");
				break;
			case 1://留资稿件
				param.put("status", 5);
				break;
			case 2://被撤稿件
				param.put("status", 6);
				break;
			default:
				break;
			}*/
			param.put("query", query);
			PageHelper.startPage(request);
			List<Map<String,Object>> list=aboutPictureMapper.selectGroupsByQuery(param);
			for (Map<String,Object> map:list) {
				if(map.containsKey("FILENAME")){
					map.put("samllPath", CommonConstant.SMALLHTTPPath+ImgFileUtils.getSamllPathByName(map.get("FILENAME").toString(),request));
				}
				if(map.containsKey("FILENAME")){
					map.put("watermarkedmediumPath",CommonConstant.SMALLHTTPPath + ImgFileUtils.getWMPathByName(map.get("FILENAME").toString(),request)); 
				}
			}
			PageHelper.addPages(result, list);
			result.setCode(CommonConstant.SUCCESSCODE);
			result.setMsg(CommonConstant.SUCCESSSTRING);
			result.setData(list);
			PageHelper.addPagesAndTotal(result, list);
		} catch (InvalidHttpArgumentException e) {
			result.setCode(e.getCode());
			result.setMsg(e.getMsg());
		}catch(Exception e1){
			e1.printStackTrace();
			log.error("查询内部留资稿件，"+e1.getMessage());
			result.setCode(CommonConstant.EXCEPTIONCODE);
			result.setMsg(CommonConstant.EXCEPTIONMSG);
		}
		return result;
	}
	/**
	 * 获取稿件历史版本
	 * @param request
	 * @param groupId 稿件id
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/getGroupHistoryVersion")
	public Object getGroupHistoryVersion(HttpServletRequest request,Integer groupId){
		ResponseMessage result=new ResponseMessage();
		try {
			CommonValidation.checkParamBlank(groupId+"", "稿件id");
			Map<String,Object> param=new HashMap<String, Object>();
			param.put("groupId", groupId);
			List<Map<String,Object>> list=aboutPictureMapper.selectGroupHistoryVersion(param);
			for (Map<String,Object> map:list) {
				if(map.containsKey("FILENAME")){
					map.put("samllPath", CommonConstant.SMALLHTTPPath+ImgFileUtils.getSamllPathByName(map.get("FILENAME").toString(),request));
				}
				if(map.containsKey("FILENAME")){
					map.put("watermarkedmediumPath",CommonConstant.SMALLHTTPPath + ImgFileUtils.getWMPathByName(map.get("FILENAME").toString(),request)); 
				}
			}
			result.setCode(CommonConstant.SUCCESSCODE);
			result.setMsg(CommonConstant.SUCCESSSTRING);
			result.setData(list);
		} catch (InvalidHttpArgumentException e) {
			result.setCode(e.getCode());
			result.setMsg(e.getMsg());
		}catch(Exception e1){
			e1.printStackTrace();
			log.error("查询稿件历史版本，"+e1.getMessage());
			result.setCode(CommonConstant.EXCEPTIONCODE);
			result.setMsg(CommonConstant.EXCEPTIONMSG);
		}
		return result;
	}
	
	/**
	 * 获取我的值班级别:如果当天有校审配置，就看我是否在校审配置里；没有的话，就看我的校审权限；
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/getMyDuty")
	public Object getMyDuty(HttpServletRequest request, String langType){
		ResponseMessage result=new ResponseMessage();
		try { 
			CpUser user=SessionUtils.getUser(request);
			int siteId=SessionUtils.getSiteId(request);
			//获取我的值班级别：返回1、 2、 3代表我的值班级别
			List<Integer> list=flowService.getMyDuty(user, siteId, Integer.valueOf(langType));
			result.setCode(CommonConstant.SUCCESSCODE);
			result.setMsg(CommonConstant.SUCCESSSTRING);
			result.setData(list);
		} catch (InvalidHttpArgumentException e) {
			result.setCode(e.getCode());
			result.setMsg(e.getMsg());
		}catch(Exception e1){
			e1.printStackTrace();
			log.error("获取我的值班级别，"+e1.getMessage());
			result.setCode(CommonConstant.EXCEPTIONCODE);
			result.setMsg(CommonConstant.EXCEPTIONMSG);
		}
		return result;
	}
	
	/**
	 * 获取待编库稿件
	 * @param request
	 * @param gType 稿件类型：0新闻，1专题，2已删
	 * @param dtType 校审级别：1一审，2二审，3三审
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/getWaitSignGroups")
	public Object getWaitSignGroups(HttpServletRequest request,Integer gType,Integer dtType,Integer langType,GroupQuery query){
		ResponseMessage result=new ResponseMessage();
		try {
			CpUser user=SessionUtils.getUser(request);
			gType=gType==null?0:gType;
			dtType=dtType==null?1:dtType;
			Map<String,Object> param=new HashMap<String, Object>();
			int siteId=SessionUtils.getSiteId(request);
			if(siteId!=1){
				param.put("siteId", siteId);
			}
			param.put("langType", langType);
			param.put("deleteFlag", 0);
			switch (gType) {
			case 0://新闻
				param.put("properties", 0);
				break;
			case 1://专题
				param.put("properties", 1);
				break;
			case 2://已删
				param.put("deleteFlag", 1);
				break;
			default:
				break;
			}
			switch (dtType) {
			case 1://一审
//				param.put("fristPfdUser", user.getUserName());
				param.put("status", 1);
				break;
			case 2://二审
				param.put("statusS", "1,2");
				break;
			case 3://三审
				param.put("statusS", "1,2,3");
//				param.put("deleteFlag", 1);
				break;
			default:
				break;
			}
			param.put("query", query);
			PageHelper.startPage(request);
			List<Map<String,Object>> list=aboutPictureMapper.selectGroupsByQuery(param);
			for (Map<String,Object> map:list) {
				if(map.containsKey("FILENAME")){
					map.put("samllPath", CommonConstant.SMALLHTTPPath+ImgFileUtils.getSamllPathByName(map.get("FILENAME").toString(),request));
				}
				if(map.containsKey("FILENAME")){
					map.put("watermarkedmediumPath",CommonConstant.SMALLHTTPPath + ImgFileUtils.getWMPathByName(map.get("FILENAME").toString(),request)); 
				}
			}
			PageHelper.addPages(result, list);
			result.setCode(CommonConstant.SUCCESSCODE);
			result.setMsg(CommonConstant.SUCCESSSTRING);
			result.setData(list);
			PageHelper.addPagesAndTotal(result, list);
		} catch (InvalidHttpArgumentException e) {
			result.setCode(e.getCode());
			result.setMsg(e.getMsg());
		}catch(Exception e1){
			e1.printStackTrace();
			log.error("查询待编库稿件，"+e1.getMessage());
			result.setCode(CommonConstant.EXCEPTIONCODE);
			result.setMsg(CommonConstant.EXCEPTIONMSG);
		}
		return result;
	}
	
	/**
	 * 获取已签栏目
	 * @param request
	 * @param groupId 稿件id
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/getHasSignCate")
	public Object getHasSignCate(HttpServletRequest request,Integer groupId,Integer langType){
		ResponseMessage result=new ResponseMessage();
		try {
			CommonValidation.checkParamBlank(groupId+"", "稿件id");
			if(langType != 0){
				List<CpColumn> groupColumnList = columnMapper.selectByGroupId(groupId,langType);
				CpColumn column = new CpColumn();
				column.setId(0);
				column.setName("louboutin");
				column.setSignPosition(0);
				column.setType(0);
				groupColumnList.add(column);
				result.setCode(CommonConstant.SUCCESSCODE);
				result.setMsg(CommonConstant.SUCCESSSTRING);
				result.setData(groupColumnList);
			}else{
//				CpPicGroupCategoryExample e=new CpPicGroupCategoryExample();
//				e.createCriteria().andTypeEqualTo(1).andGroupIdEqualTo(groupId);
				//add by xiayunan@20171009
				Map<String,Object> map = new HashMap<String,Object>();
				map.put("type", 1);
				map.put("groupId", groupId);
				List<CpPicGroupCategory> cates=categoryMapper.selectHasSignColumn(map);
//				List<CpPicGroupCategory> cates=categoryMapper.selectByExample(e);
				result.setCode(CommonConstant.SUCCESSCODE);
				result.setMsg(CommonConstant.SUCCESSSTRING);
				result.setData(cates);
			}
						
		} catch (InvalidHttpArgumentException e) {
			result.setCode(e.getCode());
			result.setMsg(e.getMsg());
		}catch(Exception e1){
			e1.printStackTrace();
			log.error("查询已签栏目版本，"+e1.getMessage());
			result.setCode(CommonConstant.EXCEPTIONCODE);
			result.setMsg(CommonConstant.EXCEPTIONMSG);
		}
		return result;
	}
	
	/**
	 * 获取稿件操作记录
	 * @param request
	 * @param groupId 稿件id
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/getGroupLogs")
	public Object getGroupLogs(HttpServletRequest request,Integer groupId){
		ResponseMessage result=new ResponseMessage();
		try {
			CommonValidation.checkParamBlank(groupId+"", "稿件id");
			CpPicGroupProcessExample e=new CpPicGroupProcessExample();
			e.createCriteria().andPicgroupIdEqualTo(groupId);
			List<CpPicGroupProcess> logs=processMapper.selectByExample(e);
			result.setCode(CommonConstant.SUCCESSCODE);
			result.setMsg(CommonConstant.SUCCESSSTRING);
			result.setData(logs);
		} catch (InvalidHttpArgumentException e) {
			result.setCode(e.getCode());
			result.setMsg(e.getMsg());
		}catch(Exception e1){
			e1.printStackTrace();
			log.error("查询稿件操作记录，"+e1.getMessage());
			result.setCode(CommonConstant.EXCEPTIONCODE);
			result.setMsg(CommonConstant.EXCEPTIONMSG);
		}
		return result;
	}
	/**
	 * 获取稿件详情
	 * @param request
	 * @param groupId 稿件id
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/getGroupPics")
	@SkipLoginCheck
	@SkipAuthCheck
	public Object getGroupPics(HttpServletRequest request,Integer groupId){
		ResponseMessage result=new ResponseMessage();
		try {
			CommonValidation.checkParamBlank(groupId+"", "稿件id");
			CpPicGroup group= aboutPictureMapper.selectGroupPics(groupId);
			
			String res = flowService.checkSstvWordAndRes(JsonUtil.getString(group));
			if(group==null){
				throw new InvalidHttpArgumentException(CommonConstant.NULLCODE, String.format("不存在稿件Id=%s的稿子", groupId));
			}
			if(group.getPics()!=null){
				for (CpPicture pic:group.getPics()) {
					if(pic.getFilename()!=null){
						pic.setSmallPath(CommonConstant.SMALLHTTPPath+ImgFileUtils.getSamllPathByName(pic.getFilename(),request));
						pic.setWmPath(CommonConstant.SMALLHTTPPath+ImgFileUtils.getWMPathByName(pic.getFilename(),request));
					}
				}
			}
			if(group.getGroupStatus() == 5){
				CpPicGroupProcessExample e3=new CpPicGroupProcessExample();
				Criteria criteria = e3.createCriteria();
				criteria.andPicgroupIdEqualTo(groupId);
				criteria.andFlowTypeEqualTo(5);
				List<CpPicGroupProcess> logs=processMapper.selectByExample(e3);
				if (logs!=null&&logs.size()>0) { 
					group.setInnerRemark(logs.get(0).getOperateMemo());
					group.setOperateUserName(logs.get(0).getOperateUsername());
					group.setOperateTime(logs.get(0).getOperateTime());
					
				}
			}
			CpPicGroupProcessExample e=new CpPicGroupProcessExample();
			Criteria criteria = e.createCriteria();
			criteria.andPicgroupIdEqualTo(groupId);
			criteria.andFlowTypeEqualTo(8);
			List<CpPicGroupProcess> logs=processMapper.selectByExample(e);
			
			CpPicGroupProcessExample e1=new CpPicGroupProcessExample();
			Criteria criteria1 = e1.createCriteria();
			criteria1.andPicgroupIdEqualTo(groupId);
			criteria1.andFlowTypeEqualTo(9);
			List<CpPicGroupProcess> logs1=processMapper.selectByExample(e1);
			if (logs!=null&&logs.size()>0) {
				group.setDelRemark(logs.get(0).getOperateMemo());

			}
			if (logs1!=null&&logs1.size()>0) {
				group.setBackRemark(logs1.get(0).getOperateMemo());

			}
			result.setMsg(res);
			result.setCode(CommonConstant.SUCCESSCODE);
			result.setData(group);
		} catch (InvalidHttpArgumentException e) {
			result.setCode(e.getCode());
			result.setMsg(e.getMsg());
		}catch(Exception e1){
			e1.printStackTrace();
			log.error("获取稿件详情，"+e1.getMessage());
			result.setCode(CommonConstant.EXCEPTIONCODE);
			result.setMsg(CommonConstant.EXCEPTIONMSG);
		}
		return result;
	}
	
	
	/**
	 * 获取Mas基础URL
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/getMasBaseUrl")
	@SkipLoginCheck
	@SkipAuthCheck
	public Object getMasBaseUrl(HttpServletRequest request){
		ResponseMessage result=new ResponseMessage();
		try {
			//返回Mas基础URL地址 	add by xiayunan 20170907
			String masBaseUrl = sysConfigService.getDbSysConfig(SysConfigConstant.MAS_BASE_URL,1);
			Map<Object,Object> map = new HashMap<Object,Object>();
			if(StringUtil.notBlank(masBaseUrl)){
				map.put("masBaseUrl", masBaseUrl);
			}
			result.setMsg("获取Mas基础URL成功");
			result.setCode(CommonConstant.SUCCESSCODE);
			result.setData(map);
		} catch (InvalidHttpArgumentException e) {
			result.setCode(e.getCode());
			result.setMsg(e.getMsg());
		}catch(Exception e1){
			e1.printStackTrace();
			log.error("获取Mas基础URL，"+e1.getMessage());
			result.setCode(CommonConstant.EXCEPTIONCODE);
			result.setMsg(CommonConstant.EXCEPTIONMSG);
		}
		return result;
	}
	
	
	/**
	 * 查询签发类型已签稿件
	 * @param request
	 * @param signId 签发分类id：1资料图片，2最新发布，3今日头条，4每日推荐，5一周最佳采用，6娱乐风尚，7财富经济，8台湾视角，9国际风采，10限价图片，11漫画图表，12两会
	 * @param limit 查询几条记录：如10条，8条，默认5
	 * @return 
	 */
	@ResponseBody
	@RequestMapping("/getSginGroupsBySginId")
	public Object getSginGroupsBySginId(HttpServletRequest request,Integer sginId,Integer limit){
		ResponseMessage result=new ResponseMessage();
		try {
			CommonValidation.checkParamBlank(sginId+"", "签发分类id");
			Map<String,Object> param=new HashMap<String, Object>();
			param.put("sginId", sginId);
			limit=limit==null?5:limit;
			param.put("limit", limit);
			List<Map<String,Object>> list=clientPictureMapper.selectClientGroup(param);
			for (Map<String,Object> map:list) {
				if(map.containsKey("FILENAME")){
					map.put("samllPath", CommonConstant.SMALLHTTPPath+ImgFileUtils.getSamllPathByName(map.get("FILENAME").toString(),request));
				}
				if(map.containsKey("FILENAME")){
					map.put("watermarkedmediumPath",CommonConstant.SMALLHTTPPath + ImgFileUtils.getWMPathByName(map.get("FILENAME").toString(),request)); 
				}
			}
			result.setCode(CommonConstant.SUCCESSCODE);
			result.setMsg(CommonConstant.SUCCESSSTRING);
			result.setData(list);
		} catch (InvalidHttpArgumentException e) {
			result.setCode(e.getCode());
			result.setMsg(e.getMsg());
		}catch(Exception e1){
			e1.printStackTrace();
			log.error("查询签发类型已签稿件，"+e1.getMessage());
			result.setCode(CommonConstant.EXCEPTIONCODE);
			result.setMsg(CommonConstant.EXCEPTIONMSG);
		}
		return result;
	}
	
	/**
	 *  高级检索
	 * @param request
	 * @param groupId 稿件ID
	 * @param properties 0：新闻，1：专题，为空查询全部
	 * @param beginSginTime 签发时间-开始时间
	 * @param endSginTime 签发时间-结束时间
	 * @param author 稿件作者
	 * @param title 稿件标题
	 * @param people 稿件人物
	 * @param place 稿件地点
	 * @param pAuthor 图片作者
	 * @param kewords 稿件关键字
	 * @param editor 稿件编辑人
	 * @param cateId 稿件分类ID
	 * @param sginId 签发分类ID 1资料图片，2最新发布，3今日头条，4每日推荐，5一周最佳采用，6娱乐风尚，7财富经济，8台湾视角，9国际风采，10限价图片，11漫画图表，12两会
	 * @param goodsType 图片来源 ：0普通图、1活动图、2老照片、3ta图和4fa图五种
	 * @param priceType 是否为特价图：0不定价，1定价，2不出售，为空查询全部
	 * @param samllPrice 特殊定价-最小
	 * @param bigPrice 特殊定价-最大
	 * @param fileName 文件名
	 * @param isPics 0单图稿件 1多图稿件，为空查询全部
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/getGroupComplex")
	public Object getGroupComplex(HttpServletRequest request,Integer groupId,Integer properties
			,String beginSginTime,String endSginTime,String author,String title
			,String people,String place,String pAuthor,String kewords,String editor
			,Integer cateId,Integer sginId,Integer goodsType,Integer priceType, Integer gType, String remark
			,Integer samllPrice,Integer bigPrice,String fileName,Integer isPics,String memo, Integer langType){
		ResponseMessage result=new ResponseMessage();
		try {
			Map<String,Object> param=new HashMap<String, Object>();
			param.put("siteId", SessionUtils.getSiteId(request));
			if(groupId!=null){
				param.put("groupId", groupId);
			}
			if(langType!=null){
				param.put("langType", langType);
			}
			if(memo != null && StringUtil.isNotBlank(memo)){
				param.put("memo", memo);
			}
			if(remark != null && StringUtil.isNotBlank(remark)){
				param.put("remark", remark);
			}
			gType=gType==null?0:gType;
			switch (gType) {
			case 0://全部稿件
				param.put("statusS", "4,6");
				break;
			case 1://已发布
				param.put("status", 4);
				break;
			case 2://被撤稿件
				param.put("status", 6);
				break;
			default:
				break;
			}
			if(properties!=null){
				param.put("properties",properties);
			}
			if(beginSginTime!=null && StringUtil.isNotBlank(beginSginTime)){
				param.put("beginSginTime",DateUtils.getDateFromString(beginSginTime+" 00:00:00"));
			}
			if(endSginTime!=null && StringUtil.isNotBlank(endSginTime)){
				param.put("endSginTime", DateUtils.getDateFromString(endSginTime+" 23:59:59"));
			}
			if(StringUtil.isNotBlank(author)){
				param.put("author", author);
			}
			if(StringUtil.isNotBlank(title)){
				param.put("title", title);
			}
			if(StringUtil.isNotBlank(people)){
				param.put("people", people);
			}
			if(StringUtil.isNotBlank(place)){
				param.put("place", place);
			}
			if(StringUtil.isNotBlank(pAuthor)){
				param.put("pAuthor", pAuthor);
			}
			if(StringUtil.isNotBlank(kewords)){
				param.put("kewords", kewords);
			}
			if(StringUtil.isNotBlank(editor)){
				param.put("editor", editor);
			}
			if(cateId!=null){
				param.put("cateId", cateId);
			}
			if(sginId!=null){
				param.put("sginId", sginId);
			}
			if(goodsType!=null){
				param.put("goodsType", goodsType);
			}
			if(priceType!=null){
				param.put("priceType", priceType);
			}
			if(samllPrice!=null){
				param.put("samllPrice", samllPrice);
			}
			if(bigPrice!=null){
				param.put("bigPrice", bigPrice);
			}
			if(StringUtil.isNotBlank(fileName)){
				param.put("fileName", fileName);
			}
			if(isPics!=null){
				param.put("isPics", isPics);
			}
			PageHelper.startPage(request);
			List<Map<String,Object>> list=otherMapper.selectGroupComplex(param);
			for (Map<String,Object> map:list) {
				if(map.containsKey("FILENAME")){
					map.put("samllPath", CommonConstant.SMALLHTTPPath+ImgFileUtils.getSamllPathByName(map.get("FILENAME").toString(),request));
				}
				if(map.containsKey("FILENAME")){
					map.put("watermarkedmediumPath",CommonConstant.SMALLHTTPPath + ImgFileUtils.getWMPathByName(map.get("FILENAME").toString(),request)); 
				}
			}
			PageHelper.addPagesAndTotal(result, list);
			result.setCode(CommonConstant.SUCCESSCODE);
			result.setMsg(CommonConstant.SUCCESSSTRING);
			result.setData(list);
		} catch (InvalidHttpArgumentException e) {
			result.setCode(e.getCode());
			result.setMsg(e.getMsg());
		}catch(Exception e1){
			e1.printStackTrace();
			log.error("获取稿件详情，"+e1.getMessage());
			result.setCode(CommonConstant.EXCEPTIONCODE);
			result.setMsg(CommonConstant.EXCEPTIONMSG);
		}
		return result;
	}
	
	
	/**
	 * 显示签发专题
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/showQianFaTopic")
	public Object showQianFaTopic(HttpServletRequest request, HttpServletResponse response) {
		ResponseMessage result = new ResponseMessage();
		try {
			PageHelper.startPage(request);
			List<CpTopic> list = topicService.showQianFaTopic();
			for (int i = 0; i < list.size(); i++) {
				String datetime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(list.get(i).getCreateTime());
				list.get(i).setDatetime(datetime);
			}
			PageHelper.addPages(result, list);
			result.setCode(CommonConstant.SUCCESSCODE);
			result.setMsg(CommonConstant.SUCCESSSTRING);
			result.setData(list);
		} catch (Exception e1) {
			e1.printStackTrace();
			log.error("不能显示签发专题，" + e1.getMessage());
			result.setCode(CommonConstant.EXCEPTIONCODE);
			result.setMsg(CommonConstant.EXCEPTIONMSG);
		}
		return result;
	}
	/**
	 * 显示未看专题
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/showNoReadPicGroupNum")
	public Object showNoReadPicGroupNum(HttpServletRequest request, HttpServletResponse response) {
		ResponseMessage result = new ResponseMessage();
		try {
			CpUser user = SessionUtils.getUser(request);
			Map<String,Object> param=new HashMap<String, Object>();
			int siteId=SessionUtils.getSiteId(request);
			if(siteId!=1){
				param.put("siteId", siteId);
			}
			param.put("status", 1);
			param.put("deleteFlag", 0);
			param.put("fristPfdUser", user.getUserName());
//			int Num =  aboutPictureMapper.showNoReadPicGroupNum(user.getId());
//			 Map<String, Integer> map=new HashMap<>();
//			 map.put("PicGroupNum", Num);
			int Num=aboutPictureMapper.selectGroupsCountByQuery(param);
			result.setCode(CommonConstant.SUCCESSCODE);
			result.setMsg(CommonConstant.SUCCESSSTRING);
			result.setData(Num);
		} catch (Exception e1) {
			e1.printStackTrace();
			log.error("不能显示签发稿件数量，" + e1.getMessage());
			result.setCode(CommonConstant.EXCEPTIONCODE);
			result.setMsg(CommonConstant.EXCEPTIONMSG);
		}
		return result;
	}
	
	
	
	/**
	 * 稿件签报
	 * @Description: TODO <BR>
	 * @author liu.jinfeng
	 * @date 2017年9月4日 上午10:36:31
	 * @param request
	 * @param response
	 * @param groupId 稿件ID
	 * @return
	 */
    @ResponseBody
    @RequestMapping("/signPic")
    public Object signPic(HttpServletRequest request,
            HttpServletResponse response, Integer groupId,Integer type) {
        ResponseMessage result = new ResponseMessage();
        CpUser user = SessionUtils.getUser(request);
        CommonValidation.checkParamBlank(groupId + "", "稿件id");
        CommonValidation.checkParamBlank(type + "", "签报类型");
        
        try {
            CpPicGroup group = aboutPictureMapper.selectGroupPics(groupId);
            if(group.getQbStatus()==1){
                log.error("不能签报");
                result.setCode(CommonConstant.EXCEPTIONCODE);
                result.setMsg("已经签报过不能再次签报");
                return result;
            }
            
            List<CpPicture> list = group.getPics();//pictureService.selectByGroupId(groupId);
            String sQbPath = ImageConfig.getQbPath(1, sysConfigService);
            for (CpPicture pic : list) {
                String fileName = pic.getFilename();
                String filePath = ImageConfig.getFilePath(fileName, "B", 1,
                        sysConfigService);
                // 判断附件是否存在
                if (ImgFileUtils.isFileExist(filePath)) {
                    // 目录不存在则新建
                    if (!ImgFileUtils.isFileExist(sQbPath)) {
                        File file = new File(sQbPath);
                        file.mkdirs();
                    }
                    // 复制图片到签报地址
                    ImgFileUtils.copyFile(filePath,
                            sQbPath + File.separator + fileName);

                    //写入xml文件
                    Document doc = XMLUtils.createDoc(group,pic,type,user);
                    XMLUtils.writeXML(doc, sQbPath+fileName.substring(0, fileName.lastIndexOf("."))+".xml");
                }
            }
            
            // 签过了更新状态
            cpPicGroupMapper.updateByGroupId(groupId);
            
            //记录流程日志
            flowService.addFlowLog(groupId, 19, "签报", null, user);
            
            result.setCode(CommonConstant.SUCCESSCODE);
            result.setMsg(CommonConstant.SUCCESSSTRING);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("不能签报，" + e.getMessage());
            result.setCode(CommonConstant.EXCEPTIONCODE);
            result.setMsg(CommonConstant.EXCEPTIONMSG);
        }
        
        return result;

    }
    
    
    @ResponseBody
    @RequestMapping("/getGroupPicsInDeal")
    public Object getGroupPicsInDeal(HttpServletRequest request,
            HttpServletResponse response,GroupQuery query) {
        ResponseMessage result = new ResponseMessage();
        CpUser user = SessionUtils.getUser(request);
        try {
            // 从操作记录中获取稿件
            CpPicGroupProcessExample e = new CpPicGroupProcessExample();
            List<Integer> types = new ArrayList<Integer>();
            types.add(0);// 发稿提交
            types.add(1);// 一审提交
            types.add(2);// 二审提交
            types.add(3);// 三审签发

            Criteria c = e.createCriteria();
            c.andFlowTypeIn(types);
            c.andOperateUserIdEqualTo(user.getId());
            List<CpPicGroupProcess> logs = processMapper.selectByExample(e);

            String sGroupIds = ",";
//            List<Integer> groupIds = new ArrayList<Integer>();
            for (CpPicGroupProcess picLog : logs) {
                int nGroupId = picLog.getPicgroupId();
                if (sGroupIds.indexOf("," + nGroupId + ",") < 0) {
                    sGroupIds += nGroupId + ",";
                }
//                if (!groupIds.contains(nGroupId)) {
//                    groupIds.add(nGroupId);
//                }
            }

            List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
            if (sGroupIds.length() > 1) {
                sGroupIds = sGroupIds.substring(1, sGroupIds.length() - 1);
                Map<String, Object> param = new HashMap<String, Object>();
                param.put("groupIds", sGroupIds);
                param.put("query", query);
                PageHelper.startPage(request);
                list = aboutPictureMapper.selectGroupsByQuery(param);
                for (Map<String, Object> map : list) {
                    if (map.containsKey("FILENAME")) {
                        map.put("samllPath",CommonConstant.SMALLHTTPPath + ImgFileUtils.getSamllPathByName(map.get("FILENAME").toString(),request));
                    }
                    if (map.containsKey("FILENAME")) {
                        map.put("wmPath",CommonConstant.SMALLHTTPPath + ImgFileUtils.getWMPathByName(map.get("FILENAME").toString(),request));
                    }
                }
                PageHelper.addPages(result, list);
                result.setData(list);
                PageHelper.addPagesAndTotal(result, list);
            }
            result.setCode(CommonConstant.SUCCESSCODE);
            result.setMsg(CommonConstant.SUCCESSSTRING);
        } catch (InvalidHttpArgumentException e) {
            result.setCode(e.getCode());
            result.setMsg(e.getMsg());
        } catch (Exception e1) {
            e1.printStackTrace();
            log.error("查询稿件操作记录，" + e1.getMessage());
            result.setCode(CommonConstant.EXCEPTIONCODE);
            result.setMsg(CommonConstant.EXCEPTIONMSG);
        }
        return result;
    }

}
