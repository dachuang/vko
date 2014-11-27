/**
 * SsoType.java
 * cn.vko.websso.common
 * Copyright (c) 2013, 北京微课创景教育科技有限公司版权所有.
*/

package cn.vko.zuoye.service.sso;

/**
 * sso的类别
 * @author   彭文杰
 * @Date	 2013-10-18 	 
 */
public enum SsoType {
	WEB(1, "网站"), MANAGE(2, "管理后台");

	private int key;
	private String value;

	private SsoType(final int key, final String value) {
		this.value = value;
		this.key = key;
	}

	public String key() {
		return String.valueOf(key);
	}

	public int getKey() {
		return key;
	}

	public String value() {
		return value;
	}

}
