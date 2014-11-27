package cn.vko.zuoye.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import cn.vko.cache.annotation.ExpireObjectCache;
import cn.vko.cache.dao.mybatis.Page;
import cn.vko.zuoye.entity.ExamOptions;

import com.vko.core.common.util.SmartMap;

/**
 * 作业系统调用题库中的表的操作
 * <p>
 * @author   宋汝波
 * @Date	 2014-7-14
 */
public interface TikuMapper {

	/**
	 * 根据id返回试题类型,用于组合作业
	 * <p>
	 *
	 * @param examIds
	*/
	@ExpireObjectCache(expire = 7 * 24 * 3600, compress = true)
	public List<SmartMap<String, Object>> getExamTypeById(@Param("examIds") List<Long> ids);

	/**
	 * 查询试题表,为发作业做准备
	 * <p>
	 *
	 * @param page 分页条件
	 * @param xuDuanId 学段id
	 * @param subjectId 学科id
	 * @param k1 一级知识点
	 * @param k2 二级知识点
	 * @param k3 三级知识点
	 * @param type 题型
	 * @param sort 排序条件
	 * @return 分页数据
	 *  @Param("xueDuanId") long xueDuanId, @Param("subjectId") long subjectId,
			@Param("k1s") String[] k1, @Param("k2s") String[] k2, @Param("k3s") String[] k3,
			@Param("types") String[] type, @Param("sort") int sort
	*/
	@ExpireObjectCache(expire = 24 * 3600, compress = true)
	public Page getPageFromTkExam(Page page, @Param("param") Map<String, Object> param);

	/**
	 * 查询试题内容
	 * <p>
	 *
	 * @param ids
	*/
	@ExpireObjectCache(expire = 7 * 24 * 3600, compress = true)
	public List<SmartMap<String, Object>> getExamsContent(@Param("examIds") List<Long> ids);

	/**
	 * 根据试题id得到试题是主观还是客观
	 * <p>
	 *
	 * @param ids
	*/
	@ExpireObjectCache(expire = 7 * 24 * 3600, compress = true)
	public List<SmartMap<String, Object>> getExamsObjective(@Param("examIds") List<Long> ids);

	/**
	 * 根据试题ids返回试题的答案
	 * <p>
	 *
	 * @param ids
	*/
	@ExpireObjectCache(expire = 7 * 24 * 3600, compress = true)
	public List<SmartMap<String, Object>> getExamsAnswers(@Param("examIds") List<Long> ids);

	/**
	 * 
	 *根据ID查询试题内容
	 */
	@ExpireObjectCache(expire = 7 * 24 * 3600, compress = true)
	public SmartMap<String, Object> getExamContent(@Param("examId") Long examId);

	/**
	 * 更新试题引用次数
	 * <p>
	 *
	 * @param ids
	*/
	public int updateReferenceNumByExamIds(@Param("examIds") List<Long> ids);

	public Integer selectIsAddedMyFalse(@Param("studentId") Long studentId, @Param("examId") Long examId);

	public int insertMyFlase(SmartMap<String, Object> data);

	@ExpireObjectCache(expire = 7 * 24 * 3600, compress = true)
	public SmartMap<String, Object> getExamBaseInfoById(@Param("examId") Long examId);

	@ExpireObjectCache(expire = 7 * 24 * 3600, compress = true)
	public List<SmartMap<String, Object>> selectResolveByExamId(Long examId);

	@ExpireObjectCache(expire = 7 * 24 * 3600, compress = true)
	public SmartMap<String, Object> selectK2ByExamId(Long examId);

	public List<ExamOptions> selectExamOPtions(@Param("examId") Long examId);

}