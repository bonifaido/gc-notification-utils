package stoptheworld;

import com.sun.management.GarbageCollectionNotificationInfo;

public class Example {

    public static void main(String[] args) throws Exception {

        GcNotificationUtil gcNotificationUtil = new GcNotificationUtil(new GcNotificationInfoListener() {
            @Override
            public void handle(GarbageCollectionNotificationInfo info, long stw) {
                if (isCmsNotification(info)) {
                    System.out.println(info.getGcName() + ": " + info.getGcInfo().getDuration() + " ms stw: " + stw + " ms");
                }
            }
        });

        gcNotificationUtil.start();

        while (true) {
            new String(new byte[100000000]);
            Thread.sleep(5000);
        }
    }
}
