package stoptheworld;

import com.sun.management.GarbageCollectionNotificationInfo;

import javax.management.Notification;
import javax.management.NotificationFilter;

class GcNotificationFilter implements NotificationFilter {
    static final GcNotificationFilter INSTANCE = new GcNotificationFilter();

    @Override
    public boolean isNotificationEnabled(Notification notification) {
        return notification.getType().equals(GarbageCollectionNotificationInfo.GARBAGE_COLLECTION_NOTIFICATION);
    }
}
