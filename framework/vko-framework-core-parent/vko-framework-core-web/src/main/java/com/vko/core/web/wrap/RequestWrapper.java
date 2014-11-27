package com.vko.core.web.wrap;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.vko.core.web.wrap.cookie.SessionFactory;

public class RequestWrapper extends HttpServletRequestWrapper {

	private HttpSession wrapper;

	public RequestWrapper(HttpServletRequest request, HttpServletResponse response, ServletContext context,
			SessionFactory sessionFactory) {
		super(request);
		PersistenceSession session = sessionFactory.buildSession(request, response);
		wrapper = new HttpSessionWrapper(context, session);
		session.setSessionListener(sessionFactory.getSessionListener());
		session.setAttributeListener(sessionFactory.getAttributeListener());
		session.setWrappedSession(wrapper);
		session.init();

	}

	@Override
	public HttpSession getSession() {
		return getSession(true);
	}

	@Override
	public HttpSession getSession(boolean create) {
		return wrapper;
	}

}
