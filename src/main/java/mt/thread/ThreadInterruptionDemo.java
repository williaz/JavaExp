package mt.thread;

/**
 * Created by williaz on 10/12/16.
 */
public class ThreadInterruptionDemo {
    public static void main(String[] args) {
        ThreadB thdb = new ThreadB();
        thdb.setName("B");
        ThreadA thda = new ThreadA(thdb);
        thda.setName("A");
        thdb.start();
        thda.start();
    }

    private static class ThreadA extends Thread {
        private Thread thdOther;

        ThreadA(Thread thdOther) {
            this.thdOther = thdOther;
        }

        public void run() {
            int sleepTime = (int) (Math.random() * 4000);
            System.out.println(getName() + " sleeping for " + sleepTime +
                    " milliseconds.");
            try {
                Thread.sleep(sleepTime);
            } catch (InterruptedException e) {
            }
            System.out.println(getName() + " waking up, interrupting other " +
                    "thread and terminating.");
            thdOther.interrupt();
        }
    }

    private static class ThreadB extends Thread {
        int count = 0;

        public void run() {
            while (!isInterrupted()) {
                try {
                    Thread.sleep((int) (Math.random() * 10));
                } catch (InterruptedException e) {
                    System.out.println(getName() + " about to terminate...");

                    interrupt();// set the interrupt status to true, thus end the while loop
                }
                System.out.println(getName() + " " + count++);
            }

        }
    }

}