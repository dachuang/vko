/**
 * UpdateStat.java
 * cn.vko.stat.mapper
 * Copyright (c) 2014, 北京微课创景教育科技有限公司版权所有.
*/

package cn.vko.stat.mapper;

/**
 * 更新统计接口
 * <p>
 * @author   宋汝波
 * @Date	 2014-8-4 	 
 */
public interface UpdateStatistics {

	/**
	 * 根据指定的key更新统计数量,+count或-count
	 * <p>
	 * 先调用update,如果返回结果是0,则调用insert
	 * @param key 键值
	 * @param count 数量
	 * @param incremental 是否增量
	*/
	public int update(String group, String key, double count, boolean incremental);

	/**
	 * 插入操作
	 * <p>
	 *
	 * @param key
	 * @param count
	*/
	public int insert(String group, String key, double count);
}
