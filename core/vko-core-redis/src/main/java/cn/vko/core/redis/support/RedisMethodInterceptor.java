/**
 * RedisMethodInterceptor.java
 * cn.vko.core.redis.support
 * Copyright (c) 2014, 北京微课创景教育科技有限公司版权所有.
*/

package cn.vko.core.redis.support;

import java.lang.reflect.Method;

import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;
import redis.clients.util.Pool;

/**
 * 处理redis调用
 * <p>
 * @author   宋汝波
 * @Date	 2014-9-4 	 
 */
public class RedisMethodInterceptor implements MethodInterceptor {
	final Pool<?> pool;

	public RedisMethodInterceptor(Pool<?> pool) {
		this.pool = pool;
	}

	@Override
	public Object intercept(Object target, Method method, Object[] args, MethodProxy proxy) throws Throwable {
		Object jedis = pool.getResource();
		try {
			return method.invoke(jedis, args);
		} finally {
			if (jedis != null) {
				pool.returnResourceObject(jedis);
			}
		}
	}
}
