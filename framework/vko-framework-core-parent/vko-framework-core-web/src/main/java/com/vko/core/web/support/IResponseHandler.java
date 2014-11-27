/**
 * IAppResponseHandler.java
 * com.bdsoft.rose.support
 * Copyright (c) 2014, 北京微课创景教育科技有限公司版权所有.
 */
package com.vko.core.web.support;

/**
 * 针对移动客户端等外部接口或设备的响应格式处理
 */
public interface IResponseHandler {

	public String formResponse(Object data);

	public String errorResponse(String msg);

	public static enum ResCode {

		SUCCESS(0, "成功"), FAILURE(1, "失败");

		private int code;
		private String msg;

		private ResCode(int code, String msg) {
			this.code = code;
			this.msg = msg;
		}

		public int getCode() {
			return code;
		}

		public void setCode(int flag) {
			this.code = flag;
		}

		public String getMsg() {
			return msg;
		}

		public void setMsg(String msg) {
			this.msg = msg;
		}
	}
}