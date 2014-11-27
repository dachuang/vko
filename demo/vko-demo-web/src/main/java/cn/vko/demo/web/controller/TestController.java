/**
 * TestController.java
 * cn.vko.demo.web.controller
 * Copyright (c) 2014, 北京微课创景教育科技有限公司版权所有.
*/

package cn.vko.demo.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.vko.demo.service.TestService;

/**
 * 测试springmvc controller
 * @author   ychuang328
 * @Date	 2014-7-29 	 
 */
@Controller
@RequestMapping("/")
public class TestController {

	@Autowired
	private TestService testService;

	@RequestMapping("index")
	public String index(Model model) {
		model.addAttribute("userName", "spring framework ");
		System.out.println("spring framework hello!!!");
		testService.index();
		return "index";
	}

	@RequestMapping("hello")
	public String hello(Model model) {
		model.addAttribute("velocity", "hello I am Velocity");
		return "hello";
	}
}
