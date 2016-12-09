package mt.thread;

/**
 * Created by williaz on 10/1/16.
 */
public class HelloThread extends Thread {
    @Override
    public void run() {
        System.out.println("Hello from Thread!");

    }

    public static void main(String[] args) {
        Thread thread = new HelloThread();
        thread.start();
    }
}
