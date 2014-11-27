package com.vko.core.common.util.code;

import java.util.Random;

/**
 * 唯一无序数生成
 * 参考:http://blog.csdn.net/visioncat/article/details/6925542
 * @author faker E-mail:darkwing.zhu@gmail.com
 * @version 创建时间：2013-3-16 上午06:12:34
 *
 */

public class RandomCode {
	private Random random;
	private String table;

	public RandomCode() {
		random = new Random();
		table = "0123456789";
	}

	public String randomId(int id) {
		String ret = null, num = String.format("%05d", id);
		int key = random.nextInt(10), seed = random.nextInt(100);
		Caesar caesar = new Caesar(table, seed);
		num = caesar.encode(key, num);
		ret = num + String.format("%01d", key) + String.format("%02d", seed);

		return ret;
	}

	public static void main(String[] args) {
		RandomCode r = new RandomCode();
		for (int i = 0; i < 300; i += 1) {
			System.out.println(r.randomId(i));
		}
	}
}
