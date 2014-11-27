/**
 * UpdateStat.java
 * cn.vko.stat.mapper
 * Copyright (c) 2014, 北京微课创景教育科技有限公司版权所有.
*/

package cn.vko.stat.mapper;

/**
 * 获取统计接口
 * <p>
 * @author   宋汝波
 * @Date	 2014-8-4 	 
 */
public interface FetchStatistics {

	/**
	 * 根据指定的key获取统计数量
	 * <p>
	 *
	 * @param key 键值
	*/
	public double fetch(String group, String key);
}
