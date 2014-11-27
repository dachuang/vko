/**
 * gfdsg.java
 * cn.vko.zuoye.interceptor
 * Copyright (c) 2014, 北京微课创景教育科技有限公司版权所有.
*/

package cn.vko.zuoye.interceptor;

import java.nio.charset.Charset;

import org.springframework.http.converter.StringHttpMessageConverter;

/**
 * 自定义spring的webMvc，返回支持包含中文的Json串
 * <p>
 * 主要解决spring默认以ISO8859-1编码，中文乱码问题
 * 
 * @author   宋汝波
 * @Date	 2014-7-25 	 
 */
public class UTF8StringHttpMessageConverter extends StringHttpMessageConverter {

	public static final Charset DEFAULT_CHARSET_UTF8 = Charset.forName("UTF-8");

	public UTF8StringHttpMessageConverter() {
		super(DEFAULT_CHARSET_UTF8);
	}
}
