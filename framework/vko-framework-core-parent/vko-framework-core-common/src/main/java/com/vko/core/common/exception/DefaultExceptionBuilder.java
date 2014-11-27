package com.vko.core.common.exception;

import java.io.PrintWriter;
import java.io.StringWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.vko.core.common.environment.EnvironmentDetect;
import com.vko.core.common.environment.RunEnvironment.Environment;

public class DefaultExceptionBuilder implements ExceptionBuilder {

	@Override
	public String buildException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
		if (Environment.PRODUCT == EnvironmentDetect.detectEnvironment()) {
			// 自动注入request,response,session,application
			return ex.getMessage();
		}
		StringWriter writer = new StringWriter(500);
		ex.printStackTrace(new PrintWriter(writer));
		return writer.toString();
	}

}
