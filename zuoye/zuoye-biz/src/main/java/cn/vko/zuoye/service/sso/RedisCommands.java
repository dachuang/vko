/**
 * JedisCommands.java
 * cn.vko.zuoye.service.sso
 * Copyright (c) 2014, 北京微课创景教育科技有限公司版权所有.
*/

package cn.vko.zuoye.service.sso;

import redis.clients.jedis.BinaryJedisCommands;
import redis.clients.jedis.JedisCommands;

/**
 * redis访问接口
 * <p>
 * @author   宋汝波
 * @Date	 2014-7-18 	 
 */
public interface RedisCommands extends JedisCommands, BinaryJedisCommands {

}
