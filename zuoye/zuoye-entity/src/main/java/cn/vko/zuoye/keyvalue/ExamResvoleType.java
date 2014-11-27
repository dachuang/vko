/**
 * ExamResvoleType.java
 * cn.vko.zuoye.keyvalue
 * Copyright (c) 2014, 北京微课创景教育科技有限公司版权所有.
*/

package cn.vko.zuoye.keyvalue;

import com.vko.core.common.keyvalue.AbstractKeyValue;
import com.vko.core.common.keyvalue.KeyValue;

/**
 * 作业试题解析类型
 * @author   杨闯
 * @Date	 2014-7-15 	 
 */
public class ExamResvoleType extends AbstractKeyValue {

	public static final int PREFIX = 103000;
	private static Class<ExamResvoleType> clsName = ExamResvoleType.class;
	public static final KeyValue TEXT = build(clsName, 1, "文本");
	public static final KeyValue VIDEO = build(clsName, 2, "视频");

	public ExamResvoleType(int suffix, String value) {

		super(suffix, value);

	}

	@Override
	public int getPrefix() {

		return PREFIX;

	}

}
