/**
 * ShardedJedisMock.java
 * cn.vko.core.redis.support
 * Copyright (c) 2014, 北京微课创景教育科技有限公司版权所有.
*/

package cn.vko.core.redis.support;

import java.util.ArrayList;

import redis.clients.jedis.JedisShardInfo;
import redis.clients.jedis.ShardedJedis;

/**
 * 加入默认构造
 * <p>
 * @author   宋汝波
 * @Date	 2014-9-4 	 
 */
public class ShardedJedisMock extends ShardedJedis {

	public ShardedJedisMock() {
		super(new ArrayList<JedisShardInfo>());
	}

}
