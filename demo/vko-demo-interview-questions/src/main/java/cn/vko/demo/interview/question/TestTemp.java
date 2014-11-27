/**
 * TestTemp.java
 * cn.vko.demo.interview.question
 * Copyright (c) 2014, 北京微课创景教育科技有限公司版权所有.
*/

package cn.vko.demo.interview.question;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;

/**
 * 测试类（临时想要验证的想法）
 * <p>
 * @author   ychuang328
 * @Date	 2014-9-29 	 
 */
public class TestTemp {
	private int number;

	public int getNumber() {
		return number;
	}

	public void setNumber(int num) {
		this.number = num;
	}

	public static void main(String[] args) {
		TestTemp tt = new TestTemp();
		Object obj = tt.testReflect();
	}

	//反射
	public <O> O testReflect() {
		try {
			Class c = Class.forName("cn.vko.demo.interview.question.Dg");
			Constructor[] cc = c.getConstructors();
			Object obj = c.newInstance();
			System.out.println(obj.toString());
			List<Integer> ss = new ArrayList<Integer>();

		} catch (ClassNotFoundException e) {

			e.printStackTrace();

		} catch (InstantiationException e) {

			// TODO Auto-generated catch block
			e.printStackTrace();

		} catch (IllegalAccessException e) {

			// TODO Auto-generated catch block
			e.printStackTrace();

		}
		return null;
	}
}
