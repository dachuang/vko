package cn.vko.zuoye.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.vko.cache.annotation.ExpireObjectCache;
import cn.vko.zuoye.entity.HwExam;

public interface HwExamMapper {

	/**
	 * 根据作业id返回分发学生作业所需数据
	 * <p>
	 *
	 * @param id
	*/
	List<HwExam> selectExamsByHWId(Long hwId);

	/**
	 * 查询，作业的所有试题
	 * <p>
	 *
	 * @param hwId 作业Id
	 * @return 作业的所有试题
	 */
	@ExpireObjectCache(expire = 24 * 3600)
	List<HwExam> selectAllExamByHwId(@Param("hwId") long hwId);

	/**
	 * 查询试题统计信息
	 * <p>
	 *
	 * @param hwId 作业Id
	 * @return 试题
	 */
	List<HwExam> selectExam(@Param("hwId") long hwId);

	int updateBegNum(@Param("hwId") Long hwId, @Param("examId") Long examId);

	/**
	 * 根据作业和试题id更新客观题统计信息
	 * <p>
	 *
	 * @param exam
	*/
	int updateObjectiveStat(HwExam exam);

	int insert(List<HwExam> record);

	/**
	 * 返回求解析次数
	 * <p>
	 *
	 * @param hwId
	 * @param examId
	*/
	int selectBegNums(@Param("hwId") Long hwId, @Param("examId") Long examId);

	/**
	 * 更新此题的已批阅人数，未批阅人数
	 * <p>
	 *
	 * @param hwId 作业Id
	 * @param eId 试题Id
	 * @return 更新数量
	 */
	int updateCheckSubjectiveNum(@Param("hwId") long hwId, @Param("eId") long eId);

}