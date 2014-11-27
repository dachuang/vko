/**
 * Ex35.java
 * cn.vko.demo.interview.question
 * Copyright (c) 2014, 北京微课创景教育科技有限公司版权所有.
*/

package cn.vko.demo.interview.question;

/**
 * Java数组元素交换问题
 * <p>
 * 题目：输入数组，最大的与第一个元素交换，最小的与最后一个元素交换，输出数组。  
 * 
 * （当最大与最小在开始和结尾时要特殊处理，不变化）
 * @author   ychuang328
 * @Date	 2014-9-25 	 
 */
import java.util.Scanner;

public class Ex35 {
	public static void main(String[] args) {
		int i, min, max, n, temp1, temp2;
		int a[];
		System.out.println("输入数组的长度:");
		Scanner keyboard = new Scanner(System.in);
		n = keyboard.nextInt();
		a = new int[n];
		for (i = 0; i < n; i++) {
			System.out.print("输入第" + (i + 1) + "个数据");
			a[i] = keyboard.nextInt();
		}
		//以上是输入整个数组
		max = 0;
		min = 0;
		//设置两个标志,开始都指向第一个数
		for (i = 1; i < n; i++) {
			if (a[i] > a[max])
				max = i; //遍历数组,如果大于a[max]，就把他的数组下标赋给max
			if (a[i] < a[min])
				min = i; //同上，如果小于a[min],就把他的数组下标赋给min
		}
		//以上for循环找到最大值和最小值，max是最大值的下标，min是最小值的下标
		temp1 = a[0];
		temp2 = a[min]; //这两个temp只是为了在交换时使用

		a[0] = a[max];
		a[max] = temp1; //首先交换a[0]和最大值a[max]

		if (min != 0) { //如果最小值不是a[0]，执行下面
			a[min] = a[n - 1];
			a[n - 1] = temp2; //交换a[min]和a[n-1]
		} else { //如果最小值是a[0],执行下面
			a[max] = a[n - 1];
			a[n - 1] = temp1;
		}

		for (i = 0; i < n; i++) { //输出数组
			System.out.print(a[i] + " ");
		}
	}
}
