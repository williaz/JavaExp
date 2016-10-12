package thread;

/**
 * Created by williaz on 10/12/16.
 */
public class ThreadGroupDemo {

    public static void main (String [] args)
    {
        ThreadGroup tg = new ThreadGroup ("subgroup 1");// main -> sub 1-> thd 1, 2,3
        Thread t1 = new Thread (tg, "thread 1");
        Thread t2 = new Thread (tg, "thread 2");
        Thread t3 = new Thread (tg, "thread 3");
        tg = new ThreadGroup ("subgroup 2");
        Thread t4 = new Thread (tg, "my thread");  //  main->sub2 -> my thd
        tg = Thread.currentThread ().getThreadGroup ();  //main
        int agc = tg.activeGroupCount ();  // 2
        System.out.println ("Active thread groups in " + tg.getName () +
                " thread group: " + agc);
        tg.list ();  //details on the tg thread's thread group and all subgroups.
    }
}
