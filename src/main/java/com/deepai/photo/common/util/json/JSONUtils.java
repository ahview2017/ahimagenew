package com.deepai.photo.common.util.json;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.sf.json.JSON;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.xml.XMLSerializer;

/**
 * JSON转换工具类
 * 
 * @author dqqiu
 * @createDate 2015/06/10
 */
@SuppressWarnings("unchecked")
public class JSONUtils {
	/**
	 * 集合转JSONArray对象
	 * 
	 * @param list
	 *            集合
	 * @return JSONArray对象
	 */
	public static JSONArray listToJSONArray(List<?> list) {
		return JSONArray.fromObject(list);
	}

	/**
	 * 集合转JSONArray字符串
	 * 
	 * @param list
	 *            集合
	 * @return JSONArray字符串
	 */
	public static String listToJSONArrayString(List<?> list) {
		return JSONArray.fromObject(list).toString();
	}

	/**
	 * 对象转JSONObject
	 * 
	 * @param object
	 *            对象
	 * @return JSONObject对象
	 */
	public static <T> JSONObject objectToJSON(T object) {
		return JSONObject.fromObject(object);
	}

	/**
	 * 对象转JSONObject字符串
	 * 
	 * @param object
	 *            对象
	 * @return JSONObject字符串
	 */
	public static <T> String objectToJSONObjectString(T object) {
		return JSONObject.fromObject(object).toString();
	}

	/**
	 * Map集合转JSONArray
	 * 
	 * @param map
	 *            Map集合
	 * @return JSONArray对象
	 */
	public static JSONArray mapToJSONArray(Map<?, ?> map) {
		return JSONArray.fromObject(map);
	}

	/**
	 * Map集合转JSONArray字符串
	 * 
	 * @param map
	 *            Map集合
	 * @return JSONArray字符串
	 */
	public static String mapToJSONArrayString(Map<?, ?> map) {
		return JSONArray.fromObject(map).toString();
	}

	/**
	 * JSONObject对象转成java中的对象
	 * 
	 * @param json
	 *            JSONObject对象
	 * @param clz
	 *            泛型对象具体类class
	 * @return java中的对象
	 */
	public static <T> T JSONObjectToBean(JSONObject json,
			@SuppressWarnings("rawtypes") Class clz) {
		return (T) JSONObject.toBean(json, clz);
	}

	/**
	 * JSONObject字符串对象转成java中的对象
	 * 
	 * @param json
	 *            JSONObject字符串
	 * @param clz
	 *            泛型对象具体类class
	 * @return java中的对象
	 */
	public static <T> T JSONObjectStringToBean(String jsonStr,
			@SuppressWarnings("rawtypes") Class clz) {
		return (T) JSONObject.toBean(JSONObject.fromObject(jsonStr), clz);
	}

	/**
	 * JSONArray对象转java中的泛型集合对象
	 * 
	 * @param array
	 *            JSONArray对象
	 * @param clz
	 *            泛型集合所对应的具体类的class
	 * @return java中的泛型集合对象
	 */
	@SuppressWarnings("deprecation")
	public static <T> List<T> JSONArrayToList(JSONArray array,
			@SuppressWarnings("rawtypes") Class clz) {
		return JSONArray.toList(array, clz);
	}

	/**
	 * JSONArray字符串转java中的泛型集合对象
	 * 
	 * @param array
	 *            JSONArray字符串
	 * @param clz
	 *            泛型集合所对应的具体类的class
	 * @return java中的泛型集合对象
	 */
	@SuppressWarnings("deprecation")
	public static <T> List<T> JSONArrayStringToList(String arrayStr,
			@SuppressWarnings("rawtypes") Class clz) {
		return JSONArray.toList(JSONArray.fromObject(arrayStr), clz);
	}

	@SuppressWarnings("rawtypes")
	public static Map JSONArrayToMap(JSONObject json) {
		Map map = new HashMap();
		Iterator it = json.keys();
		while (it.hasNext()) {
			String key = (String) it.next();
			map.put(key, json.get(key));
		}
		return map;
	}

	/**
	 * JSONObject对象转XML
	 * 
	 * @param json
	 *            JSONObject对象
	 * @return 转换后所得到的xml字符串
	 */
	public static String JSONObjectStringToXML(String json) {
		XMLSerializer xml = new XMLSerializer();
		xml.setTypeHintsEnabled(true);
		JSONObject jsonObj = JSONObject.fromObject(json);
		String xmlDoc = xml.write(jsonObj);
		return xmlDoc;
	}

