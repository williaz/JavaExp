package lint;

import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.*;
/**
 * Created by williaz on 1/15/17.
 */
public class BasicTest {
    /**
     * Fibonacci Series
     * 1, 1, 2, 3, 5, 8, 13, 21, 34, 55, ..
     */
    public int getFibonacci(int num) {
        if (num == 1 || num == 2) return 1;
        if (num > 2) {
            return getFibonacci(num - 1) + getFibonacci(num - 2);
        }
        else throw new IllegalArgumentException();
    }

    @Test
    public void test_getFibonacci() {
        assertEquals(8, getFibonacci(6));
        assertEquals(21, getFibonacci(8));
        assertEquals(55, getFibonacci(10));
    }

    /**
     * Prime Number
     * 2, 3, 5, 7, 11, 13, 17
     */
    public boolean isPrime(int num) {
        if (num < 2) return false;
        boolean isPrime = true;
        for (int i = 2; i < num / 2 + 1; i ++) {
            if (num % i == 0) {
                isPrime = false;
                break;
            }
        }
        return isPrime;
    }

    @Test
    public void test_isPrime() {
        assertTrue(isPrime(17));
        assertFalse(isPrime(345));
        assertTrue(isPrime(101));
    }

    /**
     * Factorial
     */
    public long getFactorial(long num) {
        if (num == 0) return 1;
        return num * getFactorial(num - 1);
    }

    @Test
    public void test_getFactorial() {
        assertEquals(6L, getFactorial(3));

    }

    /**
     * Swap without temp - XOR
     */
    public void swap(int[] num) {
        num[0] = num[0] ^ num[1];
        num[1] = num[0] ^ num[1];
        num[0] = num[0] ^ num[1];
    }

    @Test
    public void test_swap() {
        int[] num = {3, 5};
        int[] num1 = {5, 3};
        swap(num);
        assertTrue(Arrays.equals(num1, num));
    }



}
