package cn.vko.cache.dao.mybatis.ext;

import java.util.List;

import cn.vko.cache.dao.mybatis.interceptor.MyBatisInterceptor;

public class DefaultMyBatisExtFactory implements MyBatisExtFactory {

	private List<MyBatisInterceptor> interceptors;

	@Override
	public MyBatisInterceptor[] getMyBatisInterceptor() {

		return getInterceptors().toArray(new MyBatisInterceptor[0]);
	}

	public List<MyBatisInterceptor> getInterceptors() {
		return interceptors;
	}

	public void setInterceptors(List<MyBatisInterceptor> interceptors) {
		this.interceptors = interceptors;
	}

}
