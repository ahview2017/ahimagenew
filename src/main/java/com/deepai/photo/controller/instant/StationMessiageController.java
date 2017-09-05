package com.deepai.photo.controller.instant;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import java.util.Set;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.text.DateFormatter;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.deepai.photo.bean.CpMsg;
import com.deepai.photo.bean.CpRole;
import com.deepai.photo.bean.CpUser;
import com.deepai.photo.common.StringUtil;
import com.deepai.photo.common.annotation.LogInfo;
import com.deepai.photo.common.constant.CommonConstant;
import com.deepai.photo.common.pagehelper.PageHelper;
import com.deepai.photo.common.pojo.ResponseMessage;
import com.deepai.photo.common.util.SessionUtils;
import com.deepai.photo.common.util.date.DateUtils;
import com.deepai.photo.common.validation.CommonValidation;
import com.deepai.photo.controller.admin.RightController;
import com.deepai.photo.mapper.CpBasicMapper;
import com.deepai.photo.mapper.CpRoleMapper;
import com.deepai.photo.mapper.CpUserMapper;
import com.deepai.photo.service.admin.UserRoleRightService;
import com.deepai.photo.service.instant.StationMessiageService;

import net.sf.json.JSONObject;

/**
 * @author huqiankai:
 * 
 */
@Controller
@RequestMapping("/station")
public class StationMessiageController {
	private Logger log = Logger.getLogger(StationMessiageController.class);
	@Autowired
	private StationMessiageService stationMessiageService;
	@Autowired
	private CpUserMapper cpUserMapper;
	@Autowired
	private UserRoleRightService userRoleRightService;
	@Autowired
	private CpBasicMapper cpBasicMapper;

	/**
	 * 显示所有站内信
	 * 
	 * @param request
	 * @param response
	 * @return list
	 */
	@ResponseBody
	@RequestMapping("/show")
	public Object show(HttpServletRequest request) {
		ResponseMessage result = new ResponseMessage();
		try {
			List<CpMsg> list = null;
			CpUser user = SessionUtils.getUser(request);
			Map<String, Integer> map = new HashMap<>();
			JSONObject jsonObject = new JSONObject();
			List<CpRole> userRole = cpBasicMapper.selectUserRoleByUId(user.getId());
			for (CpRole cpRole : userRole) {
				String key = cpRole.getId().toString();
				String value = cpRole.getRoleName();
				jsonObject.accumulate(key, value);
			}
			PageHelper.startPage(request);
			if (jsonObject.containsKey("1")) {
				list = stationMessiageService.showToAdminHome(map);
			} else {
				map.put("id", user.getId());
				list = stationMessiageService.showToAdminHome(map);
			}

			for (int i = 0; i < list.size(); i++) {
				String datetime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(list.get(i).getCreate_Time());
				list.get(i).setDatetime(datetime);
				String send_name = cpUserMapper.findUserNameByUid(list.get(i).getSEND_ID());
				String ac_name = cpUserMapper.findUserNameByUid(list.get(i).getAC_ID());
				list.get(i).setSend_name(send_name);
				list.get(i).setAc_name(ac_name);
			}
			PageHelper.addPages(result, list);
			result.setCode(CommonConstant.SUCCESSCODE);
			result.setMsg(CommonConstant.SUCCESSSTRING);
			result.setData(list);
			PageHelper.addPagesAndTotal(result, list);
		} catch (Exception e1) {
			e1.printStackTrace();
			log.error("不能显示站内信，" + e1.getMessage());
			result.setCode(CommonConstant.EXCEPTIONCODE);
			result.setMsg(CommonConstant.EXCEPTIONMSG);
		}
		return result;
	}

