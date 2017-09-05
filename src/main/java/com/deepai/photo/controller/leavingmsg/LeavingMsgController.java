package com.deepai.photo.controller.leavingmsg;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
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

import com.deepai.photo.bean.CpAdvPosition;
import com.deepai.photo.bean.CpMsg;
import com.deepai.photo.bean.CpNotice;
import com.deepai.photo.bean.CpSiteMessage;
import com.deepai.photo.bean.CpUser;
import com.deepai.photo.common.annotation.LogInfo;
import com.deepai.photo.common.constant.CommonConstant;
import com.deepai.photo.common.pagehelper.PageHelper;
import com.deepai.photo.common.pojo.ResponseMessage;
import com.deepai.photo.common.util.SessionUtils;
import com.deepai.photo.controller.admin.RightController;
import com.deepai.photo.service.leavingmsg.LeavingmsgService;
import com.deepai.photo.service.notice.NoticeService;

/**
 * * @author huqiankai: *
 */
@Controller
@RequestMapping("/leavingmsg")
public class LeavingMsgController {
	private Logger log = Logger.getLogger(LeavingMsgController.class);
	@Autowired
	private LeavingmsgService leavingmsgService;

	/**
	 * 查看网站留言
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/show")
	public Object show(HttpServletRequest request, HttpServletResponse response,Integer limit,Integer page ,Integer rows) {
		ResponseMessage result = new ResponseMessage();
		try {
			Map<String, Integer>map=new HashMap<>();
			map.put("limit", limit);
			List<CpSiteMessage> list =new ArrayList<>();
			if (page!=null&&rows!=null) {
				PageHelper.startPage(request);
				list= leavingmsgService.showtoadmin();
				for (int i = 0; i < list.size(); i++) {
					String datetime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(list.get(i).getCreateTime());
					list.get(i).setDatetime(datetime);
				}
				PageHelper.addPages(result, list);
				result.setData(list);
			}
			if (page==null&&rows==null) {
				list = leavingmsgService.showLeavingMsgLim(map);
				for (int i = 0; i < list.size(); i++) {
					String datetime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(list.get(i).getCreateTime());
					list.get(i).setDatetime(datetime);
				}
				result.setData(list);
				result.setCode(CommonConstant.SUCCESSCODE);
				result.setMsg(CommonConstant.SUCCESSSTRING);
				return result;
			}
			result.setCode(CommonConstant.SUCCESSCODE);
			result.setMsg(CommonConstant.SUCCESSSTRING);
			PageHelper.addPagesAndTotal(result, list);
		} catch (Exception e1) {
			e1.printStackTrace();
			log.error("不能查看网站留言，" + e1.getMessage());
			result.setCode(CommonConstant.EXCEPTIONCODE);
			result.setMsg(CommonConstant.EXCEPTIONMSG);
		}
		return result;
	}
	/**
	 * 删除网站留言
	 * @param request
	 * @param response
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/delete")
	@LogInfo(content = "删除网站留言", opeType = 2, logTypeCode = CommonConstant.PostComment)
	public Object delete(HttpServletRequest request,HttpServletResponse respons,String id) {
		ResponseMessage result=new ResponseMessage();
		try {
			String[] split = id.split(",");
			for (String i : split) {
				leavingmsgService.delete(Integer.parseInt(i));
			}
			result.setCode(CommonConstant.SUCCESSCODE);
			result.setMsg(CommonConstant.SUCCESSSTRING);	
		} catch (Exception e1) {
			e1.printStackTrace();
			log.error("删除网站留言失败， "+e1.getMessage());
			result.setCode(CommonConstant.EXCEPTIONCODE);
			result.setMsg(CommonConstant.EXCEPTIONMSG);
		}
		return result;
	}
	/**
	 * 网站留言详情
	 * @param request
	 * @param response
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/detail")
  @LogInfo(content="网站留言详情",opeType=0,logTypeCode=CommonConstant.PostComment)
	public Object edit(HttpServletRequest request,HttpServletResponse response,int id) {
		ResponseMessage result=new ResponseMessage();
		try {
			CpSiteMessage cpSiteMessage = leavingmsgService.edit(id);
			result.setCode(CommonConstant.SUCCESSCODE);
			result.setMsg(CommonConstant.SUCCESSSTRING);
			result.setData(cpSiteMessage);
		} catch (Exception e1) {
			e1.printStackTrace();
			log.error("网站留言详情查看失败， "+e1.getMessage());
			result.setCode(CommonConstant.EXCEPTIONCODE);
			result.setMsg(CommonConstant.EXCEPTIONMSG);
		}
		return result;
	}
	/**
	 * 添加留言
	 * @param request
	 * @param response
	 * @param cpAdvPosition
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/add")
	@LogInfo(content="添加留言",opeType=0,logTypeCode=CommonConstant.PostComment)
	public Object add(HttpServletRequest request,HttpServletResponse response,CpSiteMessage cpAdvPosition) {
		ResponseMessage result=new ResponseMessage();
		try {
			CpMsg cpMsg = new CpMsg();
			CpUser user = SessionUtils.getUser(request);
			cpAdvPosition.setSmLink(user.getTelContact());
			leavingmsgService.add(cpAdvPosition);
			result.setCode(CommonConstant.SUCCESSCODE);
			result.setMsg(CommonConstant.SUCCESSSTRING);	
		} catch (Exception e1) {
			e1.printStackTrace();
			log.error("添加留言失败， "+e1.getMessage());
			result.setCode(CommonConstant.EXCEPTIONCODE);
			result.setMsg(CommonConstant.EXCEPTIONMSG);
		}
		return result;
	}
	
	/**
	 * 根据创建时间和内容搜索
	 * @param request
	 * @param response
	 * @param content  留言内容
	 * @param smLink   联系方式  电话或者邮箱
	 * @param timefrom	开始时间
	 * @param timeto	结束时间
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/serchLeavingmsg")
	public Object serchLeavingmsg(HttpServletRequest request, HttpServletResponse response ,
			String smLink, String content, String timefrom,String timeto) {
		ResponseMessage result = new ResponseMessage();
		try {
			PageHelper.startPage(request);
			List<CpSiteMessage> list = leavingmsgService.serchLeavingmsg(content,timefrom,timeto,smLink);
			for (int i = 0; i < list.size(); i++) {
				String datetime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(list.get(i).getCreateTime());
				list.get(i).setDatetime(datetime);
				}
			PageHelper.addPages(result, list);
			result.setCode(CommonConstant.SUCCESSCODE);
			result.setMsg(CommonConstant.SUCCESSSTRING);
			result.setData(list);
			PageHelper.addPagesAndTotal(result, list);
		} catch (Exception e1) {
			e1.printStackTrace();
			log.error("不能查看网站留言，" + e1.getMessage());
			result.setCode(CommonConstant.EXCEPTIONCODE);
			result.setMsg(CommonConstant.EXCEPTIONMSG);
		}
		return result;
	}
	}
