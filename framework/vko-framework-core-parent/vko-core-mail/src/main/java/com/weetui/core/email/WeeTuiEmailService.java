package com.weetui.core.email;


/**
 * 构想之邮件发送接口
 * 
 * @author darkwing
 * 
 */
public interface WeeTuiEmailService {

	/**
	 * 发送邮件
	 * 
	 * @param to
	 *            邮件接受者email
	 * @param subject
	 *            邮件主题
	 * @param htmlText
	 *            邮件内容
	 * @param siteName
	 *            邮件发件人（显示网站名称）
	 * @throws GoothinkEmailException
	 */
	void sendMail(String to, String subject, String htmlText,String siteName)
			throws WeeTuiEmailException;
}
