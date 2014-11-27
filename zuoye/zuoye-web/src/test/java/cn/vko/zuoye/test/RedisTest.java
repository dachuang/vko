/**
 * TestTeacherMapper.java
 * cn.vko.fz.dao
 * Copyright (c) 2014, 北京微课创景教育科技有限公司版权所有.
*/

package cn.vko.zuoye.test;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import cn.vko.zuoye.service.sso.RedisCommands;

public class RedisTest extends DaoTests {

	@Autowired
	private RedisCommands redisCommands;

	@Test
	public void testPageList() {
		System.out.println(redisCommands.get("vko:zuoye:worker_key_send_key"));
	}

}
