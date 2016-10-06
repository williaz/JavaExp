package concurrent;

import java.util.Random;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Lock;
//some algorithms for traversing concurrently accessed data structures require the use of "hand-over-hand"
// or "chain locking": you acquire the lock of node A, then node B, then release A and acquire C, then release B
// and acquire D and so on. Implementations of the Lock interface enable the use of such techniques by allowing a
// lock to be acquired and released in different scopes, and allowing multiple locks to be acquired and released in
// any order.
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by williaz on 10/3/16.
 */
public class Safelock {
    static class Friend {
        private final String name;
        private final Lock lock = new ReentrantLock();

        public Friend(String name) {
            this.name = name;
        }

        public String getName() {
            return this.name;
        }

        public boolean impendingBow(Friend bower) {
            Boolean myLock = false;
            Boolean yourLock = false;
            try {
                myLock = lock.tryLock();
                //Acquires the lock if it is not held by another thread
                // and returns immediately with the value true, setting the lock hold count to one.
                yourLock = bower.lock.tryLock();
            } finally {
                if (! (myLock && yourLock)) {
                    if (myLock) {
                        lock.unlock();
                    }
                    if (yourLock) {
                        bower.lock.unlock();
                    }
                }
            }
            return myLock && yourLock;
        }

        public void bow(Friend bower) {
            if (impendingBow(bower)) {
                try {
                    System.out.format("%s: %s has"
                                    + " bowed to me!%n",
                            this.name, bower.getName());
                    bower.bowBack(this);
                } finally {
                    lock.unlock();
                    bower.lock.unlock();
                }
            } else {
                System.out.format("%s: %s started"
                                + " to bow to me, but saw that"
                                + " I was already bowing to"
                                + " him.%n",
                        this.name, bower.getName());
            }
        }

        public void bowBack(Friend bower) {
            System.out.format("%s: %s has" +
                            " bowed back to me!%n",
                    this.name, bower.getName());
        }
    }

    static class BowLoop implements Runnable {
        private Friend bower;
        private Friend bowee;

        public BowLoop(Friend bower, Friend bowee) {
            this.bower = bower;
            this.bowee = bowee;
        }

        public void run() {
            Random random = new Random();
            for (int i=0;i<10;i++) {
                try {
                    Thread.sleep(random.nextInt(10));
                } catch (InterruptedException e) {}
                bowee.bow(bower);
            }
        }
    }


    public static void main(String[] args) {
        final Friend alphonse =
                new Friend("Alphonse");
        final Friend gaston =
                new Friend("Gaston");
        /*
        new Thread(new BowLoop(alphonse, gaston)).start();
        new Thread(new BowLoop(gaston, alphonse)).start();
        */
        Executor e= Executors.newFixedThreadPool(3);
        e.execute(new BowLoop(alphonse, gaston));
        e.execute(new BowLoop(gaston, alphonse));
    }
}