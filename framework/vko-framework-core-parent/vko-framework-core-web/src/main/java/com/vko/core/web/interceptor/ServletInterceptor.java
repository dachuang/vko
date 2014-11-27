/**
 * ServletInterceptor.java
 * com.vko.core.web.interceptor
 * Copyright (c) 2014, 北京微课创景教育科技有限公司版权所有.
*/

package com.vko.core.web.interceptor;

/**
 * 拦截器接口
 * <p>
 * @author   宋汝波
 * @Date	 2014-8-1 	 
 */
public interface ServletInterceptor {
	public void invoke(ServletInvocation invocation) throws Exception;
}
