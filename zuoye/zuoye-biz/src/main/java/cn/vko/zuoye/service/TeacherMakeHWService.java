/**
 * TeacherMakeHWService.java
 * cn.vko.zuoye.service
 * Copyright (c) 2014, 北京微课创景教育科技有限公司版权所有.
*/

package cn.vko.zuoye.service;

import java.util.List;
import java.util.Map;

import cn.vko.cache.dao.mybatis.Page;
import cn.vko.zuoye.entity.StudentHomework;

import com.vko.core.common.util.SmartMap;

/**
 * 教师发作业服务类接口
 * <p>
 * @author   宋汝波
 * @Date	 2014-7-14 	 
 */
public interface TeacherMakeHWService {

	/**
	 *  result.put("xueDuanId", xuDuanId);
		result.put("subjectId", subject);
	 * <p>
	 *
	*/
	public Map<String, Integer> getTeacherInfo();

	/**
	 * 分页查询
	 * <p>
	 *
	 * @param page
	 * @param teacherId
	 * @param k1
	 * @param k2
	 * @param k3
	 * @param type
	 * @param sort
	*/
	public Page getSearchExam(Page page, Integer xuDuanId, Integer subjectid, String k1, String k2, String k3,
			String type, int sort);

	/**
	 * 可供查询的选项信息
	 * <p>
	 *  result.put("typeList", typeList);
		result.put("k1List", k1List);
		result.put("k2List", k2List);
		result.put("k3List", k3List);
	 * @param k1
	 * @param k2
	*/
	public Map<String, Object> getSearchOptions(Integer xueDuanId, Integer subjectId, String k1, String k2);

	//	public Map<String, List<Long>> assembleHomework(List<Long> examIds);

	public List<SmartMap<String, Object>> getAssembleExamContent(List<Long> ids);

	//	public List<SmartMap<String, Object>> nextAssembleHomework(List<Long> ids);
	public boolean saveTikuHomework(List<Long> examIds, SmartMap<String, Object> param) throws Exception;

	public List<SmartMap<String, Object>> getExamsContent(List<Long> ids);

	public List<SmartMap<String, Object>> getMessages(List<StudentHomework> records, String content);

	public void saveSendHomework(List<Long> needDistributIds) throws Exception;

	public void saveReceiveHomework(List<Long> needDistributIds);

	public boolean saveImageHomework(SmartMap<String, Object> param) throws Exception;

	/**
	 * 获取老师所在的班级群
	 * <p>
	 *
	 * @param teacherId
	*/
	public Map<Long, String> getGroupsByTeacherId(long teacherId);

}
