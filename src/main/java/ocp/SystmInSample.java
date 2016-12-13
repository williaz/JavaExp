package ocp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by williaz on 12/13/16.
 *
 * closing System.in would prevent our application from accepting user input
 *   for the remainder of the application execution.
 */
public class SystmInSample {
    public static void main(String[] args) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        String input = in.readLine();
        System.out.println(input);
    }
}
