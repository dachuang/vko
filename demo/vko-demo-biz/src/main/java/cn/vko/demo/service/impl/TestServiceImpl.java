/**
 * TestServiceImpl.java
 * cn.vko.demo.service.impl
 * Copyright (c) 2014, 北京微课创景教育科技有限公司版权所有.
*/

package cn.vko.demo.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.vko.demo.dao.mapper.TestMapper;
import cn.vko.demo.entity.Test;
import cn.vko.demo.service.TestService;

/**
 * TODO(这里用一句话描述这个类的作用)
 * <p>
 * TODO(这里描述这个类补充说明 – 可选)
 * @author   ychuang328
 * @Date	 2014-8-2 	 
 */
@Service
public class TestServiceImpl implements TestService {
	private Logger LOG = LoggerFactory.getLogger(TestServiceImpl.class);

	@Autowired
	private TestMapper testMapper;

	public String index() {
		System.out.println("Success Service!!!");
		LOG.debug("Success Service!!!");
		List<Test> tests = testMapper.selectInfo(10);
		for (Test test : tests) {
			System.out.println("test name:" + test.getName());
			LOG.debug("test name:{}", test.getName());
		}
		Test t = testMapper.selectTest(10);
		System.err.println("test entity name is " + t.getName());
		LOG.debug("test entity name is {}", t.getName());
		return "test service successfull";
	}

	public void insert() {
		Test t = new Test();
		t.setName("java");
		t.setSs(10);
		List<Test> record = new ArrayList<Test>();
		record.add(t);
		Test t1 = new Test();
		t1.setName("c++");
		t1.setSs(20);
		record.add(t1);
		//testMapper.insert(t);
		testMapper.insertTestList(record);
	}
}
