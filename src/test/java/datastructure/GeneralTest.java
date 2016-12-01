package datastructure;

import org.junit.Test;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Deque;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collector;

import static org.junit.Assert.*;

/**
 * Created by williaz on 10/29/16.
 */
public class GeneralTest {

    public int[] swapWithoutCache(int a, int b) {
        a = a - b;
        b = b + a; //a
        a = b - a;

        return new int[]{a, b};
    }

    @Test
    public void Test_SwapWithoutCache() {
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
    public void Test_LocalArrayDefaultValue() {
        int[] arr = new int[5];
        assertEquals(0, arr[4]);

        boolean[] bs = new boolean[4];
        assertEquals(false, bs[3]);

    }

    //TODO Window Sum
    public int[] getWindowSum(int[] arr, int range) {
        final int SIZE = arr.length - range + 1;
        if (SIZE <= 0) {
            return null;
        }
        int[] sum = new int[SIZE];

        for (int j = 0; j < range; j++) {
            sum[0] += arr[j];
        }

        for (int i = 1; i < SIZE; i++) {
            sum[i] = sum[i - 1] - arr[i - 1] + arr[i + range - 1];

        }//end i

        return sum;
    }

    @Test
    public void Test_GetWindowSum() {
        int[] arr = {4, 2, 73, 11, -5};
        int[] sum2 = {6, 75, 84, 6};

        assertArrayEquals(sum2, getWindowSum(arr, 2));
    }

    public int reverseInteger(int n) {
        int sign = 1;
        if (n < 0) {
            sign = -1;
        }
        String s = "" + sign * n;
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
        if (s == null || s.isEmpty()) {
            return true;
        }
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
        if (digits == null || digits.length == 0) {
            return digits;
        }
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
                list.set(i, sum - 10);
            }
        }
        if (one == 1) {
            list.add(0, 1);
        }
        int[] re = new int[list.size()];
        for (int i = 0; i < list.size(); i++) {
            re[i] = list.get(i);
        }
        return re;
    }

    @Test
    public void test_PlusOne() {
        assertTrue(Arrays.equals(new int[]{1}, plusOne(new int[]{0})));
        assertTrue(Arrays.equals(new int[]{1, 0, 0, 0}, plusOne(new int[]{9, 9, 9})));
    }

    @Test
    public void test_Length() {
        int[] n0 = new int[0];
        List l = new ArrayList();
        String s = "";
        assertEquals(0, n0.length);
        assertEquals(0, l.size());
        assertEquals(0, s.length());
        assertTrue(s.isEmpty());
        assertTrue(l.isEmpty());
    }

    public int strStr(String source, String target) {
        if (source == null || target == null)
            return -1;
        if (target.length() > source.length()) return -1;
        if (target.length() == 0) return 0;
        int j = 0;
        int index = -1;
        for (int i = 0; i < source.length(); i++) {
            if (index == -1) {
                if (target.length() > source.length() - i) {
                    break;
                }
                if (source.charAt(i) == target.charAt(0)) {
                    index = i;
                    j = 1;
                }
            } else {
                if (source.charAt(i) != target.charAt(j)) {
                    i = index;// 1112 112
                    index = -1;
                    // j = 0

                } else if (j == target.length() - 1) {
                    break;
                }
                j++;

            }


        }
        return index;
    }

    @Test
    public void test_strStr() {
        assertEquals(4, strStr("abcde", "e"));
        assertEquals(2, strStr("abcede", "ce"));

        assertEquals(3, strStr("tartarget", "target"));
        assertEquals(-1, strStr("source", "rced"));
    }

    public static enum Chocalate {
        MILK(2), DARK(3), WHITE(4);
        private final int i;

        private Chocalate(int i) {
            this.i = i;
        }

        public int getNum() {
            return i;
        }
    }

    public static int getChocalate(int m, int d, int w) {
        int chocalateEaten = m + d + w;
        int[] papers = new int[]{m, d, w};
        ;
        int[] min = new int[]{Chocalate.MILK.getNum(), Chocalate.DARK.getNum(), Chocalate.WHITE.getNum()};
        for (int i = 0; i < 3; i++) {
            while (papers[i] >= min[i]) {
                chocalateEaten += papers[i] / min[i];
                papers[i] = (papers[i] / min[i]) + (papers[i] % min[i]);
            }
        }
        return chocalateEaten;

    }

    @Test
    public void test_Chocalate() {
        System.out.println(getChocalate(14, 26, 34));
        System.out.println(Chocalate.values());

    }

    public static double square(double value, double precision) {
        double left = 0;
        double right = value;
        double mid;
        while (left + precision < right) {
            mid = left + (right - left) / 2;
            if (mid * mid <= value) {
                left = mid;
            } else right = mid;

        }
        if (right * right <= value) return right;
        return left;
    }

    @Test
    public void test_Sqrt() {
        assertEquals(Math.sqrt(100), square(100, 1), 1);
        assertEquals(Math.sqrt(200), square(200, 0.1), 0.1);
        assertEquals(Math.sqrt(3591224), square(3591224, 0.01), 0.01);
    }

    private static class Checker {
        private Set<Character> set = new HashSet<>();

        private boolean add(char c) {
            int v = Character.getNumericValue(c);
            if (v < 1 || v > 9) return false;
            if (set.add(c)) return true;
            else return false;
        }
    }

    public boolean isValidSudoku(char[][] board) {
        // same row, board[i][*]
        // same colum, board[*][j]
        // same block, i = 0 - 2, j = 0 - 2; i = 3 - 5; j = 0 - 2; (i/3)*3 + (j/3)
        List<Checker> checkerRow = new ArrayList<>(9);
        List<Checker> checkerCol = new ArrayList<>(9);
        List<Checker> checkerBlc = new ArrayList<>(9);
        for (int n = 0; n < 9; n++) {
            checkerRow.add(new Checker());
            checkerCol.add(new Checker());
            checkerBlc.add(new Checker());
        }
        for (int i = 0; i < board.length; i++)
            for (int j = 0; j < board[i].length; j++) {
                char c = board[i][j];
                if (c == '.') continue;
                if (!checkerRow.get(i).add(c)) return false;
                if (!checkerCol.get(j).add(c)) return false;
                if (!checkerBlc.get((i / 3) * 3 + (j / 3)).add(c)) return false;
            }
        return true;
    }

    @Test
    public void test_ValidSudoku() {
        List<String> nums = Arrays.asList(".87654321", "2........", "3........", "4........", "5........", "6........", "7........", "8........", "9........");
        char[][] board = new char[9][9];
        int i = 0;
        for (String str : nums) {
            board[i] = str.toCharArray();
            i++;
        }
        for (char[] cc : board) {
            for (char c : cc) {
                System.out.print(c + " ");
            }
            System.out.println();
        }

        assertTrue(isValidSudoku(board));

    }

    public static  class ListNode {
       int val;
       ListNode next;
       ListNode(int x) { val = x; }
    }

    public ListNode swapPairs(ListNode head) {
        if (head == null) return null;

        ListNode list = new ListNode(0);
        list.next = head;

        ListNode temp = list;
        while (temp.next != null && temp.next.next != null) {
            ListNode n1 = temp.next;
            ListNode n2 = temp.next.next;
            n1.next = n2.next;
            temp.next = n2;
            n2.next = n1;
            temp = n1;
        }
        return list.next;
    }
    @Test
    public void test_SwapNodesinPairs() {
        ListNode n4 = new ListNode(4);
        ListNode n3 = new ListNode(3);
        ListNode n2 = new ListNode(2);
        ListNode n1 = new ListNode(1);
        n1.next = n2;
        n2.next = n3;
        n3.next = n4;
        ListNode list = swapPairs(n1);
        while (list != null) {
            System.out.print(list.val);
            list = list.next;
        }
    }


}
