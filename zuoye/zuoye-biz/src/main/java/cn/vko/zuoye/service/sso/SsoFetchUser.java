/**
 * WwwFetchUser.java
 * cn.vko.core.web.www.db
 * Copyright (c) 2013, 北京微课创景教育科技有限公司版权所有.
*/

package cn.vko.zuoye.service.sso;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import redis.clients.jedis.JedisCommands;

import com.vko.core.common.exception.LogicException;
import com.vko.core.common.util.ApplicationUtil;

/**
 * 获取当前用户
 * @author   宋汝波
 * @Date	 2013-12-23 	 
 */
public class SsoFetchUser {

	private static ThreadLocal<SsoContext> local = new ThreadLocal<SsoContext>();

	private static JedisCommands command;

	static {
		SsoFetchUser.command = ApplicationUtil.getBean(JedisCommands.class);
	}

	//	public SsoFetchUser(JedisCommands command) {
	//		SsoFetchUser.command = command;
	//	}

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
		return key + ssoType.key();
	}

	public static String key(final String redisKeyPrefix, final Object... objects) {
		if (null == redisKeyPrefix) {
			throw new NullPointerException("redis key前缀不能为空");
		}
		if (objects == null) {
			throw new NullPointerException("redis key后缀不为空");
		}
		StringBuilder sb = new StringBuilder(redisKeyPrefix);
		return sb.append(":").append(concat(":", objects)).toString();
	}

	public static <T> StringBuilder concat(Object c, T[] objs) {
		StringBuilder sb = new StringBuilder();
		if (null == objs || 0 == objs.length)
			return sb;

		sb.append(objs[0]);
		for (int i = 1; i < objs.length; i++)
			sb.append(c).append(objs[i]);

		return sb;
	}

	private static String fetchProxy(final SsoType ssoType, final String token) {
		String type = getRedisPrefix("sso", ssoType);
		command.expire(key("uni", type, token), 60 * 60);
		return command.get(key("uni", type, token));
	}

	protected static String getToken(HttpServletRequest request, String cookieName) {
		Cookie[] cookies = request.getCookies();
		if (cookies == null) {
			return null;
		}
		Cookie ck = null;
		for (Cookie cookie : cookies) {
			if (cookieName.equals(cookie.getName())) {
				ck = cookie;
			}
		}
		if (ck == null) {
			return null;
		}
		String token = ck.getValue();
		return token;
	}

	public long getCurrentUserIdWithEx() {
		long userId = getCurrentUserId();
		if (userId == -1) {
			throw new LogicException("您尚未登录,请先登录!", 1000);
		}
		return userId;
	}

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
		boolean isExist = command.exists(key("uni", type, token));
		if (!isExist) {
			return -1;
		}
		String userIdString = fetchProxy(SsoType.WEB, token);
		if (userIdString != null) {
			req.setAttribute("vkouserid", userIdString);
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

		Object userIdObject = req.getAttribute("vkouserid");
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
		if (userIdString == null) {
			return -1;
		}
		userId = Long.parseLong(userIdString);
		if (userId < 0) {
			return -1;
		}
		return userId;
	}

	public static long getUserIdWithEx() {
		long userId = getUserId();
		if (userId <= 0) {
			throw new LogicException("您尚未登录,请先登录!", 1000);
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
