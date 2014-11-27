package com.vko.core.captcha;

import java.util.List;

/**
 * 构想组件之验证码服务Service
 * 
 * @author darkwing
 *
 */
public interface VkoCaptchaService {
	
	/**
	 * 产生验证码图片需要的Key
	 * 
	 * @return
	 * @throws GoothinkCaptchaException
	 */
	String generateCaptchaKey() throws VkoCaptchaException;

	/**
	 * 根据key生成验证码图片的二进制流
	 * 
	 * @param captchaKey
	 * @return
	 * @throws GoothinkCaptchaException
	 */
	byte[] generateCaptchaImage(String captchaKey) throws VkoCaptchaException;

	/**
	 * 校验验证码正确与否
	 * 
	 * @param captchaKey
	 * @param captchaValue
	 * @return
	 * @throws GoothinkCaptchaException
	 */
	boolean validateCaptcha(String captchaKey, String captchaValue) throws VkoCaptchaException;

	List<String> getPreDefinedTexts();

	void setPreDefinedTexts(List<String> preDefinedTexts);
}
