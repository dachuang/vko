/**
 * WwwFetchUser.java
 * cn.vko.core.web.www.db
 * Copyright (c) 2013, 北京微课创景教育科技有限公司版权所有.
*/

package cn.vko.core.sso.service;

import static cn.vko.core.common.util.Util.*;
import static cn.vko.core.redis.util.RedisUtil.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import redis.clients.jedis.JedisCommands;
import cn.vko.core.common.exception.impl.TimeoutException;
import cn.vko.core.common.util.ExceptionUtil;
import cn.vko.core.db.dao.impl.support.IFetchUser;
import cn.vko.core.redis.RedisProxyFactory;
import cn.vko.core.redis.support.RedisKeyPrefix;
import cn.vko.core.sso.actionfilter.AbstractSsoFilter;
import cn.vko.core.sso.common.SsoContext;
import cn.vko.core.sso.common.SsoType;
import cn.vko.core.web.util.RequestUtil;

/**
 * 获取当前用户
 * @author   宋汝波
 * @Date	 2013-12-23 	 
 */
public class SsoFetchUser implements IFetchUser {

	private static ThreadLocal<SsoContext> local = new ThreadLocal<SsoContext>();

	private static JedisCommands command;

	public SsoFetchUser(RedisProxyFactory command) {
		SsoFetchUser.command = command.getProxy();
	}

	@Override
	public long getCurrentUserId() {
		return getUserId();
	}

	/**
	 * 获取存储的key前缀
	 *
	 * @param ssoType sso类型
	 * @return 拼好的前缀
	 */
	private static String getRedisPrefix(final String key, final SsoType ssoType) {
		if (isEmpty(ssoType)) {
			throw ExceptionUtil.bEx("单点登录类型为空！");
		}
		return key + ssoType.key();
	}

	private static String fetchProxy(final SsoType ssoType, final String token) {
		String type = getRedisPrefix(RedisSsoService.SSO_KEY, ssoType);
		command.expire(key(RedisKeyPrefix.UNI, type, token), RedisSsoService.SSO_DEFAULT_TIMEOUT);
		return command.get(key(RedisKeyPrefix.UNI, type, token));
	}

	protected static String getToken(HttpServletRequest req, String cookieName) {
		Cookie ck = RequestUtil.getCookie(req, cookieName);
		if (ck == null) {
			return null;
		}
		String token = ck.getValue();
		return token;
	}

	@Override
	public long getCurrentUserIdWithEx() {
		long userId = getCurrentUserId();
		if (userId == -1) {
			throw new TimeoutException("您尚未登录,请先登录!");
		}
		return userId;
	}

	@Override
	public String getCurrentUserName() {
		throw new RuntimeException("不支持");
	}

	public static long refreshTimeout() {
		SsoContext con = local.get();
		String cookieName = con.getCookieName();
		HttpServletRequest req = con.getRequest();
		String token = getToken(req, cookieName);
		//判断redis中的key是否被删除
		String type = getRedisPrefix("sso", SsoType.WEB);
		boolean isExist = command.exists(key(RedisKeyPrefix.UNI, type, token));
		if (!isExist) {
			return -1;
		}
		String userIdString = fetchProxy(SsoType.WEB, token);
		if (userIdString != null) {
			req.setAttribute(AbstractSsoFilter.REQUEST_USER_ID_KEY, userIdString);
		}
		long userId = Long.parseLong(userIdString);

		return userId;
	}

	public static long getUserId() {
		SsoContext con = local.get();
		if (con == null) {
			return -1;
		}
		HttpServletRequest req = con.getRequest();

		Object userIdObject = req.getAttribute(AbstractSsoFilter.REQUEST_USER_ID_KEY);
		//先从request中找
		long userId = -1;
		if (userIdObject != null) {
			userId = Long.parseLong(userIdObject.toString());
			if (userId > 0) {
				return userId;
			}
		}
		String cookieName = con.getCookieName();
		String token = getToken(req, cookieName);
		if (token == null) {
			return -1;
		}
		String userIdString = fetchProxy(SsoType.WEB, token);
		userId = Long.parseLong(userIdString);
		if (userId < 0) {
			return -1;
		}
		return userId;
	}

	public static long getUserIdWithEx() {
		long userId = getUserId();
		if (userId <= 0) {
			throw new TimeoutException("您尚未登录,请先登录!");
		}
		return userId;
	}

	public static void setContext(SsoContext context) {
		local.set(context);
		refreshTimeout();
	}

	public static void removeContext() {
		local.remove();
	}

}
