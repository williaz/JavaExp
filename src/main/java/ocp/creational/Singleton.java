package ocp.creational;


/**
 * Created by williaz on 11/24/16.
 */
public class Singleton {
    private int quantity = 0;
    private static final Singleton instance;
    static { //instantiate the singleton at the time the class is loaded
        instance = new Singleton();
    }
    private Singleton() {}
    public static Singleton getInstance() { //thread-safe in nature
        return instance;
    }
    public synchronized void addQuantity(int i) {
        quantity +=i;
    }
    public synchronized boolean consume(int i) {
        if (quantity < i) {
            return false;
        }
        quantity -=i;
        return true;
    }
    public synchronized int getQuantity() {
        return quantity;
    }

}
