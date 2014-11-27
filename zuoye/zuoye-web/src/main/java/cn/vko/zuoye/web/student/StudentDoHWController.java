/**
 * IndexController.java
 * cn.vko.zuoye.web
 * Copyright (c) 2014, 北京微课创景教育科技有限公司版权所有.
*/

package cn.vko.zuoye.web.student;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.vko.zuoye.entity.Homework;
import cn.vko.zuoye.entity.StudentHomework;
import cn.vko.zuoye.keyvalue.HomeworkStatus;
import cn.vko.zuoye.keyvalue.HomeworkType;
import cn.vko.zuoye.service.StudentDoHWService;
import cn.vko.zuoye.web.BaseController;

import com.vko.core.common.exception.VkoServiceException;

/**
 * 学生做作业
 * <p>
 * @author   宋汝波
 * @Date	 2014-7-2 	 
 */
@Controller
@RequestMapping("")
public class StudentDoHWController extends BaseController {

	@Autowired
	private StudentDoHWService studentDoHWService;

	/**
	 * 根据作业的类型到不同的做作业页面
	 * <p>
	 *
	 * @param model
	 * @param studentHWId
	 * @return
	*/
	@RequestMapping("dohw/{stHwId:[0-9]+}")
	public String doHomework(Model model, @PathVariable("stHwId") Long studentHWId) throws Exception {
		Map<String, Object> result = studentDoHWService.getData4DoHW(studentHWId);
		model.addAllAttributes(result);
		Homework homework = (Homework) result.get("homework");
		studentDoHWService.updateStudentHWStart(studentHWId);
		if (HomeworkType.TIKU_CHANNEL.getKey() == homework.getType()) {
			return "student/dohw/doTiku";
		} else {
			return "student/dohw/doShouji";
		}
	}

	/**
	 * 用户答题后提交，两种类型
	 * <p>
	 *
	 * @param model
	 * @param studentHWId
	 * @param type
	 * @return
	*/
	@SuppressWarnings("unchecked")
	@RequestMapping("shw/{stHwId:[0-9]+}")
	public String saveHomework(Model model, @PathVariable("stHwId") Long studentHWId, @RequestParam("type") int type)
			throws Exception {
		if (type == HomeworkType.TIKU_CHANNEL.getKey()) {
			studentDoHWService.saveHWAnswer4TK(inv.getRequest().getParameterMap(), studentHWId);
		} else {
			studentDoHWService.saveHWAnswer4Shouji(inv.getRequest().getParameterMap(), studentHWId);
		}
		studentDoHWService.updateStudentHWEnd(studentHWId);
		//Homework homework = studentDoHWService.getHomeworkInfo(null, studentHWId);
		StudentHomework homework = studentDoHWService.getStHomeworkInfo(studentHWId);
		model.addAttribute("homework", homework);
		//成功提示页面
		return "student/dohw/success";
	}

	/**
	 * 查看学生作业信息
	 * <p>
	 *
	 * @param model
	 * @param studentHWId
	 * @return
	*/
	@RequestMapping("vhw/{stHwId:[0-9]+}")
	public String viewHomework(Model model, @PathVariable("stHwId") Long studentHWId) throws Exception {
		Map<String, Object> result = studentDoHWService.getData4ViewHW(studentHWId);
		model.addAllAttributes(result);
		Homework homework = (Homework) result.get("homework");
		if (homework.getStatus() != HomeworkStatus.CORRECTED.getKey()) {
			throw new VkoServiceException("该作业还没有被判,请耐心等待老师判完之后进行查看.");
		}
		if (HomeworkType.TIKU_CHANNEL.getKey() == homework.getType()) {
			return "student/dohw/viewTiku";
		} else {
			return "student/dohw/viewShouji";
		}
	}

	/**
	 * 更新求解析次数
	 * <p>
	 *
	 * @param hwId
	 * @param examId
	 * @return
	*/
	@RequestMapping("beg/{hwId:[0-9]+}_{examId:[0-9]+}")
	@ResponseBody
	public String begNum(@PathVariable("hwId") Long hwId, @PathVariable("examId") Long examId) throws Exception {
		int nums = studentDoHWService.saveBegTimes(hwId, examId);
		return "{\"success\":true,\"nums\":" + nums + "}";
	}

	/**
	 * 添加错题本
	 * <p>
	 *
	 * @param hwId
	 * @param examId
	 * @return
	*/
	@RequestMapping("myfalse/{hwId:[0-9]+}_{examId:[0-9]+}")
	@ResponseBody
	public String addMyFalse(@PathVariable("hwId") Long hwId, @PathVariable("examId") Long examId) throws Exception {
		try {
			studentDoHWService.saveMyFalse(hwId, examId);
		} catch (VkoServiceException e) {
			return "{\"success\":false,\"msg\":\"" + e.getMessage() + "\"}";
		}
		return "{\"success\":true}";
	}

	/**
	 * 更新评论次数
	 * <p>
	 *
	 * @param hwId
	 * @return
	*/
	@RequestMapping("comment/{hwId:[0-9]+}")
	@ResponseBody
	public String addCommentTimes(@PathVariable("hwId") Long hwId) throws Exception {
		try {
			studentDoHWService.updateCommentTimes(hwId);
		} catch (VkoServiceException e) {
			return "{\"success\":false,\"msg\":\"" + e.getMessage() + "\"}";
		}
		return "{\"success\":true}";
	}
}
