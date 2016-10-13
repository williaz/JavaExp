package thread;

/**
 * Created by williaz on 10/13/16.
 */
public class MaxPriorityDemo {
    public static void main (String [] args)
    {
        ThreadGroup tg = new ThreadGroup ("A");
        System.out.println ("tg maximum priority = " + tg.getMaxPriority ());//10
        Thread t1 = new Thread (tg, "X");
        System.out.println ("t1 priority = " + t1.getPriority ());//5
        t1.setPriority (Thread.NORM_PRIORITY + 1);
        System.out.println ("t1 priority after setPriority() = " +
                t1.getPriority ());//6
        tg.setMaxPriority (Thread.NORM_PRIORITY - 1);
        System.out.println ("tg maximum priority after setMaxPriority() = " +
                tg.getMaxPriority ());//4
        System.out.println ("t1 priority after setMaxPriority() = " +
                t1.getPriority ());//4-------->6
        Thread t2 = new Thread (tg, "Y");
        System.out.println ("t2 priority = " + t2.getPriority ());//4
        t2.setPriority (Thread.NORM_PRIORITY);
        System.out.println ("t2 priority after setPriority() = " +
                t2.getPriority ());//4
    }
}
