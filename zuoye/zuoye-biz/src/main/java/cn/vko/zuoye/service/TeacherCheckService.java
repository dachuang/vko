/**
 * TeacherCheckService.java
 * cn.vko.zuoye.service
 * Copyright (c) 2014, 北京微课创景教育科技有限公司版权所有.
*/

package cn.vko.zuoye.service;

import java.util.List;
import java.util.Map;

import cn.vko.zuoye.entity.StudentAnswer;
import cn.vko.zuoye.entity.StudentOptions;

import com.vko.core.common.util.SmartMap;

/**
 * 审批作业服务接口
 * @author   杨闯
 * @Date	 2014-7-14 	 
 */
public interface TeacherCheckService {

	/**
	 * 教师批阅作业，数据加载接口
	 * <p>
	 *
	 * @param hwId 作业Id
	 */
	Map<String, Object> index(long hwId);

	/**
	 * 教师查看已发布的作业，数据加载接口
	 * <p>
	 *
	 * @param hwId 作业Id
	 */
	Map<String, Object> viewRelaseHw(long hwId);

	/**
	 * 加载学生主观题答案
	 *
	 * @param hwId 作业对象ID
	 * @param uId 学生对象ID
	 * @param eId 试题对象ID
	 * @return 学生主观题答案
	*/
	StudentAnswer selectAnswerByStudetId(long hwId, long uId, long eId);

	/**
	 * 查看试题的某个选项的选择人列表
	 *
	 * @param statId 选项统计id
	 * @return 选项的选择人列表
	 */
	List<StudentOptions> look(long statId);

	/**
	 * 审批主观题，设置得分
	 *
	 * @param hwId 作业对象ID
	 * @param eId 试题对象ID
	 * @param uId 学生对象ID 
	 * @param score 得分
	 * @return 下一个学生的主观题答案
	 */
	boolean judgeSubjective(long hwId, long uId, long eId, Integer score);

	/**
	 * 教师发试题解析
	 *
	 * @param hwId 作业对象ID
	 * @param hwExamId 试题对象ID
	 * @param resolve 解析内容
	 * @return 
	 */
	SmartMap<String, Object> releaseResolve(long hwId, long hwExamId, String resolve);

	/**
	 * 作业批阅完成(一)
	 * <p>
	 * 每个学生的作业的subjectiveRate(主观题正答率)
	 *
	 * @param hwId 作业对象ID
	 * @return 更新数量
	 */
	boolean finishStudentStat(long hwId);

	/**
	 * 作业批阅完成(二)
	 * <p>
	 * 更新作业的avgScore(主观题平均正答率), status=3（已判）
	 * 
	 * @param hwId 作业对象ID
	 * @return 更新数量
	 */
	boolean finishHomwork(long hwId);

	/**
	 * 向学生发送消息提醒（提醒查看成绩）
	 * <p>
	 * 1.发送短信提醒
	 * 2.发送站内信
	 * 3.发送App消息（TODO）
	 *
	 * @param hwId 作业Id
	 */
	void sendMsgToStudent(long hwId);
}