	/**
	 * 发送站内信
	 * 
	 * @param request
	 * @param response
	 * @param msgContent
	 * @param ids
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/send")
	@LogInfo(content = "发送站内信", opeType = 0, logTypeCode = CommonConstant.Message)
	public Object send(HttpServletRequest request, String msgContent, String team_id, String send_id, String msgTitle) {
		ResponseMessage result = new ResponseMessage();
		try {
			if (StringUtils.isBlank(team_id)) {
				result.setMsg("请选择角色");
				return result;
			}
			List<Integer> list = new ArrayList<>();
			if (StringUtils.isNotBlank(send_id)) {
				String[] notteam = send_id.split(",");
				for (String i : notteam) {
					List<Integer> id = userRoleRightService.findTeamID(Integer.parseInt(i));
					for (Integer integer : id) {
						if (!list.contains(integer)) {
							list.add(integer);
						}
					}
				}
			}
			if (StringUtils.isNotBlank(team_id)) {
				String[] split = team_id.split(",");
				for (String i : split) {
					List<Integer> list1 = cpUserMapper.findUidByTeamId(i);
					for (Integer integer : list1) {
						list.add(integer);
					}
				}
			}
			CpUser user = SessionUtils.getUser(request);
			CpMsg cpMsg = new CpMsg();
			cpMsg.setSEND_ID(user.getId());
			cpMsg.setMSG_CONTENT(msgContent);
			cpMsg.setMsgTitle(msgTitle);
			stationMessiageService.sendMsg(cpMsg);
			int id = stationMessiageService.findId();
			cpMsg.setContextId(id);
			for (int i : list) {
				cpMsg.setAC_ID(i);
				stationMessiageService.sendMsg2(cpMsg);
			}
			result.setCode(CommonConstant.SUCCESSCODE);
			result.setMsg(CommonConstant.SUCCESSSTRING);
		} catch (Exception e1) {
			e1.printStackTrace();
			log.error("发送站内信失败， " + e1.getMessage());
			result.setCode(CommonConstant.EXCEPTIONCODE);
			result.setMsg(CommonConstant.EXCEPTIONMSG);
		}
		return result;
	}

	/**
	 * 修改信息为已读
	 * 
	 * @param request
	 * @param response
	 * @param MsgIds
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/changeStatu")
	@LogInfo(content = "修改站内信状态", opeType = 2, logTypeCode = CommonConstant.Message, memo = "1是未读，0是已读")
	public Object changeStatu(HttpServletRequest request, HttpServletResponse response, String id) {
		ResponseMessage result = new ResponseMessage();
		try {
			String[] split = id.split(",");
			for (String i : split) {
				stationMessiageService.changeStatu(i);
			}
			result.setCode(CommonConstant.SUCCESSCODE);
			result.setMsg(CommonConstant.SUCCESSSTRING);
		} catch (Exception e1) {
			e1.printStackTrace();
			log.error("更改站内信状态出错， " + e1.getMessage());
			result.setCode(CommonConstant.EXCEPTIONCODE);
			result.setMsg(CommonConstant.EXCEPTIONMSG);
		}
		return result;
	}

	/**
	 * 删除站内信
	 * 
	 * @param request
	 * @param response
	 * @param MsgIds
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/delete")
	@LogInfo(content = "删除站内信", opeType = 1, logTypeCode = CommonConstant.Message)
	public Object delete(HttpServletRequest request, HttpServletResponse response, String id) {

		ResponseMessage result = new ResponseMessage();
		try {
			CpUser user = SessionUtils.getUser(request);
			CpMsg cpMsg = new CpMsg();
			cpMsg.setAC_ID(user.getId());
			String[] split = id.split(",");
			for (String i : split) {
				cpMsg.setId(Integer.parseInt(i));
				stationMessiageService.delete(cpMsg);
			}
			result.setCode(CommonConstant.SUCCESSCODE);
			result.setMsg(CommonConstant.SUCCESSSTRING);
		} catch (Exception e1) {
			e1.printStackTrace();
			log.error("删除站内信出错， " + e1.getMessage());
			result.setCode(CommonConstant.EXCEPTIONCODE);
			result.setMsg(CommonConstant.EXCEPTIONMSG);
		}
		return result;
	}

	/**
	 * 消息详情
	 * 
	 * @param request
	 * @param response
	 * @param MsgId
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/detail")
	public Object detail(HttpServletRequest request, HttpServletResponse response, Integer send_id) {

		ResponseMessage result = new ResponseMessage();
		try {
			CpMsg cpMsg = new CpMsg();
			CpUser user = SessionUtils.getUser(request);
			cpMsg.setAC_ID(user.getId());
			cpMsg.setSEND_ID(send_id);
			PageHelper.startPage(request);
			List<CpMsg> list = stationMessiageService.detail(cpMsg);
			for (int i = 0; i < list.size(); i++) {
				String datetime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(list.get(i).getCreate_Time());
				list.get(i).setDatetime(datetime);
				String send_name = cpUserMapper.findUserNameByUid(list.get(i).getSEND_ID());
				list.get(i).setSend_name(send_name);
			}
			PageHelper.addPages(result, list);
			result.setCode(CommonConstant.SUCCESSCODE);
			result.setMsg(CommonConstant.SUCCESSSTRING);
			result.setData(list);
		} catch (Exception e1) {
			e1.printStackTrace();
			log.error("站内信打开异常， " + e1.getMessage());
			result.setCode(CommonConstant.EXCEPTIONCODE);
			result.setMsg(CommonConstant.EXCEPTIONMSG);
		}
		return result;
	}

	/**
	 * 站内信的高级检索
	 * 
	 * @param request
	 * @param response
	 * @param sendName
	 *            发送者
	 * @param msgTitle
	 *            消息标题
	 * @param msgStatus
	 *            状态信息 1：未读 0：已读
	 * @param startTime
	 *            发送时间段 开始时间-----》
	 * @param endTime
	 *            --------》结束时间
	 * @return
	 */
	@ResponseBody
	@RequestMapping("advancedSearch")
	public Object advancedSearchStationMSG(HttpServletRequest request, HttpServletResponse response, String sendName,
			String msgTitle, String msgStatus, String startTime, String endTime) {

		ResponseMessage result = new ResponseMessage();
		try {
			PageHelper.startPage(request);
			Map<String, Object> param = new HashMap<String, Object>();
			CpUser user = SessionUtils.getUser(request);
			param.put("AC_ID", user.getId());

			if (!StringUtil.isEmpty(sendName)) {
				param.put("sendName", sendName);
			}
			if (!StringUtil.isEmpty(msgTitle)) {
				param.put("msgTitle", msgTitle);
			}
			if (!StringUtil.isEmpty(msgStatus)) {
				param.put("msgStatus", Integer.parseInt(msgStatus));
			}
			if (!StringUtil.isEmpty(startTime)) {
				param.put("startTime", DateUtils.fromStringToDate("yyyy-MM-dd", startTime));
			}
			if (!StringUtil.isEmpty(endTime)) {
				param.put("endTime", DateUtils.fromStringToDate("yyyy-MM-dd", endTime));
			}

			List<CpMsg> list = stationMessiageService.getStationMessageByQuery(param);
			for (int i = 0; i < list.size(); i++) {
				String datetime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(list.get(i).getCreate_Time());
				list.get(i).setDatetime(datetime);
				String send_name = cpUserMapper.findUserNameByUid(list.get(i).getSEND_ID());
				list.get(i).setSend_name(send_name);
			}
			PageHelper.addPages(result, list);
			result.setCode(CommonConstant.SUCCESSCODE);
			result.setMsg(CommonConstant.SUCCESSSTRING);
			result.setData(list);
			PageHelper.addPagesAndTotal(result, list);
		} catch (Exception e1) {
			e1.printStackTrace();
			log.error("站内信的高级检索失败，" + e1.getMessage());
			result.setCode(CommonConstant.EXCEPTIONCODE);
			result.setMsg(CommonConstant.EXCEPTIONMSG);
		}
		return result;
	}

