package com.deepai.photo.controller.admin;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.deepai.photo.bean.CpUser;
import com.deepai.photo.bean.CpUserBank;
import com.deepai.photo.bean.CpUserBankExample;
import com.deepai.photo.common.annotation.LogInfo;
import com.deepai.photo.common.annotation.SkipAuthCheck;
import com.deepai.photo.common.annotation.SkipLoginCheck;
import com.deepai.photo.common.constant.CommonConstant;
import com.deepai.photo.common.constant.SysConfigConstant;
import com.deepai.photo.common.pagehelper.PageHelper;
import com.deepai.photo.common.pojo.ResponseMessage;
import com.deepai.photo.common.util.SessionUtils;
import com.deepai.photo.common.util.date.DateUtils;
import com.deepai.photo.common.util.encrypt.Coder;
import com.deepai.photo.common.util.image.ImgFileUtils;
import com.deepai.photo.common.util.io.upload.FileUpload;
import com.deepai.photo.common.validation.CommonValidation;
import com.deepai.photo.mapper.CpUserBankMapper;
import com.deepai.photo.mapper.CpUserMapper;
import com.deepai.photo.service.admin.BasicInfoService;
import com.deepai.photo.service.admin.SysConfigService;

/**
 * 基本信息(适用于值班编辑、摄影师、销售)
 * 
 * @author zhangshuo
 */
@Controller
@RequestMapping("/basicInfo")
public class BasicInfoController {
	private Logger log = Logger.getLogger(BasicInfoController.class);

	@Autowired
	private BasicInfoService basicInfoService;
	@Autowired
	private CpUserMapper cpUserMapper;
	@Autowired
	private SysConfigService sysConfigService;
	@Autowired
	private CpUserBankMapper cpUserBankMapper;
	@Value("#{configProperties['ipAdd']}")
	private String ipAdd;

