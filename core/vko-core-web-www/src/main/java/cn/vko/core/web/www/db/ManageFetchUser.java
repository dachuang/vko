/**
 * VkoWebFetchUser.java
 * cn.vko.webbmp.db.entity
 * Copyright (c) 2013, 北京微课创景教育科技有限公司版权所有.
*/

package cn.vko.core.web.www.db;

import static org.nutz.lang.Lang.*;
import static org.nutz.mvc.Mvcs.*;

import javax.servlet.http.HttpServletRequest;

import cn.vko.core.common.exception.impl.TimeoutException;
import cn.vko.core.db.dao.impl.support.IFetchUser;

/**
 * 后台管理系统获取当前用户的方法
 * <p>
 * 针对于bmp_user
 *
 * @author   庄君祥
 * @Date	 2013-9-24 	 
 */
public class ManageFetchUser implements IFetchUser {
	/**
	 * http请求中获取当前用户的主键的键值
	 */
	public final static String REQUEST_USER_ID_KEY = "vkouserid";

	/**
	 * http请求中获取当前用户真实姓名的键值
	 */
	public final static String REQUEST_REAL_NAME_KEY = "vkorealname";

	@Override
	public long getCurrentUserId() {
		HttpServletRequest request = getReq();
		if (isEmpty(request)) {
			return -1;
		}
		Object userId = getHttpSession().getAttribute(REQUEST_USER_ID_KEY);
		if (isEmpty(userId)) {
			return -1;
		}
		return Long.valueOf(userId.toString());
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
		HttpServletRequest request = getReq();
		if (isEmpty(request)) {
			return "";
		}
		Object obj = getHttpSession().getAttribute(REQUEST_REAL_NAME_KEY);
		if (isEmpty(obj)) {
			return "";
		}
		return String.valueOf(obj);
	}

}
