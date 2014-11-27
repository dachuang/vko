/**
 * IStudentHome.java
 * cn.vko.zuoye.service.hessian.inter
 * Copyright (c) 2014, 北京微课创景教育科技有限公司版权所有.
*/

package cn.vko.zuoye.service.hessian.inter;

import cn.vko.zuoye.entity.Homework;

/**
 * <p>
 * @author   宋汝波
 * @Date	 2014-7-26 	 
 */
public interface IStudentHome {
	public String selectHomeWork(int page, int pageSize, Long subjectId, Integer status, long userId);

	public String selectMyFalses(int page, int pageSize, Long subjectId, long userId);

	public String selectExamOPtion(Long examId);

	public Homework selectHandNum(Long hwId);

	public String getStudentSubjects(Long userId);

	public int delMyFalse(long fid);

	public int selectUnHomework(long userId);
}
