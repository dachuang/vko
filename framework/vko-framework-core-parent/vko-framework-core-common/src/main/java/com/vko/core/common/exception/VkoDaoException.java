package com.vko.core.common.exception;

/**
 * 构想通用数据访问层未检查异常封装
 * 
 * @author faker
 *
 */
public class VkoDaoException extends RuntimeException {
	private static final long serialVersionUID = -7577296948595733704L;

	private String code;

	public VkoDaoException() {
		super();
	}

	public VkoDaoException(String message) {
		super(message);
	}

	public VkoDaoException(String message, Throwable throwable) {
		super(message, throwable);
	}

	public VkoDaoException(Throwable cause) {
		super(cause);
	}

	public VkoDaoException(String code, String message) {
		super(message);
		this.code = code;
	}

	public String getCode() {
		return code;
	}
}
