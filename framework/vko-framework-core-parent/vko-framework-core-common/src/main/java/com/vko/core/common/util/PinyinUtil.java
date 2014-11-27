package com.vko.core.common.util;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;

public class PinyinUtil {

	// 拼音格式
	private static HanyuPinyinOutputFormat format = null;

	static {
		format = new HanyuPinyinOutputFormat();
		format.setCaseType(HanyuPinyinCaseType.LOWERCASE);// 小写
		format.setToneType(HanyuPinyinToneType.WITHOUT_TONE);// 不带音标
		format.setVCharType(HanyuPinyinVCharType.WITH_U_UNICODE);// v-u
	}

	public static String getPinyin(String str) throws Exception {
		StringBuffer sb = new StringBuffer();
		String py = null;
		int len = str.length();
		for (int i = 0; i < len; i++) {
			py = getPinyin(str.charAt(i));
			if (py == null) {
				sb.append(str.charAt(i));
			} else {
				sb.append(py);
			}
		}
		return sb.toString();
	}

	private static String getPinyin(char hz) throws Exception {
		String[] py = PinyinHelper.toHanyuPinyinStringArray(hz, format);
		return (py == null) ? null : py[0];
	}
	
	public static void main(String[] args) throws Exception{
		String str = "微课网在线教育国内首家中学生ESNS视频学习社区加入微课网学的不只是好课实现高";
		String py = getPinyin(str);
		System.out.println(py);
	}
}
