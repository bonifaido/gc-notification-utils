package stoptheworld;

import com.sun.management.GarbageCollectionNotificationInfo;

public interface GcNotificationInfoListener {
    void handle(GarbageCollectionNotificationInfo info, long stwDuration);
}
