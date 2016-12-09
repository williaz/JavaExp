package mt.synchronization;

/**
 * Created by williaz on 10/1/16.
 */
public class ThreadCounter implements Runnable {
    Counter c;

    public ThreadCounter(Counter c) {
        this.c = c;
    }



    @Override
    public void run() {

        threadMessage("before operation "+c.value());
        //only make class Counter syn, does not prevent
        synchronized (c) {
            c.increment();

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            c.decrement();
        }

        threadMessage("after operation "+c.value());
    }

    static void threadMessage(String message) {
        String threadName =
                Thread.currentThread().getName();
        System.out.format("%s: %s%n",
                threadName,
                message);
    }

    public static void main(String[] args) {
        Counter counter=new Counter();
        /*
        ThreadCounter tc=new ThreadCounter(counter);
        Thread t1=new Thread(tc);
        Thread t2=new Thread(tc);
        Thread t3=new Thread(tc);
        */


        Thread t1=new Thread(new ThreadCounter(counter));
        Thread t2=new Thread(new ThreadCounter(counter));
        Thread t3=new Thread(new ThreadCounter(counter));


        t2.start();
        t1.start();
        t3.start();

        System.out.println("++++++++++++++");


        Counter sCounter=new SynchronizedCounter();
        /*

        Thread t4=new Thread(new ThreadCounter(sCounter));
        Thread t5=new Thread(new ThreadCounter(sCounter));
        Thread t6=new Thread(new ThreadCounter(sCounter));
        */
        ThreadCounter tc=new ThreadCounter(sCounter);
        Thread t4=new Thread(tc);
        Thread t5=new Thread(tc);
        Thread t6=new Thread(tc);


        t4.start();
        t5.start();
        t6.start();

        System.out.println("++++++++++++++");


        Counter aCounter=new AtomicCounter();
        /*

        Thread t4=new Thread(new ThreadCounter(sCounter));
        Thread t5=new Thread(new ThreadCounter(sCounter));
        Thread t6=new Thread(new ThreadCounter(sCounter));
        */
        ThreadCounter ac=new ThreadCounter(sCounter);
        Thread t7=new Thread(ac);
        Thread t8=new Thread(ac);
        Thread t9=new Thread(ac);


        t7.start();
        t8.start();
        t9.start();

    }
}
