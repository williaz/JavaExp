package thread;

/**
 * Created by williaz on 11/13/16.
 */
public class PrintThreadName extends Thread {
    public PrintThreadName(String name) {
        super(name);
    }

    @Override
    public void run() {
        System.out.println("This thread is :" + Thread.currentThread().getName());
    }

    public static void main(String[] args) {
        new PrintThreadName("thread by start").start(); // in thread itself
        new PrintThreadName("thread by run").run(); // in main thread

    }
}
