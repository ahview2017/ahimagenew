package com.deepai.photo.common.util.html;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.deepai.photo.common.util.json.GsonUtil;


public class HtmlUtil {
	protected final static Logger logger = Logger.getLogger(HtmlUtil.class);

	public static void writerJson(HttpServletResponse response, HttpServletRequest request, String jsonStr) {
		writer(response, request, jsonStr);
	}

	public static void writerJson(HttpServletResponse response, HttpServletRequest request, Object object) {
		response.setContentType("application/json");
		writer(response, request, GsonUtil.toJson(object));
	}

	public static void IEwriter2Json(HttpServletResponse response, HttpServletRequest request, Object object) {
		response.setContentType("text/plain");
		writer(response, request, GsonUtil.toJson(object));
	}

	public static void writerJson(HttpServletResponse response, HttpServletRequest request, Object object,
			String dateFormat) {
		response.setContentType("application/json");
		writer(response, request, GsonUtil.toJson(object, dateFormat));
	}

	public static void writerHtml(HttpServletResponse response, HttpServletRequest request, String htmlStr) {
		writer(response, request, htmlStr);
	}

	private static void writer(HttpServletResponse response, HttpServletRequest request, String str) {
		try {
			// 设置页面不缓存
			response.setHeader("Pragma", "No-cache");
			response.setHeader("Cache-Control", "no-cache");
			/*response.setHeader("Access-Control-Allow-Origin", request.getHeader("Origin"));
	        response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
	        response.setHeader("Access-Control-Max-Age", "3600");
	        response.setHeader("Access-Control-Allow-Headers", "x-requested-with");
	        response.setHeader("Access-Control-Allow-Credentials","true"); //是否支持cookie跨域
*/			response.setCharacterEncoding("UTF-8");
			PrintWriter out = null;
			out = response.getWriter();
			out.print(str);
			out.flush();
			out.close();
			// log.info("writer:" + str);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
