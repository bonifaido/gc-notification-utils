package stoptheworld;

import com.lmax.disruptor.BlockingWaitStrategy;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;

import javax.management.NotificationEmitter;
import java.lang.management.GarbageCollectorMXBean;
import java.lang.management.ManagementFactory;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Capture real stop-the-world ConcurrentMarkSweep times programmatically!
 * Code:
 * - Create an instance of this class, pass your GcNotificationInfoListener instance in the constructor.
 * - Start the instance.
 * Running:
 * - Run the JVM with: -XX:+UseConcMarkSweepGC
 */
public final class GcStwNotificationAdapter {
    private final ExecutorService executor = Executors.newSingleThreadExecutor();
    private final Disruptor<GcNotificationEvent> disruptor;

    GcStwNotificationAdapter(GcStwNotificationInfoListener listener) {

        // There is only one notification thread, thus the SINGLE ProducerType
        disruptor = new Disruptor<>(GcNotificationEventFactory.INSTANCE, 64, executor, ProducerType.SINGLE, new BlockingWaitStrategy());

        ManagementFactory.getGarbageCollectorMXBeans().forEach(this::addListener);

        disruptor.handleEventsWith(new GcNotificationEventHandler(listener));

        disruptor.start();
    }

    private void addListener(GarbageCollectorMXBean gcBean) {
        NotificationEmitter emitter = (NotificationEmitter) gcBean;
        GcNotificationListener listener = new GcNotificationListener(gcBean, disruptor.getRingBuffer());
        emitter.addNotificationListener(listener, GcNotificationFilter.INSTANCE, null);
    }

    public void stop() {
        disruptor.shutdown();
        executor.shutdown();
    }
}
