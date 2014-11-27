/**
 * SsoContext.java
 * cn.vko.core.sso.common
 * Copyright (c) 2014, 北京微课创景教育科技有限公司版权所有.
*/

package cn.vko.zuoye.service.sso;

import javax.servlet.http.HttpServletRequest;

/**
 * 封装类,用于简单的获取用户id
 * <p>
 * @author   宋汝波
 * @Date	 2014-5-21 	 
 */
public class SsoContext {
	HttpServletRequest request;
	String cookieName = "vkoweb";

	public SsoContext(HttpServletRequest request, String cookieName) {
		this.request = request;
		this.cookieName = cookieName;
	}

	public SsoContext(HttpServletRequest request) {
		this.request = request;
	}

	public HttpServletRequest getRequest() {
		return request;
	}

	public void setRequest(HttpServletRequest request) {
		this.request = request;
	}

	public String getCookieName() {
		return cookieName;
	}

	public void setCookieName(String cookieName) {
		this.cookieName = cookieName;
	}

}
