package cn.vko.zuoye.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.vko.cache.dao.mybatis.Page;
import cn.vko.zuoye.entity.HwImages;

import com.vko.core.common.util.SmartMap;

public interface HwImagesMapper {
	int deleteByPrimaryKey(Long id);

	int insert(List<HwImages> record);

	HwImages selectByPrimaryKey(Long id);

	/**
	 * 查询，作业引用图片的试题
	 * <p>
	 *
	 * @param hwId 作业Id
	 * @return 作业引用图片的试题
	 */
	List<HwImages> selectHwImages(@Param("hwId") long hwId);

	List<SmartMap<String, Object>> testSmartMap();

	Page testPaging(Page page, @Param("hwId") Long hwId);

	List<HwImages> selectByHWId(@Param("hwId") Long hwId);
}