/**
 * KeyValueImpl.java
 * cn.vko.fz.manage.common.type
 * Copyright (c) 2014, 北京微课创景教育科技有限公司版权所有.
*/

package com.vko.core.common.keyvalue;

/**
 * TODO(这里用一句话描述这个类的作用)
 * <p>
 * TODO(这里描述这个类补充说明 – 可选)
 * @author   宋汝波
 * @Date	 2014-6-6 	 
 */
public class TestKeyValueImpl extends AbstractKeyValue {

	static Class<TestKeyValueImpl> c = TestKeyValueImpl.class;
	public static final KeyValue FIRST = build(c, 1, "测试1");
	public static final KeyValue SECOND = build(c, 2, "测试2", FIRST);
	public static final KeyValue THIRD = build(c, 3, "测试3");
	public static final KeyValue FORTH = build(c, 4, "测试4", THIRD);

	public static final int TEST_PREFIX = 101000;

	public TestKeyValueImpl(int suffix, String value) {
		super(suffix, value);
	}

	public TestKeyValueImpl(int suffix, String value, KeyValue parent) {
		super(suffix, value, parent);
	}

	@Override
	public int getPrefix() {
		return TEST_PREFIX;

	}

	public static void main(String[] args) {
		System.out.println(FIRST);
		System.out.println(AbstractKeyValue.getByParent(THIRD));
		System.out.println(AbstractKeyValue.getByParent(SECOND));
		System.out.println(AbstractKeyValue.getByParent(FIRST));
		System.out.println(AbstractKeyValue.getByPrefix(TEST_PREFIX));
		System.out.println(AbstractKeyValue.getValue(SECOND.getKey()));
	}
}
