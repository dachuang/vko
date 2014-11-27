package cn.vko.zuoye.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.vko.cache.annotation.ExpireObjectCache;

import com.vko.core.common.util.SmartMap;

/**
 * 数据库中针对群组的访问
 * <p>
 * @author   宋汝波
 * @Date	 2014-7-14
 */
public interface GroupMapper {

	/**
	 * 根据群组的id得到群组的名称
	 * <p>
	 *
	 * @param id
	*/
	@ExpireObjectCache(expire = 24 * 3600, compress = true)
	public List<SmartMap<String, Object>> selectGroupNameByIds(@Param("ids") List<Long> id);

	/**
	 * 根据教师id得到教师所在的所有群组的id
	 * <p>
	 *
	 * @param teacherId
	*/
	public List<Long> selectGroupsIdByTeacherId(@Param("teacherId") Long teacherId);

	/**
	 * 根据组id得到所有组成员学生id
	 * <p>
	 *
	 * @param id
	*/
	public List<SmartMap<String, Object>> selectStudentByGroupId(Long id);
}