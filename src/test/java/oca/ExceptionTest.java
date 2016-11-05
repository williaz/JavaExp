package oca;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Created by williaz on 11/5/16.
 * exceptions alter the program flow.
 * In general, try to avoid return codes(==use special value for return).
 * <p></p>
 * Error means something went so horribly wrong. No OK or required to catch.
 * that your program should not attempt to recover from it.
 * Runtime exceptions(unchecked) tend to be unexpected but not necessarily fatal.
 * Checked exceptions tend to be more anticipated -> handle or declare rule (throws).
 * <- might reasonably be expected to recover from.
 * <p></p>
 * not compiling VS throwing an exception.
 * <p></p>
 * If it is impossible for one of the catch blocks to be executed, a compiler error
 * about unreachable code occurs. This happens when a superclass is caught before a subclass.<br>
 * To review catching multiple exceptions, remember that at most one catch block will run
 * and it will be the fi rst catch block that can handle it.
 */
public class ExceptionTest {
    @Test(expected = ArrayIndexOutOfBoundsException.class)
    public void Test_NullPointer() {
        String[] arr = new String[0];
        arr[0] = "where";
    }

    /**
     * 1. The curly braces are required for the try and catch blocks.<br>
     * 2. a try statement must have catch and/or finally. watch out for the order
     * 3. catch in order of going up exception Hierarchy, otherwise compiler err,
     */
    @Test
    public void Test_TryBlock() {
        try {
            throw new Exception();
        } catch (Exception e) {

        }
    }

    // bad practice of return placement
    public String testOrderOfTryWithReturn() {
        String s = "a";
        try {
            s += "b";
            throw new Exception();
        } catch (Exception e) {
            s += "c";
            return s;
        } finally {
            s += "d";
            return s;
        }
    }
    @Test
    public void Test_OrderOfTryWithReturn() {
        assertEquals("abcd", testOrderOfTryWithReturn());
    }

    public String testFinallyNoRun() {
        String s = "";
        try {
            s = "str";
            throw new Exception();
        } catch (Exception e) {
            System.exit(5);  // program stop here
        } finally {
            s += "ing";
        }
        return s; // no return
    }
    @Test
    public void Test_FinallyNoRun() {
        testFinallyNoRun();
    }
    /**
     * This is why you often see another try/catch inside a finally block —
     * to make sure it does not mask the exception from the catch block.
     */
    public void throwExceptionInMultiplePlace() {

        try {
            throw new NullPointerException();
        } catch (NullPointerException npe) {
            throw new RuntimeException();
        } finally {
            throw new ArrayIndexOutOfBoundsException();
        }
    }

    @Test(expected = ArrayIndexOutOfBoundsException.class)
    public void Test_ThrowExceptionInMultiplePlace() {
        throwExceptionInMultiplePlace();
    }
}
