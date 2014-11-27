/**
 * ParamLinkPageTag.java
 * cn.vko.core.web.www.tld
 * Copyright (c) 2013, 北京微课创景教育科技有限公司版权所有.
*/

package cn.vko.core.web.www.tld;

/**
 * V6课程分页标签实现
 * @author   高中秋
 * @Date	 2014-04-17 	 
 */
@SuppressWarnings("serial")
public class V6CoursePaginationTag extends LinkPageTag {
	@Override
	protected String getRealLink(final int pageNum) {
		StringBuilder link = new StringBuilder(getLink());
		link.append("_").append(pageNum).append("_").append(getPageSize()).append(".html");
		return link.toString();
	}
}
