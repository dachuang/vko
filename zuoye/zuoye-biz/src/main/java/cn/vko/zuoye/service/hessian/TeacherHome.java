/**
 * TeacherHome.java
 * cn.vko.zuoye.service.hessian
 * Copyright (c) 2014, 北京微课创景教育科技有限公司版权所有.
*/

package cn.vko.zuoye.service.hessian;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.vko.cache.dao.mybatis.Page;
import cn.vko.zuoye.entity.Homework;
import cn.vko.zuoye.service.TeacherGroupService;
import cn.vko.zuoye.service.TeacherHomeService;
import cn.vko.zuoye.service.hessian.inter.ITeacherHome;

import com.alibaba.fastjson.JSON;
import com.vko.core.common.util.ApplicationUtil;
import com.vko.core.common.util.SmartMap;

/**
 * TODO(这里用一句话描述这个类的作用)
 * <p>
 * TODO(这里描述这个类补充说明 – 可选)
 * @author   pc
 * @Date	 2014-7-28 	 
 */
public class TeacherHome implements ITeacherHome {

	@Override
	public boolean checkTeacher(long userId) {

		return ApplicationUtil.getBean(TeacherHomeService.class).checkTeacher(userId);

	}

	@Override
	public String selectGroupHomeWork(int page, int pageSize, Long groupId, Long subjectId, Integer status) {

		Page pageObj = new Page(page, pageSize);

		List<Homework> list = ApplicationUtil.getBean(TeacherHomeService.class).selectGroupHomeWork(pageObj, groupId,
				subjectId, 0);
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("list", list);
		result.put("page", pageObj);
		return JSON.toJSONString(result);

	}

	@Override
	public String selectHomeWork(int page, int pageSize, Long teacherId, Long groupId, Integer status) {

		Page pageObj = new Page(page, pageSize);
		List<Homework> list = ApplicationUtil.getBean(TeacherHomeService.class).selectHomeWork(pageObj, teacherId,
				groupId, status);
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("list", list);
		result.put("page", pageObj);
		return JSON.toJSONString(result);

	}

	@Override
	public String selectteacherGroups(Long teacherId) {
		List<SmartMap<String, Object>> groups = ApplicationUtil.getBean(TeacherGroupService.class)
				.selectGroupsByTeacherId(teacherId);
		return JSON.toJSONString(groups);

	}

	@Override
	public int selectUncheckNum(Long teacherId) {

		// TODO Auto-generated method stub
		return 0;

	}
}
