/**
 * LoginServiceFactory.java
 * cn.vko.websso.service.support
 * Copyright (c) 2013, 北京微课创景教育科技有限公司版权所有.
*/

package cn.vko.core.sso.service.support;

import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;

import cn.vko.core.common.util.EnumUtil;
import cn.vko.core.sso.common.SsoType;
import cn.vko.core.sso.service.ILoginService;
import cn.vko.core.sso.service.impl.DbLoginService;
import cn.vko.core.sso.service.impl.ManageLoginService;

/**
 * 登录服务的工厂类
 * @author   dell
 * @Date	 2013-10-23 	 
 */
@IocBean
public class LoginServiceFactory {
	@Inject
	private ManageLoginService manageLoginService;
	@Inject
	private DbLoginService webLoginService;

	public ILoginService getService(final String typeStr) {
		SsoType type = EnumUtil.get(SsoType.class, typeStr);
		switch (type) {
		case WEB:
			return webLoginService;
		case MANAGE:
			return manageLoginService;
		default:
			return webLoginService;
		}
	}
}
