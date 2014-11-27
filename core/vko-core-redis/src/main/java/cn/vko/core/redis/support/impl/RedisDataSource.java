/**
 * RedisDataSource.java
 * cn.vko.dao.redis.support
 * Copyright (c) 2013, 北京微课创景教育科技有限公司版权所有.
*/

package cn.vko.core.redis.support.impl;

import lombok.Data;
import net.sf.cglib.proxy.Enhancer;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCommands;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import cn.vko.core.redis.support.IRedisDataSource;
import cn.vko.core.redis.support.JedisMock;
import cn.vko.core.redis.support.RedisConfig;
import cn.vko.core.redis.support.RedisMethodInterceptor;

/**
 * redis配置的数据源
 * 
 * @author   彭文杰
 * @author   赵立伟
 * @Date	 2013-1-30 	 
 */
@Data
public class RedisDataSource implements IRedisDataSource {
	//	private JedisPool pool;
	private RedisConfig config;
	//private int maxConnect = 8;
	private int maxConnect = 500;
	static JedisCommands commands;

	/**
	 * 初始化redis链接池
	 */
	private void initPool() {
		if (commands == null) {
			synchronized (RedisDataSource.class) {
				if (commands != null) {
					return;
				}
				JedisPoolConfig conf = new JedisPoolConfig();
				conf.setMaxActive(maxConnect);
				conf.setMaxIdle(10);
				conf.setMaxWait(1000);
				conf.testOnBorrow = true;
				final JedisPool pool = new JedisPool(conf, config.getHost(), config.getPort(), config.getTimeout());
				Enhancer enhancer = new Enhancer();
				enhancer.setSuperclass(JedisMock.class);
				enhancer.setCallback(new RedisMethodInterceptor(pool));
				commands = (Jedis) enhancer.create();
			}

		}

	}

	@Override
	public JedisCommands getJedis() {
		initPool();
		return commands;
	}

	@Override
	public void returnResource(final JedisCommands jedis) {
		//empty
		//		if (jedis == null) {
		//			return;
		//		}
		//		if (!(jedis instanceof Jedis)) {
		//			throw pEx("释放redis链接时，对象类型不正确！");
		//		}
		//		pool.returnResource((Jedis) jedis);
	}

	@Override
	public void returnBrokenResource(final JedisCommands jedis) {
		//empty
		//		if (jedis == null) {
		//			return;
		//		}
		//		if (!(jedis instanceof Jedis)) {
		//			throw pEx("释放redis链接时，对象类型不正确！");
		//		}
		//		pool.returnBrokenResource((Jedis) jedis);
	}
}
