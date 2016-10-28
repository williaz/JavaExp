package oca;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
/**
 * Regular imports are for importing classes.
 * Static imports are for importing static members of classes.
 *
 * Java would give it preference over the imported one and the method we coded would be used.
 */
import static org.junit.Assert.*;

/**
 * Created by williaz on 10/28/16.
 *
 * # return type must be after access modifier, and followed by function name !!!
 *
 * The exam creators like to trick you by putting method elements
 * in the wrong order or using incorrect values.
 *
 * # Java allows the optional specifiers to appear before the access modifier.
 *
 * tricky - There is a return statement, but it doesn’t always get run. --> compile err
 *
 * # Extending means creating a subclass that has access to any protected or public members of the parent class.
 *
 *   Subclasses and classes in the same package are the only ones allowed to access protected members.
 */
public class MethodTest {

    public void printSth(){
        System.out.println("well");
        return ;  // return in void method
    }

    /**
     * When returning a value, it needs to be assignable to the return type.
     * you can implicit conversion, or autoboxing, but now in the same time
     */
    public long getNums(int a){
        return a + 10;
    }

    public Integer getInteger(int i){
        return i;
    }

//    public Long getLong(int i){
//        return i;
//    }

    /**
     * A vararg parameter must be the last element in a method’s parameter list.
     * This implies you are only allowed to have one vararg parameter per method.
     *
     * When calling a method with a vararg parameter,
     * you have a choice. You can pass in an array,
     * or you can list the elements of the array and let Java create it for you.
     *
     * You can even omit the vararg values in the method call
     * and Java will create an array of length zero for you.
     *
     * it is still possible to pass null explicitly <-- null as a placeholder of a object
     */
    public int getLength(int num, int ... varg){
        return varg.length;
    }

    @Test
    public void Test_VarargMethod(){
        assertEquals(getLength(5), 0);
        assertEquals(getLength(5, 1, 2), 2);
        assertEquals(getLength(5, new int[]{2, 3, 4}), 3);

    }

    @Test(expected = NullPointerException.class)
    public void Test_WhenNullPassToVararg_ThenThrowException(){
        getLength(5, null);
    }

    /**
     * Usage of static methods:
     * 1. For utility or helper methods that don’t require any object state.
     * 2. For state that is shared by all instances of a class, like a counter.
     */
    public static int nums;
    private static int counter; //default 0

    public MethodTest() {
        counter++;
    }

    @Test
    public void Test_PrivateStaticVariable(){
        assertEquals(MethodTest.counter, 1); // running test itself will instantiate one
        MethodTest mt = new MethodTest();
        MethodTest mt1 = new MethodTest();
        MethodTest mt2 = new MethodTest();
        MethodTest mt3 = new MethodTest();

        assertEquals(MethodTest.counter, 5);

    }

    /**
     * Remember to look at the reference type for a variable
     * when you see a static method or variable.
     *
     * The exam creators will try to trick you
     * into thinking a NullPointerException is thrown
     * because the variable happens to be null. Don’t be fooled!
     *
     * You can use an instance of the object to call a static method.
     */

    @Test
    public void Test_StaticVariable_FoolYou_NoException(){
        MethodTest mt = new MethodTest();
        mt.nums = 5;
        mt = null;
        assertEquals(mt.nums, 5);

    }

    /**
     * static final constants use a different naming convention than other variables.
     * They use all uppercase letters with underscores between “words.”
     *
     * # final for primitive and reference type
     */

    private final static double PI = 3.1415926;

    /**
     * # Static initializers add the static keyword to specify
     *   they should be run when the class is first used.
     *
     * #The static initializer runs when the class is first used.
     *  The statements in it run and assign any static variables as needed.
     */
    public static final int one;

    static{
        one = 10;
    }

    /**
     * Using static and instance initializers can make your code much harder to read.
     * Everything that could be done in an instance initializer could be done in a constructor instead.
     *
     * There is a common case to use a static initializer:
     * when you need to initialize a static field and the code to do so requires more than one line.
     */

    private static List<Integer> list;

    static {
        list = new ArrayList<>();
        list.add(5);
        list.add(9);
        list.add(7);
        list.add(3);
    }

    @Test
    public void Test_StaticInitializerUsage(){
        System.out.println(list);
    }




}
