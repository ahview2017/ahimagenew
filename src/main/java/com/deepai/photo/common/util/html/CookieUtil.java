package com.deepai.photo.common.util.html;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.deepai.photo.common.util.encrypt.Encoder3DES;



/**
 * Cookie操作工具类
 * @date 2014-10-31
 */
public class CookieUtil {
	/**
	 * 设置Cookie
	 * @param request
	 * @param response
	 * @param cookieKey cookie名称
	 * @param cookieValue 加密后字符串
	 */
	public static void setCookie(HttpServletResponse response,
				String cookieKey,String cookieValue){
    	Cookie cookie = new Cookie(cookieKey,cookieValue);
    	cookie.setPath("/");
    	response.addCookie(cookie);
	}
	
	/**
	 * @param request
	 * @param cookieKey
	 * @return
	 */
	public static String getCookie(HttpServletRequest request,String cookieKey){
		    Cookie[] cookies = request.getCookies();
		    String cookieValue = null;
		    if(cookies!=null && cookies.length > 0){
		    	for(Cookie cook : cookies){
		    		if((cookieKey).equals(cook.getName())){
		    			cookieValue = cook.getValue();
		    			break;
		    		}
		    	}
		    } 
		    return cookieValue;
	}
	
	
	
	
	/**
	 * 写cookie
	 * @param name
	 * @param value
	 * @param days
	 * @throws ServletException
	 * @throws IOException
	 */
	public static void writeCokie(HttpServletRequest request, HttpServletResponse response,
			String name, String value)  {
			int day = 15 * 60 * 60;
			Cookie cookie = new Cookie(name, value);
			cookie.setMaxAge(day);
			response.addCookie(cookie);
		
	}
	
	/**
	 * 删除指定cookie
	 * @param request
	 * @param response
	 * @param name
	 * @throws ServletException
	 * @throws IOException
	 */
	public static void deleteCokie(HttpServletResponse response,
			String name) throws ServletException, IOException {
		int day = 0;
		Cookie cookie = new Cookie(name, null);
		cookie.setMaxAge(day);
		response.addCookie(cookie);
	}
	
	
	/**
	 * 清空cookie
	 */
	public static void clearCokie(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		Cookie[] cookies = request.getCookies();
		for (int i = 0; i < cookies.length; i++) {
			Cookie cookie = cookies[i];
			cookie.setMaxAge(0);
			response.addCookie(cookie);
		}
	}
	
	/**
	 * 清楚指定的cookie
	 * @param request
	 * @param response
	 * @param name
	 */
	public static void clearCookieByName(HttpServletRequest request, HttpServletResponse response,String name){
		Cookie[] cookies = request.getCookies();
		if(cookies!=null)
		for (int i = 0; i < cookies.length; i++) {
			Cookie cookie = cookies[i];
			if(name.equals(cookie.getName())){				
				response.addCookie(cookie);
				cookie.setMaxAge(0);
			}
		}
	}
	
	
	/**
	 * 根据名称读取Cookie
	 * 
	 */
	public static String readCokie(HttpServletRequest request,
			String name)  {
		String value = null;
		if (name != null) {
			Cookie cookies[] = request.getCookies();
			if (cookies != null && cookies.length >= 2) {
				for (int i = 0; i < cookies.length; i++) {
					Cookie cookie = cookies[i];
					if (name.equals(cookie.getName())) {
						value = cookie.getValue();
					}
				}
			}
		}
		return value;
	}
 
	public static void writeCookie(HttpServletResponse response,String baiduUid,String baiduUname){
	}
	/**
	 * 根据cookie校验用户是否登录过。null 未登录 ， 非null 登录过
	 * @param request
	 * @return
	 */
	public static String validateUserLogined(HttpServletRequest request){
		return null;
	}
	
	public static String getUserLoginedName(HttpServletRequest request){
		return null;
	}
	
	public static String getUid(HttpServletRequest request){
		return Encoder3DES.decrypt(readCokie(request, Encoder3DES.encrypt("uid")));
	}
	
	public static String getUname(HttpServletRequest request){
		return Encoder3DES.decrypt(readCokie(request, "uname"));
	}
}
