package stoptheworld;

public class Example {
    public static void main(String[] args) throws Exception {

        GcNotificationUtil gcNotificationUtil = new GcNotificationUtil((info, stw) -> {
            if (info.getGcName().equals("ConcurrentMarkSweep")) {
                System.out.println(info.getGcName() + ": " + info.getGcInfo().getDuration() + " ms stw: " + stw + " ms");
            }
        });

        gcNotificationUtil.start();

        while (true) {
            new String(new byte[100000000]);
            Thread.sleep(5000);
        }
    }
}
