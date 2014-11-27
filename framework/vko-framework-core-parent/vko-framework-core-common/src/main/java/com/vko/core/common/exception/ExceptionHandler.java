package com.vko.core.common.exception;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import com.vko.core.common.log.LogUtil;

public class ExceptionHandler implements HandlerExceptionResolver {
	protected String viewName;
	protected static final Logger log = LoggerFactory.getLogger(ExceptionHandler.class);
	protected ExceptionBuilder exceptionBuilder = new DefaultExceptionBuilder();

	@Override
	public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler,
			Exception ex) {
		if (ex != null) {
			Map<String, Object> model = new HashMap<String, Object>();
			if (exceptionBuilder != null) {
				String content = exceptionBuilder.buildException(request, response, handler, ex);
				model.put("exceptionContent", content);
			}
			if (ex instanceof VkoServiceException) {
				String referer = request.getHeader("referer");
				if (referer != null) {
					request.setAttribute("referer", referer);
				}
				request.setAttribute("message", ex.getMessage());
				if (viewName != null) {
					return new ModelAndView(viewName, model);
				}
				return null;
			}
			if (ex instanceof LogicException) {
				request.setAttribute("LogicException", ex);
				return new ModelAndView();
			}

			StringWriter writer = new StringWriter(500);
			ex.printStackTrace(new PrintWriter(writer));
			//			MemoryLog.addException(writer.toString());
			log.error(LogUtil.getInfo(request));
			log.error("您的程序出错了.", ex);
			//if (viewName != null) {
			//	return new ModelAndView(viewName, model);
			//}
		}
		return null;
	}

	public String getViewName() {
		return viewName;
	}

	public void setViewName(String viewName) {
		this.viewName = viewName;
	}

	public ExceptionBuilder getExceptionBuilder() {
		return exceptionBuilder;
	}

	public void setExceptionBuilder(ExceptionBuilder exceptionBuilder) {
		this.exceptionBuilder = exceptionBuilder;
	}

}
