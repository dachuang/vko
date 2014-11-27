/**
 * HessianClientFactoryBean.java
 * cn.vko.hessian.core.client
 * Copyright (c) 2014, 北京微课创景教育科技有限公司版权所有.
*/

package cn.vko.hessian.core.client;

import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.Assert;

import cn.vko.hessian.core.VkoHessianProxyFactory;

import com.caucho.hessian.client.HessianProxyFactory;

/**
 * 用于spring注入客户端
 * <p>
 * @author   宋汝波
 * @Date	 2014-7-10 	 
 */
public class HessianClientFactoryBean<T> implements FactoryBean<T>, InitializingBean {

	protected Class<T> objectType;
	protected HessianProxyFactory factory;
	protected String url;

	@SuppressWarnings("unchecked")
	@Override
	public T getObject() throws Exception {
		T api = (T) getFactory().create(objectType, url);
		return api;

	}

	@Override
	public Class<T> getObjectType() {

		return objectType;

	}

	@Override
	public boolean isSingleton() {

		return true;

	}

	public HessianProxyFactory getFactory() {
		if (factory == null) {
			factory = new VkoHessianProxyFactory();
			factory.setHessian2Reply(true);
			factory.setHessian2Request(true);
			factory.setChunkedPost(false);
		}
		return factory;
	}

	public void setFactory(HessianProxyFactory factory) {
		this.factory = factory;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public void setObjectType(Class<T> objectType) {
		this.objectType = objectType;
	}

	@Override
	public void afterPropertiesSet() throws Exception {

		Assert.notNull(objectType, "objectType不能为null");
		Assert.notNull(url, "url不能为null");

	}

}
