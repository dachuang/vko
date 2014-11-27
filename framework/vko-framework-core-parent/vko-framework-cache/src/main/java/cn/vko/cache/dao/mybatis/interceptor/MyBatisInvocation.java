package cn.vko.cache.dao.mybatis.interceptor;

import java.lang.reflect.Method;

import org.apache.ibatis.binding.MapperMethod;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSession;

public class MyBatisInvocation {
	private final MapperMethod mapperMethod;
	private final SqlSession sqlSession;
	private final Object[] args;
	private final Class<?> mapperInterface;
	private final Method method;
	private final MyBatisInterceptor[] interceptors;
	private int index = 0;

	public MyBatisInvocation(MapperMethod mapperMethod, Class<?> mapperInterface, SqlSession sqlSession, Method method,
			Object[] args, MyBatisInterceptor[] interceptors) {
		this.mapperMethod = mapperMethod;
		this.sqlSession = sqlSession;
		this.args = args;
		this.mapperInterface = mapperInterface;
		this.method = method;
		this.interceptors = interceptors;
	}

	public Object execute() throws Throwable {
		if (interceptors == null) {
			return mapperMethod.execute(sqlSession, args);
		}
		if (index < interceptors.length) {
			return interceptors[index++].invoke(this);
		} else {
			return mapperMethod.execute(sqlSession, args);
		}

	}

	public Configuration getConfiguration() {
		return sqlSession.getConfiguration();
	}

	public Object[] getArgs() {
		return args;
	}

	public Class<?> getMapperInterface() {
		return mapperInterface;
	}

	public Method getMethod() {
		return method;
	}

}
