package com.vko.core.web.filter.interceptor;

import java.io.IOException;

import javax.servlet.FilterConfig;
import javax.servlet.ServletException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.vko.core.common.environment.EnvironmentDetect;
import com.vko.core.common.environment.RunEnvironment.Environment;
import com.vko.core.common.log.LogUtil;
import com.vko.core.web.filter.FilterInvocation;

/**
 * @author bo 性能检测
 */
public class PerformanceInterceptor implements FilterInterceptor {

	private static final Logger logger = LoggerFactory.getLogger(PerformanceInterceptor.class);
	// 超时时间默认100ms
	private Long timeForWarn = 100L;

	@Override
	public void invoke(FilterInvocation filterInvocation) throws IOException, ServletException {
		long start = System.currentTimeMillis();
		filterInvocation.doInterceptor();
		// 检测运行环境
		Environment env = EnvironmentDetect.detectEnvironment();
		if (env == Environment.DEVELOP || env == Environment.LOCAL) {
			long end = System.currentTimeMillis();
			if ((end - start) > timeForWarn && logger.isWarnEnabled()) {
				logger.warn(LogUtil.getInfo(filterInvocation.getRequest()) + "请求执行时间:" + (end - start) + "ms\r\n");
			}
		}
	}

	public Long getTimeForWarn() {
		return timeForWarn;
	}

	public void setTimeForWarn(Long timeForWarn) {
		this.timeForWarn = timeForWarn;
	}

	@Override
	public void init(FilterConfig filterConfig) {

		// TODO Auto-generated method stub

	}

	@Override
	public void destroy() {

		// TODO Auto-generated method stub

	}

}
