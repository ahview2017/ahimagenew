package com.deepai.photo.controller.email;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.UUID;

import javax.mail.Address;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.deepai.photo.bean.CpEmail;
import com.deepai.photo.bean.CpRole;
import com.deepai.photo.bean.CpUser;
import com.deepai.photo.bean.EnGroupManagement;
import com.deepai.photo.common.annotation.LogInfo;
import com.deepai.photo.common.annotation.SkipLoginCheck;
import com.deepai.photo.common.constant.CommonConstant;
import com.deepai.photo.common.constant.SysConfigConstant;
import com.deepai.photo.common.pagehelper.PageHelper;
import com.deepai.photo.common.pojo.ResponseMessage;
import com.deepai.photo.common.redis.RedisClientTemplate;
import com.deepai.photo.common.util.SessionUtils;
import com.deepai.photo.common.util.encrypt.Coder;
import com.deepai.photo.common.util.encrypt.MD5Util2;
import com.deepai.photo.common.validation.CommonValidation;
import com.deepai.photo.mapper.CpBasicMapper;
import com.deepai.photo.mapper.CpUserMapper;
import com.deepai.photo.service.admin.SysConfigService;
import com.deepai.photo.service.admin.UserRoleRightService;
import com.deepai.photo.service.enColumn.EnGroupManagementService;
import com.deepai.photo.service.mail.MailService;

import net.sf.json.JSONObject;

/**
 * * @author huqiankai: *
 */
@Controller
@RequestMapping("/mail")
public class MailController {
	private Logger log = Logger.getLogger(MailController.class);
	@Autowired
	private UserRoleRightService userRoleRightService;
	@Autowired
	private MailService mailService;
	@Autowired
	private SysConfigService configService;
	private int[] uid;
	private int[] teamId;
	@Autowired
	private CpUserMapper cpUserMapper;
	@Autowired
	private EnGroupManagementService enGroupManagementService;
	@Autowired
	private RedisClientTemplate redisClientTemplate;
	@Autowired
	private CpBasicMapper basicMapper;
	@Autowired
	private SysConfigService sysConfigService;

