/**
 * DaoTests.java
 * cn.vko.fz.test
 * Copyright (c) 2014, 北京微课创景教育科技有限公司版权所有.
*/

package cn.vko.zuoye.test;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.ResourceUtils;

import com.vko.core.common.environment.EnvironmentDetect;
import com.vko.core.common.log.LogbackWebConfigurer;
import com.vko.core.common.util.VelocityTemplateUtil;

/**
 * dao测试的基类
 * @author   dixingxing
 * @Date	 2014-5-16 	 
 */
@Ignore
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring/applicationContext-*.xml" })
public class DaoTests {

	@BeforeClass
	public static void before() throws Exception {
		try {
			URL url = ResourceUtils.getURL("classpath:xml/logback.xml");
			Map<String, Object> context = new HashMap<String, Object>();
			context.put("environment", EnvironmentDetect.detectEnvironment());
			String config = VelocityTemplateUtil.getConfiguration(context, url.getFile());
			LogbackWebConfigurer.initLogging(config.getBytes("UTF-8"));
		} catch (Exception e) {
			e.printStackTrace();

		}
	}
}
