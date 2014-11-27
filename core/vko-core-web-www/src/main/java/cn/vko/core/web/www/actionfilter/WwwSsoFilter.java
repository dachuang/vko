/**
 * WwwSsoFilter.java
 * cn.vko.web.www.actionfilter
 * Copyright (c) 2013, 北京微课创景教育科技有限公司版权所有.
*/

package cn.vko.core.web.www.actionfilter;

import cn.vko.core.sso.actionfilter.AbstractSsoFilter;
import cn.vko.core.sso.common.SsoType;

/**
 * sso过滤器
 * @author   彭文杰
 * @Date	 2013-11-14 	 
 */
public class WwwSsoFilter extends AbstractSsoFilter {

	@Override
	protected SsoType getSsoType() {
		return SsoType.WEB;
	}

}
