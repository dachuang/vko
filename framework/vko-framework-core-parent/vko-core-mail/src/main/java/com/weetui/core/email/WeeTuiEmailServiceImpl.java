package com.weetui.core.email;

import java.io.UnsupportedEncodingException;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

/**
 * 构想之邮件发送接口实现
 * 
 * @author darkwing
 * 
 */
public class WeeTuiEmailServiceImpl implements WeeTuiEmailService {

	private JavaMailSender javaMailSender;

	private String systemEmail;

	public void sendMail(String to, String subject, String htmlText, String siteName) throws WeeTuiEmailException {
		try {
			MimeMessage msg = javaMailSender.createMimeMessage();
			MimeMessageHelper msgHelper = new MimeMessageHelper(msg);

			try {
				msgHelper.setFrom(systemEmail, siteName);
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			msgHelper.setTo(to);
			msgHelper.setSubject(subject);
			msgHelper.setText(htmlText, true);

			javaMailSender.send(msg);
		} catch (MessagingException e) {
			throw new WeeTuiEmailException("Faild to send mail.", e);
		}
	}

	public JavaMailSender getJavaMailSender() {
		return javaMailSender;
	}

	public void setJavaMailSender(JavaMailSender javaMailSender) {
		this.javaMailSender = javaMailSender;
	}

	public String getSystemEmail() {
		return systemEmail;
	}

	public void setSystemEmail(String systemEmail) {
		this.systemEmail = systemEmail;
	}

}
