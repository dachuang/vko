package cn.vko.zuoye.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.vko.zuoye.entity.StudentOptions;

public interface StudentOptionsMapper {
	int deleteByPrimaryKey(Long id);

	int insert(List<StudentOptions> record);

	int insertSelective(StudentOptions record);

	StudentOptions selectByPrimaryKey(Long id);

	/**
	 * 选项的选择人列表
	 *
	 * @param statId 选项统计的Id
	 * @return 选项的选择人列表
	 */
	List<StudentOptions> selectStudentById(@Param("statId") Long statId);

	int updateByPrimaryKeySelective(StudentOptions record);

	int updateByPrimaryKey(StudentOptions record);
}