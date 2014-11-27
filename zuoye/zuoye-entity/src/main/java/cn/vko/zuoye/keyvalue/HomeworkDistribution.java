/**
 * HomeworkDistribution.java
 * cn.vko.zuoye.keyvalue
 * Copyright (c) 2014, 北京微课创景教育科技有限公司版权所有.
*/

package cn.vko.zuoye.keyvalue;

import com.vko.core.common.keyvalue.AbstractKeyValue;
import com.vko.core.common.keyvalue.KeyValue;

/**
 * 作业分发状态控制(1待发,2待收,3已收,4已判)前缀104000
 * <p>
 * @author   宋汝波
 * @Date	 2014-7-16 	 
 */
public class HomeworkDistribution extends AbstractKeyValue {

	public static final int PREFIX = 104000;
	private static Class<HomeworkDistribution> clsName = HomeworkDistribution.class;
	public static final KeyValue DAI_FA = build(clsName, 1, "待发");
	public static final KeyValue DAI_SHOU = build(clsName, 2, "待收");
	public static final KeyValue YI_SHOU = build(clsName, 3, "已收");
	public static final KeyValue YI_PAN = build(clsName, 4, "已判");

	public HomeworkDistribution(int suffix, String value) {

		super(suffix, value);

	}

	@Override
	public int getPrefix() {

		return PREFIX;

	}
}
