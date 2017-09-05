package com.deepai.photo.controller.needs;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.deepai.photo.bean.CpNeeds;
import com.deepai.photo.bean.CpNeedsContact;
import com.deepai.photo.bean.CpUser;
import com.deepai.photo.common.StringUtil;
import com.deepai.photo.common.annotation.LogInfo;
import com.deepai.photo.common.annotation.SkipAuthCheck;
import com.deepai.photo.common.constant.CommonConstant;
import com.deepai.photo.common.pagehelper.PageHelper;
import com.deepai.photo.common.pojo.ResponseMessage;
import com.deepai.photo.common.util.SessionUtils;
import com.deepai.photo.common.util.date.DateTimeUtil;
import com.deepai.photo.service.admin.UserRoleRightService;
import com.deepai.photo.service.needs.NeedService;

/**
 * * @author huqiankai: * *
 */
@Controller
@RequestMapping("/needs")
public class NeedsController {
	private Logger log = Logger.getLogger(NeedsController.class);
	@Autowired
	private NeedService NeedsService;
	@Autowired
	private UserRoleRightService userRoleRightService;
	/**
	 * 显示全部需求
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/show")
	public Object show(HttpServletRequest request, HttpServletResponse response,String strWhere,Integer roleId,Integer langType) {
		ResponseMessage result = new ResponseMessage();
		try {
//			CpUser user = SessionUtils.getUser(request);
//			List<Integer> RoseId=userRoleRightService.getRoseId(user.getId());
			PageHelper.startPage(request);
			List<CpNeeds> list = NeedsService.showtoadmin(roleId,strWhere,langType);
			for (int i = 0; i < list.size(); i++) {
				SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				if(list.get(i).getPtime() != null){
					String datetime = dateFormat.format(list.get(i).getPtime());
					list.get(i).setDatetime(datetime);
				}else{
					list.get(i).setDatetime("");
				}
				if(list.get(i).getFtime() != null){
					String Ftimetime = dateFormat.format(list.get(i).getFtime());
					list.get(i).setFalsetime(Ftimetime);
				}else{
					list.get(i).setFalsetime("");
				}
				Date userendtime = list.get(i).getUserstartime();
				Date userendtime2 = list.get(i).getUserendtime();
				if (userendtime!=null&&userendtime2!=null) {
					long day = (userendtime2.getTime()-userendtime.getTime()) / (1000 * 24 * 3600);
					list.get(i).setDay(day);
				}
			}
			PageHelper.addPages(result, list);
			result.setCode(CommonConstant.SUCCESSCODE);
			result.setMsg(CommonConstant.SUCCESSSTRING);
			result.setData(list);
			PageHelper.addPagesAndTotal(result, list);
		} catch (Exception e1) {
			e1.printStackTrace();
			log.error("不能显示全部需求，" + e1.getMessage());
			result.setCode(CommonConstant.EXCEPTIONCODE);
			result.setMsg(CommonConstant.EXCEPTIONMSG);
		}
		return result;
	}
	/**
	 * 需求的高级查询
	 * 
	 * @param request
	 * @param response
	 * @param descs  需求内容
	 * @param userName 用户
	 * @param status 状态
	 * @param pStartTime 发布时间
	 * @param pEndTime 结束发布时间 
	 * @param roleId 用户角色
	 * @param pictureUse  图片用途
	 * @param langType 中英文
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/advancedSearch")
	public Object advancedSearch(HttpServletRequest request, HttpServletResponse response,
			String descs,String userName,String status,String pStartTime,String pEndTime,Integer roleId,String pictureUse,String langType){
		
		ResponseMessage result = new ResponseMessage();
		try {
			Map<String,Object> param = new HashMap<String,Object>();
			if(StringUtil.isNotBlank(descs)){
				param.put("descs", descs);
			}
			if(StringUtil.isNotBlank(userName)){
				param.put("userName", userName);
			}
			if(StringUtil.isNotBlank(status)){
				param.put("status", Integer.parseInt(status));
			}
			if(StringUtil.isNotBlank(pictureUse)){
				param.put("pictureUse", pictureUse);
			}
			if(StringUtil.isNotBlank(langType)){
				param.put("langType", Integer.parseInt(langType));
			}
//			if(StringUtil.isNotBlank(useStartTime)){
//				Date useStartTimeTemp = DateTimeUtil.convertAsDateString(useStartTime);
//				param.put("useStartTime", useStartTimeTemp);
//			}
//			if(StringUtil.isNotBlank(useEndTime)){
//				Date useEndTimeTemp = DateTimeUtil.convertAsDateString(useEndTime);
//				param.put("useEndTime", useEndTimeTemp);
//			}
			if(StringUtil.isNotBlank(pStartTime)){
				Date pStartTimeTemp = DateTimeUtil.convertAsDateString(pStartTime);
				param.put("pStartTime", pStartTimeTemp);
			}
			if(StringUtil.isNotBlank(pEndTime)){
				Date pEndTimeTemp = DateTimeUtil.convertAsDateString(pEndTime);
				param.put("pEndTime", pEndTimeTemp);
			}
			PageHelper.startPage(request);
			List<CpNeeds> list = NeedsService.advancedSearch(roleId, param);
			for (int i = 0; i < list.size(); i++) {
				String datetime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(list.get(i).getPtime());
				list.get(i).setDatetime(datetime);
				Date userendtime = list.get(i).getUserendtime();
				Date userendtime2 = list.get(i).getUserendtime();
				long day = (userendtime.getTime() - userendtime2.getTime()) / (1000 * 24 * 3600);
				list.get(i).setDay(day);
			}
			PageHelper.addPages(result, list);
			result.setCode(CommonConstant.SUCCESSCODE);
			result.setMsg(CommonConstant.SUCCESSSTRING);
			result.setData(list);
			PageHelper.addPagesAndTotal(result, list);
		} catch (Exception e) {
			e.printStackTrace();
			log.error("不能高级检索需求，" + e.getMessage());
			result.setCode(CommonConstant.EXCEPTIONCODE);
			result.setMsg(CommonConstant.EXCEPTIONMSG);
		}
		return result;
	}

	/**
	 * 多条件查询需求
	 * @param request
	 * @param response
	 * @param desc
	 * @param username
	 * @param status
	 * @param Timefrom
	 * @param Timeto
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/search")
	public Object search(HttpServletRequest request, HttpServletResponse response,String someThing,Integer roleId,Integer langType) {
		ResponseMessage result = new ResponseMessage();
		try {
			/*CpUser user = SessionUtils.getUser(request);
			List<Integer> RoseIds=userRoleRightService.getRoseId(user.getId());*/
			PageHelper.startPage(request);
			List<CpNeeds> list = NeedsService.search(roleId, someThing, langType);
			for (int i = 0; i < list.size(); i++) {
				String datetime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(list.get(i).getPtime());
				list.get(i).setDatetime(datetime);
				Date userendtime = list.get(i).getUserendtime();
				Date userendtime2 = list.get(i).getUserendtime();
				long day = (userendtime.getTime() - userendtime2.getTime()) / (1000 * 24 * 3600);
				list.get(i).setDay(day);
			}
			
