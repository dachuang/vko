
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import cn.vko.stat.mq.StatEvent;
import cn.vko.stat.mq.StatEventFactory;
import cn.vko.stat.mq.StatEventHandler;
import cn.vko.stat.mq.StatEventProducer;

import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.SleepingWaitStrategy;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;

public class EventMain {
	@SuppressWarnings("unchecked")
	public static void main(String[] args) throws Exception {
		// Executor that will be used to construct new threads for consumers
		Executor executor = Executors.newCachedThreadPool();

		// The factory for the event
		StatEventFactory factory = new StatEventFactory();

		// Specify the size of the ring buffer, must be power of 2.
		int bufferSize = 1024;

		// Construct the Disruptor
		Disruptor<StatEvent> disruptor = new Disruptor<StatEvent>(factory, bufferSize, executor, ProducerType.MULTI,
				new SleepingWaitStrategy());

		// Connect the handler
		disruptor.handleEventsWith(new StatEventHandler(null));
		// Start the Disruptor, starts all threads running
		disruptor.start();

		// Get the ring buffer from the Disruptor to be used for publishing.
		RingBuffer<StatEvent> ringBuffer = disruptor.getRingBuffer();

		StatEventProducer producer = new StatEventProducer(ringBuffer);

		//		for (; true;) {
		//			producer.onData(new ArrayList<Object[]>());
		//			Thread.sleep(1);
		//		}
	}
}