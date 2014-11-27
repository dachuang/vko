package cn.vko.stat.mq;

import java.util.List;

import cn.vko.stat.StatisticBean;

import com.lmax.disruptor.RingBuffer;

public class StatEventProducer {
	private final RingBuffer<StatEvent> ringBuffer;

	public StatEventProducer(RingBuffer<StatEvent> ringBuffer) {
		this.ringBuffer = ringBuffer;
	}

	public void onData(List<StatisticBean> data) {
		long sequence = ringBuffer.next(); // Grab the next sequence
		try {
			StatEvent event = ringBuffer.get(sequence); // Get the entry in the Disruptor
			event.set(data); // Fill with data
		} finally {
			ringBuffer.publish(sequence);
		}
	}
}