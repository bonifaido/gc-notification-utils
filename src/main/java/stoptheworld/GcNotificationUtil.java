package stoptheworld;

import com.lmax.disruptor.BlockingWaitStrategy;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;

import javax.management.NotificationEmitter;
import java.lang.management.GarbageCollectorMXBean;
import java.lang.management.ManagementFactory;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Capture real stop-the-world ConcurrentMarkSweep times!
 * Code:
 * - Create an instance of this object, pass your GcNotificationInfoListener instance in the constructor.
 * - Start the instance.
 * Running:
 * - Run the JVM with: -XX:+UseConcMarkSweepGC
 */
public class GcNotificationUtil {
    private final ExecutorService executor = Executors.newSingleThreadExecutor();
    private final Disruptor<GcNotificationEvent> disruptor;

    GcNotificationUtil(GcNotificationInfoListener listener) {

        disruptor = new Disruptor<>(GcNotificationEventFactory.INSTANCE, 64, executor, ProducerType.SINGLE, new BlockingWaitStrategy());

        List<GarbageCollectorMXBean> gcBeans = ManagementFactory.getGarbageCollectorMXBeans();
        for (GarbageCollectorMXBean gcBean : gcBeans) {
            NotificationEmitter notificationEmitter = (NotificationEmitter) gcBean;
            notificationEmitter.addNotificationListener(new GcNotificationListener(gcBean, disruptor.getRingBuffer()), GcNotificationFilter.INSTANCE, null);
        }

        disruptor.handleEventsWith(new GcNotificationEventHandler(listener));
    }

    public void start() {
        disruptor.start();
    }

    public void stop() {
        disruptor.shutdown();
        executor.shutdown();
    }
}
