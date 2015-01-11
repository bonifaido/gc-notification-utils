package stoptheworld;

import com.lmax.disruptor.EventFactory;

class GcNotificationEventFactory implements EventFactory<GcNotificationEvent> {
    static final GcNotificationEventFactory INSTANCE = new GcNotificationEventFactory();

    @Override
    public GcNotificationEvent newInstance() {
        return new GcNotificationEvent();
    }
}
