package com.deepai.photo.controller.admin;

import java.io.ByteArrayOutputStream;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.deepai.photo.bean.CpLog;
import com.deepai.photo.bean.CpRole;
import com.deepai.photo.bean.CpUser;
import com.deepai.photo.bean.CpUserBank;
import com.deepai.photo.bean.CpUserExample;
import com.deepai.photo.bean.CpUserRole;
import com.deepai.photo.common.StringUtil;
import com.deepai.photo.common.annotation.LogInfo;
import com.deepai.photo.common.annotation.SkipAuthCheck;
import com.deepai.photo.common.annotation.SkipLoginCheck;
import com.deepai.photo.common.constant.CommonConstant;
import com.deepai.photo.common.constant.SysConfigConstant;
import com.deepai.photo.common.exception.InvalidHttpArgumentException;
import com.deepai.photo.common.pagehelper.PageHelper;
import com.deepai.photo.common.pagehelper.PageInfo;
import com.deepai.photo.common.pojo.ResponseMessage;
import com.deepai.photo.common.util.MathUtil;
import com.deepai.photo.common.util.SessionUtils;
import com.deepai.photo.common.util.date.DateUtils;
import com.deepai.photo.common.util.encrypt.Coder;
import com.deepai.photo.common.validation.CommonValidation;
import com.deepai.photo.controller.email.MailController;
import com.deepai.photo.controller.instant.StationMessiageController;
import com.deepai.photo.controller.phonemsg.PhoneMSGController;
import com.deepai.photo.mapper.CpBasicMapper;
import com.deepai.photo.mapper.CpLogMapper;
import com.deepai.photo.mapper.CpUserBankMapper;
import com.deepai.photo.mapper.CpUserMapper;
import com.deepai.photo.mapper.CpUserRoleMapper;
import com.deepai.photo.mapper.OtherMapper;
import com.deepai.photo.service.admin.BasicInfoService;
import com.deepai.photo.service.admin.LogService;
import com.deepai.photo.service.admin.SysConfigService;
import com.deepai.photo.service.admin.UserRoleRightService;
import com.deepai.photo.service.enColumn.EnGroupManagementService;
import com.deepai.photo.service.enColumn.ExportUserInfo;

import net.sf.json.JSONObject;

/**
 * @author guoyanhong 用户管理
 */
@Controller
@RequestMapping("/userCtro")
public class UserController {
	private Logger log = Logger.getLogger(UserController.class);
	@Autowired
	private CpUserMapper cpUserMapper;
	@Autowired
	private CpUserRoleMapper cpUserRoleMapper;
	@Autowired
	private CpBasicMapper basicMapper;
	@Autowired
	private UserRoleRightService userRoleRightService;
	@Autowired
	private SysConfigService configService;
	@Autowired
	private MailController mailController;
	@Autowired
	private PhoneMSGController phoneMSGController;
	@Autowired
	private OtherMapper otherMapper;
	@Autowired
	private StationMessiageController stationMessiageController;
	@Autowired
	private BasicInfoService basicInfoService;
	@Autowired
	private CpUserBankMapper cpBankMapper;
	@Autowired
	private ExportUserInfo exportUserInfo;
	@Autowired
	private EnGroupManagementService enGroupManagementService;
	@Autowired
	private CpLogMapper cpLogMapper;
	@Autowired
	private LogService logService;
	/**
	 * 
	 * @param request
	 * @param userName
	 *            用户名称，查询条件，可以为空
	 * @param userId
	 *            用户ID，查询条件，可以为空
	 * @param roleId
	 *            角色ID，查询条件，可以为空
	 * @param userStatus
	 *            用户状态：0已开通，1正申请，2已删除，3已禁用，查询条件，可以为空
	 * @param telContact
	 *            联系电话，查询条件，可以为空
	 * @param direction
	 *            摄影方向，查询条件，可以为空
	 * @param zone
	 *            所属地域，查询条件，可以为空
	 * @param province
	 *            省，查询条件，可以为空
	 * @param city
	 *            市，查询条件，可以为空
	 * @param telContact
	 *            联系方式 0：邮箱 1：手机 2:QQ 3:微信
	 * @param emailBind
	 *            绑定邮箱
	 * @param telBind
	 *            绑定电话
	 * @param standBy1
	 *            QQ
	 * @param standBy2
	 *            微信
	 * @param trueName
	 *            真实姓名
	 * @param subScriberType
	 *            订户类型
	 * @param userType
	 *            订户级别
	 * @param unitFlax
	 *            单位船政
	 * @param unitTel
	 *            单位电话
	 * @param authorName
	 *            作者名
	 * @param address
	 *            详细地址
	 * @param unitName
	 *            单位名称
	 * @param unitAddress
	 *            单位地址
	 * @param mailAddress
	 *            通信地址
	 * @param mailUserName
	 *            收稿费人姓名
	 * @param mailIdCard
	 *            收稿费人身份证卡号
	 * @param feeType
	 *            接收稿费方式
	 * @param mailZipCode
	 *            邮政编码
	 * @param bankAccount
	 *            邮政储蓄存折卡号或账号
	 * @param bankUserName
	 *            上述存折或卡的户名
	 * @param bankIdCard
	 *            上述卡户名的身份证号
	 * @param bankName
	 *            开户行
	 * @param memo
	 *            备注
	 * @param parameter
	 *            基本检索条件
	 * @param orderTime
	 * @param page
	 * @param rows
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/getUserByQuery")
	public Object getUserByQuery(HttpServletRequest request, String userName, String userId, String roleId,
			String userStatus, String direction, String zone, String province, String langType, String city,
			Integer telContact, String emailBind, String telBind, String standBy1, String standBy2, String trueName,
			String subScriberType, String userType, String unitFlax, String unitTel, String authorName, String address,
			String unitName, String unitAddress, String mailAddress, String mailUserName, String mailIdCard,
			String feeType, String mailZipCode, String bankAccount, String bankUserName, String bankIdCard, String bankName,
			String memo, String parameter, String orderTime, Integer page, Integer rows) {
		ResponseMessage result = new ResponseMessage();
		try {

			List<Map<String, Object>> userList = getAdvancedSearchData(request, userName, userId, roleId, userStatus,
					direction, zone, province, langType, city, telContact, emailBind, telBind, standBy1, standBy2,
					trueName, subScriberType, userType, unitFlax, unitTel, authorName, address, unitName, unitAddress,
					mailAddress, mailUserName, mailIdCard, feeType, mailZipCode, bankAccount, bankUserName, bankIdCard,
					bankName, memo, parameter, orderTime, page, rows, 1);
			PageInfo pageInfo = new PageInfo(userList);

			int c = (int) pageInfo.getTotal();
			int p = pageInfo.getPages();
			result.setPage(p);
			result.setCode(CommonConstant.SUCCESSCODE);
			result.setMsg(CommonConstant.SUCCESSSTRING);
			result.setData(userList);
			result.setOther(c);
		} catch (Exception e1) {
			e1.printStackTrace();
			log.error("查询用户信息出错，" + e1.getMessage());
			result.setCode(CommonConstant.EXCEPTIONCODE);
			result.setMsg(CommonConstant.EXCEPTIONMSG);
		}
		return result;
	}

	private List<Map<String, Object>> getAdvancedSearchData(HttpServletRequest request, String userName, String userId,
			String roleId, String userStatus, String direction, String zone, String province, String langType,
			String city, Integer telContact, String emailBind, String telBind, String standBy1, String standBy2,
			String trueName, String subScriberType, String userType, String unitFlax, String unitTel, String authorName,
			String address, String unitName, String unitAddress, String mailAddress, String mailUserName,
			String mailIdCard, String feeType, String mailZipCode, String bankAccount, String bankUserName,
			String bankIdCard, String bankName, String memo, String parameter, String orderTime, Integer page,
			Integer rows, Integer pageType) throws Exception {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("siteId", SessionUtils.getSiteId(request));
		List<Map<String, Object>> userList = null;
		if (StringUtil.isNotBlank(userId)) {
			param.put("userId", userId);
		}
		if (StringUtil.isNotBlank(userName)) {
			param.put("userName", userName);
		}
		if (StringUtil.isNotBlank(roleId)) {
			param.put("roleId", roleId);
		}
		if (StringUtil.isNotBlank(userStatus)) {
			param.put("userStatus", userStatus);
		}
		if (telContact != null) {
			switch (telContact) {
			case 0:
				param.put("emailBind", emailBind);
				break;
			case 1:
				param.put("telBind", telBind);
				break;
			case 2:
				param.put("standBy1", standBy1);
				break;
			case 3:
				param.put("standBy2", standBy2);
				break;
			}
		}
		if (StringUtil.isNotBlank(direction)) {
			param.put("direction", direction);
		}
		if (StringUtil.isNotBlank(zone)) {
			param.put("zone", zone);
		}
		if (StringUtil.isNotBlank(province)) {
			param.put("province", province);
		}
		if (StringUtil.isNotBlank(city)) {
			param.put("city", city);
		}
		if (StringUtil.isNotBlank(trueName)) {
			param.put("trueName", trueName);
		}
		if (StringUtil.isNotBlank(subScriberType)) {
			param.put("subScriberType", subScriberType);
		}
		if (StringUtil.isNotBlank(userType)) {
			param.put("userType", userType);
		}
		if (StringUtil.isNotBlank(unitFlax)) {
			param.put("unitFlax", unitFlax);
		}
		if (StringUtil.isNotBlank(unitTel)) {
			param.put("unitTel", unitTel);
		}
		if (StringUtil.isNotBlank(authorName)) {
			param.put("authorName", authorName);
		}
		if (StringUtil.isNotBlank(address)) {
			param.put("address", address);
		}
		if (StringUtil.isNotBlank(unitName)) {
			param.put("unitName", unitName);
		}
		if (StringUtil.isNotBlank(unitAddress)) {
			param.put("unitAddress", unitAddress);
		}
		if (StringUtil.isNotBlank(mailAddress)) {
			param.put("mailAddress", mailAddress);
		}
		if (StringUtil.isNotBlank(mailUserName)) {
			param.put("mailUserName", mailUserName);
		}
		if (StringUtil.isNotBlank(mailIdCard)) {
			param.put("mailIdCard", mailIdCard);
		}
		if (StringUtil.isNotBlank(feeType)) {
			param.put("feeType", feeType);
		}
		if (StringUtil.isNotBlank(mailZipCode)) {
			param.put("mailZipCode", mailZipCode);
		}
		if (StringUtil.isNotBlank(bankAccount)) {
			param.put("bankAccount", bankAccount);
		}
		if (StringUtil.isNotBlank(bankUserName)) {
			param.put("bankUserName", bankUserName);
		}
		if (StringUtil.isNotBlank(bankIdCard)) {
			param.put("bankIdCard", bankIdCard);
		}
		if (StringUtil.isNotBlank(bankName)) {
			param.put("bankName", bankName);
		}
		if (StringUtil.isNotBlank(memo)) {
			param.put("memo", memo);
		}
		if (StringUtil.isNotBlank(parameter)) {
			param.put("parameter", parameter);
		}
		if (StringUtil.isNotBlank(langType)) {
			param.put("langType", langType);
		}
		page = page == null ? 1 : page;
		rows = rows == null ? 10 : rows;
		if (pageType == 1) {
			PageHelper.startPage(page, rows);
		}

		// 查询角色

		if (StringUtil.isNotBlank(orderTime) && "0".equals(orderTime)) {
			userList = basicMapper.selectUserByCreate(param);
		} else if (StringUtil.isNotBlank(orderTime) && "1".equals(orderTime)) {
			userList = basicMapper.selectUserByLogin(param);
		} else if (StringUtil.isNotBlank(orderTime) && "2".equals(orderTime)) {
			userList = basicMapper.selectUserByOrderId(param);
		}else {
			userList = basicMapper.selectUserByCreate(param);
		}
		if (userList != null && userList.size() > 0) {
			for (int i = 0; i < userList.size(); i++) {
				Map<String, Object> user = userList.get(i);
				List<CpRole> r = basicMapper.selectUserRoleByUId(Integer.valueOf(user.get("ID").toString()));// 查询用户角色
				user.put("roles", r);
			}
		}
		return userList;
	}

	/**
	 * 查询所有的用户 ，返回用户名、真实姓名
	 * @author xiayunan
	 * @date 2018年4月17日 
	 * @param request
	 * @param response
	 */
	@ResponseBody
	@RequestMapping("/getUserAll")
	public Object getUserAll(HttpServletRequest request, HttpServletResponse response, String userName, Integer groupId,
			Integer page, Integer rows) {
		ResponseMessage result = new ResponseMessage();
		List<Map<String, Object>> userList = null;
		try {
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("siteId", SessionUtils.getSiteId(request));
			param.put("groupId", groupId);
			if (StringUtil.isNotBlank(userName)) {
				param.put("userName", userName);
			}
			page = page == null ? 1 : page;
			rows = rows == null ? 10 : rows;
			PageHelper.startPage(page, rows);
			userList = basicMapper.getUserAll(param);

			PageInfo pageInfo = new PageInfo(userList);
			// PageHelper.addPages(result, userList);
			// int c=basicMapper.selectUserByQuery1Count(param);
			int c = (int) pageInfo.getTotal();
			int p = pageInfo.getPages();
			result.setPage(p);
			result.setCode(CommonConstant.SUCCESSCODE);
			result.setMsg(CommonConstant.SUCCESSSTRING);
			result.setData(userList);
			result.setOther(c);
		} catch (Exception e1) {
			e1.printStackTrace();
			log.error("查询用户信息出错，" + e1.getMessage());
			result.setCode(CommonConstant.EXCEPTIONCODE);
			result.setMsg(CommonConstant.EXCEPTIONMSG);
		}
		return result;
	}
	
	
	
	

