/**
 * IndexController.java
 * cn.vko.zuoye.web
 * Copyright (c) 2014, 北京微课创景教育科技有限公司版权所有.
*/

package cn.vko.zuoye.web.teacher;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.vko.cache.dao.mybatis.Page;
import cn.vko.zuoye.entity.Homework;
import cn.vko.zuoye.interceptor.VeloCityUtil;
import cn.vko.zuoye.service.TeacherGroupService;
import cn.vko.zuoye.service.TeacherHomeService;
import cn.vko.zuoye.service.sso.SsoFetchUser;
import cn.vko.zuoye.web.BaseController;

import com.vko.core.common.util.HttpUtil;
import com.vko.core.common.util.SmartMap;

/**
 * <p>
 * @author   宋汝波
 * @Date	 2014-7-2 	 
 */
@Controller
@RequestMapping("teacher")
public class TeacherHomeController extends BaseController {

	@Autowired
	private TeacherHomeService teacherHomeService;

	@Autowired
	private TeacherGroupService techaerGroupService;

	@RequestMapping("/homework")
	public String hwManager(Model model) {

		//Long teacherId = teacherHomeService.checkTeacher();
		Long teacherId = SsoFetchUser.getUserIdWithEx();
		Integer status = HttpUtil.getInt(inv.getRequest(), "status", 0);

		List<SmartMap<String, Object>> groups = techaerGroupService.selectGroupsByTeacherId(teacherId);
		if (groups != null && groups.size() > 0) {
			model.addAttribute("groups", groups);

			SmartMap<String, Object> defaultGroup = groups.get(0);
			Long groupId = defaultGroup.getLong("id");
			Page page = getPage();

			List<Homework> hwList = teacherHomeService.selectHomeWork(page, teacherId, groupId, status);

			model.addAttribute("hwList", hwList);
			model.addAttribute("page", page);
			model.addAttribute("util", new VeloCityUtil());
		}

		return "teacher/homework-manager";
	}

}
