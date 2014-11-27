/**
 * JsonResponseHandler.java
 * com.bdsoft.rose.support
 * Copyright (c) 2014, 北京微课创景教育科技有限公司版权所有.
 */
package cn.vko.core.web.chain.support;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

import cn.vko.core.common.util.MapUtil;

import com.google.gson.Gson;

/**
 * Json格式 响应处理
 */
public class AppJsonResponse {

	/**
	 * 正常响应返回
	 */
	public static String formResponse(Object data) {
		return new Gson().toJson(new JsonBody(ResCode.SUCCESS, data));
	}

	/**
	 * 错误响应返回
	 */
	public static String errorResponse(String msg) {
		return new Gson().toJson(new JsonBody(ResCode.FAILURE, MapUtil.map(msg)));
	}

	/**
	 * 返回正常数据
	 */
	public static Map<String, Object> successData(Object data) {
		LinkedHashMap<String, Object> ret = new LinkedHashMap<String, Object>();
		ret.put("code", ResCode.SUCCESS.getCode());
		ret.put("data", data);
		return ret;
	}

	/**
	 * 返回错误数据
	 */
	public static Map<String, Object> errorData(String msg) {
		Map<String, Object> ret = new LinkedHashMap<String, Object>();
		ret.put("code", ResCode.FAILURE.getCode());
		ret.put("data", MapUtil.map("error", msg));
		return ret;
	}

	/**
	 * JSON格式响应的基本结构体
	 */
	static class JsonBody {

		int code;
		long servertime;
		Object data;

		public JsonBody() {
		}

		public JsonBody(ResCode op, Object data) {
			this.code = op.getCode();
			this.data = data;
			this.servertime = new Date().getTime();
		}
	}

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