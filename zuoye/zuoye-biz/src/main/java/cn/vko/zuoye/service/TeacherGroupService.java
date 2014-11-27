/**
 * TeacherGroupService.java
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
public interface TeacherGroupService {

	/**
	 * 查询教师所在的群组
	 * <p>
	 *
	 * @param id
	*/
	public List<SmartMap<String, Object>> selectGroupsByTeacherId(Long teacherId);

}
