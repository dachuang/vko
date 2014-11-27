/**
 * StudentHome.java
 * cn.vko.zuoye.service.hessian
 * Copyright (c) 2014, 北京微课创景教育科技有限公司版权所有.
*/

package cn.vko.zuoye.service.hessian;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.vko.cache.dao.mybatis.Page;
import cn.vko.zuoye.entity.ExamOptions;
import cn.vko.zuoye.entity.Homework;
import cn.vko.zuoye.entity.MyFalse;
import cn.vko.zuoye.entity.StudentHomework;
import cn.vko.zuoye.service.StudentHomeService;
import cn.vko.zuoye.service.TeacherHomeService;
import cn.vko.zuoye.service.hessian.inter.IStudentHome;

import com.alibaba.fastjson.JSON;
import com.vko.core.common.util.ApplicationUtil;
import com.vko.core.common.util.SmartMap;

/**
 * <p>
 * @author   宋汝波
 * @Date	 2014-7-26 	 
 */
public class StudentHome implements IStudentHome {

	@Override
	public String selectHomeWork(int page, int pageSize, Long subjectId, Integer status, long userId) {
		Page pageObj = new Page(page, pageSize);
		List<StudentHomework> list = ApplicationUtil.getBean(StudentHomeService.class).selectHomeWork(pageObj, userId,
				subjectId, status);
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("list", list);
		result.put("page", pageObj);
		return JSON.toJSONString(result);

	}

	@Override
	public String selectMyFalses(int page, int pageSize, Long subjectId, long userId) {
		Page pageObj = new Page(page, pageSize);
		List<MyFalse> list = ApplicationUtil.getBean(StudentHomeService.class).selectMyFalses(pageObj, userId,
				subjectId);
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("list", list);
		result.put("page", pageObj);
		return JSON.toJSONString(result);

	}

	@Override
	public String selectExamOPtion(Long examId) {
		List<ExamOptions> list = ApplicationUtil.getBean(StudentHomeService.class).selectExamOPtion(examId);
		return JSON.toJSONString(list);
	}

	@Override
	public Homework selectHandNum(Long hwId) {
		Homework hw = ApplicationUtil.getBean(TeacherHomeService.class).selectHomeWork(hwId);
		return hw;

	}

	@Override
	public String getStudentSubjects(Long userId) {

		List<SmartMap<String, Object>> list = ApplicationUtil.getBean(StudentHomeService.class).getStudentSubjects(
				userId);
		return JSON.toJSONString(list);

	}

	@Override
	public int delMyFalse(long fid) {
		return ApplicationUtil.getBean(StudentHomeService.class).delMyFalse(fid);
	}

	@Override
	public int selectUnHomework(long userId) {
		return ApplicationUtil.getBean(StudentHomeService.class).selectUnHomework(userId);

	}
}
