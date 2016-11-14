package oca;

import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.*;

/**
 * Created by williaz on 11/5/16 - 11/6 2D
 * exceptions alter the program flow.
 * In general, try to avoid return codes(==use special value for return).
 * <p></p>
 * Error means something went so horribly wrong. No allowed or required to catch.
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
 * and it will be the first catch block that can handle it.
 * <p></p>
 * you can handle or declare Throwable class and its subclass (Exception and error)
 * finally must run.
 * catch clauses only catch exception thrown from try clause.
 * you can throw runtime exceptions anytime, anywhere. no restrictions.
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
     * 3. catch clauses should appear in order of going up exception Hierarchy, otherwise compiler err,
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

    /**
     * RuntimeException:
     * NumberFormatException is a subclass of IllegalArgumentException
     * <p></p>
     * Checked
     * FileNotFoundException is a subclass of IOException
     * <p></p>
     * Error:
     * ExceptionInInitializerError Thrown by the JVM when a static initializer
     * throws an exception and does not handle it<br>
     * NoClassDefFoundError Thrown by the JVM when a class
     * that the code uses is available at compile time but not runtime
     */
    @Test(expected = ArithmeticException.class)
    public void test_ThrowArithmeticException() {
        int x = 3/0;
    }
    @Test(expected = ArrayIndexOutOfBoundsException.class)
    public void test_ThrowArrayIndexOutOfBoundsException() {
        int[] arr = new int[5];
        arr[5] = 34;
    }
    @Test(expected = ClassCastException.class)
    public void test_ThrowClassCastException() {
        String x = "will";
        Object o = x;
        StringBuilder sb = (StringBuilder) o;
    }
    public static int countEatenEggs(int eggs) {
        if (eggs < 0){
            throw new IllegalArgumentException("Cannot be negative!");
        }
        return eggs + 2;
    }
    @Test(expected = IllegalArgumentException.class)
    public void test_ThrowIllegalArgumentException() {
        countEatenEggs(-5);
    }
    @Test(expected = NullPointerException.class)
    public void test_ThrowNullPointerException() {
        String str = null;
        str.length();
    }
    @Test(expected = NumberFormatException.class)
    public void test_ThrowNumberFormatException() {
        int i = Integer.parseInt("2.34");
    }

    /**
     * The compiler is still on the lookout for unreachable code.
     * X catch exception which would not be thrown in the try clause - compiler err.
     * Declaring an unused exception isn’t considered unreachable code. - declare throws with method signature.
     * <p></p>
     * Override method in child class could declare that it throws Exception directly,
     * or it could declare that it throws a more specific type of Exception.
     * It could even declare that it throws nothing at all.
     * <p></p>
     * When writing your own code, print out a stack trace or at least a message when catching an exception.
     * Also, consider whether continuing is the best course of action.
     */
    @Test
    public void test_SmartCheckUnreachable() {
        try {
            int i = 34;
        } catch (ArithmeticException ae) {
            System.out.println("can use");
        }
        /*catch (IOException ioe) {
            System.out.println("cannot use");
        }
        */
    }


}
