package cn.vko.cache.util;

public interface ExpireCache {

	public Object get(String key, Invoker invoker, int expire);

	public Object get(String key, Invoker invoker, int expire, boolean compress);

	public void remove(String key);

}
