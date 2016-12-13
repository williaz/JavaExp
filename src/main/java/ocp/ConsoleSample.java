package ocp;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.Console;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Locale;

/**
 * Created by williaz on 12/13/16.
 * in java dir level, java ocp.ConsoleSample
 */
public class ConsoleSample {
    public static void main(String[] args) throws IOException {
        //System.out.println(new ConsoleSample().getClass().getName());
        Console console = System.console();
        if (console != null) {
            console.writer().format(new Locale("US"), "Where do you come from?");
            console.format("\n China\n");

            console.writer().println("Where are you?");
            console.flush();
            String input = console.readLine();
            console.writer().println("In: " + input);

            console.flush();
            String name = console.readLine("\nYour name:"); //only Console

            BufferedReader reader = new BufferedReader(console.reader());
            console.flush();
            String str = reader.readLine();
            console.printf("\nIs it " + str);

            char[] pwd = {'w', '2', '4'};
            console.flush();
            char[] toVerify = console.readPassword("\nYour passwords: ");
            boolean match = Arrays.equals(pwd, toVerify);
            // Immediately clear passwords from memory
            for (int i = 0; i < toVerify.length; i++)
                toVerify[i] = 'x';
            //also: Arrays.fill(toVerify, 'x');
            console.printf(match == true? "Correct PWD\n" : "Wrong PWD\n");

        } else throw new RuntimeException("No Console available!");

    }
}
