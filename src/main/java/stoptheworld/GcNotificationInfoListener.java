package stoptheworld;

import com.sun.management.GarbageCollectionNotificationInfo;

public interface GcNotificationInfoListener {

    String CONCURRENT_MARK_SWEEP = "ConcurrentMarkSweep";

    /**
     * Gets called back after a GC run is finished by the JVM.
     *
     * @param info        the usual object that could be extracted from the notification
     * @param stwDuration the real stop-the-world duration of the GC run
     */
    void handle(GarbageCollectionNotificationInfo info, long stwDuration);

    default boolean isCmsNotification(GarbageCollectionNotificationInfo info) {
        return info.getGcName().equals(CONCURRENT_MARK_SWEEP);
    }
}
