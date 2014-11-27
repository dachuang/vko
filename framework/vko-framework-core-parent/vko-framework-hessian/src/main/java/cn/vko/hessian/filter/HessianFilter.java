/**
 * HessianFilter.java
 * cn.vko.hessian.filter
 * Copyright (c) 2014, 北京微课创景教育科技有限公司版权所有.
*/

package cn.vko.hessian.filter;

import java.io.IOException;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;

import cn.vko.hessian.conf.XmlConfigParser;
import cn.vko.hessian.core.MetadataProcessor;
import cn.vko.hessian.core.Security;
import cn.vko.hessian.core.ServiceHandler;
import cn.vko.hessian.object.Application;
import cn.vko.hessian.object.Service;
import cn.vko.hessian.object.ServiceVersion;

/**
 * hessian过滤器
 * <p>
 * @author   宋汝波
 * @Date	 2014-7-28 	 
 */
public class HessianFilter implements Filter {
	private ResourcePatternResolver resourcePatternResolver = new PathMatchingResourcePatternResolver();
	private HessianFilterHandler handler;
	public static final String CONFIG_FILE = "configFile";
	public static final String PREFIX = "prefix";

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		String configFile = filterConfig.getInitParameter(CONFIG_FILE);
		String prefix = filterConfig.getInitParameter(PREFIX);
		if (configFile == null) {
			configFile = "classpath:hessian.xml";
		}
		// 解析xml，注册服务
		XmlConfigParser configParser = new XmlConfigParser(resourcePatternResolver.getResource(configFile));

		List<Application> appList = configParser.parseApplication();
		if (appList != null) {
			for (Application app : appList) {
				Security.addToApplicationMap(app);
			}
		}
		List<Service> serviceList = configParser.parseService();
		for (Service service : serviceList) {
			ServiceHandler.addToServiceMap(service);
		}
		List<ServiceVersion> versionList = configParser.parseSecurity();
		if (versionList != null) {
			for (ServiceVersion version : versionList) {
				ServiceHandler.addToVersionMap(version);
			}
		}
		MetadataProcessor.initMetaDataMap();

		if (prefix != null) {
			handler = new HessianFilterHandler(prefix);
		} else {
			handler = new HessianFilterHandler();
		}
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException,
			ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;
		if ("x-application/hessian".equals(request.getContentType())) {
			handler.service(req, res);
			return;
		}
		chain.doFilter(request, response);
	}

	@Override
	public void destroy() {

	}

}
