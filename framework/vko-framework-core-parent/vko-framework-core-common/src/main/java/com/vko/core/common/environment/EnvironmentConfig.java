package com.vko.core.common.environment;

import org.springframework.core.io.Resource;

public class EnvironmentConfig {

	private Resource local;
	private Resource develop;
	private Resource test;
	private Resource product;

	public Resource getLocal() {
		return local;
	}

	public void setLocal(Resource local) {
		this.local = local;
	}

	public Resource getDevelop() {
		return develop;
	}

	public void setDevelop(Resource develop) {
		this.develop = develop;
	}

	public Resource getTest() {
		return test;
	}

	public void setTest(Resource test) {
		this.test = test;
	}

	public Resource getProduct() {
		return product;
	}

	public void setProduct(Resource product) {
		this.product = product;
	}

}
