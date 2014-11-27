package com.vko.core.web.wrap;

import java.io.IOException;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

import com.vko.core.web.wrap.cookie.CookieConfig;
import com.vko.core.web.wrap.cookie.SessionFactory;

public class ResponseWrapper extends HttpServletResponseWrapper {
	SessionFactory sessionFactory;

	public ResponseWrapper(HttpServletResponse response, SessionFactory sessionFactory) {
		super(response);
		this.sessionFactory = sessionFactory;
	}
	@Override
	public void flushBuffer() throws IOException {
		//disabled
		throw new RuntimeException("不支持清空缓冲区!");
	}
	@Override
	public void addCookie(Cookie cookie) {
		// 检测是否允许添加对应名字的cookie
		CookieConfig cookieConfig = sessionFactory.getCookieConfig();
		String[] cookieNames = cookieConfig.getAllowCookieNames();
		if (cookieNames != null) {
			boolean search = false;
			for (int i = 0; i < cookieNames.length; i++) {
				if (cookie.getName().equals(cookieNames[i])) {
					search = true;
					break;
				}
			}
			if (!search) {
				throw new RuntimeException(cookie.getName() + "设定为不能向Cookie中添加");
			}
		}
		super.addCookie(cookie);
	}
}
