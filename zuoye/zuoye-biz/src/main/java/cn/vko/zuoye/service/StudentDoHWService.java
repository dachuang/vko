/**
 * StudentDoHWService.java
 * cn.vko.zuoye.service
 * Copyright (c) 2014, 北京微课创景教育科技有限公司版权所有.
*/

package cn.vko.zuoye.service;

import java.util.List;
import java.util.Map;

import cn.vko.zuoye.entity.Homework;
import cn.vko.zuoye.entity.HwAnswerCard;
import cn.vko.zuoye.entity.StudentHomework;

import com.vko.core.common.util.SmartMap;

/**
 * 做作业接口
 * <p>
 * @author   宋汝波
 * @Date	 2014-7-22 	 
 */
public interface StudentDoHWService {
	public Map<String, Object> getData4DoHW(Long studentHWId);

	public Map<String, Object> getData4ViewHW(Long studentHWId);

	public List<HwAnswerCard> getDataFromAnswerCard(Long hwId);

	public List<SmartMap<String, Object>> getDataFromTK(Long hwId);

	public List<SmartMap<String, Object>> getExamsAnswerK2(long examId);

	public List<SmartMap<String, Object>> getExamsResolve(long examId);

	public int saveBegTimes(Long hwId, Long examId);

	public int saveMyFalse(Long hwId, Long examId);

	public void updateStudentHWStart(Long studentHWId);

	public void updateStudentHWEnd(Long studentHWId);

	public void saveHWAnswer4TK(Map<String, String[]> answers, Long studentHWId);

	public void saveHWAnswer4Shouji(Map<String, String[]> answers, Long studentHWId);

	public int updateCommentTimes(Long hwId);

	public Homework getHomeworkInfo(Long hwId, Long studentHWId);

	public StudentHomework getStHomeworkInfo(Long studentHWId);

}
