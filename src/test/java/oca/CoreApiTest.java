package oca;

import org.junit.Test;

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


}
