package simple;

import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.*;

/**
 * Created by williaz on 11/20/16.
 */
public class generalTest {
    private Integer[] arrInt;

    @Before
    public void setUp() throws Exception {
        arrInt = new Integer[]{34, 12, 4, 56, 124, 34, 65, 98, 54, 10, 3, 67, 7, 33};
    }

    /**
     * #1. How to Check if an Array Contains a Value in Java Efficiently?
     * 1. List contains - fair
     * 2. Set contains - slowest
     * 3. loop array  - Best
     * 4. sort and binarySearch - req sorted
     * <p></p>
     * Pushing the array to another collection requires spin through all elements to read them in
     * before doing anything with the collection type.
     */

    @Test
    public void test_ArrayContainsValue1() {

        List<Integer> list = Arrays.asList(arrInt);
        assertTrue(list.contains(54));
    }

    @Test
    public void test_ArrayContainsValue2() {
        Set<Integer> set = new HashSet<>(Arrays.asList(arrInt));
        assertTrue(set.contains(54));
    }

    @Test
    public void test_ArrayContainsValue3() {
        boolean flag = false;
        for (int i : arrInt) {
            if (54 == i) flag = true;
        }
        assertTrue(flag);
    }

    @Test
    public void test_ArrayContainsValue4() {
        Arrays.sort(arrInt);
        assertTrue(Arrays.binarySearch(arrInt, 54) >= 0);
    }

    /**
     * #2. Exception
     * <p></p>
     * If an exception can be properly handled then it should be caught,
     * otherwise, it should be thrown.
     */
    @Test(expected = NullPointerException.class)
    public void test_Exception1() {
        Double.parseDouble(null);
    }

    @Test(expected = NumberFormatException.class)
    public void test_Exception2() {
        Integer.parseInt(null);
    }

    /**
     * #3. Field hiding
     * Within a class, a field that has the same name as a field in the superclass
     * hides the superclass’s field, even if their types are different.
     * Within the subclass, the field in the superclass cannot be referenced by its simple name.
     * Instead, the field must be accessed through super.
     * Generally speaking, we don’t recommend hiding fields as it makes code difficult to read.
     */

    /**
     * #4. constructor
     * The reason to have super constructor called is that
     * if super class could have private fields which need to be initialized by its constructor.
     */

    /**
     * #5. enum
     */
    public enum Color {
        RED(50), GREEN(100), BLUE(150), YELLOW(200);
        private final int val;

        private Color(int i) {
            val = i;
        }

        public int getVal() {
            return val;
        }
    }

    @Test
    public void test_Enum() {
        for (Color c : Color.values()) {
            System.out.println(c + " is " + c.getVal()); // in order
        }
    }
}
