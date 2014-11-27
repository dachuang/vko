/**
 * HomeworkType.java
 * cn.vko.zuoye.keyvalue
 * Copyright (c) 2014, 北京微课创景教育科技有限公司版权所有.
*/

package cn.vko.zuoye.keyvalue;

import com.vko.core.common.keyvalue.AbstractKeyValue;
import com.vko.core.common.keyvalue.KeyValue;

/**
 * 学生作业状态,两种
 * <p>
 * @author   宋汝波
 * @Date	 2014-7-11 	 
 */
public class StudentHomeworkStatus extends AbstractKeyValue {

	public static final int PREFIX = 102000;
	private static Class<StudentHomeworkStatus> clsName = StudentHomeworkStatus.class;
	public static final KeyValue NOT_HAND_IN = build(clsName, 1, "未交");
	public static final KeyValue HAND_IN = build(clsName, 2, "已交");

	public StudentHomeworkStatus(int suffix, String value) {

		super(suffix, value);

	}

	@Override
	public int getPrefix() {

		return PREFIX;

	}

}
