/**
 * EntityCacheInterceptor.java
 * cn.vko.cache.dao.mybatis.interceptor
 * Copyright (c) 2014, 北京微课创景教育科技有限公司版权所有.
*/

package cn.vko.cache.dao.mybatis.interceptor;

import java.lang.reflect.Method;

import cn.vko.cache.dao.mybatis.MapperCacheAspectSupport;
import cn.vko.cache.util.Invoker;
import cn.vko.cache.util.ThrowableWrapper;

/**
 * <p>
 * @author   宋汝波
 * @Date	 2014-7-7 	 
 */
public class EntityCacheInterceptor extends MapperCacheAspectSupport implements MyBatisInterceptor {

	@Override
	public Object invoke(final MyBatisInvocation handler) throws Throwable {
		Method method = handler.getMethod();
		Invoker aopAllianceInvoker = new Invoker() {
			@Override
			public Object invoke() {
				try {
					return handler.execute();
				} catch (Throwable ex) {
					throw new ThrowableWrapper(ex);
				}
			}
		};
		try {
			final Object result = execute(aopAllianceInvoker, method, handler.getArgs());
			//			if (result == null && method.getReturnType().isPrimitive() && !method.getReturnType().equals(Void.TYPE)) {
			//				throw new BindingException("Mapper method '" + method.getName() + "' (" + method.getDeclaringClass()
			//						+ ") attempted to return null from a method with a primitive return type ("
			//						+ method.getReturnType() + ").");
			//			}
			return result;
		} catch (ThrowableWrapper th) {
			throw th.original;
		}

	}

}
