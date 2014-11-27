package cn.vko.zuoye.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.vko.cache.dao.mybatis.Page;
import cn.vko.zuoye.entity.Homework;

import com.vko.core.common.util.SmartMap;

public interface HomeworkMapper {
	int deleteByPrimaryKey(Long id);

	int insert(Homework record);

	int insertSelective(Homework record);

	Homework selectByPrimaryKey(Long id);

	/**
	 * 根据状态查询分发作业
	 * <p>
	 *
	*/
	List<Homework> selectDistributionHW(@Param("status") int status);

	/**
	 * 
	 * 按照群组ID与状态查询作业列表
	 * <p>
	 * TODO(这里描述这个方法详情– 可选)
	 *
	 * @param page 分页信息
	 * @param groupId 群组ID
	 * @param status 状态 1已发,2待判,3已判
	 * @return 作业列表
	 */
	List<Homework> selectHomeWork(Page page, @Param("groupId") long groupId, @Param("subjectId") long subjectId,
			@Param("status") Integer status);

	/**
	 * 
	 * 按照教师ID、群组ID与状态查询作业列表
	 * <p>
	 * TODO(这里描述这个方法详情– 可选)
	 *
	 * @param page 分页信息
	 * @param teacherId 教师ID
	 * @param groupId 群组ID
	 * @param status 状态 1已发,2待判,3已判
	 * @return 作业列表
	 */
	List<Homework> selectTeacherHomeWork(Page page, @Param("teacherId") Long teacherId, @Param("groupId") Long groupId,
			@Param("status") Integer status);

	/**
	 * 
	 * 查询已发作业和待判作业的数量
	 * <p>
	 *
	 * @param groupId 群组ID
	 * @return 作业数量
	 */
	int selectUnPanNum(Long groupId);

	/**
	 * 获取需要分发学生作业的作业数据
	 * <p>
	 *
	 * @param id
	*/
	Homework selectHWForDistribution(Long id);

	/**
	 * 查询作业供做作业用
	 * <p>
	 *
	 * @param id
	*/
	Homework selectHWForDoHW(Long id);

	/**
	 * 查询作业是否有解析
	 *
	 * @param id 作业Id
	 * @return 是否有解析
	 */
	Boolean selectHasResolveById(@Param("id") long id);

	/**
	 * 对此次作业的整体统计
	 * <p>
	 *
	 * @param id 作业Id
	 * @return 此次作业的整体统计数据
	 */
	SmartMap<String, Object> selectStat(@Param("id") long id);

	/**
	 * 讲作业状态更改
	 * <p>
	 * @param id
	*/
	int updateHWDistribution(@Param("id") Long id, @Param("status") int status);

	int updateByPrimaryKeySelective(Homework record);

	int updateByPrimaryKey(Homework record);

	/**
	 * 更新作业是否有解析
	 * <p>
	 *
	 * @param id 作业Id
	 * @param hasResolve 是否有解析
	 * @return 更新数量
	 */
	int updateHasResolveById(@Param("id") long id, @Param("hasResolve") boolean hasResolve);

	/**
	 * 更新作业的avgScore(主观题平均正答率), status=3（已判）
	 * <p>
	 *
	 * @param hwId 作业ID
	 * @param status 作业状态
	 * @return 更新数量
	 */
	int updateAvgScoreAndStatus(@Param("id") long id, @Param("status") int status);

	/**
	 * 更新评论次数
	 * <p>
	 *
	 * @param id
	*/
	int updateCommentTimes(Long id);

	/**
	 * 
	 * TODO(这里用一句话描述这个方法的作用)
	 * <p>
	 * TODO(这里描述这个方法详情– 可选)
	 *
	 * @param hwId
	 * @return TODO(这里描述每个参数,如果有返回值描述返回值,如果有异常描述异常)
	 */
	Homework selectHomeWorkForHand(@Param("id") long hwId);
}