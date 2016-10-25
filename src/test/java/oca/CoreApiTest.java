package oca;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static java.lang.Integer.valueOf;
import static org.junit.Assert.*;

/**
 * Created by williaz on 10/23/16.
 *
 * # Placing one String before the other String and combining them together
 *   is called string concatenation
 */
public class CoreApiTest {
    /**
     * 1. If both operands are numeric, + means numeric addition.
     * 2. If either operand is a String, + means concatenation.
     * 3. The expression is evaluated left to right.
     */
    @Test
    public void Test_StringConcatenation(){
        assertEquals("3c", (1 + 2 + "c"));

        String str1 = "1";
        str1 += 2;

        assertEquals(str1, "12");
    }

    /**
     *
     * immutable classes in Java are final, and subclasses can’t add mutable behavior.
     */
    @Test
    public void Test_StringImmutable(){
        String str1 = "w";
        String str2 = str1.concat("i");
        str2.concat("ll");

        assertEquals("wi", str2);

    }

    /**
     * The string pool contains literal values that appear in your program.
     * toString() is a string but not a literal, so it does not go into the string pool.
     * Strings not in the string pool are garbage collected just like any other object.
     */
    @Test
    public void Test_StringLiteral(){
        String str1 = "will";
        String str2 = new String("will");

        assertEquals(str1, str2);
    }

    /**
     * For all these methods, you need to remember that a string is a sequence of characters
     * and Java counts from 0 when indexed.
     */
    @Test
    public void Test_String13Methods(){
        String str = "animal";
        assertEquals(6,str.length());
        assertEquals('a', str.charAt(4));
        assertEquals(4, str.indexOf('a', 4)); // include the from index
        assertEquals(-1, str.indexOf('n', 4));  // no found -1
        assertEquals("im", str.substring(2,4)); // endIndex - exclude
        assertEquals("animal", str.substring(0, 6));
        assertEquals("ANIMAL", str.toUpperCase());

        String name = "Will";
        assertEquals("will", name.toLowerCase());
        assertTrue("Will".equals("Will"));
        assertTrue("Will".equalsIgnoreCase("will"));

        assertFalse("abcd".startsWith("Ab"));
        assertTrue("abcd".endsWith("d"));

        assertTrue("Austin".contains("in"));
        assertFalse("Edison".contains("op"));

        assertEquals("Will".replace('W', 'B'), "Bill");  // CharSequence!!!
        assertEquals("Harrison".replace("Harr", "Ed"), "Edison");

        /*
        whitespace consists of spaces along with the \t (tab) and \n (newline)
        and \r (carriage return) are included in what gets trimmed.
         */
        assertEquals("\t \ra b cde \n".trim(), "a b cde");

        // Method Chaining
        assertEquals("AnimAl", "aniMaL is food".substring(0, 6).toLowerCase().replace('a', 'A'));

    }

    /**
     * When use string directly in concatenation, many objects are instantiated,
     * but most of which are immediately eligible for garbage collection.
     */
    @Test
    public void Test_StringBuilderMutable(){
        StringBuilder sb1 = new StringBuilder("abc");
        StringBuilder sb2 = sb1.append("de"); //returns a reference to the current StringBuilder.
        sb2.append("f").append("ghi");

        assertEquals("abcdefghi", sb1.toString());
        assertEquals("abcdefghi", sb2.toString());
        assertTrue(sb1 == sb2); // point to the same object !!!

        //VS

        String str1 = "abc";
        String str2 = str1.concat("de");
        str2.concat("f").concat("ghi");

        assertEquals("abc", str1);
        assertEquals("abcde", str2);
        assertFalse(str1 == str2);

    }

    /**
     * Size is the number of characters currently in the sequence,
     * and capacity is the number of characters the sequence can currently hold.
     *
     * Often StringBuilder is used internally for performance purposes
     * but the end result needs to be a String.
     */
    @Test
    public void Test_StringBuilderMethods(){
        StringBuilder sb = new StringBuilder();
        assertEquals(16, sb.capacity());

        sb = new StringBuilder(42);
        assertEquals(42, sb.capacity());

        sb = new StringBuilder("will");
        assertEquals(20, sb.capacity());// "will".length()+16

        assertEquals(4, sb.length());
        assertEquals(2, sb.indexOf("l")); // first one
        assertEquals('i', sb.charAt(1));
        assertEquals("il", sb.substring(1,3));

        char[] cc= {'a', 'b'};
        StringBuilder sb1 = new StringBuilder()
                .append(true).append(' ').append(cc)
                .append(" is good ").append(23).append(1.42)
                .append(9000L).append(sb);
        assertEquals("true ab is good 231.429000will", sb1.toString());

        sb.insert(2,242);
        assertEquals("wi242ll", sb.toString());

        sb.deleteCharAt(2); // to delete only one
        assertEquals("wi42ll", sb.toString());

        sb.delete(2, 4);
        assertEquals("will", sb.toString());

        sb.reverse();
        assertEquals("lliw", sb.toString());


    }

    /**
     * Remember that Strings are immutable and literals are pooled.
     * The JVM created only one literal in memory.
     *
     * the authors of StringBuilder did not implement equals().
     */
    @Test
    public void Test_Equality(){

        String str1 = "will is best";
        String str2 = "will is best";

        assertTrue(str1 == str2);

        String str3 = str2.replace('w', 'W');

        assertTrue(str1 == str2);
        assertNotEquals("Will is best", str2);

        /*
        The lesson is to never use == to compare String objects.
        The only time you should have to deal with == for Strings is on the exam.
         */

        String str4 = new String("will is best");
        assertFalse(str1 == str4);

        StringBuilder sb1 = new StringBuilder("will");
        StringBuilder sb2 = new StringBuilder("will");

        assertFalse(sb1.equals(sb2));
        assertNotEquals(sb1, sb2);

    }

