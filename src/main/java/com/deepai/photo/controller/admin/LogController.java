package com.deepai.photo.controller.admin;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.deepai.photo.bean.CpUser;
import com.deepai.photo.common.StringUtil;
import com.deepai.photo.common.annotation.SkipAuthCheck;
import com.deepai.photo.common.constant.CommonConstant;
import com.deepai.photo.common.exception.InvalidHttpArgumentException;
import com.deepai.photo.common.pagehelper.PageHelper;
import com.deepai.photo.common.pojo.ResponseMessage;
import com.deepai.photo.common.util.SessionUtils;
import com.deepai.photo.common.util.date.DateUtils;
import com.deepai.photo.common.validation.CommonValidation;
import com.deepai.photo.mapper.CpBasicMapper;
import com.deepai.photo.mapper.CpLogMapper;
import com.deepai.photo.mapper.CpLogTypeMapper;
import com.deepai.photo.service.admin.LogService;

/**
 * @author guoyanhong 日志
 */
@Controller
@RequestMapping("/logCtro")
public class LogController {
	private Logger log = Logger.getLogger(LogController.class);

	@Autowired
	private CpLogMapper cpLogManager;
	@Autowired
	private CpBasicMapper basicMapper;
	@Autowired
	private CpLogTypeMapper cpLogTypeMapper;
	@Autowired
	private LogService logService;

