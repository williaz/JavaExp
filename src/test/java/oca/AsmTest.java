package oca;

import org.junit.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

/**
 * Created by williaz on 10/17/16.
 * the exception base class is a checked exception.
 */
public class AsmTest {
    String c_a;
    int c_i;


    /**
     * #1. local variables both primitive and ref type require assignment before referencing them.
     * compile error, no runtime
     */
    @Test
    //(expected = Exception.class)
    public void localVariableNeedAssigementBeforeRef(){
        String a;
        int i;
        //System.out.println(a);
        //System.out.println(i);
        System.out.println(c_i);
        System.out.println(c_a);
        //assertEquals(c_a, a);

    }

    /**
     * #2. String literals are used from the string pool.
     * toString() uses a method to compute the value and it is not from the string pool.
     */
    @Test
    public void test_StringRef() {
        String s1 = "java";
        String s2 = "java";
        String s3 = new String("java");

        assertEquals(s1, s2);
        assertEquals(s2, s3);

        assertTrue(s1 == s2);
        assertFalse(s2 == s3);

        StringBuilder sb = new StringBuilder("java");
        assertEquals(s1, sb.toString());
        assertFalse(s1 == sb.toString());


    }

    @Test
    public void orderOfPostIncrement(){
        int count = 0;

        int i =1;
        while(count++ < 3){

            assertEquals(i, count);
            i++;
        }
    }

    /**
     * #3, swtich fall without break
     */
    @Test
    public void test_SwitchWithoutBreak(){
        int y = 4;
        int i = 0;

        switch (y){
            default:

            case 3: i = 3; break;

            case 4: i = 4;

            case 5: i = 5;
        }

        assertEquals(i, 5);
    }

    /**
     * As of Java 7, only one of the right-hand expressions of
     the ternary operator will be evaluated at runtime.

     Note that it is often helpful for readability to add parentheses around the expressions in
     ternary operations, although it is certainly not required.
     */
    @Test
    public void Test_ShortCircuitOfTernaryOperator(){
        int x = 1;
        int y = 20;
        int z = (x < 10)? (x++) : (y--);
        assertEquals(x, 2);
        assertEquals(y, 20);
        assertEquals(z, 1);
        int s = (y < 10)? (x++) : (y--);
        assertEquals(x, 2);
        assertEquals(y, 19);
        assertEquals(s, 20);
    }

    /**
     * !!!!!!!!
     * Prior to Java 5.0, this variable could only be int values
     * or those values that could be promoted to int, specifically
     * byte, short, char, or int.
     * When enum was added in Java 5.0, support was added to switch statements to support enum values.
     * In Java 7, switch statements were further updated to allow matching on String values.
     * Finally, the switch statement also supports any of the primitive numeric wrapper classes,
     * such as Byte, Short, Character, or Integer.
     *
     * Data types supported by switch statements include the following:
     * int and Integer
     * byte and Byte
     * short and Short
     * char and Character
     * int and Integer
     * String
     * enum values
     *
     * Note that boolean and long, and their associated wrapper classes, are not supported by switch statements.
     *
     *  The values in each case statement must be compile-time constant values of the same data type
     *  as the switch value. This means you can use only literals, enum constants,
     *  or final constant variables of the same data type. No function parameter directly.
     *
     *
     */
    @Test
    public void Test_Switch(){
        String will = "best";
        final String bo ="Bo";
        int level = 0;

        switch(will){
            case "Best": level =1; break;

            case "best": level =2; break;

            case "secondary": level =3; break;

            case bo: level =4; break;

            default: level = 5;
        }

        assertEquals(2, level);

        level =0;
        Integer i = 5;
        char cc = 'b';


        switch(cc){
            case 5 : level =1; break;

            case 'a': level =2; break;
        }


        assertEquals(level, 0);

        final int xx = 5;
        //final char xx = 'c';

        switch(cc){
            case xx : level =1; break;

            case 'a': level =2; break;
        }

    }

    @Test
    public void Test_WhileAndDoWhile(){
        int counter1 = 0;
        int counter2 = 0;
        int i = 0;

        while(i<10){
            counter1++;
            i++;
        }
        assertEquals(10, counter1);

        i = 0;
        do{
            counter2++;
            i++;
        }while(i<10);

        assertEquals(10, counter2);


    }
    /**
     * Note that the semicolons separating the three sections are required,
     * as for( ; ) and for( ) will not compile
     *
     * When for-each was introduced in Java 5, it was added as a compile-time enhancement.
     * This means that Java actually converts the for-each loop into a standard for loop during compilation.
     */
    @Test(timeout = 1000L)
    public void Test_InfiniteLoopUsingFor(){
        int i=0;
        for( ; ; ){
            i++;
        }

    }

    /**
     * multi-term in for, only one data type can be used initialization part
     */
    @Test
    public void Test_MultiTermForLoop(){
        int i =0;
        for (long x = 0,y =4; y<10 && x<10; x++, y++){
            i++;

        }

        assertEquals(6, i);
    }

    /**
     * For readability, they are commonly expressed in uppercase,
     * with underscores between words, to distinguish them from regular variables.
     *
     * if-then statements, switch statements, and loops all can have optional labels
     */
    @Test
    public void Test_BreakWithLabel(){
        int[][] list = {{1,13,5},{1,2,5},{2,7,2}};
        int searchValue = 2;
        int positionX = -1;
        int positionY = -1;


        PARENT_LOOP: for(int i=0; i<list.length; i++) {
            for(int j=0; j<list[i].length; j++) {
                if(list[i][j]==searchValue) {
                    positionX = i;
                    positionY = j;
                    break PARENT_LOOP;
                }
            }
        }

        assertEquals(positionX,1);
        assertEquals(positionY,1);


    }

    @Test(timeout = 100)
    public void test_IfMisuseBooleanExpression() {
        boolean flag = false;
        while (flag = true) {
            System.out.println("Help!");
        }

    }

    @Test(timeout = 100)
    public void test_DoWhileWithoutBraces() {

        do System.out.println("ww");
        while(true);
    }

    @Test
    public void test_ForEachWithSuperclassType() {
        List<String> strs = Arrays.asList("will", "abc", "wang", "xxx", "xyz");
        for (Object o : strs) {
            System.out.println(o);
        }
    }

    @Test
    public void test_OperatorWithChar() {
        char a = 'a';
        char b = 'b';
        int i = a + b;
        char c = (char)i;
        System.out.println(c);
    }
    @Test
    public void test_PrintArray() {
        int[] arr = {3, 4, 5, 112, 34, 56};
        System.out.println(arr); //[I@4cdf35a9
        System.out.println(Arrays.toString(arr));//[3, 4, 5, 112, 34, 56]
        System.out.println(Arrays.asList(arr)); //[[I@4cdf35a9], as one Object
        System.out.println(Arrays.asList(Arrays.toString(arr))); // as one String
        Integer[] arr1 = {3, 4, 5, 112, 34, 56};
        System.out.println(Arrays.asList(arr1)); // parameter should be T


    }

    @Test(expected = NullPointerException.class)
    public void test_EqualsEquals() {
        int i = 5;
        Integer ig = new Integer(5);
        assertTrue(i == ig);
        Integer in = null;
        assertTrue(i == in);

    }



//
//    private String color;
//
//    /**
//     *  can access private var in main() of the class
//     */
//    public static void main(String[] args) {
//        AsmTest at = new AsmTest();
//        at.color = "white";
//        System.out.print(at.color);
//    }

}
