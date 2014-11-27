package cn.vko.zuoye.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.vko.zuoye.entity.ExamOptions;

public interface ExamOptionsMapper {
	int deleteByPrimaryKey(Long id);

	int insert(List<ExamOptions> record);

	int insertSelective(ExamOptions record);

	ExamOptions selectByPrimaryKey(Long id);

	/**
	 * 查询试题选项的统计信息
	 * <p>
	 *
	 * @param hwId 作业Id
	 * @return TODO
	 */
	List<ExamOptions> selectExamOptions(@Param("hwId") long hwId);

	int updateByPrimaryKeySelective(ExamOptions record);

	int updateByPrimaryKey(ExamOptions record);

}