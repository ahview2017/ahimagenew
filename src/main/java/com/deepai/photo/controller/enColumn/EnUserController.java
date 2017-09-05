package com.deepai.photo.controller.enColumn;

import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.deepai.photo.bean.CpUser;
import com.deepai.photo.common.constant.CommonConstant;
import com.deepai.photo.common.pojo.ResponseMessage;
import com.deepai.photo.mapper.CpUserMapper;
import com.deepai.photo.service.admin.BasicInfoService;

/**
 * @author chenglong
 * 获取摄影师详情
 *
 */
@Controller
@RequestMapping("/enGetGroupPicCtro")
public class EnUserController {
	
	private Logger log = Logger.getLogger(EnColumnGroupPicController.class);
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
	public ResponseMessage selCpUserBasicInfo(CpUser cpUser)
			throws Exception {
		ResponseMessage result = new ResponseMessage();
		try {
			CpUser user = cpUserMapper.selectByPrimaryKey(cpUser.getId());
			if (user == null) {
				result.setCode(CommonConstant.NULLCODE);
				result.setMsg(String.format("不存在用户名为【%s】的用户 ！",
						cpUser.getUserName()));
			} else {
				result.setCode(CommonConstant.SUCCESSCODE);
				result.setMsg(CommonConstant.SUCCESSSTRING);
				result.setData(user);
			}
		} catch (Exception e1) {
			e1.printStackTrace();
			log.error("查询用户基本信息出错，" + e1.getMessage());
			result.setCode(CommonConstant.EXCEPTIONCODE);
			result.setMsg(CommonConstant.EXCEPTIONMSG);
		}
		return result;
	}
}
	