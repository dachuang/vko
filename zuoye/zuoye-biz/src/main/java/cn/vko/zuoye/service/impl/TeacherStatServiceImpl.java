/**
 * TeacherStatServiceImpl.java
 * cn.vko.zuoye.service.impl
 * Copyright (c) 2014, 北京微课创景教育科技有限公司版权所有.
*/

package cn.vko.zuoye.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import cn.vko.zuoye.service.BaseService;
import cn.vko.zuoye.service.TeacherStatService;

import com.vko.core.common.exception.VkoServiceException;
import com.vko.core.common.util.SmartMap;

/**
 * 教师作业统计服务接口实现类
 * @author   杨闯
 * @Date	 2014-7-14 	 
 */
@Service
public class TeacherStatServiceImpl extends BaseService implements TeacherStatService {

	/**
	 * @see cn.vko.zuoye.service.TeacherStatService#stat(long)
	 */
	@Override
	public SmartMap<String, Object> stat(long hwId) {
		if (hwId < 0) {
			throw new VkoServiceException("作业不存在：" + hwId);
		}
		//对此次作业的整体统计
		SmartMap<String, Object> hw = homeworkMapper.selectStat(hwId);
		//耗时--》11小时20分钟
		hw.put("useTime", fillUseTime(hw.getint("elapse")));
		//对此次作业学生成绩统计
		List<SmartMap<String, Object>> list = studentHomeworkMapper.selectStat(hwId);
		for (int i = 0; i < list.size(); i++) {
			list.get(i).put("useTime", fillUseTime(list.get(i).getint("elapse")));
		}
		SmartMap<String, Object> retVal = new SmartMap<String, Object>();
		retVal.put("hwStat", hw);
		retVal.put("stuStat", list);
		return retVal;
	}

	//填充数据: 耗时转化为字符串显示
	public static String fillUseTime(Integer elapse) {
		StringBuilder str = new StringBuilder();
		if (elapse == null) {
			return "0分钟";
		}
		long time = elapse;
		if (time <= 0) {
			return "0分钟";
		}
		long hour = time / 3600;
		long minute = time % 3600 / 60;
		long second = time % 3600 % 60 % 60;
		if (hour > 0) {
			str.append(hour);
			str.append("小时");
		}
		if (minute > 0) {
			str.append(minute);
			str.append("分钟");
		}
		if (second > 0) {
			str.append(second);
			str.append("秒");
		}
		if (str.length() == 0) {
			str.append("0分钟");
		}
		return str.toString();
	}
}
