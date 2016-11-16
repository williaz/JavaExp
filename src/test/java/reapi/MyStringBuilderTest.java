package reapi;

import org.junit.Test;
import static org.junit.Assert.*;
/**
 * Created by williaz on 11/15/16.
 */
public class MyStringBuilderTest {
    @Test
    public void test_ToString() {
        MyStringBuilder msb = new MyStringBuilder("williaz");
        assertEquals("williaz", msb.toString());
    }
    @Test
    public void test_Substring() {
        MyStringBuilder msb = new MyStringBuilder("williaz");
        assertEquals("li", msb.substring(3, 5));
        assertEquals("will", msb.substring(0, 4));
    }

    @Test
    public void test_Append_Chaining() {
        MyStringBuilder msb = new MyStringBuilder("will");
        MyStringBuilder msb1 = new MyStringBuilder("best");
        msb.append(" is ").append(msb1);
        assertEquals("will is best", msb.toString());
    }

    @Test
    public void test_Reverse() {
        MyStringBuilder msb = new MyStringBuilder("will");
        msb.reverse();
        assertEquals("lliw", msb.toString());

        MyStringBuilder msb1 = new MyStringBuilder("williaz");
        msb1.reverse();
        assertEquals("zailliw", msb1.toString());

    }

}
