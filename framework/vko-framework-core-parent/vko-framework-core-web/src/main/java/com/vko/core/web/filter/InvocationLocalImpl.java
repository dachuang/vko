/*
 * Copyright 2007-2009 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.vko.core.web.filter;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.context.WebApplicationContext;

/**
 * 
 * @author 王志亮 [qieqie.wang@gmail.com]
 * 
 */
public class InvocationLocalImpl implements InvocationLocal {

	@Override
	public Invocation getCurrent(boolean required) {
		Invocation inv = InvocationUtils.getInvocation(InvocationUtils.getCurrentThreadRequest());
		if (inv == null && required) {
			throw new IllegalStateException("invocation");
		}
		return inv;
	}

	private Invocation required() {
		return getCurrent(true);
	}

	@Override
	public WebApplicationContext getApplicationContext() {
		return required().getApplicationContext();
	}

	@Override
	public Object getAttribute(String name) {
		return required().getAttribute(name);
	}

	@Override
	public String getParameter(String name) {
		return required().getParameter(name);
	}

	@Override
	public HttpServletRequest getRequest() {
		return required().getRequest();
	}

	@Override
	public HttpServletResponse getResponse() {
		return required().getResponse();
	}

	@Override
	public ServletContext getServletContext() {
		return required().getServletContext();
	}

	@Override
	public void removeAttribute(String name) {
		required().removeAttribute(name);

	}

	@Override
	public Invocation setAttribute(String name, Object value) {
		return required().setAttribute(name, value);
	}

}
