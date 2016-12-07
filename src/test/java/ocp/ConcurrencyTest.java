package ocp;

import org.junit.Test;

/**
 * Created by williaz on 12/6/16.
 */
public class ConcurrencyTest {
    /**
     * A thread is the smallest unit of execution that can be scheduled by the operating system.
     * A process is a group of associated threads that execute in the same, shared environment.
     *   -> single / multiple -threaded process <- user defined thread
     *   shared environment - the threads in the same process share the same memory space and can communicate directly with one another.
     * A task is a single unit of work performed by a thread.
     * A system thread is created by the JVM and runs in the background of the application. -> Error
     * a user-defined thread is one created by the application developer to accomplish a specific task.
     * a daemon[i:] thread is one that will not prevent the JVM from exiting when the program finishes.
     * Concurrency: The property of executing multiple threads and processes at the same time.
     * Operating systems use a thread scheduler to determine which threads should be currently executing
     *      Round-Robin schedule in which each available thread receives an equal number of CPU cycles
     *           with which to execute, with threads visited in a circular order.
     *      A context switch is the process of storing a thread’s current state
     *           and later restoring the state of the thread to continue execution.
     * A thread priority is a numeric value associated with a thread that is taken into consideration
     *    by the thread scheduler when determining which threads should currently be executing.
     *    -> integer
     *    Thread.MIN_PRIORITY : 1; NORM_PRIORITY : 5; MAX_PRIORITY : 10
     */

    /**
     * Runnable for short, is a functional interface that takes no arguments and returns no data.
     * Java does not provide any guarantees about the order in which a thread will be processed once it is started.
     * While the order of thread execution once the threads have been started is indeterminate,
     *    the order within a single thread is still linear.
     * start() separate thread execution; run() no
     * Runnable VS Thread:
     *  1. If you need to define your own Thread rules upon which multiple tasks will rely,
     *       such as a priority Thread, extending Thread may be preferable.
     *  2. Since Java doesn't support multiple inheritance, extending Thread does not allow you to extend any other class,
     *       whereas implementing Runnable lets you extend another class.
     *  3. Implementing Runnable is often a better object-oriented design practice
     *       since it separates the task being performed from the Thread object performing it.
     *  4. Implementing Runnable allows the class to be used by numerous Concurrency API classes.
     *
     * use the ExecutorService to perform thread tasks without having to create Thread objects directly
     *
     * @see Runnable
     * @see Thread
     */
    @Test
    public void test_ThreadCreation() {
        System.out.println(Thread.currentThread().getName()+ " is running");
        new Thread(() -> {System.out.println(Thread.currentThread().getName()+ " is running");}
                , "Lambda Runable").start();

    }

    /**
     * Polling is the process of intermittently checking data at some fixed interval.
     * use sleep() to not ties up CPU resource
     */
    @Test
    public void test_Sleep() {
        new Thread(() -> {
            for (int i = 0; i < 1_000_000; i++) ConcurrencyTest.counter++;
        }).start();
        while (counter < 800_000) {
            System.out.println("No yet reach");
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("Finally reach");
    }
    static int counter;

}
