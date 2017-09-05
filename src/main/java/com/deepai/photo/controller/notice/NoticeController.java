package com.deepai.photo.controller.notice;

import java.text.SimpleDateFormat;
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

import com.deepai.photo.bean.CpAdvPosition;
import com.deepai.photo.bean.CpMsg;
import com.deepai.photo.bean.CpNotice;
import com.deepai.photo.bean.CpUser;
import com.deepai.photo.common.annotation.LogInfo;
import com.deepai.photo.common.annotation.SkipAuthCheck;
import com.deepai.photo.common.annotation.SkipLoginCheck;
import com.deepai.photo.common.constant.CommonConstant;
import com.deepai.photo.common.pagehelper.PageHelper;
import com.deepai.photo.common.pojo.ResponseMessage;
import com.deepai.photo.common.util.SessionUtils;
import com.deepai.photo.controller.admin.RightController;
import com.deepai.photo.service.instant.StationMessiageService;
import com.deepai.photo.service.lanmu.LanmuService;
import com.deepai.photo.service.notice.NoticeService;
import com.deepai.photo.service.topic.TopicService;

/**
 * * @author huqiankai: *
 */
@Controller
@RequestMapping("/notice")
public class NoticeController {
	private Logger log = Logger.getLogger(NoticeController.class);
	@Autowired
	private NoticeService noticeService;
	@Autowired
	private TopicService topicService;
	@Autowired
	private LanmuService lanmuService;

