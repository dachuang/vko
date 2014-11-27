package cn.vko.zuoye.mapper;

import java.util.List;

import cn.vko.zuoye.entity.HwAnswerCard;

public interface HwAnswerCardMapper {

	int insert(List<HwAnswerCard> records);

	/**
	 * 根据ids返回所有字段
	 * <p>
	 *
	 * @param ids
	*/
	List<HwAnswerCard> selectAllByIds(List<Long> ids);

}