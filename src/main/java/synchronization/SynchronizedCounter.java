package synchronization;

/**
 * Created by williaz on 10/1/16.
 */
public class SynchronizedCounter extends Counter {
    private int c = 0;

    public synchronized void increment() {
        c++;
    }

    public synchronized void decrement() {
        c--;
    }

    public synchronized int value() {
        return c;
    }
}