	/**
	 * 用户切换角色
	 * 
	 * @param roleId
	 *            要切换的角色id
	 * @param userId
	 *            用户ID
	 * @return 用户改角色下所有权限U用户个人权限
	 */
	@ResponseBody
	@RequestMapping("/changeUserRole")
	@SkipAuthCheck
	public Object changeUserRole(HttpServletRequest request, String roleId) {
		ResponseMessage result = new ResponseMessage();
		try {
			CommonValidation.checkParamBlank(roleId, "角色Id");
			if (roleId.equals("5")) {

			}
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("siteId", SessionUtils.getSiteId(request));
			param.put("userId", SessionUtils.getUser(request).getId());
			if (StringUtil.isNotBlank(roleId)) {
				param.put("roleId", roleId);
			}
			// 查询角色
			List<Map<String, Object>> rights = basicMapper.selectAllRightByUserId(param);
			result.setCode(CommonConstant.SUCCESSCODE);
			result.setMsg(CommonConstant.SUCCESSSTRING);
			result.setData(rights);
		} catch (InvalidHttpArgumentException e) {
			e.printStackTrace();
			result.setCode(e.getCode());
			result.setMsg(e.getMsg());
		} catch (Exception e1) {
			e1.printStackTrace();
			log.error("切换角色出错，" + e1.getMessage());
			result.setCode(CommonConstant.EXCEPTIONCODE);
			result.setMsg(CommonConstant.EXCEPTIONMSG);
		}
		return result;
	}

