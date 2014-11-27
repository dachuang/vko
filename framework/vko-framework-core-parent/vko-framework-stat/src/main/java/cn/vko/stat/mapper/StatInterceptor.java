/**
 * StatInterceptor.java
 * cn.vko.stat.mapper
 * Copyright (c) 2014, 北京微课创景教育科技有限公司版权所有.
*/

package cn.vko.stat.mapper;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ognl.Ognl;

import org.apache.ibatis.annotations.Param;
import org.springframework.util.Assert;

import cn.vko.cache.dao.mybatis.interceptor.MyBatisInterceptor;
import cn.vko.cache.dao.mybatis.interceptor.MyBatisInvocation;
import cn.vko.stat.StatisticBean;
import cn.vko.stat.mapper.annotation.Statistics;
import cn.vko.stat.service.StatService;

/**
 * 统计的拦截器
 * <p>
 * @author   宋汝波
 * @Date	 2014-8-1 	 
 */
public class StatInterceptor implements MyBatisInterceptor {
	/*
	 * 注入的统计服务
	 */
	private StatService statService;

	@Override
	public Object invoke(MyBatisInvocation handler) throws Throwable {
		Object result = handler.execute();
		Method method = handler.getMethod();
		Statistics stat = method.getAnnotation(Statistics.class);
		if (stat != null) {
			Map<String, Object> param = getParamMap(method, handler.getArgs());
			if (param.isEmpty()) {
				throw new RuntimeException("@Param参数不能为null");
			}
			double[] counts = stat.result();
			String[] keys = stat.key();
			String[] groups = stat.group();
			boolean[] incremental = stat.incremental();
			if (counts.length != keys.length) {
				throw new RuntimeException("count和key数量不一致");
			}
			if (groups.length != 1 && groups.length != counts.length) {
				throw new RuntimeException("count和prefix数量不一致");
			}
			List<StatisticBean> data = new ArrayList<StatisticBean>();
			String group = null;
			if (groups.length == 1) {
				group = groups[0];
			}
			for (int i = 0; i < keys.length; i++) {
				Object key = Ognl.getValue(keys[i], param);
				Assert.notNull(key, "您提供key值是null");
				if (group == null) {
					data.add(new StatisticBean(groups[i], key, counts[i], incremental[i]));
				} else {
					data.add(new StatisticBean(group, key, counts[i], incremental[i]));
				}
			}
			//发送统计信息到队列
			statService.addStat(data);
		}
		return result;

	}

	public StatService getStatService() {
		return statService;
	}

	public void setStatService(StatService statService) {
		this.statService = statService;
	}

	/**
	 *查找参数
	 * @param method
	 * @param args
	*/
	private Map<String, Object> getParamMap(Method method, Object[] args) {
		Annotation[][] parameterAnnotations = method.getParameterAnnotations();
		Map<String, Object> paramsMap = new HashMap<String, Object>();
		for (int i = 0; i < parameterAnnotations.length; i++) {
			for (Annotation annotation : parameterAnnotations[i]) {
				if (annotation instanceof Param) {
					Param myAnnotation = (Param) annotation;
					paramsMap.put(myAnnotation.value(), args[i]);
				}
			}
		}
		return paramsMap;
	}
}
