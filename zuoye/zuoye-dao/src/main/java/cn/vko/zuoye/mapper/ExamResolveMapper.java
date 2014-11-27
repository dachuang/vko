package cn.vko.zuoye.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.vko.zuoye.entity.ExamResolve;

public interface ExamResolveMapper {
	int deleteByPrimaryKey(Long id);

	int insert(ExamResolve record);

	int insertSelective(ExamResolve record);

	ExamResolve selectByPrimaryKey(Long id);

	/**
	 * 查询，试题解析
	 * <p>
	 * 
	 * @param hwExamIds ids
	 * @return 试题解析
	 */
	List<ExamResolve> selectResolveByHwExamIds(@Param("hwExamIds") List<Long> hwExamIds);

	List<ExamResolve> selectResolveByExamId(@Param("examId") Long examId);

	int updateByPrimaryKeySelective(ExamResolve record);

	int updateByPrimaryKeyWithBLOBs(ExamResolve record);

	int updateByPrimaryKey(ExamResolve record);
}