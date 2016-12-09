package mt.thread;

/**
 * Created by williaz on 10/1/16.
 */
public class SleepMessages {


    public static void main(String[] args) throws InterruptedException {
        String importantInfo[] = {
                "Mares eat oats",
                "Does eat oats",
                "Little lambs eat ivy",
                "A kid will eat ivy too"
        };

        for (int i=0; i<importantInfo.length; i++){
            Thread.sleep(400);
            //Thread.sleep(0,4000);
            System.out.println(importantInfo[i]);

        }

    }
}
