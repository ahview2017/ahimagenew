package com.deepai.photo.common.util.mailOrPhone.mail;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.commons.mail.DataSourceResolver;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;
import org.apache.commons.mail.ImageHtmlEmail;
import org.apache.commons.mail.resolver.DataSourceCompositeResolver;
import org.apache.commons.mail.resolver.DataSourceFileResolver;
import org.apache.commons.mail.resolver.DataSourceUrlResolver;
import org.apache.log4j.Logger;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;

import com.deepai.photo.common.constant.MailConfig;

public class MailUtil {

	private static Logger loger = Logger.getLogger(MailUtil.class);
	
	public static void sendMail(String toEmail, String subject, String htmlContent) throws Exception {

		JavaMailSenderImpl senderImpl = new JavaMailSenderImpl();
		
		//为验证码封装html
		htmlContent = getHtmlContent(htmlContent);

		// 发送邮箱的邮件服务器
		senderImpl.setHost(MailConfig.HOST);
		// 建立邮件消息,发送简单邮件和html邮件的区别
		MimeMessage mailMessage = senderImpl.createMimeMessage();
		// 为防止乱码，添加编码集设置
		MimeMessageHelper messageHelper = new MimeMessageHelper(mailMessage, "UTF-8");
		messageHelper.setReplyTo(MailConfig.FROM);
		messageHelper.setSentDate(new Date());

		try {
			// 接收方邮箱
			messageHelper.setTo(toEmail);
		} catch (MessagingException e) {
			throw new Exception("收件人邮箱地址出错！");
		}
		try {
			// 发送方邮箱
			messageHelper.setFrom(MailConfig.FROM);
		} catch (MessagingException e) {
			throw new Exception("发件人邮箱地址出错！");
		}
		try {
			messageHelper.setSubject(subject);
		} catch (MessagingException e) {
			throw new Exception("邮件主题出错！");
		}
		try {
			// true 表示启动HTML格式的邮件
			messageHelper.setText(htmlContent, true);
		} catch (MessagingException e) {
			throw new Exception("邮件内容出错！");
		}

		Properties prop = new Properties();
		// 将这个参数设为true，让服务器进行认证,认证用户名和密码是否正确
		prop.put("mail.smtp.auth", "true");
		// 超时时间
		prop.put("mail.smtp.timeout", "25000");

		// 添加验证
		MyAuthenticator auth = new MyAuthenticator(MailConfig.USERNAME, MailConfig.PASSWORD);

		Session session = Session.getDefaultInstance(prop, auth);
		senderImpl.setSession(session);

		// 发送邮件
		senderImpl.send(mailMessage);
	}
	/**
	 * @author tuilp  
	 * @description apache common发送 
	 * @param emails
	 * @param title
	 * @param content
	 * @param user
	 * @param password
	 * @throws EmailException 
	 */
	public static void sendMail(List<String> emails,String title,String content,String user,String password ) throws EmailException{
		 // Create the email message
		  HtmlEmail email = new HtmlEmail();
		  
		  List<InternetAddress> list = new ArrayList<InternetAddress>();
		  
		  for (String str : emails) {
			  try {
				list.add(new InternetAddress(str));
			} catch (AddressException e) {
				e.printStackTrace();
			}
		  }
		  email.setHostName("smtp.163.com");
		  email.setFrom(user, "cns");
		  // set the html message
		  email.setBcc(list);
		  email.setAuthentication(user, password);
		  email.setCharset("utf-8");
		  email.setSubject(title);
		  email.setHtmlMsg(content);

		  // send the email
		  email.send();
			  
		
	}
	
	public static void main(String[] args) throws Exception {

		// 发送邮件
		// 主题
		String subject = "信息";
		// 正文
		StringBuilder builder = new StringBuilder();
		builder.append("<html><head>");
		builder.append("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\" />");
		builder.append("</head><body>");
		builder.append("您好，张三：<br />");
		builder.append("\t系统已为您重置了RUI密码，账户信息如下：<br />");
		builder.append("用户账户：zhangsan<br />用户密码：123456<br />您可以点击以下链接登录RUI：");
		builder.append("<a href=\"");
		builder.append("\">");
		builder.append("</a>");
		builder.append("</body></html>");
		String htmlContent = builder.toString();

//		MailUtil.sendMail("1041954045@qq.com", subject, htmlContent);
		MailUtil.sendMail("18618289118@163.com", subject, htmlContent);
	}
	
	/**
	 * @Title: getHtmlContent 
	 * @Description: 为验证码封装html
	 * @param ret
	 * @return String     
	 * @throws
	 */
	public static String getHtmlContent(String ret){
		StringBuilder builder = new StringBuilder();
		builder.append("<html><head>");
		builder.append("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\" />");
		builder.append("</head><body>");
		builder.append("发送的验证码为："+ret+"<br />");
		builder.append("</body></html>");
		String htmlContent = builder.toString();
		return htmlContent;
	}
	
	/**
	 * @Description: 为验证码用户绑定的email 是合法的
	 * @param email
	 * @return
	 */
	public static boolean isRightEmail(String email){
		String regex="^[a-z_0-9.-]{1,64}@([a-z0-9-]{1,200}.){1,5}[a-z]{1,6}$";
//		String regix="/^[a-z]([a-z0-9]*[-_]?[a-z0-9]+)*@([a-z0-9]*[-_]?[a-z0-9]+)+[\.][a-z]{2,3}([\.][a-z]{2})?$/i";
		 boolean flag = false;
         try{
        	 Pattern patter = Pattern.compile(regex);
             Matcher matcher = patter.matcher(email);
             flag = matcher.matches();
         } catch(Exception e){
        	 flag = false;
        	 loger.error(e.getStackTrace());
        	 e.printStackTrace();
         }
         return flag;
	}
	
}
