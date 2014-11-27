/**
 * HessianInterceptor.java
 * cn.vko.zuoye.common.filter.interceptor
 * Copyright (c) 2014, 北京微课创景教育科技有限公司版权所有.
*/

package cn.vko.hessian.filter.interceptor;

import java.io.IOException;

import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.Assert;

import cn.vko.hessian.core.HessianServiceConfig;
import cn.vko.hessian.filter.HessianFilterHandler;

import com.vko.core.web.filter.FilterInvocation;
import com.vko.core.web.filter.interceptor.FilterInterceptor;

/**
 * 处理hessian请求
 * <p>
 * @author   宋汝波
 * @Date	 2014-7-4 	 
 */
public class HessianInterceptor implements FilterInterceptor, InitializingBean {
	private HessianFilterHandler handler;
	private HessianServiceConfig config;

	@Override
	public void invoke(FilterInvocation filterInvocation) throws IOException, ServletException {
		HttpServletRequest request = filterInvocation.getRequest();
		HttpServletResponse response = filterInvocation.getResponse();
		if (!"x-application/hessian".equals(request.getContentType())) {
			filterInvocation.doInterceptor();
			return;
		}
		handler.service(request, response);
	}

	@Override
	public void init(FilterConfig filterConfig) {

	}

	@Override
	public void destroy() {

	}

	@Override
	public void afterPropertiesSet() throws Exception {
		Assert.notNull(config, "配置不能为null");
		handler = new HessianFilterHandler(config);

	}

	public HessianServiceConfig getConfig() {
		return config;
	}

	public void setConfig(HessianServiceConfig config) {
		this.config = config;
	}

}
