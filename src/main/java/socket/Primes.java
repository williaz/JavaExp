package socket;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.Queue;

/**
 * Created by williaz on 10/25/16.
 */
public class Primes {

    public static boolean isPrime(int n) {
        if (n == 1 ){
            return false;
        }
        //check if n is a multiple of 2
        if (n % 2 == 0){
            return false;
        }
        //if not, then just check the odds
        for(int i = 3;i * i <= n;i += 2) {
            if(n % i == 0)
                return false;
        }
        return true;
    }


    public static void main(String[] args) throws IOException
    {
        ServerSocket serverSocket = null;

        try {
            serverSocket = new ServerSocket(10007);
        }
        catch (IOException e)
        {
            System.err.println("Could not listen on port: 10007.");
            System.exit(1);
        }

        // -----
        Socket clientSocket = null;

        try {
            System.out.println ("Waiting for Client");
            clientSocket = serverSocket.accept();
        }
        catch (IOException e)
        {
            System.err.println("Accept failed.");
            System.exit(1);
        }

        //------
        ObjectOutputStream out = new ObjectOutputStream(
                clientSocket.getOutputStream());
        ObjectInputStream in = new ObjectInputStream(
                clientSocket.getInputStream());

        Queue<Integer> randomInts = null;

        try {
            randomInts = (Queue<Integer>) in.readObject();
        }
        catch (Exception ex)
        {
            System.out.println (ex.getMessage());
        }


        System.out.println ("Server received random numbers" + randomInts.toString() +"from Client");

        Map<Integer, Boolean> answers = new HashMap<>();

        for(Integer i : randomInts){
            boolean b = isPrime(i);
            answers.put(i, b);
        }

        System.out.println ("Server sending answers" + answers.toString()+" to Client");
        out.writeObject(answers);
        out.flush();


        out.close();
        in.close();
        clientSocket.close();
        serverSocket.close();
    }
}