    /**
     * you can type the [] before or after the name, and adding a space is optional
     *
     */
    @Test
    public void Test_Array(){
        int num[] = new int[5];
        int num3 [] = new int[9];
        int[] num1 = new int[]{3, 5, 1, 3};
        int [] num2 = {12, 34, 15, 41};
        assertEquals("[3, 5, 1, 3]", Arrays.toString(num1));

        String[] strings = {"will best", "Who?", "Where?","123", "655"};

        /*
        alphabetic order: Numbers sort before letters
        and uppercase sorts before lowercase,
         */
        Arrays.sort(num2);
        assertArrayEquals(new int[]{12, 15, 34, 41}, num2);
        Arrays.sort(strings);
        assertArrayEquals(new String[]{"123", "655", "Where?", "Who?", "will best"}, strings);

        /*
        No Found -- Negative value showing one smaller than the negative of index,
        where a match needs to be inserted to preserve sorted order

        As soon as you see the array isn’t sorted,
        look for an answer choice about unpredictable output.
         */
        assertEquals(2, Arrays.binarySearch(num2, 34));
        assertEquals(-4, Arrays.binarySearch(num2, 35));

        Object[] objects = strings;
        objects[0] = new String(); // type safe
       // objects[0] = new Object(); // mistype, ArrayStoreException
        assertEquals(5, num.length);  // final length
        assertEquals(num[3], 0);




        //Varargs
        assertEquals(12, getSum(num1));

        int a =3;
        int b =5;

        assertEquals(getSum(a, b), 8);

        int[][] arr = new int[3][];
        arr[0] = new int[4];
        arr[1] = new int[2];
        arr[2] = new int[6];


    }

    public int getSum(int... args){
        int sum =0;

        for(int i: args){
            sum += i;
        }

        return sum;
    }

    @Test(expected = ArrayIndexOutOfBoundsException.class)
    public void Test_ArrayOutOfBound(){
        int num[] = new int[5];
        //num[5] = 4; //1
        for (int i =0; i<= num.length; i++){
            num[i]++;
        } //2


    }

    @Test
    public void Test_ArrayList(){
        ArrayList<Integer> list = new ArrayList<>();
        list.add(2);
        list.add(5);
        list.add(0, 3);// insert, not replace
        list.add(12);
        assertEquals(list.toString(), "[3, 2, 5, 12]");

        ArrayList<Integer> list1 = new ArrayList<>(list);
        assertEquals(list, list1);
        assertFalse(list == list1);

        assertEquals(new Integer(5), list1.remove(2));  // index
        //assertEquals(5, list1.remove(2));  // index, no auto unboxing,
        assertTrue(list1.remove(new Integer(2)));  // E object

        int gotReplaced = Integer.valueOf( list.set(0, new Integer(12)) );
        assertEquals(3, gotReplaced);

        assertFalse(list.isEmpty());
        assertEquals(list.size(), 4);
        assertEquals(list1.size(), 2);

        list1.clear();
        assertTrue(list1.isEmpty());

        assertTrue(list.contains(new Integer(5)));

        assertFalse(list.equals(list1));

    }

    /**
     * String --->
     * The parse methods, such as parseInt(), return a primitive,
     * and the valueOf() method returns a wrapper class
     *
     * the Character class doesn’t participate in the parse/ valueOf methods.
     */

    @Test
    public void Test_WrapperClass(){
        int primitives = Integer.parseInt("234");
        Integer wrapper = Integer.valueOf("234");

        assertEquals(new Integer(primitives), wrapper);

        Boolean bWrapper = Boolean.valueOf("TRUe"); //equalsIgnoreCase()

        assertEquals(bWrapper, true);

        List<Integer> nums = new ArrayList<>();
        nums.add(1);
        nums.add(4);
        nums.add(7);
        nums.remove(1); // it is the index, no the object stored
        assertEquals(nums.get(0), new Integer(1));

    }

    /**
     * If you like, you can suggest a larger array to be used instead.
     */

    @Test(expected = IndexOutOfBoundsException.class)
    public void Test_toArray_NoLinked(){
        List<Integer> nums = new ArrayList<>();
        nums.add(1);
        nums.add(4);
        nums.add(7);
        nums.add(2);

        Integer[] ints = nums.toArray(new Integer[10]);
        ints[4] = 13;

        assertEquals( nums.get(4), new Integer(13));


    }

    @Test(expected = UnsupportedOperationException.class)
    public void Test_asList_LinkedButFixed(){
        String[] strs ={"Will", "is", "Best", "of", "All", "!"};
        List<String> list = Arrays.asList(strs);
        list.add("True");
    }

    @Test
    public void Test_asList_Linked(){
        String[] strs ={"Will", "is", "Best", "of", "All", "!"};
        List<String> list = Arrays.asList(strs); //It is a fixed-size, backed version of a List.
        String toReplace ="the Best";
        list.set(2, toReplace);
        assertEquals(strs[2], toReplace);

        strs[4] = "the world";
        assertEquals(list.get(4), strs[4]);

        //asList() takes varargs, which let you pass in an array or just type out the String values.
        List<Double> doubles = Arrays.asList(23.12, 3.4, 44.4, 15.1); //handy when testing

    }

    @Test
    public void Test_CollectionsSort(){
        List<Double> doubles = Arrays.asList(23.12, 3.4, 44.4, 15.1); //handy when testing
        Collections.sort(doubles);
        Double[] doubles1 ={3.4, 15.1, 23.12, 44.4};
        assertArrayEquals(doubles.toArray(new Double[0]), doubles1);

    }






}
