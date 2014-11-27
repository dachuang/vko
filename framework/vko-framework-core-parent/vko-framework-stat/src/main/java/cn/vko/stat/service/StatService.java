/**
 * StatService.java
 * cn.vko.stat.mq
 * Copyright (c) 2014, 北京微课创景教育科技有限公司版权所有.
*/

package cn.vko.stat.service;

import java.util.List;

import cn.vko.stat.StatisticBean;

/**
 * 统计服务,需要配置此服务
 * <p>
 * @author   宋汝波
 * @Date	 2014-8-4 	 
 */
public interface StatService {

	public void addStat(List<StatisticBean> data);

	public void addStat(StatisticBean data);

	public void addStat(String group, String key, double result);

	public double getStat(String group, String key);

	public int getStatInt(String group, String key);

}
