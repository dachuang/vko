/**
 * DefaultHandlerInterceptor.java
 * cn.vko.zuoye.interceptor
 * Copyright (c) 2014, 北京微课创景教育科技有限公司版权所有.
*/

package cn.vko.zuoye.interceptor;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import cn.vko.cache.annotation.HTMLCache;
import cn.vko.zuoye.service.sso.SsoContext;
import cn.vko.zuoye.service.sso.SsoFetchUser;

import com.vko.core.common.exception.LogicException;
import com.vko.core.common.util.ApplicationUtil;
import com.vko.core.common.util.ConfigKey;
import com.vko.core.common.util.KvConfig;
import com.vko.core.web.html.HtmlFlag;
import com.vko.core.web.html.HtmlFlagBean;

/**
 * 拦截所有sprinmvc请求
 * <p>
 * @author   宋汝波
 * @Date	 2014-7-10 	 
 */
public class DefaultHandlerInterceptor implements HandlerInterceptor {

	private Logger logger = LoggerFactory.getLogger(DefaultHandlerInterceptor.class);

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		if (handler instanceof HandlerMethod) {
			SsoFetchUser.setContext(new SsoContext(request));
			HandlerMethod handlerMethod = (HandlerMethod) handler;
			HTMLCache annotation = handlerMethod.getMethod().getAnnotation(HTMLCache.class);
			if (annotation != null) {
				HtmlFlag.doFlag(new HtmlFlagBean(annotation.expire()));
			}
		}
		return true;

	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		if (modelAndView != null) {
			//HttpInclude include = new HttpInclude(request, response);
			//modelAndView.addObject("httpInclude", include);
		}

	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		SsoFetchUser.removeContext();
		Object obj = request.getAttribute("LogicException");
		//		//检测超时异常,到登陆页面
		if (obj instanceof LogicException) {
			LogicException logic = (LogicException) obj;
			if (logic.getCode() == 1000) {
				//支持登陆成功后跳转操作
				String loginUrl = ApplicationUtil.getBean(KvConfig.class).getValue(ConfigKey.LOGIN_PAGE.key());
				String rUrl = getDestinationUrl(request, loginUrl);
				response.sendRedirect(rUrl);
			}

		}
	}

	public static String getUrl(final HttpServletRequest request) {
		StringBuilder url = new StringBuilder();
		url.append(request.getRequestURL());
		String queryString = request.getQueryString();
		if (queryString != null) {
			url.append("?").append(queryString);
		}
		return url.toString();
	}

	public static String getDestinationUrl(final HttpServletRequest request, final String url)
			throws UnsupportedEncodingException {
		StringBuilder redirectUrl = new StringBuilder(url);
		String oldUrl = getUrl(request);
		if (redirectUrl.indexOf("?") > -1) {
			redirectUrl.append("&");
		} else {
			redirectUrl.append("?");
		}
		redirectUrl.append("destinationUrl").append("=").append(URLEncoder.encode(oldUrl, "UTF-8"));

		return redirectUrl.toString();
	}
}
