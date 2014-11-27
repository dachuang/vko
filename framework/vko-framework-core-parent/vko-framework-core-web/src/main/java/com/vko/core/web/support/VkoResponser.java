/**
 * 
 */
package com.vko.core.web.support;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Scope("prototype")
@Component
public class VkoResponser {

	@Autowired
	@Qualifier("jsonResponseHandler")
	private IResponseHandler responseHandler;

	/**
	 * 成功响应返回
	 * @param data
	 * @return
	 */
	public String response(Object data) {
		return responseHandler.formResponse(data);
	}

	/**
	 * 失败错误返回
	 * @return
	 */
	public String error(String msg) {
		return responseHandler.errorResponse(msg);
	}
}
