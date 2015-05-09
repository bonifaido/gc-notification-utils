package stoptheworld;

public final class GcNotificationUtil {

    public static GcStwNotificationAdapter stwAdapter(GcStwNotificationInfoListener listener) {
        return new GcStwNotificationAdapter(listener);
    }
}
