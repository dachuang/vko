/**
 * KvConfig.java
 * cn.vko.web.common.config
 * Copyright (c) 2013, 北京微课创景教育科技有限公司版权所有.
*/

package com.vko.core.common.util;

import java.util.Map;

import javax.servlet.ServletContext;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.web.context.WebApplicationContext;

/**
 * keyvalue配置文件，系统统一配置
 * @author  宋汝波
 * @Date	 2013-11-6 	 
 */
public class KvConfig implements InitializingBean, ApplicationContextAware {
	/**
	 * map结构存储的配置
	 */
	private Map<String, String> values;
	private WebApplicationContext context;

	public Map<String, String> getValues() {
		return values;
	}

	public void setValues(Map<String, String> values) {
		this.values = values;
	}

	public String getValue(final String key) {
		if (values == null) {
			return null;
		}
		return values.get(key);
	}

	public String getValue(final ConfigKey key) {
		if (values == null) {
			return null;
		}
		if (key == null) {
			return null;
		}
		return values.get(key.key());
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		if (context == null) {
			return;
		}
		ServletContext servlet = context.getServletContext();
		if (servlet.getAttribute(InitUtil.GLOBAL_CONFIG_KEY) == null) {
			if (servlet.getAttribute(InitUtil.GLOBAL_CONFIG_KEY) == null) {
				InitUtil.initWebGlobalConfig(this, servlet);
			}
		}
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		if (applicationContext instanceof WebApplicationContext) {
			context = (WebApplicationContext) applicationContext;
			ApplicationUtil.setApplication(context);
		}

	}
}
