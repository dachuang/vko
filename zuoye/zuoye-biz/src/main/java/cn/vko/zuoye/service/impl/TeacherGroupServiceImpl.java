/**
 * TeacherHomeServiceImpl.java
 * cn.vko.zuoye.service.impl
 * Copyright (c) 2014, 北京微课创景教育科技有限公司版权所有.
*/

package cn.vko.zuoye.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import cn.vko.zuoye.service.BaseService;
import cn.vko.zuoye.service.TeacherGroupService;

import com.vko.core.common.util.SmartMap;

@Service
public class TeacherGroupServiceImpl extends BaseService implements TeacherGroupService {

	@Override
	public List<SmartMap<String, Object>> selectGroupsByTeacherId(Long teacherId) {

		if (teacherId == null || teacherId < 0L) {
			return null;
		}

		List<Long> groupIds = groupMapper.selectGroupsIdByTeacherId(teacherId);
		if (groupIds == null || groupIds.size() == 0) {
			return null;
		}

		List<SmartMap<String, Object>> groups = groupMapper.selectGroupNameByIds(groupIds);

		return groups;

	}

}
