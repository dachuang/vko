/**
 * RedisDataSource.java
 * cn.vko.dao.redis.support
 * Copyright (c) 2013, 北京微课创景教育科技有限公司版权所有.
*/

package cn.vko.core.redis.support.impl;

import static cn.vko.core.common.util.CollectionUtil.*;
import static cn.vko.core.common.util.ExceptionUtil.*;
import static cn.vko.core.common.util.Util.*;

import java.util.List;

import lombok.Data;
import net.sf.cglib.proxy.Enhancer;
import redis.clients.jedis.JedisCommands;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisShardInfo;
import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPool;
import cn.vko.core.redis.support.IRedisDataSource;
import cn.vko.core.redis.support.RedisConfig;
import cn.vko.core.redis.support.RedisMethodInterceptor;
import cn.vko.core.redis.support.ShardedJedisMock;

/**
 * redis配置的集群数据源
 * 
 * @author   彭文杰
 * @author   赵立伟
 * @Date	 2013-1-30 	 
 */
@Data
public class ShardRedisDataSource implements IRedisDataSource {
	//	private ShardedJedisPool pool;
	private List<RedisConfig> config;
	//private int maxConnect = 8;
	private int maxConnect = 500;
	static JedisCommands commands;

	/**
	 * 初始化redis链接池
	 * 
	 * @exception DownException
	 */
	private void initPool() {
		if (commands == null) {
			synchronized (ShardRedisDataSource.class) {
				if (commands != null) {
					return;
				}
				List<JedisShardInfo> shards = getShardInfos();
				if (isEmpty(shards)) {
					throw pEx("读取JedisShardInfo 相关配置信息失败");
				}
				JedisPoolConfig conf = new JedisPoolConfig();
				conf.setMaxActive(maxConnect);
				conf.setMaxIdle(10);
				conf.setMaxWait(1000);
				conf.testOnBorrow = true;
				ShardedJedisPool pool = new ShardedJedisPool(conf, shards);
				Enhancer enhancer = new Enhancer();
				enhancer.setSuperclass(ShardedJedisMock.class);
				enhancer.setCallback(new RedisMethodInterceptor(pool));
				commands = (ShardedJedis) enhancer.create();
			}
		}

	}

	/**
	 * 获取集群信息列表
	 *
	 * @return 集群信息列表
	 */
	public List<JedisShardInfo> getShardInfos() {
		List<JedisShardInfo> shardInfos = list();
		for (RedisConfig redisConfig : config) {
			JedisShardInfo shardInfo = new JedisShardInfo(redisConfig.getHost(), redisConfig.getPort());
			shardInfos.add(shardInfo);
		}
		return shardInfos;
	}

	@Override
	public JedisCommands getJedis() {
		initPool();
		return commands;
	}

	@Override
	public void returnResource(final JedisCommands jedis) {
		//		if (jedis == null) {
		//			return;
		//		}
		//		if (!(jedis instanceof ShardedJedis)) {
		//			throw pEx("释放redis链接时，对象类型不正确！");
		//		}
		//		pool.returnResource((ShardedJedis) jedis);
	}

	@Override
	public void returnBrokenResource(final JedisCommands jedis) {
		//		if (jedis == null) {
		//			return;
		//		}
		//		if (!(jedis instanceof ShardedJedis)) {
		//			throw pEx("释放redis链接时，对象类型不正确！");
		//		}
		//		pool.returnBrokenResource((ShardedJedis) jedis);
	}
}
