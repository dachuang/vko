/**
 * TestMoneyFormat.java
 * cn.vko.demo.util
 * Copyright (c) 2014, 北京微课创景教育科技有限公司版权所有.
*/

package cn.vko.demo.util;

import org.junit.Assert;
import org.junit.Test;

/**
 * 数字金额转中文金额工具类 单元测试
 * <p>
 * 
 * @author   ychuang328
 * @Date	 2014-9-25 	 
 */
public class TestMoneyFormat {

	/**
	 * 数字金额转中文金额方法测试
	 * <p>
	 * 
	 */
	@Test
	public void testFormatString() {
		Assert.assertEquals("壹拾元壹分", MoneyFormat.getInstance().format("10.01"));
		Assert.assertEquals("零元壹分", MoneyFormat.getInstance().format("0.01"));
		Assert.assertEquals("零元壹角整", MoneyFormat.getInstance().format("0.1"));
		Assert.assertEquals("壹拾元整", MoneyFormat.getInstance().format("10"));
		Assert.assertEquals("壹万零壹佰零壹亿零壹佰零壹万壹仟零壹元壹分", MoneyFormat.getInstance().format("1010101011001.01"));
	}
}