	/**
	 * 查询基本信息
	 * 
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/selCpUserBasicInfo")
	public Object selCpUserBasicInfo(HttpServletRequest request) {
		ResponseMessage result = new ResponseMessage();
		try {
			CpUser cpUser = SessionUtils.getUser(request);
			Map<String, Object> map = basicInfoService.selCpUserBasicInfo(cpUser);
			if (!map.isEmpty()) {
				result.setCode(CommonConstant.SUCCESSCODE);
				result.setMsg(CommonConstant.SUCCESSSTRING);
				result.setData(map);
			} else {
				result.setCode(CommonConstant.NULLCODE);
				result.setMsg(String.format("不存在用户名为【%s】的用户 ！", cpUser.getUnitName()));
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
	 * 修改基本的信息
	 * 
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/upCpUserBasicInfo")
	@LogInfo(content = "修改基本的信息", opeType = 2, logTypeCode = CommonConstant.User)
	public Object upCpUserBasicInfo(HttpServletRequest request, CpUser cpUser) {
		ResponseMessage result = new ResponseMessage();
		try {
			CpUser user = SessionUtils.getUser(request);
			cpUser.setId(user.getId());
			cpUserMapper.updateByPrimaryKeySelective(cpUser);
			result.setCode(CommonConstant.SUCCESSCODE);
			result.setMsg(CommonConstant.SUCCESSSTRING);
			result.setOther(String.format("【%s】用户 ,资料修改成功！", user.getUserName()));
		} catch (Exception e1) {
			e1.printStackTrace();
			log.error("查询用户基本信息出错，" + e1.getMessage());
			result.setCode(CommonConstant.EXCEPTIONCODE);
			result.setMsg(CommonConstant.EXCEPTIONMSG);
		}
		return result;
	}

	/**
	 * 修改基本的信息用户头像
	 * 
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/upCpUserHead")
	@LogInfo(content = "修改基本的信息用户头像", opeType = 2, logTypeCode = CommonConstant.User)
	public Object upCpUserHead(HttpServletRequest request, MultipartFile headPortraitFile) {
		ResponseMessage result = new ResponseMessage();
		try {
			CpUser user = SessionUtils.getUser(request);
			if (headPortraitFile != null) {
				StringBuffer sb = new StringBuffer();
				sb.append(DateUtils.getFormattedTime(DateUtils.sdfLongTime));
				sb.append(System.currentTimeMillis());
				sb.append("_hp.png");
				String fileName = sb.toString();
				// 上传
				String filePath = FileUpload.fileUpName(headPortraitFile, sysConfigService.getDbSysConfig(
						SysConfigConstant.HEAD_PORTRAIT_PATH, SessionUtils.getSiteId(request)), fileName);
				String newfilepath = ImgFileUtils.getReplacePathByName(filePath,sysConfigService.getDbSysConfig(SysConfigConstant.HEAD_PORTRAIT_PATH,SessionUtils.getSiteId(request))
						, ipAdd, "/headportrait/", request);
						
				user.setStandby3(newfilepath);
				cpUserMapper.updateByPrimaryKeySelective(user);
				result.setCode(CommonConstant.SUCCESSCODE);
				result.setMsg(CommonConstant.SUCCESSSTRING);
				result.setData(newfilepath);
				result.setOther(String.format("【%s】用户 ,资料修改成功！", user.getUserName()));
			} else {
				result.setCode(CommonConstant.PARAMERROR);
				result.setMsg(CommonConstant.PARAMERRORMSG);
				result.setOther("请上传头像图片");
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
	 * 修改密码
	 * 
	 * @param request
	 *            , password, newPassword
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/upPassword")
	@LogInfo(content = "修改用户密码", opeType = 2, logTypeCode = CommonConstant.User)
	public Object upPassword(HttpServletRequest request, String password, String newPassword) {
		ResponseMessage result = new ResponseMessage();
		try {
			CommonValidation.checkParamBlank(password, "当前密码");
			CommonValidation.checkParamBlank(newPassword, "新密码");
			CpUser cpUser = SessionUtils.getUser(request);
			String p1 = Coder.encryptBASE64(Coder.reverse(password));
			String p2 = cpUser.getPassword();
			/*
			 * System.out.println(p1.trim()); System.out.println(p2);
			 * System.out.println(p1.trim().equals(p2));
			 */
			if (p1.trim().equals(p2)) {
				cpUser.setUpdateUser(cpUser.getUserName());
				cpUser.setUpdateTime(new Date());
				cpUser.setSiteId(SessionUtils.getSiteId(request));
				cpUser.setPassword(Coder.encryptBASE64(Coder.reverse(newPassword)));
				cpUserMapper.updateByPrimaryKeySelective(cpUser);
				result.setCode(CommonConstant.SUCCESSCODE);
				result.setMsg(CommonConstant.SUCCESSSTRING);
				result.setOther(String.format("用户名为【%s】", cpUser.getUserName()));
			} else {
				result.setCode(CommonConstant.PARAMERROR);
				result.setMsg(String.format("用户名为【%s】的用户,当前密码错误 ！", cpUser.getUserName()));
			}
		} catch (Exception e1) {
			e1.printStackTrace();
			log.error("修改用户密码出错，" + e1.getMessage());
			result.setCode(CommonConstant.EXCEPTIONCODE);
			result.setMsg(CommonConstant.EXCEPTIONMSG);
		}
		return result;
	}

	/**
	 * 设置支付方式
	 * 
	 * @param request
	 *            , category, wmFile
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/upCpUserPayType")
	@LogInfo(content = "设置支付方式", opeType = 2, logTypeCode = CommonConstant.User)
	public Object upCpUserPayType(HttpServletRequest request, CpUser user) {
		ResponseMessage result = new ResponseMessage();
		try {
			CpUser cpUser = SessionUtils.getUser(request);
			user.setUpdateUser(cpUser.getUserName());
			user.setUpdateTime(new Date());
			user.setSiteId(SessionUtils.getSiteId(request));
			user.setId(cpUser.getId());
			cpUserMapper.updateByPrimaryKeySelective(user);
			result.setCode(CommonConstant.SUCCESSCODE);
			result.setMsg(CommonConstant.SUCCESSSTRING);
		} catch (Exception e1) {
			e1.printStackTrace();
			log.error("设置支付方式出错，" + e1.getMessage());
			result.setCode(CommonConstant.EXCEPTIONCODE);
			result.setMsg(CommonConstant.EXCEPTIONMSG);
		}
		return result;
	}

	/**
	 * 查询用户银行卡信息
	 * 
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/selBankInfo")
	public Object selBankInfo(HttpServletRequest request) {
		ResponseMessage result = new ResponseMessage();
		try {
			CpUser cpUser = SessionUtils.getUser(request);
			CpUserBankExample example = new CpUserBankExample();
			example.createCriteria().andUserIdEqualTo(cpUser.getId());
			example.setOrderByClause("CREATE_TIME desc");
			PageHelper.startPage(request);
			List<CpUserBank> list = cpUserBankMapper.selectByExample(example);
			PageHelper.addPages(result, list);
			result.setCode(CommonConstant.SUCCESSCODE);
			result.setMsg(CommonConstant.SUCCESSSTRING);
			result.setData(list);
		} catch (Exception e1) {
			e1.printStackTrace();
			log.error("查询用户银行卡信息出错，" + e1.getMessage());
			result.setCode(CommonConstant.EXCEPTIONCODE);
			result.setMsg(CommonConstant.EXCEPTIONMSG);
		}
		return result;
	}

	/**
	 * 添加或默认银行卡信息
	 * 
	 * @param request
	 * @param cpUserBank
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/addBankInfo")
	public Object addBankInfo(HttpServletRequest request, CpUserBank cpUserBank) {
		ResponseMessage result = new ResponseMessage();
		try {
			CpUser cpUser = SessionUtils.getUser(request);
			cpUserBank.setUserId(cpUser.getId());
			cpUserBankMapper.insertSelective(cpUserBank);
			CpUserBankExample example = new CpUserBankExample();
			example.createCriteria().andUserIdEqualTo(cpUser.getId()).andIsDefaultEqualTo(0);
			List<CpUserBank> listBank = cpUserBankMapper.selectByExample(example);
			if (listBank == null || listBank.size() < 1) {
				cpUserBankMapper.setDefault(cpUser.getId());
				listBank = cpUserBankMapper.selectByExample(example);
				cpUserBank = listBank.get(0);
				CpUser user = new CpUser();
				user.setBankAccount(cpUserBank.getBankAccount());
				user.setBankIdCard(cpUserBank.getBankIdCard());
				user.setBankName(cpUserBank.getBankName());
				user.setBankUsername(cpUserBank.getBankUsername());
				user.setId(cpUser.getId());
				cpUserMapper.updateByPrimaryKeySelective(user);
			}
			result.setCode(CommonConstant.SUCCESSCODE);
			result.setMsg(CommonConstant.SUCCESSSTRING);
		} catch (Exception e1) {
			e1.printStackTrace();
			log.error("查询用户银行卡信息出错，" + e1.getMessage());
			result.setCode(CommonConstant.EXCEPTIONCODE);
			result.setMsg(CommonConstant.EXCEPTIONMSG);
		}
		return result;
	}

	/**
	 * 修改用户银行卡信息
	 * 
	 * @param request
	 * @param cpUserBank
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/upBankInfo")
	public Object upBankInfo(HttpServletRequest request, CpUserBank cpUserBank) {
		ResponseMessage result = new ResponseMessage();
		try {
			cpUserBankMapper.updateByPrimaryKeySelective(cpUserBank);
			result.setCode(CommonConstant.SUCCESSCODE);
			result.setMsg(CommonConstant.SUCCESSSTRING);
		} catch (Exception e1) {
			e1.printStackTrace();
			log.error("修改用户银行卡信息出错，" + e1.getMessage());
			result.setCode(CommonConstant.EXCEPTIONCODE);
			result.setMsg(CommonConstant.EXCEPTIONMSG);
		}
		return result;
	}

	/**
	 * 删除银行卡信息
	 * 
	 * @param request
	 * @param cpUserBank
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/delBankInfo")
	public Object delBankInfo(HttpServletRequest request, CpUserBank cpUserBank) {
		ResponseMessage result = new ResponseMessage();
		try {
			CpUser cpUser = SessionUtils.getUser(request);
			CpUserBankExample example = new CpUserBankExample();
			example.createCriteria().andIdEqualTo(cpUserBank.getId());
			cpUserBankMapper.deleteByExample(example);
			example.clear();
			example.createCriteria().andUserIdEqualTo(cpUser.getId()).andIsDefaultEqualTo(0);
			List<CpUserBank> listBank = cpUserBankMapper.selectByExample(example);
			if (listBank == null || listBank.size() < 1) {
				cpUserBankMapper.setDefault(cpUser.getId());
				listBank = cpUserBankMapper.selectByExample(example);
				cpUserBank = listBank.get(0);
				CpUser user = new CpUser();
				user.setBankAccount(cpUserBank.getBankAccount());
				user.setBankIdCard(cpUserBank.getBankIdCard());
				user.setBankName(cpUserBank.getBankName());
				user.setBankUsername(cpUserBank.getBankUsername());
				user.setId(cpUser.getId());
				cpUserMapper.updateByPrimaryKeySelective(user);
			}
			result.setCode(CommonConstant.SUCCESSCODE);
			result.setMsg(CommonConstant.SUCCESSSTRING);
		} catch (Exception e1) {
			e1.printStackTrace();
			log.error("修改用户银行卡信息出错，" + e1.getMessage());
			result.setCode(CommonConstant.EXCEPTIONCODE);
			result.setMsg(CommonConstant.EXCEPTIONMSG);
		}
		return result;
	}
	/**
	 * 手动设置默认银行卡
	 * @param request
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/bankdef")
	public Object bankdef(HttpServletRequest request, Integer id) {
		ResponseMessage result = new ResponseMessage();
		try {
			CpUser cpUser = SessionUtils.getUser(request);
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("uid", cpUser.getId());
			map.put("id", id);
			cpUserBankMapper.setBankdef(map);
			result.setCode(CommonConstant.SUCCESSCODE);
			result.setMsg(CommonConstant.SUCCESSSTRING);
		} catch (Exception e1) {
			e1.printStackTrace();
			log.error("修改用户银行卡信息出错，" + e1.getMessage());
			result.setCode(CommonConstant.EXCEPTIONCODE);
			result.setMsg(CommonConstant.EXCEPTIONMSG);
		}
		return result;
	}
}
