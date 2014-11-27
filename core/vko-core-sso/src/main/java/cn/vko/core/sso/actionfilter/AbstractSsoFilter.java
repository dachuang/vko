/**
 * SsoFilter.java
 * cn.vko.web.common.actionfilter
 * Copyright (c) 2013, 北京微课创景教育科技有限公司版权所有.
*/

package cn.vko.core.sso.actionfilter;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import lombok.Data;

import org.nutz.mvc.ActionContext;
import org.nutz.mvc.ActionFilter;
import org.nutz.mvc.View;

import cn.vko.core.common.util.Util;
import cn.vko.core.sso.common.SsoType;
import cn.vko.core.sso.service.RedisSsoService;
import cn.vko.core.web.config.ConfigKey;
import cn.vko.core.web.config.KvConfig;
import cn.vko.core.web.util.RequestUtil;

/**
 * 单点登录过滤器
 * @author   彭文杰
 * @Date	 2013-11-6 	 
 */
@Data
public abstract class AbstractSsoFilter implements ActionFilter {
	private KvConfig config;
	private RedisSsoService redisSsoService;

	/**
	 * http请求中获取当前用户的主键的键值
	 */
	public final static String REQUEST_USER_ID_KEY = "vkouserid";

	/**
	 * cookie中存取userId的主键的键值
	 */
	public final static String COOKIE_USERID_KEY = "uid";

	@Override
	public View match(final ActionContext ac) {
		String userId = null;
		try {
			HttpServletRequest req = ac.getRequest();
			String host = req.getServerName();
			//临时策略
			if (!Util.isEmpty(host) && host.indexOf("z.vko.cn") != -1) {
				userId = getUserId(ac);

			} else {

				String token = getToken(ac);
				if (Util.isEmpty(token)) {
					return null;
				}
				userId = redisSsoService.fetch(getSsoType(), token);
				if (Util.isEmpty(userId)) {
					return null;
				}
			}
			ac.getRequest().setAttribute(REQUEST_USER_ID_KEY, userId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;

	}

	/**
	 * 从请求中获取token
	 *
	 * @param ac 请求上下文
	 * @return token
	 */
	protected String getToken(final ActionContext ac) {
		try {
			Cookie ck = RequestUtil.getCookie(ac.getRequest(), config.getValue(ConfigKey.SSO_COOKIE_KEY));
			if (ck == null) {
				return null;
			}
			String token = ck.getValue();
			return token;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 从请求中获取userId
	 * <p>
	 * 只适用于小灶
	 * 
	 * @param	ac 请求上下文
	 * @return 	userId
	 */
	private String getUserId(final ActionContext ac) {
		try {
			Cookie ck = RequestUtil.getCookie(ac.getRequest(), COOKIE_USERID_KEY);
			if (ck == null) {
				return null;
			}
			return ck.getValue();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 获取当前sso的类别
	 * 
	 * @return sso的类别
	 */
	protected abstract SsoType getSsoType();
}
