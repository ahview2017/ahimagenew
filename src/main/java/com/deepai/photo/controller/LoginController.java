package com.deepai.photo.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.UUID;

import javax.mail.Address;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.httpclient.NameValuePair;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.deepai.photo.bean.CpEmail;
import com.deepai.photo.bean.CpLog;
import com.deepai.photo.bean.CpPicAllpath;
import com.deepai.photo.bean.CpPicAllpathExample;
import com.deepai.photo.bean.CpRole;
import com.deepai.photo.bean.CpUser;
import com.deepai.photo.bean.CpUserExample;
import com.deepai.photo.common.annotation.LogInfo;
import com.deepai.photo.common.annotation.SkipAuthCheck;
import com.deepai.photo.common.annotation.SkipLoginCheck;
import com.deepai.photo.common.constant.CommonConstant;
import com.deepai.photo.common.constant.SysConfigConstant;
import com.deepai.photo.common.exception.InvalidHttpArgumentException;
import com.deepai.photo.common.pojo.ResponseMessage;
import com.deepai.photo.common.redis.RedisClientTemplate;
import com.deepai.photo.common.util.SessionUtils;
import com.deepai.photo.common.util.encrypt.Coder;
import com.deepai.photo.common.util.encrypt.MD5Util2;
import com.deepai.photo.common.util.json.GsonUtil;
import com.deepai.photo.common.util.json.JsonUtil;
import com.deepai.photo.common.validation.CommonValidation;
import com.deepai.photo.controller.email.MailController;
import com.deepai.photo.controller.phonemsg.PhoneMSGController;
import com.deepai.photo.controller.util.UserUtils;
import com.deepai.photo.mapper.ClientPictureMapper;
import com.deepai.photo.mapper.CpBasicMapper;
import com.deepai.photo.mapper.CpPicAllpathMapper;
import com.deepai.photo.mapper.CpUserMapper;
import com.deepai.photo.service.admin.LogService;
import com.deepai.photo.service.admin.SysConfigService;
import com.deepai.photo.service.admin.UserRoleRightService;
import com.deepai.photo.service.mail.MailService;

/**
 * @author guoyanhong 用户登录注册
 *
 */
@Controller
@RequestMapping("/login")
public class LoginController {
	private Logger log = Logger.getLogger(LoginController.class);

	@Autowired
	private CpUserMapper cpUserMapper;
	@Autowired
	private CpBasicMapper basicMapper;
	@Autowired
	private RedisClientTemplate redisClientTemplate;
	@Autowired
	private LogService logService;
	@Autowired
	private UserRoleRightService userRoleRightService;
	@Autowired
	private MailController mailController;
	@Autowired
	private PhoneMSGController phoneMSGController;
	@Autowired
	private SysConfigService sysConfigService;
	@Autowired
	private ClientPictureMapper clientPictureMapper;
	@Autowired
	private CpPicAllpathMapper CpPicAllpathMapper;
	@Autowired
	private MailService mailService;
	@Autowired
	private SysConfigService configService;
	/**
	 * 
	 * 获取salt
	 */
	@ResponseBody
	@RequestMapping("/getSalt")
	@SkipLoginCheck
	@SkipAuthCheck
	public Object getSalt(HttpServletRequest request, String userName) {
		ResponseMessage res = new ResponseMessage();
		try {
			CommonValidation.checkParamBlank(userName, "用户名");
			/*
			 * if(redisClientTemplate.exists(CommonConstant.USERSALT+userName)){
			 * res.setCode(CommonConstant.FAILURECODE);
			 * res.setMsg("该用户正在登录中，请稍后"); return res; }
			 */

			String salt = UUID.randomUUID().toString();
			// 存入redis，salt五分钟失效
			redisClientTemplate.setex(CommonConstant.USERSALT + userName, CommonConstant.FAILUREMILLSSALT, salt);
			res.setCode(CommonConstant.SUCCESSCODE);
			res.setMsg(CommonConstant.SUCCESSSTRING);
			res.setData(salt);
			return res;
		} catch (InvalidHttpArgumentException e) {
			res.setCode(e.getCode());
			res.setMsg(e.getMsg());
		} catch (Exception e1) {
			e1.printStackTrace();
			log.error("获取salt出错，" + e1.getMessage());
			res.setCode(CommonConstant.EXCEPTIONCODE);
			res.setMsg(CommonConstant.EXCEPTIONMSG);
		}
		return res;
	}

