package cn.vko.cache.util;

import java.lang.reflect.Method;
import java.util.HashMap;

import ognl.Ognl;
import ognl.OgnlException;

import org.springframework.cache.interceptor.KeyGenerator;

public class CacheOperationContext {

	//保存是否对当前线程操作进行缓存
	private static ThreadLocal<Boolean> threadCachehoder = new ThreadLocal<Boolean>();

	private final KeyGenerator keyGenerator = new HashCodeKeyGenerator();

	private final CacheOperation operation;

	private final Method method;

	private final Object[] args;

	public CacheOperationContext(CacheOperation operation, Method method, Object[] args) {
		this.operation = operation;
		this.method = method;
		this.args = args;
	}

	public Object generateKey() {
		return keyGenerator.generate(method.getDeclaringClass(), this.method, this.args);
	}

	public Long getId() {
		try {
			HashMap<String, Object> vars = new HashMap<String, Object>(1);
			vars.put("args", args);
			Object obj = Ognl.getValue(this.operation.getId(), vars);
			return new Long(obj.toString());
		} catch (OgnlException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static void setCache(boolean isCache) {
		threadCachehoder.set(isCache);
	}

	public static boolean getCache() {
		Boolean isCahe = threadCachehoder.get();
		if (isCahe == null) {
			return true;
		}
		return isCahe;
	}

	public static void removeCache() {
		threadCachehoder.remove();
	}

	public String getTableName() {
		return this.operation.getTable_key();
	}

	public int getOperation() {
		return this.operation.getOperation();
	}

	public int getExpireTime() {
		return this.operation.getExpire();
	}

	public boolean isCompress() {
		return this.operation.isCompress();
	}
}
