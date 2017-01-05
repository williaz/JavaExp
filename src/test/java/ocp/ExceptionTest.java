package ocp;

import org.junit.Test;

import java.io.IOException;
import java.util.Random;
import java.util.Scanner;

import oca.ood.Animal;

/**
 * Created by williaz on 12/5/16 - 12/6 2d.
 * Idempotent means that the method can called be multiple times
 *    without any side effects or undesirable behavior on subsequent runs.
 * If both catch and finally throw an exception, the one from finally gets thrown
 * watch out:
 * 1. a multi-catch cannot catch both superclass and subclass.
 * 2. AutoCloseable call close() after try clause, before catch and finally clauses
 * 3. unreachable for checked exception##
 */
public class ExceptionTest {
    /**
     * Checked exceptions must follow the handle or declare rule where they are either caught or thrown to the caller.
     * While it is legal to catch an error, it is not a good practice.
     *
     * Checked: 1. ParseException; 2. IOException, FileNotFoundException, NotSerializableException; 3. SQLException
     * Unchecked: 1. ArrayStoreException; 2. DateTimeException; 3. MissingResourceException;
     *            4. IllegalStateException; 5. UnsupportedOperationException.
     * ce: 1. order, unreachable; 2. cannot potentially be thrown, unreachable
     * it is most common to extend Exception (for checked) or RuntimeException (for unchecked.)
     * @see java.io.NotSerializableException
     * @see java.text.ParseException
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
     * separated by a pipe (|).
     *
     * Remember that the exceptions can be listed in any order within the catch clause.
     * However, the variable name must appear only once and at the end.
     * Java intends multi-catch to be used for exceptions that aren’t related,
     * Multi-catch Is Effectively Final, Java forbids reassigning the exception variable in a multi-catch situation.
     * at most one catch block can run.
     * The multiple exception types are not allowed to have a subclass/ superclass relationship.
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
     * # Closeable extends AutoCloseable
     * @see java.io.Closeable
     * @see AutoCloseable
     *
     */
    @Test
    public void test_TryWithResource() {
        try (Scanner sc = new Scanner("will is best")) {
            String mimic = sc.nextLine();
            System.out.println(mimic);
        }

        try (AutoCloseableResource shop = new AutoCloseableResource();
             CloseableResource ioShop = new CloseableResource()) {

        } catch (IOException ioe) { // have to catch or declare exception that may throw by the close()!
            System.out.println(ioe.getMessage());
        }
    }

    /**
     * When multiple exceptions are thrown, all but the first are called suppressed exceptions.
     * first - primary exception; others - suppressed exception
     * The idea is that Java treats the first exception as the primary one
     *     and tacks on any that come up while automatically closing.
     * Keep in mind that the catch block looks for matches on the primary exception.
     * Java remembers the suppressed exceptions that go with a primary exception even if we don’t handle them in the code.
     * Java closes resources in the reverse order from which it created them.
     * Suppressed exceptions apply only to exceptions thrown in the try clause.
     */
    @Test
    public void test_SuppressedException() {
        // A try- with- resources statement is allowed to create suppressed exceptions in the try clause or when closing resources.
        //Neither is allowed to create suppressed exceptions by combining the try and finally (or catch) clauses.
        try {
            throw new RuntimeException();
        } catch (Exception e) {
            for (Throwable t : e.getSuppressed()) {
                System.out.println("Suppressed: " + t.getMessage());
            }
        }


        try (AutoCloseableResource shop = new AutoCloseableResource()) {
            throw new RuntimeException("Leaking");
        } catch (RuntimeException e) {
            System.out.println("Caused by: " + e.getMessage());
            for (Throwable t : e.getSuppressed()) {
                System.out.println("Suppressed: " + t.getMessage());
            }
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    @Test
    public void test_CloseOrder() {
        try (ResourceOrder r1 = new ResourceOrder(1);
             ResourceOrder r2 = new ResourceOrder(2)) {

        }
    }

    /**
     * It is a common pattern to log and then throw the same exception.
     */
    @Test(expected = RuntimeException.class)
    public void test_RethrowException() throws IOException {
        try (AutoCloseableResource shop = new AutoCloseableResource()) {
            throw new RuntimeException("Leaking");
        } catch (RuntimeException | IOException e) {
            System.err.println(e.getMessage());
            throw e;
        }
    }

    /**
     * Assertions are used for debugging purposes
     *
     * An assertion is a Boolean expression that you place at a point in your code where you expect something to be true.
     * An assertion throws an AssertionError if it is false.
     * The optional error message is a String used as the message for the AssertionError that is thrown.
     *
     * 1. assert boolean_expression;
     * 2. assert boolean_expression: error_message;
     *
     * your assertions should not contain any business logic that affects the outcome of your code.
     *
     * By default, assert statements are ignored by the JVM at runtime.
     * Using the -enableassertions or -ea flag without any arguments enables assertions in all classes except system classes.
     * java -enableassertions Xxx; java –ea Xxx
     * -disableassertions (or -da ) fl ag
     * java -ea:com.wiley.demos... my.programs.Main : The three dots means any class in the specified package or subpackages.
     *
     * Control Flow Assertion: You could place an assert statement at any location in your code that you assume will not be reached.
     * Class Invariant Assertion: private method to test object state
     * Do not use assertions to check for valid arguments passed in to a method. Use an IllegalArgumentException instead.
     *
     */
    @Test
            //(expected = AssertionError.class)
    public void test_Assertion() {
        int guest = -2;
        assert guest > 0 : "guest number cannot be negative";
        System.out.println(guest);
    }

}
