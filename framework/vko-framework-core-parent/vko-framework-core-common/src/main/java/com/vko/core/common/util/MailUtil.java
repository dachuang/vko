package com.vko.core.common.util;

import org.apache.commons.lang.StringUtils;


public class MailUtil {
	/***
	 * 获取email的网址
	 * 
	 * @param email
	 * @return
	 */
	public static String getMailSite(String email) {
		String emailSite = "";
		if(StringUtils.isNotEmpty(email))
		{
			if(email.indexOf("@yahoo.com.cn") != -1) {
				emailSite = "http://mail.yahoo.com.cn";
			}else if(email.indexOf("@163.com") != -1) {
				emailSite = "http://mail.163.com";
			}else if(email.indexOf("@qq.com") != -1) {
				emailSite = "http://mail.qq.com";
			}else if(email.indexOf("@sina.com") != -1) {
				emailSite = "http://www.sina.com";
			}else if(email.indexOf("@gmail.com") != -1) {
				emailSite = "http://mail.google.com";
			}else if(email.indexOf("@sohu.com") != -1) {
				emailSite = "http://mail.sohu.com";
			}else if(email.indexOf("@tom.com") != -1) {
				emailSite = "http://mail.tom.com";
			}else if(email.indexOf("@hotmail.com") != -1) {
				emailSite = "http://mail.live.com";
			}else if(email.indexOf("@21cn.com") != -1) {
				emailSite = "http://mail.21cn.com";
			}
		}
		return emailSite;
	}
}
