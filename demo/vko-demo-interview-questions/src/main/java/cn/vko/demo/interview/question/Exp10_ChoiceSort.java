/**
 * Exp10_ChoiceSort.java
 * cn.vko.demo.interview.question
 * Copyright (c) 2014, 北京微课创景教育科技有限公司版权所有.
*/

package cn.vko.demo.interview.question;

/**
 * 选择排序
 * <p>
 * 选择排序和冒泡排序差不多，只是冒泡排序在发现比它小的时候就交换，而选择排序是只有在确定了最小的数据之后，才会发生交换。
 * 
 * 选择排序的基本思想：第i趟简单选择排序是指通过n-i次关键字的比较，从n-i+1个记录中选出关键字最小的记录，并和第i个记录进行交换。先临时记录其位置，只有在一趟
 * 循环完以后确定了最小的数据，才会发生交换
 * 
 * 参考：http://blog.csdn.net/wangkuifeng0118/article/details/7289594
 * @author   ychuang328
 * @Date	 2014-10-9 	 
 */
public class Exp10_ChoiceSort {

	public static int[] choiceSort(int[] arr) {
		if (null == arr || arr.length == 0) {
			return arr;
		}

		//循环遍历
		for (int i = 0; i < arr.length; i++) {
			int min = i;//设当前下标定义为最小值下标
			for (int j = i + 1; j < arr.length; j++) {
				//如果有小于当前最小值的关键字
				if (arr[min] > arr[j]) {
					min = j;//将此关键字的下标赋值给min
				}
			}
			if (min != i) {
				// 若min不等于i，说明找到最小值，交换
				int temp = arr[i];
				arr[i] = arr[min];
				arr[min] = temp;
			}
		}
		return arr;
	}

	public static int[] asc(int[] arr) {
		return choiceSort(arr);
	}

	public static int[] desc(int[] arr) {
		if (null == arr || arr.length == 0) {
			return arr;
		}
		int[] reverseArr = new int[arr.length];
		arr = choiceSort(arr);
		int index = 0;
		for (int i = arr.length - 1; i >= 0; i--, index++) {
			reverseArr[index] = arr[i];
		}
		return reverseArr;
	}

	public static void main(String[] args) {
		int[] arr = { 23, 13, 54, 12, 12, 34, 53, 78 };
		arr = Exp10_ChoiceSort.choiceSort(arr);
		int[] descArr = Exp10_ChoiceSort.desc(arr);
		for (int i = 0; i < arr.length; i++) {
			System.out.print(arr[i] + ",");
		}
		System.out.println();
		for (int i = 0; i < descArr.length; i++) {
			System.out.print(descArr[i] + ",");
		}
	}
}
