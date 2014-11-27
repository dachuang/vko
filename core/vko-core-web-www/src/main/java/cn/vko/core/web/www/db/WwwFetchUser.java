/**
 * WwwFetchUser.java
 * cn.vko.core.web.www.db
 * Copyright (c) 2013, 北京微课创景教育科技有限公司版权所有.
*/

package cn.vko.core.web.www.db;

import javax.servlet.http.HttpServletRequest;

import org.nutz.mvc.Mvcs;

import cn.vko.core.common.exception.impl.TimeoutException;
import cn.vko.core.common.util.ConvertUtil;
import cn.vko.core.db.dao.impl.support.IFetchUser;
import cn.vko.core.sso.actionfilter.AbstractSsoFilter;

/**
 * 获取当前用户
 * @author   彭文杰
 * @Date	 2013-12-23 	 
 */
public class WwwFetchUser implements IFetchUser {

	@Override
	public long getCurrentUserId() {
		HttpServletRequest req = Mvcs.getReq();
		if (req == null) {
			return -1;
		}
		Object userIdObject = req.getAttribute(AbstractSsoFilter.REQUEST_USER_ID_KEY);

		long userId = ConvertUtil.obj2long(userIdObject);
		if (userId < 0) {
			return -1;
		}
		return userId;
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
		//TODO 需要吗？
		return "todo";
	}

}
