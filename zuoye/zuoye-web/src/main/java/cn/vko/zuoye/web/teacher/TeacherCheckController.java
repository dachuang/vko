/**
 * TeacherCheckController.java
 * cn.vko.zuoye.web.teacher
 * Copyright (c) 2014, 北京微课创景教育科技有限公司版权所有.
*/

package cn.vko.zuoye.web.teacher;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.vko.zuoye.entity.Homework;
import cn.vko.zuoye.entity.StudentAnswer;
import cn.vko.zuoye.entity.StudentOptions;
import cn.vko.zuoye.keyvalue.HomeworkType;
import cn.vko.zuoye.service.TeacherCheckService;
import cn.vko.zuoye.web.BaseController;

import com.google.gson.Gson;
import com.vko.core.common.util.SmartMap;

/**
 * 作业审批模块
 * @author   杨闯
 * @Date	 2014-7-14 	 
 */
@Controller
@RequestMapping("")
public class TeacherCheckController extends BaseController {

	@Autowired
	private TeacherCheckService teacherCheckService;

	/**
	 * 入口
	 * @param hwId 作业对象ID
	 * @param act 操作类型（null/1：引用题库; 2：引用图片）
	 * @return 跳转到判作业页面
	*/
	@RequestMapping("/check/{hwId:[\\d]+}")
	public String index(Model model, @PathVariable long hwId) {
		//加载初始化的数据
		Map<String, Object> init = teacherCheckService.index(hwId);
		model.addAttribute("obj", init);

		String pageName = "";//返回的视图名称
		Homework hw = (Homework) init.get("hw");
		if (HomeworkType.TIKU_CHANNEL.getKey() == hw.getType().intValue()) {
			//引用题库试题
			pageName = "/teacher/check/indexTiku";
		} else {
			//引用图片试题
			pageName = "/teacher/check/indexImage";
		}
		return pageName;
	}

	/**
	 * 查看，已判作业
	 * <p>
	 *
	 * @param hwId 作业对象ID
	 * @param act 操作类型（null/1：引用题库; 2：引用图片）
	 * @return 跳转到查看，已判作业页面
	 */
	@RequestMapping("/check/view/{hwId:[\\d]+}")
	public String review(Model model, @PathVariable long hwId) {
		//加载初始化的数据
		Map<String, Object> init = teacherCheckService.index(hwId);
		model.addAttribute("obj", init);

		String pageName = "";//返回的视图名称
		if (HomeworkType.TIKU_CHANNEL.getKey() == ((Homework) init.get("hw")).getType().intValue()) {
			//引用题库试题
			pageName = "/teacher/check/viewTiku";
		} else {
			//引用图片试题
			pageName = "/teacher/check/viewImage";
		}
		return pageName;
	}

	/**
	 * 教师查看已发布的作业
	 * <p>
	 *
	 * @param hwId 作业Id
	 * @return 跳转到已发布的作业页面
	 */
	@RequestMapping("/release/view/{hwId:[\\d]+}")
	public String view(Model model, @PathVariable long hwId) {
		//加载教师查看已发布的作业数据
		Map<String, Object> init = teacherCheckService.viewRelaseHw(hwId);
		model.addAllAttributes(init);

		String pageName = "";//返回的视图名称
		if (HomeworkType.TIKU_CHANNEL.getKey() == ((Homework) init.get("homework")).getType().intValue()) {
			//引用题库试题
			pageName = "/teacher/release/viewTiku";
		} else {
			//引用图片试题
			pageName = "/teacher/release/viewImage";
		}
		return pageName;
	}

	/**
	 * 查看试题的某个选项的选择人列表
	 *
	 * @param statId 选项统计id
	 * @return 选项的选择人列表
	 * @throws IOException 
	 */
	@RequestMapping("/check/look")
	@ResponseBody
	public String look(@RequestParam("statId") long statId) throws IOException {
		List<StudentOptions> relVal = teacherCheckService.look(statId);
		inv.getResponse().setContentType("application/json;charset=UTF-8");
		inv.getResponse().getWriter().write(new Gson().toJson(relVal));
		return null;
	}

	/**
	 * 查看学生的试题答案
	 *
	 * @param hwId 作业id
	 * @param uId 学生Id
	 * @return 选项的选择人列表
	 * @throws IOException 
	 */
	@RequestMapping("/check/answer")
	@ResponseBody
	public String loadAnswer(@RequestParam("hwId") long hwId, @RequestParam("uId") long uId,
			@RequestParam("examId") long examId) throws IOException {
		StudentAnswer relVal = teacherCheckService.selectAnswerByStudetId(hwId, uId, examId);
		inv.getResponse().setContentType("application/json;charset=UTF-8");
		inv.getResponse().getWriter().write(new Gson().toJson(relVal));
		return null;
	}

	/**
	 * 审批主观题，设置得分
	 *
	 * @param hwId 作业对象ID
	 * @param eId 试题对象ID
	 * @param uId 学生对象ID 
	 * @param score 得分
	 * @return 
	 */
	@RequestMapping("/check/judge")
	@ResponseBody
	public String judge(@RequestParam("hwId") long hwId, @RequestParam("examId") long examId,
			@RequestParam("uId") long uId, @RequestParam("score") Integer score) {
		Boolean relsult = teacherCheckService.judgeSubjective(hwId, uId, examId, score);
		SmartMap<String, Boolean> rel = new SmartMap<String, Boolean>();
		rel.put("success", relsult);
		return new Gson().toJson(rel);
	}

	/**
	 * 教师发试题解析
	 *
	 * @param hwId 作业对象ID
	 * @param hwExamId 作业试题映射ID
	 * @param uId 学生对象ID 
	 * @param resolve 解析内容
	 * @return 
	 */
	@RequestMapping(value = "/check/release", method = RequestMethod.POST)
	@ResponseBody
	public String releaseResolve(@RequestParam("hwId") long hwId, @RequestParam("hwExamId") long hwExamId,
			@RequestParam("resolve") String resolve) {
		SmartMap<String, Object> relsult = teacherCheckService.releaseResolve(hwId, hwExamId, resolve);
		return new Gson().toJson(relsult);
	}

	/**
	 * 作业批阅完成
	 *
	 * @param hwId 作业对象Id
	 * @param act 操作类型（null/1：引用题库; 2：引用图片）
	 * @return 跳转到作业预览页面
	 * @throws IOException 
	 */
	@RequestMapping("/check/finish")
	@ResponseBody
	public String finish(@RequestParam("hwId") long hwId) throws IOException {
		//每个学生的作业的subjectiveRate(主观题正答率)
		teacherCheckService.finishStudentStat(hwId);
		//更新作业的avgScore(主观题平均正答率), status=3（已判）
		teacherCheckService.finishHomwork(hwId);
		//向学生发送消息提醒（提醒查看成绩）
		teacherCheckService.sendMsgToStudent(hwId);

		//跳转到预览页面
		StringBuilder desUrl = new StringBuilder().append("/check/view/").append(hwId).append(".html");
		//inv.getResponse().sendRedirect(desUrl.toString());
		return new Gson().toJson("{\"success\":true, \"url\":\"" + desUrl + "\"}");
	}
}
