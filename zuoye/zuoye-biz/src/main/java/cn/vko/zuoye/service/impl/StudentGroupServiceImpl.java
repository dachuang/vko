/**
 * TeacherHomeServiceImpl.java
 * cn.vko.zuoye.service.impl
 * Copyright (c) 2014, 北京微课创景教育科技有限公司版权所有.
*/

package cn.vko.zuoye.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import cn.vko.zuoye.service.BaseService;
import cn.vko.zuoye.service.StudentGroupService;

import com.vko.core.common.util.SmartMap;

@Service
public class StudentGroupServiceImpl extends BaseService implements StudentGroupService {

	@Override
	public List<SmartMap<String, Object>> selectGroupNameByIds(List<Long> id) {

		return groupMapper.selectGroupNameByIds(id);

	}

	@Override
	public List<Long> selectGroupsIdByTeacherId(Long teacherId) {

		List<Long> groups = groupMapper.selectGroupsIdByTeacherId(teacherId);
		List<SmartMap<String, Object>> aaa = groupMapper.selectGroupNameByIds(groups);

		System.out.println(aaa);
		return groups;

	}

	@Override
	public List<SmartMap> selectStudentByGroupId(Long id) {

		// TODO Auto-generated method stub
		return null;

	}
}
