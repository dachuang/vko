/**
 * SsoLogService.java
 * cn.vko.sso.service
 * Copyright (c) 2013, 北京微课创景教育科技有限公司版权所有.
*/

package cn.vko.core.sso.service;

import java.util.List;

import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;

import cn.vko.core.db.dao.IDbDao;
import cn.vko.core.sso.common.SsoType;
import cn.vko.core.sso.service.entity.SsoLog;
import cn.vko.core.sso.service.entity.SsoLog.LogType;

/**
 * 
 * @author   彭文杰
 * @Date	 2013-11-25 	 
 */
@IocBean
public class SsoLogService {
	@Inject
	private IDbDao ssoDao6;

	public SsoLogService() {

	}

	public SsoLogService(IDbDao ssoDao6) {
		this.ssoDao6 = ssoDao6;
	}

	/**
	 * 记录sso相关日志
	 *
	 * @param ssoType sso类别
	 * @param logType 记录类别
	 * @param userId 用户id
	 */
	public SsoLog log(final SsoType ssoType, final LogType logType, final String userId) {
		SsoLog sl = new SsoLog();
		sl.setLogType(logType.getKey());
		sl.setSsoType(ssoType.getKey());
		sl.setUserId(userId);
		ssoDao6.insert(sl);
		return sl;
	}

	/**
	 * 记录sso相关日志
	 *
	 * @param ssoType sso类别
	 * @param logType 记录类别
	 * @param userId 用户id
	 */
	public void batch(final List<SsoLog> ssoLogs) {
		ssoDao6.insert(ssoLogs);
	}

}
