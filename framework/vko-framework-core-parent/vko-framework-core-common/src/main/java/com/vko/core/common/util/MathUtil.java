/**
 * MathUtil.java
 * cn.vko.core.common.util
 * Copyright (c) 2013, 北京微课创景教育科技有限公司版权所有.
*/
package com.vko.core.common.util;

import java.math.BigDecimal;

import com.vko.core.common.exception.VkoServiceException;

/**
 * 有关数学操作的工具类
 * 
 * @author   马磊
 * @Date	 2013-12-18 	 
 */
public class MathUtil {

	/**   
	 * 加法运算
	 * @param	p1	加数   
	 * @param   p2	被加数   
	 * @return  两个参数的和   
	 */
	public static double add(final String p1, final String p2) {
		BigDecimal b1 = new BigDecimal(p1);
		BigDecimal b2 = new BigDecimal(p2);
		return b1.add(b2).doubleValue();
	}

	/**
	 * 减法运算
	 * @param	p1	减数
	 * @param	p2	被减数
	 * @return	返回两数之差
	 */
	public static double sub(final String p1, final String p2) {
		BigDecimal b1 = new BigDecimal(p1);
		BigDecimal b2 = new BigDecimal(p2);
		return b1.subtract(b2).doubleValue();
	}

	/**
	 * 乘法运算
	 * @param	p1	乘数
	 * @param	p2	被乘数
	 * @return	返回两数之积
	 */
	public static double mul(final String p1, final String p2) {
		BigDecimal b1 = new BigDecimal(p1);
		BigDecimal b2 = new BigDecimal(p2);
		return b1.multiply(b2).doubleValue();
	}

	/**   
	* 提供（相对）精确的除法运算。当发生除不尽的情况时，由scale参数指定精度，以后的数字四舍五入。   
	* @param   p1   被除数   
	* @param   p2   除数   
	* @param   scale   表示表示需要精确到小数点以后几位。   
	* @return  返回两数之商   
	*/

	public static double div(final double p1, final double p2, final int scale) {
		if (scale < 0) {
			throw new VkoServiceException("精度格式不正确!");
		}
		if (p2 == 0d) {
			return 0;
		}
		BigDecimal b1 = new BigDecimal(p1);
		BigDecimal b2 = new BigDecimal(p2);
		return b1.divide(b2, scale, BigDecimal.ROUND_HALF_UP).doubleValue();
	}

}
