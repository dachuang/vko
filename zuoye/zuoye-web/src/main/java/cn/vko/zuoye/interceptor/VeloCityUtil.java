/**
 * VeloCityUtil.java
 * com.vko.core.common.util
 * Copyright (c) 2014, 北京微课创景教育科技有限公司版权所有.
*/

package cn.vko.zuoye.interceptor;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.vko.core.common.keyvalue.AbstractKeyValue;
import com.vko.core.web.filter.InvocationUtils;

/**
 * 页面相应函数操作
 * <p>
 * @author   宋汝波
 * @Date	 2014-5-13 	 
 */
public class VeloCityUtil {
	protected final static Logger logger = LoggerFactory.getLogger(VeloCityUtil.class);

	/**
	 * 数字,字符转化
	 * <p>
	 *
	 * @param number
	 * @return 
	*/
	public String Int2Char(int number) {
		return Character.toString((char) number);
	}

	/**
	 * 请求url,带参数
	 * <p>
	 *
	*/
	public String getUrl() {
		HttpServletRequest request = InvocationUtils.getCurrentThreadRequest();
		if (request == null) {
			throw new IllegalStateException("invocation");
		}
		String url = request.getRequestURL().toString();
		String query = request.getQueryString();
		StringBuilder str = new StringBuilder(url);
		if (query != null) {
			if (query.contains("_index=")) {
				query = query.substring(0, query.lastIndexOf("_index="));
			}
			str.append("?");
			str.append(query);
			if (!query.endsWith("&")) {
				str.append("&");
			}
		} else {
			str.append("?");
		}
		return str.toString();
	}

	/**
	 * 根据提供的整形值获得文本值
	 * <p>
	 *
	 * @param key
	*/
	public String keyResolve(Integer key) {
		if (key == null) {
			return "";
		}
		return AbstractKeyValue.getValue(key);
	}

	/**
	 * 判断是否为真的null
	 * <p>
	 *
	 * @param obj
	*/
	public boolean isNull(Object obj) {
		if (obj == null) {
			return true;
		}

		return false;
	}

	public boolean contains(Object src, Object dest) {
		if (src instanceof String) {
			String str = (String) src;
			return str.contains((String) dest);
		}
		if (src instanceof Map) {
			Map map = (Map) src;
			return map.containsKey(dest);
		}
		//		if (src instanceof List) {
		//			Map map = (Map) src;
		//			return map.containsKey(dest);
		//		}
		return false;
	}

	public String formatDate(Date date) {
		if (date == null) {
			return "";
		}

		return new SimpleDateFormat("yyyy-MM-dd").format(date);
	}

	public String formatDateTime(Date date) {
		if (date == null) {
			return "";
		}

		return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);
	}

}