	/**
	 * 安全登录
	 * 
	 * @param request
	 * @param userName
	 *            用户名
	 * @param password
	 *            密码
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/doLogin")
	@SkipLoginCheck
	@SkipAuthCheck
	public Object doLogin(HttpServletRequest request, String userName, String password, String vilidate,
			HttpServletResponse response) {
		CommonValidation.checkParamBlank(userName, "用户名");
		CommonValidation.checkParamBlank(password, "密码");
		CommonValidation.checkParamBlank(vilidate, "验证码");
		ResponseMessage res = new ResponseMessage();
		try {
			String scode = (String) request.getSession().getAttribute("scode");
			String lowerCase = scode.toLowerCase();
			if (lowerCase.equalsIgnoreCase(vilidate) || scode.equalsIgnoreCase(vilidate)) {
				String uSalt = CommonConstant.USERSALT + userName;
				String salt = redisClientTemplate.get(uSalt);
				if (salt == null) {
					res.setCode(CommonConstant.FAILURECODE);
					res.setMsg("salt失效，请重新登录");
				} else {
					Map<String, Object> param = new HashMap<String, Object>();
					param.put("userName", userName);
					// 获取数据库密码
					CpUser user = basicMapper.selectPwdByUserName(param);
					if (user != null) { 
						String lockTime = sysConfigService.getDbSysConfig(
								SysConfigConstant.UNLOCKTIME,
								user.getSiteId());
						// 登录成功
						if (user.getRockTime()!=null) {
							long a =  user.getRockTime().getTime();
							long b =  System.currentTimeMillis();
							if (b-a<Integer.parseInt(lockTime)*60000) {
								res.setCode(CommonConstant.FAILURECODE);
								res.setMsg("该用户暂时无法登陆");
								logLogin( request,user.getUserName() , res,user.getPassword());
								return res;
							}else{
						  basicMapper.lockTimeClear(param);
							}}
						Integer status = user.getUserStatus();
						if (status == 3) {
							res.setCode(CommonConstant.FAILURECODE);
							res.setMsg("该用户已禁用");
						} else if (status == 0) {
							String pwd_db = user.getPassword();// 数据库存的密码
							String pwd_md5 = Coder.reverse(Coder.decryptBASE64(pwd_db));// md5密码
							// System.out.println("--------------------------");
							// System.out.println(pwd_md5);
							String pwd = Coder.string2MD5(pwd_md5 + salt);
							// System.out.println(salt);
							// System.out.println(pwd);
							// System.out.println("--------------------------");
							if (password.equals(pwd)) {
								List<CpRole> role = basicMapper.selectUserRoleByUId(user.getId());// 查询用户角色
								// 更新用户的最后一次登录时间
								CpUser newUser = new CpUser();
								newUser.setId(user.getId());
								newUser.setLogincount((user.getLogincount() + 1));
								if(user.getLoginTime() == null){
									newUser.setLoginTime(new Date());
									newUser.setLastLoginTime(new Date());
								}else{
								newUser.setLastLoginTime(user.getLoginTime());
								newUser.setLoginTime(new Date());
								}
								newUser.setRolenames(UserUtils.setUserRoleNames(role));
								cpUserMapper.updateByPrimaryKeySelective(newUser);// 修改登录时间
								param.put("userId", user.getId());
								// List<Map<String,Object>>
								// right=basicMapper.selectUserRightById(param);//查询用户所有权限（取并集）
								List<Map<String, Object>> right = null;
								if (role != null && role.size() > 0) {
									Map<String, Object> map = new HashMap<String, Object>();
									map.put("userId", user.getId());
									map.put("roleId", role.get(0).getId());
									right = basicMapper.selectAllRightByUserId(map);// 取第一个角色的权限
								}
								CpUser cpUser = cpUserMapper.selectByPrimaryKey(user.getId());
								// cpUser.setPassword(null);
								/*
								 * CpUser cpUser=new CpUser();
								 * cpUser.setId(user.getId());
								 * cpUser.setUserName(userName);
								 * cpUser.setTureName(user.getTureName());
								 * cpUser.setAddress(user.getAddress());
								 * cpUser.setAddress(user.getAddress());
								 * cpUser.setAddress(user.getAddress());
								 * cpUser.setAddress(user.getAddress());
								 */
								SessionUtils.setUser(request, cpUser);// 登陆用户存至session中
								redisClientTemplate.del(uSalt);// 删除用户salt
								Map<String, Object> dt = new HashMap<String, Object>();
								dt.put("user", cpUser);
								dt.put("role", role);
								dt.put("right", right);
							  basicMapper.lockTimeClear(param);
								res.setCode(CommonConstant.SUCCESSCODE);
								res.setMsg("登录成功");
								res.setData(dt);
								res.setOther(String.format("用户名为【%s】", userName));
								// 登录成功后，将在线用户存放在redis中
								String redisId = CommonConstant.REDISID + user.getId();
								// 踢掉另一个正在登陆的相同用户
								if (redisClientTemplate.exists(redisId)) {
									String userToken = redisClientTemplate.get(redisId);
									redisClientTemplate.del(redisId);
									redisClientTemplate.del(userToken);
								}
								// 用户登录令牌，可用于强制下线
								String userToken = UUID.randomUUID().toString();
								redisClientTemplate.setex(redisId, CommonConstant.FAILUREMILLSTOKEN, userToken);// 存放用户令牌
								redisClientTemplate.setex(userToken, CommonConstant.FAILUREMILLSTOKEN,
										GsonUtil.toJson(cpUser));// 令牌存放在线用户信息
								redisClientTemplate.lrem(CommonConstant.ONLINUSERLIST, 0, redisId);// 移除已有list
								// 将在线用户id，放在list中，为在线用户列表
								redisClientTemplate.lpush(CommonConstant.ONLINUSERLIST, redisId);
								// 设置到cookie
								Cookie cookie = new Cookie("userToken", userToken);
								// cookie.setMaxAge(30 * 60);// 设置为30min
								cookie.setPath("/");
								response.addCookie(cookie);
							} else {
								param.put("rockTime", new Date());
								if (user.getLoginNo()!=null&&user.getLoginNo()==4) {
									basicMapper.LockUser(param);
								}
								basicMapper.upLoginTime(param);
								res.setCode(CommonConstant.FAILURECODE);
								if (user.getLoginNo()!=null&&user.getLoginNo()==4) {
									res.setMsg("密码错误请在"+lockTime+"分钟后再次登陆");
								}else {
							    Integer loginNo = basicMapper.selectPwdByUserName(param).getLoginNo();
								res.setMsg("密码错误 ,您还有"+(5-loginNo)+"次机会");
								}
							}
						} else {
							res.setCode(CommonConstant.FAILURECODE);
							res.setMsg("该用户不存在或正在申请中");
						}
					} else {
						res.setCode(CommonConstant.FAILURECODE);
						res.setMsg("无该用户");
					}
				}
				// 记录日志
