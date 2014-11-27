/**
 * ServletContextInterceptor.java
 * cn.vko.zuoye.interceptor
 * Copyright (c) 2014, 北京微课创景教育科技有限公司版权所有.
*/

package cn.vko.zuoye.interceptor;

import java.io.IOException;

import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.velocity.tools.generic.DateTool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.vko.core.common.keyvalue.AbstractKeyValue;
import com.vko.core.common.util.DateUtil;
import com.vko.core.common.util.HttpInclude;
import com.vko.core.web.filter.FilterInvocation;
import com.vko.core.web.filter.Invocation;
import com.vko.core.web.filter.InvocationUtils;
import com.vko.core.web.filter.interceptor.FilterInterceptor;

/**
 * 加载一些公用的对象到servletcontext
 * <p>
 * @author   宋汝波
 * @Date	 2014-7-10 	 
 */
public class ServletContextInterceptor implements FilterInterceptor {

	Logger logger = LoggerFactory.getLogger(ServletContextInterceptor.class);

	@Override
	public void invoke(FilterInvocation filterInvocation) throws IOException, ServletException {
		filterInvocation.doInterceptor();
	}

	@Override
	public void init(FilterConfig filterConfig) {
		ServletContext context = filterConfig.getServletContext();
		//加载velocity工具类
		context.setAttribute("vkoUtil", new VeloCityUtil());
		context.setAttribute("dateUtil", new DateUtil());
		context.setAttribute("dateTool", new DateTool());
		context.setAttribute("httpInclude", new HttpInclude() {
			@Override
			public String include(String includePath, HttpServletRequest request, HttpServletResponse response) {
				Invocation inv = InvocationUtils.getInvocation(InvocationUtils.getCurrentThreadRequest());
				return super.include(includePath, inv.getRequest(), inv.getResponse());
			}
		});
		//keyValue状态类的加载
		try {
			AbstractKeyValue.init("classpath*:cn/vko/zuoye/keyvalue/**/*.class", "cn.vko.zuoye.keyvalue");
		} catch (Exception e) {
			logger.error("加载状态字出错", e);

		}
	}

	@Override
	public void destroy() {

	}

}
