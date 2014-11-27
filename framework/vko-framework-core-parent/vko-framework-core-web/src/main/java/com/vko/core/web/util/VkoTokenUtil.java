/**
 * VkoTokenUtil.java
 * com.vko.core.web.util
 * Copyright (c) 2014, 北京微课创景教育科技有限公司版权所有.
 */
package com.vko.core.web.util;

import java.util.Date;

import com.vko.core.common.exception.VkoServiceException;
import com.vko.core.common.util.security.MD5Util;

/**
 * 
 * @author 丁辰叶
 * @date 2014-4-25
 */
public class VkoTokenUtil {

	public static final String REDIS_TOKEN_TYPE = "access_token";
	public static final String REDIS_OAUTH2_TYPE = "oauth2";

	public static String createAccessToken(final long userId) {
		if (userId == 0) {
			throw new VkoServiceException("生成访问令牌时，缺少用户主键");
		}
		StringBuffer sb = new StringBuffer();
		sb.append("vko");
		sb.append(userId);// 用户ID
		sb.append(new Date().getTime());// 时间序列
		return MD5Util.md5Hex(sb.toString());// md5 生成 token
	}
}
