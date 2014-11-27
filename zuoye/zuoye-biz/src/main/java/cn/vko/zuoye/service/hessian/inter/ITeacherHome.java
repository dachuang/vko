/**
 * ITeacherHome.java
 * cn.vko.zuoye.service.hessian.inter
 * Copyright (c) 2014, 北京微课创景教育科技有限公司版权所有.
*/

package cn.vko.zuoye.service.hessian.inter;

/**
 * TODO(这里用一句话描述这个类的作用)
 * <p>
 * TODO(这里描述这个类补充说明 – 可选)
 * @author   宋汝波
 * @Date	 2014-7-28 	 
 */
public interface ITeacherHome {

	/**
	 * 检查是否是教师
	 */
	public boolean checkTeacher(long userId);

	/**
	 * 
	 * 查询班级群作业
	 * <p>
	 *
	 * @return 作业列表
	 */
	public String selectGroupHomeWork(int page, int pageSize, Long groupId, Long subjectId, Integer status);

	/**
	 * 
	 * 查询教师作业作业
	 * <p>
	 *
	 * @return 作业列表
	 */
	public String selectHomeWork(int page, int pageSize, Long teacherId, Long groupId, Integer status);

	/**
	 * 查询老师所在的群组
	 * TODO(这里用一句话描述这个方法的作用)
	 * <p>
	 * TODO(这里描述这个方法详情– 可选)
	 *
	 * @param teacherId
	 * @return TODO(这里描述每个参数,如果有返回值描述返回值,如果有异常描述异常)
	 */
	public String selectteacherGroups(Long teacherId);

	public int selectUncheckNum(Long teacherId);
}