	/**
	 * 查询用户权限 =所属角色权限+用户权限
	 * 
	 * @param request
	 * @param userId
	 *            用户ID，查询条件，可以为空
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/getUserRights")
	public Object getUserRights(HttpServletRequest request, String userId) {
		ResponseMessage result = new ResponseMessage();
		try {
			CommonValidation.checkParamBlank(userId, "用户Id");
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("userId", userId);
			List<Map<String, Object>> userList = basicMapper.selectUserRightById(param);
			result.setCode(CommonConstant.SUCCESSCODE);
			result.setMsg(CommonConstant.SUCCESSSTRING);
			result.setData(userList);
		} catch (InvalidHttpArgumentException e) {
			e.printStackTrace();
			result.setCode(e.getCode());
			result.setMsg(e.getMsg());
		} catch (Exception e1) {
			e1.printStackTrace();
			log.error("查询用户权限出错，" + e1.getMessage());
			result.setCode(CommonConstant.EXCEPTIONCODE);
			result.setMsg(CommonConstant.EXCEPTIONMSG);
		}
		return result;
	}

	/**
	 * 管理员新建用户信息
	 * 
	 * @param request
	 * @param userName
	 *            用户名称
	 * @param roleIds
	 *            用户角色ID,多个用英文逗号隔开
	 * @param email
	 *            用户邮箱
	 * @param tureName
	 *            真实姓名
	 * @param tel
	 *            手机
	 * @param procity
	 *            所属省市
	 * @param idcard
	 *            身份证号
	 * @param authorName
	 *            笔名/联系人，如果是订户，则是联系人，摄影师是笔名
	 * @param address
	 *            详细地址
	 * @param unitName
	 *            单位名称
	 * @param zipcode
	 *            用户邮编 String userName,String memo,String roleId, String
	 *            email,String tureName,String procity,String idcard, String
	 *            authorName,String address,String unitName,String zipcode
	 * @return
	 */
	// 一个用户可以有多个角色吗？答： 一对多 登录成功之后，取所有角色权限+用户权限并集
	@ResponseBody
	@RequestMapping("/addUser")
	@LogInfo(content = "管理员新建用户信息", opeType = 0, logTypeCode = CommonConstant.User)
	public Object addUser(HttpServletRequest request, CpUser user, String roleIds,String userToken) {
		ResponseMessage result = new ResponseMessage();
		try {
			CommonValidation.checkParamBlank(user.getUserName(), "用户名");
			CommonValidation.checkParamBlank(roleIds, "角色");
			CommonValidation.checkParamBlank(user.getTureName(), "真实姓名");
			// CommonValidation.checkParamBlank(user.getIdCard(), "身份证号");
			CommonValidation.checkParamBlank(user.getPassword(), "密码");
			CpUser admin = SessionUtils.getUser(request);// 当前登录用户
			CpUser cpUser = userRoleRightService.verify(userToken);
			if (cpUser ==null || (int)cpUser.getId() != (int)admin.getId()) {
				result.setCode(CommonConstant.FAILURECODE);
				result.setMsg("新建用户失败，请重新登录！");
				return result;
			}
			
			CpUserExample ex = new CpUserExample();
			ex.createCriteria().andUserNameEqualTo(user.getUserName());// .andUserStatusNotEqualTo(2);
			List<CpUser> list = cpUserMapper.selectByExample(ex);
			if (list != null && list.size() > 0) {
				result.setCode(CommonConstant.REPEATCODE);
				result.setMsg(String.format("用户名为【%s】已存在！", user.getUserName()));
				return result;
			}
			// 校验身份证号
			if (user.getIdCard() != null) {
				Integer a = otherMapper.checkIdCard(user.getIdCard(), user.getId());
				if (a != null && a > 0) {
					throw new InvalidHttpArgumentException(CommonConstant.FAILURECODE, "身份证号已存在，请重新输入");
				}
			}
			// 前端传来的是单次md5加密后的密码，倒叙后再次加密存入数据库
			user.setPassword(Coder.encryptBASE64(Coder.reverse(user.getPassword())));
			if (user.getUserStatus() == null) {
				user.setUserStatus(0);
			}
			user.setSiteId(SessionUtils.getSiteId(request));
			user.setRegDate(new Date());
			user.setCreateUser(admin.getUserName());
			user.setCreateTime(new Date());
			userRoleRightService.addUserAndRole(user, roleIds, admin);
			result.setOther(
					String.format("新增用户名为【%s】,userId=%s;roleIds=%s", user.getUserName(), user.getId(), roleIds));
			result.setCode(CommonConstant.SUCCESSCODE);
			result.setMsg(CommonConstant.SUCCESSSTRING);
			result.setData(user);
		} catch (InvalidHttpArgumentException e) {
			e.printStackTrace();
			result.setCode(e.getCode());
			result.setMsg(e.getMsg());
		} catch (Exception e1) {
			e1.printStackTrace();
			log.error("管理员新建用户信息出错，" + e1.getMessage());
			result.setCode(CommonConstant.EXCEPTIONCODE);
			result.setMsg(CommonConstant.EXCEPTIONMSG);
		}
		return result;
	}

