/**
 * MyFalseMapper.java
 * cn.vko.zuoye.mapper
 * Copyright (c) 2014, 北京微课创景教育科技有限公司版权所有.
*/

package cn.vko.zuoye.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.vko.cache.dao.mybatis.Page;
import cn.vko.zuoye.entity.MyFalse;

/**
 * TODO(这里用一句话描述这个类的作用)
 * <p>
 * TODO(这里描述这个类补充说明 – 可选)
 * @author   pc
 * @Date	 2014-7-23 	 
 */
public interface MyFalseMapper {

	public List<MyFalse> selectMyFalse(Page page, @Param("stdId") Long stdId, @Param("subjectId") Long subjectId);

	public int delMyfalse(@Param("fid") Long fid);

}
