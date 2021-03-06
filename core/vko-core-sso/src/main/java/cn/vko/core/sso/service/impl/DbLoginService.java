/**
 * DbLoginService.java
 * cn.vko.websso.service.impl
 * Copyright (c) 2013, 北京微课创景教育科技有限公司版权所有.
*/

package cn.vko.core.sso.service.impl;

/**
 * 从数据库获取用户登录信息
 * @author   彭文杰
 * @Date	 2013-11-7 	 
 */
import org.nutz.dao.Sqls;
import org.nutz.dao.entity.Record;
import org.nutz.dao.sql.Sql;

import cn.vko.core.common.util.BeanUtil;
import cn.vko.core.db.dao.IDbDao;
import cn.vko.core.sso.service.dto.SsoUser;

public abstract class DbLoginService extends AbstractLoginService {

	public DbLoginService() {
		super();
	}

	/**
	 * 使用数据库链接获取SsoUser数据
	 *
	 * @param dao 数据库连接
	 * @param loginName 登录名
	 * @param s sql语句
	 * @return 获取的用户对象
	 */
	protected SsoUser getUser(final IDbDao dao, final String loginName, final String s) {
		Sql sql = Sqls.create(s);
		sql.params().set("loginName", loginName);
		Record r = dao.fetch(sql);
		if (r == null) {
			return null;
		}
		SsoUser u = BeanUtil.map2Object(r, SsoUser.class);
		return u;
	}
	
}
