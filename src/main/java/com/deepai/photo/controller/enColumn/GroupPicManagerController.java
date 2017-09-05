package com.deepai.photo.controller.enColumn;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.deepai.photo.bean.CpPicGroup;
import com.deepai.photo.bean.CpPicture;
import com.deepai.photo.bean.CpUser;
import com.deepai.photo.bean.GroupQuery;
import com.deepai.photo.common.StringUtil;
import com.deepai.photo.common.annotation.LogInfo;
import com.deepai.photo.common.constant.CommonConstant;
import com.deepai.photo.common.exception.InvalidHttpArgumentException;
import com.deepai.photo.common.pagehelper.PageHelper;
import com.deepai.photo.common.pojo.ResponseMessage;
import com.deepai.photo.common.redis.RedisClientTemplate;
import com.deepai.photo.common.util.SessionUtils;
import com.deepai.photo.common.util.date.DateUtils;
import com.deepai.photo.common.util.image.ImgFileUtils;
import com.deepai.photo.common.validation.CommonValidation;
import com.deepai.photo.controller.picture.GroupPicController;
import com.deepai.photo.mapper.AboutPictureMapper;
import com.deepai.photo.mapper.ClientPictureMapper;
import com.deepai.photo.mapper.CpPicGroupCategoryMapper;
import com.deepai.photo.mapper.CpPicGroupMapper;
import com.deepai.photo.mapper.CpPicGroupProcessMapper;
import com.deepai.photo.mapper.OtherMapper;
import com.deepai.photo.service.admin.SysConfigService;
import com.deepai.photo.service.picture.FlowService;
import com.deepai.photo.service.picture.PictureService;
import com.deepai.photo.service.topic.TopicService;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

@Controller
@RequestMapping("/enGroupPicCtro")
public class GroupPicManagerController {
	
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
//					param.put("deleteFlag",null);
					param.put("notStatus", 0);
					break;
				case 1://已签稿件
					param.put("statusS", "4,6");
					break;
				case 2://待签稿件
					param.put("deleteFlag", null);
					param.put("statusS", "1,2,3,5");
					break;
				case 3://被退稿件
					param.put("status", 7);
					break;
				case 4://已删稿件
					param.put("statusS","0,7");
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
		 * 提交稿件：稿件已存在，用于草稿箱稿件提交
		 * @param request
		 * @param groupIds 稿件IDs
		 * @return
		 */
//		@ResponseBody
//		@RequestMapping("/submitGroups")
//		@LogInfo(content="提交稿件",opeType=2,logTypeCode=CommonConstant.PicGroupOperation)
//		public Object submitGroups(HttpServletRequest request,String groupIds,int roleId){
//			ResponseMessage result=new ResponseMessage();
//			try {
//				CommonValidation.checkParamBlank("groupIds", "稿件id");
//				CommonValidation.checkParamBlank(roleId+"", "用户角色ID");
//				String[] ids=groupIds.split(",");
//				for (int i = 0; i <ids.length ; i++) {
//					//提交稿件，并自动分配给一审
//					flowService.submitGruop(SessionUtils.getSiteId(request), Integer.valueOf(ids[i]), SessionUtils.getUser(request),roleId);
//				}
//				result.setCode(CommonConstant.SUCCESSCODE);
//				result.setMsg(CommonConstant.SUCCESSSTRING);
//				result.setOther(String.format("提交稿件groupId=%s",groupIds));
//			} catch (InvalidHttpArgumentException e) {
//				result.setCode(e.getCode());
//				result.setMsg(e.getMsg());
//			}catch(Exception e1){
//				e1.printStackTrace();
//				log.error("提交稿件，"+e1.getMessage());
//				result.setCode(CommonConstant.EXCEPTIONCODE);
//				result.setMsg(CommonConstant.EXCEPTIONMSG);
//			}
//			return result;
//		}
}
