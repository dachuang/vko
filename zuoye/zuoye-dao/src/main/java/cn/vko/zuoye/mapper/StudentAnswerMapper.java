package cn.vko.zuoye.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.vko.zuoye.entity.StudentAnswer;

public interface StudentAnswerMapper {

	/**
	 * 根据学生作业id查询所有客观题数据
	 * <p>
	 *
	 * @param stHWId
	*/
	List<StudentAnswer> selectObjectiveByStudentHWId(Long stHWId);

	List<StudentAnswer> selectAllByStudentHWId(Long stHWId);

	/**
	 * 根据作业id获取所有客观题数据
	 * <p>
	 *
	 * @param hwId
	*/
	List<StudentAnswer> selectObjectiveByHWId(@Param("hwId") Long hwId, @Param("objective") boolean objective);

	/**
	 * 更新客观题对错
	 * <p>
	 *
	 * @param ids
	 * @param isRight
	*/
	int updateObjectiveIsRight(@Param("ids") List<Long> ids, @Param("isRight") Integer isRight);

	int deleteByPrimaryKey(Long id);

	int insert(List<StudentAnswer> record);

	int insertSelective(StudentAnswer record);

	/**
	 * 查看学生主观题的答案
	 *
	 * @param hwId 作业Id
	 * @param uId 学生Id
	 * @param eId 试题Id
	 * @return 学生答题信息
	 */
	StudentAnswer selectAnswerByStudetId(@Param("hwId") long hwId, @Param("uId") long uId, @Param("eId") long eId);

	/**
	 * 主观题的学生列表
	 * <p>
	 *
	 * @param hwId 作业Id
	 * @param ids 主观题Ids
	 * @return TODO
	 */
	List<StudentAnswer> selectStudentAnswer(@Param("hwId") long hwId, @Param("ids") List<Long> ids);

	/**
	 * 查询，默认试题的答案与分数
	 * <p>
	 * 显示指定一个学生的答案与分数
	 *
	 * @param hwId 作业Id
	 * @param uId 学生Id
	 * @param ids 主观题Ids
	 * @return 默认试题的答案与分数
	 */
	List<StudentAnswer> selectDefaultStudentAnswer(@Param("hwId") long hwId, @Param("uId") long uId,
			@Param("ids") List<Long> ids);

	int updateByPrimaryKeySelective(StudentAnswer record);

	int updateByPrimaryKeyWithBLOBs(StudentAnswer record);

	int updateByPrimaryKey(StudentAnswer record);

	/**
	 * 更新主观题得分,并更新此题的批阅状态为已阅
	 * <p>
	 *
	 * @param hwId 作业Id
	 * @param uId 学生Id
	 * @param eId 试题Id
	 * @param score 得分
	 */
	int updateScore(@Param("hwId") long hwId, @Param("uId") long uId, @Param("eId") long eId, @Param("score") int score);

}