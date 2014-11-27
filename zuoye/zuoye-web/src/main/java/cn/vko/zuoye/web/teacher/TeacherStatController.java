/**
 * TeacherStatController.java
 * cn.vko.zuoye.web.teacher
 * Copyright (c) 2014, 北京微课创景教育科技有限公司版权所有.
*/

package cn.vko.zuoye.web.teacher;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.vko.zuoye.service.TeacherStatService;
import cn.vko.zuoye.web.BaseController;

import com.vko.core.common.util.DateUtil;

/**
 * 老师作业统计模块
 * @author   杨闯
 * @Date	 2014-7-17 	 
 */
@Controller
@RequestMapping("")
public class TeacherStatController extends BaseController {

	@Autowired
	private TeacherStatService teacherStatService;

	/**
	 * 查看，作业统计
	 * <p>
	 *
	 * @param hwId 作业Id
	 * @return 跳转到作业统计页面
	 */
	@RequestMapping("/stat/{hwId:[\\d]+}")
	public String stat(Model model, @PathVariable long hwId) {
		model.addAttribute("obj", teacherStatService.stat(hwId));
		model.addAttribute("dateUtil", new DateUtil());
		return "teacher/stat";
	}
}
