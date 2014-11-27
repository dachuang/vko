/**
 * StatisticBean.java
 * cn.vko.stat
 * Copyright (c) 2014, 北京微课创景教育科技有限公司版权所有.
*/

package cn.vko.stat;

/**
 * 用于传递统计信息
 * <p>
 * @author   宋汝波
 * @Date	 2014-8-12 	 
 */
public class StatisticBean {
	//对某项统计的名称
	private String group;
	//对应于某项id
	private Object key;
	//数量是增是减,+1,-1.
	private double result;
	//数量是否是增量变化,false是绝对
	private boolean incremental = true;

	public StatisticBean(String group, Object key, double result) {
		this(group, key, result, true);
	}

	public StatisticBean(String group, Object key, double result, boolean incremental) {
		this.group = group;
		this.key = key;
		this.result = result;
		this.incremental = incremental;
	}

	public String getGroup() {
		return group;
	}

	public void setGroup(String group) {
		this.group = group;
	}

	public Object getKey() {
		return key;
	}

	public void setKey(Object key) {
		this.key = key;
	}

	public double getResult() {
		return result;
	}

	public void setResult(double result) {
		this.result = result;
	}

	public boolean isIncremental() {
		return incremental;
	}

	public void setIncremental(boolean incremental) {
		this.incremental = incremental;
	}

}
