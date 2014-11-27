package com.vko.core.common.exception;

/**
 * 构想通用业务服务层未检查异常封装
 * 
 * @author faker
 *
 */

public class VkoServiceException extends RuntimeException {
	private static final long serialVersionUID = -2089833235564087572L;
	private String code;

	private Object[] args;

	public VkoServiceException() {
		super();
	}

	public VkoServiceException(String message, Throwable cause) {
		super(message, cause);
	}

	public VkoServiceException(String message) {
		super(message);
	}

	@Override
	public synchronized Throwable fillInStackTrace() {

		return null;

	}

	public VkoServiceException(Throwable cause) {
		super(cause);
	}

	public VkoServiceException(String code, String message) {
		super(message);
		this.code = code;
		this.args = new Object[] {};
	}

	public String getCode() {
		return code;
	}

	public Object[] getArgs() {
		return args;
	}

}
