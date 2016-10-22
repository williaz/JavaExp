package oca;

import org.junit.Test;

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
