/**
 * TeacherHomeServiceImpl.java
 * cn.vko.zuoye.service.impl
 * Copyright (c) 2014, 北京微课创景教育科技有限公司版权所有.
*/

package cn.vko.zuoye.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.vko.cache.dao.mybatis.Page;
import cn.vko.zuoye.entity.ExamOptions;
import cn.vko.zuoye.entity.ExamResolve;
import cn.vko.zuoye.entity.MyFalse;
import cn.vko.zuoye.entity.StudentHomework;
import cn.vko.zuoye.keyvalue.SysCodeType;
import cn.vko.zuoye.service.BaseService;
import cn.vko.zuoye.service.StudentDoHWService;
import cn.vko.zuoye.service.StudentHomeService;

import com.vko.core.common.util.SmartMap;

@Service
public class StudentHomeServiceImpl extends BaseService implements StudentHomeService {

	@Autowired
	private StudentDoHWService studentDoHWService;

	@Override
	public List<StudentHomework> selectHomeWork(Page page, Long studentId, Long subjectId, Integer status) {

		if (studentId < 1L) {
			return null;
		}

		List<StudentHomework> list = studentHomeworkMapper.selectMyHomeWork(page, studentId, subjectId, status);

		return list;
	}

	@Override
	public List<MyFalse> selectMyFalses(Page page, Long studentId, Long subjectId) {

		List<MyFalse> list = myFalsemapper.selectMyFalse(page, studentId, subjectId);
		for (MyFalse m : list) {
			SmartMap<String, Object> exam = tikuMapper.getExamContent(m.getExamsId());
			Map<String, Object> resolves = new HashMap<String, Object>();

			List<ExamResolve> resolveMap = examResolveMapper.selectResolveByExamId(m.getExamsId());
			resolves.put("k2List", studentDoHWService.getExamsAnswerK2(exam.getLong("id")));
			resolves.put("resolvesList", studentDoHWService.getExamsResolve(exam.getLong("id")));
			resolves.put("teacherResolves", resolveMap);

			m.setExamContent(exam.getString("content"));
			m.setResolve(resolves);
		}
		return list;

	}

	@Override
	public List<ExamOptions> selectExamOPtion(Long examId) {
		List<ExamOptions> list = tikuMapper.selectExamOPtions(examId);
		return list;
	}

	@Override
	public List<SmartMap<String, Object>> getStudentSubjects(Long userId) {
		SmartMap<String, Object> map = vkoMapper.getTeacherDetail(userId);
		if (map == null) {
			return null;
		}
		Integer grade = map.getInteger("grade");
		Integer xueDuanId = getXueDuan(grade);

		List<SmartMap<String, Object>> subjects = vkoMapper
				.selectSysCodeByParent(SysCodeType.SUBJECT.type(), xueDuanId);

		return subjects;
	}

	@Override
	public int delMyFalse(long fid) {
		return myFalsemapper.delMyfalse(fid);

	}

	@Override
	public int selectUnHomework(long userId) {
		return studentHomeworkMapper.selectUnHomework(userId);
	}
}
