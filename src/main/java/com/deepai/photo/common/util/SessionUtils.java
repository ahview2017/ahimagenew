package com.deepai.photo.common.util;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.deepai.photo.bean.CpSite;
import com.deepai.photo.bean.CpUser;
import com.deepai.photo.common.redis.RedisClientTemplate;

/**
 * 
 * Cookie 工具类
 *
 */
public final class SessionUtils {

	protected static final Logger logger = Logger.getLogger(SessionUtils.class);

	public static final String SESSION_USER = "session_user";
	public static final String SESSION_SITE = "session_site";
	public static final String SESSION_SITE_ID = "session_site_id";
	public static final String SESSION_LANGTYPE = "session_langType";

	public static final String SESSION_MANAGE_USER = "session_manage_user";

	public static final String SESSION_USER_HOTEL = "session_user_hotel";

	public static final String SESSION_SUPPLIER_USER = "session_supplier_user";

	public static final String SESSION_AGENT_USER = "session_agent_user";

	public static final String SESSION_USER_AGENT = "session_user_agent";

	public static final String SESSION_ORGANIZATION_ID = "session_organization_id";

	public static final String SESSION_VALIDATECODE = "session_validatecode";// 验证码

	public static final String SESSION_ACCESS_URLS = "session_access_urls"; // 系统能够访问的URL

	public static final String SESSION_MENUBTN_MAP = "session_menubtn_map"; // 系统菜单按钮

	public static final String SESSION_USER_ACCESS = "session_user_access"; // 系统菜单按钮

	/**
	 * 设置session的值
	 * 
	 * @param request
	 * @param key
	 * @param value
	 */
	public static void setAttr(HttpServletRequest request, String key,
			Object value) {
		request.getSession(true).setAttribute(key, value);
	}

	/**
	 * 设置session的值
	 * 
	 * @param redisClientTemplate
	 * @param key
	 * @param value
	 */
	public static void setAttr(RedisClientTemplate redisClientTemplate,
			String key, Object value) {
		redisClientTemplate.set(key.getBytes(), SerializeUtil.serialize(value));
	}

	/**
	 * 获取当前用户权限
	 * 
	 * @param request
	 * @param key
	 */
	@SuppressWarnings("unchecked")
	public static List<String> getAccess(HttpServletRequest request) {
		return (List<String>) request.getSession(true).getAttribute(
				SESSION_USER_ACCESS);
	}

	/**
	 * 获取当前用户权限
	 * 
	 * @param request
	 * @param key
	 */
	public static void setAccess(HttpServletRequest request,
			List<String> accessList) {
		request.getSession(true).setAttribute(SESSION_USER_ACCESS, accessList);
	}

	/**
	 * 获取session的值
	 * 
	 * @param request
	 * @param key
	 */
	public static Object getAttr(HttpServletRequest request, String key) {
		return request.getSession(true).getAttribute(key);
	}

	/**
	 * 获取session的值
	 * 
	 * @param redisClientTemplate
	 * @param key
	 */
	public static Object getAttr(RedisClientTemplate redisClientTemplate,
			String key) {
		return redisClientTemplate.get(key.getBytes());
	}

	/**
	 * 删除Session值
	 * 
	 * @param request
	 * @param key
	 */
	public static void removeAttr(HttpServletRequest request, String key) {
		request.getSession(true).removeAttribute(key);
	}

	/**
	 * 删除Session值
	 * 
	 * @param redisClientTemplate
	 * @param key
	 */
	public static void removeAttr(RedisClientTemplate redisClientTemplate,
			String key) {
		redisClientTemplate.del(key);
	}

	/**
	 * 设置用户信息 到session
	 * 
	 * @param request
	 * @param user
	 */
	public static void setUser(HttpServletRequest request, CpUser user) {
		request.getSession(true).setAttribute(SESSION_USER, user);
		request.getSession(true).setMaxInactiveInterval(1800);
	}

	/**
	 * 
	 * @Title: getUser
	 * @Description: 获取用户信息
	 * @param request
	 * @return WjUser
	 * @author hongkai
	 * @throws
	 */
	public static CpUser getUser(HttpServletRequest request) {
		CpUser user = (CpUser) request.getSession(true).getAttribute(
				SESSION_USER);
		return user;

	}

	/**
	 * 从session中去除用户信息
	 * 
	 * @param request
	 * @return SysUser
	 */
	public static void removeUser(HttpServletRequest request) {
		removeAttr(request, SESSION_USER);
	}
	/**
	 * 设置site信息 到session
	 * 
	 * @param request
	 * @param user
	 */
	public static void setSite(HttpServletRequest request, CpSite site) {
		request.getSession(true).setAttribute(SESSION_SITE, site);
		request.getSession(true).setMaxInactiveInterval(1800);
	}
	
	/**
	 * 
	 * @Description: 获取site信息
	 * @throws
	 */
	public static CpSite getSite(HttpServletRequest request) {
		CpSite site = (CpSite) request.getSession(true).getAttribute(SESSION_SITE);
		return site;
		
	}
	