	// (用户审核（通过-已开通，不通过-已禁用）或敏感信息（审核通过、修改密码、充值、绑定手机、绑定邮箱、身份证号）修改，发送邮件)
	/**
	 * 审核用户
	 * 
	 * @param request
	 * @param type
	 *            0通过，1不通过
	 * @param userId
	 *            用户id
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/examUser")
	@LogInfo(content = "审核用户", opeType = 2, logTypeCode = CommonConstant.User, memo = "第一个参数表示0通过，1不通过")
	public Object examUser(HttpServletRequest request, String type, String userId) {
		ResponseMessage result = new ResponseMessage();
		try {
			CommonValidation.checkParamBlank(userId, "用户ID");
			CommonValidation.checkParamBlank(type, "类型");
			CpUser admin = SessionUtils.getUser(request);

			CpUser s = cpUserMapper.selectByPrimaryKey(Integer.valueOf(userId));
			if (s != null) {
				if (s.getUserStatus() == 1) {// 正申请时才可审核
					CpUser user = new CpUser();
					user.setId(Integer.valueOf(userId));
					String ope = null;
					if (type.equals("0")) {
						user.setUserStatus(0);// 已开通
						ope = "已开通";
					} else {
						user.setUserStatus(3);// 已禁用
						ope = "已禁用";
					}
					user.setUpdateUser(admin.getUserName());
					user.setUpdateTime(new Date());
					result.setCode(CommonConstant.SUCCESSCODE);
					result.setMsg(ope);
					int a = cpUserMapper.updateByPrimaryKeySelective(user);
					result.setOther(String.format("用户名【%s】,userId=%s", user.getUserName(), user.getId()));
					if (type.equals("0") && a > 0) {
						// TODO 发送通知
						// 获取通知用户方式：0短信，1邮件，2邮件和短信
						String notice = configService.getDbSysConfig(SysConfigConstant.DEFAULTNOTICE,
								SessionUtils.getSiteId(request));
						switch (notice) {
						case "0":
							// 短信通知
							// HttpServletRequest request, int[] uid, String
							// content,String title, int[] teamId, int status
							List<String> findEmail = configService.findEmail(SysConfigConstant.ExamUserTitle,
									SysConfigConstant.ExamUserContent);
							mailController.send(request, user.getId() + "", findEmail.get(1), findEmail.get(0), null,
									6);
							break;
						case "1": // int[] uid, String content,int [] teamId,int
									// status
							List<String> findphone = configService.findEmail("", SysConfigConstant.ExamUserContent);
							phoneMSGController.send(request, user.getId() + "", findphone.get(0) + findphone.get(1),
									null, 6);
							// 邮件通知
							break;
						case "2":
							List<String> findEmails = configService.findEmail(SysConfigConstant.ExamUserTitle,
									SysConfigConstant.ExamUserContent);
							mailController.send(request, user.getId() + "", findEmails.get(1), findEmails.get(0), null,
									6);
							List<String> findphones = configService.findEmail("", SysConfigConstant.ExamUserContent);
							phoneMSGController.send(request, user.getId() + "", findphones.get(1), null, 6);
							// 邮件和短信通知
							break;

						default:
							break;
						}
					}
				} else {
					result.setCode(CommonConstant.FAILURECODE);
					result.setMsg("当前用户不在正申请阶段，不可审核！");
					return result;
				}
			} else {
				result.setCode(CommonConstant.NULLCODE);
				result.setMsg(String.format("不存在ID为【%s】的用户 ！", Integer.valueOf(userId)));
				return result;
			}

		} catch (InvalidHttpArgumentException e) {
			e.printStackTrace();
			result.setCode(e.getCode());
			result.setMsg(e.getMsg());
		} catch (Exception e1) {
			e1.printStackTrace();
			log.error("审核用户出错，" + e1.getMessage());
			result.setCode(CommonConstant.EXCEPTIONCODE);
			result.setMsg(CommonConstant.EXCEPTIONMSG);
		}
		return result;
	}

	/**
	 * 更新用户信息
	 * 
	 * @param request
	 * @param user
	 *            用户信息（用户名不可修改）
	 * @param roleIds
	 *            角色ids（用户名不可修改）
	 */
	// 绑定手机、绑定邮箱、身份证号 修改，发送邮件
	@ResponseBody
	@RequestMapping("/updateUser")
	/*@LogInfo(content = "更新用户信息", opeType = 2, logTypeCode = CommonConstant.User)*/
	public Object updateUser(HttpServletRequest request, CpUser user, String roleIds,String userToken) {
		ResponseMessage result = new ResponseMessage();
		try {
			CommonValidation.checkParamBlank(user.getId() + "", "用户ID");
			CpUser cpUser = userRoleRightService.verify(userToken);
			CpUser admin = SessionUtils.getUser(request);
			if (cpUser ==null || (int)cpUser.getId() != (int)admin.getId()) {
				result.setCode(CommonConstant.FAILURECODE);
				result.setMsg("操作失败，请登录！");
				return result;
			}

			CpUser s = cpUserMapper.selectByPrimaryKey(user.getId());
			/*
			 * if (s.getSubscriberType()==null||s.getSubscriberType()!=1) { if (
			 * user.getIdCard()==null||!user.getIdCard().equals(s.getIdCard()))
			 * { result.setCode(CommonConstant.NULLCODE);
			 * result.setMsg("身份证号码有误"); return result; } }
			 */
			JSONObject jsonObject = new JSONObject();
			List<CpRole> userRole = basicMapper.selectUserRoleByUId(admin.getId());
			for (CpRole cpRole : userRole) {
				String key = cpRole.getId().toString();
				String value = cpRole.getRoleName();
				jsonObject.accumulate(key, value);
			}
			if (s != null) {
				if(jsonObject.containsKey("1")){//管理员权限才可以修改作者名
					user.setUserName(null);// 用户名不可修改
					user.setPassword(null);
					user.setUpdateUser(admin.getUserName());
					user.setUpdateTime(new Date());
				}else{
					user.setUserName(null);// 用户名不可修改
					user.setAuthorName(null);// 作者名不可修改
					user.setPassword(null);
					user.setUpdateUser(admin.getUserName());
					user.setUpdateTime(new Date());
				}
				
				// 校验身份证号
				/*
				 * if(user.getIdCard()!=null&&!user.getIdCard().equals(s.
				 * getIdCard())){ Integer
				 * a=otherMapper.checkIdCard(user.getIdCard(), user.getId());
				 * if(a!=null&&a>0){ throw new
				 * InvalidHttpArgumentException(CommonConstant.FAILURECODE,
				 * "身份证号已存在，请重新输入"); } }
				 */
				/*if(user.getUserStatus() != s.getUserStatus()){
					String ip =request.getHeader("X-Real-IP");
					int siteId = (Integer) SessionUtils.getSiteId(request);
					CpLog log = new CpLog();
					switch(user.getUserStatus()){
					case 0 : 
						log.setOpeContent("开通用户信息");
						break;
					case 1 : 
						log.setOpeContent("申请用户信息");
						break;
					case 2 : 
						log.setOpeContent("删除用户信息");
						break;
					case 3 : 
						log.setOpeContent("禁用用户信息");
						break;
					default:
						break;
					}
					log.setLogtypeId(3);
					log.setOpeIp(ip);
					log.setOpeIds("userName="+s.getUserName()+","+"userId="+s.getId().toString());
					log.setOpeUserId(admin.getId());
					log.setOpeTime(new Date());
					log.setOpeType(CommonConstant.BYTE2);
					log.setOpeParam("userName="+s.getUserName()+","+"userId="+s.getId().toString());
					log.setOpeUser(admin.getUserName());
					log.setOpeResult("操作成功");
					log.setSiteId(siteId);
					logService.addLog(log);
				}*/
				int a = userRoleRightService.updateUserAndRole(user, roleIds, admin);// 增加银行卡信息
				CpUserBank cpUserBank = new CpUserBank();
				cpUserBank.setBankAccount(user.getBankAccount());
				cpUserBank.setBankIdCard(user.getBankIdCard());
				cpUserBank.setBankName(user.getBankName());
				cpUserBank.setBankUsername(user.getBankUsername());
				cpUserBank.setUserId(user.getId());
				cpUserBank.setIsDefault(0);
				cpBankMapper.insertSelective(cpUserBank);
				result.setOther(
						String.format("用户名【%s】,userId=%s;roleIds=%s", user.getUserName(), user.getId(), roleIds));
				if (a > 0) {
					String content = "更新用户信息：";// 通知内容，待定
					boolean flag = false;
					String opeContent = "修改内容：";
					if(!s.getTureName().equals(user.getTureName())){
						content +="真实姓名为："+user.getTureName()+",";
						opeContent +="真实姓名,";
						flag = true;
					}
					if(!s.getAuthorName().equals(user.getAuthorName())){
						if(jsonObject.containsKey("1")){//管理员权限才可以修改作者名
							content +="作者名为："+user.getAuthorName()+",";
							flag = true;
							opeContent +="作者名,";
						}else{
							flag = false;
						}
					}
					if(!s.getIdCard().equals(user.getIdCard())){
						content +="身份证号为："+user.getIdCard()+",";
						opeContent +="身份证号,";
						flag = true;
					}
					if(s.getBankIdCard()!=null&&!s.getBankIdCard().equals(user.getBankIdCard())){
						content +="帐号或卡号为："+user.getBankIdCard()+",";
						opeContent +="帐号或卡号,";
						flag = true;
					}
					if(s.getMailAddress()!=null&&!s.getMailAddress().equals(user.getMailAddress())){
						content +="通信地址为："+user.getMailAddress()+",";
						opeContent +="通信地址,";
						flag = true;
					}
					if(s.getMailUsername()!=null&&!s.getMailUsername().equals(user.getMailUsername())){
						content +="收稿费姓名："+user.getMailUsername()+",";
						opeContent +="收稿费姓名,";
						flag = true;
					}
					if(s.getMailZipCode()!=null&&!s.getMailZipCode().equals(user.getMailZipCode())){
						content +="收稿费邮政编码："+user.getMailZipCode()+",";
						opeContent +="收稿费邮政编码,";
						flag = true;
					}
					CpLog log = new CpLog();
					String ip =request.getHeader("X-Real-IP");
					int siteId = (Integer) SessionUtils.getSiteId(request);	
					if(user.getUserStatus() != s.getUserStatus()){											
						switch(user.getUserStatus()){
						case 0 : 
							log.setOpeContent("开通用户信息");
							break;
						case 1 : 
							log.setOpeContent("申请用户信息");
							break;
						case 2 : 
							log.setOpeContent("删除用户信息");
							break;
						case 3 : 
							log.setOpeContent("禁用用户信息");
							break;
						default:
							break;
						}
						log.setLogtypeId(3);
						log.setOpeIp(ip);
						log.setOpeIds("userName="+s.getUserName()+","+"userId="+s.getId().toString());
						log.setOpeUserId(admin.getId());
						log.setOpeTime(new Date());
						log.setOpeType(CommonConstant.BYTE2);
						log.setOpeParam("userName="+s.getUserName()+","+"userId="+s.getId().toString());
						log.setOpeUser(admin.getUserName());
						log.setOpeResult("操作成功");
						log.setSiteId(siteId);
						logService.addLog(log);
					}else{
						log.setOpeContent(opeContent);
						log.setLogtypeId(3);
						log.setOpeIp(ip);
						log.setOpeIds("userName="+s.getUserName()+","+"userId="+s.getId().toString());
						log.setOpeUserId(admin.getId());
						log.setOpeTime(new Date());
						log.setOpeType(CommonConstant.BYTE2);
						log.setOpeParam("userName="+s.getUserName()+","+"userId="+s.getId().toString());
						log.setOpeUser(admin.getUserName());
						log.setOpeResult("操作成功");
						log.setSiteId(siteId);
						logService.addLog(log);
					}
					JSONObject json = new JSONObject();
					List<CpRole> Role = basicMapper.selectUserRoleByUId(user.getId());
					for (CpRole cpRole : Role) {
						String key = cpRole.getId().toString();
						String value = cpRole.getRoleName();
						json.accumulate(key, value);
					}
					//被修改的用户具有值班编辑和管理员时不发送邮件
					if(json.containsKey("1")){
						flag = false;
					}else if(json.containsKey("2")){
						flag = false;
					}
					if (flag) {
						// TODO 发送通知
						// 获取通知用户方式：0短信，1邮件，2邮件和短信
						String notice = configService.getDbSysConfig(SysConfigConstant.DEFAULTNOTICE,
								SessionUtils.getSiteId(request));
						switch (notice) {
						case "0":
							// 短信通知
							List<String> findEmail = configService.findEmail(SysConfigConstant.UpdateUserTitle,
									SysConfigConstant.UpdateUserContent);
							mailController.send(request, user.getId() + "", content, findEmail.get(0), null,
									6);
							stationMessiageController.send(request, s.getUserName() + findEmail.get(1), "2", null,
									s.getUserName() + findEmail.get(0));
							break;
						case "1":
							// 邮件通知
							List<String> findphone = configService.findEmail("", SysConfigConstant.UpdateUserContent);
							phoneMSGController.send(request, user.getId() + "", findphone.get(0) + findphone.get(1),
									null, 6);
							stationMessiageController.send(request, s.getUserName() + findphone.get(1), "2", null,
									s.getUserName() + findphone.get(0));
							break;
						case "2":
							// 邮件和短信通知
							List<String> findEmails = configService.findEmail(SysConfigConstant.UpdateUserTitle,
									SysConfigConstant.UpdateUserContent);
							mailController.send(request, user.getId() + "", findEmails.get(1), findEmails.get(0), null,
									6);
							List<String> findphones = configService.findEmail("", SysConfigConstant.UpdateUserContent);
							phoneMSGController.send(request, user.getId() + "", findphones.get(1), null, 6);
							stationMessiageController.send(request, s.getUserName() + findEmails.get(1), "2", null,
									s.getUserName() + findEmails.get(0));
							break;

						default:
							break;
						}
					}
				}
			} else {
				result.setCode(CommonConstant.NULLCODE);
				result.setMsg(String.format("不存在ID为【%s】的用户 ！", user.getId()));
				return result;
			}
			result.setCode(CommonConstant.SUCCESSCODE);
			result.setMsg(CommonConstant.SUCCESSSTRING);
		} catch (InvalidHttpArgumentException e) {
			e.printStackTrace();
			result.setCode(e.getCode());
			result.setMsg(e.getMsg());
		} catch (Exception e1) {
			e1.printStackTrace();
			log.error("更新用户信息出错，" + e1.getMessage());
			result.setCode(CommonConstant.EXCEPTIONCODE);
			result.setMsg(CommonConstant.EXCEPTIONMSG);
		}
		return result;
	}

