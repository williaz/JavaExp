package concurrent;

import java.util.concurrent.CountDownLatch;

/**
 * Created by williaz on 10/1/16.
 * Non-negtive numbers
 */
public class SleepSort {
    public static void sleepSortAndPrint(int[] nums) {
        final CountDownLatch doneSignal = new CountDownLatch(nums.length);
        for (final int num : nums) {
            new Thread(new Runnable() {
                public void run() {
                    doneSignal.countDown();
                    try {
                        doneSignal.await();

                        //using straight milliseconds produces unpredictable
                        //results with small numbers
                        //using 1000 here gives a nifty demonstration
                        //Thread.sleep(num * 1000);
                        Thread.sleep(num);

                        System.out.println(num);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }
    }
    public static void main(String[] args) {
        int[] nums = {5,4,111,2,245,7,5,8,411,12,44,54,1,1234,24,13};

        sleepSortAndPrint(nums);
    }
}
