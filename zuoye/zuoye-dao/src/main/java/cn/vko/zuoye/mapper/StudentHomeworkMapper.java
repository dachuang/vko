package cn.vko.zuoye.mapper;

import java.math.BigDecimal;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.vko.cache.dao.mybatis.Page;
import cn.vko.zuoye.entity.StudentHomework;

import com.vko.core.common.util.SmartMap;

public interface StudentHomeworkMapper {
	int deleteByPrimaryKey(Long id);

	int insert(List<StudentHomework> record);

	int insertSelective(StudentHomework record);

	StudentHomework selectByPrimaryKey(Long id);

	Double selectAvgElapseByHWId(@Param("hwId") long hwId);

	SmartMap<String, Object> selectHandedNumByHWId(@Param("hwId") long hwId);

	/**
	 * 统计作业学生的主观题平均正答率
	 * <p>
	 *
	 * @param hwId 作业Id
	 * @return 主观题平均正答率
	 */
	BigDecimal selectAvgRate(@Param("hwId") long hwId);

	/**
	 * 对此次作业学生成绩统计
	 * <p>
	 *
	 * @param hwId 作业Id
	 * @return 此次作业学生成绩统计数据
	 */
	List<SmartMap<String, Object>> selectStat(@Param("hwId") long hwId);

	/**
	 * 统计此次作业所有学生集合
	 * <p>
	 *
	 * @param hwId 作业Id
	 * @return 学生集合
	 */
	List<StudentHomework> selectStudent(@Param("hwId") long hwId);

	int updateByPrimaryKeySelective(StudentHomework record);

	int updateByPrimaryKey(StudentHomework record);

	int updateSubjectiveRate(@Param("hwId") long hwId);

	/**
	 * 
	 * 查询我的作业
	 * <p>
	 *
	 * @param page 分页
	 * @param status 状态 1未交,2已交
	 * @return 我的作业列表
	 */
	List<StudentHomework> selectMyHomeWork(Page page, @Param("stdId") long stdId, @Param("subjectId") long subjectId,
			@Param("status") int status);

	/**
	 * 
	 * 查询未完成的作业数量
	 * <p>
	 * TODO(这里描述这个方法详情– 可选)
	 *
	 * @return TODO(这里描述每个参数,如果有返回值描述返回值,如果有异常描述异常)
	 */
	int selectUnHomework(@Param("stdId") long stdId);

}