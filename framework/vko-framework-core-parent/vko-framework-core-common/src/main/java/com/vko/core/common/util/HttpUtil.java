package com.vko.core.common.util;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

public final class HttpUtil {

	public static String getURL(HttpServletRequest request) {
		StringBuffer sb = request.getRequestURL();
		String queryString = request.getQueryString();
		if (queryString != null)
			return sb.toString() + "?" + queryString;
		return sb.toString();
	}

	/**
	 * Get Integer parameter from request. If specified parameter name is not
	 * found, the default value will be returned.
	 */
	public static int getInt(HttpServletRequest request, String paramName,
			int defaultValue) {
		String s = request.getParameter(paramName);
		if (s == null || s.equals(""))
			return defaultValue;
		return Integer.parseInt(s);
	}

	/**
	 * Get Integer parameter from request. If specified parameter name is not
	 * found, an Exception will be thrown.
	 */
	public static int getInt(HttpServletRequest request, String paramName) {
		String s = request.getParameter(paramName);
		return Integer.parseInt(s);
	}
	
	public static long getLong(HttpServletRequest request, String paramName,
			long defaultValue) {
		String s = request.getParameter(paramName);
		if (s == null || s.equals(""))
			return defaultValue;
		return Long.parseLong(s);
	}
	
	public static long getLong(HttpServletRequest request, String paramName) {
		String s = request.getParameter(paramName);
		return Long.parseLong(s);
	}

	/**
	 * Get String parameter from request. If specified parameter name is not
	 * found, the default value will be returned.
	 */
	public static String getString(HttpServletRequest request,
			String paramName, String defaultValue) {
		String s = request.getParameter(paramName);
		if (s == null || s.equals(""))
			return defaultValue;
		return s;
	}

	/**
	 * Get String parameter from request. If specified parameter name is not
	 * found or empty, an Exception will be thrown.
	 */
	public static String getString(HttpServletRequest request, String paramName) {
		String s = request.getParameter(paramName);
		if (s == null || s.equals(""))
			throw new NullPointerException("Null parameter: " + paramName);
		return s;
	}

	/**
	 * Get Boolean parameter from request. If specified parameter name is not
	 * found, an Exception will be thrown.
	 */
	public static boolean getBoolean(HttpServletRequest request,
			String paramName) {
		String s = request.getParameter(paramName);
		return Boolean.parseBoolean(s);
	}

	/**
	 * Get Boolean parameter from request. If specified parameter name is not
	 * found, the default value will be returned.
	 */
	public static boolean getBoolean(HttpServletRequest request,
			String paramName, boolean defaultValue) {
		String s = request.getParameter(paramName);
		if (s == null || s.equals(""))
			return defaultValue;
		return Boolean.parseBoolean(s);
	}

	/**
	 * Get float parameter from request. If specified parameter name is not
	 * found, an Exception will be thrown.
	 */
	public static float getFloat(HttpServletRequest request, String paramName) {
		String s = request.getParameter(paramName);
		return Float.parseFloat(s);
	}

	/**
	 * Create a FormBean and bind data to it. Example: If found a parameter
	 * named "age", the object's setAge() method will be invoked if this method
	 * exists. If a setXxx() method exists but no corrsponding parameter, this
	 * setXxx() method will never be invoked.<br/>
	 * <b>NOTE:</b> only public setXxx() method can be invoked successfully.
	 */
	@SuppressWarnings("unchecked")
	public static Object createFormBean(HttpServletRequest request, Class c) {
		Object bean;
		try {
			bean = c.newInstance();
		} catch (Exception e) {
			return new Object();
		}
		Method[] ms = c.getMethods();
		for (int i = 0; i < ms.length; i++) {
			String name = ms[i].getName();
			if (name.startsWith("set")) {
				Class[] cc = ms[i].getParameterTypes();
				if (cc.length == 1) {
					String type = cc[0].getName(); // parameter type
					try {
						// get property name:
						String prop = Character.toLowerCase(name.charAt(3))
								+ name.substring(4);
						// get parameter value:
						String param = getString(request, prop);
						if (param != null && !param.equals("")) {
							if (type.equals("java.lang.String")) {
								ms[i].invoke(bean,
										new Object[] { htmlEncode(param) });
							} else if (type.equals("int")
									|| type.equals("java.lang.Integer")) {
								ms[i].invoke(bean, new Object[] { new Integer(
										param) });
							} else if (type.equals("long")
									|| type.equals("java.lang.Long")) {
								ms[i].invoke(bean, new Object[] { new Long(
										param) });
							} else if (type.equals("boolean")
									|| type.equals("java.lang.Boolean")) {
								ms[i].invoke(bean, new Object[] { Boolean
										.valueOf(param) });
							} else if (type.equals("float")
									|| type.equals("java.lang.Float")) {
								ms[i].invoke(bean, new Object[] { new Float(
										param) });
							} else if (type.equals("java.util.Date")) {
								Date date = null;
								if (param.indexOf(':') != (-1))
									date = DateUtil.parseDateTime(param);
								else
									date = DateUtil.parseDate(param);
								if (date != null)
									ms[i].invoke(bean, new Object[] { date });
								else
									System.err
											.println("WARNING: date is null: "
													+ param);
							}
						}
					} catch (Exception e) {
						System.err.println("WARNING: Invoke method "
								+ ms[i].getName() + " failed: "
								+ e.getMessage());
					}
				}
			}
		}
		return bean;
	}

	public static String htmlEncode(String text) {
		if (text == null || "".equals(text))
			return "";
		text = text.replace("<", "&lt;");
		text = text.replace(">", "&gt;");
		text = text.replace(" ", "&nbsp;");
		text = text.replace("\"", "&quot;");
		text = text.replace("\'", "&apos;");
		return text.replace("\n", "<br/>");
	}

