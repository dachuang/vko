/**
 * EhEntityCache.java
 * cn.vko.cache.ehcache
 * Copyright (c) 2014, 北京微课创景教育科技有限公司版权所有.
*/

package cn.vko.cache.ehcache;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;

import org.springframework.beans.factory.InitializingBean;

import cn.vko.cache.LockSupport;
import cn.vko.cache.util.EntityCache;
import cn.vko.cache.util.Invoker;

import com.vko.core.common.util.ObjectCompressUtil;

/**
 * ehcache实现的实体缓存
 * <p>
 * @author   宋汝波
 * @Date	 2014-7-9 	 
 */
public class EhEntityCache extends LockSupport implements EntityCache, InitializingBean {
	private CacheManager manager;

	@Override
	public Object get(String table_key, Object id, Invoker invoker) {
		return get(table_key, id, invoker, false);

	}

	private String getkey(String table_key, Object id) {
		StringBuilder key = new StringBuilder(table_key);
		key.append("#");
		key.append(id.toString());
		return key.toString();
	}

	@Override
	public Object get(String table_key, Object id, Invoker invoker, boolean compress) {
		String key = getkey(table_key, id);
		Cache cache = getCache(table_key);
		Object result = null;
		Element element = cache.get(key);
		if (element != null) {
			result = element.getObjectValue();
			if (compress) {
				result = ObjectCompressUtil.uncompress((byte[]) result);
			}
			return result;
		}
		Object aLock = lock.get(key);
		synchronized (aLock) {
			element = cache.get(key);
			if (element != null) {
				result = element.getObjectValue();
				if (compress) {
					result = ObjectCompressUtil.uncompress((byte[]) result);
				}
				return result;
			}
			result = invoker.invoke();
			if (result != null) {
				if (compress) {
					result = ObjectCompressUtil.compress(result);
				}
				//永久有效,直到被删除
				element = new Element(key, result, true, null, null);

				cache.put(element);
			}
		}
		return result;

	}

	@Override
	public void remove(String table_key, Object id) {
		Cache cache = getCache(table_key);
		cache.remove(getkey(table_key, id));
	}

	@Override
	public void removeAll(String table_key) {
		Cache cache = getCache(table_key);
		cache.removeAll();
	}

	private Cache getCache(String table_key) {
		Cache cache = null;
		String cacheName = "entity_cache:" + table_key;
		if (!manager.cacheExists(cacheName)) {
			cache = new Cache(cacheName, 100000, false, true, 0, 0);
			manager.addCacheIfAbsent(cache);

		} else {
			cache = manager.getCache(cacheName);
		}
		return cache;
	}

	@Override
	public void afterPropertiesSet() throws Exception {
	}

	public CacheManager getManager() {
		return manager;
	}

	public void setManager(CacheManager manager) {
		this.manager = manager;
	}

}
