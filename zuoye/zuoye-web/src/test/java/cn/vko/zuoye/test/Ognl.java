/**
 * Ognl.java
 * cn.vko.zuoye.test
 * Copyright (c) 2014, 北京微课创景教育科技有限公司版权所有.
*/

package cn.vko.zuoye.test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.apache.ibatis.scripting.xmltags.OgnlCache;
import org.springframework.util.StringUtils;

/**
 * TODO(这里用一句话描述这个类的作用)
 * <p>
 * TODO(这里描述这个类补充说明 – 可选)
 * @author   宋汝波
 * @Date	 2014-7-17 	 
 */
public class Ognl {

	/**
	 * TODO(这里用一句话描述这个方法的作用)
	 * <p>
	 * TODO(这里描述这个方法详情– 可选)
	 *
	 * @param args TODO(这里描述每个参数,如果有返回值描述返回值,如果有异常描述异常)
	*/

	public static void main(String[] args) {

		Map<String, Object> root = new HashMap<String, Object>();
		Map<String, Object> root2 = new HashMap<String, Object>();
		root2.put("xueDuanId", "123");

		root.put("_parameter", root2);
		try {
			Object value = OgnlCache.getValue("_parameter['xueDuanId']", root);
			System.out.println(value);

			String sss = "tt,,,ttt";

			System.out.println(Arrays.toString(StringUtils.tokenizeToStringArray(sss, ",")));
			System.out.println(Arrays.toString(sss.split(",")));
		} catch (Exception e) {

			e.printStackTrace();

		}

	}

}
