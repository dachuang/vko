/**
 * RandomUtil.java
 * cn.vko.common.util
 * Copyright (c) 2013, 北京微课创景教育科技有限公司版权所有.
*/

package cn.vko.demo.util;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

/**
 * 随机工具类
 * <p>
 * 生成随机数，随机字符串等和随机有关的工具
 * @author   庄君祥
 * @author   宋星明
 * @author   赵立伟
 * @Date	 2013-1-22 	 
 * @version  5.3.0
 */
public abstract class RandomUtil {

	/**
	 * 产生随机字符串
	 * <p>
	 * 字符串由26个字母以及数字构成
	 * @param lengths 字符串长度
	 * @return 随机的字符串
	 */
	public static String randomString(final int lengths) {
		StringBuilder buffer = new StringBuilder("0123456789abcdefghijklmnopqrstuvwsyz");
		return randomString(buffer, lengths);
	}

	/**
	 * 根据范围生成随机字符串
	 * 
	 * @param array 取值范围
	 * @param lengths 生成字符串长度
	 * @return 随机字符串
	 */
	private static String randomString(final StringBuilder array, final int lengths) {
		if (lengths < 0) {
			return "";
		}
		StringBuilder sb = new StringBuilder();
		Random r = new Random();
		int range = array.length();
		for (int i = 0; i < lengths; i++) {
			sb.append(array.charAt(r.nextInt(range)));
		}
		return sb.toString();
	}

	/**
	 * 产生随机字符串
	 * <p>
	 * 字符串由数字构成
	 * @param lengths 字符串长度
	 * @return 随机的字符串
	 */
	public static String randomNumString(final int lengths) {
		StringBuilder buffer = new StringBuilder("0123456789");
		return randomString(buffer, lengths);
	}

	/**
	 * 从列表中随机获取指定个数的不重复对象
	 *
	 * @param <T> 对象类型
	 * @param source 来源
	 * @param count 个数
	 * @return 随机的对象
	 */
	public static <T> Set<T> randomSet(final List<T> source, final int count) {
		Set<T> result = new LinkedHashSet<T>();
		if (null == source || source.size() == 0) {
			return result;
		}
		if (source.size() < count) {
			result.addAll(source);
			return result;
		}
		List<T> temp = new ArrayList<T>();
		temp.addAll(source);
		Random random = new Random();
		while (temp.size() > 0 && result.size() < count) {
			int pos = random.nextInt(temp.size());
			result.add(temp.get(pos));
			temp.remove(pos);
		}
		return result;
	}

	/**
	 * 随机返回数据源中的一个元素
	 *
	 * @param source 数据源
	 * @return 一个元素
	 */
	public static <T> T random(final List<T> source) {
		T result = null;
		if (null == source || source.size() == 0) {
			return result;
		}
		List<T> temp = new ArrayList<T>();
		temp.addAll(source);
		Random random = new Random();
		int pos = random.nextInt(temp.size());
		return temp.get(pos);
	}

	/**
	 * 产生随机数
	 *
	 * @param n 最大值
	 * @return 随机数
	 */
	public static int nextInt(final int n) {
		if (n <= 0) {
			throw new RuntimeException("n必须是正数");
		}
		return new SecureRandom().nextInt(n);
	}
}
