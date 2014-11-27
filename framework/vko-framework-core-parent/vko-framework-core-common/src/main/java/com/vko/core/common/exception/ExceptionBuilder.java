package com.vko.core.common.exception;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface ExceptionBuilder {

	public String buildException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex);
}
