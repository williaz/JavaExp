package mt.thread;

/**
 * Created by williaz on 10/10/16.
 */
public class PriorityDemo {

    public static void main (String [] args)
    {
        BlockingThread bt = new BlockingThread ();
        bt.setPriority (Thread.NORM_PRIORITY + 1);
        CalculatingThread ct = new CalculatingThread ();
        bt.setName("Blocking Thread");
        ct.setName("Calculating Thread");
        bt.start ();
        ct.start ();
        System.out.println(Thread.currentThread().getName()+" doing.");
        try
        {
            Thread.sleep (5000);
        }
        catch (InterruptedException e)
        {
        }
        bt.setFinished (true);
        ct.setFinished (true);
        System.out.println(Thread.currentThread().getName()+" doing. End");
    }

private static class BlockingThread extends Thread
{
    private boolean finished = false;
    public void run ()
    {
        while (!finished)
        {
            try
            {
                System.out.println(Thread.currentThread().getName()+" doing.");
                int i;
                do
                {
                    i = System.in.read ();
                    System.out.print (i + " ");
                }
                while (i != '\n');

                System.out.print ('\n');
            }
            catch (java.io.IOException e)
            {
            }
        }
    }
    public void setFinished (boolean f)
    {
        finished = f;
    }
}
private static class CalculatingThread extends Thread
{
    private boolean finished = false;
    public void run ()
    {
        System.out.println(Thread.currentThread().getName()+" doing.");
        int sum = 0;
        while (!finished)
            sum++;
    }
    public void setFinished (boolean f)
    {
        finished = f;
    }
}
}
