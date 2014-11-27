package com.vko.core.web;

import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.vko.core.captcha.VkoCaptchaException;
import com.vko.core.captcha.VkoCaptchaService;


/**
 * 产生验证码图片Servlet
 * 
 * @author darkwing
 *
 */
public class CaptchaImageServlet extends HttpServlet {
	private ApplicationContext context;

	private static final long serialVersionUID = 5274323889605521606L;

	@Override
	public void init() throws ServletException {
		super.init();
		context = WebApplicationContextUtils
				.getWebApplicationContext(getServletContext());
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
	    VkoCaptchaService service = (VkoCaptchaService) context
				.getBean("captchaService");

		try {
			String captchaKey = service.generateCaptchaKey();
			response.setContentType("image/jpeg");
			OutputStream out = response.getOutputStream();
			out.write(service.generateCaptchaImage(captchaKey));
			out.close();
		} catch (VkoCaptchaException e) {
			response.sendError(400, e.getMessage());
		}

	}
}
