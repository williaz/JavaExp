package ocp;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

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
            for (int i = 0; i < 1_000_000; i++) counter++;
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
    private static int counter;

    /**
     * ExecutorService to create and manage Threads,
     * Executors factory
     * With a single-thread executor, results are guaranteed to be executed in the order
     *     in which they are added to the executor service.
     *
     * The shutdown process for a thread executor involves first rejecting any new tasks submitted to the thread executor while continuing to execute any previously submitted tasks.
     * Active -> Shutting down -> Shutdown
     * 1. During Shutting down time, calling isShutdown() will return true, while isTerminated() will return false.
     *    If a new task is submitted to the thread executor while it is shutting down, a RejectedExecutionException will be thrown.
     * 2. Once all active tasks have been completed, isShutdown() and isTerminated() will both return true.
     * Remember that failure to shut down a thread executor after at least one thread has been created will result in the program hanging
     *
     */
    @Test(expected = RejectedExecutionException.class)
    public void test_SingleThreadExecutor() {
        ExecutorService service = null;
        try {
            service = Executors.newSingleThreadExecutor();
            System.out.println("starting");
            service.execute(() -> System.out.println("service printing!"));
            service.execute(() -> {
                for(int i = 0; i < 300; i++) {
                    System.out.println("printed "+ i + " times");
                    try {
                        Thread.sleep(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

            });
            service.execute(() -> System.out.println("that is it."));
            service.execute(() -> System.out.println("print work finished!"));
            System.out.println("end");
        } finally {
            if (service != null) service.shutdown();
            System.out.println("Shutting down: " + service.isShutdown());
            System.out.println("Terminating: "+ service.isTerminated());
            List<Runnable> todoList = service.shutdownNow();
            for (Runnable r : todoList) System.out.println("left task: " + r);
            service.execute(() -> System.err.println("Can I?"));

        }
    }

    /**
     *
     * The execute() method takes a Runnable lambda expression or instance and completes the task asynchronously.
     *     "fire-and-forget "
     * submit() returns a Future object that can be used to determine if the task is complete.
     *     It can also be used to return a generic result object after the task has been completed.
     * invokeAll() method will wait indefinitely until all tasks are complete,
     * invokeAny() method will wait indefinitely until at least one task completes.
     *     -> both can take a timeout value and TimeUnit parameter.
     *
     * void execute(Runnable r);
     * Future<?> submit(Runnable r);
     * Future<T> submit(Callable<T>< c);
     * List<Future<T>> invokeAll(Collection<? extends Callable<T>> tasks) throws InterruptedException;
     *     synchronously returning the results, same order
     * T invokeAll(Collection<? extends Callable<T>> tasks) throws InterruptedException, ExecutionException;
     *     cancelling any unfinished tasks
     * @see ExecutorService
     */

    /**
     * Future<T>
     * the return type of Runnable.run() is void, the get() method always returns null.
     *
     * boolean isDone() : completed, exception, cancelled
     * boolean isCancelled() : cancelled before completed
     * boolean cancel() : cancel
     * V get() : result
     * V get(long timeout, TimeUnit unit) : TimeUnit.DAYS -.. - TimeUnit.MILLISECONDS - MICROSECONDS - NANOSECONDS
     * @see Future
     */
    @Test
    public void test_Future() throws InterruptedException {
        ExecutorService service = null;
        try {
            service = Executors.newSingleThreadExecutor();
            Future<?> result = service.submit(() -> {
                for (int i = 0; i < 500; i++) System.out.println(i + "times");
            });
            System.err.println(result.get(10, TimeUnit.MILLISECONDS));
            System.out.println("get here!");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        } finally {
            if (service != null) service.shutdown();
        }

        if (service != null) {
            service.awaitTermination(10, TimeUnit.MILLISECONDS);
            if (service.isTerminated())
                System.out.println("All finished");
            else
                System.out.println("still running");

        }
    }

    /**
     * Callable<T> : FunctionalInterface
     * call() method returns a value and can throw a checked exception.
     * Supplier VS Callable:
     *  call() throws Exception
     *  ce - When the compiler is unable to assign a functional interface to a lambda expression,
     *       it is referred to as an ambiguous lambda expression. <- explicit cast
     *
     * ce - Runnable methods do not support checked exceptions, if body contains
     * @see java.util.concurrent.Callable
     * @see java.util.function.Supplier
     */
    @Test
    public void test_Callable() throws ExecutionException, InterruptedException {
        ExecutorService service = null;
        try {
            service = Executors.newSingleThreadExecutor();
            Future<Integer> result = service.submit(() -> {
                Thread.sleep(10); // only Callable only, Runnable -> ce if without try-catch
                return 12 + 74;
            });
            System.out.println(result.get());
        } finally {
            if (service != null) service.shutdown();
        }
    }

    /**
     * ScheduledExecutor:
     * schedule a task to happen at some future time, to happen repeatedly at some set interval.
     *
     * schedule(Callable<T> c, long delay, TimeUnit unit);
     * schedule(Runnable r, long delay, TimeUnit unit);
     * scheduleAtFixedRate(Runnable r, long initDelay, long period, TimeUnit unit); regardless of whether or not the previous task finished.
     * scheduleWithFixedDelay(Runnable r, long initDelay, long delay, TimeUnit unit); creates a new task after th previous task has finished.
     *     only Runnable, as may run infinitely
     * @see ScheduledExecutorService
     */
    @Test
    public void test_Schedule() throws ExecutionException, InterruptedException {
        ScheduledExecutorService service = null;
        try{
            service = Executors.newSingleThreadScheduledExecutor();
            Future<Integer> future = service.schedule(() -> 43 + 1, 20, TimeUnit.MILLISECONDS);
            System.out.println(future.get());
        } finally {
            if (service != null) service.shutdown();
        }

    }

    @Test
    public void test_ScheduleRunnable() throws ExecutionException, InterruptedException {
        ScheduledExecutorService service = null;
        try{
            service = Executors.newSingleThreadScheduledExecutor();
            service.schedule(() -> System.out.println("Delayed run"), 20, TimeUnit.MILLISECONDS);
            service.awaitTermination(60, TimeUnit.MILLISECONDS);
        } finally {
            if (service != null) service.shutdown();
        }

    }

    @Test
    public  void test_ScheduleAtFixedRate() throws InterruptedException {
        ScheduledExecutorService service = null;
        try{
            service = Executors.newSingleThreadScheduledExecutor();
            service.scheduleAtFixedRate(() -> {
                System.out.println("time to check!"); // 4 times
                try {
                    Thread.sleep(10); //10ms
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }, 20,10, TimeUnit.MILLISECONDS);
            service.awaitTermination(60, TimeUnit.MILLISECONDS); // 60ms = run + period(10ms) + new run + p + nr + p + nr + p
            System.out.println("Routine");
        } finally {
            if (service != null) service.shutdown();
        }

    }

    @Test
    public  void test_ScheduleWithFixedDelay() throws InterruptedException {
        ScheduledExecutorService service = null;
        try{
            service = Executors.newSingleThreadScheduledExecutor();
            service.scheduleWithFixedDelay(() -> {
                System.out.println("time to feed!"); // 2 times!
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }, 20,10, TimeUnit.MILLISECONDS);
            service.awaitTermination(60, TimeUnit.MILLISECONDS); //60 ms = 20ms + run(10ms) + delay(10ms) + new run + delay
            System.out.println("doing");
        } finally {
            if (service != null) service.shutdown();
        }

    }

    /**
     * A thread pool is a group of pre-instantiated reusable threads that are available to perform a set of arbitrary tasks.
     * Executors static:
     * ExecutorService newSingleThreadExecutor();
     * ExecutorService newCachedThreadPool(); create new thread as needed, unbound size, also will reuse
     *                                        when require executing many short-lived asynchronous tasks
     * ExecutorService newFixedThreadPool(int num);
     *
     * ScheduledExecutorService newSingleThreadScheduledExecutor();
     * ScheduledExecutorService newScheduledExecutorPool(int num); fix the scheduleAtFixedRate()'s endless thread risk
     *
     */
    @Test
    public void test_ThreadPool() throws InterruptedException {
        //the number of CPUs available is used to determine the thread size:
        System.out.println(Runtime.getRuntime().availableProcessors());
        ExecutorService pool = null;
        List<Callable<Long>> tasks = new ArrayList<>();
        tasks.add(() -> {
            long mup = 1;
            for (int i = 1; i < 101; i++) {
                mup += i;
            }
            return mup;
        } );
        tasks.add(() -> {
            long mup = 1;
            for (int i = 101; i < 201; i++) {
                mup += i;
            }
            return mup;
        } );
        tasks.add(() -> {
            long mup = 1;
            for (int i = 201; i < 301; i++) {
                mup += i;
            }
            return mup;
        } );
        try {
            pool = Executors.newFixedThreadPool(5);
            List<Future<Long>> result = pool.invokeAll(tasks);
            //pool.awaitTermination(1, TimeUnit.SECONDS);
            for (Future<Long> r : result) {
                System.out.println(r.get()); // in same order
            }
        } catch (ExecutionException e) {
            e.printStackTrace();
        } finally {
            if (pool != null) pool.shutdown();
        }
    }

}
