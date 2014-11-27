package com.vko.core.common.environment;

import java.io.StringReader;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.core.io.Resource;
import org.springframework.util.Assert;

import com.vko.core.common.util.VelocityTemplateUtil;

/**
 * @author bo 探测运行环境
 */
public class EnvironmentDetect implements RunEnvironment, RunConfig, InitializingBean {

	private static final Logger logger = LoggerFactory.getLogger(EnvironmentDetect.class);
	public static final String DOMAIN_NAME = "environment.config";
	public static final byte[] LOCAL = new byte[] { 1, 0, 0, 0 };
	public static final byte[] DEVELOP = new byte[] { 2, 0, 0, 0 };
	public static final byte[] TEST = new byte[] { 3, 0, 0, 0 };
	public static final byte[] PRODUCT = new byte[] { 4, 0, 0, 0 };
	private Resource[] resources;
	private Properties properties;
	// 当前环境
	private static final Environment environment;

	static {
		environment = detectEnvironment();
	}

	public static Environment detectEnvironment() {
		if (environment != null) {
			return environment;
		}
		Environment env = null;
		try {
			logger.info("开始检测环境类型...");
			InetAddress address = InetAddress.getByName(DOMAIN_NAME);
			if (address != null) {
				byte[] addressBytes = address.getAddress();
				if (Arrays.equals(LOCAL, addressBytes)) {
					env = Environment.LOCAL;
				} else if (Arrays.equals(DEVELOP, addressBytes)) {
					env = Environment.DEVELOP;
				} else if (Arrays.equals(TEST, addressBytes)) {
					env = Environment.TEST;
				} else if (Arrays.equals(PRODUCT, addressBytes)) {
					env = Environment.PRODUCT;
				}
			}
		} catch (UnknownHostException e) {
			env = Environment.PRODUCT;
			// logger.error("检测环境类型错误,默认", e);
		}
		if (env == null) {
			env = Environment.PRODUCT;
		}
		logger.info("当前是:" + env.getName());
		return env;
	}

	@Override
	public Environment getEnvironment() {
		return environment;
	}

	@Override
	public RunConfig getRunConfig() {

		return this;
	}

	public void init() throws Exception {
		if (properties != null) {
			return;
		}
		properties = new Properties();
		Map<String, Object> context = new HashMap<String, Object>();
		context.put("environment", EnvironmentDetect.detectEnvironment());
		for (int i = 0; i < resources.length; i++) {
			String location = resources[i].getURL().getFile();
			String config = VelocityTemplateUtil.getConfiguration(context, location);
			StringReader reader = new StringReader(config);
			properties.load(reader);
		}
	}

	@Override
	public String getProperty(String key) {
		return properties.getProperty(key);
	}

	@Override
	public String getProperty(String key, String defaultValue) {
		return properties.getProperty(key, defaultValue);
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		Assert.notNull(resources, "属性文件配置不能为null");
		init();

	}

	@Override
	public Properties getProperties() {
		return properties;
	}

	public Resource[] getResources() {
		return resources;
	}

	public void setResources(Resource[] resources) {
		this.resources = resources;
	}

}
