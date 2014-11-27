package cn.vko.core.redis;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import redis.clients.jedis.JedisCommands;
import cn.vko.core.redis.support.IRedisDataSource;

/**
 * 代理类,获取操作对象
 * <p>
 * @author   宋汝波
 * @Date	 2014-5-21
 */
public class RedisProxyFactory implements InvocationHandler {
	private IRedisDataSource pool;
	private JedisCommands proxy;

	public RedisProxyFactory(IRedisDataSource pool) {
		this.pool = pool;
		proxy = (JedisCommands) Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(),
				new Class[] { JedisCommands.class }, this);
	}

	@SuppressWarnings("hiding")
	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		JedisCommands jedis = pool.getJedis();
		try {
			return method.invoke(jedis, args);
		} finally {
			pool.returnResource(jedis);
		}
	}

	public JedisCommands getProxy() {
		return proxy;
	}
}
