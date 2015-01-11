package stoptheworld;

import com.lmax.disruptor.EventFactory;

/**
 * Created by bonifaido on 1/11/15.
 */
class GcNotificationEventFactory implements EventFactory<GcNotificationEvent> {
    static final GcNotificationEventFactory INSTANCE = new GcNotificationEventFactory();

    @Override
    public GcNotificationEvent newInstance() {
        return new GcNotificationEvent();
    }
}
