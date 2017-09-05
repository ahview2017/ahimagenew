package com.deepai.photo.common.pojo;

import java.io.Serializable;

/**
 * 返回消息体类
 * @author guoyanhong
 * @version 1.0
 */
public class ResponseMessage implements Serializable{
	private static final long serialVersionUID = -6214499917233203828L;
	/** 返回代码 */
	private Integer code;
	/** 返回信息 */
	private String msg;
	/** 返回数据 */
	private Object data;
	private Object page;
	private Object other;
	
	
	
	public Object getOther() {
		return other;
	}
	public void setOther(Object other) {
		this.other = other;
	}
	public Object getPage() {
		return page;
	}
	public void setPage(Object page) {
		this.page = page;
	}
	public Integer getCode() {
		return code;
	}
	public void setCode(Integer code) {
		this.code = code;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public Object getData() {
		return data;
	}
	public void setData(Object data) {
		this.data = data;
	}
}
