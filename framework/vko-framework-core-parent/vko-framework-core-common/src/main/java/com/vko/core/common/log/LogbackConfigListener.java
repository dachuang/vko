package com.vko.core.common.log;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.springframework.util.ResourceUtils;
import org.springframework.util.SystemPropertyUtils;

import com.vko.core.common.environment.EnvironmentDetect;
import com.vko.core.common.util.VelocityTemplateUtil;

public class LogbackConfigListener implements ServletContextListener {

	@Override
	public void contextInitialized(ServletContextEvent event) {
		// 判断环境,并设置到context里面去
		// 加载不同的log配置
		try {

			String location = event.getServletContext().getInitParameter(LogbackWebConfigurer.CONFIG_LOCATION_PARAM);
			String resolvedLocation = SystemPropertyUtils.resolvePlaceholders(location);
			URL url = ResourceUtils.getURL(resolvedLocation);
			Map<String, Object> context = new HashMap<String, Object>();
			context.put("environment", EnvironmentDetect.detectEnvironment());
			String config = VelocityTemplateUtil.getConfiguration(context, url.getFile());
			LogbackWebConfigurer.initLogging(config.getBytes("UTF-8"));
			return;
		} catch (Exception e) {
			e.printStackTrace();
		}
		LogbackWebConfigurer.initLogging(event.getServletContext());
	}

	@Override
	public void contextDestroyed(ServletContextEvent event) {
		LogbackWebConfigurer.shutdownLogging(event.getServletContext());
	}
}
