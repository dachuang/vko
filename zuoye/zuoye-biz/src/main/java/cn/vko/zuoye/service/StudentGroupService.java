/**
 * StudentGroupService.java
 * cn.vko.zuoye.service
 * Copyright (c) 2014, 北京微课创景教育科技有限公司版权所有.
*/

package cn.vko.zuoye.service;

import java.util.List;

import com.vko.core.common.util.SmartMap;

/**
 * TODO(这里用一句话描述这个类的作用)
 * <p>
 * TODO(这里描述这个类补充说明 – 可选)
 * @author   宋汝波
 * @Date	 2014-7-11 	 
 */
public interface StudentGroupService {

	/**
	 * 根据群组的id得到群组的名称
	 * <p>
	 *
	 * @param id
	*/
	public List<SmartMap<String, Object>> selectGroupNameByIds(List<Long> id);

	/**
	 * 根据教师id得到教师所在的所有群组的id
	 * <p>
	 *
	 * @param teacherId
	*/
	public List<Long> selectGroupsIdByTeacherId(Long teacherId);

	/**
	 * 根据组id得到所有组成员学生id
	 * <p>
	 *
	 * @param id
	*/
	public List<SmartMap> selectStudentByGroupId(Long id);
}