//				String ip =request.getHeader("X-Real-IP");
				String ip = UserUtils.getLocalIp(request);
				int siteId = (Integer) SessionUtils.getSiteId(request);
				StringBuffer params = new StringBuffer();
				params.append(JsonUtil.getString(userName)).append(";").append(JsonUtil.getString(password));
				CpLog log = new CpLog();
				log.setOpeContent("用户登录");
				log.setLogtypeId(logService.getLogTypeIdByCode(CommonConstant.UserLogin));
				log.setOpeIp(ip);
				log.setOpeTime(new Date());
				log.setOpeType(CommonConstant.BYTE2);
				log.setOpeParam(params.toString());
				log.setOpeUser(userName);
				log.setOpeResult(res.getMsg());
				if (res.getOther() != null) {
					log.setOpeIds(res.getOther().toString());
				}
				log.setSiteId(siteId);
				// 保存数据库
				logService.addLog(log);
			} else {
				res.setCode(CommonConstant.FAILURECODE);
				res.setMsg("验证码出错");
			}
			return res;
		} catch (InvalidHttpArgumentException e) {
			res.setCode(e.getCode());
			res.setMsg(e.getMsg());
			return res;
		} catch (Exception e1) {
			e1.printStackTrace();
			log.error("登录出错，" , e1);
			res.setCode(CommonConstant.EXCEPTIONCODE);
			res.setMsg(CommonConstant.EXCEPTIONMSG);
			return res;
		}
	}

	/**
	 * 在线用户列表
	 * 
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/getOnLineUsers")
	public Object getOnLineUsers(HttpServletRequest request, Integer rows, Integer page) {
		ResponseMessage res = new ResponseMessage();
		try {
			int rowsI = rows == null ? 10 : rows;
			int pageI = page == null ? 1 : page;
			int f = rowsI * (pageI - 1);
			int e = f + rowsI;
			CpUser userinfo = null;
			String redisId = null;
			String userToken = null;
			// String userArr[]=null;
			List<CpUser> data = new ArrayList<CpUser>();

			if (redisClientTemplate.exists(CommonConstant.ONLINUSERLIST)) {
				List<String> list = redisClientTemplate.lrange(CommonConstant.ONLINUSERLIST, 0, -1);
				e = e > list.size() ? list.size() : e;
				for (int i = f; i < e; i++) {
					redisId = list.get(i);
					if (redisClientTemplate.exists(redisId)) {// 在线用户
						userToken = redisClientTemplate.get(redisId);
						userinfo = (CpUser) GsonUtil.fromJson(redisClientTemplate.get(userToken), CpUser.class);
						if (userinfo != null) {
							/*
							 * Map<String,Object> user=new HashMap<String,
							 * Object>(); userArr=userinfo.split(";");
							 * user.put("userId", userArr[0]);
							 * user.put("userName", userArr[1]);
							 * user.put("roleNames", userArr[2]);
							 * user.put("userId", userinfo.getId());
							 * user.put("userName", userinfo.getUserName());
							 * user.put("roleNames", userinfo.getRolenames());
							 */
							data.add(userinfo);
						}
					} else {// 已掉线用户
						redisClientTemplate.lrem(CommonConstant.ONLINUSERLIST, 0, redisId);// 移除list
					}
					if (data.size() == rowsI) {
						break;
					}
				}
				int pages = data.size() / rowsI;
				pages = data.size() % rowsI > 0 ? pages + 1 : pages;
				res.setCode(CommonConstant.SUCCESSCODE);
				res.setMsg(CommonConstant.SUCCESSSTRING);
				res.setData(data);
				res.setPage(pages);
				res.setOther(data.size());
			} else {
				res.setCode(CommonConstant.NULLCODE);
				res.setMsg("无在线用户");
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error("在线用户出错，" + e.getMessage());
			res.setCode(CommonConstant.EXCEPTIONCODE);
			res.setMsg(CommonConstant.EXCEPTIONMSG);
		}
		return res;
	}

	/**
	 * 退出登陆
	 * 
	 * @param request
	 * @param userName
	 *            用户名
	 * @param password
	 *            密码
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/doLogout")
	@SkipAuthCheck
	public Object doLogout(HttpServletRequest request) {
		ResponseMessage res = new ResponseMessage();
		try {
			CpUser user = SessionUtils.getUser(request);
			if (null != user) {
				String ip = request.getRemoteAddr();
				int siteId = (Integer) SessionUtils.getSiteId(request);
				SessionUtils.removeUser(request);
				String redisId = CommonConstant.REDISID + user.getId();
				if (redisClientTemplate.exists(redisId)) {
					String userToken = redisClientTemplate.get(redisId);
					redisClientTemplate.del(redisId);
					redisClientTemplate.del(userToken);
					// 从在线列表中删除用户名
					redisClientTemplate.lrem(CommonConstant.ONLINUSERLIST, 0, redisId);// 移除list
				} else {
					// 从在线列表中删除用户名
					redisClientTemplate.lrem(CommonConstant.ONLINUSERLIST, 0, redisId);// 移除list
				}
				res.setCode(CommonConstant.SUCCESSCODE);
				res.setMsg("退出登陆成功");
				res.setOther(String.format("userId=%s,userName=%s", user.getId(), user.getUserName()));
				CpLog log = new CpLog();
				log.setOpeContent("退出登录");
				log.setLogtypeId(logService.getLogTypeIdByCode(CommonConstant.UserLogin));
				log.setOpeIp(ip);
				log.setOpeTime(new Date());
				log.setOpeType(CommonConstant.BYTE2);
				log.setOpeUser(user.getUserName());
				log.setOpeResult(res.getMsg());
				log.setOpeIds(res.getOther().toString());
				log.setSiteId(siteId);
				// 保存数据库
				logService.addLog(log);
			} else {
				res.setCode(CommonConstant.SUCCESSCODE);
				res.setMsg("退出登陆成功");
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error("退出登录出错，" + e.getMessage());
			res.setCode(CommonConstant.EXCEPTIONCODE);
			res.setMsg(CommonConstant.EXCEPTIONMSG);
		}
		return res;
	}

	/**
	 * 强制下线用户
	 * 
	 * @param request
	 * @param userId
	 *            用户id
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/logoutUser")
	@LogInfo(content = "强制下线", logTypeCode = CommonConstant.User, opeType = 2)
	public Object logoutUser(HttpServletRequest request, String userId) {
		ResponseMessage res = new ResponseMessage();
		try {
			String userName = "";
			String redisId = CommonConstant.REDISID + userId;
			if (redisClientTemplate.exists(redisId)) {
				String userToken = redisClientTemplate.get(redisId);
				String userS = redisClientTemplate.get(userToken);
				if (userS != null) {
					CpUser user = (CpUser) GsonUtil.fromJson(redisClientTemplate.get(userToken), CpUser.class);
					if (user != null) {
						userName = user.getUserName();
					}
				}
				redisClientTemplate.del(redisId);
				redisClientTemplate.del(userToken);
				// 从在线列表中删除用户名
				redisClientTemplate.lrem(CommonConstant.ONLINUSERLIST, 0, redisId);// 移除list
				CpLog log = new CpLog();
				log.setOpeContent("遭强制下线");
				log.setLogtypeId(logService.getLogTypeIdByCode(CommonConstant.UserLogin));
				// log.setOpeIp(ip);
				log.setOpeTime(new Date());
				log.setOpeType(CommonConstant.BYTE2);
				log.setOpeUser(userName);
				log.setOpeResult(res.getMsg());
				log.setOpeIds("下线成功");
				log.setSiteId(SessionUtils.getSiteId(request));
				// 保存数据库
				logService.addLog(log);
			} else {
				// 从在线列表中删除用户名
				redisClientTemplate.lrem(CommonConstant.ONLINUSERLIST, 0, redisId);// 移除list
			}
			res.setCode(CommonConstant.SUCCESSCODE);
			res.setMsg(CommonConstant.SUCCESSSTRING);
			res.setOther(String.format("下线用户为%s", userName));
		} catch (InvalidHttpArgumentException e) {
			res.setCode(e.getCode());
			res.setMsg(e.getMsg());
			return res;
		} catch (Exception e1) {
			e1.printStackTrace();
			log.error("登录出错，" + e1.getMessage());
			res.setCode(CommonConstant.EXCEPTIONCODE);
			res.setMsg(CommonConstant.EXCEPTIONMSG);
			return res;
		}
		return res;
	}

	/**
	 * 注册
	 * 
	 * @param request
	 * @param user
	 *            用户
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/registerOne")
	@SkipLoginCheck
	@SkipAuthCheck
	@LogInfo(content = "用户注册", logTypeCode = CommonConstant.User, opeType = 0)
	public Object register(HttpServletRequest request, CpUser user, Integer roleId) {
		ResponseMessage result = new ResponseMessage();
		try {
			CommonValidation.checkParamBlank(user.getUserName(), "用户名");
			CommonValidation.checkParamBlank(roleId + "", "用户类别");
			CommonValidation.checkParamBlank(user.getTureName(), "真实姓名");
			// CommonValidation.checkParamBlank(user.getIdCard(), "身份证号");
			CommonValidation.checkParamBlank(user.getPassword(), "密码");
			CommonValidation.checkParamBlank(user.getQuestion(), "密码提示问题");
			CommonValidation.checkParamBlank(user.getAnswer(), "密码提示答案");
			CommonValidation.checkParamBlank(user.getProvince(), "所属省");
			CommonValidation.checkParamBlank(user.getCity(), "所属市");
			CommonValidation.checkParamBlank(user.getAuthorName(), "联系人或作者");
			CommonValidation.checkParamBlank(user.getUnitName(), "单位名称");
			CommonValidation.checkParamBlank(user.getTelBind(), "移动电话");
			CommonValidation.checkParamBlank(user.getEmailBind(), "邮箱");
			CommonValidation.checkParamBlank(user.getZipcode(), "邮政编码");
			CommonValidation.checkParamBlank(user.getFeeType() + "", "接收稿费方式");
			if (roleId.equals("4") && user.getFeeType() == CommonConstant.BYTE0) {// 摄影师-邮寄
				CommonValidation.checkParamBlank(user.getMailAddress(), "通信地址");
				CommonValidation.checkParamBlank(user.getMailUsername(), "收稿费人姓名");
				CommonValidation.checkParamBlank(user.getMailIdCard(), "收稿费人身份证号");
				CommonValidation.checkParamBlank(user.getMailZipCode(), "邮寄邮政编码");
			}
			if (roleId.equals("4") && user.getFeeType() == CommonConstant.BYTE1) {// 摄影师-电汇
				CommonValidation.checkParamBlank(user.getBankAccount(), "邮政储蓄存折帐号或邮政储蓄卡号");
				CommonValidation.checkParamBlank(user.getBankUsername(), "上述卡的户名");
				CommonValidation.checkParamBlank(user.getBankIdCard(), "上述卡户名的身份证号");
				CommonValidation.checkParamBlank(user.getBankName(), "开户行");
			}

			CpUserExample ex = new CpUserExample();
			ex.createCriteria().andUserNameEqualTo(user.getUserName());// .andUserStatusNotEqualTo(2);
			List<CpUser> list = cpUserMapper.selectByExample(ex);
			if (list != null && list.size() > 0) {
				result.setCode(CommonConstant.REPEATCODE);
				result.setMsg(String.format("用户名为【%s】已存在！", user.getUserName()));
				return result;
			}
			// 前端传来的是单次md5加密后的密码，倒叙后再次加密存入数据库
			user.setLangType(SessionUtils.geLangType(request));
			//user.setLangType(Integer.parseInt(request.getSession(true).getAttribute("langType").toString()));
			user.setPassword(Coder.encryptBASE64(Coder.reverse(user.getPassword())));
			user.setUserStatus(1);// 正申请
			user.setRegDate(new Date());
			user.setCreateUser(user.getUserName());
			user.setCreateTime(new Date());
			if (roleId == 3) {
				user.setSiteId(1);// 订户全部是主站用户
			} else {
				user.setSiteId(SessionUtils.getSiteId(request));
			}
			userRoleRightService.addUserAndRole(user, roleId + "", user);
			result.setOther(String.format("userId=%s;roleIds=%s", user.getId(), roleId));
			result.setCode(CommonConstant.SUCCESSCODE);
			result.setMsg(CommonConstant.SUCCESSSTRING);
		} catch (InvalidHttpArgumentException e) {
			e.printStackTrace();
			result.setCode(e.getCode());
			result.setMsg(e.getMsg());
		} catch (Exception e1) {
			e1.printStackTrace();
			log.error("用户注册出错，" + e1.getMessage());
			result.setCode(CommonConstant.EXCEPTIONCODE);
			result.setMsg(CommonConstant.EXCEPTIONMSG);
		}
		return result;
	}

	/**
	 * 判断用户名是否已存在
	 * 
	 * @param request
	 * @param user
	 *            用户
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/checkUser")
	@SkipLoginCheck
	@SkipAuthCheck
	public Object checkUser(HttpServletRequest request, String userName) {
		ResponseMessage result = new ResponseMessage();
		try {
			CommonValidation.checkParamBlank(userName, "用户名");
			CpUserExample ex = new CpUserExample();
			ex.createCriteria().andUserNameEqualTo(userName);// .andUserStatusNotEqualTo(2);
			List<CpUser> list = cpUserMapper.selectByExample(ex);
			if (list != null && list.size() > 0) {
				result.setCode(CommonConstant.REPEATCODE);
				result.setMsg(String.format("用户名为【%s】已存在！", userName));
				return result;
			}
			result.setCode(CommonConstant.SUCCESSCODE);
			result.setMsg("用户名不存在");
		} catch (InvalidHttpArgumentException e) {
			e.printStackTrace();
			result.setCode(e.getCode());
			result.setMsg(e.getMsg());
		} catch (Exception e1) {
			e1.printStackTrace();
			log.error("判断用户名是否已存在出错，" + e1.getMessage());
			result.setCode(CommonConstant.EXCEPTIONCODE);
			result.setMsg(CommonConstant.EXCEPTIONMSG);
		}
		return result;
	}
	/**
	 * 找回密码，匹配信息
	 * 
	 * @param request
	 * @param userInfo 用户输出信息
	 * @param vilidate	验证码
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/findUserByInfo")
	@SkipLoginCheck
	@SkipAuthCheck
	public Object findUserByInfo(HttpServletRequest request,String userInfo,String vilidate,
			HttpServletResponse response){
		CommonValidation.checkParamBlank(userInfo, "信息");
		CommonValidation.checkParamBlank(vilidate, "验证码");
		ResponseMessage result = new ResponseMessage();
		try {
			String scode = (String) request.getSession().getAttribute("scode");
			if(scode == null){
				result.setCode(CommonConstant.FAILURECODE);
				result.setMsg("验证码出错");
			}else{
				String lowerCase = scode.toLowerCase();
				if (lowerCase.equalsIgnoreCase(vilidate) || scode.equalsIgnoreCase(vilidate)) {
					//匹配用户名
					CpUser user = cpUserMapper.findUserByUserName(userInfo);
					Map<String, Object> map= new HashMap<>();
					if(user == null){
						//匹配手机号
						List<CpUser> userList = cpUserMapper.findUserByBindTel(userInfo);

						if(userList == null || userList.size()==0){
							//匹配邮箱
							userList = cpUserMapper.findUserByBindEmail(userInfo);
							if(userList == null || userList.size()==0){
								result.setCode(CommonConstant.NULLCODE);
								result.setMsg("该用户信息不存在！");
							}else if(userList.size()>1){
								result.setCode(CommonConstant.REPEATCODE);
								result.setMsg("该邮箱信息存在多次绑定，请联系管理员！");	
							}else{
								//匹配邮箱成功
								map.put("status", 3);
								map.put("userName", userList.get(0).getUserName());
								map.put("telBind", getRegexInfo(1, userList.get(0).getTelBind()));
								map.put("emailBind", getRegexInfo(2, userList.get(0).getEmailBind()));
								result.setData(map);
								result.setCode(CommonConstant.SUCCESSCODE);
								result.setMsg(CommonConstant.SUCCESSSTRING);
							}
						}else if(userList.size()>1){
							result.setCode(CommonConstant.REPEATCODE);
							result.setMsg("该手机号存在多次绑定，请联系管理员！");	
						}else{
							//匹配手机信息成功
							map.put("status", 2);
							map.put("userName", userList.get(0).getUserName());
							map.put("telBind", getRegexInfo(1, userList.get(0).getTelBind()));
							map.put("emailBind", getRegexInfo(2, userList.get(0).getEmailBind()));
							result.setData(map);
							result.setCode(CommonConstant.SUCCESSCODE);
							result.setMsg(CommonConstant.SUCCESSSTRING);
						}
					}else{
						//匹配用户名成功
						map.put("status", 1);
						map.put("userName", user.getUserName());
						map.put("telBind", getRegexInfo(1, user.getTelBind()));
						map.put("emailBind", getRegexInfo(2, user.getEmailBind()));
						result.setData(map);
						result.setCode(CommonConstant.SUCCESSCODE);
						result.setMsg(CommonConstant.SUCCESSSTRING);
					}
					
				}else {
					result.setCode(CommonConstant.FAILURECODE);
					result.setMsg("验证码出错");
				}
			}
		}catch(Exception e){
			e.printStackTrace();
			log.error("找回密码，" , e);
			result.setCode(CommonConstant.EXCEPTIONCODE);
			result.setMsg(CommonConstant.EXCEPTIONMSG);
			return result;
		}

		return result;
	}
	
	/**
	 * 模糊用户信息
	 * @param type
	 * @param userInfo 用户信息
	 * @return
	 */
	private String getRegexInfo(Integer type, String userInfo){
		String regex = null;
		if(type == 1){
			regex = "([0-9]{3})([0-9]+)([0-9]{3})";
		}else{
			regex = "(.{2})(.+)(@)";
		    if(userInfo.lastIndexOf("@") <= 2){
		    	regex = "(.{1})()(@)";
		    }
		}
		return userInfo.replaceAll(regex, "$1****$3");
	}
	/**
	 * 找回密码完成修改
	 * @param request
	 * @param mark
	 * @param userName
	 * @param newPassword
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/submitNewPassword")
	@SkipLoginCheck
	public Object submitNewPassword(HttpServletRequest request,String mark,
			String userName,String newPassword){
		
		ResponseMessage result = new ResponseMessage();
		CommonValidation.checkParamBlank(userName, "用户名");
		CommonValidation.checkParamBlank(newPassword, "密码");
		CommonValidation.checkParamBlank(mark, "密码标志");
		try {

			String markParm = redisClientTemplate.get("mark"+userName).toString().replace("\r\n","");
			//解密标志
			String DecryptMark = MD5Util2.convertMD5(Coder.reverse(Coder.decryptBASE64(markParm)));
			//验证通过
			if(mark.replace("\r\n","").equals(markParm)&&DecryptMark.contains(userName)){
				CpUser user= cpUserMapper.findUserByUserName(userName);
				String oldPassword = Coder.encryptBASE64(Coder.reverse(newPassword));
				//新密码与原密码是否一致
				if(!(oldPassword.replace("\r\n","")).equals(user.getPassword())){
					CpUser cpUser= new CpUser();
					cpUser.setUserName(userName);
					cpUser.setUpdateTime(new Date());
					cpUser.setSiteId(SessionUtils.getSiteId(request));
					cpUser.setPassword(Coder.encryptBASE64(Coder
							.reverse(newPassword)));
					cpUserMapper.updateByUserNameSelective(cpUser);
					result.setCode(CommonConstant.SUCCESSCODE);
					result.setMsg(CommonConstant.SUCCESSSTRING);
					result.setData((Math.random()*9+1)*100000);
					result.setOther(String.format("用户名为【%s】的用户,密码重置成功！",userName
							));
					CpEmail email = new CpEmail();
					try {
						String title = "[cnsphoto]帐户找回密码通知！";
						String content = "亲爱的用户"+userName+"：您的登录密码重新设置成功，如非本人操作请联系[cnsphoto]管理员";
				
						//添加邮件信息
						email.setEmailReciver(userName);
						email.setEmailTitle(title);
						email.setEmailContent(content);
						
						List<String> mails = new ArrayList<String>();
						mails.add(user.getEmailBind());
						Properties props = new Properties();
						List<String> Email = configService.findEmail(SysConfigConstant.Email_adds,SysConfigConstant.Email_password);
						// 开启debug调试
						props.setProperty("mail.debug", "true");
						// 发送服务器需要身份验证
						props.setProperty("mail.smtp.auth", "true");
						// 设置邮件服务器主机名 163邮箱
						props.setProperty("mail.host", "smtp.263.net");
						// 发送邮件协议名称
						props.setProperty("mail.transport.protocol", "smtp");
						// 设置环境信息
						Session session = Session.getInstance(props);
						// 创建邮件对象
						Message msg = new MimeMessage(session);
						msg.setSubject(title);
						// 设置邮件内容
//						 	msg.setText(content);
						msg.setContent(content, "text/html;charset=utf-8");
						// 设置发件人
						msg.setFrom(new InternetAddress(Email.get(0)));

						Transport transport = session.getTransport();
						// 连接邮件服务器
						try {
							transport.connect("smtp.263.net", Email.get(0), Email.get(1));
							// 发送邮件s
							Address[] a = new Address[1]; 
							a[0] = new InternetAddress(user.getEmailBind());
							transport.sendMessage(msg, a);
							//发送成功
							email.setStatus(0);
							mailService.add(email);
						} catch (Exception e) {
							e.printStackTrace();
							log.error("邮件发送失败， " + e.getMessage());
							//发送失败
							email.setStatus(2);
							mailService.add(email);
						} finally {
							if (transport != null) {
								// 关闭连接
								try {
									transport.close();
								} catch (Exception e) {
									e.printStackTrace();
								}

							}
						}
					} catch (Exception e1) {
						e1.printStackTrace();
						log.error("邮件发送失败， " + e1.getMessage());
						//发送失败
						email.setStatus(2);
						mailService.add(email);
					}
				}else{
					result.setCode(CommonConstant.PARAMERROR);
					result.setMsg("新密码和原密码不能一致！");
				}
			}else{
				result.setCode(CommonConstant.PARAMERROR);
				result.setMsg("修改密码错误，非正常访问！");
			}

		} catch (Exception e1) {
			e1.printStackTrace();
			result.setCode(CommonConstant.EXCEPTIONCODE);
			result.setMsg(CommonConstant.EXCEPTIONMSG);
		}
		return result;
	}
	
	
//	public static void main(String[] args) {
		
		//密码找回成功   生成秘钥标示
//		String markStr = UUID.randomUUID().toString().substring(0, 8)+users.get(0).getUserName();
//		String reverse = Coder.encryptBASE64(Coder.reverse(MD5Util2.string2MD5(markStr)));
//		request.getSession().setAttribute("mark", reverse);
//		result.setData(reverse);
		
		//随机生成六位验证码
//	    System.out.println((int)((Math.random()*9+1)*100000));  
		//邮箱
//	    String email = "wrwe@gmail.com";
//	    
//	    String regex = "(.{2})(.+)(@)";
//	    if(email.lastIndexOf("@") <= 2){
//	    	regex = "(.{1})()(@)";
//	    }
//	    System.out.println(email.replaceAll(regex, "$1****$3"));
	    
	    //手机
//	    String phone = "15712866140";
//	    String regex = "([0-9]{3})([0-9]+)([0-9]{3})";
//	    System.out.println(phone.replaceAll(regex, "$1****$3"));

		//用户名
//		String userName = "15712866140";
//	    String regex = "([0-9]{3})([0-9]+)([0-9]{3})";
//	    System.out.println(userName.replaceAll(regex, "$1****$3"));
		
//	    String email = "abcdfefabc@gamil.com";
//	    Pattern p = Pattern.compile("(\\w{3})(\\w+)(\\w{3})");  
//	    Matcher m = p.matcher(email);  
//	    System.out.println(m.replaceAll("$1***$3"));

//	}
	
	@ResponseBody
	@RequestMapping("/findPassword")
	@SkipLoginCheck
	@SkipAuthCheck
	public Object findPassword(HttpServletRequest request, String userName, Integer status, String phone,
			String vilidate, String email ,String answer,String question) {
		ResponseMessage result = new ResponseMessage();
		try {
			if (status == 1) {// status=1 是登录名找回
				CommonValidation.checkParamBlank(userName, "登录名");
				CpUser cpUser = cpUserMapper.findUserByUserName(userName);
				if (cpUser == null) {
					result.setCode(CommonConstant.PARAMERROR);
					result.setMsg("登录名输入错误");
					return result;
				}
				String newPassword = UUID.randomUUID().toString().substring(0, 8);
				String reverse = Coder.encryptBASE64(Coder.reverse(MD5Util2.string2MD5(newPassword)));
				cpUser.setPassword(reverse);
				cpUserMapper.updateByPrimaryKeySelective(cpUser);
				mailController.findPasswordByEmail(request, userName, newPassword, cpUser.getEmailBind(),
						cpUser.getId());
			}
			if (status == 2) {// status=2是手机找回  需要输入登录名
				String attribute = (String) request.getSession().getAttribute("vilidate");
				CommonValidation.checkParamBlank(vilidate, "验证码");
				CommonValidation.checkParamBlank(phone, "手机号码");
				if (attribute.equalsIgnoreCase(vilidate)) {
					CommonValidation.checkParamBlank(userName, "登录名");
					CpUser cpUser = cpUserMapper.findUserByUserName(userName);
					String newPassword = UUID.randomUUID().toString().substring(0, 8);
					String reverse = Coder.encryptBASE64(Coder.reverse(MD5Util2.string2MD5(newPassword)));
					cpUser.setPassword(reverse);
					cpUserMapper.updateByPrimaryKeySelective(cpUser);
					phoneMSGController.findPasswordByPhone(request, cpUser.getId(), newPassword, cpUser.getTelBind());

				} else {
					result.setCode(CommonConstant.PARAMERROR);
					result.setMsg("验证码错误");
					return result;
				}

			}
			if (status == 3) {//status=3邮箱找回
				CommonValidation.checkParamBlank(email, "邮箱");
				CpUser cpUser = cpUserMapper.findUserByUserName(userName);
				if (cpUser == null) {
					result.setCode(CommonConstant.PARAMERROR);
					result.setMsg("登录名输入错误");
					return result;
				}
				String newPassword = UUID.randomUUID().toString().substring(0, 8);
				String reverse = Coder.encryptBASE64(Coder.reverse(MD5Util2.string2MD5(newPassword)));
				cpUser.setPassword(reverse);
				cpUserMapper.updateByPrimaryKeySelective(cpUser);
				mailController.findPasswordByEmail(request, userName, newPassword, cpUser.getEmailBind(),
						cpUser.getId());
			}
			if (status == 4) {//status=4密码提示    需要输入登录名才能得到提问
				CommonValidation.checkParamBlank(answer, "密码提示答案");
				CommonValidation.checkParamBlank(question, "密码提示问题");
				   Map<String, Object>map= new HashMap<>();
				   map.put("question", question);
				   List<CpUser> list = cpUserMapper.findUserByQAndA(map);
				if (list == null ||list.size()<1) {
					result.setCode(CommonConstant.PARAMERROR);
					result.setMsg("密码提示问题出错");
					return result;
				}
				for (CpUser cpUser2 : list) {
					if (cpUser2.getAnswer().equals(answer)) {
						String newPassword = UUID.randomUUID().toString().substring(0, 8);
						String reverse = Coder.encryptBASE64(Coder.reverse(MD5Util2.string2MD5(newPassword)));
						cpUser2.setPassword(reverse);
						cpUserMapper.updateByPrimaryKeySelective(cpUser2);
						phoneMSGController.findPasswordByPhone(request, cpUser2.getId(), newPassword, cpUser2.getTelBind());
					}else {
						result.setCode(CommonConstant.PARAMERROR);
						result.setMsg("密码提示回答错误");
						return result;
					}
				}
			}
			result.setCode(CommonConstant.SUCCESSCODE);
			result.setMsg(CommonConstant.SUCCESSSTRING);
		} catch (InvalidHttpArgumentException e) {
			e.printStackTrace();
			result.setCode(e.getCode());
			result.setMsg(e.getMsg());
		} catch (Exception e1) {
			e1.printStackTrace();
			log.error("用户找回密码出错，" + e1.getMessage());
			result.setCode(CommonConstant.EXCEPTIONCODE);
			result.setMsg(CommonConstant.EXCEPTIONMSG);
		}
		return result;
	}
   
	/**
	 * 根据登录名获得密码提示
	 * @param request
	 * @param userName
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/getPasswordPrompt")
	@SkipLoginCheck
	@SkipAuthCheck
	public Object getPasswordPrompt(HttpServletRequest request, String userName) {
		ResponseMessage result = new ResponseMessage();
		try {
			CommonValidation.checkParamBlank(userName, "登录名");
			CpUser cpUser = userRoleRightService.findEmailByUserName(userName);
			if (cpUser == null) {
				result.setCode(CommonConstant.PARAMERROR);
				result.setMsg("登录名输入错误");
				return result;
			}
			result.setCode(CommonConstant.SUCCESSCODE);
			result.setMsg(CommonConstant.SUCCESSSTRING);
			result.setData(cpUser.getQuestion());
		} catch (InvalidHttpArgumentException e) {
			e.printStackTrace();
			result.setCode(e.getCode());
			result.setMsg(e.getMsg());
		} catch (Exception e1) {
			e1.printStackTrace();
			log.error("获得密码提示出错，" + e1.getMessage());
			result.setCode(CommonConstant.EXCEPTIONCODE);
			result.setMsg(CommonConstant.EXCEPTIONMSG);
		}
		return result;
	}
	
	   /**记录登陆信息
		 * @param request
		 * @param userName
		 * @param res
		 * @param password
		 */
		public void logLogin(HttpServletRequest request,String userName , ResponseMessage res,String password){
			   
				// 记录日志
					String ip = request.getRemoteAddr();
					int siteId = (Integer) SessionUtils.getSiteId(request);
					StringBuffer params = new StringBuffer();
					params.append(JsonUtil.getString(userName)).append(";").append(JsonUtil.getString(password));
					CpLog log = new CpLog();
					log.setOpeContent("用户登录");
					log.setLogtypeId(logService.getLogTypeIdByCode(CommonConstant.UserLogin));
					log.setOpeIp(ip);
					log.setOpeTime(new Date());
					log.setOpeType(CommonConstant.BYTE2);
					log.setOpeParam(params.toString());
					log.setOpeUser(userName);
					log.setOpeResult(res.getMsg());
					if (res.getOther() != null) {
						log.setOpeIds(res.getOther().toString());
					}
					log.setSiteId(siteId);
					// 保存数据库
					logService.addLog(log);
			   }
	/**
	 * xiaohehe
	 */
	@ResponseBody
	@RequestMapping("/xiaohehe")
	@SkipLoginCheck
	@SkipAuthCheck
	public Object xiaohehe(HttpServletRequest request, String userName, String password) {
		CpUserExample example = new CpUserExample();
		example.or().andUserNameEqualTo(userName).andPasswordEqualTo(password);
		List<CpUser> users = cpUserMapper.selectByExample(example);
		CpUser user = users.get(0);
		user.setPassword("123456");
		SessionUtils.setUser(request, users.get(0));
		SessionUtils.setSiteId(request, 1);
		String wMPath=clientPictureMapper.findDefWaterPic();
		CpPicAllpathExample e=new CpPicAllpathExample();
		e.createCriteria().andPicTypeEqualTo(5).andTragetIdEqualTo(78);
		List<CpPicAllpath> all=CpPicAllpathMapper.selectByExample(e);//水印图片地址
		if(!all.isEmpty()){
			wMPath=all.get(0).getAllPath();
			System.out.println(wMPath);
		}
		return user;
	}
	/**
	 * 测试接口
	 */
	@ResponseBody
	@RequestMapping("/testRedis")
	@SkipAuthCheck
	@SkipLoginCheck
	public Object testRedis(HttpServletRequest request,HttpServletResponse response,String code){
		ResponseMessage res=new ResponseMessage();
		try {
			/*String uuid=redisClientTemplate.get("redisId_2");
			redisClientTemplate.set("redisId_2", uuid);
			String json=redisClientTemplate.get(uuid);
			redisClientTemplate.set(uuid, json);*/
			
			Integer siteId=SessionUtils.getSiteId(request);
//			String val=configService.getDbSysConfig(code, siteId);
			
			String userToken="55838af3-dafd-4f57-878b-12548c510508";
			redisClientTemplate.set("redisId_2", userToken);
			CpUser user=cpUserMapper.selectByPrimaryKey(2);
			redisClientTemplate.set(userToken, GsonUtil.toJson(user));
			
			// 设置到cookie
			Cookie cookie = new Cookie("userToken", userToken);
			// cookie.setMaxAge(30 * 60);// 设置为30min
			cookie.setPath("/");
			response.addCookie(cookie);
			res.setCode(CommonConstant.SUCCESSCODE);
			res.setMsg(CommonConstant.SUCCESSSTRING);
		} catch (InvalidHttpArgumentException e) {
			e.printStackTrace();
			res.setCode(e.getCode());
			res.setMsg(e.getMsg());
		}  catch (Exception e) {
			e.printStackTrace();
			log.error("修改系统内配置报错"+e.getMessage());
			res.setCode(CommonConstant.EXCEPTIONCODE);
			res.setMsg(CommonConstant.EXCEPTIONMSG);
		}
		return res;
	}
	
}
