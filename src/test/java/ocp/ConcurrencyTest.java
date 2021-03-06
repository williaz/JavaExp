package ocp;

import org.junit.Test;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.SystemEnvironmentPropertySource;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.ConcurrentModificationException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by williaz on 12/6/16 - 12/10 4.5d.
 * watch out:
 * 1. Concurrent collections no fail fast
 * ＃ ExecutorService VS ScheduledExecutorService
 * 2. ScheduleExecutor only schedule() can take Callable<T> as argument,
 *    scheduleAtFixedRate() and scheduleWithFixedDelay() only take Runnable
 * 3. sorted() affect on single thread --
 * 4. reduce(U identity, BiFunction<U, ? extends T, U> accum, BiOperator<U> comb): accum first parameter is identity type
 * 5. be wary of ForkJoin base case - StackOverflow
 * 6. Collectors for Concurrent faces non-parallel stream -- no exception
 * 7. catch InterruptException, related to timeout, threading waiting, sleeping!
 * 8. shut down Executor, otherwise the code will wait forever, no terminate
 * 9. resource-heavy tasks benefits more than CPU-intensive tasks from parallel
 * 10. ExecutorService: void execute(), Future<? / T> submit(Runnable / Callable);
 *     invokeAll(), invokeAny() only take Callable
 * # StackOverFlowError
 */
public class ConcurrencyTest {
    /**e
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
        new Thread(() -> System.out.println(Thread.currentThread().getName()+ " is running")
                , "Lambda Runable").start();

    }

    /**
     * Polling is the process of intermittently checking data at some fixed interval.
     * use sleep() to not ties up CPU resource, but no lose lock
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
     * The shutdown process for a thread executor involves first rejecting any new tasks submitted to the thread executor
     *    while continuing to execute any previously submitted tasks.
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
     * T invokeAny(Collection<? extends Callable<T>> tasks) throws InterruptedException, ExecutionException;
     *     cancelling any unfinished tasks
     * @see ExecutorService
     */
    @Test
    public void test_Invoke() {
        ExecutorService service = null;
        List<Callable<Integer>> tasks = new ArrayList<>();
        tasks.add(() -> 1 + 5);
        tasks.add(() -> 2 + 6);
        tasks.add(() -> 3 + 7);

        try{
            service = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
            Integer result = service.invokeAny(tasks);
            System.out.println(result);

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } finally {
            if (service != null) service.shutdown();
        }
    }

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
            System.err.println("GET:"+ result.get(10, TimeUnit.MILLISECONDS)); //return null
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
            pool = Executors.newCachedThreadPool();
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

    /**
     * thread safety is the property of an object that guarantees safe execution by multiple threads at the same time.
     * Atomic is the property of an operation to be carried out as a single unit of execution
     *        without any interference by another thread.
     *
     * AtomicBoolean, AtomicInteger, AtomicLong, AtomicReference;
     * AtomicIntegerArray, AtomicLongArray, AtomicReferenceArray;
     *
     * get(), set()
     * getAndSet(newValue): set the new, get the old
     * incrementAndGet(): ++i
     * getAndIncrement(): i++
     * decrementAndGet(): --i
     * getAndDecrement(): i--
     *
     * @see AtomicInteger
     */
    @Test
    public void test_AtomicClass() throws InterruptedException {
        ExecutorService service = null;
        try {
            service = Executors.newFixedThreadPool(10);
            Counter counter = new Counter();
            for (int i = 0; i < 10; i++) {
                service.submit(() -> counter.countAndReport()); //may duplicate
            }
            service.awaitTermination(100, TimeUnit.MILLISECONDS);
        } finally {
            if (service != null) service.shutdown();
        }

        System.out.println("\n----------");
        ExecutorService service1 = null;
        try {
            service1 = Executors.newFixedThreadPool(10);
            AtomicCounter counter = new AtomicCounter();
            for (int i = 0; i < 10; i++) {
                service1.submit(() -> counter.countAndReport()); //no dup, but random order
            }
            service1.awaitTermination(100, TimeUnit.MILLISECONDS);
        } finally {
            if (service1 != null) service1.shutdown();
        }


    }

