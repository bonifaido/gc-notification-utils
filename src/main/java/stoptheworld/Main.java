package stoptheworld;

/**
 * Capture real stop-the-world ConcurrentMarkSweep times!
 */
public class Main {
    public static void main(String[] args) throws Exception {

        GcNotificationUtil gcNotificationUtil = new GcNotificationUtil((info, stw) -> {
            if (info.getGcName().equals("ConcurrentMarkSweep")) {
                System.out.println(info.getGcName() + ": " + info.getGcInfo().getDuration() + " stw: " + stw);
            }
        });

        gcNotificationUtil.start();

        while (true) {
            new String(new byte[100000000]);
            Thread.sleep(5000);
        }
    }
}
