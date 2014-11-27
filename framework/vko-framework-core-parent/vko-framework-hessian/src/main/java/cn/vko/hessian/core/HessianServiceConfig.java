package cn.vko.hessian.core;

import java.util.List;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.io.Resource;
import org.springframework.util.Assert;

import cn.vko.hessian.conf.XmlConfigParser;
import cn.vko.hessian.object.Application;
import cn.vko.hessian.object.Service;
import cn.vko.hessian.object.ServiceVersion;

public class HessianServiceConfig implements ApplicationContextAware, InitializingBean {
	protected ApplicationContext context;
	//xml配置文件
	protected Resource configFile;
	//前缀
	private String prefix = "/apis/";

	public String getPrefix() {
		return prefix;
	}

	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}

	@Override
	public void setApplicationContext(ApplicationContext arg0) throws BeansException {
		this.context = arg0;
		ServiceHandler.setApplicationContext(context);
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		Assert.notNull(configFile, "configFile不能为null");
		// 解析xml，注册服务
		XmlConfigParser configParser = new XmlConfigParser(configFile);

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
	}

	public Resource getConfigFile() {
		return configFile;
	}

	public void setConfigFile(Resource configFile) {
		this.configFile = configFile;
	}

}
