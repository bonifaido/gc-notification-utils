package stoptheworld;

import com.lmax.disruptor.RingBuffer;

import javax.management.Notification;
import javax.management.NotificationListener;
import java.lang.management.GarbageCollectorMXBean;

/**
 * The listener called back on the JVM Service thread, the producer from Disruptor point of view.
 */
class GcNotificationListener implements NotificationListener {

    private final GarbageCollectorMXBean gcBean;
    private final RingBuffer<GcNotificationEvent> disruptor;
    private long lastCollectionTime;

    public GcNotificationListener(GarbageCollectorMXBean gcBean, RingBuffer<GcNotificationEvent> disruptor) {
        this.gcBean = gcBean;
        this.disruptor = disruptor;
        this.lastCollectionTime = gcBean.getCollectionTime();
    }

    /**
     * Do a very quick path here, because this goes on the VM Service thread.
     */
    @Override
    public void handleNotification(Notification notification, Object handback) {
        long currentCollectionTime = gcBean.getCollectionTime();
        long duration = currentCollectionTime - lastCollectionTime;
        lastCollectionTime = currentCollectionTime;

        long sequence = disruptor.next();

        GcNotificationEvent event = disruptor.get(sequence);
        event.notification = notification;
        event.stwDuration = duration;

        disruptor.publish(sequence);
    }
}
