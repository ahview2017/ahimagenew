package com.deepai.photo.common.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * DBManager工具类
 * @author daichongqing
 *
 */
public class DatabaseListUtil {
	
	/**
	 * 将List<Object> 强转为List<Map<String, Object>>
	 * @param list
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static List<Map<String, Object>> convert(List<Object> list) {
		List<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();
		if (list==null) {
			return resultList;
		}
		for (int i = 0; i < list.size(); i++) {
			resultList.add((Map<String, Object>) list.get(i));
		}
		return resultList;
	}
}
