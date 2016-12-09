package mt.synchronization;

/**
 * Created by williaz on 10/1/16.
 */
class Counter {
    private int c = 0;

    public void increment() {
        c++;
    }

    public void decrement() {
        c--;
    }

    public int value() {
        return c;
    }



}
