/**
 * HomeworkType.java
 * cn.vko.zuoye.keyvalue
 * Copyright (c) 2014, 北京微课创景教育科技有限公司版权所有.
*/

package cn.vko.zuoye.keyvalue;

import com.vko.core.common.keyvalue.KeyValue;

/**
 * 试题类型,兼容题库的试题类型
 * <p>
 * @author   宋汝波
 * @Date	 2014-7-11 	 
 */
public class ImageExamType implements KeyValue {

	public static final KeyValue SINGLE = new ImageExamType(351, "单项选择题");
	public static final KeyValue MULTUPLE = new ImageExamType(353, "多项选择题");
	public static final KeyValue JUDGEMENT = new ImageExamType(368, "判断题");
	public static final KeyValue OTHER = new ImageExamType(10000, "其他");

	private int key;
	private String value;

	private ImageExamType(int key, String value) {
		this.key = key;
		this.value = value;
	}

	@Override
	public int getKey() {

		return key;

	}

	@Override
	public String getValue() {

		return value;

	}

	/**
	 * 判断是主观还是客观
	 * <p>
	 *
	 * @param type
	*/
	public static boolean isObjective(int type) {
		if (type == 351 || type == 353 || type == 368) {
			return true;
		}
		return false;
	}

	@Override
	public KeyValue getParent() {

		return null;

	}

}
