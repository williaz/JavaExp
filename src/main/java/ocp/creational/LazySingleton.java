package ocp.creational;


/**
 * Created by williaz on 11/24/16.
 * Lazy instantiation reduces memory usage
 * and improves performance when an application starts up.
 */
public class LazySingleton {
    private int quantity = 0;
    private static volatile LazySingleton instance;
    private LazySingleton() {}
    public static LazySingleton getInstance() { // double-checked locking
        if (instance == null) {
            synchronized (LazySingleton.class) {
                if (instance == null) {
                    instance = new LazySingleton();
                }
            }
        }
        return instance;
    }
    // same with Singleton.class
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
