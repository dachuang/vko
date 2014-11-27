/**
 * TestTeacherMapper.java
 * cn.vko.fz.dao
 * Copyright (c) 2014, 北京微课创景教育科技有限公司版权所有.
*/

package cn.vko.zuoye.test;

import junit.framework.Assert;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import cn.vko.zuoye.entity.Homework;
import cn.vko.zuoye.keyvalue.HomeworkStatus;
import cn.vko.zuoye.keyvalue.HomeworkType;
import cn.vko.zuoye.mapper.HomeworkMapper;

public class TestHomeworkMapper extends DaoTests {

	@Autowired
	private HomeworkMapper mapper;

	@Test
	public void testPageList() {
		mapper.selectDistributionHW(1);
		mapper.selectHasResolveById(12L);
		mapper.selectHWForDistribution(12L);
		mapper.selectHWForDoHW(12L);
	}

	@Test
	public void testInsert() {
		Homework record = new Homework();
		record.setName("测试作业");
		record.setTeacherId(1L);
		record.setSubjectId(1L);
		record.setStatus(HomeworkStatus.SENDED.getKey());
		record.setType(HomeworkType.TIKU_CHANNEL.getKey());
		record.setGroupId(1L);
		record.setGroupName("测试群组");
		mapper.insert(record);
		Assert.assertNotNull(record.getId());
	}

	@Test
	public void testUpdate() {
		//		Homework record = new Homework();
		//		record.setName("测试作业");
		//		record.setTeacherId(1L);
		//		record.setSubjectId(1L);
		//		record.setStatus(HomeworkStatus.SENDED.getKey());
		//		record.setType(HomeworkType.TIKU_CHANNEL.getKey());
		//		record.setGroupId(1L);
		//		record.setGroupName("测试群组");
		mapper.updateHWDistribution(1L, 1);
		//		Assert.assertNotNull(record.getId());
	}

}
