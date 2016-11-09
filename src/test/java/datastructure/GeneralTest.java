package datastructure;

import org.junit.Test;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Deque;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by williaz on 10/29/16.
 */
public class GeneralTest {

    public int[] swapWithoutCache(int a, int b){
        a = a - b;
        b = b + a; //a
        a = b - a;

        return new int[] {a, b};
    }

    @Test
    public void Test_SwapWithoutCache(){
        int a = 10;
        int b = 13;

        int[] x = swapWithoutCache(a, b);

        assertEquals(b, x[0]);
        assertEquals(a, x[1]);

    }

    /**
     * every Object uses new to instantiate but without initialization, will get the default value
     *
     * <p>String, primitive uses literal in local scope, should be guarantee be initialized
     */
    @Test
    public void Test_LocalArrayDefaultValue(){
        int[] arr = new int[5];
        assertEquals(0, arr[4]);

        boolean[] bs = new boolean[4];
        assertEquals(false, bs[3]);

    }

    //TODO Window Sum
    public int[] getWindowSum(int[] arr, int range){
        final int SIZE = arr.length - range + 1;
        if(SIZE <= 0){
            return null;
        }
        int[] sum = new int[SIZE];

        for(int j = 0; j < range; j++){
            sum[0] += arr[j];
        }

        for(int i = 1; i < SIZE; i++){
            sum[i] = sum[i - 1] - arr[i - 1] + arr[i + range - 1];

        }//end i

        return sum;
    }

    @Test
    public void Test_GetWindowSum(){
        int[] arr = {4, 2, 73, 11, -5};
        int[] sum2 = {6, 75, 84, 6};

        assertArrayEquals(sum2, getWindowSum(arr, 2));
    }

    public int reverseInteger(int n) {
        int sign = 1;
        if (n < 0) { sign = -1; }
        String s = ""+sign*n;
        StringBuilder sb = new StringBuilder(s);// constructor accept String
        sb.reverse();
        int i = Integer.parseInt(sb.toString());
        return sign * i;
    }

    @Test
    public void test_ReverseInteger() {
        assertEquals(345, reverseInteger(543));
        assertEquals(-345, reverseInteger(-543));
        assertEquals(0, reverseInteger(0));
    }

    public boolean isCouple(char s1, char s2) { // left, right
        if (s1 == '(' && s2 == ')') return true;
        else if (s1 == '[' && s2 == ']') return true;
        else if (s1 == '{' && s2 == '}') return true;
        else return false;
    }
    public boolean isValidParentheses(String s) {
        if (s == null || s.isEmpty()) { return true; }
        Deque<Character> stack = new ArrayDeque<>();
        char[] arr = s.toCharArray();
        for (char c : arr) {
            if (c == '(' || c == '[' || c == '{') {
                stack.push(c);
            } else if (c == ')' || c == ']' || c == '}') {
                if (stack.isEmpty()) return false;
                char left = stack.pop();
                if (!isCouple(left, c)) {
                    return false;
                }
            }
        }
        if (stack.isEmpty()) return true;
        else return false;
    }
    @Test
    public void test_ValidParentheses() {
        String s = "()";
        assertTrue(isValidParentheses(s));
        s = "(x,y) {[xc[]xy]}";
        assertTrue(isValidParentheses(s));
    }


    public int[] plusOne(int[] digits) {
        if (digits == null || digits.length == 0 ) { return digits; }
        int one = 1;
        List<Integer> list = new ArrayList();
        for (int i = 0; i < digits.length; i++) {
            list.add(digits[i]);
        }


        // no asList directly
        for (int i = list.size() - 1; i >= 0; i--) {
            int sum = list.get(i) + one;
            if (sum <= 9) {
                list.set(i, sum);
                one = 0;
                break;
            } else {
                list.set(i, sum -10);
            }
        }
        if (one == 1) {
            list.add(0, 1);
        }
        int[] re = new int[list.size()];
        for (int i =0; i < list.size(); i++) {
            re[i] = list.get(i);
        }
        return re;
    }
    @Test
    public void test_PlusOne() {
        assertTrue(Arrays.equals(new int[] {1}, plusOne(new int[] {0})));
        assertTrue(Arrays.equals(new int[] {1,0,0,0}, plusOne(new int[] {9,9,9})));
    }
}
