package com.deepai.photo.controller.topic;

import java.awt.image.BufferedImage;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.deepai.photo.bean.CpLanmu;
import com.deepai.photo.bean.CpPicturePrice;
import com.deepai.photo.bean.CpTopic;
import com.deepai.photo.bean.CpUser;
import com.deepai.photo.common.annotation.LogInfo;
import com.deepai.photo.common.annotation.SkipAuthCheck;
import com.deepai.photo.common.annotation.SkipLoginCheck;
import com.deepai.photo.common.constant.CommonConstant;
import com.deepai.photo.common.constant.SysConfigConstant;
import com.deepai.photo.common.pagehelper.PageHelper;
import com.deepai.photo.common.pagehelper.PageInfo;
import com.deepai.photo.common.pojo.ResponseMessage;
import com.deepai.photo.common.util.SessionUtils;
import com.deepai.photo.controller.admin.RightController;
import com.deepai.photo.service.admin.SysConfigService;
import com.deepai.photo.service.admin.UserRoleRightService;
import com.deepai.photo.service.admin.WaterMarkService;
import com.deepai.photo.service.lanmu.LanmuService;
import com.deepai.photo.service.topic.TopicService;

/**
 * * @author huqiankai *
 */
@Controller
@RequestMapping("/topic")
public class TopicController {
	private Logger log = Logger.getLogger(TopicController.class);
	@Autowired
	private TopicService topicService;
	@Autowired
	private LanmuService lanmuService;
	@Autowired
	private WaterMarkService waterMarkService;
	@Autowired
	private UserRoleRightService userRoleRightService;
	@Autowired
	private SysConfigService sysConfigService;
	@Autowired
	private SysConfigService configService; 
	
