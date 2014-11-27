/**
 * StudentHomeService.java
 * cn.vko.zuoye.service
 * Copyright (c) 2014, 北京微课创景教育科技有限公司版权所有.
*/

package cn.vko.zuoye.service;

import java.util.List;

import cn.vko.cache.dao.mybatis.Page;
import cn.vko.zuoye.entity.ExamOptions;
import cn.vko.zuoye.entity.MyFalse;
import cn.vko.zuoye.entity.StudentHomework;

import com.vko.core.common.util.SmartMap;

/**
 * TODO(这里用一句话描述这个类的作用)
 * <p>
 * TODO(这里描述这个类补充说明 – 可选)
 * @author   宋汝波
 * @Date	 2014-7-11 	 
 */
public interface StudentHomeService {

	public List<StudentHomework> selectHomeWork(Page page, Long studentId, Long subjectId, Integer status);

	public List<MyFalse> selectMyFalses(Page page, Long studentId, Long subjectId);

	public List<ExamOptions> selectExamOPtion(Long examId);

	public List<SmartMap<String, Object>> getStudentSubjects(Long userId);

	public int delMyFalse(long fid);

	public int selectUnHomework(long userId);

}
