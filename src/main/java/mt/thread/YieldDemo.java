package mt.thread;

/**
 * Created by williaz on 10/11/16.
 * producer-consumer using yield sample, no relating moniter
 */
public class YieldDemo extends Thread
{
    static boolean finished = false;
    static int sum = 0;
    static boolean consumed = false;


    public static void main (String [] args)
    {
        new YieldDemo ().start ();
        for (int i = 1; i <= 50000; i++)
        {
            while(consumed) {
                sum++;
                consumed=false;
                Thread.yield();
            }
        }
        finished = true;//time uncontrollable---->
    }
    public void run ()
    {
        while (!finished && !consumed){
            System.out.println ("sum = " + sum);
            consumed=true;
            Thread.yield();
        }

    }
}