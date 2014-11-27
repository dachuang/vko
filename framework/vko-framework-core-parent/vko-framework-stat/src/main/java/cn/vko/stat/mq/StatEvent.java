/**
 * StatEvent.java
 * cn.vko.stat.mq
 * Copyright (c) 2014, 北京微课创景教育科技有限公司版权所有.
*/

package cn.vko.stat.mq;

import java.util.List;

import cn.vko.stat.StatisticBean;

/**
 * 一次统计任务事件
 * <p>
 * @author   宋汝波
 * @Date	 2014-8-1 	 
 */
public class StatEvent {
	private List<StatisticBean> data;

	public void set(List<StatisticBean> data) {
		this.data = data;
	}

	public List<StatisticBean> get() {
		return data;
	}
}
