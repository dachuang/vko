/**
 * IndexController.java
 * cn.vko.zuoye.web
 * Copyright (c) 2014, 北京微课创景教育科技有限公司版权所有.
*/

package cn.vko.zuoye.web.student;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.vko.cache.dao.mybatis.Page;
import cn.vko.zuoye.entity.MyFalse;
import cn.vko.zuoye.entity.StudentHomework;
import cn.vko.zuoye.interceptor.VeloCityUtil;
import cn.vko.zuoye.service.StudentHomeService;
import cn.vko.zuoye.web.BaseController;

import com.vko.core.common.util.HttpUtil;

/**
 * <p>
 * @author   宋汝波
 * @Date	 2014-7-2 	 
 */
@Controller
@RequestMapping("student")
public class StudentHomeController extends BaseController {

	@Autowired
	private StudentHomeService studentHomeService;

	@RequestMapping("/myhomework")
	public String myhomework(Model model) {

		Long studentId = HttpUtil.getLong(inv.getRequest(), "userId", 0L);
		Long subjectId = HttpUtil.getLong(inv.getRequest(), "subjectId", 0L);
		Integer status = HttpUtil.getInt(inv.getRequest(), "status", 0);

		Page page = getPage();
		List<StudentHomework> hwList = studentHomeService.selectHomeWork(page, studentId, subjectId, status);

		model.addAttribute("page", page);
		model.addAttribute("hwList", hwList);
		model.addAttribute("util", new VeloCityUtil());

		return "student/myhomework";
	}

	@RequestMapping("/myfalse")
	public String myfalse(Model model) {

		Long studentId = HttpUtil.getLong(inv.getRequest(), "userId", 0L);
		Long subjectId = HttpUtil.getLong(inv.getRequest(), "subjectId", 0L);

		Page page = getPage();
		List<MyFalse> flist = studentHomeService.selectMyFalses(page, studentId, subjectId);

		model.addAttribute("page", page);
		model.addAttribute("flist", flist);
		model.addAttribute("util", new VeloCityUtil());

		return "student/myfalse";
	}

	@RequestMapping("/delfalse")
	@ResponseBody
	public String deleteMyfalse() {

		Long fid = HttpUtil.getLong(inv.getRequest(), "fid", 0L);

		int result = studentHomeService.delMyFalse(fid);

		if (result == 1) {
			return "{\"status\":\"OK\"}";
		} else {
			return "{\"status\":\"ERROR\"}";
		}

	}

}
