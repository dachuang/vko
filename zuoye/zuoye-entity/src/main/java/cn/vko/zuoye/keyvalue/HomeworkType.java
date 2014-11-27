/**
 * HomeworkType.java
 * cn.vko.zuoye.keyvalue
 * Copyright (c) 2014, 北京微课创景教育科技有限公司版权所有.
*/

package cn.vko.zuoye.keyvalue;

import com.vko.core.common.keyvalue.AbstractKeyValue;
import com.vko.core.common.keyvalue.KeyValue;

/**
 * 教师布置作业的类型,两种
 * <p>
 * @author   宋汝波
 * @Date	 2014-7-11 	 
 */
public class HomeworkType extends AbstractKeyValue {

	public static final int PREFIX = 100000;
	private static Class<HomeworkType> clsName = HomeworkType.class;
	public static final KeyValue TIKU_CHANNEL = build(clsName, 1, "根据题库发布");
	public static final KeyValue SELF_UPLOAD = build(clsName, 2, "老师上传发布");

	public HomeworkType(int suffix, String value) {

		super(suffix, value);

	}

	@Override
	public int getPrefix() {

		return PREFIX;

	}

}
