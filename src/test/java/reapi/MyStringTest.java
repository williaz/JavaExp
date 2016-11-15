package reapi;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Created by williaz on 11/15/16.
 */
public class MyStringTest {
    @Test
    public void test_Constructor() {
        MyString mStr = new MyString(new char[] {'a', 'c', 'e'});
        assertEquals("ace", mStr.toString());
        String str = "will is best 1";
        MyString mStr1 = new MyString(str);
        assertEquals(str, mStr1.toString());
        StringBuilder sb = new StringBuilder("you SB");
        MyString mStr2 = new MyString(sb);
        assertEquals(sb.toString(), mStr2.toString());
    }
    @Test
    public void test_Substring() {
        String str = "will is best 1";
        MyString mStr1 = new MyString(str);
        assertEquals("will", mStr1.substring(0, 4));
        assertEquals("best", mStr1.substring(8, 12));
        assertEquals("1", mStr1.substring(13, 14));
        assertEquals("best 1", mStr1.substring(8));
    }

    @Test
    public void test_IndexOf_Contains() {
        String str = "will is best 1";
        MyString mStr1 = new MyString(str);
        assertTrue(0 == mStr1.indexOf("will"));
        assertTrue(8 == mStr1.indexOf("best"));
        assertTrue(13 == mStr1.indexOf("1"));

        assertTrue(mStr1.contains("will"));
        assertTrue(mStr1.contains("best"));
        assertTrue(mStr1.contains("1"));
        assertFalse(mStr1.contains("abc"));
    }

    @Test
    public void test_Concat() {
        MyString mStr1 = new MyString("will");
        assertEquals("williaz", mStr1.concat("iaz"));
    }
    @Test
    public void test_Replace() {
        MyString mStr1 = new MyString("wiabciaz");
        assertEquals("williaz", mStr1.replace("abc", "ll"));
    }

}
