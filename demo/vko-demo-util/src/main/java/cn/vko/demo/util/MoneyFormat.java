/**
 * MoneyFormat.java
 * cn.vko.demo.util
 * Copyright (c) 2014, 北京微课创景教育科技有限公司版权所有.
*/

package cn.vko.demo.util;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.HashMap;
import java.util.Map;

/**
 * 数字金额转化为中文金额工具类
 * <p>
 * 支持如下形式：
 * <pre>
 *  壹拾亿壹仟零壹万零贰拾壹元壹分 | 1010010021.01
 *	零元壹角整 | 0.10
 *	壹万零贰佰贰拾元壹角整 | 10,220.1
 *	贰拾元壹角整 | 20.1
 *	贰拾元整 | 20
 * </pre>
 * @author   ychuang328
 * @Date	 2014-9-24 	 
 */
public class MoneyFormat {
	//0-9对应的中文数字常量
	public static final String EMPTY = "";
	public static final String ZERO = "零";
	public static final String ONE = "壹";
	public static final String TWO = "贰";
	public static final String THREE = "叁";
	public static final String FOUR = "肆";
	public static final String FIVE = "伍";
	public static final String SIX = "陆";
	public static final String SEVEN = "柒";
	public static final String EIGHT = "捌";
	public static final String NINE = "玖";
	//中文数字对应的单位常量
	public static final String TEN = "拾";
	public static final String HUNDRED = "佰";
	public static final String THOUSAND = "仟";
	public static final String TEN_THOUSAND = "万";
	public static final String HUNDRED_MILLION = "亿";
	public static final String YUAN = "元";
	public static final String JIAO = "角";
	public static final String FEN = "分";
	public static final String DOT = ".";

	private static MoneyFormat formatter = null;
	//0-9对应的中文数字关系
	private Map<String, String> chineseNumberMap = new HashMap<String, String>();
	//对应中文数字单位关系
	private Map<String, String> chineseMoneyPattern = new HashMap<String, String>();
	private NumberFormat numberFormat = NumberFormat.getInstance();

	private MoneyFormat() {

		numberFormat.setMaximumFractionDigits(4);
		numberFormat.setMinimumFractionDigits(2);
		numberFormat.setGroupingUsed(false);

		chineseNumberMap.put("0", ZERO);
		chineseNumberMap.put("1", ONE);
		chineseNumberMap.put("2", TWO);
		chineseNumberMap.put("3", THREE);
		chineseNumberMap.put("4", FOUR);
		chineseNumberMap.put("5", FIVE);
		chineseNumberMap.put("6", SIX);
		chineseNumberMap.put("7", SEVEN);
		chineseNumberMap.put("8", EIGHT);
		chineseNumberMap.put("9", NINE);
		chineseNumberMap.put(DOT, DOT);
		chineseMoneyPattern.put("1", TEN);
		chineseMoneyPattern.put("2", HUNDRED);
		chineseMoneyPattern.put("3", THOUSAND);
		chineseMoneyPattern.put("4", TEN_THOUSAND);
		chineseMoneyPattern.put("5", TEN);
		chineseMoneyPattern.put("6", HUNDRED);
		chineseMoneyPattern.put("7", THOUSAND);
		chineseMoneyPattern.put("8", HUNDRED_MILLION);
	}

	public static MoneyFormat getInstance() {
		if (formatter == null)
			formatter = new MoneyFormat();
		return formatter;
	}

	/**
	 * 将数字金额转化为中文金额
	 * <p>
	 * 如：
	 * <pre>
	 *  壹拾亿壹仟零壹万零贰拾壹元壹分 | 1010010021.01
	 *	零元壹角整 | 0.10
	 *	壹万零贰佰贰拾元壹角整 | 10,220.1
	 *	贰拾元壹角整 | 20.1
	 *	贰拾元整 | 20
	 * </pre>
	 * 
	 * @param moneyStr 需要转化的阿拉伯数字金额
	 * @return 中文金额
	 */
	public String format(String moneyStr) {
		//检查精度--精度不能比 分 还低
		checkPrecision(moneyStr);
		moneyStr = removeComma(moneyStr);
		moneyStr = fillTwoDecimal(moneyStr);
		String result;
		result = convertToChineseNumber(moneyStr);
		result = addUnitsToChineseMoneyString(result);
		return result;
	}

	public String format(double moneyDouble) {
		return format(numberFormat.format(moneyDouble));
	}

	public String format(int moneyInt) {
		return format(numberFormat.format(moneyInt));
	}

	public String format(long moneyLong) {
		return format(numberFormat.format(moneyLong));
	}

	public String format(Number moneyNum) {
		return format(numberFormat.format(moneyNum));
	}

