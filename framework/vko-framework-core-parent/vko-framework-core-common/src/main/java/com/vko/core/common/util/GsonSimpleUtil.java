/**
 * GsonUtil.java
 * com.vko.core.common.util
 * Copyright (c) 2014, 北京微课创景教育科技有限公司版权所有.
*/

package com.vko.core.common.util;

import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.google.gson.Gson;

/**
 * 用Gson处理json
 * <p>
 * @author   宋汝波
 * @Date	 2014-5-14 	 
 */
public class GsonSimpleUtil {

	private static Gson gson = new Gson();

	public static String toJson(Object obj) {
		return gson.toJson(obj);
	}

	public static <T> T fromJson(String json, Class<T> cls) {
		return gson.fromJson(json, cls);
	}

	public static <T> T obj2Bean(Object obj, Class<T> cls) {
		return gson.fromJson(gson.toJson(obj), cls);
	}

	@SuppressWarnings("unchecked")
	public static Map<String, Object> obj2Map(Object obj) {
		return gson.fromJson(gson.toJson(obj), Map.class);
	}

	public static String toJSONString(Object obj) {

		return JSON.toJSONString(obj, SerializerFeature.WriteClassName);
	}

	public static <T> T parse(String json) {
		return (T) JSON.parse(json);
	}

	public static <T> List<T> parseArray(String json, Class<T> t) {
		return JSON.parseArray(json, t);
	}

	public static void main(String[] args) {
		String test = "[\"516\",\"518\"]";
		JSON.parseArray(test, String.class);
		System.out.println(JSON.parseArray(test, String.class).get(0).getClass());
	}
}
