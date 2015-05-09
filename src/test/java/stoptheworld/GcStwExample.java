package stoptheworld;

import com.sun.management.GarbageCollectionNotificationInfo;

public class GcStwExample {

    public static void main(String[] args) throws Exception {

        GcStwNotificationAdapter gcNotificationUtil = GcNotificationUtil.stwAdapter(new GcStwNotificationInfoListener() {
            @Override
            public void handle(GarbageCollectionNotificationInfo info, long stwDuration) {
                if (isCmsNotification(info)) {
                    System.out.println(info.getGcName() + ": " + info.getGcInfo().getDuration() + " ms stw: " + stwDuration + " ms");
                }
            }
        });

        for (int i = 0; i < 100; i++) {
            new String(new byte[100000000]);
            Thread.sleep(5000);
        }

        gcNotificationUtil.stop();
    }
}
