/**
 * IndexController.java
 * cn.vko.zuoye.web
 * Copyright (c) 2014, 北京微课创景教育科技有限公司版权所有.
*/

package cn.vko.zuoye.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.vko.zuoye.interceptor.VeloCityUtil;
import cn.vko.zuoye.service.TestService;

/**
 * <p>
 * @author   宋汝波
 * @Date	 2014-7-2 	 
 */
@Controller
@RequestMapping("")
public class IndexController extends BaseController {

	@Autowired
	private TestService service;

	@RequestMapping("/index")
	public String test(Model model) {
		logger.info("12123132");
		long start = System.currentTimeMillis();
		VeloCityUtil ttt = new VeloCityUtil();
		System.out.println(ttt.keyResolve(100001));
		model.addAttribute("test", ttt.keyResolve(100001));
		logger.debug("sgsdgdsg");
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		logger.debug("test cost: {} ms", (System.currentTimeMillis() - start));
		//		int i = 1 / 0;
		return "test";
	}

	@RequestMapping("/data")
	@ResponseBody
	public String testHttpData() {
		System.out.println(service.getObj());

		return "{\"name\":\"OK\"}";
	}
}