	private String convertToChineseNumber(String moneyStr) {
		String result;//存储转化后的中文金额
		StringBuffer cMoneyStringBuffer = new StringBuffer();
		//壹零零壹零零贰壹.零壹(10010021.01)
		for (int i = 0; i < moneyStr.length(); i++) {
			cMoneyStringBuffer.append(chineseNumberMap.get(moneyStr.substring(i, i + 1)));
		}
		//拾佰仟万亿等都是汉字里面才有的单位，加上它们（单位由小到大）
		int indexOfDot = cMoneyStringBuffer.indexOf(DOT);
		int moneyPatternCursor = 1;//单位游标
		//壹仟零佰零拾壹万零仟零佰贰拾壹.零壹
		for (int i = indexOfDot - 1; i > 0; i--) {
			cMoneyStringBuffer.insert(i, chineseMoneyPattern.get(EMPTY + moneyPatternCursor));
			moneyPatternCursor = moneyPatternCursor == 8 ? 1 : moneyPatternCursor + 1;
		}
		//小数部分
		String fractionPart = cMoneyStringBuffer.substring(cMoneyStringBuffer.indexOf("."));
		cMoneyStringBuffer.delete(cMoneyStringBuffer.indexOf("."), cMoneyStringBuffer.length());
		//处理整数部分正确
		while (cMoneyStringBuffer.indexOf("零拾") != -1) {
			cMoneyStringBuffer.replace(cMoneyStringBuffer.indexOf("零拾"), cMoneyStringBuffer.indexOf("零拾") + 2, ZERO);
		}
		while (cMoneyStringBuffer.indexOf("零佰") != -1) {
			cMoneyStringBuffer.replace(cMoneyStringBuffer.indexOf("零佰"), cMoneyStringBuffer.indexOf("零佰") + 2, ZERO);
		}
		while (cMoneyStringBuffer.indexOf("零仟") != -1) {
			cMoneyStringBuffer.replace(cMoneyStringBuffer.indexOf("零仟"), cMoneyStringBuffer.indexOf("零仟") + 2, ZERO);
		}
		while (cMoneyStringBuffer.indexOf("零万") != -1) {
			cMoneyStringBuffer.replace(cMoneyStringBuffer.indexOf("零万"), cMoneyStringBuffer.indexOf("零万") + 2,
					TEN_THOUSAND);
		}
		while (cMoneyStringBuffer.indexOf("零亿") != -1) {
			cMoneyStringBuffer.replace(cMoneyStringBuffer.indexOf("零亿"), cMoneyStringBuffer.indexOf("零亿") + 2,
					HUNDRED_MILLION);
		}
		while (cMoneyStringBuffer.indexOf("零零") != -1) {
			cMoneyStringBuffer.replace(cMoneyStringBuffer.indexOf("零零"), cMoneyStringBuffer.indexOf("零零") + 2, ZERO);
		}
		//最后一位为零时的情况（删除）
		if (cMoneyStringBuffer.lastIndexOf(ZERO) == cMoneyStringBuffer.length() - 1 && cMoneyStringBuffer.length() != 1) {
			//（删除零）
			cMoneyStringBuffer.delete(cMoneyStringBuffer.length() - 1, cMoneyStringBuffer.length());
		}
		cMoneyStringBuffer.append(fractionPart);
		result = cMoneyStringBuffer.toString();
		return result;
	}

	//处理小数点与元、角、分、整等单位
	private String addUnitsToChineseMoneyString(String moneyStr) {
		String result;
		StringBuffer cMoneyStringBuffer = new StringBuffer(moneyStr);
		int indexOfDot = cMoneyStringBuffer.indexOf(DOT);
		cMoneyStringBuffer.replace(indexOfDot, indexOfDot + 1, YUAN);//将点替换为元
		cMoneyStringBuffer.insert(cMoneyStringBuffer.length() - 1, JIAO);//插入角
		cMoneyStringBuffer.insert(cMoneyStringBuffer.length(), FEN);//插入分
		if (cMoneyStringBuffer.indexOf("零角零分") != -1) {// 没有零头，加整
			cMoneyStringBuffer.replace(cMoneyStringBuffer.indexOf("零角零分"), cMoneyStringBuffer.length(), "整");
		} else if (cMoneyStringBuffer.indexOf("零分") != -1) {// 没有零分，加整
			cMoneyStringBuffer.replace(cMoneyStringBuffer.indexOf("零分"), cMoneyStringBuffer.length(), "整");
		} else {
			if (cMoneyStringBuffer.indexOf("零角") != -1)
				cMoneyStringBuffer.delete(cMoneyStringBuffer.indexOf("零角"), cMoneyStringBuffer.indexOf("零角") + 2);
			// tmpBuffer.append("整");
		}
		result = cMoneyStringBuffer.toString();
		return result;
	}

	/**
	 * 检查精度--精度不能比 分 还低
	 * @param moneyStr
	 */
	private void checkPrecision(String moneyStr) {
		//金额小数位
		int fractionDigits = moneyStr.length() - moneyStr.indexOf(DOT) - 1;
		if (fractionDigits > 2) {
			throw new RuntimeException("金额" + moneyStr + "的小数位多于两位。"); // 精度不能比分低
		} else if (fractionDigits <= 0) {
			throw new RuntimeException("金额" + moneyStr + "的小数位小<=0位。"); // 精度不能是0位  (如＂1.＂）
		}
	}

	//补充完整的小数位（两位）
	public String fillTwoDecimal(String moneyStr) {
		return new DecimalFormat("#0.00").format(Double.valueOf(moneyStr));
	}

	//补充完整的小数位（四位）
	public String fillFourDecimal(String moneyStr) {
		return new DecimalFormat("#0.0000").format(Double.valueOf(moneyStr));
	}

	//删除金额中的逗号
	public String removeComma(String moneyStr) {
		//TODO 优化建议（使用正则匹配）
		return moneyStr.replace(" ", "").replace(",", "").replace("，", "");
	}

	public static void main(String args[]) {
		System.out.println(getInstance().format(new Double(1010010021.01)) + " | " + "1010010021.01");
		System.out.println(getInstance().format(new Double(0.10)) + " | " + "0.10");
		System.out.println(getInstance().format("10,220.1") + " | " + "10,220.1");
		System.out.println(getInstance().format("20.1") + " | " + "20.1");
		System.out.println(getInstance().format("20") + " | " + "20");
	}
}