/**
 * RedisSsoService.java
 * cn.vko.sso.service
 * Copyright (c) 2013, 北京微课创景教育科技有限公司版权所有.
*/

package cn.vko.core.sso.service;

import static cn.vko.core.common.util.CollectionUtil.*;
import static cn.vko.core.common.util.DateTimeUtil.*;
import static cn.vko.core.common.util.Util.*;
import static cn.vko.core.redis.util.RedisUtil.*;

import java.sql.Timestamp;
import java.util.List;
import java.util.Set;

import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;

import redis.clients.jedis.JedisCommands;
import cn.vko.core.common.util.ExceptionUtil;
import cn.vko.core.redis.IRedisDao;
import cn.vko.core.redis.RedisProxyFactory;
import cn.vko.core.redis.support.RedisKeyPrefix;
import cn.vko.core.redis.util.RedisLiveUtil;
import cn.vko.core.sso.common.SsoType;
import cn.vko.core.sso.service.entity.SsoLog;
import cn.vko.core.sso.service.entity.SsoLog.LogType;

/**
 * 使用redis进行sso缓存的服务
 * @author   彭文杰
 * @Date	 2013-11-6 	 
 */
@IocBean
public class RedisSsoService {
	public static final String SSO_KEY = "sso";
	public static final String SSO_LIVE_USER_KEY = "user";
	//单位是秒，总共1小时
	public static final int SSO_DEFAULT_TIMEOUT = 60 * 60;
	//单位是秒，总共2个周时间
	public static final int SSO_RM_TIMEOUT = 2 * 7 * 24 * 60 * 60;
	/** 用户未读消息的key前缀 */
	public static final String NEW_MESSAGE_COUNT_KEY_PRE = "newMessageCount:";

	@Inject
	private IRedisDao ssoRedis6;
	@Inject
	private SsoLogService ssoLogService;

	private JedisCommands command;

	public RedisSsoService() {

	}

	public RedisSsoService(RedisProxyFactory command) {
		this.command = command.getProxy();
	}

	/**
	 * 通过redis代理获取用户
	 * <p>
	 *
	 * @param ssoType
	 * @param token
	 * @return 
	*/
	public String fetchProxy(final SsoType ssoType, final String token) {
		String type = getRedisPrefix(SSO_KEY, ssoType);
		command.expire(key(RedisKeyPrefix.UNI, type, token), SSO_DEFAULT_TIMEOUT);
		return command.get(key(RedisKeyPrefix.UNI, type, token));
	}

	/**
	 * 保持token和id对应关系，并设置超时时间
	 *
	 * @param ssoType  sso类别
	 * @param token 唯一标示
	 * @param userId 用户id
	 * @param rememberme 是否记住我
	 */
	public void save(final SsoType ssoType, final String token, final String userId, final boolean rememberme) {
		set(ssoRedis6, getRedisPrefix(SSO_KEY, ssoType), token, userId);
		if (rememberme) {
			expire(ssoRedis6, getRedisPrefix(SSO_KEY, ssoType), token, SSO_RM_TIMEOUT);
		} else {
			expire(ssoRedis6, getRedisPrefix(SSO_KEY, ssoType), token, SSO_DEFAULT_TIMEOUT);
		}
		RedisLiveUtil.add(ssoRedis6, getRedisPrefix(SSO_LIVE_USER_KEY, ssoType), millis(), userId);
		ssoLogService.log(ssoType, LogType.LOGIN, userId);
	}

	/**
	 * 获取存储的key前缀
	 *
	 * @param ssoType sso类型
	 * @return 拼好的前缀
	 */
	private String getRedisPrefix(final String key, final SsoType ssoType) {
		if (isEmpty(ssoType)) {
			throw ExceptionUtil.bEx("单点登录类型为空！");
		}
		return key + ssoType.key();
	}

	/**
	 * 删除
	 *
	 * @param ssoType sso类别
	 * @param token 唯一标示
	 */
	public void rm(final SsoType ssoType, final String token) {
		String userId = fetch(ssoType, token);
		if (userId == null) {
			return;
		}
		del(ssoRedis6, getRedisPrefix(SSO_KEY, ssoType), token);
		RedisLiveUtil.add(ssoRedis6, getRedisPrefix(SSO_LIVE_USER_KEY, ssoType), millis(), userId);
		ssoLogService.log(ssoType, LogType.LOGOUT, userId);
	}

	/**
	 * 获取用户id
	 *
	 * @param ssoType sso类别
	 * @param token 唯一标示
	 * @return 用户id
	 */
	public String fetch(final SsoType ssoType, final String token) {
		expire(ssoRedis6, getRedisPrefix(SSO_KEY, ssoType), token, SSO_DEFAULT_TIMEOUT);
		return get(ssoRedis6, getRedisPrefix(SSO_KEY, ssoType), token);
	}

	/**
	 * 获取用户id并更新超时时间
	 *
	 * @param ssoType sso类别
	 * @param token 唯一标示
	 * @return 用户id
	 */
	public String fetchRefreshExpire(final SsoType ssoType, final String token) {
		String userId = fetch(ssoType, token);
		if (userId == null) {
			return null;
		}
		if (!RedisLiveUtil.isLive(ssoRedis6, getRedisPrefix(SSO_LIVE_USER_KEY, ssoType), userId)) {
			ssoLogService.log(ssoType, LogType.LOGIN, userId);
		}
		RedisLiveUtil.add(ssoRedis6, getRedisPrefix(SSO_LIVE_USER_KEY, ssoType), millis(), userId);
		expireMin(ssoRedis6, getRedisPrefix(SSO_KEY, ssoType), token, SSO_DEFAULT_TIMEOUT);
		return userId;
	}

	/**
	 * 移除过期的没有反映的用户
	 * 每隔5分钟执行一次
	 */
	public void rmOutDate(final SsoType ssoType) {
		if (ssoType == null) {
			return;
		}
		Set<String> outDates = RedisLiveUtil.outDate(ssoRedis6, getRedisPrefix(SSO_LIVE_USER_KEY, ssoType), 0, millis()
				- SSO_DEFAULT_TIMEOUT * 1000);
		if (isEmpty(outDates)) {
			return;
		}
		RedisLiveUtil.rm(ssoRedis6, getRedisPrefix(SSO_LIVE_USER_KEY, ssoType), collection2array(outDates));
		Timestamp tt = nowDateTime();
		List<SsoLog> sls = list();
		for (String outDate : outDates) {
			SsoLog sl = new SsoLog();
			sl.setUserId(outDate);
			sl.setLogType(LogType.TIMEOUT.getKey());
			sl.setSsoType(ssoType.getKey());
			sl.setLogTime(tt);
			sls.add(sl);
		}
		ssoLogService.batch(sls);
	}

	/**
	 * 设置未读消息个数
	 * @author dixingxing 
	 * @param key
	 * @param count
	 */
	public void setNewMessageCount(long userId, String count) {
		String key = NEW_MESSAGE_COUNT_KEY_PRE + userId;
		ssoRedis6.set(key, count);
		ssoRedis6.expire(key, 300);
	}

	/**
	 * 清除未读消息个数的缓存
	 * 
	 * @param userId
	 */
	public void removeNewMessageCount(long userId) {
		ssoRedis6.del(NEW_MESSAGE_COUNT_KEY_PRE + userId);
	}

	/**
	 * 获取未读消息个数
	 * @author dixingxing 
	 * @param key
	 * @return
	 */
	public String getNewMessageCount(long userId) {
		return ssoRedis6.get(NEW_MESSAGE_COUNT_KEY_PRE + userId);
	}
}
