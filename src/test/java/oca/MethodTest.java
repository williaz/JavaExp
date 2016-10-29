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
 * When you see such questions on the exam, write down the values of each variable.
 *
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
 *
 * # Watch for this on the exam. Ignored returned values are tricky.
 *
 * # The exam will often use the same name to try to confuse you.
 *
 *
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

    private final static double MATH_PI = 3.1415926;

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

    /**
     * # Java is a “pass-by-value” language. -->for Variable Assignments !!!
     *   This means that a copy of the variable is made and the method receives that copy.
     */

    public void changeInt(int a){
        a = 10;
    }

    public void changeSb(StringBuilder sb){
        sb.append("some");
    }

    @Test
    public void Test_Java_PassByValue(){

        int a = 5;
        StringBuilder sb = new StringBuilder("awe");

        changeInt(a);
        changeSb(sb);

        assertEquals(a, 5);
        assertEquals(sb.toString(), "awesome");

    }

    /**
     * # Method overloading occurs when there are different method signatures
     *   with the same name but different type or numbers of  parameters.
     *
     * # there can be different access modifiers, specifiers (like static), return types, and exception lists.
     *
     * # [Array vs Varargs]: Trick question: Remember that Java treats varargs as if they were an array.
     *   you can only call the varargs version with stand-alone parameters
     */

    public String changeName(String name){
        return name+"son";
    }

    private String changeName(StringBuilder firstName, StringBuilder lastName) throws NullPointerException{
        return firstName.append(" ").append(lastName).append("son").toString();
    }

    @Test
    public void Test_MethodOverloading(){
        String name ="Will Tomp";
        StringBuilder first = new StringBuilder("Will");
        StringBuilder last = new StringBuilder("Tomp");

        name = changeName(name);
        String name1 = changeName(first, last);

        assertEquals(name, name1);

    }

    public int addOne(int a){
        return a+1;
    }

    public Integer addOne(Integer a){
        return a+1;
    }

    public String rename(String s){
        return s+"y";
    }

    public Object rename(Object o){
        return o+"son";
    }

    /**
     * Java tries to use the most specific parameter list it can find.
     * Note that Java can only accept wider types.
     * autoboxing and varargs come last when Java looks at overloaded methods.(1.5 -)
     *
     * # It cannot handle converting in two steps: int -> long, long->Long
     *   OK: int -> Integer = Object
     */
    @Test
    public void Test_AutoboxingWithOverloading(){
        Integer i = new Integer(3);

        assertEquals(4, addOne(3));
        assertEquals(new Integer(4), addOne(i));

        assertEquals("Willy", rename("Will"));
        assertEquals("1son", rename(1)); // use Object version through Autoboxing

    }

    /**
     * Constructors are used when creating a new object.
     * This process is called instantiation because it creates a new instance of the class.
     *
     * A constructor is typically used to initialize instance variables
     *
     * # Java sees no constructor was coded and generated default no-argument constructor.
     *   This happens during the compile step.
     *
     * # Having a private constructor in a class tells the compiler not to provide a default noargument constructor.
     *   It also prevents other classes from instantiating the class.
     *   This is useful when a class only has static methods
     *   or the class wants to control all calls to create new instances of itself.
     *
     * # the this() call must be the first non-commented statement in the constructor
     *
     * # constructor chaining] is to have each constructor add one parameter until getting to the constructor
     * that does all the work.
     *
     * # The constructor is part of the initialization process,
     *   so it is allowed to assign final instance variables in it.
     *   By the time the constructor completes,
     *   all final instance variables must have been set.
     */


    static public class Mouse {
        private int numTeeth;
        private int numWhiskers;
        private int weight;
        public Mouse(int weight) {
            this(weight, 16); // calls constructor with 2 parameters
        }
        public Mouse(int weight, int numTeeth) {
            this(weight, numTeeth, 6); // calls constructor with 3 parameters
            // new Mouse(weight, numTeeth, 6); lost
        }
        public Mouse(int weight, int numTeeth, int numWhiskers) {
            this.weight = weight;
            this.numTeeth = numTeeth;
            this.numWhiskers = numWhiskers;
        }
        public String print() {
            return (weight + " " + numTeeth + " " + numWhiskers);
        }
    }

    @Test
    public void Test_ConstructorChaining(){
        Mouse mouse = new Mouse(15);
        assertEquals("15 16 6", mouse.print());
    }

    /**
     * # [Order of Initialization]
     * 1. If there is a superclass, initialize it first
     * 2. Static variable declarations and static initializers in the order they appear in the file.
     * 3. Instance variable declarations and instance initializers in the order they appear in the file.
     * 4. The constructor.
     *
     * # Keep in mind that the four rules apply only if an object is instantiated.
     *   If the class is referred to without a new call, only rules 1 and 2 apply.
     *   The other two rules relate to instances and constructors.
     *   They have to wait until there is code to instantiate the object.
     */


    /**
     * Encapsulation means we set up the class so only methods in the class
     * with the variables can refer to the instance variables.
     *
     * For encapsulation, remember that data (an instance variable) is private and getters/setters are public.
     * Java defines a naming convention that is used in JavaBeans.
     * JavaBeans are reusable software components. JavaBeans call an instance variable a property.
     */

    /**
     * immutable is only measured after the object is constructed.
     * Immutable classes are allowed to have values. They just can't change after instantiation.
     *
     * A solution is to make a copy of the mutable object. This is called a defensive copy.
     * Another approach for the getter is to return an immutable object:
     */

    static public final class ImmutableStringBuider{

        StringBuilder sb;

        public ImmutableStringBuider(StringBuilder sb) {
            this.sb = sb;
        }

        public StringBuilder getSB(){
            return new StringBuilder(sb); // defensive copy
        }


    }

    @Test
    public void Test_ImmutableObject(){
        StringBuilder sb = new StringBuilder("will");

        ImmutableStringBuider iSB = new ImmutableStringBuider(sb);


    }

    /**
     * Encapsulation refers to preventing callers from changing the instance variables directly.
     * Immutability refers to preventing callers from changing the instance variables at all.
     */


}
