package com.vko.core.web.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.vko.core.common.util.HttpUtil;

/**
 * Test performance of how long a request handles.
 * 
 * @author sunyi
 * 
 * @spring.bean id="performanceInterceptor"
 */
public class PerformanceHandlerInterceptor implements HandlerInterceptor {
	private static final Logger logger = LoggerFactory
			.getLogger(PerformanceHandlerInterceptor.class);
	private static final String START_TIME = "perf_start";

	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		request.setAttribute(START_TIME, System.currentTimeMillis());
		return true;
	}

	public void postHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		/* Nothing to do */
	}

	public void afterCompletion(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		Long startTime = (Long) request.getAttribute(START_TIME);
		if (startTime != null) {
			long last = System.currentTimeMillis() - startTime.longValue();
			logger.info("Request for URI: " + HttpUtil.getURL(request));
			logger.info("Performace: " + last + "ms.");
		}
	}

}
