/**
 * AbstractCms.java
 * cn.vko.core.cms.interfaces.impl
 * Copyright (c) 2013, 北京微课创景教育科技有限公司版权所有.
*/

package cn.vko.core.cms.interfaces.impl;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import cn.vko.core.cms.interfaces.ICms;
import cn.vko.core.common.util.Util;

/**
 * 抽象的cms父类
 * 主要提供统一的匹配类别的逻辑，比如将<cms:date uid="2013-12-31 12:30:00">
 * 从字符串中匹配出来，然后从Set<String>中移除，并将需要替换的内容放入needChange的Map中
 * @author   彭文杰
 * @Date	 2013-12-20 	 
 */
public abstract class AbstractCms implements ICms {
	/**
	 * 子类型
	 */
	public static final String SUB_TYPE = "st";
	/**
	 * 唯一标示
	 */
	public static final String UID = "uid";

	/**
	 * 匹配需要处理的字符串,并将需要处理的标签从总的标签中移除
	 *
	 * @param cmsTags 待处理的标签
	 * @return 需要处理的标签
	 */
	protected Set<String> matchStr(final Set<String> cmsTags) {
		if (Util.isEmpty(cmsTags)) {
			return null;
		}
		Set<String> result = new HashSet<String>();
		for (String tag : cmsTags) {
			if (Util.isEmpty(tag)) {
				continue;
			}
			if (tag.startsWith(getCmsTypeStr())) {
				result.add(tag);
			}
		}
		cmsTags.removeAll(result);
		return result;
	}

	private String getCmsTypeStr() {
		return "<cms:" + getType();
	}

	/**
	 * 获取标签类别
	 *
	 * @return 获取cms:xxx 中的xxx
	 */
	protected abstract String getType();

	@Override
	public void change(final HttpServletRequest req, final Set<String> cmsTags, final Map<String, String> needChange) {
		Set<String> needDeal = matchStr(cmsTags);
		if (Util.isEmpty(needDeal)) {
			return;
		}
		changeMatch(req, needDeal, needChange);
	}

	/**
	 * 将需要转换的内容，形成map
	 *
	 * @param needDeal 需要转换的内容
	 * @return 转换为map
	 */
	protected abstract void changeMatch(final HttpServletRequest req, final Set<String> needDeal,
			final Map<String, String> result);

	@Override
	public void rmCache(final String type, final String id) {

	}
}
