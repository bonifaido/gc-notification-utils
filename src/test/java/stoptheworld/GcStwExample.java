package stoptheworld;

public class GcStwExample {

    public static void main(String[] args) throws Exception {

        GcStwNotificationAdapter gcNotificationUtil = GcNotificationUtil.stwAdapter((info, stwDuration)
                -> System.out.println("# " + info.getGcName() + ": " + info.getGcInfo().getDuration() + " ms stw: " + stwDuration + " ms"));

        for (int i = 0; i < 100; i++) {
            new String(new byte[100000000]);
            Thread.sleep(5000);
        }

        gcNotificationUtil.stop();
    }
}
