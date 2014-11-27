/**
 * Ex37.java
 * cn.vko.demo.interview.question
 * Copyright (c) 2014, 北京微课创景教育科技有限公司版权所有.
*/

package cn.vko.demo.interview.question;

/**
 * Java实例，n个人围成一圈，顺序排号问题
 * <p>
 * 题目：有n个人围成一圈，顺序排号(1,2,3...n)。从第一个人开始报数（从1到3报数），凡报到3的人退出圈子，问最后留下的是原来第几号的那位。
 * 
 * 思路：情况复杂时，需要从题目中提取哪些是变量，并按需要加临时记录变量
 * 
 * @author   ychuang328
 * @Date	 2014-9-25 	 
 */

public class Ex37 {

	/**
	 *  方法一：
	 *  循环遍历
	 * <p>
	 * 
	 */
	public static void doWhile(int n) {
		//		Scanner s = new Scanner(System.in);
		//		int n = s.nextInt();
		boolean[] arr = new boolean[n];
		for (int i = 0; i < arr.length; i++) {
			arr[i] = true;//下标为TRUE时说明还在圈里
		}
		int leftCount = n;
		int countNum = 0;
		int index = 0;
		while (leftCount > 1) {
			if (arr[index] == true) {//当在圈里时
				countNum++; //报数递加
				if (countNum == 3) {//报道3时
					countNum = 0;//从零开始继续报数
					arr[index] = false;//此人退出圈子
					leftCount--;//剩余人数减一
				}
			}
			index++;//每报一次数，下标加一
			if (index == n) {//是循环数数，当下标大于n时，说明已经数了一圈，
				index = 0;//将下标设为零重新开始。
			}
		}
		for (int i = 0; i < n; i++) {
			if (arr[i] == true) {
				//序号从0开始的，正常是从1开始，结果需要加1
				System.out.println(i + 1);
			}
		}
	}

	private static int[] arrN;

	/**
	 * 方法二：递归
	 * <p>
	 * 递归：需要条件（继续的条件+终止条件）
	 *
	 * @param num 总人数的数据（开始为null）
	 * @param n 当前玩的总人数
	 * @param i 记录报数（init为1）
	 * @param l 人的序号（init为0）
	 */
	public static void diGui(int[] num, int n, int i, int l) {
		//第一次递归时给所有人一个编号（0-n）
		if (arrN == null) {
			arrN = new int[n];
			for (int j = 0; j < n; j++) {
				arrN[j] = 1;
			}
			num = arrN;
		} else {
			num = arrN;
		}
		//剩最后一个人时，结果递归
		if (n == 1) {
			for (int j = 0; j < num.length; j++) {
				if (num[j] == 1) {
					//序号从0开始的，正常是从1开始，结果需要加1
					System.out.println(j + 1);
				}
			}
			return;
		}

		//当前所有序号都遍历一次时，重置为0，重新遍历
		if (l == arrN.length) {
			l = 0;
		}
		l += 1;
		//为1时，表时人还在游戏中
		if (arrN[l - 1] == 1) {
			if (i == 3) {
				arrN[l - 1] = 0;
				diGui(num, n - 1, 1, l);
			} else {
				diGui(num, n, (i + 1) > 3 ? 1 : (i + 1), l);
			}
		} else {
			//为0时，表时人已退出游戏，继续下一个玩此次游戏
			diGui(num, n, i, l);
		}

	}

	public static void main(String[] args) {
		doWhile(3);
		diGui(null, 3, 1, 0);
	}
}
