package mt.thread;

/**
 * Created by williaz on 10/10/16.
 */
public class CalcPI1 {
    // CalcPI1.java
        public static void main (String [] args)
        {
            MyThread mt = new MyThread ();
            mt.setName("Calc_Pi_Thread");
            mt.start ();

            BackThread bt= new BackThread();
            bt.setName("Background_Thread");
            bt.setDaemon(true);
            bt.start();

            // #1 same with join()

            while(mt.isAlive()) {
                try {
                    //which threads are actively running in your program
                    Thread [] threads = new Thread [Thread.activeCount ()];
                    int n = Thread.enumerate (threads);
                    for (int i = 0; i < n; i++)
                        System.out.println (threads [i].toString ());// [group, priority, name]
                    System.out.println ("Sleep--------------");

                    Thread.sleep(0, 1); // Sleep for 1 nanoseconds
                } catch (InterruptedException e) {
                }
            }

            /*
            try
            {
                mt.join ();
            }
            catch (InterruptedException e)
            {
            }
            */

            System.out.println ("pi = " + mt.pi);

        }


    private static class MyThread extends Thread {
        boolean negative = true;
        double pi; // Initializes to 0.0, by default

        public void run() {
            for (int i = 3; i < 100000; i += 2) {
                if (negative)
                    pi -= (1.0 / i);
                else
                    pi += (1.0 / i);
                negative = !negative;
            }
            pi += 1.0;
            pi *= 4.0;
            System.out.println(this.getName()+": Finished calculating PI");
        }
    }


    private static class BackThread extends Thread
    {
        public void run ()
        {
            System.out.println ("Daemon is " + isDaemon ());
            while (true);
        }
    }
}
