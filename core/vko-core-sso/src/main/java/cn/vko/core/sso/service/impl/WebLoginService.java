/**
 * WebLoginService.java
 * cn.vko.websso.service.impl
 * Copyright (c) 2013, 北京微课创景教育科技有限公司版权所有.
*/

package cn.vko.core.sso.service.impl;

import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;

import cn.vko.core.common.util.Util;
import cn.vko.core.db.dao.IDbDao;
import cn.vko.core.sso.common.SsoType;
import cn.vko.core.sso.service.dto.SsoUser;

/**
 * 网站使用的登录服务
 * @author   彭文杰
 * @Date	 2013-10-18 	 
 */
@IocBean(fields = { "redisSsoService" })
public class WebLoginService extends DbLoginService {
	@Inject
	private IDbDao webDao6;

	@Override
	public SsoType getSsoType() {
		return SsoType.WEB;
	}

	@Override
	public SsoUser getUser(final String loginName) {
		String s = "select id,password,status as valid from web_user where loginName=@loginName limit 1";
		SsoUser u = getUser(webDao6, loginName, s);
		if (Util.isEmpty(u)) {
			String str = "select wu.id,wu.password,wu.status as valid from web_user wu,user_relation ur where ur.userId=wu.id and ur.loginName=@loginName limit 1";
			u = getUser(webDao6, loginName, str);
		}
		return u;
	}

}
