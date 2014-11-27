/**
 * HomeworkType.java
 * cn.vko.zuoye.keyvalue
 * Copyright (c) 2014, 北京微课创景教育科技有限公司版权所有.
*/

package cn.vko.zuoye.keyvalue;

import com.vko.core.common.keyvalue.AbstractKeyValue;
import com.vko.core.common.keyvalue.KeyValue;

/**
 * 教师布置作业的状态,三种
 * <p>
 * @author   宋汝波
 * @Date	 2014-7-11 	 
 */
public class HomeworkStatus extends AbstractKeyValue {

	public static final int PREFIX = 101000;
	private static Class<HomeworkStatus> clsName = HomeworkStatus.class;
	public static final KeyValue SENDED = build(clsName, 1, "已发");
	public static final KeyValue WILL_CORRECT = build(clsName, 2, "待判");
	public static final KeyValue CORRECTED = build(clsName, 3, "已判");

	public HomeworkStatus(int suffix, String value) {

		super(suffix, value);

	}

	@Override
	public int getPrefix() {

		return PREFIX;

	}

}
