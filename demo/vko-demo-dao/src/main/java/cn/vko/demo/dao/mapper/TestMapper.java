/**
 * Test.java
 * cn.vko.demo.dao
 * Copyright (c) 2014, 北京微课创景教育科技有限公司版权所有.
*/

package cn.vko.demo.dao.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import cn.vko.demo.entity.Test;

/**
 * TODO(这里用一句话描述这个类的作用)
 * <p>
 * @author   ychuang328
 * @Date	 2014-8-1 	 
 */
public interface TestMapper {

	@Select("select name from test where id=#{id}")
	public Test selectTest(@Param("id") int id);

	public List<Test> selectInfo(@Param("id") Integer id);

	public void insertTestList(List<Test> record);

	public void insert(Test test);
}
