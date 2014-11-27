/**
 * KeyValue.java
 * com.vko.core.common.keyvalue
 * Copyright (c) 2014, 北京微课创景教育科技有限公司版权所有.
*/

package com.vko.core.common.keyvalue;


/**
 * int,String类型的接口
 * <p>
 * @author   宋汝波
 * @Date	 2014-6-6 	 
 */
public interface KeyValue {

	/**
	 * 
	 * 整形数值
	 * <p>
	 */
	public int getKey();

	/**
	 * String数值
	 * <p>
	 */
	public String getValue();

	/**
	 *父类型 
	 * <p>
	 *
	 */
	public KeyValue getParent();
}