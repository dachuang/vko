/**
 * IndexController.java
 * cn.vko.zuoye.web
 * Copyright (c) 2014, 北京微课创景教育科技有限公司版权所有.
*/

package cn.vko.zuoye.web.teacher;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.vko.zuoye.service.TestService;
import cn.vko.zuoye.web.BaseController;

/**
 * <p>
 * @author   宋汝波
 * @Date	 2014-7-2 	 
 */
@Controller
@RequestMapping("teacher")
public class TeacherGroupController extends BaseController {

	@Autowired
	private TestService service;

}
