package socket;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.*;

/**
 * Created by williaz on 10/25/16.
 */
public class Randomizer {
    public static void main(String[] args) throws IOException {

        Socket echoSocket = null;
        ObjectOutputStream out = null;
        ObjectInputStream in = null;

        try {

            echoSocket = new Socket("127.0.0.1", 10007);

            out = new ObjectOutputStream(echoSocket.getOutputStream());
            in = new ObjectInputStream(echoSocket.getInputStream());

        } catch (UnknownHostException e) {
            System.err.println("Don't know about this host");
            System.exit(1);
        } catch (IOException e) {
            System.err.println("Couldn't get I/O for "
                    + "the connection to the host.");
            System.exit(1);
        }


        //generate 20 random positive integer queue
        Queue<Integer> randomInts = new LinkedList<>();

        for(int i =0; i <20 ; i++) {
            Random random = new Random();
            int positiveRandomInteger = random.nextInt(Integer.MAX_VALUE) + 1;
            randomInts.add(positiveRandomInteger);
        }


        System.out.println ("Sending random positive integer queue: " + randomInts.toString() + " to Primes Server");
        out.writeObject(randomInts);
        out.flush();
        System.out.println ("Send random positive integer queue, waiting for return value");

        Map<Integer, Boolean> answers = null;

        try {
            answers = (Map<Integer, Boolean>) in.readObject();
        }
        catch (Exception ex)
        {
            System.out.println (ex.getMessage());
        }

        Set<Integer> set = answers.keySet();
        for(Integer i: set){
            System.out.println("The Number "+ i + " is " + answers.get(i)+ " Prime ");
        }

        out.close();
        in.close();
        echoSocket.close();
    }
}
