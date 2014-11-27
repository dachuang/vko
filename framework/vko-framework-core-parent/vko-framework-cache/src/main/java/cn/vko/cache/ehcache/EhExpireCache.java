/**
 * EhExpireCache.java
 * cn.vko.cache.ehcache
 * Copyright (c) 2014, 北京微课创景教育科技有限公司版权所有.
*/

package cn.vko.cache.ehcache;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.Assert;

import cn.vko.cache.LockSupport;
import cn.vko.cache.util.ExpireCache;
import cn.vko.cache.util.Invoker;

import com.vko.core.common.util.ObjectCompressUtil;

/**
 * ehcache实现按时间缓存
 * <p>
 * @author   宋汝波
 * @Date	 2014-7-7 	 
 */
public class EhExpireCache extends LockSupport implements ExpireCache, InitializingBean {
	private Cache cache;
	private CacheManager manager;
	private String cacheName;

	public void setCache(Cache cache) {
		this.cache = cache;
	}

	@Override
	public Object get(String key, Invoker invoker, int expire) {
		return get(key, invoker, expire, false);

	}

	@Override
	public void remove(String key) {
		cache.remove(key);
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		Assert.notNull(cacheName, "缓存名称不能为null");
		cache = manager.getCache(cacheName);
	}

	public String getCacheName() {
		return cacheName;
	}

	public void setCacheName(String cacheName) {
		this.cacheName = cacheName;
	}

	public CacheManager getManager() {
		return manager;
	}

	public void setManager(CacheManager manager) {
		this.manager = manager;
	}

	@Override
	public Object get(String key, Invoker invoker, int expire, boolean compress) {

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
					element = new Element(key, ObjectCompressUtil.compress(result), false, 0, expire);
				} else {
					element = new Element(key, result, false, 0, expire);

				}
				cache.put(element);
			}
		}
		return result;

	}

}
