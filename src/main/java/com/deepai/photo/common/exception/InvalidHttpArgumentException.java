package com.deepai.photo.common.exception;

public class InvalidHttpArgumentException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	private int code;
	private String msg;
	
	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}
	
	

	public InvalidHttpArgumentException() {
		super();
	}
	
	public InvalidHttpArgumentException(String s) {		
		super(s);
		this.msg=s;
	}
	
	public InvalidHttpArgumentException(int code,String s) {		
		super(s);
		this.code=code;
		this.msg=s;
	}
	
	public InvalidHttpArgumentException(Throwable t) {
		super(t);
	}
	
	public InvalidHttpArgumentException(String msg, Throwable t) {
		super(msg, t);
		this.msg=msg;
	}
	

}
