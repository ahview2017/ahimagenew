package com.deepai.photo.controller.client;

import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.deepai.photo.bean.CpUser;
import com.deepai.photo.common.annotation.LogInfo;
import com.deepai.photo.common.annotation.SkipAuthCheck;
import com.deepai.photo.common.annotation.SkipLoginCheck;
import com.deepai.photo.common.constant.CommonConstant;
import com.deepai.photo.common.pojo.ResponseMessage;
import com.deepai.photo.common.util.SessionUtils;
import com.deepai.photo.common.util.encrypt.Coder;
import com.deepai.photo.common.validation.CommonValidation;
import com.deepai.photo.mapper.CpUserMapper;
import com.deepai.photo.service.admin.BasicInfoService;

/**
 * 我的信息
 * 
 * @author zhangshuo
 */
@Controller
@RequestMapping("/myInfo")
public class MyInfoController {
	private Logger log = Logger.getLogger(MyInfoController.class);

	@Autowired
	private BasicInfoService basicInfoService;
	@Autowired
	private CpUserMapper cpUserMapper;

	/**
	 * 查询基本信息
	 * 
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/selCpUserBasicInfo")
	@SkipAuthCheck
	public Object selCpUserBasicInfo(HttpServletRequest request) {
		ResponseMessage result = new ResponseMessage();
		try {
			CpUser cpUser = SessionUtils.getUser(request);
			Map<String, Object> map = basicInfoService
					.selCpUserBasicInfo(cpUser);
			if (map.isEmpty()) {
				result.setCode(CommonConstant.NULLCODE);
				result.setMsg(String.format("不存在用户名为【%s】的用户 ！",
						cpUser.getUserName()));
			} else {
				result.setCode(CommonConstant.SUCCESSCODE);
				result.setMsg(CommonConstant.SUCCESSSTRING);
				result.setData(map);
			}
		} catch (Exception e1) {
			e1.printStackTrace();
			log.error("查询用户基本信息出错，" + e1.getMessage());
			result.setCode(CommonConstant.EXCEPTIONCODE);
			result.setMsg(CommonConstant.EXCEPTIONMSG);
		}
		return result;
	}

	/**
	 * 修改我的信息
	 * 
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/upCpUserBasicInfo")
	@SkipLoginCheck
	public Object upCpUserBasicInfo(HttpServletRequest request, CpUser cpUser) {
		ResponseMessage result = new ResponseMessage();
		try {
			CpUser user = SessionUtils.getUser(request);
			cpUser.setId(user.getId());
			cpUserMapper.updateByPrimaryKeySelective(cpUser);
			result.setCode(CommonConstant.SUCCESSCODE);
			result.setMsg(CommonConstant.SUCCESSSTRING);
			result.setOther(String.format("【%s】用户 ,资料修改成功！",
					user.getUserName()));
		} catch (Exception e1) {
			e1.printStackTrace();
			log.error("查询用户基本信息出错，" + e1.getMessage());
			result.setCode(CommonConstant.EXCEPTIONCODE);
			result.setMsg(CommonConstant.EXCEPTIONMSG);
		}
		return result;
	}

	/**
	 * 修改密码
	 * 
	 * @param request
	 *            , password, newPassword
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/upPassword")
	@LogInfo(content = "修改用户密码", opeType = 2, logTypeCode = CommonConstant.User)
	@SkipLoginCheck
	public Object upPassword(HttpServletRequest request, String password,
			String newPassword) {
		ResponseMessage result = new ResponseMessage();
		try {
			CommonValidation.checkParamBlank(password, "当前密码");
			CommonValidation.checkParamBlank(newPassword, "新密码");
			CpUser cpUser = SessionUtils.getUser(request);
			String strPassword = Coder.encryptBASE64(Coder.reverse(password));
			if ((strPassword.replace("\r\n","")).equals(cpUser.getPassword())) {
				cpUser.setUpdateUser(cpUser.getUserName());
				cpUser.setUpdateTime(new Date());
				cpUser.setSiteId(SessionUtils.getSiteId(request));
				cpUser.setPassword(Coder.encryptBASE64(Coder
						.reverse(newPassword)));
				cpUserMapper.updateByPrimaryKeySelective(cpUser);
				result.setCode(CommonConstant.SUCCESSCODE);
				result.setMsg(CommonConstant.SUCCESSSTRING);
				result.setOther(String.format("用户名为【%s】的用户,密码重置成功！",
						cpUser.getUserName()));
			} else {
				result.setCode(CommonConstant.PARAMERROR);
				result.setMsg(String.format("用户名为【%s】的用户,当前密码错误 ！",
						cpUser.getUserName()));
			}
		} catch (Exception e1) {
			e1.printStackTrace();
			log.error("修改用户密码出错，" + e1.getMessage());
			result.setCode(CommonConstant.EXCEPTIONCODE);
			result.setMsg(CommonConstant.EXCEPTIONMSG);
		}
		return result;
	}
}
