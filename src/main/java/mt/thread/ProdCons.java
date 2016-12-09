package mt.thread;

/**
 * Created by williaz on 10/11/16.
 */
public class ProdCons {
    public static void main(String[] args) {
        Shared s = new Shared();
        new Producer(s).start();
        new Consumer(s).start();
    }


    private static class Shared {
        private char c = '\u0000';
        private boolean writeable = true;

        synchronized void setSharedChar(char c) {
            while (!writeable) {
                try {
                    wait();
                } catch (InterruptedException e) {
                }
            }
            this.c = c;
            writeable = false;
            notify();
        }

        synchronized char getSharedChar() {
            while (writeable) {
                try {
                    wait();
                } catch (InterruptedException e) {
                }
            }
            writeable = true;
            notify();

            return c;
        }
    }

    private static class Producer extends Thread {
        private Shared s;

        Producer(Shared s) {
            this.s = s;
        }

        public void run() {
            for (char ch = 'A'; ch <= 'Z'; ch++) {
                try {
                    Thread.sleep((int) (Math.random() * 4000));
                } catch (InterruptedException e) {
                }

                s.setSharedChar(ch);
                System.out.println(ch + " produced by producer.");
            }
        }
    }

    private  static class Consumer extends Thread {
        private Shared s;

        Consumer(Shared s) {
            this.s = s;
        }

        public void run() {
            char ch;

            do {
                try {
                    Thread.sleep((int) (Math.random() * 4000));
                } catch (InterruptedException e) {
                }

                ch = s.getSharedChar();
                System.out.println(ch + " consumed by consumer.");
            }
            while (ch != 'Z');
        }
    }
}