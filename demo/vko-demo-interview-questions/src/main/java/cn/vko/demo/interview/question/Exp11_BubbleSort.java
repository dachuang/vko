/**
 * Exp11_BubbleSort.java
 * cn.vko.demo.interview.question
 * Copyright (c) 2014, 北京微课创景教育科技有限公司版权所有.
*/

package cn.vko.demo.interview.question;

/**
 * 冒泡排序
 * <p>
 * 
 * 冒泡排序的基本思想：冒泡排序的过程很简单，就是将第一个记录的关键字和第二个记录的关键字进行比较，如果后面的比前面的小则交换，然后比较第二个和第三个，依次类推。
 * 比完一趟，最大的那个已经放到了最后的位置，这样就可以对前面N-1个数再循环比较。
 * 
 * 这样就排好序了，不过这种效率不是最好的，时间复杂度是O（n²）。
 * 
 * 参考：http://blog.csdn.net/wangkuifeng0118/article/details/7286812
 * @author   ychuang328
 * @Date	 2014-10-9 	 
 */
public class Exp11_BubbleSort {

	public static int[] bubbleSort(int[] arr) {
		if (null == arr || arr.length == 0) {
			return arr;
		}

		//循环遍历
		for (int i = 0; i < arr.length; i++) {
			for (int j = i; j < arr.length - i - 1; j++) {
				//如果后一个数小于前一个数交换
				if (arr[j] > arr[j + 1]) {
					int temp = arr[j];
					arr[j] = arr[j + 1];
					arr[j + 1] = temp;
				}
			}
		}
		return arr;
	}

	public static int[] asc(int[] arr) {
		return bubbleSort(arr);
	}

	public static int[] desc(int[] arr) {
		if (null == arr || arr.length == 0) {
			return arr;
		}
		int[] reverseArr = new int[arr.length];
		arr = bubbleSort(arr);
		int index = 0;
		for (int i = arr.length - 1; i >= 0; i--, index++) {
			reverseArr[index] = arr[i];
		}
		return reverseArr;
	}

	public static void main(String[] args) {
		int[] arr = { 23, 13, 54, 12, 12, 34, 53, 78 };
		arr = Exp11_BubbleSort.bubbleSort(arr);
		int[] descArr = Exp11_BubbleSort.desc(arr);
		for (int i = 0; i < arr.length; i++) {
			System.out.print(arr[i] + ",");
		}
		System.out.println();
		for (int i = 0; i < descArr.length; i++) {
			System.out.print(descArr[i] + ",");
		}
	}
}
