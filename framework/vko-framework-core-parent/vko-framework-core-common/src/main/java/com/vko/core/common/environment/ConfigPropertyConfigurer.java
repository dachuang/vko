package com.vko.core.common.environment;

import java.io.IOException;
import java.util.Properties;

import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;

public class ConfigPropertyConfigurer extends PropertyPlaceholderConfigurer {
	//ApplicationContext context;
	RunEnvironment environment;

	//	public ConfigPropertyConfigurer(ApplicationContext context) {
	//		this.context = context;
	//	}

	public ConfigPropertyConfigurer() {
		super();
	}

	@Override
	protected Properties mergeProperties() throws IOException {
		//RunConfig config = this.context.getBean(RunConfig.class);
		this.setProperties(environment.getRunConfig().getProperties());
		// this.setLocation(location);
		return super.mergeProperties();
	}

	public RunEnvironment getEnvironment() {
		return environment;
	}

	public void setEnvironment(RunEnvironment environment) {
		this.environment = environment;
	}

}
