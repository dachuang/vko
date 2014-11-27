/**
 * Exp12_QuickSort.java
 * cn.vko.demo.interview.question
 * Copyright (c) 2014, 北京微课创景教育科技有限公司版权所有.
*/

package cn.vko.demo.interview.question;

/**
 * 快速排序
 * <p>
 * 
 * 快速排序的基本思想：通过一趟排序将待排序记录分割成独立的两部分，其中一部分记录的关键字均比另一部分关键字小，则分别对这两部分继续进行排序，直到整个序列有序。
 * 把整个序列看做一个数组，把第零个位置看做中轴，和最后一个比，如果比它小交换，比它大不做任何处理；交换了以后再和小的那端比，比它小不交换，比他大交换。
 * 这样循环往复，一趟排序完成，左边就是比中轴小的，右边就是比中轴大的，然后再用分治法，分别对这两个独立的数组进行排序。
 * 
 * 这样就排序好了，快速排序是对冒泡排序的一种改进，平均时间复杂度是O(nlogn)。
 * @author   ychuang328
 * @Date	 2014-10-9 	 
 */
public class Exp12_QuickSort {

	private static int getMiddle(int[] list, int low, int high) {
		int tmp = list[low]; //数组的第一个作为中轴  
		while (low < high) {
			while (low < high && list[high] >= tmp) {
				high--;
			}
			list[low] = list[high]; //比中轴小的记录移到低端  
			while (low < high && list[low] <= tmp) {
				low++;
			}
			list[high] = list[low]; //比中轴大的记录移到高端  
		}
		list[low] = tmp; //中轴记录到尾  
		return low; //返回中轴的位置  
	}

	private static int[] _quickSort(int[] list, int low, int high) {
		if (low < high) {
			int middle = getMiddle(list, low, high); //将list数组进行一分为二  
			_quickSort(list, low, middle - 1); //对低字表进行递归排序  
			_quickSort(list, middle + 1, high); //对高字表进行递归排序  
			return list;
		}
		return list;
	}

	private static int[] quickSort(int[] str) {
		if (str.length > 0) { //查看数组是否为空  
			return _quickSort(str, 0, str.length - 1);
		}
		return str;
	}

	public static int[] asc(int[] arr) {
		return quickSort(arr);
	}

	public static int[] desc(int[] arr) {
		if (null == arr || arr.length == 0) {
			return arr;
		}
		int[] reverseArr = new int[arr.length];
		arr = quickSort(arr);
		int index = 0;
		for (int i = arr.length - 1; i >= 0; i--, index++) {
			reverseArr[index] = arr[i];
		}
		return reverseArr;
	}

	public static void main(String[] args) {
		int[] arr = { 23, 13, 54, 12, 12, 34, 53, 78 };
		//		Arrays.sort(arr);//默认使用的就是快速排序
		//		for (int i = 0; i < arr.length; i++) {
		//			System.out.print(arr[i] + ",");
		//		}
		arr = Exp12_QuickSort.quickSort(arr);
		int[] descArr = Exp12_QuickSort.desc(arr);
		for (int i = 0; i < arr.length; i++) {
			System.out.print(arr[i] + ",");
		}
		System.out.println();
		for (int i = 0; i < descArr.length; i++) {
			System.out.print(descArr[i] + ",");
		}
	}
}
