package com.test.java;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.util.JSON;
import com.deepai.photo.common.util.mailOrPhone.mail.MailUtil;

@RunWith(SpringJUnit4ClassRunner.class)     //表示继承了SpringJUnit4ClassRunner类  
@ContextConfiguration(locations = {"classpath:spring-mybatis.xml"})  
public class UserServiceTest {
	
	@Test
	public void testSendMail(){
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

		try {
			MailUtil.sendMail("1041954045@qq.com", subject, htmlContent);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


}
