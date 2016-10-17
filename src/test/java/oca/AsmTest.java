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