    public static class Counter {
        private int count = 0;
        private void countAndReport() {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.print((++count) + " ");
        }
    }

    public static class AtomicCounter {
        private AtomicInteger count = new AtomicInteger(0);
        private void countAndReport() {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.print(count.incrementAndGet() + " ");
        }
    }

    public static class SynBlcCounter {
        private int count = 0;
        private void countAndReport() {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            synchronized (this) {
                System.out.print((++count) + " ");
            }
        }
    }

    public static class SynMhdCounter {
        private int count = 0;
        private synchronized void countAndReport() {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.print((++count) + " ");
        }
    }

    /**
     * A monitor is a structure that supports mutual exclusion or the property
     *   that at most one thread is executing a particular segment of code at a given time.
     * any Object can be used as a monitor;
     * synchronized block, synchronized method;
     * You can use static synchronization if you need to order thread access across all instances,
     *   rather than a single instance.
     * synchronization is about making multiple threads perform in a more single-threaded manner.
     * Synchronization is about protecting data integrity at the cost of performance.
     */
    @Test
    public void test_Synchronized() throws InterruptedException {
        ExecutorService service = null;
        try {
            service = Executors.newFixedThreadPool(10);
            SynBlcCounter counter = new SynBlcCounter();
            for (int i = 0; i < 10; i++) {
                service.submit(() -> counter.countAndReport()); //in order
            }
            service.awaitTermination(100, TimeUnit.MILLISECONDS);
        } finally {
            if (service != null) service.shutdown();
        }

        System.out.println("\n----------");
        ExecutorService service1 = null;
        try {
            service1 = Executors.newFixedThreadPool(10);
            SynMhdCounter counter = new SynMhdCounter();
            for (int i = 0; i < 10; i++) {
                service1.submit(() -> counter.countAndReport()); //in order
            }
            service1.awaitTermination(1500, TimeUnit.MILLISECONDS); //slow one
        } finally {
            if (service1 != null) service1.shutdown();
        }

    }

    /**
     * ConcurrentHashMap
     */
    @Test
    public void test_ConcurrentCollection() throws InterruptedException {
        //PriceTipe tipe = new PriceTipe(); //???
        ConcurrentPriceTipe tipe = new ConcurrentPriceTipe();
        Callable<Double> task = () -> {
            tipe.add("APPL", 120.1);
            return tipe.getPrice("APPL");
        };
        Callable<Double> task1 = () -> {
            tipe.add("FB", 130.4);
            return tipe.getPrice("FB");
        };
        Callable<Double> task4 = () -> {
            tipe.add("FB", 150.4);
            return tipe.removePrice("APPL");
        };

        Callable<Double> task2 = () -> {
            tipe.add("GS", 220.8);
            return tipe.getPrice("GS");
        };
        Callable<Double> task5 = () -> {
            tipe.add("APPL", 130.1);
            return tipe.removePrice("GS");
        };

        List<Callable<Double>> tasks = new ArrayList<>();
        tasks.add(task);  // APPL add -> get
        tasks.add(task1); //FB add -> get
        tasks.add(task4); //add FB -> remove APPL
        tasks.add(task2); //GS add -> get
        tasks.add(task5); //add APPL -> remove GS

        ExecutorService service = null;
        try {
            service = Executors.newFixedThreadPool(5);
            List<Future<Double>> values = service.invokeAll(tasks); //invokaAll and invokeAny for Callable only
            System.out.println("APPL - FB - APPL - GS - GS");
            for (Future<Double> v : values) {
                System.out.print(v.get() + " ");
            }
        } catch (ExecutionException e) {
            e.printStackTrace();
        } finally {
            if (service != null) service.shutdown();
        }


    }

    public static class PriceTipe {
        private Map<String, Double> prices = new HashMap<>();

        public Double getPrice(String key) {
            return prices.get(key);
        }

