/**
 * LockSupport.java
 * cn.vko.cache
 * Copyright (c) 2014, 北京微课创景教育科技有限公司版权所有.
*/

package cn.vko.cache;

import java.util.HashMap;
import java.util.Map;

import cn.vko.cache.util.ConsistentHashImpl;

/**
 * 锁实现
 * <p>
 * @author   宋汝波
 * @Date	 2014-8-18 	 
 */
public abstract class LockSupport {
	protected ConsistentHashImpl<Object> lock;

	public LockSupport() {
		Map<String, Object> lockMap = new HashMap<String, Object>();
		for (int i = 0; i < 1000; i++) {
			Object obj = new Object();
			lockMap.put(obj.toString(), obj);
		}
		lock = new ConsistentHashImpl<Object>(lockMap);
	}

}
