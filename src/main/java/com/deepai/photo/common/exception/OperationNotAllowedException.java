package com.deepai.photo.common.exception;

public class OperationNotAllowedException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public OperationNotAllowedException() {
		super();
	}

	public OperationNotAllowedException(String s) {
		super(s);
	}

	public OperationNotAllowedException(Throwable t) {
		super(t);
	}
}
