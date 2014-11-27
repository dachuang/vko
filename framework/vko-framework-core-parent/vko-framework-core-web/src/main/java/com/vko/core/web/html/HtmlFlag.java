package com.vko.core.web.html;

/**
 * @author bo 标识用
 */
public abstract class HtmlFlag {

	private static final ThreadLocal<HtmlFlagBean> flag = new ThreadLocal<HtmlFlagBean>();

	public static void doFlag(HtmlFlagBean bean) {
		flag.set(bean);
	}

	public static HtmlFlagBean getFlag() {
		HtmlFlagBean bool = flag.get();
		return bool;
	}

	public static void removeFlag() {
		flag.remove();
	}
}
