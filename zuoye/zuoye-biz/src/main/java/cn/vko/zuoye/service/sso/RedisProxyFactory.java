package cn.vko.zuoye.service.sso;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import org.springframework.beans.factory.FactoryBean;

import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPool;

/**
 * 代理类,获取操作对象
 * <p>
 * @author   宋汝波
 * @Date	 2014-5-21
 */
public class RedisProxyFactory implements InvocationHandler, FactoryBean<RedisCommands> {
	private ShardedJedisPool pool;
	private RedisCommands proxy;

	public RedisProxyFactory(ShardedJedisPool pool) {
		this.pool = pool;
		proxy = (RedisCommands) Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(),
				new Class[] { RedisCommands.class }, this);
	}

	@SuppressWarnings("hiding")
	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		ShardedJedis jedis = pool.getResource();
		try {
			return method.invoke(jedis, args);
		} finally {
			pool.returnResourceObject(jedis);
		}
	}

	public RedisCommands getProxy() {
		return proxy;
	}

	@Override
	public RedisCommands getObject() throws Exception {

		return proxy;

	}

	@Override
	public Class<?> getObjectType() {

		return RedisCommands.class;

	}

	@Override
	public boolean isSingleton() {

		return true;

	}
}
