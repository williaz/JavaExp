package ocp;

import org.junit.Test;

import java.io.IOException;
import java.util.Random;
import java.util.Scanner;

/**
 * Created by williaz on 12/5/16.
 * Idempotent means that the method can called be multiple times
 *    without any side effects or undesirable behavior on subsequent runs.
 */
public class ExceptionTest {
    /**
     * Checked exceptions must follow the handle or declare rule where they are either caught or thrown to the caller.
     * While it is legal to catch an error, it is not a good practice.
     *
     * Cheched: ParseException; IOException, FileNotFoundException, NotSerializableException; SQLException
     * ce: 1. order, unreachable; 2. cannot potentially be thrown, unreachable
     * it is most common to extend Exception (for checked) or RuntimeException (for unchecked.)
     */
    @Test
    public void test_CustomException() {
        try {
            throw new LostException();
        } catch (LostException e) {
            e.printStackTrace(); //stack trace - error messages
        }

        try {
            throw new LostException("losting");
        } catch (RuntimeException e) { // any runtime exception may be caught.
            e.printStackTrace();
        }catch (LostException e) {
            e.printStackTrace();
        }

        try {
            throw new LostException(new IllegalArgumentException()); //Caused by: ---
        } catch (LostException e) {
            //e = new LostException(); // allow reassign, but bad practice
            e.printStackTrace();
        }
    }

    /**
     * Remember that the exceptions can be listed in any order within the catch clause.
     * However, the variable name must appear only once and at the end.
     * Java intends multi-catch to be used for exceptions that aren’t related,
     * Multi-catch Is Effectively Final, Java forbids reassigning the exception variable in a multi-catch situation.
     */
    @Test
    public void test_MultiCatch() {
        try {
            int random = new Random().nextInt();
            if (random / 2 == 1) {
                throw new IllegalArgumentException(String.valueOf(random));
            }
            else {
                throw new LostException(new IllegalArgumentException(String.valueOf(random))); //Caused by: ---
            }
        } catch (LostException | RuntimeException e) { // only one "e"
            e.printStackTrace();
        }
    }

    /**
     * automatic resource management
     * a try-with-resources statement is still allowed to have catch and/or finally blocks.
     *   They are run in addition to the implicit one.
     *   The implicit finally block runs before any programmer-coded ones.
     *   -> # the resource is no longer available.
     * In order for a class to be created in the try clause,
     *   Java requires it to implement an interface called AutoCloseable.
     *   Java strongly recommends that close() not actually throw Exception, but a more specific exception.
     *   Java also recommends to make the close() method idempotent.
     */
    @Test
    public void test_TryWithResource() {
        try (Scanner sc = new Scanner("will is best")) {
            String mimic = sc.nextLine();
            System.out.println(mimic);
        }

        try (AutoCloseableResource shop = new AutoCloseableResource()) {

        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }
}
