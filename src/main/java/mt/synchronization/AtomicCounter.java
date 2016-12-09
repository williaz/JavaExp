package mt.synchronization;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by williaz on 10/3/16.
 */
class AtomicCounter extends Counter{
    private AtomicInteger c = new AtomicInteger(0);

    public void increment() {
        c.incrementAndGet();
    }

    public void decrement() {
        c.decrementAndGet();
    }

    public int value() {
        return c.get();
    }

}