	/**
	 * 为用户分配或修改权限
	 * 
	 * @param request
	 * @param userId
	 *            用户ID
	 * @param rightIds
	 *            用户分配权限ID，多个用英文逗号隔开
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/authUser")
	@LogInfo(content = "为用户分配或修改权限", opeType = 2, logTypeCode = CommonConstant.User)
	public Object authUser(HttpServletRequest request, String userId, String rightIds) {
		ResponseMessage result = new ResponseMessage();
		try {
			CommonValidation.checkParamBlank(userId, "用户ID");
			CommonValidation.checkParamBlank(rightIds, "权限ID");
			CpUser admin = SessionUtils.getUser(request);

			userRoleRightService.authUserRight(Integer.valueOf(userId), rightIds, admin);
			result.setOther(String.format("userId=%s,rightIds=%s", userId, rightIds));
			result.setCode(CommonConstant.SUCCESSCODE);
			result.setMsg(CommonConstant.SUCCESSSTRING);
		} catch (InvalidHttpArgumentException e) {
			e.printStackTrace();
			result.setCode(e.getCode());
			result.setMsg(e.getMsg());
		} catch (Exception e1) {
			e1.printStackTrace();
			log.error("为用户分配或修改权限出错，" + e1.getMessage());
			result.setCode(CommonConstant.EXCEPTIONCODE);
			result.setMsg(CommonConstant.EXCEPTIONMSG);
		}
		return result;
	}

	/**
	 * 修改用户密码
	 * 
	 * @param request
	 * @param password
	 *            修改后的密码
	 * @param userId
	 *            用户id
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/updatePassword")
	@LogInfo(content = "修改用户密码", opeType = 2, logTypeCode = CommonConstant.User)
	public Object updatePassword(HttpServletRequest request, String password, String userId,String userToken) {
		ResponseMessage result = new ResponseMessage();
		try {
			CommonValidation.checkParamBlank(userId, "用户ID");
			CommonValidation.checkParamBlank(password, "密码");
			CpUser cpUser = userRoleRightService.verify(userToken);
			CpUser admin = SessionUtils.getUser(request);
			if (cpUser ==null || (int)cpUser.getId() != (int)admin.getId()) {
				result.setCode(CommonConstant.FAILURECODE);
				result.setMsg("操作失败，请登录！");
				return result;
			}

			CpUser s = cpUserMapper.selectByPrimaryKey(Integer.valueOf(userId));
			if (s != null) {
				CpUser user = new CpUser();
				user.setId(Integer.valueOf(userId));
				user.setPassword(Coder.encryptBASE64(Coder.reverse(password)));
				user.setUpdateUser(admin.getUserName());
				user.setUpdateTime(new Date());
				int a = cpUserMapper.updateByPrimaryKeySelective(user);
				result.setOther(String.format("用户名【%s】,userId=%s", s.getUserName(), s.getId()));
				if (a > 0) {
					// TODO 发送通知
					// 获取通知用户方式：0短信，1邮件，2邮件和短信
					String notice = configService.getDbSysConfig(SysConfigConstant.DEFAULTNOTICE,
							SessionUtils.getSiteId(request));
					switch (notice) {
					case "0":
						// 短信通知
						List<String> findEmail = configService.findEmail(SysConfigConstant.UpdatePasswordTitle,
								SysConfigConstant.UpdatePasswordContent);
						mailController.send(request, user.getId() + "", findEmail.get(1), findEmail.get(0), null, 6);
						break;
					case "1":
						// 邮件通知
						List<String> findphone = configService.findEmail("", SysConfigConstant.UpdatePasswordContent);
						phoneMSGController.send(request, user.getId() + "", findphone.get(0) + findphone.get(1), null,
								6);
						break;
					case "2":
						// 邮件和短信通知
						List<String> findEmails = configService.findEmail(SysConfigConstant.UpdatePasswordTitle,
								SysConfigConstant.UpdatePasswordContent);
						mailController.send(request, user.getId() + "", findEmails.get(1), findEmails.get(0), null, 6);
						List<String> findphones = configService.findEmail("", SysConfigConstant.UpdatePasswordContent);
						phoneMSGController.send(request, user.getId() + "", findphones.get(1), null, 6);
						break;

					default:
						break;
					}
				}
			} else {
				result.setCode(CommonConstant.NULLCODE);
				result.setMsg(String.format("不存在ID为【%s】的用户 ！", Integer.valueOf(userId)));
				return result;
			}
			result.setCode(CommonConstant.SUCCESSCODE);
			result.setMsg(CommonConstant.SUCCESSSTRING);
		} catch (InvalidHttpArgumentException e) {
			e.printStackTrace();
			result.setCode(e.getCode());
			result.setMsg(e.getMsg());
		} catch (Exception e1) {
			e1.printStackTrace();
			log.error("修改用户密码出错，" + e1.getMessage());
			result.setCode(CommonConstant.EXCEPTIONCODE);
			result.setMsg(CommonConstant.EXCEPTIONMSG);
		}
		return result;
	}

	/**
	 * 为用户充值
	 * 
	 * @param request
	 * @param cash
	 *            重置金额
	 * @param userId
	 *            用户id
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/rechargeUser")
	@LogInfo(content = "为用户充值", opeType = 2, logTypeCode = CommonConstant.User)
	public Object rechargeUser(HttpServletRequest request, Integer cash, String userId,String userToken) {
		ResponseMessage result = new ResponseMessage();
		try {
			CommonValidation.checkParamBlank(userId, "用户ID");
			CommonValidation.checkParamBlank(cash + "", "充值额");
			CpUser cpUser = userRoleRightService.verify(userToken);
			CpUser admin = SessionUtils.getUser(request);
			if (cpUser ==null || (int)cpUser.getId() != (int)admin.getId()) {
				result.setCode(CommonConstant.FAILURECODE);
				result.setMsg("操作失败，请登录！");
				return result;
			}

			CpUser s = cpUserMapper.selectByPrimaryKey(Integer.valueOf(userId));
			if (s != null) {
				BigDecimal account = s.getAccount();// 当前余额
				account = account == null ? new BigDecimal(0.00) : account;
				CpUser user = new CpUser();
				user.setId(Integer.valueOf(userId));
				// 需要重新计算 账户余额ACCOUNT
				// BigDecimal now=account.add(new
				// BigDecimal(Double.valueOf(cash)));
				BigDecimal now = MathUtil.add(account, new BigDecimal(cash));
				user.setAccount(now);
				/*
				 * if(StringUtil.isNotBlank(user.getPerprice()+"")&&StringUtil.
				 * isNumeric(user.getPerprice()+"")){//用户价格存在时 //修改用户余额内限制数量 int
				 * balanceNum=now.divide(now,user.getPerprice(),BigDecimal.
				 * ROUND_HALF_UP).intValue(); user.setBalanceNum(balanceNum); }
				 */
				user.setUpdateUser(admin.getUserName());
				user.setUpdateTime(new Date());
				int a = cpUserMapper.updateByPrimaryKeySelective(user);
				result.setOther(String.format("用户名【%s】,userId=%s", user.getUserName(), user.getId()));
				if (a > 0) {
					// TODO 发送通知
					// 获取通知用户方式：0短信，1邮件，2邮件和短信
					String notice = configService.getDbSysConfig(SysConfigConstant.DEFAULTNOTICE,
							SessionUtils.getSiteId(request));
					switch (notice) {
					case "0":
						// 短信通知
						List<String> findEmail = configService.findEmail(SysConfigConstant.RechargeUserTitle,
								SysConfigConstant.RechargeUserContent);
						mailController.send(request, user.getId() + "", findEmail.get(1), findEmail.get(0), null, 6);
						break;
					case "1":
						// 邮件通知
						List<String> findphone = configService.findEmail("", SysConfigConstant.RechargeUserContent);
						phoneMSGController.send(request, user.getId() + "", findphone.get(0) + findphone.get(1), null,
								6);
						break;
					case "2":
						// 邮件和短信通知
						List<String> findEmails = configService.findEmail(SysConfigConstant.RechargeUserTitle,
								SysConfigConstant.RechargeUserContent);
						mailController.send(request, user.getId() + "", findEmails.get(1), findEmails.get(0), null, 6);
						List<String> findphones = configService.findEmail("", SysConfigConstant.RechargeUserContent);
						phoneMSGController.send(request, user.getId() + "", findphones.get(1), null, 6);
						break;
					default:
						break;
					}
				}
			} else {
				result.setCode(CommonConstant.NULLCODE);
				result.setMsg(String.format("不存在ID为【%s】的用户 ！", Integer.valueOf(userId)));
				return result;
			}
			result.setCode(CommonConstant.SUCCESSCODE);
			result.setMsg(CommonConstant.SUCCESSSTRING);
		} catch (InvalidHttpArgumentException e) {
			e.printStackTrace();
			result.setCode(e.getCode());
			result.setMsg(e.getMsg());
		} catch (Exception e1) {
			e1.printStackTrace();
			log.error("为用户充值出错，" + e1.getMessage());
			result.setCode(CommonConstant.EXCEPTIONCODE);
			result.setMsg(CommonConstant.EXCEPTIONMSG);
		}
		return result;
	}

	/**
	 * 禁用或解禁用户
	 * 
	 * @param request
	 * @param type
	 *            0解禁，1禁用
	 * @param userId
	 *            用户id
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/lockOrOpenUser")
	@LogInfo(content = "禁用或解禁用户", opeType = 2, logTypeCode = CommonConstant.User, memo = "第一个参数表示0解禁，1禁用")
	public Object lockOrOpenUser(HttpServletRequest request, String type, String userId,String userToken) {
		ResponseMessage result = new ResponseMessage();
		try {
			CommonValidation.checkParamBlank(userId, "用户ID");
			CommonValidation.checkParamBlank(type, "类型");
			CpUser cpUser = userRoleRightService.verify(userToken);
			CpUser admin = SessionUtils.getUser(request);
			if (cpUser ==null || (int)cpUser.getId() != (int)admin.getId()) {
				result.setCode(CommonConstant.FAILURECODE);
				result.setMsg("操作失败，请登录！");
				return result;
			}

			String typeS = "";
			CpUser s = cpUserMapper.selectByPrimaryKey(Integer.valueOf(userId));
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("userName", s.getUserName());
			basicMapper.lockTimeClear(param);
			/*
			 * if(s!=null){ // 0已开通，1正申请，2已删除，3已禁用 答：已开通和正申请可以禁用，解禁之后变成已开通
			 * if(type.equals("0")){
			 * if(s.getUserStatus()==0||s.getUserStatus()==1){ CpUser user=new
			 * CpUser(); user.setId(Integer.valueOf(userId));
			 * user.setUserStatus(3); user.setUpdateUser(admin.getUserName());
			 * user.setUpdateTime(new Date());
			 * cpUserMapper.updateByPrimaryKeySelective(user); typeS="禁用";
			 * result.setOther(String.format("用户名【%s】,userId=%s",user.
			 * getUserName(),user.getId())); }else{
			 * result.setCode(CommonConstant.FAILURECODE);
			 * result.setMsg("当前用户不可禁用！"); return result; } }else
			 * if(type.equals("1")){ if(s.getUserStatus()==3){ CpUser user=new
			 * CpUser(); user.setId(Integer.valueOf(userId));
			 * user.setUserStatus(0); user.setUpdateUser(admin.getUserName());
			 * user.setUpdateTime(new Date()); //
			 * cpUserMapper.updateByPrimaryKeySelective(user); Map<String,
			 * Object> param = new HashMap<String, Object>();
			 * param.put("userName", admin.getUserName());
			 * basicMapper.lockTimeClear(param); typeS="解禁";
			 * result.setOther(String.format("用户名【%s】,userId=%s",user.
			 * getUserName(),user.getId())); }else{
			 * result.setCode(CommonConstant.FAILURECODE);
			 * result.setMsg("当前用户不可解禁！"); return result; } }else{
			 * result.setCode(CommonConstant.PARAMERROR);
			 * result.setMsg("type类型错误"); return result; } }else{
			 * result.setCode(CommonConstant.NULLCODE);
			 * result.setMsg(String.format("不存在ID为【%s】的用户 ！"
			 * ,Integer.valueOf(userId))); return result; }
			 */
			result.setCode(CommonConstant.SUCCESSCODE);
			result.setMsg(typeS + CommonConstant.SUCCESSSTRING);
		} catch (InvalidHttpArgumentException e) {
			e.printStackTrace();
			result.setCode(e.getCode());
			result.setMsg(e.getMsg());
		} catch (Exception e1) {
			e1.printStackTrace();
			log.error("解禁用户出错，" + e1.getMessage());
			result.setCode(CommonConstant.EXCEPTIONCODE);
			result.setMsg(CommonConstant.EXCEPTIONMSG);
		}
		return result;
	}

	/**
	 * 修改用户下载管理
	 * 
	 * @param request
	 * @param isDownload
	 *            是否允许下载；0：不能下载，1：允许下载
	 * @param downloadType
	 *            用户下载方式(也是用户结算方式)：0充值(余额结算)，1合同(张数结算)，2充值与合同(余额和张数结算)`
	 *            DOWNLOAD_TYPE 余额
	 * @param balancePerprice
	 *            用户余额协议价格（普通图下载价格）; 单价，单位元 BALANCE_PERPRICE
	 * @param balanceBasePerprice
	 *            余额-普通图-分成基价; 单价，单位元 BALANCE_BASE_PERPRICE
	 * @param balanceSale
	 *            余额-特殊图打折系数 BALANCE_SALE
	 * @param balanceRevenue
	 *            余额-税收系数 BALANCE_REVENUE
	 * @param balanceLimitType
	 *            余额下载限制类型：0每天，1每月，2每年 BALANCE_LIMIT_TYPE
	 * @param balanceNum
	 *            余额下载限制数量 BALANCE_NUM 合同
	 * @param startTime
	 *            合同期开始时间 CONTRACT_START_TIME
	 * @param endTime
	 *            合同期结束时间 CONTRACT_END_TIME
	 * @param contractPerprice
	 *            用户合同协议价格(无用); 单价，单位元 CONTRACT_PERPRICE
	 * @param contractBasePerprice
	 *            合同-分成基价; 单价，单位元 CONTRACT_BASE_PERPRICE
	 * @param contractBuyNum
	 *            购买数量 当前合同期内购买数量-99不限制 （合同期内当前限制数量，随着用户下载量减少） CONTRACT_BUY_NUM
	 * @param contractLimitType
	 *            合同下载限制类型：0每天，1每月，2每年 CONTRACT_LIMIT_TYPE
	 * @param contractLimitNum
	 *            下载限制数量；CONTRACT_LIMIT_NUM 合同和余额
	 * @param threeLimitType
	 *            合同和余额下载限制类型：0每天，1每月，2每年 THREE_LIMIT_TYPE
	 * @param threeLimitNum
	 *            合同和余额下载限制数量；THREE_LIMIT_NUM
	 * @param Id
	 *            用户id
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/updateUserDownload")
	@LogInfo(content = "修改用户下载管理", opeType = 2, logTypeCode = CommonConstant.User)
	public Object updateUserDownload(HttpServletRequest request, CpUser user, String startTime, String endTime) {
		ResponseMessage result = new ResponseMessage();
		try {
			CommonValidation.checkParamBlank(user.getId() + "", "用户ID");
			CommonValidation.checkParamBlank(user.getDownloadType() + "", "下载方式");

			boolean flag = false;
			if (user.getContractBuyNum() != null && StringUtil.isNotBlank(startTime)
					&& StringUtil.isNotBlank(endTime)) {
				flag = true;
			}
			if (user.getContractBuyNum() == null && StringUtil.isBlank(startTime) && StringUtil.isBlank(endTime)) {
				flag = true;
			}

			if (!flag) {
				result.setCode(CommonConstant.PARAMERROR);
				result.setMsg("购买数量和合同期限应同时存在或为空");
				return result;
			}
			if (user.getDownloadType() == 1 || user.getDownloadType() == 2) {
				if (StringUtil.isNotBlank(startTime)) {
					user.setContractStartTime(
							DateUtils.fromStringToDate(DateUtils.sdfLongTimePlus, startTime + " 00:00:00"));
				} else {
					user.setContractStartTime(null);
				}
				if (StringUtil.isNotBlank(endTime)) {
					Date a = DateUtils.fromStringToDate(DateUtils.sdfLongTimePlus, endTime + " 23:59:59");
					user.setContractEndTime(a);
				} else {
					user.setContractEndTime(null);
				}
			}

			CpUser admin = SessionUtils.getUser(request);
			CpUser oldUser = cpUserMapper.selectByPrimaryKey(user.getId());
			if (oldUser == null) {
				result.setCode(CommonConstant.NULLCODE);
				result.setMsg(String.format("不存在ID为【%s】的用户 ！", user.getId()));
				return result;
			}
			user.setUpdateUser(admin.getUserName());
			user.setUpdateTime(new Date());
			/*
			 * if(user.getPerprice()!=null){//修改用户价格时，同时修改余额内限制数量 BigDecimal
			 * now=oldUser.getAccount();
			 * if(now!=null&&now.compareTo(CommonConstant.BIGDECIMAL0)>0){//余额>0
			 * //修改用户余额内限制数量 int
			 * balanceNum=now.divide(now,user.getPerprice(),BigDecimal.
			 * ROUND_HALF_UP).intValue(); user.setBalanceNum(balanceNum); } }
			 */
			cpUserMapper.updateByPrimaryKeySelective(user);
			result.setOther(String.format("用户名【%s】,userId=%s", oldUser.getUserName(), oldUser.getId()));
			result.setCode(CommonConstant.SUCCESSCODE);
			result.setMsg(CommonConstant.SUCCESSSTRING);
		} catch (InvalidHttpArgumentException e) {
			e.printStackTrace();
			result.setCode(e.getCode());
			result.setMsg(e.getMsg());
		} catch (Exception e1) {
			e1.printStackTrace();
			log.error("修改用户下载管理出错，" + e1.getMessage());
			result.setCode(CommonConstant.EXCEPTIONCODE);
			result.setMsg(CommonConstant.EXCEPTIONMSG);
		}
		return result;
	}

	/**
	 * 删除用户信息
	 * 
	 * @param userId
	 *            用户ID，多个用英文逗号隔开
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/delUserByIds")
	@LogInfo(content = "删除用户", opeType = 1, logTypeCode = CommonConstant.User)
	public Object delUserByIds(HttpServletRequest request, String userIds,String userToken) {
		ResponseMessage result = new ResponseMessage();
		try {
			CommonValidation.checkParamBlank(userIds, "用户id");
			
			CpUser cpUser = userRoleRightService.verify(userToken);
			CpUser admin = SessionUtils.getUser(request);
			if (cpUser ==null || (int)cpUser.getId() != (int)admin.getId()) {
				result.setCode(CommonConstant.FAILURECODE);
				result.setMsg("操作失败，请登录！");
				return result;
			}
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("username", admin.getUserName());
			param.put("updatetime", new Date());
			param.put("ids", userIds.split(","));
			basicMapper.delUserByIds(param);
			result.setOther(String.format("userIds=%s", userIds));
			result.setCode(CommonConstant.SUCCESSCODE);
			result.setMsg(CommonConstant.SUCCESSSTRING);
		} catch (InvalidHttpArgumentException e) {
			e.printStackTrace();
			result.setCode(e.getCode());
			result.setMsg(e.getMsg());
		} catch (Exception e1) {
			e1.printStackTrace();
			log.error("删除用户信息出错，" + e1.getMessage());
			result.setCode(CommonConstant.EXCEPTIONCODE);
			result.setMsg(CommonConstant.EXCEPTIONMSG);
		}
		return result;
	}

	@ResponseBody
	@RequestMapping("/removeUser")
	@LogInfo(content = "彻底删除用户", opeType = 1, logTypeCode = CommonConstant.User)
	public Object removeUser(HttpServletRequest request, String userIds,String userToken) {
		ResponseMessage result = new ResponseMessage();
		try {
			CommonValidation.checkParamBlank(userIds, "用户id");
			CpUser cpUser = userRoleRightService.verify(userToken);
			CpUser admin = SessionUtils.getUser(request);
			if (cpUser ==null || (int)cpUser.getId() != (int)admin.getId()) {
				result.setCode(CommonConstant.FAILURECODE);
				result.setMsg("删除失败，请重新登录！");
				return result;
			}
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("username", admin.getUserName());
			param.put("updatetime", new Date());
			param.put("ids", userIds.split(","));
			basicMapper.delUserByIds(param);
			basicMapper.insOther(param);
			basicMapper.removeUser(param);
			result.setOther(String.format("userIds=%s", userIds));
			result.setCode(CommonConstant.SUCCESSCODE);
			result.setMsg(CommonConstant.SUCCESSSTRING);
		} catch (InvalidHttpArgumentException e) {
			e.printStackTrace();
			result.setCode(e.getCode());
			result.setMsg(e.getMsg());
		} catch (Exception e1) {
			e1.printStackTrace();
			log.error("删除用户信息出错，" + e1.getMessage());
			result.setCode(CommonConstant.EXCEPTIONCODE);
			result.setMsg(CommonConstant.EXCEPTIONMSG);
		}
		return result;
	}

	/**
	 * 查询摄影师
	 * 
	 * @param request
	 * @param uName
	 *            搜索条件
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/getPhotoUser")
	public Object getPhotoUser(HttpServletRequest request, String uName) {
		ResponseMessage result = new ResponseMessage();
		try {
			List<Map<String, Object>> list = otherMapper.selectPhotoUser(uName);
			result.setCode(CommonConstant.SUCCESSCODE);
			result.setMsg(CommonConstant.SUCCESSSTRING);
			result.setData(list);
		} catch (InvalidHttpArgumentException e) {
			e.printStackTrace();
			result.setCode(e.getCode());
			result.setMsg(e.getMsg());
		} catch (Exception e1) {
			e1.printStackTrace();
			log.error("查询查询摄影师出错，" + e1.getMessage());
			result.setCode(CommonConstant.EXCEPTIONCODE);
			result.setMsg(CommonConstant.EXCEPTIONMSG);
		}
		return result;
	}
	
	
	
	
	/**
	 * 查询签约摄影家
	 * 
	 * @param request
	 * @param uName
	 *            搜索条件
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/getPhotographerList")
	@SkipAuthCheck
	@SkipLoginCheck
	public Object getPhotographerList(HttpServletRequest request) {
		ResponseMessage result = new ResponseMessage();
		try {
			PageHelper.startPage(request);
			List<Map<String, Object>> list = otherMapper.selectPhotographer();
			PageHelper.addPagesAndTotal(result, list);
			result.setCode(CommonConstant.SUCCESSCODE);
			result.setMsg(CommonConstant.SUCCESSSTRING);
//			result.setPage(list.size());
			result.setData(list);
		} catch (InvalidHttpArgumentException e) {
			e.printStackTrace();
			result.setCode(e.getCode());
			result.setMsg(e.getMsg());
		} catch (Exception e1) {
			e1.printStackTrace();
			log.error("查询签约摄影师出错，" + e1.getMessage());
			result.setCode(CommonConstant.EXCEPTIONCODE);
			result.setMsg(CommonConstant.EXCEPTIONMSG);
		}
		return result;
	}
	
	/**
	 * 查询签约摄影家
	 * 
	 * @param request
	 * @param uName
	 *            搜索条件
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/getArtistList")
	@SkipAuthCheck
	@SkipLoginCheck
	public Object getArtistList(HttpServletRequest request) {
		ResponseMessage result = new ResponseMessage();
		try {
			PageHelper.startPage(request);
			List<Map<String, Object>> list = otherMapper.selectArtist();
			PageHelper.addPagesAndTotal(result, list);
			result.setCode(CommonConstant.SUCCESSCODE);
			result.setMsg(CommonConstant.SUCCESSSTRING);
//			result.setPage(list.size());
			result.setData(list);
		} catch (InvalidHttpArgumentException e) {
			e.printStackTrace();
			result.setCode(e.getCode());
			result.setMsg(e.getMsg());
		} catch (Exception e1) {
			e1.printStackTrace();
			log.error("查询签约摄影师出错，" + e1.getMessage());
			result.setCode(CommonConstant.EXCEPTIONCODE);
			result.setMsg(CommonConstant.EXCEPTIONMSG);
		}
		return result;
	}
	
	
	/**
	 * 查询用户详情
	 * 
	 * @param request
	 * @param uName
	 *            搜索条件
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/getPhotographerByUserId")
	@SkipAuthCheck
	@SkipLoginCheck
	public Object getPhotographerByUserId(HttpServletRequest request,Integer userId) {
		ResponseMessage result = new ResponseMessage();
		try {
			Map<String,Object> map = otherMapper.selectPhotographerByUserId(userId);
			result.setCode(CommonConstant.SUCCESSCODE);
			result.setMsg(CommonConstant.SUCCESSSTRING);
			result.setData(map);
		} catch (InvalidHttpArgumentException e) {
			e.printStackTrace();
			result.setCode(e.getCode());
			result.setMsg(e.getMsg());
		} catch (Exception e1) {
			e1.printStackTrace();
			log.error("查询用户信息出错，" + e1.getMessage());
			result.setCode(CommonConstant.EXCEPTIONCODE);
			result.setMsg(CommonConstant.EXCEPTIONMSG);
		}
		return result;
	}
	
	

	/**
	 * 导出用户信息
	 * 
	 * @param request
	 * @param cpuser
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/downLoadUserInfo")
	public Object downLoadUserinfo(HttpServletRequest request, HttpServletResponse response, String userName,
			String userId, String roleId, String userStatus, String direction, String zone, String province,
			String langType, String city, Integer telContact, String emailBind, String telBind, String standBy1,
			String standBy2, String trueName, String subScriberType, String userType, String unitFlax, String unitTel,
			String authorName, String address, String unitName, String unitAddress, String mailAddress,
			String unitUserName, String mailIdCard, String feeType, String zipCode, String bankAccount,
			String bankUserName, String bankIdCard, String bankName, String memo, String parameter, String orderTime,
			Integer page, Integer rows, String userInfo, Integer groupId) {

		ResponseMessage result = new ResponseMessage();
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		try {
			JSONObject jsonObject = JSONObject.fromObject(userInfo);
			SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss"); // 定义文件名格式
			// List<CpUser> userList = basicMapper.selectUser();
			List<Map<String, Object>> userList = null;
			if (groupId != null) {
				userList = enGroupManagementService.getGroupManagementUser(groupId, userName);// 获取群组的用户的信息
			} else {
				userList = getAdvancedSearchData(request, userName, userId, roleId, userStatus, direction, zone,
						province, langType, city, telContact, emailBind, telBind, standBy1, standBy2, trueName,
						subScriberType, userType, unitFlax, unitTel, authorName, address, unitName, unitAddress,
						mailAddress, unitUserName, mailIdCard, feeType, zipCode, bankAccount, bankUserName, bankIdCard,
						bankName, memo, parameter, orderTime, page, rows, 0);
			}
			HSSFWorkbook hssfWorkbook = exportUserInfo.exportUser(jsonObject, userList);// 生成表格
			String msg = new String(("用户信息_" + format.format(new Date()) + ".xls").getBytes(), "ISO-8859-1");
			// 以导出时间作为文件名
			response.setContentType("application/vnd.ms-excel");
			response.addHeader("Content-Disposition", "attachment;filename=" + msg);
			hssfWorkbook.write(os);
			hssfWorkbook.write(response.getOutputStream());
			result.setCode(CommonConstant.SUCCESSCODE);
			result.setMsg(CommonConstant.SUCCESSSTRING);
		} catch (Exception e) {
			e.printStackTrace();
			log.error("导出用户信息失败，" + e.getMessage());
			result.setCode(CommonConstant.EXCEPTIONCODE);
			result.setMsg(CommonConstant.EXCEPTIONMSG);
		}
		return result;
	}
	
	/**
	 * @author xiayunan
	 * @date 2018年4月18日
	 * @description 查询用户是否具有某一角色
	 * @param request
	 * @param userId
	 * @param roleId
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/checkUserRole")
	@SkipAuthCheck
	@SkipLoginCheck
	public Object checkUserRole(HttpServletRequest request,String roleId) {
		ResponseMessage result = new ResponseMessage();
		try {
			CommonValidation.checkParamBlank(roleId, "角色Id");
			Map<String, Object> param = new HashMap<String, Object>();
			CpUser cpUser = (CpUser)SessionUtils.getUser(request);
			if(cpUser==null){
				result.setCode(CommonConstant.NOTLOGINCODE);
				result.setMsg(CommonConstant.NOTLOGINMSG);
				result.setData(false);
				return result;
			}
			param.put("userId",cpUser.getId());
			param.put("roleId", roleId);
			CpUserRole cpUserRole = cpUserRoleMapper.findUserRole(param);
			if(cpUserRole!=null){
				result.setData(true);
			}else{
				result.setData(false);
			}
			result.setCode(CommonConstant.SUCCESSCODE);
			result.setMsg(CommonConstant.SUCCESSSTRING);
			
		} catch (InvalidHttpArgumentException e) {
			e.printStackTrace();
			result.setCode(e.getCode());
			result.setMsg(e.getMsg());
		} catch (Exception e1) {
			e1.printStackTrace();
			log.error("查询用户角色出错，" + e1.getMessage());
			result.setCode(CommonConstant.EXCEPTIONCODE);
			result.setMsg(CommonConstant.EXCEPTIONMSG);
		}
		return result;
	}
	
}