/**
 * Dg.java
 * cn.vko.demo.interview.question
 * Copyright (c) 2014, 北京微课创景教育科技有限公司版权所有.
*/

package cn.vko.demo.interview.question;

/**
 * Java实例，海滩上有一堆桃子，五只猴子来分()
 * <p>
 * 题目：海滩上有一堆桃子，五只猴子来分。第一只猴子把这堆桃子凭据分为五份，多了一个，这只猴子把多的一个扔入海中，拿走了一份。第二只猴子把剩下的桃子又平均分成五份，又多了一个，它同样把多的一个扔入海中，拿走了一份，第三、第四、第五只猴子都是这样做的，问海滩上原来最少有多少个桃子？ 
 * 
 * 思路：
 * 方法：递归（参数桃子总数，数量由小到大匹配）
 * 终止条件：1.设定一个桃子的总数，结果不能大于总数，（递归最大数次时结束，返回0）
 * 		 2.不满足猴子5次分完，与每次分多一个条件时，结果加一，次数归0，重新开始递归
 *       3.当正好满足猴子分5次，与每次分时多一个时，此时的桃子数是就是总桃子数
 * @author   ychuang328
 * @Date	 2014-9-25 	 
 */
public class Dg {

	private static int ts = 0;//桃子总数
	private int index = 1;//记录分的次数
	private static int hs = 5;//猴子数...
	private int tsscope = 5000;//桃子数的取值范围.太大容易溢出.

	//分桃
	public int fT(int t) {
		if (t == tsscope) {
			//当桃子数到了最大的取值范围时取消递归
			System.out.println("结束");
			return 0;
		} else {
			if ((t - 1) % hs == 0 && index <= hs) {
				if (index == hs) {
					System.out.println("桃子数 = " + ts + " 时满足分桃条件");
				}
				index += 1;
				return fT((t - 1) / 5 * 4);// 返回猴子拿走一份后的剩下的总数
			} else {
				//没满足条件
				index = 1;//分的次数重置为1
				return fT(ts += 1);//桃子数加+1
			}
		}
	}

	public static void main(String[] args) {
		new Dg().fT(0);
	}
}
