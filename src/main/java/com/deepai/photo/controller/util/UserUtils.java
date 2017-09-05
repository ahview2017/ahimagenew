package com.deepai.photo.controller.util;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.deepai.photo.bean.CpRole;
import com.deepai.photo.bean.CpUser;

/**
 * @author guoyanhong
 *
 */
public class UserUtils {
	
	/**
	 * 设置在线用户
	 * @param user
	 * @param roles
	 * @return
	 */
	public static String setOnUserInfo(CpUser user){
		StringBuffer userinfo= new StringBuffer();
		userinfo.append(user.getId()).append(";").append(user.getUserName()).append(";").append(user.getRolenames());
		return userinfo.toString();
	}
	/**
	 * 设置用户角色名
	 * @param roles
	 * @return
	 */
	public static String setUserRoleNames(List<CpRole> roles){
		StringBuffer userinfo= new StringBuffer();
		if(roles!=null){
			for (int i = 0; i < roles.size(); i++) {
				userinfo.append(roles.get(i).getRoleName()).append(",");
			}
		}
		return userinfo.toString();
	}
	/**
	 * 获取项目路径
	 * @param roles
	 * @return
	 */
	public static String getHttpUrl(HttpServletRequest request){
		StringBuffer url=request.getRequestURL();
		String u=url.substring(0, url.indexOf(request.getContextPath()));
		return u;
	}
	/**
	 * 获取登录用户的ip
	 * @param request
	 * @return
	 */
	 public static String getLocalIp(HttpServletRequest request) {
		 String ip = request.getHeader("X-Real-IP");
	        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
	            ip = request.getHeader("x-forwarded-for");
	        }
		    if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) { 
		        ip = request.getHeader("Proxy-Client-IP"); 
		    } 
		    if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) { 
		        ip = request.getHeader("WL-Proxy-Client-IP"); 
		    } 
		    if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) { 
		        ip = request.getRemoteAddr(); 
		    } 
		    return ip; 
	    }
}
