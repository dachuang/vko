/**
 * Ex12.java
 * cn.vko.demo.interview.question
 * Copyright (c) 2014, 北京微课创景教育科技有限公司版权所有.
*/

package cn.vko.demo.interview.question;

/**
 * Java编程计算兔子生兔子的问题)
 * <p>
 * 题目：古典问题：有一对兔子，从出生后第3个月起每个月都生一对兔子，小兔子长到第四个月后每个月又生一对兔子，假如兔子都不死，问每个月的兔子总数为多少？  

 *	程序分析：   兔子的规律为数列1,1,2,3,5,8,13,21....  （f(n-1)+f(n-2)=下一个出现的数）

 * @author   ychuang328
 * @Date	 2014-9-26 	 
 */
public class Ex12 {
	public static void main(String args[]) {
		int i = 0;
		for (i = 1; i <= 20; i++)
			System.out.println(f(i));
	}

	public static int f(int x) {
		if (x == 1 || x == 2)
			return 1;
		else
			return f(x - 1) + f(x - 2);
	}
}