        public Double removePrice(String key) {
            return prices.remove(key); // removed V
        }

        public void add(String key, Double price) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            prices.put(key, price);
        }
    }

    public static class ConcurrentPriceTipe {
        private Map<String, Double> prices = new ConcurrentHashMap<>();

        public Double getPrice(String key) {
            return prices.get(key);
        }

        public Double removePrice(String key) {
            return prices.remove(key); // removed V
        }

        public void add(String key, Double price) {
            prices.put(key, price);
        }
    }

    /**
     * A memory consistency error occurs when two threads have inconsistent views of what should be the same data.
     *   <- concurrent collection solve
     * When two threads try to modify the same non-concurrent collection,
     *    the JVM may throw a ConcurrentModificationException at runtime.
     * ConcurrentHashMap is ordering read/write access such that all access to the class is consistent.
     * At any given instance, all threads should have the same consistent view of the structure of the collection.
     *
     * You should use a concurrent collection class anytime that you are going to have
     *     multiple threads modify a collections object outside a synchronized block or method
     * a good practice to instantiate a concurrent collection but
     *     pass it around using a non-concurrent interface whenever possible
     *
     * @see ConcurrentHashMap
     * @see java.util.concurrent.ConcurrentLinkedDeque
     * @see java.util.concurrent.ConcurrentLinkedQueue
     *
     * @see java.util.concurrent.ConcurrentMap
     */
    @Test
    public void test_ConcurrentMap() {
        ConcurrentMap<String, Character> scores = new ConcurrentHashMap<>();
        scores.put("CS", 'A');
        scores.put("IT", 'A');
        scores.put("MATH", 'A');
        scores.put("EN", 'B');
        for (String s : scores.keySet()) { //keySet() is updated as soon as an object is removed
            if (s.equals("IT"))
                scores.remove(s);
        }

    }

    @Test(expected = ConcurrentModificationException.class)
    public void test_Map_FastFail() {
        Map<String, Character> scores = new HashMap<>();
        scores.put("CS", 'A');
        scores.put("IT", 'A');
        scores.put("MATH", 'A');
        scores.put("EN", 'B');
        for (String s : scores.keySet()) {
            if (s.equals("IT"))
                scores.remove(s);
        }

    }

    /**
     * The BlockingQueue is just like a regular Queue, except that
     *     it includes methods that will wait a specific amount of time to complete an operation.
     * LinkedBlockingQueue implements BlockingQueue:
     * offer(E e, long timeout, TimeUnit unit);
     * poll(long timeout, TimeUnit unit);
     * LinkedBlockingDeque: implements BlockDeque:
     * offerFirst, offerLast;
     * pollFirst, pollLast;
     *
     * @see java.util.concurrent.BlockingDeque
     * @see java.util.concurrent.BlockingQueue
     * @see java.util.concurrent.LinkedBlockingDeque
     * @see java.util.concurrent.LinkedBlockingQueue
     */
    @Test
    public void test_BlockingDeque() throws InterruptedException {
        BlockingDeque<Integer> nums = new LinkedBlockingDeque<>(2);
        Runnable sleeping = () -> {
            synchronized (nums) {
                try {
                    Thread.sleep(1000);

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };

        ExecutorService service = null;
        try {
            service = Executors.newFixedThreadPool(4);

            service.execute(() -> {
                try {
                    for (int i = 0; i < 5; i++)
                        nums.offerFirst(45, 10, TimeUnit.MILLISECONDS); //NANO then 3 null
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
            service.execute(() -> {
                try {
                    for (int i = 0; i < 5; i++)
                        System.out.println(nums.pollFirst(10, TimeUnit.MILLISECONDS));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
            service.awaitTermination(2000, TimeUnit.MILLISECONDS);

        } finally {
            if (service != null) service.shutdown();
        }
    }

    /**
     * The SkipList classes, ConcurrentSkipListSet and ConcurrentSkipListMap,
     *     are concurrent versions of their sorted counterparts, TreeSet and TreeMap, respectively.
     * SortedMap or NavigableSet.
     * @see java.util.concurrent.ConcurrentSkipListMap
     * @see java.util.concurrent.ConcurrentSkipListSet
     */

    /**
     * These classes copy all of their elements to a new underlying structure
     *     anytime an element is added, modified, or removed from the collection.
     *  By a modified element, we mean that the reference in the collection is changed.
     *  Modifying the actual contents of the collection will not cause a new structure to be allocated.
     *
     *  Any iterator established prior to a modifi cation will not see the changes,
     *     but instead it will iterate over the original elements prior to the modification
     *  They are commonly used in multi-threaded environment situations where reads are far more common than writes.
     *
     *  @see java.util.concurrent.CopyOnWriteArrayList
     *  @see java.util.concurrent.CopyOnWriteArraySet
     */
    @Test
    public void test_CopyOnWrite() {
        List<Integer> list = new CopyOnWriteArrayList<>();
        list.add(43);
        list.add(14);
        list.add(13);

        for (Integer i : list) {
            System.out.print(i +  " ");
            list.add(1, 7);
        }
        System.out.println("\n ---------");
        for (Integer i : list) {
            System.out.print(i +  " ");
        }

    }

    /**
     * methods defined in the Collections class, contain synchronized methods that operate on the inputted collection
     *    and return a reference that is the same type as the underlying collection.
     * if you are given an existing collection that is not a concurrent class
     *    and need to access it among multiple threads, you can wrap it using the methods
     *
     * they do not synchronize access on any iterators that you may create from the synchronized collection.
     *    <- synchronization block
     *
     * synchronizedCollection(Collection<T> c);
     * synchronizedList(List<T> l);
     * synchronizedSet(Set<T> s);
     * synchronizedMap(Map<K, V> m);
     * synchronizedSortedSet(SortedSet<T> s);
     * synchronizedSortedMap(SortedMap<K, V> m);
     * synchronizedNavigableSet(NavigableSet<T> s);
     * synchronizedNavigableMap(NavigableMap<K, V> m)
     * @see java.util.Collections
     */
    @Test
    public void test_SynchronizedMethod() throws InterruptedException {
        List<Integer> list = new ArrayList<>();
        list.add(34);
        list.add(25);
        list.add(16);
        List<Integer> synList = Collections.synchronizedList(list);
        ExecutorService service = null;
        try{
            service = Executors.newFixedThreadPool(2);
            service.execute( () -> {
                synchronized (synList) { //without then ConcurrentModificationException
                    for (int i : synList) {
                        System.out.print(i + " ");
                        try {
                            Thread.sleep(100);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            });
            service.execute(() -> {
               for (int i = 0; i < 2; i++) {
                   synList.add(55);
               }
            });
            service.awaitTermination(1000, TimeUnit.MILLISECONDS);
        } finally {
            if (service != null) service.shutdown();
        }

    }

    /**
     * A serial stream is a stream in which the results are ordered,
     *   with only one entry being processed at a time.
     * A parallel stream is a stream that is capable of processing results concurrently,
     *   using multiple threads.
     * By default, the number of threads available in a parallel stream is related to
     *   the number of available CPUs in your environment.
     * parallel() : intermediate operation
     * parallelStream()
     * isParallel()
     * Stream.concat(Stream s1, Stream s2) is parallel if either s1 or s2 is parallel.
     * flatMap() creates a new stream that is not parallel by default
     *
     * stream operations that occur before/after the forEachOrdered() can still gain performance improvements
     *   for using a parallel stream.
     * Scaling is the property that, as we add more resources such as CPUs, the results gradually improve.
     * AVOID STATEFUL LAMBDA EXPRESSIONS
     * A stateful lambda expression is one whose result depends on any state that might change
     *   during the execution of a pipeline.
     * Anytime you are working with a collection with a parallel stream,
     *   it is recommended that you use a concurrent collection.
     */
    @Test
    public void test_Parallel() {
        Stream<Integer> parallelStream = Arrays.asList(1, 2, 3, 4, 5, 6).parallelStream();
        parallelStream.forEach((i) -> System.out.print(i + " ")); //is equivalent to submitting multiple Runnable lambda expressions to a pooled thread executor.
        System.out.println();
        Arrays.asList(1, 2, 3, 4, 5, 6).parallelStream().unordered().forEachOrdered((i) -> System.out.print(i + " "));

        System.out.println();
        //two threads both trigger the array to be resized at the same time, a result can be lost,
        //List<Integer> list = new ArrayList<>(); //NullPointerException
        List<Integer> list = Collections.synchronizedList(new ArrayList<>());
        Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9 , 10, 11, 12, 13)
                .parallelStream()
                .forEach(i -> {
                    for (int j = 0 ; j <= i ; j++ )
                        list.add(j);
                });
        System.out.println();
        for (int i : list) {
            System.out.print(i + " ");
        }

    }

    /**
     * Reduction operations on parallel streams are referred to as parallel reductions.
     * findFirst(), limit(), or skip() : slower in parallel <- synchronized-like
     * unordered() to improve parallel stream
     * it is recommended that you use the three-argument version of reduce() when working with parallel streams.
     * reduce() to be in order:
     * 1. identity: defined logic no content
     * 2. accumulator: associative and stateless
     * 3. combiner: associative, stateless, compatible with identity
     */
    @Test
    public void test_ParallelReduce() {
        System.out.println(Arrays.asList("w","o","l","f", " ", "i", "s", " ", "d", "a", "n", "g", "o", "u", "s")
                .parallelStream().unordered()
                .reduce("_", (c, s1) -> {
                    try {
                        if ("o".equals(s1))
                              Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println(Thread.currentThread().getName()+ " " + c+s1);
                    return c + s1;
                }, (s2, s3) -> {
                    try {
                        if (s2.contains("s"))
                            Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println(Thread.currentThread().getName()+ " " + s2 + s3);
                    return s2 + s3;
                })); // TODO still in order
                //.reduce("",String::concat, String::concat));
    }

    /**
     * use a concurrent collection to combine the results,
     *   ensuring that the results of concurrent threads do not cause a ConcurrentModificationException.
     *
     * Requirements for Parallel Reduction with collect():
     * 1. The stream is parallel.
     * 2. The parameter of the collect operation has the Collector.Characteristics.CONCURRENT characteristic.
     * 3. Either the stream is unordered, or the collector has the characteristic Collector.Characteristics.UNORDERED.
     *
     * The Collectors class includes two sets of methods for retrieving collectors
     *     that are both UNORDERED and CONCURRENT,
     *     Collectors.toConcurrentMap() and Collectors.groupingByConcurrent(),
     */
    @Test
    public void test_ParallelCollect() {
        Stream<String> nameStream = Stream.of("Will", "Bill", "Yelp", "Monica", "Finance").parallel();
        Set<String> names = nameStream.collect(Collectors.toSet()); //will not be performed as a concurrent reduction.
        System.out.println(Collectors.toSet().characteristics());
        System.out.println(names);

        ConcurrentMap<Character, List<String>> alpha = Stream.of("Will", "William", "Bill", "Edison", "Harrison", "Eason")
                .parallel().collect(Collectors.groupingByConcurrent(i -> i.charAt(0)));
        System.out.println(alpha);
    }

    /**
     * The CyclicBarrier takes in its constructors a limit value, indicating the number of threads to wait for.
     * As each thread finishes, it calls the await() method on the cyclic barrier.
     * Once the specified number of threads have each called await(), the barrier is released and all threads can continue.
     * If you are using a thread pool, make sure that you set the number of available threads to be
     *   at least as large as your CyclicBarrier limit value.
     * # all threads stop and wait at logical barriers.
     * @see CyclicBarrier
     */
    @Test
    public void test_CyclicBarrier() {
        ExecutorService service = null;
        try {
            service = Executors.newFixedThreadPool(4);
            CyclicBarrier barrier = new CyclicBarrier(3,
                    () -> System.out.println(Thread.currentThread().getName()+ " Finished"));
            for (int i = 0; i < 6; i++) {
                service.execute( () -> collaborateTasks(barrier));
            }

        } finally {
            if (service != null) service.shutdown();
        }


    }

    public void collaborateTasks(CyclicBarrier b) {

        try {
            System.out.println("first task");
            b.await();
            System.out.println("second task");
            b.await();
            System.out.println("third task");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (BrokenBarrierException e) {
            e.printStackTrace();
        }

    }

    /**
     * The fork/join framework relies on the concept of recursion to solve complex tasks.
     * Recursion is the process by which a task calls itself to solve a problem.
     *
     * A recursive solution is constructed with a base case and a recursive case:
     * 1. Base case: A non-recursive method that is used to terminate the recursive path
     * 2. Recursive case: A recursive method that may call itself one or multiple times to solve a problem
     *
     * Applying the fork/join framework requires us to perform three steps:
     * 1. Create a ForkJoinTask.
     * 2. Create the ForkJoinPool.
     * 3. Start the ForkJoinTask.
     *
     * The goal of the fork/join framework is to break up large tasks into smaller ones
     *
     * @see java.util.concurrent.ForkJoinTask
     * @see java.util.concurrent.RecursiveAction
     * @see java.util.concurrent.RecursiveTask
     *
     * @see BinaryRecursiveSearch
     */
    @Test
    public void test_RecursiveAction() {
        int size = 2000;
        int target = 573;
        Random random = new Random();
        int[] nums = new int[size];
        for (int i = 0; i < size; i++)
            nums[i] = random.nextInt(4000); //[0, 4000)

        long timer1 = System.currentTimeMillis();

        ForkJoinTask<?> task = new BinaryRecursiveSearch(0, size, nums, target);
        ForkJoinPool pool = new ForkJoinPool();
        pool.invoke(task);

        timer1 = System.currentTimeMillis() - timer1;

        long timer2 = System.currentTimeMillis();
        for (int i = 0; i < size; i++)
            if (nums[i] == target) System.out.println("Found out also at " + i);
        timer2 = System.currentTimeMillis() - timer2;

        System.out.println(timer1 + " VS " + timer2); //shame on u ~
    }

    /**
     * The fork() method instructs the fork/join framework to complete the task in a separate thread,
     * while the join() method causes the current thread to wait for the results.
     *
     * make sure that fork() is called before the current thread begins a sub-task
     *   and that join() is called after it finishes retrieving the results,
     *   in order for them to be done in parallel.
     *
     * The fork() method should be called before the current thread performs a compute() operation,
     *    with join() called to read the results afterward.
     * Since compute() takes no arguments, the constructor of the class is often used to pass instructions to the task.
     *
     * @see RecursiveFactorialTask
     */
    @Test
    public void test_RecursiveTask() {
        ForkJoinTask<Long> factorial = new RecursiveFactorialTask(0, 15);
        ForkJoinPool pool = new ForkJoinPool();
        long result = pool.invoke(factorial);
        System.out.print(result);
    }

    /**
     * Liveness is the ability of an application to be able to execute in a timely manner.
     * deadlock, starvation, and livelock.
     * 1. Deadlock occurs when two or more threads are blocked forever, each waiting on the other.
     *    -> avoid: to order their resource requests.
     * 2. Starvation occurs when a single thread is perpetually denied access to a shared resource or lock.
     * 3. Livelock occurs when two or more threads are conceptually blocked forever,
     *         although they are each still active and trying to complete their task.
     *    Livelock is often a result of two threads trying to resolve a deadlock.
     *    stuck in an endless cycle.
     *
     * A race condition is an undesirable result that occurs when two tasks,
     *   which should be completed sequentially, are completed at the same time.
     *   -> 1. use a monitor to synchronize on the relevant overlapping task.
     *      2. use singletons to coordinate access to shared resources.
     */

}
