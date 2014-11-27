package cn.vko.cache.util;

/**
 * 对象实体缓存
 * <p>
 * @author   宋汝波
 * @Date	 2014-7-9
 */
public interface EntityCache {

	public Object get(String table_key, Object id, Invoker invoker);

	public Object get(String table_key, Object id, Invoker invoker, boolean compress);

	public void remove(String table_key, Object id);

	public void removeAll(String table_key);
}
