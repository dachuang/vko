/**
 * JsonResponseHandler.java
 * com.bdsoft.rose.support
 * Copyright (c) 2014, 北京微课创景教育科技有限公司版权所有.
 */
package com.vko.core.web.support;

import java.util.Date;

import org.springframework.stereotype.Service;

import com.google.gson.Gson;

/**
 * Json格式 响应处理
 */
@Service
public class JsonResponseHandler implements IResponseHandler {

	/**
	 * 正常响应返回
	 */
	@Override
	public String formResponse(Object data) {
		return new Gson().toJson(new JsonBody(ResCode.SUCCESS, data));
	}

	/**
	 * 错误响应返回
	 */
	@Override
	public String errorResponse(String msg) {
		return new Gson().toJson(new JsonBody(msg));
	}

	/**
	 * JSON格式响应的基本结构体
	 */
	class JsonBody {

		int code;
		String msg;
		long servertime;
		Object data;

		public JsonBody() {
		}

		public JsonBody(String msg) {
			this.code = ResCode.FAILURE.getCode();
			this.msg = msg;
		}

		public JsonBody(ResCode op, Object data) {
			this.code = op.getCode();
			this.msg = op.getMsg();
			this.data = data;
			this.servertime = new Date().getTime();
		}
	}

}