	/**
	 * 从session中去除site信息
	 * 
	 * @param request
	 * @return SysUser
	 */
	public static void removeSite(HttpServletRequest request) {
		removeAttr(request, SESSION_SITE);
	}
	/**
	 * 设置site信息 到session
	 * 
	 * @param request
	 * @param user
	 */
	public static void setSiteId(HttpServletRequest request, int siteId) {
		request.getSession(true).setAttribute(SESSION_SITE_ID, siteId);
		request.getSession(true).setMaxInactiveInterval(1800);
	}
	
	/**
	 * 
	 * @Description: 获取site信息
	 * @throws
	 */
	public static int getSiteId(HttpServletRequest request) {
		Integer siteId = (Integer) request.getSession(true).getAttribute(SESSION_SITE_ID);
		return siteId==null?1:siteId;//为空默认为主站
		
	}
	/**
	 * 设置langType信息 到session
	 * 
	 * @param request
	 * @param user
	 */
	public static void setLangType(HttpServletRequest request, int langType) {
		request.getSession(true).setAttribute(SESSION_LANGTYPE, langType);
		request.getSession(true).setMaxInactiveInterval(1800);
	}
	
	/**
	 * 
	 * @Description: 获取langType信息
	 * @throws
	 */
	public static int geLangType(HttpServletRequest request) {
		Integer langType = (Integer) request.getSession(true).getAttribute(SESSION_LANGTYPE);
		return langType==null?0:langType;//为空默认为中文
		
	}
	
	/**
	 * 从session中去除site信息
	 * 
	 * @param request
	 * @return SysUser
	 */
	public static void removeSiteId(HttpServletRequest request) {
		removeAttr(request, SESSION_SITE_ID);
	}

	/**
	 * 清空session
	 * 
	 * @param request
	 * @return SysUser
	 */
	public static void removeSession(HttpServletRequest request) {
		request.getSession(true).invalidate();
	}

	/**
	 * 设置session过期时间
	 * 
	 * @param request
	 * @return SysUser
	 */
	public static void setSesstionAvailableTime(HttpServletRequest request,
			int second) {
		request.getSession().setMaxInactiveInterval(second);
	}

	/**
	 * 设置验证码 到session
	 * 
	 * @param request
	 * @param user
	 */
	public static void setValidateCode(HttpServletRequest request,
			String validateCode) {
		request.getSession(true).setAttribute(SESSION_VALIDATECODE,
				validateCode);
	}

	/**
	 * 从session中获取验证码
	 * 
	 * @param request
	 * @return SysUser
	 */
	public static String getValidateCode(HttpServletRequest request) {
		return (String) request.getSession(true).getAttribute(
				SESSION_VALIDATECODE);
	}

	/**
	 * 从session中获删除验证码
	 * 
	 * @param request
	 * @return SysUser
	 */
	public static void removeValidateCode(HttpServletRequest request) {
		removeAttr(request, SESSION_VALIDATECODE);
	}

	/**
	 * 判断当前登录用户是否超级管理员
	 *
	 * @param request
	 * @return
	 */
	public static boolean isAdmin(HttpServletRequest request) { //
		// 判断登录用户是否超级管理员
		CpUser user = getUser(request);
		if (user == null || user.getId() != 1) {
			return false;
		}
		return true;
	}

	/**
	 * 判断当前登录用户是否超级管理员
	 * 
	 * @param request
	 * @return
	 */
	public static void setAccessUrl(HttpServletRequest request,
			List<String> accessUrls) { // 判断登录用户是否超级管理员
		setAttr(request, SESSION_ACCESS_URLS, accessUrls);
	}

	/**
	 * 判断URL是否可访问
	 * 
	 * @param request
	 * @return
	 */
	public static boolean isAccessUrl(HttpServletRequest request, String url) {
		List<String> accessUrls = (List) getAttr(request, SESSION_ACCESS_URLS);
		if (accessUrls == null || accessUrls.isEmpty()
				|| !accessUrls.contains(url)) {
			return false;
		}
		return true;
	}

	/**
	 * 设置菜单按钮
	 * 
	 * @param request
	 * @param btnMap
	 */
	public static void setMemuBtnMap(HttpServletRequest request,
			Map<String, List> btnMap) { // 判断登录用户是否超级管理员
		setAttr(request, SESSION_MENUBTN_MAP, btnMap);
	}

	/**
	 * 获取菜单按钮
	 * 
	 * @param request
	 * @param btnMap
	 */
	public static List<String> getMemuBtnListVal(HttpServletRequest request,
			String menuUri) { // 判断登录用户是否超级管理员
		Map btnMap = (Map) getAttr(request, SESSION_MENUBTN_MAP);
		if (btnMap == null || btnMap.isEmpty()) {
			return null;
		}
		return (List<String>) btnMap.get(menuUri);
	}

	// public static final String SESSION_ACCESS_URLS = "session_access_urls";
	// //系统能够访问的URL
	//
	//
	// public static final String SESSION_MENUBTN_MAP = "session_menubtn_map";
	// //系统菜单按钮

}