package cn.vko.cache.dao.mybatis;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.ibatis.logging.Log;
import org.apache.ibatis.logging.LogFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.util.CollectionUtils;

import cn.vko.cache.annotation.EntityObjectCache;
import cn.vko.cache.annotation.ExpireObjectCache;
import cn.vko.cache.util.CacheOperation;
import cn.vko.cache.util.CacheOperationContext;
import cn.vko.cache.util.EntityCache;
import cn.vko.cache.util.ExpireCache;
import cn.vko.cache.util.Invoker;

/**
 * @author bo
 * 
 */
public abstract class MapperCacheAspectSupport implements ApplicationContextAware {

	protected ApplicationContext applicationContext;

	protected final Log logger = LogFactory.getLog(getClass());

	private EntityCache entityCache;
	private ExpireCache expireCache;

	protected Object execute(Invoker invoker, Method method, Object[] args) {
		if (!CacheOperationContext.getCache()) {
			return invoker.invoke();
		}
		Annotation[] anntations = method.getAnnotations();
		if (anntations == null || anntations.length == 0) {
			return invoker.invoke();
		}
		boolean[] isEntity = new boolean[] { false };
		final Collection<CacheOperation> cacheOp = getCacheOperations(anntations, isEntity);
		Object retVal = null;
		boolean[] isInvoke = new boolean[] { false };
		if (!CollectionUtils.isEmpty(cacheOp)) {
			Collection<CacheOperationContext> ops = createOperationContext(cacheOp, method, args);
			if (isEntity[0]) {
				retVal = inspectEntityCaches(ops, invoker, isInvoke);
			} else {
				retVal = inspectTimeCaches(ops, invoker, isInvoke);
			}
			if (isInvoke[0]) {
				return retVal;
			}
		}
		return invoker.invoke();
	}

	public Collection<CacheOperation> getCacheOperations(Annotation[] anntations, boolean[] isEntity) {
		List<CacheOperation> list = new ArrayList<CacheOperation>(2);
		for (Annotation annotation : anntations) {
			Class<?> c = annotation.getClass();
			if (EntityObjectCache.class.isAssignableFrom(c)) {
				isEntity[0] = true;
				EntityObjectCache annot = (EntityObjectCache) annotation;
				CacheOperation cacheOperation = new CacheOperation();
				cacheOperation.setId(annot.id());
				cacheOperation.setOperation(annot.operation());
				cacheOperation.setTable_key(annot.table_key());
				cacheOperation.setCompress(annot.compress());
				list.add(cacheOperation);
			} else if (ExpireObjectCache.class.isAssignableFrom(c)) {
				isEntity[0] = false;
				ExpireObjectCache annot = (ExpireObjectCache) annotation;
				CacheOperation cacheOperation = new CacheOperation();
				cacheOperation.setExpire(annot.expire());
				cacheOperation.setCompress(annot.compress());
				list.add(cacheOperation);
			}
		}
		return list;
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;

	}

	private Object inspectTimeCaches(Collection<CacheOperationContext> cacheables, Invoker invoker, boolean[] isInvoke) {
		if (entityCache == null) {
			expireCache = applicationContext.getBean(ExpireCache.class);
		}
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

	private Object inspectEntityCaches(Collection<CacheOperationContext> entity, Invoker invoker, boolean[] isInvoke) {
		if (entityCache == null) {
			entityCache = applicationContext.getBean(EntityCache.class);
		}
		Object retObj = null;
		if (!entity.isEmpty()) {
			for (CacheOperationContext context : entity) {
				switch (context.getOperation()) {
				case CacheOperation.SELECT_BY_ID:
					// 缓存有走缓存
					String table_name = context.getTableName();
					Long id = context.getId();
					retObj = entityCache.get(table_name, id, invoker);
					isInvoke[0] = true;
					break;
				case CacheOperation.UPDATE_BY_ID:
					// 先进行编辑操作,在从数据库获取新值
					retObj = invoker.invoke();
					isInvoke[0] = true;
					entityCache.remove(context.getTableName(), context.getId());
					break;
				case CacheOperation.DELETE_BY_ID:
					retObj = invoker.invoke();
					entityCache.remove(context.getTableName(), context.getId());
					isInvoke[0] = true;
					break;
				case CacheOperation.DELETE_ALL:
					retObj = invoker.invoke();
					entityCache.removeAll(context.getTableName());
					isInvoke[0] = true;
					break;
				}

			}
		}

		return retObj;
	}

	public ExpireCache getExpireCache() {
		return expireCache;
	}

	public void setExpireCache(ExpireCache expireCache) {
		this.expireCache = expireCache;
	}

	private List<CacheOperationContext> createOperationContext(Collection<CacheOperation> cacheOp, Method method,
			Object[] args) {
		List<CacheOperationContext> list_entity = new ArrayList<CacheOperationContext>(2);
		for (CacheOperation cacheOperation : cacheOp) {
			CacheOperationContext opContext = new CacheOperationContext(cacheOperation, method, args);
			list_entity.add(opContext);
		}
		return list_entity;
	}

}