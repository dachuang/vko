package cn.vko.stat.mq;

import com.lmax.disruptor.EventFactory;

public class StatEventFactory implements EventFactory<StatEvent> {
	@Override
	public StatEvent newInstance() {
		return new StatEvent();
	}
}