	public static String htmlEncodeNotBlank(String text) {
		if (text == null || "".equals(text))
			return "";
		text = text.replace("<", "&lt;");
		text = text.replace(">", "&gt;");
		text = text.replace("\"", "&quot;");
		text = text.replace("\'", "&apos;");
		return text.replace("\n", "<br/>");
	}

	public static String encode(String text) {
		if (text == null || "".equals(text))
			return "";
		text = text.replace("<", "&lt;");
		text = text.replace(">", "&gt;");
		text = text.replace("\"", "&quot;");
		text = text.replace("\'", "&apos;");
		text = text.replace("&", "&amp;");
		return text.replace("\n", "<br/>");
	}

	public static String encodeHtml(String text) {
		if (text == null || "".equals(text))
			return "";
		text = text.replace("&lt;", "<");
		text = text.replace("&gt;", ">");
		text = text.replace("&nbsp;", " ");
		text = text.replace("&quot;", "\"");
		text = text.replace("&apos;", "\'");
		return text.replace("<br/>", "\n");
	}

	public static String encodeHtml2(String text) {
		if (text == null || "".equals(text))
			return "";
		text = text.replace("&lt;", "<");
		text = text.replace("&gt;", ">");
		text = text.replace("&nbsp;", " ");
		text = text.replace("&quot;", "");
		text = text.replace("&apos;", "");
		return text.replace("<br/>", "\n");
	}

	/**
	 * 获取客户端IP地址
	 * 
	 * @param request
	 * @return
	 */
	public static String getIpAddr(HttpServletRequest request) {
		String ip = request.getHeader("x-forwarded-for");
		if ((ip == null) || (ip.length() == 0)
				|| ("unknown".equalsIgnoreCase(ip))) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if ((ip == null) || (ip.length() == 0)
				|| ("unknown".equalsIgnoreCase(ip))) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if ((ip == null) || (ip.length() == 0)
				|| ("unknown".equalsIgnoreCase(ip))) {
			ip = request.getRemoteAddr();
		}
		return ip;
	}

	/**
	 * 按字节长度截取字符串(支持截取带HTML代码样式的字符串)
	 * 
	 * @param param
	 *            将要截取的字符串参数
	 * @param length
	 *            截取的字节长度
	 * @param end
	 *            字符串末尾补上的字符串
	 * @return 返回截取后的字符串
	 */
	@SuppressWarnings("unchecked")
	public static String subStringHTML(String param, int length, String end) {
		StringBuffer result = new StringBuffer();
		int n = 0;
		char temp;
		boolean isCode = false; // 是不是HTML代码
		boolean isHTML = false; // 是不是HTML特殊字符,如
		for (int i = 0; i < param.length(); i++) {
			temp = param.charAt(i);
			if (temp == '<') {
				isCode = true;
			} else if (temp == '&') {
				isHTML = true;
			} else if (temp == '>' && isCode) {
				n = n - 1;
				isCode = false;
			} else if (temp == ';' && isHTML) {
				isHTML = false;
			}

			if (!isCode && !isHTML) {
				n = n + 1;
				// UNICODE码字符占两个字节
				if ((temp + "").getBytes().length > 1) {
					n = n + 1;
				}
			}

			result.append(temp);
			if (n >= length) {
				break;
			}
		}
		result.append(end);
		// 取出截取字符串中的HTML标记
		String temp_result = result.toString().replaceAll("(>)[^<>]*(<?)",
				"$1$2");
		// 去掉不需要结素标记的HTML标记
		temp_result = temp_result
				.replaceAll(
						"</?(AREA|BASE|BASEFONT|BODY|BR|COL|COLGROUP|DD|DT|FRAME|HEAD|HR|HTML|IMG|INPUT|ISINDEX|LI|LINK|META|OPTION|P|PARAM|TBODY|TD|TFOOT|TH|THEAD|TR|area|base|basefont|body|br|col|colgroup|dd|dt|frame|head|hr|html|img|input|isindex|li|link|meta|option|p|param|tbody|td|tfoot|th|thead|tr)[^<>]*/?>",
						"");
		// 去掉成对的HTML标记
		temp_result = temp_result.replaceAll("<([a-zA-Z]+)[^<>]*>(.*?)</\\1>",
				"$2");
		// 用正则表达式取出标记
		Pattern p = Pattern.compile("<([a-zA-Z]+)[^<>]*>");
		Matcher m = p.matcher(temp_result);

		List endHTML = new ArrayList();

		while (m.find()) {
			endHTML.add(m.group(1));
		}
		// 补全不成对的HTML标记
		for (int i = endHTML.size() - 1; i >= 0; i--) {
			result.append("</");
			result.append(endHTML.get(i));
			result.append(">");
		}

		return result.toString();
	}

	public static void main(String[] args) {
		String test = "&lt;FONT&nbsp;color=#1a6be6&gt;像艺术家一样浪漫，像工程师一样严谨;安分竭力，泊然如一无所求者，不过两年，则必为上官僚友所钦属也;虚妄的世界寻求一方净土:人生哲学-智者的天空;四书五经-儒、道、佛家的经典;日韩潮流-浪漫唯美的一隅；经典流行-清新雅乐的纯净&lt;/FONT&gt;";
		System.out.println(test);
		System.out
				.println("=============================================================================");
		System.out.println(encodeHtml(test));

		System.out.println(HttpUtil.subStringHTML(encodeHtml(test), 20, "..."));

	}
}
