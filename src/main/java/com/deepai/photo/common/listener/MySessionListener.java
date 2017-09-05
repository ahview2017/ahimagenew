package com.deepai.photo.common.listener;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import com.deepai.photo.bean.CpUser;
import com.deepai.photo.common.constant.CommonConstant;
import com.deepai.photo.common.redis.RedisClientTemplate;
import com.deepai.photo.common.util.SessionUtils;

/**
 * Application Lifecycle Listener implementation class MySessionListener
 *
 */
public class MySessionListener implements HttpSessionListener {

	public void sessionCreated(HttpSessionEvent event)  { 
//		System.out.println("---");
	}

	public void sessionDestroyed(HttpSessionEvent event)  { 
		HttpSession session = event.getSession();
		RedisClientTemplate redisClientTemplate=(RedisClientTemplate)SpringContextUtil.getBean("redisClientTemplate");
		// 取得登录的用户
		CpUser user = (CpUser)session.getAttribute(SessionUtils.SESSION_USER);
		if(user!=null){
			String redisId=CommonConstant.REDISID+user.getId();
			String userToken=redisClientTemplate.get(redisId);
			redisClientTemplate.del(redisId);
			redisClientTemplate.del(userToken);
			// 从在线列表中删除用户名
			redisClientTemplate.lrem(CommonConstant.ONLINUSERLIST, 0, redisId);//移除list
//			System.out.println(user.getUserName() + "超时退出");
		}		
	}
}
