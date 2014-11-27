/**
 * CacheKeyPrefix.java
 * cn.vko.cache.util
 * Copyright (c) 2014, 北京微课创景教育科技有限公司版权所有.
*/

package cn.vko.cache.util;

/**
 * 根据不同请求生成不同缓存key的前缀
 * <p>
 * @author   宋汝波
 * @Date	 2014-7-7 	 
 */
public class CacheKeyPrefix {

	private static final ThreadLocal<String> KEY_HOLDER = new ThreadLocal<String>();

	public static void set(String key) {
		KEY_HOLDER.set(key);
	}

	public static String get() {
		String prefix = KEY_HOLDER.get();
		if (prefix == null) {
			return "";
		}
		return prefix;
	}

	public static void remove() {
		KEY_HOLDER.remove();
	}
}
