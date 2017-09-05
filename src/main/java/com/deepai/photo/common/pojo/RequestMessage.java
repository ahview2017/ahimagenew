package com.deepai.photo.common.pojo;

import java.io.Serializable;
import java.util.Map;

/**
 * 发送消息体
 * @author zhangzhizhi
 * @version 1.0
 */
public class RequestMessage implements Serializable{
	private static final long serialVersionUID = 7230200166188618904L;
	/** 验证的id */
	private String UUID;
	/** 请求id */
	private String requestId;
	/** 请求类型 */
	private String key;
	/** 附带的值 */
	private Map<String, String> data;
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public Map<String, String> getData() {
		return data;
	}
	public void setData(Map<String, String> data) {
		this.data = data;
	}
	public String getUUID() {
		return UUID;
	}
	public void setUUID(String uUID) {
		UUID = uUID;
	}
	public String getRequestId() {
		return requestId;
	}
	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}	
}
