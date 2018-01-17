package com.deepai.photo.common.inteceptor;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.deepai.photo.bean.CpUser;
import com.deepai.photo.common.StringUtil;
import com.deepai.photo.common.annotation.SkipAuthCheck;
import com.deepai.photo.common.annotation.SkipLoginCheck;
import com.deepai.photo.common.constant.CommonConstant;
import com.deepai.photo.common.constant.Constants;
import com.deepai.photo.common.pojo.ResponseMessage;
import com.deepai.photo.common.redis.RedisClientTemplate;
import com.deepai.photo.common.util.RudderIOHandler;
import com.deepai.photo.common.util.SessionUtils;
import com.deepai.photo.common.util.json.GsonUtil;
import com.deepai.photo.controller.util.UserUtils;
import com.deepai.photo.service.admin.UserRoleRightService;

/**
 * @author Administrator
 *
 */
public class LoginInterceptor implements HandlerInterceptor{
	private Logger log=Logger.getLogger(LoginInterceptor.class);
	
	@Autowired
	private UserRoleRightService userRoleRightService;
	@Autowired
	private RedisClientTemplate redisClientTemplate;
	
	private String userToken = "userToken";
 
	@Override
	public void afterCompletion(HttpServletRequest arg0,
			HttpServletResponse arg1, Object arg2, Exception arg3)
					throws Exception {

	}

	@Override
	public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1,
			Object arg2, ModelAndView arg3) throws Exception {

	}

	/**
	 * 在业务处理器处理请求之前被调用 如果返回false 从当前的拦截器往回执行所有拦截器的afterCompletion(),再退出拦截器链
	 * 如果返回true 执行下一个拦截器,直到所有的拦截器都执行完毕 再执行被拦截的Controller 然后进入拦截器链,
	 * 从最后一个拦截器往回执行所有的postHandle() 接着再从最后一个拦截器往回执行所有的afterCompletion()
	 *
	 * 可根据需要继续重写postHandle(显示视图前拦截)，postHandle(显示完视图后拦截)
	 */
	//登录以及权限拦截
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
			Object handler) throws Exception {
		ResponseMessage responseMessage = new ResponseMessage();
		try {
			request.setCharacterEncoding("utf-8");
			response.setContentType("text/html;charset=UTF-8");
			String requestUri = request.getRequestURI();
			CpUser user =null;
			// 排除不进行拦截的请求
			if (handler.getClass().isAssignableFrom(HandlerMethod.class)) {
				//当前站点，为空默认为主站
				int siteId=request.getParameter("sitId")==null?1:Integer.valueOf(request.getParameter("sitId"));
				SessionUtils.setSiteId(request, siteId);
				//当前语言，为空默认为中文
				int langType=(request.getParameter("langType")==null||request.getParameter("langType").equals(""))?SessionUtils.geLangType(request):Integer.valueOf(request.getParameter("langType"));
				SessionUtils.setLangType(request, langType);
				SkipLoginCheck loginCheck = ((HandlerMethod) handler).getMethodAnnotation(SkipLoginCheck.class);
				//声明不验证登录（排除登录）
				if (loginCheck != null) {
					return true;
				}else {
					String token=null;
					// 从公共获取用户信息
					Cookie[] cookie = request.getCookies();
					if(cookie!=null&&cookie.length>0){
						for (int i = 0; i < cookie.length; i++) {
							Cookie cook = cookie[i];
							if (cook.getName().equalsIgnoreCase(userToken)) { // 获取键
								token = cook.getValue().toString();
								if (StringUtil.isNotEmpty(token)) {
									user = userRoleRightService.verify(token);//校验用户是否登录
									break;
								}
							}
						}
					}
					if (user == null) {
						// 检测到登陆异常
						//add by xiayunan@20180116
						responseMessage.setCode(CommonConstant.NOTLOGINCODE);
						responseMessage.setMsg(CommonConstant.NOTLOGINMSG);
						RudderIOHandler.renderObject(responseMessage, response);
						return false;
					}else{
						SessionUtils.setUser(request, user);
						//更新用户过期时间
						String redisId=CommonConstant.REDISID+user.getId();
						expireRedisTime(redisId, token, user);
						//已登录，校验站点
						if(user.getSiteId()!=siteId){
							responseMessage.setCode(CommonConstant.NORIGHT);
							responseMessage.setMsg(String.format("您没有当前站点【%s】的操作权限",siteId));
							RudderIOHandler.renderObject(responseMessage, response);
							return false;
						}else{
							//校验权限
							SkipAuthCheck skipAuthCheck = ((HandlerMethod) handler).getMethodAnnotation(SkipAuthCheck.class);
							// 声明不验证权限 （不用校验权限）
							if (skipAuthCheck != null){
								return true;
							}else{
								// 开始进行权限验证
								requestUri=requestUri.substring(0, requestUri.lastIndexOf(".")).replace("/photo/", "");
								if (userRoleRightService.checkUserRight(user.getId(), requestUri)){
									return true;
								} else {
									log.error("权限拦截 " + user.getUserName() + " 没有权限操作:"+ requestUri);
									responseMessage.setCode(CommonConstant.NORIGHT);
									responseMessage.setMsg("您没有操作权限");
									RudderIOHandler.renderObject(responseMessage, response);
									return false;
								}
							}
						}
					}
				}
			}else{
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error("拦截器报错："+e.getMessage());
			responseMessage.setCode(CommonConstant.EXCEPTIONCODE);
			responseMessage.setMsg(CommonConstant.EXCEPTIONMSG);
			RudderIOHandler.renderObject(responseMessage, response);
			return false;
		}
	}

	
	/**
	 * 重置用户过期时间
	 * @param redisId reidsId_用户id
	 * @param token 令牌
	 */
	public void expireRedisTime(String redisId,String token,CpUser user){
		if(redisClientTemplate.exists(redisId)){
			redisClientTemplate.expire(redisId,CommonConstant.FAILUREMILLSTOKEN);
			redisClientTemplate.expire(token,CommonConstant.FAILUREMILLSTOKEN);
			redisClientTemplate.lrem(CommonConstant.ONLINUSERLIST, 0, redisId);//移除已有list
			//将在线用户id，放在list中，为在线用户列表
			redisClientTemplate.lpush(CommonConstant.ONLINUSERLIST, redisId);
		}else{
			//登录成功后，将在线用户存放在redis中
			redisClientTemplate.setex(redisId, CommonConstant.FAILUREMILLSTOKEN, token);	
			redisClientTemplate.setex(token,CommonConstant.FAILUREMILLSTOKEN,GsonUtil.toJson(user));//redis总存放在线用户信息
			redisClientTemplate.lrem(CommonConstant.ONLINUSERLIST, 0, redisId);//移除已有list
			//将在线用户id，放在list中，为在线用户列表
			redisClientTemplate.lpush(CommonConstant.ONLINUSERLIST, redisId);
		}
	}
}
