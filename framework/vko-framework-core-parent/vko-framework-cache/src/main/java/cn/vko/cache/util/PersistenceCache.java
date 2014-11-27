/**
 * PersistenceCache.java
 * cn.vko.cache.util
 * Copyright (c) 2014, 北京微课创景教育科技有限公司版权所有.
*/

package cn.vko.cache.util;

/**
 * 永久缓存,直到全部清空
 * <p>
 * @author   宋汝波
 * @Date	 2014-7-9 	 
 */
public interface PersistenceCache {

	public Object get(String table_key, Object key, Invoker invoker);

	public Object get(String table_key, Object key, Invoker invoker, boolean compress);

	public void removeAll(String table_key);
}
