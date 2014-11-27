/**
 * TestTeacherMapper.java
 * cn.vko.fz.dao
 * Copyright (c) 2014, 北京微课创景教育科技有限公司版权所有.
*/

package cn.vko.zuoye.test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import cn.vko.cache.dao.mybatis.Page;
import cn.vko.zuoye.mapper.TikuMapper;

public class TestTikuMapper extends DaoTests {

	@Autowired
	private TikuMapper mapper;

	@Test
	public void testPageList() {
		Page page = new Page(1, 10);
		String[] k1 = new String[] { "1", "2" };
		String[] k2 = new String[] { "1", "2" };
		String[] k3 = new String[] { "1", "2" };
		String[] type = new String[] { "1", "2" };
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("xueDuanId", 51);
		param.put("subjectId", 51);
		param.put("k1s", k1);
		param.put("k2s", k2);
		param.put("k3s", k3);
		param.put("types", type);
		param.put("sort", 51);
		mapper.getPageFromTkExam(page, param);
	}

	@Test
	public void testoo() {
		List<Long> ids = new ArrayList<Long>();
		ids.add(4545L);
		mapper.getExamTypeById(ids);
		mapper.getExamsAnswers(ids);
		mapper.getExamsContent(ids);
		mapper.getExamsObjective(ids);
	}
}
