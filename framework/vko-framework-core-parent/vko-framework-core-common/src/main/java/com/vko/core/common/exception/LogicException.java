package com.vko.core.common.exception;

public class LogicException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1866173300240982923L;

	private final String message;
	private final int code;

	public LogicException(String message) {
		this.message = message;
		this.code = -1;
	}

	@Override
	public synchronized Throwable initCause(Throwable cause) {

		return null;
	}

	public LogicException(String message, int code) {
		this.message = message;
		this.code = code;
	}

	public LogicException(int code) {
		this.code = code;
		this.message = "";
	}

	/*
	 * 重写,用不到堆栈信息
	 */
	@Override
	public synchronized Throwable fillInStackTrace() {

		return null;
	}

	@Override
	public String getMessage() {
		return message;
	}

	public int getCode() {
		return code;
	}

}
