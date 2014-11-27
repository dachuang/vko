/**
 * ManageLoginService.java
 * cn.vko.websso.service.impl
 * Copyright (c) 2013, 北京微课创景教育科技有限公司版权所有.
*/

package cn.vko.core.sso.service.impl;

import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;

import cn.vko.core.db.dao.IDbDao;
import cn.vko.core.sso.common.SsoType;
import cn.vko.core.sso.service.dto.SsoUser;

/**
 * 管理端使用的登录账号
 * @author   彭文杰
 * @Date	 2013-10-18 	 
 */
@IocBean(fields = { "redisSsoService" })
public class ManageLoginService extends DbLoginService {
	@Inject
	private IDbDao manageDao6;

	@Override
	public SsoUser getUser(final String loginName) {
		String s = "select id,password,valid from bmp_user where loginName=@loginName";
		return getUser(manageDao6, loginName, s);
	}

	@Override
	public SsoType getSsoType() {
		return SsoType.MANAGE;
	}

}
