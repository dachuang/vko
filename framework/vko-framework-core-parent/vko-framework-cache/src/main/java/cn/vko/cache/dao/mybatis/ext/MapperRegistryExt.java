package cn.vko.cache.dao.mybatis.ext;

import java.lang.reflect.Field;
import java.lang.reflect.Proxy;
import java.util.Map;

import org.apache.ibatis.binding.BindingException;
import org.apache.ibatis.binding.MapperProxyFactory;
import org.apache.ibatis.binding.MapperRegistry;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;

import cn.vko.cache.dao.mybatis.interceptor.MyBatisInterceptor;

/**
 * @author songrubo
 * @version 2013年11月30日 下午12:04:57
 */
public class MapperRegistryExt extends MapperRegistry {
	private Map<Class<?>, MapperProxyFactory<?>> knownMappers;
	MyBatisInterceptor[] interceptor;

	@SuppressWarnings("unchecked")
	public MapperRegistryExt(Configuration config, ApplicationContext applicationContext) {
		super(config);
		try {
			Field field = MapperRegistry.class.getDeclaredField("knownMappers");
			field.setAccessible(true);
			knownMappers = (Map<Class<?>, MapperProxyFactory<?>>) field.get(this);
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			MyBatisExtFactory myBatisExt = applicationContext.getBean(MyBatisExtFactory.class);
			interceptor = myBatisExt.getMyBatisInterceptor();
		} catch (BeansException e) {
			// ignore
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> T getMapper(Class<T> type, SqlSession sqlSession) {
		final MapperProxyFactory<T> mapperProxyFactory = (MapperProxyFactory<T>) knownMappers.get(type);
		if (mapperProxyFactory == null)
			throw new BindingException("Type " + type + " is not known to the MapperRegistry.");
		try {
			Class<T> mapperInterface = mapperProxyFactory.getMapperInterface();
			final MapperProxyExt<T> mapperProxy = new MapperProxyExt<T>(sqlSession, mapperInterface,
					mapperProxyFactory.getMethodCache(), interceptor);
			return (T) Proxy.newProxyInstance(mapperInterface.getClassLoader(), new Class[] { mapperInterface },
					mapperProxy);
		} catch (Exception e) {
			throw new BindingException("Error getting mapper instance. Cause: " + e, e);
		}
	}
}