	/**
	 * 专题添加
	 * 
	 * @param request
	 * @param response
	 * @param cpTopic
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/addTopic")
	@LogInfo(content = "专题添加", opeType = 0, logTypeCode = CommonConstant.Topic)
	public Object add(HttpServletRequest request, HttpServletResponse response, CpTopic cpTopic,
			@RequestParam(required = false, value = "wmFile") MultipartFile wmFile) {
		ResponseMessage result = new ResponseMessage();
		CpUser user = SessionUtils.getUser(request);
		try {
			String userName = user.getUserName();
			Integer userId = user.getId();
			Timestamp date = new Timestamp((new Date()).getTime());
			cpTopic.setCreateUser(userName);
			cpTopic.setCreateTime(date);
			cpTopic.setCreateUserId(userId);
			// 是否签发，网站是否显示
			if (wmFile != null && !wmFile.isEmpty()) {
				String pic_path = waterMarkService.insertOnePicture(wmFile,request);
				cpTopic.setEmage(pic_path);
			}
			topicService.add(cpTopic);
			result.setCode(CommonConstant.SUCCESSCODE);
			result.setMsg(CommonConstant.SUCCESSSTRING);
		} catch (Exception e1) {
			e1.printStackTrace();
			log.error("专题添加失败， " + e1.getMessage());
			result.setCode(CommonConstant.EXCEPTIONCODE);
			result.setMsg(CommonConstant.EXCEPTIONMSG);
		}
		return result;
	}

	/**
	 * 显示所有专题
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/showTopic")
	public Object show(HttpServletRequest request, HttpServletResponse response,Integer langType) {
		ResponseMessage result = new ResponseMessage();
		try {
			CpUser user = SessionUtils.getUser(request);
			List<Integer> RoseId = userRoleRightService.getRoseId(user.getId());
			PageHelper.startPage(request);
			List<CpTopic> list = topicService.show(RoseId,langType);
			/*
			String CLIENTIP = sysConfigService.getDbSysConfig(
			SysConfigConstant.CLIENTIP, 
			user.getSiteId());
			*/
			String CLIENTIP = null;
			List<String> clientList= configService.findEmail(SysConfigConstant.CLIENTIP,SysConfigConstant.CLIENTIP);
			if (clientList.size()>0) {
				CLIENTIP = clientList.get(0);
			}else{
				CLIENTIP = sysConfigService.getDbSysConfig(
						SysConfigConstant.CLIENTIP,
						user.getSiteId());
			}
			for (int i = 0; i < list.size(); i++) {
				String datetime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(list.get(i).getCreateTime());
				list.get(i).setDatetime(datetime);
				if (StringUtils.isNotBlank(CLIENTIP)) {
					list.get(i).setClentIp(CLIENTIP);
				}
			}
			PageHelper.addPages(result, list);
			result.setCode(CommonConstant.SUCCESSCODE);
			result.setMsg(CommonConstant.SUCCESSSTRING);
			result.setData(list);
			PageHelper.addPagesAndTotal(result, list);
		} catch (Exception e1) {
			e1.printStackTrace();
			log.error("不能显示所有专题，" + e1.getMessage());
			result.setCode(CommonConstant.EXCEPTIONCODE);
			result.setMsg(CommonConstant.EXCEPTIONMSG);
		}
		return result;
	}

	/**
	 * 显示所有专题到客户端
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/showTopicToClient")
	@SkipLoginCheck
	@SkipAuthCheck
	public Object showTopicToClient(HttpServletRequest request, HttpServletResponse response) {
		ResponseMessage result = new ResponseMessage();
		try {
			List<CpTopic> list = topicService.showTopicToClient();
			result.setCode(CommonConstant.SUCCESSCODE);
			result.setMsg(CommonConstant.SUCCESSSTRING);
			result.setData(list);
		} catch (Exception e1) {
			e1.printStackTrace();
			log.error("不能显示所有专题，" + e1.getMessage());
			result.setCode(CommonConstant.EXCEPTIONCODE);
			result.setMsg(CommonConstant.EXCEPTIONMSG);
		}
		return result;
	}

	/**
	 * 在广告页面显示要选择的专题
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/showTopicToAdv")
	public Object showTopicToAdv(HttpServletRequest request, HttpServletResponse response) {
		ResponseMessage result = new ResponseMessage();
		try {
			List<Map<String, Object>> list = topicService.showTopicToAdv();
			result.setCode(CommonConstant.SUCCESSCODE);
			result.setMsg(CommonConstant.SUCCESSSTRING);
			result.setData(list);
		} catch (Exception e1) {
			e1.printStackTrace();
			log.error("不能显示所有专题，" + e1.getMessage());
			result.setCode(CommonConstant.EXCEPTIONCODE);
			result.setMsg(CommonConstant.EXCEPTIONMSG);
		}
		return result;
	}

	/**
	 * 删除专题
	 * 
	 * @param request
	 * @param response
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/deleteTopic")
	@LogInfo(content = "专题删除", opeType = 1, logTypeCode = CommonConstant.Topic)
	public Object delete(HttpServletRequest request, HttpServletResponse response, String id) {
		ResponseMessage result = new ResponseMessage();
		try {
			String[] split = id.split(",");
			for (String i : split) {
				topicService.delete(Integer.parseInt(i));
			}
			result.setCode(CommonConstant.SUCCESSCODE);
			result.setMsg(CommonConstant.SUCCESSSTRING);
		} catch (Exception e1) {
			e1.printStackTrace();
			log.error("删除专题失败， " + e1.getMessage());
			result.setCode(CommonConstant.EXCEPTIONCODE);
			result.setMsg(CommonConstant.EXCEPTIONMSG);
		}
		return result;
	}

	/**
	 * 编辑保存专题
	 * 
	 * @param request
	 * @param response
	 * @param cpTopic
	 * @param wmFile
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/editTopic")
	@LogInfo(content = "专题修改", opeType = 2, logTypeCode = CommonConstant.Topic)
	public Object edit(HttpServletRequest request, HttpServletResponse response, CpTopic cpTopic,
			@RequestParam(required = false, value = "wmFile") MultipartFile[] wmFile) {
		ResponseMessage result = new ResponseMessage();
		try {
			if (wmFile != null && wmFile.length > 0) {
				String pic_path = waterMarkService.insertOnePicture(wmFile[0],request);
				cpTopic.setEmage(pic_path);
			}
			topicService.edit(cpTopic);
			result.setCode(CommonConstant.SUCCESSCODE);
			result.setMsg(CommonConstant.SUCCESSSTRING);
		} catch (Exception e1) {
			e1.printStackTrace();
			log.error("编辑保存专题失败， " + e1.getMessage());
			result.setCode(CommonConstant.EXCEPTIONCODE);
			result.setMsg(CommonConstant.EXCEPTIONMSG);
		}
		return result;
	}

	/**
	 * 按专题名字搜索专题
	 * 
	 * @param request
	 * @param response
	 * @param name
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/searchTopic")
	public Object searchTopic(HttpServletRequest request, HttpServletResponse response, String name,Integer langType) {
		ResponseMessage result = new ResponseMessage();
		try {
			CpUser user = SessionUtils.getUser(request);
			List<Integer> RoseId = userRoleRightService.getRoseId(user.getId());
			PageHelper.startPage(request);
			List<CpTopic> list = topicService.searchTopic(name, RoseId,langType);
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
			log.error("搜索专题失败， " + e1.getMessage());
			result.setCode(CommonConstant.EXCEPTIONCODE);
			result.setMsg(CommonConstant.EXCEPTIONMSG);
		}
		return result;
	}

	@ResponseBody
	@RequestMapping("/showToEditTopic")
	public Object show(HttpServletRequest request, HttpServletResponse response, int id) {
		ResponseMessage result = new ResponseMessage();
		try {
			CpTopic cpTopic = topicService.findTopicTopicId(id);
			result.setCode(CommonConstant.SUCCESSCODE);
			result.setMsg(CommonConstant.SUCCESSSTRING);
			result.setData(cpTopic);
		} catch (Exception e1) {
			e1.printStackTrace();
			log.error("不能显示所有专题，" + e1.getMessage());
			result.setCode(CommonConstant.EXCEPTIONCODE);
			result.setMsg(CommonConstant.EXCEPTIONMSG);
		}
		return result;
	}
	
	@RequestMapping("/topicPreview")
	@SkipAuthCheck
	public ModelAndView topicPreview(HttpServletRequest request, HttpServletResponse response){
		String url = request.getServletPath();
		String id = request.getParameter("topicId");
		url = request.getRequestURL().toString();
		url = url.substring(0, url.indexOf("cnsphoto"));
		url = url+"#/special/"+id;
		return new ModelAndView(new RedirectView(url));
	}
	
	@ResponseBody
	@RequestMapping("/getTopicByQuery")
	public Object getTopicByQuery(HttpServletRequest request, HttpServletResponse response, Integer siteShow,
			Integer signShow, String createUser, String topicName, String endTime, String beginTime, Integer page, Integer rows) {
		ResponseMessage result = new ResponseMessage();
		try {
			page=page==null?1:page;
			rows=rows==null?10:rows;
			PageHelper.startPage(page, rows);
			List<CpTopic> cpTopicList = topicService.getTopicByQuery(siteShow, signShow, createUser, topicName, endTime, beginTime);
			PageInfo pageInfo = new PageInfo(cpTopicList);
			int c = (int)pageInfo.getTotal();
			int p = pageInfo.getPages();			
			result.setCode(CommonConstant.SUCCESSCODE);
			result.setMsg(CommonConstant.SUCCESSSTRING);
			result.setData(cpTopicList);
			result.setPage(p);
			result.setOther(c);
		} catch (Exception e1) {
			e1.printStackTrace();
			log.error("不能显示所有专题，" + e1.getMessage());
			result.setCode(CommonConstant.EXCEPTIONCODE);
			result.setMsg(CommonConstant.EXCEPTIONMSG);
		}
		return result;
	}
}
