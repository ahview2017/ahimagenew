package com.deepai.photo.controller.enColumn;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.deepai.photo.common.constant.CommonConstant;
import com.deepai.photo.common.pojo.ResponseMessage;
import com.deepai.photo.mapper.CpPicGroupColumnMapper;
import com.deepai.photo.service.enColumn.EnSignColumnService;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

@Controller
@RequestMapping("/enSign")
public class EnSignColumnController {
	@Autowired
	private EnSignColumnService enSignColumnService;
	@Autowired
	private CpPicGroupColumnMapper cpPicGroupColumnMapper;

	private Logger log = Logger.getLogger(EnColumnController.class);

	@ResponseBody
	@RequestMapping("/signEnColumn")
	public Object sign(HttpServletRequest request, HttpServletResponse response, Integer groupId, String signColumn,
			Integer langType, String user) {
		ResponseMessage result = new ResponseMessage();
		try {
			Gson gson = new Gson();
			List<Map<String, Integer>> cates = gson.fromJson(signColumn, new TypeToken<List<Map<String, Integer>>>() {
			}.getType());
			for (Map<String, Integer> map : cates) {
				Integer signId = map.get("signId");
				Integer columnId = map.get("columnId");
				int i = cpPicGroupColumnMapper.getContByColumnIdAndSignId(columnId, signId, langType);// 查询columnId和columnId的这条记录是否存在0表示不存在
				if (i == 0) {
					enSignColumnService.signColumn(groupId, signId, columnId, langType);// 签发
				} else {
					enSignColumnService.updateCpPicGroupColumn(signId, columnId, groupId);// 如果这条记录存在就修改这条记录
				}

			}

			Timestamp date = new Timestamp((new Date()).getTime());
			enSignColumnService.updateGroupStatus(groupId, date, user);// 修改稿件的状态为4表示以签发
			result.setCode(CommonConstant.SUCCESSCODE);
			result.setMsg(CommonConstant.SUCCESSSTRING);
		} catch (Exception e) {
			e.printStackTrace();
			log.error("栏目展示失败，" + e.getMessage());
			result.setCode(CommonConstant.EXCEPTIONCODE);
			result.setMsg(CommonConstant.EXCEPTIONMSG);
		}

		return result;
	}

	/**
	 * 查看以签发在栏目的稿件
	 * 
	 * @param request
	 * @param response
	 * @param columnId
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/browsePosition")
	public Object browsePosition(HttpServletRequest request, HttpServletResponse response, Integer columnId) {
		ResponseMessage result = new ResponseMessage();
		try {
			List<Map<String, Object>> cpPicGroupList = enSignColumnService.browsePosition(columnId);
			result.setCode(CommonConstant.SUCCESSCODE);
			result.setMsg(CommonConstant.SUCCESSSTRING);
			result.setData(cpPicGroupList);
		} catch (Exception e) {
			e.printStackTrace();
			log.error("栏目展示失败，" + e.getMessage());
			result.setCode(CommonConstant.EXCEPTIONCODE);
			result.setMsg(CommonConstant.EXCEPTIONMSG);
		}

		return result;
	}

}
