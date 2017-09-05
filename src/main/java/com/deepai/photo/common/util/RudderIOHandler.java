package com.deepai.photo.common.util;
import java.io.BufferedReader;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;

import com.deepai.photo.common.pojo.RequestMessage;
import com.deepai.photo.common.pojo.ResponseMessage;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
/**
 * 输入输出处理类
 */
public class RudderIOHandler {
	protected static final Logger logger = Logger.getLogger(RudderIOHandler.class);
	/**
	 * 得到SentBody类型
	 * @param request 请求句柄
	 * @return 消息体
	 */
	public static RequestMessage getRequestMessage(HttpServletRequest request){
		try {
			String result = null;
			String tmp = null;
			BufferedReader reader = request.getReader();
			while((tmp = reader.readLine()) != null){
				result += tmp;
			}
			reader.close();
			return getRequestMessageByString(result);
		} catch (Exception e) {
			return null;
		}
	}
	/**
	 * 根据String得到SentBody类型
	 * @param message 字符串
	 * @return 消息体
	 */
	public static RequestMessage getRequestMessageByString(String message){
		try {
			Map <String, Class> classMap = new HashMap <String, Class>();
			classMap.put("data", Map.class);
			RequestMessage requestMessage = (RequestMessage)JSONObject.toBean(JSONObject.fromObject(message), RequestMessage.class,classMap);
			return requestMessage;
		} catch (Exception e) {
			return null;
		}
	}
	/**
	 * 发送文本消息至客户端
	 * @param text 消息内容
	 * @param response 返回消息句柄
	 * @return 是否成功
	 */
	public static boolean renderText(String text, HttpServletResponse response) {
		try {
			response.getWriter().write(text);
			logger.debug("renderText:"+text);
		} catch (Exception e) {
			return false;
		}
		return true;
	}
	
	/**
	 * 发送对象Json至客户端
	 * @param responseMessage 消息对象
	 * @param response 返回消息句柄
	 * @return 是否成功
	 */
	public static boolean renderObject(ResponseMessage responseMessage, HttpServletResponse response) {
		try {
			PrintWriter printer = response.getWriter();
			Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
			String message = gson.toJson(responseMessage);
			printer.write(message);
			logger.info("renderObject:"+message);
			printer.close();
		} catch (Exception e) {
			return false;
		}
		return true;
	}
}
