package cn.vko.cache.dao.mybatis.ext;

import cn.vko.cache.dao.mybatis.interceptor.MyBatisInterceptor;

public interface MyBatisExtFactory {

	//拦截器,用于缓存
	public MyBatisInterceptor[] getMyBatisInterceptor();
	//orm映射关系
}
