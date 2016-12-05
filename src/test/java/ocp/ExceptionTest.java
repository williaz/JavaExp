package ocp;

import org.junit.Test;

import java.util.Random;

/**
 * Created by williaz on 12/5/16.
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
}
