package cn.vko.cache.dao.mybatis.ext;

import java.io.Serializable;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Map;

import org.apache.ibatis.binding.MapperMethod;
import org.apache.ibatis.session.SqlSession;

import cn.vko.cache.dao.mybatis.interceptor.MyBatisInterceptor;
import cn.vko.cache.dao.mybatis.interceptor.MyBatisInvocation;

/**
 * @author songrubo
 * @version 2013年11月30日 下午12:09:51
 */
public class MapperProxyExt<T> implements InvocationHandler, Serializable {

	private static final long serialVersionUID = -6424540398559729838L;
	private final SqlSession sqlSession;
	private final Class<T> mapperInterface;
	private final Map<Method, MapperMethod> methodCache;
	private final MyBatisInterceptor[] interceptors;
	private final static ThreadLocal<MyBatisInvocation> myBatisInvocationHolder = new ThreadLocal<MyBatisInvocation>();

	public MapperProxyExt(SqlSession sqlSession, Class<T> mapperInterface, Map<Method, MapperMethod> methodCache,
			MyBatisInterceptor[] interceptors) {
		this.sqlSession = sqlSession;
		this.mapperInterface = mapperInterface;
		this.methodCache = methodCache;
		this.interceptors = interceptors;
	}

	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		if (Object.class.equals(method.getDeclaringClass())) {
			return method.invoke(this, args);
		}
		try {
			final MapperMethod mapperMethod = cachedMapperMethod(method);
			MyBatisInvocation invocation = new MyBatisInvocation(mapperMethod, mapperInterface, sqlSession, method,
					args, interceptors);
			myBatisInvocationHolder.set(invocation);
			return invocation.execute();
		} finally {
			myBatisInvocationHolder.remove();
		}

	}

	public static MyBatisInvocation getMyBatisInvocation() {
		return myBatisInvocationHolder.get();
	}

	private MapperMethod cachedMapperMethod(Method method) {
		MapperMethod mapperMethod = methodCache.get(method);
		if (mapperMethod == null) {
			mapperMethod = new MapperMethod(mapperInterface, method, sqlSession.getConfiguration());
			methodCache.put(method, mapperMethod);
		}
		return mapperMethod;
	}

}
