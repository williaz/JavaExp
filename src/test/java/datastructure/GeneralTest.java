package datastructure;

import org.junit.Test;

import java.math.BigInteger;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;
import java.util.TreeMap;
import java.util.stream.Collector;
import java.util.zip.Inflater;

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

    public static class ListNode {
        int val;
        ListNode next;

        ListNode(int x) {
            val = x;
        }
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

    public ListNode mergeKLists(List<ListNode> lists) {
        Comparator<ListNode> cpt = (a, b) -> a.val - b.val;
        PriorityQueue<ListNode> queue = new PriorityQueue<>(cpt);
        for (ListNode node : lists) {
            while (node != null) {
                queue.offer(node);
                node = node.next;
            }
        }
        ListNode dummy = new ListNode(0);
        ListNode walker = dummy;
        while (!queue.isEmpty()) {
            walker.next = queue.poll();
            walker = walker.next;
        }

        return dummy.next;
    }


    private ListNode reverse(ListNode head) {
        ListNode newHead = null;
        while (head != null) {
            ListNode temp = head.next;
            head.next = newHead;
            newHead = head;
            head = temp;
        }
        return newHead;
    }

    @Test
    public void test_ReverseListNode() {
        ListNode n4 = new ListNode(4);
        ListNode n3 = new ListNode(3);
        ListNode n2 = new ListNode(2);
        ListNode n1 = new ListNode(1);
        n1.next = n2;
        n2.next = n3;
        n3.next = n4;
        ListNode list = reverse(n1);
        while (list != null) {
            System.out.print(list.val);
            list = list.next;
        }
    }


    public ListNode getMid(ListNode node) {
        ListNode slow = node;
        ListNode fast = node; // mid right for even
        //ListNode fast = node.next; //mid left for even
        while (fast != null && fast.next != null) {
            fast = fast.next.next;
            slow = slow.next;
        }
        return slow;
    }

    @Test
    public void test_getMidListNode() {
        //ListNode n4 = new ListNode(4);
        ListNode n3 = new ListNode(3);
        ListNode n2 = new ListNode(2);
        ListNode n1 = new ListNode(1);
        n1.next = n2;
        n2.next = n3;
        //n3.next = n4;
        ListNode list = getMid(n1);
        while (list != null) {
            System.out.print(list.val);
            list = list.next;
        }
    }

    public ListNode merge(ListNode n1, ListNode n2) {
        ListNode list = new ListNode(0);
        ListNode dum = list;
        while (n1 != null && n2 != null) {
            ListNode first = n1;
            n1 = n1.next;
            first.next = null;

            ListNode sec = n2;
            n2 = n2.next;
            sec.next = null;

            list.next = first;
            list = list.next;
            list.next = sec;
            list = list.next;
        }
        if (n1 != null) list.next = n1;
        if (n2 != null) list.next = n2;
        return dum.next;
    }

    @Test
    public void test_MergeListNode() {
        ListNode n4 = new ListNode(4);
        ListNode n3 = new ListNode(3);
        ListNode n2 = new ListNode(2);
        ListNode n1 = new ListNode(1);
        n1.next = n2;
        n2.next = n3;
        n3.next = n4;

        ListNode n7 = new ListNode(7);
        ListNode n6 = new ListNode(6);
        ListNode n5 = new ListNode(5);
        n5.next = n6;
        n6.next = n7;

        ListNode list = merge(n1, n5);
        while (list != null) {
            System.out.print(list.val);
            list = list.next;
        }
    }

    @Test
    public void test_Regx() {
        //String s = ">----<";
        String s = "<<>><";
        String ns = s.replaceAll("[^<>]", "");
        System.out.println(ns);

        // Pattern p1 = Pattern.compile("<");
        // Matcher m = p1.matcher(s);
        // while (m.find()) count1++;
    }

    @Test
    public void test_answer1() {
        String n = "17";
        System.out.println(answer1(n));
    }

    public static int answer1(String n) {

        if (n == null || n.isEmpty()) return 0;

        BigInteger num = new BigInteger(n);
        BigInteger b1 = new BigInteger("1");
        BigInteger b2 = new BigInteger("2");
        BigInteger b4 = new BigInteger("4");

        int count = 0;
        while (num.compareTo(b1) > 0) {
            if (num.mod(b2).intValue() == 0) {
                num = num.divide(b2);
            } else {
                if (
                        ((num.add(b1)).mod(b4).intValue() == 0)
                                && !(num.subtract(b1)).equals(b2)
                        ) num = num.add(b1);
                else num = num.subtract(b1);
            }
            count++;
        }
        return count;

        /*
        int count = 0;
    while(n>1){
        if(n%2==0) n/=2;
        else{
            if((n+1)%4==0&&(n-1!=2)) n+=1;
            else n-=1;
        }
        count++;
    }
    return count;
    }
         */

    }

    @Test
    public void test_answer() {
        //int[] l = {1, 1, 1};
        int[] l = {1, 2, 3, 4, 5, 6};
        System.out.println(answer(l));
    }

    private static int result = 0;
    public static int answer(int[] l) {
        //Set<List<Integer>> result = new HashSet<>();
        List<Integer> list = new ArrayList<>();
        Arrays.sort(l);
        int index = 0;
        //dfs(l, list, index, result);
        dfs(l, list, index);
        //return result.size();
        return result;
    }

    public static void dfs(int[] l, List<Integer> list, int index) {
        if (list.size() > 3) {
            return;
        }
        if ((list.size() == 3) && (list.get(1)%list.get(0) == 0)
                && (list.get(2)%list.get(1) == 0)) {
            //result.add(new ArrayList<Integer>(list));
            result++;

        }
        int prev = -1;
        for (int i = index; i < l.length; i++) {
            if (prev != -1 && prev == l[i]) continue;
            list.add(l[i]);
            //dfs(l, list, i+1, result);
            dfs(l, list, i+1);
            list.remove(list.size() - 1);
            prev = l[i];
        }
    }

//    public static int answer(int[] l) {
//        Set<List<Integer>> result = new HashSet<>();
//        List<Integer> list = new ArrayList<>();
//        Arrays.sort(l);
//        int index = 0;
//        dfs(l, list, index, result);
//        System.out.println(result);
//        return result.size();
//    }
//
//    public static void dfs(int[] l, List<Integer> list, int index, Set<List<Integer>> result) {
//        if (list.size() > 3) {
//            //list.clear();
//            return;
//        }
//        if ((list.size() == 3) && (list.get(1)%list.get(0) == 0)
//                && (list.get(2)%list.get(1) == 0)) {
//            result.add(new ArrayList<>(list));
//            //return;
//            //list.clear();
//        }
//        int prev = -1;
//        for (int i = index; i < l.length; i++) {
//            if (prev != -1 && prev == l[i]) continue;
//            list.add(l[i]);
//            dfs(l, list, i+1, result);
//            list.remove(list.size() - 1);
//            prev = l[i];
//        }
//    }

    public static int answer3(int start, int length) {
        int first = start;
        int result = start;
        System.out.print(start + " ");
        for (int i = 0; i < length; i++) {
            if (i != 0) first += length;
            for (int j = 0; j < length - i; j++) {
                if (i == 0 && j == 0) continue;
                System.out.print(first + j + " ");
                result ^= (first + j);
            }
            System.out.println();
        }
        System.out.println("-----------");
        return result;

    }

    public static int answer4(int start, int length) {
        int count1 = 0;
        int result = -1;
        int l = length;
        for (int i = 0; i < length; i++) {
            boolean evenLen = l % 2 == 0;
            //System.out.println(start);
            if (start % 2 == 0 && evenLen) count1 += l/2;
            else if (start % 2 == 0 && !evenLen) {
                count1 += l/2;
                if (result == -1) result = start+ l -1;
                else result ^= (start+ l -1);
            }
            else if (start % 2 != 0 && evenLen) {
                count1 += l/2 - 1;
                if (result != -1) {
                    result ^= start;
                    result ^= (start + l - 1);
                } else {
                    result = start;
                    result ^= (start + l - 1);
                }
            } else {
                count1 += l/2;
                if (result != -1) result ^= start;
                else result = start;
            }
            start += length;
            l--;
        }
        if (count1 % 2 == 0) result ^= 0;
        else result ^= 1;
        //System.out.println(1 +"s "+count1);
        return result;

    }


    @Test
    public void test_Answer3() {
        assertEquals(2, answer3(0, 3));
        assertEquals(2, answer4(0, 3));
        assertEquals(14, answer3(17, 4));
        assertEquals(14, answer4(17, 4));
        answer3(0, 6);
    }

    @Test
    public void test_DecimalToBinary() {
        for (int i = 17; i < 29; i++) {
            System.out.println(i +" " +Integer.toBinaryString(i));
        }
    }

    public static void splitMatrix(int[][] m) {
        if (m == null) return ;
        int row = m.length;
        int col = m[0].length;
        int[] zero = new int[col];
        Arrays.fill(zero, 0);
        List<int[]> I = new ArrayList<>();
        List<int[]> RQ = new ArrayList<>();

        for (int i = 0; i < row; i++) {
            //I
            if(Arrays.equals(m[i], zero)) {
                int s = I.size();
                m[i][s] = 1;
                I.add(m[i]);
            }
            //R
            else {
                RQ.add(m[i]);
            }
        }
        int iLen = col - RQ.size();
        int[][] iMx = new int[I.size()][];
        for (int i = 0; i < I.size(); i++) {
            iMx[i] = Arrays.copyOf(I.get(i), iLen);
        }
        int[][] rMx = new int[RQ.size()][iLen];
        int[][] qMx = new int[RQ.size()][RQ.size()];
        for (int i = 0; i < RQ.size(); i++) {
            rMx[i] = Arrays.copyOfRange(RQ.get(i), RQ.size(), col);
            qMx[i] = Arrays.copyOfRange(RQ.get(i), 0, RQ.size());
        }
//        print2mArray(iMx);
//        print2mArray(rMx);
//        print2mArray(qMx);

    }

    public static void print2mArray(int[][] i) {
        for (int[] a : i)
            System.out.println(Arrays.toString(a));
        System.out.println();
    }

    @Test
    public void test_SplitMatrix() {
        int[][] matrix = {{0,1,0,0, 0,1}, {4,0,0,3,2,0},{0,0,0,0,0,0}, {0,0,0,0,0,0},{0,0,0,0,0,0},{0,0,0,0,0,0}};
        splitMatrix(matrix);

    }


    public static List<int[]> restruct(int[][] m) {
        if (m == null) return null;
        int row = m.length;
        int col = m[0].length;
        int[] zero = new int[col];
        Arrays.fill(zero, 0);
        List<int[]> I = new ArrayList<>();
        List<int[]> RQ = new ArrayList<>();

        for (int i = 0; i < row; i++) {
            //I
            if(Arrays.equals(m[i], zero)) {
                int s = I.size();
                m[i][s] = 1;
                I.add(m[i]);
            }
            //R
            else {
                RQ.add(m[i]);
            }
        }
        //change position
        int rsize = RQ.size();
        for (int i = 0; i < rsize; i++) {
            int[] old = RQ.get(i);
            int[] chg = new int[col];
            int index = 0;
            for(int j = col - rsize; j < col; j++) {
                chg[j] = old[index++];
                //System.out.println(index);
            }
            for (int k = 0; k < col - rsize; k++) {
                chg[k] = old[index++];
            }
            RQ.set(i, chg);
        }
        I.addAll(RQ);
        return I;
    }

    @Test
    public void test_Restruct() {
        int[][] matrix = {{0,1,0,0, 0,1}, {4,0,0,3,2,0},{0,0,0,0,0,0}, {0,0,0,0,0,0},{0,0,0,0,0,0},{0,0,0,0,0,0}};
        List<int[]> I = restruct(matrix);
        for (int[] i : I) {
            for (int t: i) System.out.print(t + " ");
            System.out.println();
        }

    }


    public void sortColors(int[] nums) {
        //Arrays.sort(nums);
        if (nums == null || nums.length == 0) return;
        int len = nums.length;
        int i0 = 0;
        int i2 = len - 1;
        for (int i =  0; i < len; i++) {
            if (nums[i] != 0) {
                i0 = i;
                break;
            }
        }
        for (int i = len - 1; i>= 0;  i-- ) {
            if (nums[i] != 2) {
                i2 = i;
                break;
            }
        }
        for (int i = i0; (i < i2 ) &&(i0 < i2); ) {
            if (nums[i] == 0) {
                //System.out.println(i0);
                int temp = nums[i0];
                nums[i0] = 0;
                nums[i] = temp;
                i0++;
                i++;
            } else if (nums[i] == 2) {
                int temp = nums[i2];
                nums[i2] = 2;
                nums[i] = temp;
                i2--;
            } else i++;
            //System.out.println(Arrays.toString(nums)+" , "+i);
        }

    }

    @Test
    public void test_SortColors() {
        int[] old = {2,0,2,2,1,2,2,1,2,0,0,0,1};
        sortColors(old);
        int[] old1 = {2,0,0,1,2,0,2};
        sortColors(old1);
    }

    public int partitionArray(int[] nums, int k) {
        if (nums == null || nums.length == 0) return 0;
        int len = nums.length;
        int ki = 0;
        //find the first k, ki store first >=k number
        for (int i = 0; i < len; i++) {
            if (nums[i] >= k) {
                ki = i;
                break;
            }
        }
        // if <k, swap with ki,
        for (int i = ki+1; i < len; i++) {
            if (nums[i] < k) {
                int temp = nums[ki];
                nums[ki] = nums[i];
                nums[i] = temp;
                ki++;
                //System.out.println(Arrays.toString(nums) + " : " + ki);
            }
        }
        if (ki == len - 1) return ki+1;
        else return ki;
    }

    @Test
    public void test_partitionArray() {
        int[] arr = {9,9,9,8,9,8,7,9,8,8,8,9,8,9,8,8,6,9};
        assertEquals(10, partitionArray(arr, 9));
    }

    public int[] updateArr(int len, int[][] operation) {

        int[] ans = new int[len];
        Map<Integer, Integer> map = new HashMap<>();//index, operation
        //operation must be triplet!
        for (int i = 0; i < operation.length; i++) {
            map.merge(operation[i][0] - 1, -operation[i][2], (o, n) -> o+n);
            map.merge(operation[i][1], operation[i][2], (o, n) -> o+n);
        }
        System.out.println(map);
        int opt = 0;
        for (int i = len-1; i >= 0; i--) {
            if (map.containsKey(i)) {
                opt += map.get(i);
            }
            ans[i] = opt;
        }
        return ans;
    }

    //backward
    public int[] updateArr1(int len, int[][] operation) {

        int[] ans = new int[len];
        int[] sum = new int[len +1]; //first ith sum
        sum[0] = 0;
        for (int i = 0; i < operation.length; i++) {
            sum[operation[i][0]] += -operation[i][2];
            sum[operation[i][1]+1] += operation[i][2];
        }
        System.out.println(Arrays.toString(sum));
        ans[len - 1] = sum[len];
        for (int i = len-1; i > 0; i--) {
            ans[i-1] = ans[i] + sum[i];
        }
        return ans;
    }
    //forward
    public int[] updateArr2(int len, int[][] operation) {
        int[] ans = new int[len];
        int[] sum = new int[len+1];//ith's operation KK
        for (int i = 0; i < operation.length; i++) {
            sum[operation[i][0]] += operation[i][2];
            sum[operation[i][1]+1] += -operation[i][2];
        }
        System.out.println(Arrays.toString(sum));
        int opt = 0;
        for (int i = 0; i < len; i++) {
            opt += sum[i];
            ans[i] = opt;
        }
        return ans;

    }

    @Test
    public void test_updateArr() {
        int[][] matrix = {{1,3,2},{2, 4, 3}, {0,2, -2}};
        System.out.println(Arrays.toString(updateArr2(5, matrix)));
        int[][] matrix1 = {{2,5,1},{3,4,2}};
        System.out.println(Arrays.toString(updateArr2(6, matrix1)));
    }


}