			PageHelper.addPages(result, list);
			result.setCode(CommonConstant.SUCCESSCODE);
			result.setMsg(CommonConstant.SUCCESSSTRING);
			result.setData(list);
			PageHelper.addPagesAndTotal(result, list);
		} catch (Exception e1) {
			e1.printStackTrace();
			log.error("不能多条件查询需求，" + e1.getMessage());
			result.setCode(CommonConstant.EXCEPTIONCODE);
			result.setMsg(CommonConstant.EXCEPTIONMSG);
		}
		return result;
	}


	/**
	 * 需求详情
	 * @param request
	 * @param response
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/detail")
	public Object detail(HttpServletRequest request, HttpServletResponse response, Integer id) {

		ResponseMessage result = new ResponseMessage();
		try {
			PageHelper.startPage(request);
			CpNeeds cpNeeds = NeedsService.detail(id);
			String datetime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(cpNeeds.getPtime());
			
			String endtime = new SimpleDateFormat("yyyy-MM-dd").format(cpNeeds.getUserendtime());
			String starttime = new SimpleDateFormat("yyyy-MM-dd").format(cpNeeds.getUserstartime());
			if(cpNeeds.getFtime() != null){
				String ftime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(cpNeeds.getFtime());
				cpNeeds.setFalsetime(ftime);
			}
			cpNeeds.setDatetime(datetime);
			cpNeeds.setStarttime(starttime);
			cpNeeds.setEndtime(endtime);
			result.setCode(CommonConstant.SUCCESSCODE);
			result.setMsg(CommonConstant.SUCCESSSTRING);
			result.setData(cpNeeds);
		} catch (Exception e1) {
			e1.printStackTrace();
			log.error("需求详情打开异常， " + e1.getMessage());
			result.setCode(CommonConstant.EXCEPTIONCODE);
			result.setMsg(CommonConstant.EXCEPTIONMSG);
		}
		return result;
	}

	/**
	 * 删除需求   
	 * @param request
	 * @param response
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/delete")
	@LogInfo(content = "删除需求", opeType = 1, logTypeCode = CommonConstant.Needs)
	public Object delete(HttpServletRequest request, HttpServletResponse response, Integer id) {
		ResponseMessage result = new ResponseMessage();
		try {
			NeedsService.delete(id);
			result.setCode(CommonConstant.SUCCESSCODE);
			result.setMsg(CommonConstant.SUCCESSSTRING);
		} catch (Exception e1) {
			e1.printStackTrace();
			log.error("删除需求失败， " + e1.getMessage());
			result.setCode(CommonConstant.EXCEPTIONCODE);
			result.setMsg(CommonConstant.EXCEPTIONMSG);
		}
		return result;
	}
	/**
	 * 关闭需求
	 * @param request
	 * @param response
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/closeneeds")
	@LogInfo(content = "关闭需求", opeType = 2, logTypeCode = CommonConstant.Needs)
	public Object closeneeds(HttpServletRequest request, HttpServletResponse response, Integer id) {
		ResponseMessage result = new ResponseMessage();
		try {
			//关闭需求接口
			CpNeeds cpNeeds = NeedsService.detail(id);
			cpNeeds.setStatus(4);
			cpNeeds.setFtime(new Date());
			NeedsService.changeStatu(cpNeeds);
			result.setCode(CommonConstant.SUCCESSCODE);
			result.setMsg(CommonConstant.SUCCESSSTRING);
		} catch (Exception e1) {
			e1.printStackTrace();
			log.error("关闭需求失败， " + e1.getMessage());
			result.setCode(CommonConstant.EXCEPTIONCODE);
			result.setMsg(CommonConstant.EXCEPTIONMSG);
		}
		return result;
	}
	
	/**
	 * 审核需求
	 * @param request
	 * @param response
	 * @param cpNeeds
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/changeStatu")
	@LogInfo(content = "审核需求", opeType = 2, logTypeCode = CommonConstant.Needs, memo = "0是未审核，1是审核通过，2是审核未通过，3保存文档，4审核通过需求关闭")
	public Object changeStatu(HttpServletRequest request, HttpServletResponse response, CpNeeds cpNeeds) {
		ResponseMessage result = new ResponseMessage();
		try {
			NeedsService.changeStatu(cpNeeds);
			result.setCode(CommonConstant.SUCCESSCODE);
			result.setMsg(CommonConstant.SUCCESSSTRING);
		} catch (Exception e1) {
			e1.printStackTrace();
			log.error("审核需求失败， " + e1.getMessage());
			result.setCode(CommonConstant.EXCEPTIONCODE);
			result.setMsg(CommonConstant.EXCEPTIONMSG);
		}
		return result;
	}
	/**
	 * 添加需求
	 * @param request
	 * @param response
	 * @param cpNeeds
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/add")
	@SkipAuthCheck
	@LogInfo(content="添加需求",opeType=0,logTypeCode=CommonConstant.Needs)
	public Object add(HttpServletRequest request,HttpServletResponse response,CpNeeds cpNeeds) {
		ResponseMessage result=new ResponseMessage();
		try {
			CpUser user = SessionUtils.getUser(request);
			cpNeeds.setUsername(user.getUserName());
			if (cpNeeds.getStarttime()!=null) {
				Date Starttime = DateTimeUtil.convertAsDateString(cpNeeds.getStarttime());
				cpNeeds.setUserstartime(Starttime);
			}
			if (cpNeeds.getEndtime()!=null) {
				Date Endtime = DateTimeUtil.convertAsDateString(cpNeeds.getEndtime());
				cpNeeds.setUserendtime(Endtime);
			}
			cpNeeds.setSiteId(SessionUtils.getSiteId(request));
			NeedsService.add(cpNeeds);
			result.setCode(CommonConstant.SUCCESSCODE);
			result.setMsg(CommonConstant.SUCCESSSTRING);	
		} catch (Exception e1) {
			e1.printStackTrace();
			log.error("添加需求失败， "+e1.getMessage());
			result.setCode(CommonConstant.EXCEPTIONCODE);
			result.setMsg(CommonConstant.EXCEPTIONMSG);
		}
		return result;
	}
	@ResponseBody
	@RequestMapping("/tigong")
	@SkipAuthCheck
	@LogInfo(content="提供照片",opeType=0,logTypeCode=CommonConstant.Needs)
	public Object tigong(HttpServletRequest request,HttpServletResponse response,CpNeedsContact cpNeedsContact) {
		ResponseMessage result=new ResponseMessage();
		try {
			NeedsService.tigong(cpNeedsContact);
			result.setCode(CommonConstant.SUCCESSCODE);
			result.setMsg(CommonConstant.SUCCESSSTRING);	
		} catch (Exception e1) {
			e1.printStackTrace();
			log.error("添加需求失败， "+e1.getMessage());
			result.setCode(CommonConstant.EXCEPTIONCODE);
			result.setMsg(CommonConstant.EXCEPTIONMSG);
		}
		return result;
	}
	
	@ResponseBody
	@RequestMapping("/edit")
	@LogInfo(content = "保存审核需求", opeType = 2, logTypeCode = CommonConstant.Other, memo = "0是未审核，1是审核通过，2是审核未通过，3保存文档，4审核通过需求关闭")
	public Object edit(HttpServletRequest request, HttpServletResponse response, CpNeeds cpNeeds) {
		ResponseMessage result = new ResponseMessage();
		try {
			if (StringUtils.isNotBlank(cpNeeds.getDatetime())) {
				Date Datetime = DateTimeUtil.convertAsDateString(cpNeeds.getDatetime());
				cpNeeds.setFtime(Datetime);
			}
			if (StringUtils.isNotBlank(cpNeeds.getFalsetime())) {
				Date Falsetime = DateTimeUtil.convertAsDateString(cpNeeds.getFalsetime());
				cpNeeds.setFtime(Falsetime);
			}
			if (StringUtils.isNotBlank(cpNeeds.getStarttime())) {
				Date Starttime = DateTimeUtil.convertAsDateString(cpNeeds.getStarttime());
				cpNeeds.setUserstartime(Starttime);
			}
			if (StringUtils.isNotBlank(cpNeeds.getEndtime())) {
				Date Endtime = DateTimeUtil.convertAsDateString(cpNeeds.getEndtime());
				cpNeeds.setUserendtime(Endtime);
			}
			NeedsService.edit(cpNeeds);
			result.setCode(CommonConstant.SUCCESSCODE);
			result.setMsg(CommonConstant.SUCCESSSTRING);
		} catch (Exception e1) {
			e1.printStackTrace();
			log.error("保存审核需求失败， " + e1.getMessage());
			result.setCode(CommonConstant.EXCEPTIONCODE);
			result.setMsg(CommonConstant.EXCEPTIONMSG);
		}
		return result;
	}
	/**
	 * 前台页面展示需求
	 * @param request
	 * @param response
	 * @param strWhere
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/showNeedToClient")
	@SkipAuthCheck
	public Object showToFrontPage(HttpServletRequest request, HttpServletResponse response,Integer langType) {
		ResponseMessage result = new ResponseMessage();
		try {
			PageHelper.startPage(request);
			String name =SessionUtils.getUser(request).getUserName();
			List<CpNeeds> list = NeedsService.showToFrontPage(SessionUtils.getSiteId(request),SessionUtils.getUser(request).getUserName(),langType);
			for (int i = 0; i < list.size(); i++) {
				SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				String datetime = dateFormat.format(list.get(i).getPtime());
				if(list.get(i).getFtime() != null){
					String Ftimetime = dateFormat.format(list.get(i).getFtime());
					list.get(i).setFalsetime(Ftimetime);
				}
				list.get(i).setDatetime(datetime);
				Date userendtime = list.get(i).getUserstartime();
				Date userendtime2 = list.get(i).getUserendtime();
				if (userendtime!=null&&userendtime2!=null) {
					long day = (userendtime2.getTime()-userendtime.getTime()) / (1000 * 24 * 3600);
					list.get(i).setDay(day);
				}
			}
			PageHelper.addPages(result, list);
			result.setCode(CommonConstant.SUCCESSCODE);
			result.setMsg(CommonConstant.SUCCESSSTRING);
			result.setData(list);
			PageHelper.addPagesAndTotal(result, list);
		} catch (Exception e1) {
			e1.printStackTrace();
			log.error("不能显示全部需求，" + e1.getMessage());
			result.setCode(CommonConstant.EXCEPTIONCODE);
			result.setMsg(CommonConstant.EXCEPTIONMSG);
		}
		return result;
	}
	@ResponseBody
	@RequestMapping("/searchForDutyEditor")
	public Object searchForDutyEditor(HttpServletRequest request, HttpServletResponse response,String desc,String username
			,String Timefrom ,String Timeto, Integer langType) {
		ResponseMessage result = new ResponseMessage();
		try {
			PageHelper.startPage(request);
			List<CpNeeds> list = NeedsService.searchForDutyEditor(desc,username,Timefrom,Timeto,langType);
			for (int i = 0; i < list.size(); i++) {
				String datetime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(list.get(i).getPtime());
				list.get(i).setDatetime(datetime);
				Date userendtime = list.get(i).getUserendtime();
				Date userendtime2 = list.get(i).getUserendtime();
				long day = (userendtime.getTime() - userendtime2.getTime()) / (1000 * 24 * 3600);
				list.get(i).setDay(day);
			}
			
			PageHelper.addPages(result, list);
			result.setCode(CommonConstant.SUCCESSCODE);
			result.setMsg(CommonConstant.SUCCESSSTRING);
			result.setData(list);
		} catch (Exception e1) {
			e1.printStackTrace();
			log.error("不能多条件查询需求，" + e1.getMessage());
			result.setCode(CommonConstant.EXCEPTIONCODE);
			result.setMsg(CommonConstant.EXCEPTIONMSG);
		}
		return result;
	}

}
