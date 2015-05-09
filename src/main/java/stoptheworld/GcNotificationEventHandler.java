package stoptheworld;

import com.lmax.disruptor.EventHandler;
import com.sun.management.GarbageCollectionNotificationInfo;

import javax.management.openmbean.CompositeData;

/**
 * The listener called back on the supplied ExecutorService thread, the consumer from Disruptor point of view.
 */
class GcNotificationEventHandler implements EventHandler<GcNotificationEvent> {

    private final GcStwNotificationInfoListener delegate;

    GcNotificationEventHandler(GcStwNotificationInfoListener delegate) {
        this.delegate = delegate;
    }

    @Override
    public void onEvent(GcNotificationEvent event, long sequence, boolean endOfBatch) throws Exception {
        GarbageCollectionNotificationInfo gcNotificationInfo = GarbageCollectionNotificationInfo.from((CompositeData) event.notification.getUserData());
        delegate.handle(gcNotificationInfo, event.stwDuration);
    }
}
