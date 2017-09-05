package com.deepai.photo.controller.webinfo;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.deepai.photo.bean.CpAdvPosition;
import com.deepai.photo.bean.CpNotice;
import com.deepai.photo.bean.CpPicturePrice;
import com.deepai.photo.bean.CpSite;
import com.deepai.photo.bean.CpUser;
import com.deepai.photo.bean.CpWebsiteInfo;
import com.deepai.photo.common.annotation.LogInfo;
import com.deepai.photo.common.annotation.SkipAuthCheck;
import com.deepai.photo.common.annotation.SkipLoginCheck;
import com.deepai.photo.common.constant.CommonConstant;
import com.deepai.photo.common.pagehelper.PageHelper;
import com.deepai.photo.common.pojo.ResponseMessage;
import com.deepai.photo.common.util.SessionUtils;
import com.deepai.photo.controller.admin.RightController;
import com.deepai.photo.service.websiteInfo.CpWebsiteInfoService;

/**
 * * @author huqiankai: *
 */
@Controller
@RequestMapping("/webmsg")
public class WebmsgController {
	private Logger log = Logger.getLogger(WebmsgController.class);
	@Autowired
	private CpWebsiteInfoService cpWebsiteInfoService;

	/**
	 * 显示网站信息
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/show")
	public Object show(HttpServletRequest request, HttpServletResponse response,Integer langType) {
		ResponseMessage result = new ResponseMessage();
		try {
			PageHelper.startPage(request);
			String strWhere = request.getParameter("strWhere");
			List<CpWebsiteInfo> list = cpWebsiteInfoService.show(strWhere,langType);
			for (int i = 0; i < list.size(); i++) {
				String datetime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(list.get(i).getLastUpdateTime());
				list.get(i).setDatetime(datetime);
			}
			PageHelper.addPages(result, list);
			result.setCode(CommonConstant.SUCCESSCODE);
			result.setMsg(CommonConstant.SUCCESSSTRING);
			result.setData(list);
			PageHelper.addPagesAndTotal(result, list);
		} catch (Exception e1) {
			e1.printStackTrace();
			log.error("不能显示网站信息，" + e1.getMessage());
			result.setCode(CommonConstant.EXCEPTIONCODE);
			result.setMsg(CommonConstant.EXCEPTIONMSG);
		}
		return result;
	}

	/**
	 * 添加网站信息
	 * @param request
	 * @param response
	 * @param cpWebsiteInfo
	 * @param container
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/add")
	@LogInfo(content = "添加网站信息", opeType = 0, logTypeCode = CommonConstant.Other)
	public Object add(HttpServletRequest request, HttpServletResponse response, CpWebsiteInfo cpWebsiteInfo,
			String container ) {
		ResponseMessage result = new ResponseMessage();
		try {
			CpUser user = SessionUtils.getUser(request);
			cpWebsiteInfo.setSiteId(SessionUtils.getSiteId(request));
			cpWebsiteInfo.setLastUpdateUser(user.getUserName());
			cpWebsiteInfo.setLangType(SessionUtils.geLangType(request));
			cpWebsiteInfoService.add(cpWebsiteInfo);
			result.setCode(CommonConstant.SUCCESSCODE);
			result.setMsg(CommonConstant.SUCCESSSTRING);
		} catch (Exception e1) {
			e1.printStackTrace();
			log.error("不能添加网站信息， " + e1.getMessage());
			result.setCode(CommonConstant.EXCEPTIONCODE);
			result.setMsg(CommonConstant.EXCEPTIONMSG);
		}
		return result;
	}

	/**
	 * 显示网站信息详情
	 * @param request
	 * @param response
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/showtoedit")
	@SkipLoginCheck
	@SkipAuthCheck
	public Object showtoedit(HttpServletRequest request, HttpServletResponse response, Integer id) {
		ResponseMessage result = new ResponseMessage();
		try {
			CpWebsiteInfo showtoedit = cpWebsiteInfoService.showtoedit(id);
			String datetime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(showtoedit.getLastUpdateTime());
			showtoedit.setDatetime(datetime);
			result.setCode(CommonConstant.SUCCESSCODE);
			result.setMsg(CommonConstant.SUCCESSSTRING);
			result.setData(showtoedit);
		} catch (Exception e1) {
			e1.printStackTrace();
			log.error("不能显示网站信息详情，" + e1.getMessage());
			result.setCode(CommonConstant.EXCEPTIONCODE);
			result.setMsg(CommonConstant.EXCEPTIONMSG);
		}
		return result;
	}
	/**
	 * 修改网站信息
	 * @param request
	 * @param response
	 * @param cpWebsiteInfo
	 * @param container
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/edit")
	@LogInfo(content = "修改网站信息", opeType = 2, logTypeCode = CommonConstant.Notice)
	public Object edit(HttpServletRequest request, HttpServletResponse response, CpWebsiteInfo cpWebsiteInfo ,String container) {
		ResponseMessage result = new ResponseMessage();
		try {
			CpUser user = SessionUtils.getUser(request);
			cpWebsiteInfo.setLastUpdateUser(user.getUserName());
			cpWebsiteInfo.setLastUpdateTime(new Date());
			cpWebsiteInfoService.edit(cpWebsiteInfo);
			result.setCode(CommonConstant.SUCCESSCODE);
			result.setMsg(CommonConstant.SUCCESSSTRING);
		} catch (Exception e1) {
			e1.printStackTrace();
			log.error("修改网站信息失败， " + e1.getMessage());
			result.setCode(CommonConstant.EXCEPTIONCODE);
			result.setMsg(CommonConstant.EXCEPTIONMSG);
		}
		return result;
	}
	/**
	 * 删除网站信息
	 * @param request
	 * @param response
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/delete")
	@LogInfo(content="删除网站信息",opeType=1,logTypeCode=CommonConstant.Other)
	public Object delete(HttpServletRequest request,HttpServletResponse response,String id) {
		ResponseMessage result=new ResponseMessage();
		try {
			String[] split = id.split(",");
			for (String i : split) {
				cpWebsiteInfoService.delete(Integer.parseInt(i));
			}
			result.setCode(CommonConstant.SUCCESSCODE);
			result.setMsg(CommonConstant.SUCCESSSTRING);	
		} catch (Exception e1) {
			e1.printStackTrace();
			log.error("删除广告位信息失败， "+e1.getMessage());
			result.setCode(CommonConstant.EXCEPTIONCODE);
			result.setMsg(CommonConstant.EXCEPTIONMSG);
		}
		return result;
	}
	/**
	 * 搜索网站信息
	 * @param request
	 * @param response
	 * @param search
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/search")
	public Object search(HttpServletRequest request, HttpServletResponse response, String search ,Integer langType) {
		ResponseMessage result = new ResponseMessage();
		try {
			PageHelper.startPage(request);
			List<CpWebsiteInfo> list = cpWebsiteInfoService.search(search,langType);
			for (int i = 0; i < list.size(); i++) {
				String datetime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(list.get(i).getLastUpdateTime());
				list.get(i).setDatetime(datetime);
			}
			PageHelper.addPages(result, list);
			result.setCode(CommonConstant.SUCCESSCODE);
			result.setMsg(CommonConstant.SUCCESSSTRING);
			result.setData(list);
			PageHelper.addPagesAndTotal(result, list);
		} catch (Exception e1) {
			e1.printStackTrace();
			log.error("搜索网站信息失败，" + e1.getMessage());
			result.setCode(CommonConstant.EXCEPTIONCODE);
			result.setMsg(CommonConstant.EXCEPTIONMSG);
		}
		return result;
	}
	@ResponseBody
	@RequestMapping("showToHomePage")
	@SkipLoginCheck
	@SkipAuthCheck
	public Object showtoindex(HttpServletRequest request, HttpServletResponse response) {
		ResponseMessage result = new ResponseMessage();
		try {
			CpSite site = SessionUtils.getSite(request);
			int geLangType = SessionUtils.geLangType(request);
			List<CpWebsiteInfo> list = cpWebsiteInfoService.showtoindex(SessionUtils.getSiteId(request),geLangType);
			result.setCode(CommonConstant.SUCCESSCODE);
			result.setMsg(CommonConstant.SUCCESSSTRING);
			result.setData(list);
		} catch (Exception e1) {
			e1.printStackTrace();
			log.error("不能显示网站信息，" + e1.getMessage());
			result.setCode(CommonConstant.EXCEPTIONCODE);
			result.setMsg(CommonConstant.EXCEPTIONMSG);
		}
		return result;
	}
}
