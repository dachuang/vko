/**
 * SimpleJudgeUtil.java
 * com.vko.support.judge
 * Copyright (c) 2014, 北京微课创景教育科技有限公司版权所有.
*/

package com.vko.core.common.util;

/**
 * 对选择题进行判断
 * <p>
 * @author   宋汝波
 * @Date	 2014-5-14 	 
 */
public class SimpleJudgeUtil {

	/**
	 * 选项ascii码加和验证
	 * <p>
	 *
	 * @param userAnswer
	 * @param dbAnswer
	*/
	public static boolean isRight(String userAnswer, String dbAnswer) {
		if (userAnswer == null || dbAnswer == null) {
			return false;
		}
		String an1 = userAnswer.replace(",", "").replaceAll("\\s", "").toUpperCase();
		String an2 = dbAnswer.replace(",", "").replaceAll("\\s", "").toUpperCase();
		if (addChar(an1) == addChar(an2)) {
			//正确
			return true;
		}
		return false;
	}

	private static int addChar(String answer) {
		int size = answer.length();
		int value = 0;
		for (int i = 0; i < size; i++) {
			value += answer.charAt(i);
		}
		return value;
	}
}
