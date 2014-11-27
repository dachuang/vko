/**
 * TeacherGroupService.java
 * cn.vko.zuoye.service
 * Copyright (c) 2014, 北京微课创景教育科技有限公司版权所有.
*/

package cn.vko.zuoye.service;

import com.vko.core.common.util.SmartMap;

/**
 * 教师作业统计服务接口
 * <p>
 * @author   杨闯
 * @Date	 2014-7-17 	 
 */
public interface TeacherStatService {

	/**
	 * 加载作业统计数据
	 * <p>
	 *
	 * @param hwId 作业Id
	 * @return 返回作业统计数据
	 */
	SmartMap<String, Object> stat(long hwId);

}
