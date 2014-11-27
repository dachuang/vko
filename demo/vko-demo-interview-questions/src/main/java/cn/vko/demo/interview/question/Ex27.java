/**
 * Ex27.java
 * cn.vko.demo.interview.question
 * Copyright (c) 2014, 北京微课创景教育科技有限公司版权所有.
*/

package cn.vko.demo.interview.question;

/**
 * Java求100之内的素数
 * <p>
 * 题目：求100之内的素数
 * 素数：大于1。。。
 * @author   ychuang328
 * @Date	 2014-9-25 	 
 */
public class Ex27 {
	public static void main(String args[]) {
		int sum, i;
		for (sum = 2; sum <= 100; sum++) {
			for (i = 2; i <= sum / 2; i++) {
				if (sum % i == 0)
					break;
			}
			if (i > sum / 2)
				System.out.println(sum + "是素数");
		}
	}
}
