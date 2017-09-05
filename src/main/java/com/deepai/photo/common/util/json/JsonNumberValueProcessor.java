package com.deepai.photo.common.util.json;

import net.sf.json.JsonConfig;
import net.sf.json.processors.JsonValueProcessor;

/**
 * 长整形按字符串处理
 * 解决页面JS取LONG失精度的问题
 * @Author:songyunhu
 * @CreateDate:2015-7-23 上午10:23:55
 */
public class JsonNumberValueProcessor implements JsonValueProcessor {

	private Object process(Object value) {
		return value == null ? "" : value.toString();
	}

	@Override
	public Object processArrayValue(Object value, JsonConfig config) {
		return process(value);
	}

	@Override
	public Object processObjectValue(String key, Object value, JsonConfig config) {
		return process(value);
	}

}
