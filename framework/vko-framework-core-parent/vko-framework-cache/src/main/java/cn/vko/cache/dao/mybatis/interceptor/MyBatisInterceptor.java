package cn.vko.cache.dao.mybatis.interceptor;

public interface MyBatisInterceptor {

	public Object invoke(MyBatisInvocation handler) throws Throwable;
}
