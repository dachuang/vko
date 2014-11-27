package cn.vko.stat.mq;

import java.util.List;

import cn.vko.stat.StatisticBean;
import cn.vko.stat.mapper.UpdateStatistics;

import com.lmax.disruptor.EventHandler;

public class StatEventHandler implements EventHandler<StatEvent> {

	UpdateStatistics update;

	public StatEventHandler(UpdateStatistics update) {
		this.update = update;
	}

	@Override
	public void onEvent(StatEvent event, long sequence, boolean endOfBatch) {
		List<StatisticBean> data = event.get();
		if (data != null) {
			for (StatisticBean objs : data) {
				int count = update.update(objs.getGroup(), objs.getKey().toString(), objs.getResult(),
						objs.isIncremental());
				if (count == 0) {
					//更新条数为0,则插入
					update.insert(objs.getGroup(), objs.getKey().toString(), objs.getResult());
				}
			}
		}
	}
}