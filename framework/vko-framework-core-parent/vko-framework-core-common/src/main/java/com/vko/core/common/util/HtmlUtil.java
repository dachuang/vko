package com.vko.core.common.util;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 处理html的工具类
 * 
 * @author longyin E-mail:darkwing.zhu@gmail.com
 * @version 创建时间：2012-7-17 下午12:54:25
 *
 */
public class HtmlUtil {
	@SuppressWarnings("unchecked")
	public static String subStringHTML(String param, int length, String end) {
		StringBuffer result = new StringBuffer();
		int n = 0;

		boolean isCode = false;
		boolean isHTML = false;
		for (int i = 0; i < param.length(); i++) {
			char temp = param.charAt(i);
			if (temp == '<') {
				isCode = true;
			} else if (temp == '&') {
				isHTML = true;
			} else if ((temp == '>') && (isCode)) {
				n -= 1;
				isCode = false;
			} else if ((temp == ';') && (isHTML)) {
				isHTML = false;
			}

			if ((!isCode) && (!isHTML)) {
				n += 1;

				if ((temp + "").getBytes().length > 1) {
					n += 1;
				}
			}

			result.append(temp);
			if (n >= length) {
				break;
			}
		}
		result.append(end);

		String temp_result = result.toString().replaceAll("(>)[^<>]*(<?)", "$1$2");

		temp_result = temp_result.replaceAll(
				"</?(AREA|BASE|BASEFONT|BODY|BR|COL|COLGROUP|DD|DT|FRAME|HEAD|HR|HTML|IMG|INPUT|ISINDEX|LI|LINK|META|OPTION|P|PARAM|TBODY|TD|TFOOT|TH|THEAD|TR|area|base|basefont|body|br|col|colgroup|dd|dt|frame|head|hr|html|img|input|isindex|li|link|meta|option|p|param|tbody|td|tfoot|th|thead|tr)[^<>]*/?>",
				"");

		temp_result = temp_result.replaceAll("<([a-zA-Z]+)[^<>]*>(.*?)</\\1>", "$2");

		Pattern p = Pattern.compile("<([a-zA-Z]+)[^<>]*>");
		Matcher m = p.matcher(temp_result);

		List endHTML = new ArrayList();

		while (m.find()) {
			endHTML.add(m.group(1));
		}

		for (int i = endHTML.size() - 1; i >= 0; i--) {
			result.append("</");
			result.append(endHTML.get(i));
			result.append(">");
		}

		return result.toString();
	}

	public static String Html2Text(String inputString) {
		String htmlStr = inputString;
		String textStr = "";
		try {
			String regEx_script = "<[\\s]*?script[^>]*?>[\\s\\S]*?<[\\s]*?\\/[\\s]*?script[\\s]*?>";

			String regEx_style = "<[\\s]*?style[^>]*?>[\\s\\S]*?<[\\s]*?\\/[\\s]*?style[\\s]*?>";

			String regEx_html = "<[^>]+>";
			String regEx_html1 = "<[^>]+";
			Pattern p_script = Pattern.compile(regEx_script, 2);
			Matcher m_script = p_script.matcher(htmlStr);
			htmlStr = m_script.replaceAll("");

			Pattern p_style = Pattern.compile(regEx_style, 2);
			Matcher m_style = p_style.matcher(htmlStr);
			htmlStr = m_style.replaceAll("");

			Pattern p_html = Pattern.compile(regEx_html, 2);
			Matcher m_html = p_html.matcher(htmlStr);
			htmlStr = m_html.replaceAll("");

			Pattern p_html1 = Pattern.compile(regEx_html1, 2);
			Matcher m_html1 = p_html1.matcher(htmlStr);
			htmlStr = m_html1.replaceAll("");

			textStr = htmlStr;
		} catch (Exception e) {
			System.err.println("Html2Text: " + e.getMessage());
		}

		return textStr;
	}

	@SuppressWarnings("unchecked")
	public static List<String> subImgFromHTML(String inputString) {
		List srcPathList = new ArrayList();

		srcPathList.addAll(getSrcPath("src='(.+?)'", inputString));
		srcPathList.addAll(getSrcPath("src=\"(.+?)\"", inputString));
		srcPathList.addAll(getSrcPath("src=(.+?)", inputString));

		return srcPathList;
	}

	@SuppressWarnings("unchecked")
	private static List<String> getSrcPath(String patternStr, String inputString) {
		List picPathGroup = new ArrayList();
		List srcPathList = new ArrayList();
		Pattern pattern = Pattern.compile(patternStr);
		Matcher matcher = pattern.matcher(inputString);
		while (matcher.find()) {
			picPathGroup.add(matcher.group());
		}
		for (int i = 0; i < picPathGroup.size(); i++) {
			String str = (String) picPathGroup.get(i);
			if (str.length() <= 5)
				continue;
			String srcPath = str.substring(5, str.length() - 1);
			srcPathList.add(srcPath);
		}

		return srcPathList;
	}

	@SuppressWarnings("unchecked")
	public static void main(String[] as) {
		String str = "<P> <IMG src='C:\\无心的流得01.gif' borde=0>xxx...> <IMG src=\"d:\\xxx.gif\" borde=0>xxx... ";

		List st = subImgFromHTML(str);
		for (int i = 0; i < st.size(); i++) {
			System.out.println((String) st.get(i));
		}
	}
}