	/**
	 * JSONObject对象转XML
	 * 
	 * @param json
	 *            JSONObject对象
	 * @return 转换后所得到的xml字符串
	 */
	public static String JSONObjectStringToXML(String json, String objectName) {
		XMLSerializer xml = new XMLSerializer();
		xml.setObjectName(objectName);
		xml.setTypeHintsEnabled(false);
		JSONObject jsonObj = JSONObject.fromObject(json);
		String xmlDoc = xml.write(jsonObj);
		return xmlDoc;
	}

	/**
	 * JSON字符串对象转XML
	 * 
	 * @param json
	 *            JSON字符串
	 * @param objectName
	 *            生成对象的元素标签的名称，默认为o
	 * @return 转换后所得到的xml字符串
	 */
	public static String JSONObjectToXML(JSONObject json, String objectName) {
		XMLSerializer xml = new XMLSerializer();
		xml.setObjectName(objectName);
		xml.setTypeHintsEnabled(true);
		String xmlDoc = xml.write(json);
		return xmlDoc;
	}

	/**
	 * JSON字符串对象转XML
	 * 
	 * @param json
	 *            JSON字符串
	 * @return 转换后所得到的xml字符串
	 */
	public static String JSONObjectToXML(JSONObject json) {
		XMLSerializer xml = new XMLSerializer();
		xml.setTypeHintsEnabled(true);
		String xmlDoc = xml.write(json);
		return xmlDoc;
	}

	/**
	 * xml字符串转JSONObject对象
	 * 
	 * @param xml
	 *            xml字符串
	 * @return JSONObject对象
	 */
	public static JSONObject XMLToJSONObject(String xml) {
		XMLSerializer serializer = new XMLSerializer();
		serializer.setTypeHintsEnabled(true);
		JSON json = serializer.read(xml);
		return (JSONObject) json;
	}

	/**
	 * xml字符串转JSONObject字符串
	 * 
	 * @param xml
	 *            xml字符串
	 * @return JSONObject字符串
	 */
	public static String XMLToJSONObjectString(String xml) {
		XMLSerializer serializer = new XMLSerializer();
		serializer.setTypeHintsEnabled(true);
		JSON json = serializer.read(xml);
		return json.toString();
	}

	/*
	 * string类型的json转化为map对象
	 */
	public static List<Map<String, String>> jsonStringToList(String rsContent) {
		JSONArray arry = JSONArray.fromObject(rsContent);
		List<Map<String, String>> rsList = new ArrayList<Map<String, String>>();
		for (int i = 0; i < arry.size(); i++) {
			JSONObject jsonObject = arry.getJSONObject(i);
			Map<String, String> map = new HashMap<String, String>();
			for (Iterator<?> iter = jsonObject.keys(); iter.hasNext();) {
				String key = (String) iter.next();
				String value = jsonObject.get(key).toString();
				map.put(key, value);
			}
			rsList.add(map);
		}
		return rsList;
	}
	
	
	
	/*
	 * string类型的json转化为map对象
	 */
	public static List<Map<String, Object>> jsonToList(String rsContent) {
		JSONArray arry = JSONArray.fromObject(rsContent);
		List<Map<String, Object>> rsList = new ArrayList<Map<String, Object>>();
		for (int i = 0; i < arry.size(); i++) {
			JSONObject jsonObject = arry.getJSONObject(i);
			Map<String, Object> map = new HashMap<String, Object>();
			for (Iterator<?> iter = jsonObject.keys(); iter.hasNext();) {
				String key = (String) iter.next();
				Object value = jsonObject.get(key);
				map.put(key, value);
			}
			rsList.add(map);
		}
		return rsList;
	}

	public static Map<String, Object> jsonToObject(String jsonStr)
			throws Exception {
		JSONObject jsonObj = JSONObject.fromObject(jsonStr);
		Iterator<String> nameItr = jsonObj.keys();
		String name;
		Map<String, Object> outMap = new HashMap<String, Object>();
		while (nameItr.hasNext()) {
			name = nameItr.next();
			outMap.put(name, jsonObj.get(name));
		}
		return outMap;
	}

	/**
	 * 将java对象转换成json字符串
	 *
	 * @param bean
	 * @return
	 */
	public static String beanToJson(Object bean) {

		JSONObject json = JSONObject.fromObject(bean);

		return json.toString();

	}

	public static String beanListToJson(List<?> beans) {

		StringBuffer rest = new StringBuffer();

		rest.append("[");

		int size = beans.size();

		for (int i = 0; i < size; i++) {

			rest.append(beanToJson(beans.get(i)) + ((i < size - 1) ? "," : ""));

		}

		rest.append("]");

		return rest.toString();

	}
}
