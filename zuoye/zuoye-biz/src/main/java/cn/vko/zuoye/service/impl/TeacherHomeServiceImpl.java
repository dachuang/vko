/**
 * TeacherHomeServiceImpl.java
 * cn.vko.zuoye.service.impl
 * Copyright (c) 2014, 北京微课创景教育科技有限公司版权所有.
*/

package cn.vko.zuoye.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import cn.vko.cache.dao.mybatis.Page;
import cn.vko.zuoye.entity.Homework;
import cn.vko.zuoye.service.BaseService;
import cn.vko.zuoye.service.TeacherHomeService;

import com.vko.core.common.util.SmartMap;

@Service
public class TeacherHomeServiceImpl extends BaseService implements TeacherHomeService {

	@Override
	public boolean checkTeacher(long userId) {
		SmartMap<String, Object> map = vkoMapper.getTeacherDetail(userId);
		if (map == null) {
			return false;
		}
		return map.getBoolean("isTeacher");
	}

	@Override
	public List<Homework> selectGroupHomeWork(Page page, Long groupId, Long subjectId, Integer status) {

		List<Homework> list = homeworkMapper.selectHomeWork(page, groupId, subjectId, status);

		return list;
	}

	@Override
	public List<Homework> selectHomeWork(Page page, Long teacherId, Long groupId, Integer status) {

		List<Homework> list = homeworkMapper.selectTeacherHomeWork(page, teacherId, groupId, status);

		return list;

	}

	@Override
	public Homework selectHomeWork(Long hwId) {

		Homework hw = homeworkMapper.selectHomeWorkForHand(hwId);
		return hw;

	}

	@Override
	public int selectUncheckNum(Long teacherId) {

		// TODO Auto-generated method stub
		return 0;

	}

}