	/**
	 * 显示网站公告
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
//			int langType = SessionUtils.geLangType(request);
			PageHelper.startPage(request);
			List<CpNotice> list = noticeService.show(langType);
			for (int i = 0; i < list.size(); i++) {
				if (list.get(i).getTopicId() != null) {
					String topicName = lanmuService.findTopicNameByTopicId(list.get(i).getTopicId());
					list.get(i).setTopicName(topicName);
				}
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
			log.error("不能显示网站公告，" + e1.getMessage());
			result.setCode(CommonConstant.EXCEPTIONCODE);
			result.setMsg(CommonConstant.EXCEPTIONMSG);
		}
		return result;
	}

	/**
	 * 创建网站公告
	 * 
	 * @param request
	 * @param response
	 * @param cpNotice
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/add")
	@LogInfo(content = "创建网站公告", opeType = 0, logTypeCode = CommonConstant.Notice)
	public Object add(HttpServletRequest request, HttpServletResponse response, String notice_title,
			String notice_content, Integer status, Integer tipId,Integer topic_id, Integer position, Integer langType) {
		ResponseMessage result = new ResponseMessage();
		try {
			CpNotice cpNotice = new CpNotice();
			if (StringUtils.isNotBlank(notice_content)) {
				cpNotice.setNoticeContent(notice_content);
			}
			if (notice_title == null) {
				result.setCode(CommonConstant.PARAMERROR);
				result.setMsg("标题不能为空");
				return result;
			}
			if (notice_content == null) {
				result.setCode(CommonConstant.PARAMERROR);
				result.setMsg("内容不能为空");
				return result;
			}
			if (StringUtils.isNotBlank(notice_title)) {
				cpNotice.setNoticeTitle(notice_title);
			}
			if (status != null) {
				cpNotice.setStatus(status);
			}
			if (tipId != null) {
				cpNotice.setTipId(tipId);
			}
			if (topic_id != null) {
				cpNotice.setTopicId(topic_id);
			}
			if (position != null) {
				cpNotice.setPosition(position);
			}
//			int geLangType = SessionUtils.geLangType(request);
			CpUser user = SessionUtils.getUser(request);
			cpNotice.setCreater(user.getUserName());
			cpNotice.setLangType(langType);
			cpNotice.setSiteId(SessionUtils.getSiteId(request));
			noticeService.add(cpNotice);
			result.setCode(CommonConstant.SUCCESSCODE);
			result.setMsg(CommonConstant.SUCCESSSTRING);
		} catch (Exception e1) {
			e1.printStackTrace();
			log.error("创建网站公告失败， " + e1.getMessage());
			result.setCode(CommonConstant.EXCEPTIONCODE);
			result.setMsg(CommonConstant.EXCEPTIONMSG);
		}
		return result;
	}

	/**
	 * 删除网站公告
	 * 
	 * @param request
	 * @param response
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/delete")
	@LogInfo(content = "删除网站公告", opeType = 1, logTypeCode = CommonConstant.Notice)
	public Object delete(HttpServletRequest request, HttpServletResponse response, String id) {
		ResponseMessage result = new ResponseMessage();
		try {
			String[] split = id.split(",");
			for (String i : split) {
				noticeService.delete(Integer.parseInt(i));
			}
			result.setCode(CommonConstant.SUCCESSCODE);
			result.setMsg(CommonConstant.SUCCESSSTRING);
		} catch (Exception e1) {
			e1.printStackTrace();
			log.error("删除网站公告失败， " + e1.getMessage());
			result.setCode(CommonConstant.EXCEPTIONCODE);
			result.setMsg(CommonConstant.EXCEPTIONMSG);
		}
		return result;
	}

	/**
	 * 修改网站公告
	 * 
	 * @param request
	 * @param response
	 * @param cpNotice
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/edit")
	@LogInfo(content = "修改网站公告", opeType = 2, logTypeCode = CommonConstant.Notice)
	public Object edit(HttpServletRequest request, HttpServletResponse response, String notice_title,
			String notice_content, Integer status, Integer topic_id, int id, Integer position, Integer yposition,Integer tipId) {
		ResponseMessage result = new ResponseMessage();
		try {
			CpNotice cpNotice = new CpNotice();
			cpNotice.setId(id);
			if (StringUtils.isNotBlank(notice_content)) {
				cpNotice.setNoticeContent(notice_content);
			}
			if (StringUtils.isNotBlank(notice_title)) {
				cpNotice.setNoticeTitle(notice_title);
			}
			if (tipId != null) {
				cpNotice.setTipId(tipId);
			}
			if (status != null) {
				cpNotice.setStatus(status);
			}
			if (topic_id != null) {
				cpNotice.setTopicId(topic_id);
			}
			if (yposition != null && yposition < position) {
				noticeService.edit1(cpNotice, yposition, position);
			}
			if (yposition != null && yposition > position) {
				noticeService.edit2(cpNotice, yposition, position);
			}
			if (yposition == null || yposition == position) {
				noticeService.edit(cpNotice);
			}
			result.setCode(CommonConstant.SUCCESSCODE);
			result.setMsg(CommonConstant.SUCCESSSTRING);
		} catch (Exception e1) {
			e1.printStackTrace();
			log.error("修改网站公告失败， " + e1.getMessage());
			result.setCode(CommonConstant.EXCEPTIONCODE);
			result.setMsg(CommonConstant.EXCEPTIONMSG);
		}
		return result;
	}

	@ResponseBody
	@RequestMapping("/showtoedit")
	@SkipLoginCheck
	@SkipAuthCheck
	public Object showtoedit(HttpServletRequest request, HttpServletResponse response, Integer id) {
		ResponseMessage result = new ResponseMessage();
		try {
			CpNotice cpNotice = noticeService.showtoedit(id);
			String datetime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(cpNotice.getCreateTime());
			cpNotice.setDatetime(datetime);
			result.setCode(CommonConstant.SUCCESSCODE);
			result.setMsg(CommonConstant.SUCCESSSTRING);
			result.setData(cpNotice);
		} catch (Exception e1) {
			e1.printStackTrace();
			log.error("不能显示公告具体信息，" + e1.getMessage());
			result.setCode(CommonConstant.EXCEPTIONCODE);
			result.setMsg(CommonConstant.EXCEPTIONMSG);
		}
		return result;
	}

	/**
	 * 搜索网站公告
	 * 
	 * @param request
	 * @param response
	 * @param searchName 公告标题
	 * @param langType 中英文字段
	 * @param timeFrom 开始时间
	 * @param timeTo   结束时间
	 * @param creater  发布者
	 * @param status   状态信息
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/search")
	public Object search(HttpServletRequest request, HttpServletResponse response,
			String searchName,Integer langType,String timeFrom,String timeTo,String creater,Integer status,Integer tipId) {
		ResponseMessage result = new ResponseMessage();
		try {
//			int langType = SessionUtils.geLangType(request);
			PageHelper.startPage(request);
			List<CpNotice> list = noticeService.search(searchName,timeFrom,timeTo,creater,status,tipId,langType,SessionUtils.getSiteId(request));
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
			log.error("搜索网站公告失败，" + e1.getMessage());
			result.setCode(CommonConstant.EXCEPTIONCODE);
			result.setMsg(CommonConstant.EXCEPTIONMSG);
		}
		return result;
	}

	@ResponseBody
	@RequestMapping("/showToHomePage")
	@SkipLoginCheck
	@SkipAuthCheck
	public Object showToHomePage(HttpServletRequest request, HttpServletResponse response ,Integer limit,Integer page ,Integer rows,Integer langType) {
		ResponseMessage result = new ResponseMessage();
		try {
			if (page!=null&&rows!=null) {
				PageHelper.startPage(request);
			}
			Map<String, Integer>map=new HashMap<>();
			map.put("limit", limit);
			map.put("site", SessionUtils.getSiteId(request));
			map.put("langType", langType);
			List<CpNotice> list = noticeService.showToHomePage(map);
			if (page!=null&&rows!=null) {
			PageHelper.addPages(result, list);}
			result.setCode(CommonConstant.SUCCESSCODE);
			result.setMsg(CommonConstant.SUCCESSSTRING);
			result.setData(list);
			result.setOther(list.size());
		} catch (Exception e1) {
			e1.printStackTrace();
			log.error("不能显示网站公告，" + e1.getMessage());
			result.setCode(CommonConstant.EXCEPTIONCODE);
			result.setMsg(CommonConstant.EXCEPTIONMSG);
		}
		return result;
	}
	/**
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/selTopicAdv")
	@SkipLoginCheck
	@SkipAuthCheck
	public Object selTopicAdv(HttpServletRequest request, HttpServletResponse response ,Integer topicId) {
		ResponseMessage result = new ResponseMessage();
		try {
			int langType = SessionUtils.geLangType(request);
			PageHelper.startPage(request);
			List<CpNotice> list = noticeService.showMoreLanmuWithAdv(topicId);
			PageHelper.addPages(result, list);
			result.setCode(CommonConstant.SUCCESSCODE);
			result.setMsg(CommonConstant.SUCCESSSTRING);
			result.setData(list);
			PageHelper.addPagesAndTotal(result, list);
		} catch (Exception e1) {
			e1.printStackTrace();
			log.error("不能显示网站公告，" + e1.getMessage());
			result.setCode(CommonConstant.EXCEPTIONCODE);
			result.setMsg(CommonConstant.EXCEPTIONMSG);
		}
		return result;
	}
}
