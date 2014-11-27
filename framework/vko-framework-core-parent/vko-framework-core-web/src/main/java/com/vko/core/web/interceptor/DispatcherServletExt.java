/**
 * DispatcherServletExt.java
 * cn.vko.fz.manage.filter
 * Copyright (c) 2014, 北京微课创景教育科技有限公司版权所有.
*/

package com.vko.core.web.interceptor;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

/**
 * 扩展DispatcherServlet,能够用另一种拦截器的方式控制
 * <p>
 * @author   宋汝波
 * @Date	 2014-6-4 	 
 */
public class DispatcherServletExt extends DispatcherServlet {

	/**
	 */
	private Logger log = LoggerFactory.getLogger(this.getClass());
	private ServletInterceptor[] interceptor;
	private static final long serialVersionUID = -3902581886695402142L;

	@Override
	protected void initFrameworkServlet() throws ServletException {
		WebApplicationContext application = this.getWebApplicationContext();
		try {
			interceptor = application.getBean(ServletInterceptorConfig.class).getInterceptors();
		} catch (BeansException e) {
			log.warn("DispatcherServlet无拦截器");
		}
	}

	@Override
	protected void doService(HttpServletRequest request, HttpServletResponse response) throws Exception {
		if (interceptor != null) {
			ServletInvocation invocation = new ServletInvocation(request, response, this, interceptor);
			invocation.doInterceptor();
		} else {
			super.doService(request, response);
		}
	}

	public void proceed(HttpServletRequest request, HttpServletResponse response) throws Exception {
		super.doService(request, response);
	}
}
