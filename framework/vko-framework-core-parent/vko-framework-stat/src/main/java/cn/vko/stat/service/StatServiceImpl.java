/**
 * StatServiceImpl.java
 * cn.vko.stat.service
 * Copyright (c) 2014, 北京微课创景教育科技有限公司版权所有.
*/

package cn.vko.stat.service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.Assert;

import cn.vko.stat.StatisticBean;
import cn.vko.stat.mapper.FetchStatistics;
import cn.vko.stat.mapper.UpdateStatistics;
import cn.vko.stat.mq.StatEvent;
import cn.vko.stat.mq.StatEventFactory;
import cn.vko.stat.mq.StatEventHandler;
import cn.vko.stat.mq.StatEventProducer;

import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.SleepingWaitStrategy;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;

/**
 * 封装disruptor服务
 * <p>
 * @author   宋汝波
 * @Date	 2014-8-4 	 
 */
public class StatServiceImpl implements StatService, InitializingBean, DisposableBean {
	private UpdateStatistics update;
	private FetchStatistics fetch;
	private int bufferSize = 2048;
	private StatEventProducer producer;
	private Disruptor<StatEvent> disruptor;

	@Override
	public double getStat(String prefix, String key) {
		return fetch.fetch(prefix, key);

	}

	@Override
	public void destroy() throws Exception {
		if (disruptor != null) {
			disruptor.shutdown();
		}

	}

	@SuppressWarnings("unchecked")
	@Override
	public void afterPropertiesSet() throws Exception {
		Assert.notNull(update, "统计更新接口必须实现" + UpdateStatistics.class.getName());
		Assert.notNull(fetch, "统计获取接口必须实现" + FetchStatistics.class.getName());
		Executor executor = Executors.newCachedThreadPool();
		// The factory for the event
		StatEventFactory factory = new StatEventFactory();
		// Construct the Disruptor
		disruptor = new Disruptor<StatEvent>(factory, bufferSize, executor, ProducerType.SINGLE,
				new SleepingWaitStrategy());
		// Connect the handler
		disruptor.handleEventsWith(new StatEventHandler(update));
		// Start the Disruptor, starts all threads running
		RingBuffer<StatEvent> ringBuffer = disruptor.start();
		// Get the ring buffer from the Disruptor to be used for publishing.
		//		RingBuffer<StatEvent> ringBuffer = disruptor.getRingBuffer();
		producer = new StatEventProducer(ringBuffer);
	}

	public UpdateStatistics getUpdate() {
		return update;
	}

	public void setUpdate(UpdateStatistics update) {
		this.update = update;
	}

	public FetchStatistics getFetch() {
		return fetch;
	}

	public void setFetch(FetchStatistics fetch) {
		this.fetch = fetch;
	}

	@Override
	public void addStat(String group, String key, double result) {
		List<StatisticBean> data = new ArrayList<StatisticBean>(1);
		data.add(new StatisticBean(group, key, result));
		producer.onData(data);
	}

	@Override
	public void addStat(List<StatisticBean> lst) {
		producer.onData(lst);

	}

	@Override
	public void addStat(StatisticBean data) {

		List<StatisticBean> lst = new ArrayList<StatisticBean>(1);
		lst.add(data);
		producer.onData(lst);

	}

	@Override
	public int getStatInt(String group, String key) {
		double result = fetch.fetch(group, key);
		return Double.valueOf(result).intValue();

	}

}
