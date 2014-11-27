package com.vko.core.web.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.vko.core.web.filter.interceptor.FilterInterceptor;
import com.vko.core.web.filter.interceptor.FilterInterceptorConfig;

public final class CoreFilter implements Filter {
	private static final Logger logger = LoggerFactory.getLogger(CoreFilter.class);
	private static final String KEY_ENCODING = "encoding";
	// 通过配置设置的编码信息,可以替代spring自带的编码过滤
	private String encoding = null;
	// 过滤接口
	private FilterInterceptor[] interceptor = null;
	// servlet上下文
	private ServletContext context = null;

	public static final String INVOCATION_KEY = "$$spring-mvc.invocation";

	@Override
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException,
			ServletException {
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) res;
		// 设置编码
		if (encoding != null) {
			request.setCharacterEncoding(encoding);
		}
		try {
			DefaultFilterInvocation invocation = null;
			invocation = new DefaultFilterInvocation(request, response, chain, interceptor, context);
			request.setAttribute(INVOCATION_KEY, invocation);
			InvocationUtils.bindRequestToCurrentThread(request);
			// 调用拦截器
			invocation.doInterceptor();
		} finally {
			InvocationUtils.unindRequestFromCurrentThread();
		}

	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		context = filterConfig.getServletContext();
		encoding = filterConfig.getInitParameter(KEY_ENCODING);
		// context-param -> listener -> filter -> servlet

		ConfigurableApplicationContext applicationContext = (ConfigurableApplicationContext) WebApplicationContextUtils
				.getWebApplicationContext(context);
		DefaultListableBeanFactory beanFactory = (DefaultListableBeanFactory) applicationContext.getBeanFactory();
		BeanDefinitionBuilder builder = BeanDefinitionBuilder.rootBeanDefinition(InvocationLocalImpl.class);
		beanFactory.registerBeanDefinition("InvocationLocalImpl", builder.getBeanDefinition());
		try {
			interceptor = applicationContext.getBean(FilterInterceptorConfig.class).getFilterInterceptors();
			for (int i = 0; i < interceptor.length; i++) {
				interceptor[i].init(filterConfig);
			}
		} catch (Exception e) {
			logger.warn("无拦截器配置");
		}
	}

	@Override
	public void destroy() {
		if (interceptor != null) {
			for (int i = 0; i < interceptor.length; i++) {
				interceptor[i].destroy();
			}
		}

	}

}
