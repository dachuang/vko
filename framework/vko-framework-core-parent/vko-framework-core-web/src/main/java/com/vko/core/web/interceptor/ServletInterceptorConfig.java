package com.vko.core.web.interceptor;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.Assert;

/**
 * 拦截器配置入口
 * <p>
 * @author   宋汝波
 * @Date	 2014-8-1
 */
public class ServletInterceptorConfig implements InitializingBean {

	private ServletInterceptor[] interceptors;

	public ServletInterceptor[] getInterceptors() {
		return interceptors;
	}

	public void setInterceptors(ServletInterceptor[] interceptors) {
		this.interceptors = interceptors;
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		Assert.notNull(interceptors, "请添加拦截器!");
		Assert.notEmpty(interceptors, "拦截器不能为空");
	}

}
