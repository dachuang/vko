/**
 * ServletInvacation.java
 * com.vko.core.web.interceptor
 * Copyright (c) 2014, 北京微课创景教育科技有限公司版权所有.
*/

package com.vko.core.web.interceptor;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 拦截器调用实现
 * <p>
 * @author   宋汝波
 * @Date	 2014-8-1 	 
 */
public class ServletInvocation {
	HttpServletRequest request;
	HttpServletResponse response;
	ServletInterceptor[] interceptor;
	DispatcherServletExt servlet;
	int index = 0;

	public ServletInvocation(HttpServletRequest request, HttpServletResponse response, DispatcherServletExt servlet,
			ServletInterceptor[] interceptor) {
		this.request = request;
		this.response = response;
		this.servlet = servlet;
		this.interceptor = interceptor;
	}

	public void doInterceptor() throws Exception {
		if (interceptor == null) {
			skipInterceptor();
			return;
		}
		if (interceptor.length > index) {
			interceptor[index++].invoke(this);
		} else {
			skipInterceptor();
		}

	}

	public void skipInterceptor() throws Exception {
		servlet.proceed(request, response);
	}

	public HttpServletRequest getRequest() {
		return request;
	}

	public HttpServletResponse getResponse() {
		return response;
	}

	public ServletContext getServletContext() {
		return servlet.getServletContext();
	}

}