	/**
	 * 搜索站内信
	 * 
	 * @param request
	 * @param response
	 * @param sendName
	 * @param sendConten
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/serachStationMSG")
	public Object serachStationMSG(HttpServletRequest request, HttpServletResponse response, String sendName,
			String msg_CONTENT) {
		ResponseMessage result = new ResponseMessage();
		try {
			CpUser user = SessionUtils.getUser(request);
			CpMsg cpMsg = new CpMsg();
			cpMsg.setAC_ID(user.getId());
			cpMsg.setSend_name(sendName);
			PageHelper.startPage(request);
			List<CpMsg> list = stationMessiageService.serachStationMSG(cpMsg);
			for (int i = 0; i < list.size(); i++) {
				String datetime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(list.get(i).getCreate_Time());
				list.get(i).setDatetime(datetime);
				String send_name = cpUserMapper.findUserNameByUid(list.get(i).getSEND_ID());
				list.get(i).setSend_name(send_name);
			}
			PageHelper.addPages(result, list);
			result.setCode(CommonConstant.SUCCESSCODE);
			result.setMsg(CommonConstant.SUCCESSSTRING);
			result.setData(list);
			PageHelper.addPagesAndTotal(result, list);
		} catch (Exception e1) {
			e1.printStackTrace();
			log.error("不能搜索站内信，" + e1.getMessage());
			result.setCode(CommonConstant.EXCEPTIONCODE);
			result.setMsg(CommonConstant.EXCEPTIONMSG);
		}
		return result;
	}

	@ResponseBody
	@RequestMapping("/showToAdminHome")
	public Object showToAdminHome(HttpServletRequest request, HttpServletResponse response, Integer page, Integer rows,
			Integer limit) {
		ResponseMessage result = new ResponseMessage();
		try {
			CpUser user = SessionUtils.getUser(request);
			if (page != null && rows != null) {
				PageHelper.startPage(request);
			}
			Map<String, Integer> map = new HashMap<>();
			map.put("limit", limit);
			map.put("id", user.getId());
			List<CpMsg> list = stationMessiageService.showToAdminHome(map);
			for (int i = 0; i < list.size(); i++) {
				String datetime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(list.get(i).getCreate_Time());
				list.get(i).setDatetime(datetime);
				String send_name = cpUserMapper.findUserNameByUid(list.get(i).getSEND_ID());
				list.get(i).setSend_name(send_name);
			}
			if (page != null && rows != null) {
				PageHelper.addPages(result, list);
			}
			result.setCode(CommonConstant.SUCCESSCODE);
			result.setMsg(CommonConstant.SUCCESSSTRING);
			result.setData(list);
		} catch (Exception e1) {
			e1.printStackTrace();
			log.error("不能显示站内信，" + e1.getMessage());
			result.setCode(CommonConstant.EXCEPTIONCODE);
			result.setMsg(CommonConstant.EXCEPTIONMSG);
		}
		return result;
	}

}