	/**
	 * 查询所有日志类型信息
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/getAllLogType")
	public Object getAllLogType(HttpServletRequest request, HttpServletResponse response) {
		ResponseMessage res = new ResponseMessage();
		try {
			List<Map<String, Object>> data = cpLogTypeMapper.selectAllLog();
			res.setCode(CommonConstant.SUCCESSCODE);
			res.setMsg(CommonConstant.SUCCESSSTRING);
			res.setData(data);
		} catch (Exception e) {
			e.printStackTrace();
			log.error("查询日志类型信息报错" + e.getMessage());
			res.setCode(CommonConstant.EXCEPTIONCODE);
			res.setMsg(CommonConstant.EXCEPTIONMSG);
		}
		return res;
	}

	/**
	 * 日志的高级检索
	 * 
	 * @param request
	 * @param opeIp
	 *            用户IP
	 * @param cpeUser
	 *            操作用户名
	 * @param opeContent
	 *            操作内容
	 * @param opeType
	 *            操作类型
	 * @param logtypeId
	 *            日志类型id
	 * @param beginTime
	 *            操作开始时间
	 * @param endTime
	 *            操作结束时间
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/advancedSearch")
	public Object getLogBySearch(HttpServletRequest request, String opeIp, String cpeUser, String opeContent,
			String opeType, String logtypeId, String beginTime, String endTime) {

		ResponseMessage res = new ResponseMessage();
		try {
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("siteId", SessionUtils.getSiteId(request));
			if (!StringUtil.isEmpty(cpeUser)) {
				param.put("cpeUser", cpeUser);
			}
			if (!StringUtil.isEmpty(opeContent)) {
				param.put("opeContent", opeContent);
			}
			if (!StringUtil.isEmpty(opeIp)) {
				param.put("opeIp", opeIp);
			}
			if (!StringUtil.isEmpty(opeType)) {
				param.put("opeType", Integer.parseInt(opeType));
			}
			if (!StringUtil.isEmpty(logtypeId)) {
				param.put("logtypeId", Integer.parseInt(logtypeId));
			}
			if (!StringUtil.isEmpty(beginTime)) {
				param.put("beginTime", DateUtils.fromStringToDate("yyyy-MM-dd", beginTime));
			}
			if (!StringUtil.isEmpty(endTime)) {
				param.put("endTime", DateUtils.fromStringToDate("yyyy-MM-dd", endTime));
			}
			PageHelper.startPage(request);
			List<Map<String, Object>> data = basicMapper.selectLogByQuery(param);
			PageHelper.addPages(res, data);
			res.setCode(CommonConstant.SUCCESSCODE);
			res.setMsg(CommonConstant.SUCCESSSTRING);
			res.setData(data);
			PageHelper.addPagesAndTotal(res, data);
		} catch (Exception e) {
			e.printStackTrace();
			log.error("查询日志信息报错" + e.getMessage());
			res.setCode(CommonConstant.EXCEPTIONCODE);
			res.setMsg(CommonConstant.EXCEPTIONMSG);
		}
		return res;
	}

	/**
	 * 查询日志
	 * 
	 * @param request
	 * @param cpeUser
	 *            操作人
	 * @param opeContent
	 *            操作内容
	 * @param beginTime
	 *            开始时间
	 * @param endTime
	 *            结束时间
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/getLogByQuery")
	@SkipAuthCheck
	public Object getLogByQuery(HttpServletRequest request, String cpeUser, String opeContent, String beginTime,
			String endTime) {
		ResponseMessage res = new ResponseMessage();
		try {
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("siteId", SessionUtils.getSiteId(request));
			String strWhere = request.getParameter("strWhere");
			if (!StringUtil.isEmpty(strWhere)) {
				param.put("strWhere", strWhere);
			}
			if (!StringUtil.isEmpty(cpeUser)) {
				param.put("cpeUser", cpeUser);
			}
			if (!StringUtil.isEmpty(opeContent)) {
				param.put("opeContent", opeContent);
			}
			if (!StringUtil.isEmpty(beginTime)) {
				param.put("beginTime", DateUtils.fromStringToDate("yyyy-MM-dd HH:mm:ss", beginTime));
			}
			if (!StringUtil.isEmpty(endTime)) {
				param.put("endTime", DateUtils.fromStringToDate("yyyy-MM-dd HH:mm:ss", endTime));
			}
			PageHelper.startPage(request);
			List<Map<String, Object>> data = basicMapper.selectLogByQuery(param);
			PageHelper.addPages(res, data);
			res.setCode(CommonConstant.SUCCESSCODE);
			res.setMsg(CommonConstant.SUCCESSSTRING);
			res.setData(data);
			PageHelper.addPagesAndTotal(res, data);
		} catch (Exception e) {
			e.printStackTrace();
			log.error("查询日志信息报错" + e.getMessage());
			res.setCode(CommonConstant.EXCEPTIONCODE);
			res.setMsg(CommonConstant.EXCEPTIONMSG);
		}
		return res;
	}

	/**
	 * 查询我的日志
	 * 
	 * @param request
	 * @param type
	 *            0登录日志，1操作日志
	 * @param days
	 *            最近几天
	 * @param userName
	 *            操作对象名称
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/getUserLogByQuery")
	public Object getUserLogByQuery(HttpServletRequest request, String type, String days) {
		ResponseMessage res = new ResponseMessage();
		try {
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("siteId", SessionUtils.getSiteId(request));
			param.put("cpeUser", SessionUtils.getUser(request).getUserName());//
			if (!StringUtil.isEmpty(days)) {
				param.put("days", Integer.valueOf(days));
			} else {
				param.put("days", 1);
			}
			if (!StringUtil.isEmpty(type)) {
				param.put("type", type);
			} else {
				param.put("type", 0);
			}

			PageHelper.startPage(request);
			List<Map<String, Object>> data = basicMapper.selectLogByQuery(param);
			PageHelper.addPages(res, data);
			res.setCode(CommonConstant.SUCCESSCODE);
			res.setMsg(CommonConstant.SUCCESSSTRING);
			res.setData(data);
		} catch (Exception e) {
			e.printStackTrace();
			log.error("查询日志信息报错" + e.getMessage());
			res.setCode(CommonConstant.EXCEPTIONCODE);
			res.setMsg(CommonConstant.EXCEPTIONMSG);
		}
		return res;
	}

	/**
	 * 查询用户登录日志
	 * 
	 * @param request
	 * @param type
	 * @param days
	 * @param userName
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/getUserLoginLog")
	@SkipAuthCheck
	public Object getUserLoginLog(HttpServletRequest request, String days, String userName, Integer siteId,
			Integer flag) {
		ResponseMessage res = new ResponseMessage();
		try {
			Map<String, Object> param = new HashMap<String, Object>();
			if (flag == null) {
				CommonValidation.checkParamBlank(userName, "用户名");
				param.put("cpeUser", userName);
			} else {
				CpUser user = SessionUtils.getUser(request);
				param.put("cpeUser", user.getUserName());
			}
			CommonValidation.checkParamBlank(siteId + "", "用户站点ID");
			param.put("siteId", siteId);
			if (!StringUtil.isEmpty(days)) {
				param.put("days", Integer.valueOf(days));
			} else {
				param.put("days", 1);
			}
			PageHelper.startPage(request);
			List<Map<String, Object>> data = basicMapper.selectUserLoginLog(param);
			PageHelper.addPages(res, data);
			res.setCode(CommonConstant.SUCCESSCODE);
			res.setMsg(CommonConstant.SUCCESSSTRING);
			res.setData(data);
			PageHelper.addPagesAndTotal(res, data);
		} catch (InvalidHttpArgumentException e) {
			res.setCode(e.getCode());
			res.setMsg(e.getMsg());
		} catch (Exception e) {
			e.printStackTrace();
			log.error("查询日志信息报错" + e.getMessage());
			res.setCode(CommonConstant.EXCEPTIONCODE);
			res.setMsg(CommonConstant.EXCEPTIONMSG);
		}
		return res;
	}

	/**
	 * 查询用户操作日志
	 * 
	 * @param request
	 * @param type
	 * @param days
	 * @param userName
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/getUserOpeLog")
	public Object getUserOpeLog(HttpServletRequest request, String days, Integer userId) {
		ResponseMessage res = new ResponseMessage();
		try {
			CommonValidation.checkParamBlank(userId + "", "用户Id");
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("opeId", "userid=" + userId);
			if (!StringUtil.isEmpty(days)) {
				param.put("days", Integer.valueOf(days));
			} else {
				param.put("days", 1);
			}
			PageHelper.startPage(request);
			List<Map<String, Object>> data = basicMapper.selectUserOpeLog(param);
			PageHelper.addPages(res, data);
			res.setCode(CommonConstant.SUCCESSCODE);
			res.setMsg(CommonConstant.SUCCESSSTRING);
			res.setData(data);
		} catch (InvalidHttpArgumentException e) {
			res.setCode(e.getCode());
			res.setMsg(e.getMsg());
		} catch (Exception e) {
			e.printStackTrace();
			log.error("查询日志信息报错" + e.getMessage());
			res.setCode(CommonConstant.EXCEPTIONCODE);
			res.setMsg(CommonConstant.EXCEPTIONMSG);
		}
		return res;
	}

	/**
	 * 日志的导出
	 * 
	 * @param request
	 * @param opeIp
	 *            用户IP
	 * @param cpeUser
	 *            操作用户名
	 * @param opeContent
	 *            操作内容
	 * @param opeType
	 *            操作类型
	 * @param logtypeId
	 *            日志类型id
	 * @param beginTime
	 *            操作开始时间
	 * @param endTime
	 *            操作结束时间
	 * @param seachType
	 *            检索类型
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/downLoadog")
	public Object exportLogInfo(HttpServletRequest request, HttpServletResponse response, String opeIp, String cpeUser,
			String opeContent, String opeType, String logtypeId, String beginTime, String endTime, String seachType) {

		ResponseMessage res = new ResponseMessage();
		try {
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("siteId", SessionUtils.getSiteId(request));
			String strWhere = request.getParameter("strWhere");
			
			if (StringUtil.isEmpty(seachType)) {
				if (!StringUtil.isEmpty(strWhere)) {
					param.put("strWhere", strWhere);
				}
			} 
			if (!StringUtil.isEmpty(cpeUser)) {
				param.put("cpeUser", cpeUser);
			}
			if (!StringUtil.isEmpty(opeContent)) {
				param.put("opeContent", opeContent);
			}
			if (!StringUtil.isEmpty(opeIp)) {
				param.put("opeIp", opeIp);
			}
			if (!StringUtil.isEmpty(opeType)) {
				param.put("opeType", Integer.parseInt(opeType));
			}
			if (!StringUtil.isEmpty(logtypeId)) {
				param.put("logtypeId", Integer.parseInt(logtypeId));
			}
			if (!StringUtil.isEmpty(beginTime)) {
				param.put("beginTime", DateUtils.fromStringToDate("yyyy-MM-dd", beginTime));
			}
			if (!StringUtil.isEmpty(endTime)) {
				param.put("endTime", DateUtils.fromStringToDate("yyyy-MM-dd", endTime));
			}
			// 日志的集合
			List<Map<String, Object>> logList = basicMapper.selectLogByQuery(param);
			ByteArrayOutputStream os = new ByteArrayOutputStream();
			SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss"); // 定义文件名格式
			HSSFWorkbook hssfWorkbook = logService.exportLog(logList);
			String msg = new String(("系统日志_" + format.format(new Date()) + ".xls").getBytes(), "ISO-8859-1");
			// 以导出时间作为文件名
			response.setContentType("application/vnd.ms-excel");
			response.addHeader("Content-Disposition", "attachment;filename=" + msg);
			hssfWorkbook.write(os);
			hssfWorkbook.write(response.getOutputStream());
			res.setCode(CommonConstant.SUCCESSCODE);
			res.setMsg(CommonConstant.SUCCESSSTRING);
		} catch (Exception e) {
			e.printStackTrace();
			log.error("导出日志信息报错" + e.getMessage());
			res.setCode(CommonConstant.EXCEPTIONCODE);
			res.setMsg(CommonConstant.EXCEPTIONMSG);
		}
		return res;
	}
}
