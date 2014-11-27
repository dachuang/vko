/**
 * Ex34.java
 * cn.vko.demo.interview.question
 * Copyright (c) 2014, 北京微课创景教育科技有限公司版权所有.
*/

package cn.vko.demo.interview.question;

import java.util.Arrays;

/**
 * Java实例，输入3个数，按大小顺序输出
 * <p>
 * 题目：输入3个数a,b,c，按大小顺序输出。  
 * @author   ychuang328
 * @Date	 2014-9-25 	 
 */
public class Ex34 {

	/**
	 * 冒泡排序
	 * <p>
	 */
	public static void ascSort() {
		int[] arrays = { 800, 56, 500 };
		for (int i = arrays.length; --i >= 0;) {
			for (int j = 0; j < i; j++) {
				if (arrays[j] > arrays[j + 1]) {
					int temp = arrays[j];
					arrays[j] = arrays[j + 1];
					arrays[j + 1] = temp;
				}
			}
		}
		for (int n = 0; n < arrays.length; n++) {
			System.out.print(arrays[n] + ",");
		}
		System.out.println();
	}

	/**
	 * java数据自带的排序工具（以外国的排序为主1-n, a-z）
	 */
	public static void ascSort2() {
		int[] arrays = { 800, 56, 500 };
		Arrays.sort(arrays);
		for (int n = 0; n < arrays.length; n++) {
			System.out.print(arrays[n] + ",");
		}
		System.out.println();
	}

	public static void main(String[] args) {
		Ex34.ascSort();
		Ex34.ascSort2();
	}

}