	/**
	 * 发送邮件
	 * 
	 * @param request
	 * @param response
	 * @param uid
	 * @param content
	 * @param title
	 * @param teamId
	 * @param status
	 * @return
	 * @throws MessagingException
	 */
	@ResponseBody
	@RequestMapping("/sendEmail")
	@LogInfo(content = "发送邮件", opeType = 0, logTypeCode = CommonConstant.Email)
	public Object send(HttpServletRequest request, String uids, String content,
			 String title, String teamIds, int status) throws MessagingException {
		ResponseMessage result = new ResponseMessage();
		if (teamIds == null) {
			teamId = null;
		}
		if (uids == null) {
			uid = null;
		}
		try {
			String roseid = null;
			String name=null;
			List<String> mails = new ArrayList<String>();// 存放邮件的地址
			if (uids == null || ("").equals(uids)) {
				String userName = null;
				String[] teamId2 = teamIds.split(",");
				for (String string : teamId2) {
					EnGroupManagement groupManagement = enGroupManagementService.selectById(Integer.parseInt(string));// 获取群组的名字
					name =groupManagement.getGroupName() + ":";
					roseid="(群组)"+name;
					List<Map<String, Object>> userList = enGroupManagementService.getGroupManagementUser(Integer.parseInt(string), userName);// 获取群组的用户的信息
					for (Map<String, Object> map : userList) {
						mails.add(map.get("EMAIL_BIND").toString());
					}
				}
			
			} else {
				CpUser cpUser = cpUserMapper.selectByPrimaryKey(Integer.parseInt(uids));
				JSONObject jsonObject = new JSONObject();
				List<CpRole> userRole = basicMapper.selectUserRoleByUId(cpUser.getId());//获取被修改的用户权限
				for (CpRole cpRole : userRole) {
					String key = cpRole.getId().toString();
					String value = cpRole.getRoleName();
					jsonObject.accumulate(key, value);
				}
				if(jsonObject.containsKey("3")){//签约摄影师
					String email_edit = sysConfigService.getDbSysConfig(SysConfigConstant.Email_edit, SessionUtils.getSiteId(request));
					mails.add(email_edit);
				}else if(jsonObject.containsKey("5")){//订户
					String email_sell = sysConfigService.getDbSysConfig(SysConfigConstant.Email_sell, SessionUtils.getSiteId(request));
					mails.add(email_sell);
				}
				roseid = cpUser.getUserName();
			}


			if (status == 1) { // status==1 保存信息
				save(uid, content, teamId, request, status, title);
				result.setCode(CommonConstant.SUCCESSCODE);
				result.setMsg(CommonConstant.SUCCESSSTRING);
				return result;
			}
			String userid = "";
			List<Integer> list = new ArrayList<Integer>();
			if (teamId!=null&&teamId.length > 0) {
				for (int i : teamId) {
					List<Integer> id = userRoleRightService.findTeamID(i);
					list.addAll(id);
					String roseid1=userRoleRightService.getRoseName(i);
					roseid = roseid +"(组)"+roseid1 + ":";
				}
			}
			if (uid!=null&&uid.length > 0) {
				for (int i : uid) {
					list.add(i);
					String findNameByUid = userRoleRightService.findNameByUid(i);
					userid = userid + findNameByUid + ":";
					log.info("初始化userid------------------>"+userid);
				}
			}
			Properties props = new Properties();
			List<String> Email = configService.findEmail(SysConfigConstant.Email_adds,
					SysConfigConstant.Email_password);
			// 开启debug调试
			props.setProperty("mail.debug", "true");
			// 发送服务器需要身份验证
			props.setProperty("mail.smtp.auth", "true");
			// 设置邮件服务器主机名 企业邮箱
			props.setProperty("mail.host", "smtp.263.net");
			// 发送邮件协议名称
			props.setProperty("mail.transport.protocol", "smtp");
			
			// 设置环境信息
			Session session = Session.getInstance(props);
			// 创建邮件对象
			Message msg = new MimeMessage(session);
			msg.setSubject(title);
			// 设置邮件内容
			msg.setText(content);

			// 设置发件人
			msg.setFrom(new InternetAddress(Email.get(0)));

			Transport transport = session.getTransport();
			// 连接邮件服务器
			try {
				transport.connect("smtp.263.net", Email.get(0), Email.get(1));
				// 发送邮件
				Address[] a = new Address[mails.size()];
				for (int i = 0; i < mails.size(); i++) {
					// 发送的邮箱
					a[i] = new InternetAddress(mails.get(i));

				}
				transport.sendMessage(msg, a);
				CpEmail email = new CpEmail();
				email.setEmailReciverRole(roseid);
				email.setEmailReciver(userid);
				email.setEmailTitle(title);
				email.setSiteId(SessionUtils.getSiteId(request));
				email.setStatus(0);
				email.setEmailContent(content);
				email.setSender( SessionUtils.getUser(request).getUserName());
				mailService.add(email);
				result.setCode(CommonConstant.SUCCESSCODE);
				result.setMsg(CommonConstant.SUCCESSSTRING);
			} catch (Exception e) {
				e.printStackTrace();
				save(uid, content, teamId, request, 2, title); // status==2 发送失败
				log.error("邮件发送失败， " + e.getMessage());
				result.setCode(CommonConstant.EXCEPTIONCODE);
				result.setMsg(CommonConstant.EMAILEXCEPTIONMSG);
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
			save(uid, content, teamId, request, 2, title); // status==2 发送失败
			log.info("邮件发送失败 ------------------->",e1);
			result.setCode(CommonConstant.EXCEPTIONCODE);
			result.setMsg(CommonConstant.EMAILEXCEPTIONMSG);
		}
		return result;
	}

	/**
	 * 查看邮件
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/showEmail")
	public Object showEmail(HttpServletRequest request, HttpServletResponse response) {
		ResponseMessage result = new ResponseMessage();
		try {
			PageHelper.startPage(request);
			List<CpEmail> list = mailService.show();
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
			log.error("不能显示邮件，" + e1.getMessage());
			result.setCode(CommonConstant.EXCEPTIONCODE);
			result.setMsg(CommonConstant.EXCEPTIONMSG);
		}
		return result;
	}

	/**
	 * 邮件详情查看
	 * 
	 * @param request
	 * @param response
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/detailEmail")
	public Object detail(HttpServletRequest request, HttpServletResponse response, int id) {
		ResponseMessage result = new ResponseMessage();
		try {
			CpEmail cpEmail = mailService.detail(id);
			result.setCode(CommonConstant.SUCCESSCODE);
			result.setMsg(CommonConstant.SUCCESSSTRING);
			result.setData(cpEmail);
		} catch (Exception e1) {
			e1.printStackTrace();
			log.error("邮件详情查看失败， " + e1.getMessage());
			result.setCode(CommonConstant.EXCEPTIONCODE);
			result.setMsg(CommonConstant.EXCEPTIONMSG);
		}
		return result;
	}

	@ResponseBody
	@RequestMapping("/deleteEmail")
	@LogInfo(content = "删除邮件", opeType = 2, logTypeCode = CommonConstant.Email)
	public Object delete(HttpServletRequest request, HttpServletResponse response, String id) {
		ResponseMessage result = new ResponseMessage();
		try {
			String[] split = id.split(",");
			for (String i : split) {
				mailService.delete(Integer.parseInt(i));
			}
			result.setCode(CommonConstant.SUCCESSCODE);
			result.setMsg(CommonConstant.SUCCESSSTRING);
		} catch (Exception e1) {
			e1.printStackTrace();
			log.error("删除邮件失败， " + e1.getMessage());
			result.setCode(CommonConstant.EXCEPTIONCODE);
			result.setMsg(CommonConstant.EXCEPTIONMSG);
		}
		return result;
	}
	/**
	 * 邮件的高级检索
	 * @param request
	 * @param response
	 * @param title  邮件标题
	 * @param reciver  接受者
	 * @param timeFrom  开始时间
	 * @param timeTo   结束时间
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/searchEmail")
	public Object search(HttpServletRequest request, HttpServletResponse response, String title, String reciver,String timeFrom,String timeTo) {
		ResponseMessage result = new ResponseMessage();
		try {
		    PageHelper.startPage(request);
		    List<CpEmail> list = mailService.search(title,reciver,timeFrom,timeTo);
		    PageHelper.addPages(result, list);
			for (int i = 0; i < list.size(); i++) {
				String datetime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(list.get(i).getCreateTime());
				list.get(i).setDatetime(datetime);
			}
			result.setCode(CommonConstant.SUCCESSCODE);
			result.setMsg(CommonConstant.SUCCESSSTRING);
			result.setData(list);
			PageHelper.addPagesAndTotal(result, list);
		} catch (Exception e1) {
			e1.printStackTrace();
			log.error("搜索邮件失败，" + e1.getMessage());
			result.setCode(CommonConstant.EXCEPTIONCODE);
			result.setMsg(CommonConstant.EXCEPTIONMSG);
		}
		return result;
	}

	public void save(int[] uid, String content, int[] teamId, HttpServletRequest request, int Status, String title) {
		String roseid = "";
		String userid = "";
		if (teamId != null && teamId.length > 0) {
			for (int i : teamId) {
				String roseid1 = userRoleRightService.getRoseName(i);
				roseid = roseid + "(组)" + roseid1 + ":";
			}
		}
		if (uid != null && uid.length > 0) {
			for (int i : uid) {
				String findNameByUid = userRoleRightService.findNameByUid(i);
				userid = userid + findNameByUid + ":";
			}
		}
		if (roseid != null && roseid.length() > 1) {
			roseid = roseid.substring(0, roseid.length() - 1);
		}
		if (userid != null && userid.length() > 1) {
			userid = userid.substring(0, userid.length() - 1);
		}
		CpEmail email = new CpEmail();
		email.setEmailReciverRole(roseid);
		email.setEmailReciver(userid);
		email.setEmailTitle(title);
		email.setStatus(Status);
		email.setEmailContent(content);
		mailService.add(email);
	}
	/**
	 * 用户注册信息，发送邮件
	 * @param request
	 * @param userName
	 * @param password
	 * @param userEmail
	 * @param roleId
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/sendEmailByName")
	@SkipLoginCheck
	public Object sendEmailByName(HttpServletRequest request, String userName, String password, String userEmail,Integer roleId){
		
		CommonValidation.checkParamBlank(userName, "用户名");
		CommonValidation.checkParamBlank(password, "密码");
		ResponseMessage result = new ResponseMessage();
		CpEmail email = new CpEmail();
		try {
			CpUser cpUser = cpUserMapper.findUserByUserName(userName);
			String pwd_db = cpUser.getPassword();// 数据库存的密码
			String pwd_md5 = Coder.reverse(Coder.decryptBASE64(pwd_db));// md5密码
			
			if(pwd_md5.equals(password)){
//				String title = "[photo]帐户注册通知！";
//				String content = "亲爱的用户"+userName+"：您好！ "
//						+"感谢您注册photo的账户，您的注册手机号为："+cpUser.getTelBind();
//				
//				if(roleId != null&&roleId == 3){
//					//摄影师
//					content += ",您的作者名是："+cpUser.getAuthorName();	
//				}
//				content += ",请您核实信息！";		
			    String title = configService
                        .getDbSysConfig(SysConfigConstant.MAIL_TITLE_CODE, 1);
			    String sContent = configService
                        .getDbSysConfig(SysConfigConstant.MAIL_SUCCESS_CODE, 1);
			    sContent = String.format(sContent, userName,cpUser.getTelBind());
			    
				//添加邮件信息
				email.setEmailReciver(userName);
				email.setEmailTitle(title);
				email.setEmailContent(sContent);
				
				List<String> mails = new ArrayList<String>();
				mails.add(userEmail);
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
//			 	msg.setText(content);
				msg.setContent(sContent, "text/html;charset=utf-8");
				// 设置发件人
				msg.setFrom(new InternetAddress(Email.get(0)));

				Transport transport = session.getTransport();
				// 连接邮件服务器
				try {
					transport.connect("smtp.263.net", Email.get(0), Email.get(1));
					// 发送邮件s
					Address[] a = new Address[1]; 
					a[0] = new InternetAddress(userEmail);
					transport.sendMessage(msg, a);
					//发送成功
					email.setStatus(0);
					mailService.add(email);
					result.setCode(CommonConstant.SUCCESSCODE);
					result.setMsg(CommonConstant.SUCCESSSTRING);
				} catch (Exception e) {
					e.printStackTrace();
					log.error("邮件发送失败， " + e.getMessage());
					//发送失败
					email.setStatus(2);
					mailService.add(email);
					result.setCode(CommonConstant.EXCEPTIONCODE);
					result.setMsg(CommonConstant.EMAILEXCEPTIONMSG);
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
			}else{
				result.setCode(CommonConstant.FAILURECODE);
				result.setMsg("该用户不存在或正在申请中");
			}
		} catch (Exception e1) {
			e1.printStackTrace();
			log.error("邮件发送失败， " + e1.getMessage());
			//发送失败
			email.setStatus(2);
			mailService.add(email);
			result.setCode(CommonConstant.EXCEPTIONCODE);
			result.setMsg(CommonConstant.EMAILEXCEPTIONMSG);
		}
		return result;
		
	}
	/**
	 * 确认邮箱验证码
	 * @param request
	 * @param userName
	 * @param vilidate 确认验证码
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/confirmEmailVilidate")
	@SkipLoginCheck
	public Object confirmEmailVilidate(HttpServletRequest request,String userName
			,String vilidate){
		ResponseMessage result = new ResponseMessage();
		CommonValidation.checkParamBlank(vilidate, "验证码");
		CommonValidation.checkParamBlank(userName, "用户名");
		try {
			CpUser user= cpUserMapper.findUserByUserName(userName);
			if(redisClientTemplate.ttl("EMAIL"+userName+vilidate)==-2){
				result.setCode(CommonConstant.PARAMERROR);
				result.setMsg("验证码已失效！");
			}else{
				String emailVilidate = redisClientTemplate.get("EMAIL"+userName+vilidate);
//				request.getSession().getAttribute("emailVilidate").toString();
				if(vilidate.equals(emailVilidate)){
					//密码找回成功   生成秘钥标示
					String markStr = UUID.randomUUID().toString().substring(0, 8)+user.getUserName();
					String reverse = Coder.encryptBASE64(Coder.reverse(MD5Util2.convertMD5(markStr)));
//					request.getSession().setAttribute("mark", reverse);
					
					redisClientTemplate.set("mark"+userName, reverse);
					redisClientTemplate.expire("mark"+userName, 60*10);
					result.setData(reverse);
					result.setCode(CommonConstant.SUCCESSCODE);
					result.setMsg(CommonConstant.SUCCESSSTRING);
				}else{
					result.setCode(CommonConstant.PARAMERROR);
					result.setMsg("验证码不正确！");
				}
			}
			
		} catch (Exception e1) {
			e1.printStackTrace();
			result.setCode(CommonConstant.EXCEPTIONCODE);
			result.setMsg(CommonConstant.EXCEPTIONMSG);
		}
		return result;
	}
	/**
	 * 找回密码通过邮箱验证码
	 * @param request
	 * @param userName
	 * @param userId
	 * @return
	 * @throws MessagingException
	 */
	@ResponseBody
	@RequestMapping("/sendEmailVilidate")
	@SkipLoginCheck
	public Object sendEmailVilidate(HttpServletRequest request,String userName
			,Integer userId) throws MessagingException {
		ResponseMessage result = new ResponseMessage();
		CommonValidation.checkParamBlank(userName, "用户名");
		try {
//			if(redisClientTemplate.ttl("PHONE"+userName+vilidate).){
//				
//			}
			CpUser user= cpUserMapper.findUserByUserName(userName);
//			String title = "中新社密码找回";
			String title = configService
                    .getDbSysConfig(SysConfigConstant.MAIL_FORGETTITLE_CODE, 1);
			//生成六位验证码发送到邮箱
			Integer vilidate = (int)((Math.random()*9+1)*100000);
			redisClientTemplate.set("EMAIL"+userName+vilidate, vilidate+"");
			redisClientTemplate.expire("EMAIL"+userName+vilidate, 60*2);
			
//			//清除过期key值
//			request.getSession().setAttribute("emailVilidate", vilidate);
//			redisClientTemplate.expire("EMAIL"+userName+request.getSession().getAttribute("emailVilidate"), -2);
			
//			String content = "【中新社】 您正在执行中新社密码找回，验证码是: "+vilidate+"。请按页面提示提交验证码，切记请勿将验证码泄露给他人。";
			String content = configService
                    .getDbSysConfig(SysConfigConstant.MAIL_FORGET_CODE, 1);
			content = String.format(content, vilidate);
			
			
			List<String> mails = new ArrayList<String>();
			mails.add(user.getEmailBind());
			Properties props = new Properties();
			List<String> Email = configService.findEmail(SysConfigConstant.Email_adds,SysConfigConstant.Email_password);
			// 开启debug调试
			props.setProperty("mail.debug", "true");
			// 发送服务器需要身份验证
			props.setProperty("mail.smtp.auth", "true");
			// 设置邮件服务器主机名 263邮箱
			props.setProperty("mail.host", "smtp.263.net");
			// 发送邮件协议名称
			props.setProperty("mail.transport.protocol", "smtp");
			// 设置环境信息
			Session session = Session.getInstance(props);
			// 创建邮件对象
			Message msg = new MimeMessage(session);
			msg.setSubject(title);
			// 设置邮件内容
//		 	msg.setText(content);
			msg.setContent(content, "text/html;charset=utf-8");
			// 设置发件人
			msg.setFrom(new InternetAddress(Email.get(0)));

			Transport transport = session.getTransport();
			// 连接邮件服务器
			try {
				transport.connect("smtp.263.net", Email.get(0), Email.get(1));
				// 发送邮件
				Address[] a = new Address[mails.size()];
				for (int i = 0; i < mails.size(); i++) {
					// 发送的邮箱
					a[i] = new InternetAddress(mails.get(i));
				}
				transport.sendMessage(msg, a);
			} catch (Exception e) {
				e.printStackTrace();
				save(uid, content, teamId, request, 2, title); // status==2 发送失败
				log.error("邮件发送失败， " + e.getMessage());
				result.setCode(CommonConstant.EXCEPTIONCODE);
				result.setMsg(CommonConstant.EMAILEXCEPTIONMSG);
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
			result.setCode(CommonConstant.SUCCESSCODE);
			result.setMsg(CommonConstant.SUCCESSSTRING);
		} catch (Exception e1) {
			e1.printStackTrace();
			log.error("邮件发送失败， " + e1.getMessage());
			result.setCode(CommonConstant.EXCEPTIONCODE);
			result.setMsg(CommonConstant.EMAILEXCEPTIONMSG);
		}
		return result;
	}
	
	@ResponseBody
	@RequestMapping("/findPasswordByEmail")
	public Object findPasswordByEmail(HttpServletRequest request, String userName, String newPassword, String userEmail
			,Integer userId) throws Exception {
		ResponseMessage result = new ResponseMessage();
//		String title = "中新社密码找回";
		String title = configService
                .getDbSysConfig(SysConfigConstant.MAIL_FORGETTITLE_CODE, 1);
//		String content = "用户的密码是: " + newPassword;
		String content = configService
                .getDbSysConfig(SysConfigConstant.MAIL_GET_CODE, 1);
		content = String.format(content, newPassword);
		int[] teamId = null;
		int[] uid = { userId };
		try {
			List<String> mails = new ArrayList<String>();
			mails.add(userEmail);
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
//		 	msg.setText(content);
			msg.setContent(content, "text/html;charset=utf-8");
			// 设置发件人
			msg.setFrom(new InternetAddress(Email.get(0)));

			Transport transport = session.getTransport();
			// 连接邮件服务器
			try {
				transport.connect("smtp.263.net", Email.get(0), Email.get(1));
				// 发送邮件
				Address[] a = new Address[mails.size()];
				for (int i = 0; i < mails.size(); i++) {
					// 发送的邮箱
					a[i] = new InternetAddress(mails.get(i));
				}
				transport.sendMessage(msg, a);
			} catch (Exception e) {
				e.printStackTrace();
				save(uid, content, teamId, request, 2, title); // status==2 发送失败
				log.error("邮件发送失败， " + e.getMessage());
				result.setCode(CommonConstant.EXCEPTIONCODE);
				result.setMsg(CommonConstant.EMAILEXCEPTIONMSG);
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
			CpEmail email = new CpEmail();
			String findNameByUid = userRoleRightService.findNameByUid(userId);
			email.setEmailReciver(findNameByUid);
			email.setEmailTitle(title);
			email.setStatus(0);
			email.setEmailContent(content);
			mailService.add(email);
			result.setCode(CommonConstant.SUCCESSCODE);
			result.setMsg(CommonConstant.SUCCESSSTRING);
		} catch (Exception e1) {
			e1.printStackTrace();
			save(uid, content, teamId, request, 2, title); // status==2 发送失败
			log.error("邮件发送失败， " + e1.getMessage());
			result.setCode(CommonConstant.EXCEPTIONCODE);
			result.setMsg(CommonConstant.EMAILEXCEPTIONMSG);
		}
		return result;
	}
}
