package cn.vko.zuoye.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.vko.cache.annotation.ExpireObjectCache;

import com.vko.core.common.util.SmartMap;

/**
 * 调用数据库中其他表
 * <p>
 * @author   宋汝波
 * @Date	 2014-7-14
 */
public interface VkoMapper {

	/**
	 * user_detail表中学科和学段,老师所属的
	 * <p>
	 *
	 * @param teacherid
	*/
	@ExpireObjectCache(expire = 7 * 24 * 3600)
	public SmartMap<String, Object> getTeacherDetail(@Param("teacherId") long teacherid);

	/**
	 * 查询web_user表
	 * <p>
	 *
	 * @param teacherid
	*/
	@ExpireObjectCache(expire = 7 * 24 * 3600, compress = true)
	public SmartMap<String, Object> getTeacherInfo(@Param("teacherId") long teacherid);

	public List<SmartMap<String, Object>> selectMobileByUserId(@Param("ids") List<Long> userIds);

	@ExpireObjectCache(expire = 7 * 24 * 3600, compress = true)
	public List<SmartMap<String, Object>> selectSysCodeByType(int type);

	@ExpireObjectCache(expire = 7 * 24 * 3600, compress = true)
	public List<SmartMap<String, Object>> selectByPhaseSubjectType(@Param("phaseId") long phaseId,
			@Param("subjectId") long subjectId, @Param("type") int type);

	@ExpireObjectCache(expire = 7 * 24 * 3600, compress = true)
	public SmartMap<String, Object> selectSysCodeById(Long id);

	@ExpireObjectCache(expire = 7 * 24 * 3600, compress = true)
	public List<SmartMap<String, Object>> selectSysCodeByParentType(int type);

	@ExpireObjectCache(expire = 7 * 24 * 3600, compress = true)
	public List<SmartMap<String, Object>> selectSysCodeByParent(@Param("type") int type, @Param("pid") long parentId);

	@ExpireObjectCache(expire = 7 * 24 * 3600, compress = true)
	public List<Long> selectIdsByParentId(@Param("ids") String[] ids);

	@ExpireObjectCache(expire = 7 * 24 * 3600, compress = true)
	public List<SmartMap<String, Object>> selectSysCodeByIdType(@Param("ids") List<Long> ids, @Param("type") int type);

	/**
	 * 插入站内消息
	 * <p>
	 *
	 * @param data
	*/
	int insertMessage(@Param("lst") List<SmartMap<String, Object>> data);
}