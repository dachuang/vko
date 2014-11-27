package cn.vko.cache.service;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import cn.vko.cache.annotation.Cacheable;
import cn.vko.cache.annotation.ServiceCache;
import cn.vko.cache.util.CacheOperation;
import cn.vko.cache.util.CacheOperationContext;
import cn.vko.cache.util.ExpireCache;
import cn.vko.cache.util.Invoker;

/**
 * @author bo
 *
 */
public abstract class ServiceCacheAspectSupport implements ApplicationContextAware, InitializingBean {

	protected ApplicationContext applicationContext;

	private ExpireCache expireCache;

	protected Object execute(Invoker invoker, Method method, Object[] args) {
		if (!CacheOperationContext.getCache()) {
			return invoker.invoke();
		}
		final Collection<CacheOperation> cacheOp = getCacheOperations(method);
		Object retVal = null;
		boolean[] isInvoke = new boolean[] { false };
		if (!CollectionUtils.isEmpty(cacheOp)) {
			Collection<CacheOperationContext> ops = createOperationContext(cacheOp, method, args);
			retVal = inspectTimeCaches(ops, invoker, isInvoke);
			if (isInvoke[0]) {
				return retVal;
			}

		}

		return invoker.invoke();
	}

	public Collection<CacheOperation> getCacheOperations(Method method) {
		Annotation[] anntations = method.getAnnotations();
		if (anntations.length == 0) {
			return null;
		}
		List<CacheOperation> list = new ArrayList<CacheOperation>(2);
		for (Annotation annotation : anntations) {
			Class<?> c = annotation.getClass();
			if (ServiceCache.class.isAssignableFrom(c)) {
				ServiceCache annot = (ServiceCache) annotation;
				// 处理过期
				CacheOperation cacheOperation = new CacheOperation();
				cacheOperation.setExpire(annot.expire());
				cacheOperation.setCompress(annot.compress());
				//cacheOperation.setOperation(annot.operation());
				list.add(cacheOperation);
			} else if (Cacheable.class.isAssignableFrom(c)) {
				Cacheable annot = (Cacheable) annotation;
				CacheOperationContext.setCache(annot.cache());
				return null;
			}
		}
		return list;
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		Assert.notNull(expireCache, "expireCache不能为null");

	}

	private Object inspectTimeCaches(Collection<CacheOperationContext> cacheables, Invoker invoker, boolean[] isInvoke) {
		Object retVal = null;
		if (!cacheables.isEmpty()) {
			for (CacheOperationContext context : cacheables) {
				Object key = context.generateKey();
				String key_string = key.toString();
				retVal = expireCache.get(key_string, invoker, context.getExpireTime(), context.isCompress());
				isInvoke[0] = true;

			}
		}

		return retVal;
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;

	}

	private List<CacheOperationContext> createOperationContext(Collection<CacheOperation> cacheOp, Method method,
			Object[] args) {
		List<CacheOperationContext> list_time = new ArrayList<CacheOperationContext>(2);
		for (CacheOperation cacheOperation : cacheOp) {
			CacheOperationContext opContext = new CacheOperationContext(cacheOperation, method, args);
			list_time.add(opContext);
		}

		return list_time;
	}

	public ExpireCache getExpireCache() {
		return expireCache;
	}

	public void setExpireCache(ExpireCache expireCache) {
		this.expireCache = expireCache;
	}

}