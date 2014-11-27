/**
 * AbstractIntString.java
 * cn.vko.fz.manage.common.type
 * Copyright (c) 2014, 北京微课创景教育科技有限公司版权所有.
*/

package com.vko.core.common.keyvalue;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;

/**
 * 所有类型的父类
 * <p>
 * @author   宋汝波
 * @Date	 2014-6-6 	 
 */
public abstract class AbstractKeyValue implements KeyValue {
	//真实值
	protected int key;
	//文本值
	protected String value;
	//前缀值
	protected int prefix;
	//后缀值
	protected int suffix;
	//父类型
	protected KeyValue parent;
	//存储所有类型
	private static Map<Integer, KeyValue> elementsMap = new LinkedHashMap<Integer, KeyValue>();
	private static Map<Integer, Map<Integer, KeyValue>> prefixMap = new LinkedHashMap<Integer, Map<Integer, KeyValue>>();
	private static ResourcePatternResolver resourcePatternResolver = new PathMatchingResourcePatternResolver();

	private static void isPrefixValid(int prefix) {
		if (prefix < 100000 || prefix > 999999) {
			throw new RuntimeException("不支持的前缀" + prefix);
		}
		if (prefix % 1000 != 0) {
			throw new RuntimeException("不支持的前缀" + prefix);
		}
	}

	private static void isSuffixValid(int suffix) {
		if (suffix <= 0 || suffix > 999) {
			throw new RuntimeException("不支持的后缀" + suffix);
		}
	}

	private static void isKeyValid(int key) {
		if (key < 100000 || key > 999999) {
			throw new RuntimeException("不支持的key" + key);
		}
	}

	public AbstractKeyValue(int suffix, String value, KeyValue parent) {
		prefix = getPrefix();
		isPrefixValid(prefix);
		this.suffix = suffix;
		isSuffixValid(suffix);
		this.value = value;
		this.parent = parent;
		//计算key
		this.key = prefix + suffix;
		isKeyValid(this.key);
		synchronized (AbstractKeyValue.class) {
			if (elementsMap.containsKey(key)) {
				throw new IllegalArgumentException("已经存在:" + key + "");
			}
			elementsMap.put(this.key, this);
			Map<Integer, KeyValue> parents = prefixMap.get(prefix);
			if (parents == null) {
				prefixMap.put(prefix, elementsMap);
			}
		}

	}

	public AbstractKeyValue(int suffix, String value) {
		this(suffix, value, null);
	}

	@Override
	public int getKey() {

		return key;

	}

	/**
	 * 得到类型的前三位,由六位构成100001,返回100000
	 * <p>
	 *必须重写
	*/
	public abstract int getPrefix();

	public static Collection<KeyValue> getByParent(KeyValue parent) {
		if (parent == null) {
			throw new RuntimeException("parent不能为null");
		}
		int key = parent.getKey();
		isKeyValid(key);
		//查找前缀,变成101000
		int prefix = (key / 1000) * 1000;
		//获取该类型所有
		Map<Integer, KeyValue> elements = prefixMap.get(prefix);
		if (elements != null) {
			List<KeyValue> result = new ArrayList<KeyValue>();
			Collection<KeyValue> all = elements.values();
			for (KeyValue keyValue : all) {
				KeyValue subParent = keyValue.getParent();
				//判断父亲的key相等
				if (subParent != null && parent.getKey() == subParent.getKey()) {
					result.add(keyValue);
				}
			}
			return result;
		}
		return null;
	}

	/**
	 * 根据前缀找所有
	 * <p>
	 *
	 * @param prefix
	*/
	public static Collection<KeyValue> getByPrefix(int prefix) {
		isPrefixValid(prefix);
		Map<Integer, KeyValue> elements = prefixMap.get(prefix);
		if (elements != null) {
			return elements.values();
		}
		return null;
	}

	/**
	 * 根据key找到对应的文本
	 * <p>
	 *
	 * @param key
	*/
	public static String getValue(int key) {
		isKeyValid(key);
		KeyValue keyValue = elementsMap.get(key);
		if (keyValue != null) {
			return keyValue.getValue();
		}
		return null;
	}

	@Override
	public String getValue() {

		return value;

	}

	@Override
	public KeyValue getParent() {

		return parent;

	}

	public static KeyValue build(Class<? extends KeyValue> c, int suffix, String value, KeyValue parent) {
		try {
			return c.getConstructor(int.class, String.class, KeyValue.class).newInstance(suffix, value, parent);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static KeyValue build(Class<? extends KeyValue> c, int suffix, String value) {
		try {
			return c.getConstructor(int.class, String.class).newInstance(suffix, value);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public String toString() {

		return "key:" + key + "_value:" + value;

	}

	/**
	 * classpath*:
	 * 预先加载所有状态字
	 * <p>
	 * "**\/*.class"
	 * @param path
	 * @param packageName
	*/
	public static void init(String path, String packageName) throws Exception {
		Resource[] resources = resourcePatternResolver.getResources(path);
		if (resources == null) {
			return;
		}
		for (int i = 0; i < resources.length; i++) {
			String fileName = resources[i].getURL().getFile();
			fileName = fileName.replace("/", ".");
			String className = fileName.substring(fileName.indexOf(packageName));
			Class.forName(className.replace(".class", ""));
		}

	}
}
