/**
 * FstOperation.java
 * com.vko.core.web.wrap.cookie
 * Copyright (c) 2014, 北京微课创景教育科技有限公司版权所有.
*/

package com.vko.core.web.wrap.cookie;

import com.vko.core.common.util.SerializeUtil;

/**
 * <p>
 * @author   宋汝波
 * @Date	 2014-7-7 	 
 */
public class FstOperation implements ObjectSerialize {
	@Override
	public Object deSerialize(Object obj) {
		return SerializeUtil.fastDeserialize((byte[]) obj);
	}

	@Override
	public Object serialize(Object obj) {
		return SerializeUtil.fastSerialize(obj);
	}

}
