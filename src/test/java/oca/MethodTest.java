package oca;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
/**
 * Regular imports are for importing classes.
 * Static imports are for importing static members of classes.
 *
 * Java would give it preference over the imported one and the method we coded would be used.
 *
 * lenient -- more freedom
 * <p></p>
 * Tricky!!! You can't call the default constructor written by the compiler using this().
 * Because you can only call this() in a constructor, once you have this constructor,
 * compiler won't write the default one for you any more.
 * <p></>
 * default constructor will only exist when there is no user defined constructor
 * including the user define public no-arg constructor!!
 */
import static org.junit.Assert.*;

/**
 * When you see such questions on the exam, write down the values of each variable. <p> Created by
 * williaz on 10/28/16 - 10/30, 3D
 * <p>
 * # return type must be after access modifier, and followed by function
 * name !!! <p> The exam creators like to trick you by putting method elements in the wrong order or
 * using incorrect values. <p> # Java allows the optional specifiers to appear before the access
 * modifier. <p> tricky - There is a return statement, but it doesn’t always get run. --> compile
 * err <p> # Extending means creating a subclass that has access to any protected or public members
 * of the parent class. <p> Subclasses and classes in the same package are the only ones allowed to
 * access protected members. <p> # Watch for this on the exam. Ignored returned values are tricky.
 * <p> # The exam will often use the same name to try to confuse you.
 */
public class MethodTest {

    public void printSth() {
        System.out.println("well");
        return;  // return in void method
    }

    /**
     * When returning a value, it needs to be assignable to the return type.
     * you can implicit conversion, or autoboxing, but now in the same time
     */
    public long getNums(int a) {
        return a + 10;
    }

    public Integer getInteger(int i) {
        return i;
    }

//    public Long getLong(int i){
//        return i;
//    }

    /**
     * A vararg parameter must be the last element in a method’s parameter list.
     * This implies you are only allowed to have one vararg parameter per method.
     * <p>
     * When calling a method with a vararg parameter,
     * you have a choice. You can pass in an array,
     * or you can list the elements of the array and let Java create it for you.
     * <p>
     * You can even omit the vararg values in the method call
     * and Java will create an array of length zero for you.
     * <p>
     * it is still possible to pass null explicitly <-- null as a placeholder of a object
     */
    public int getLength(int num, int... varg) {
        return varg.length;
    }

    @Test
    public void Test_VarargMethod() {
        assertEquals(getLength(5), 0);
        assertEquals(getLength(5, 1, 2), 2);
        assertEquals(getLength(5, new int[]{2, 3, 4}), 3);

    }

    @Test(expected = NullPointerException.class)
    public void Test_WhenNullPassToVararg_ThenThrowException() {
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
    public void Test_PrivateStaticVariable() {
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
     * <p>
     * The exam creators will try to trick you
     * into thinking a NullPointerException is thrown
     * because the variable happens to be null. Don’t be fooled!!!!!
     * <p>
     * You can use an instance of the object to call a static method.
     */

    @Test
    public void Test_StaticVariable_FoolYou_NoException() {
        MethodTest mt = new MethodTest();
        mt.nums = 5;
        mt = null;
        assertEquals(mt.nums, 5);

    }

    /**
     * static final constants use a different naming convention than other variables.
     * They use all uppercase letters with underscores between “words.”
     * <p>
     * # final for primitive and reference type
     */

    private final static double MATH_PI = 3.1415926;

    /**
     * # Static initializers add the static keyword to specify
     * they should be run when the class is first used.
     * <p>
     * #The static initializer runs when the class is first used.
     * The statements in it run and assign any static variables as needed.
     */
    public static final int one;

    static {
        one = 10;
    }

    /**
     * Using static and instance initializer can make your code much harder to read. Everything
     * that could be done in an instance initializer could be done in a constructor instead. <p>
     * There is a common case to use a static initializer: when you need to initialize a static
     * field and the code to do so requires more than one line.
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
    public void Test_StaticInitializerUsage() {
        System.out.println(list);
    }

    /**
     * # Java is a “pass-by-value” language. -->for Variable Assignments !!!
     * This means that a copy of the variable is made and the method receives that copy.
     */

    public void changeInt(int a) {
        a = 10;
    }

    public void changeSb(StringBuilder sb) {
        sb.append("some");
    }

    @Test
    public void Test_Java_PassByValue() {

        int a = 5;
        StringBuilder sb = new StringBuilder("awe");

        changeInt(a);
        changeSb(sb);

        assertEquals(a, 5);
        assertEquals(sb.toString(), "awesome");

    }

    /**
     * # Method overloading occurs when there are different method signatures with the same name but
     * different type or numbers of  parameters. <p> # there can be different access modifiers,
     * specifiers (like static), return types, and exception lists. <p> # [Array vs Varargs]: Trick
     * question: Remember that Java treats varargs as if they were an array. you can only call the
     * varargs version with stand-alone parameters
     */
    //public int getLen(int[] arr) { return arr.length; } // doesn't count overload
    public int getLen(int... arr) {return arr.length; }

    public String changeName(String name) {
        return name + "son";
    }

    private String changeName(StringBuilder firstName, StringBuilder lastName) throws NullPointerException {
        return firstName.append(" ").append(lastName).append("son").toString();
    }

    @Test
    public void Test_MethodOverloading() {
        String name = "Will Tomp";
        StringBuilder first = new StringBuilder("Will");
        StringBuilder last = new StringBuilder("Tomp");

        name = changeName(name);
        String name1 = changeName(first, last);

        assertEquals(name, name1);

    }

    public int addOne(int a) {
        return a + 1;
    }

    public Integer addOne(Integer a) {
        return a + 1;
    }

    public String rename(String s) {
        return s + "y";
    }

    public Object rename(Object o) {
        return o + "son";
    }

    /**
     * Java tries to use the most specific parameter list it can find.
     * Note that Java can only accept wider types.
     * autoboxing and varargs come last when Java looks at overloaded methods.(1.5 -)
     * <p>
     * # It cannot handle converting in two steps: int -> long, long->Long
     * OK: int -> Integer = Object
     */
    public String getStr (long l) { return Long.toString(l); };
    public String getStr (Integer i) {return Integer.toString(i)+"X"; }
    @Test
    public void Test_AutoboxingWithOverloading() {
        assertEquals("34", getStr(34));
        Integer i = new Integer(3);

        assertEquals(4, addOne(3));
        assertEquals(new Integer(4), addOne(i));

        assertEquals("Willy", rename("Will"));
        assertEquals("1son", rename(1)); // use Object version through Autoboxing

    }

    /**
     * Constructors are used when creating a new object. This process is called instantiation
     * because it creates a new instance of the class. <p> A constructor is typically used to
     * initialize instance variables <p> # Java sees no constructor was coded and generated default
     * no-argument constructor. This happens during the compile step. <p> # Having a private
     * constructor in a class tells the compiler not to provide a default no argument constructor. It
     * also prevents other classes from instantiating the class. This is useful when a class only
     * has static methods or the class wants to control all calls to create new instances of itself.
     * <p> # the this() call must be the first non-commented statement in the constructor <br> #
     * constructor chaining] is to have each constructor add one parameter until getting to the
     * constructor that does all the work. <p> # The constructor is part of the initialization
     * process, so it is allowed to assign final instance variables in it. By the time the
     * constructor completes, all final instance variables must have been set.
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
    public void Test_ConstructorChaining() {
        Mouse mouse = new Mouse(15);
        assertEquals("15 16 6", mouse.print());
    }

    /**
     * # [Order of Initialization]
     * 1. If there is a superclass, initialize it first
     * 2. Static variable declarations and static initializer in the order they appear in the file.
     * 3. Instance variable declarations and instance initializer in the order they appear in the file.
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
     * <br>
     * A solution is to make a copy of the mutable object. This is called a defensive copy.
     * Another approach for the getter is to return an immutable object:
     */

    static public final class ImmutableStringBuider {

        StringBuilder sb;

        public ImmutableStringBuider(StringBuilder sb) {
            this.sb = sb;
        }

        public StringBuilder getSB() {
            return new StringBuilder(sb); // defensive copy
        }


    }

    @Test
    public void Test_ImmutableObject() {
        StringBuilder sb = new StringBuilder("will");

        ImmutableStringBuider iSB = new ImmutableStringBuider(sb);


    }

    /**
     * Encapsulation refers to preventing callers from changing the instance variables directly.
     * <- Instance variables must be private for this to work.
     * Immutability refers to preventing callers from changing the instance variables at all.
     */

    static public class Animal {
        private String species;
        private boolean isHop;
        private boolean isSwim;
        private boolean isFly;

        public Animal(String speciesName, boolean hopper, boolean swimmer) {

            this(speciesName, hopper, swimmer, false);
        }

        public Animal(String species, boolean isHop, boolean isSwim, boolean isFly) {
            this.species = species;
            this.isHop = isHop;
            this.isSwim = isSwim;
            this.isFly = isFly;
        }

        public boolean canHop() {
            return isHop;
        }

        public boolean canSwim() {
            return isSwim;
        }

        public boolean canFly() {
            return isFly;
        }

        public String toString() {
            return "species: " + species;
        }
    }

    /**
     * parameter -> body
     * <p>Functional programming is a way of writing code more declaratively. You specify what you
     * want to do rather than dealing with the state of objects. You focus more on expressions than
     * loops.</p> <p>A lambda expression is a block of code that gets passed around. -> anonymous
     * method, closures. a lambda expression is like a method that you can pass as if it were a
     * variable.</p> <p>Deferred execution means that code is specified now but will run later.</p>
     * <p>A body that has one or more lines of code, including a semicolon and a return statement;
     * Java doesn’t require you to type return or use a semicolon when no braces are used. Java
     * doesn’t allow us to redeclare a local variable in lambda expression</p> <p>The parentheses
     * can only be omitted if there is a single parameter and its type is not explicitly stated.
     * Remember that the parentheses are only optional when there is one parameter and it doesn’t
     * have a type declared. <br> there isn’t a rule that says you must use all defi ned parameters.
     * <p></p> Remember the one method in Predicate interface called boolean test(T t). <p> Any
     * change need not be duplicated into multiple places. It improves manageability of code. </p>
     * <p></>
     * It is tricky to use types in a lambda when they are implicitly specified.
     * Remember to check the interface for the real type.
     */

    @Test
    public void Test_Predicate_Test() {
        List<Animal> animals = new ArrayList<Animal>(); // list of animals
        animals.add(new Animal("fish", false, true));
        animals.add(new Animal("kangaroo", true, false));
        animals.add(new Animal("rabbit", true, false));
        animals.add(new Animal("turtle", false, true));

        animals.stream().filter(a -> a.canHop()).forEach(System.out::println);
        animals.stream().filter(a -> a.canSwim()).forEach(System.out::println);
        animals.stream().map(a -> a.species).filter(a -> a.startsWith("ra")).forEach(System.out::println);


    }

    //Predicate<Animal>
    private static void print(List<Animal> animals, CheckTrait checker) {
        for (Animal animal : animals) {
            if (checker.test(animal)) // the general check
                System.out.print(animal + " ");
        }
        System.out.println();
    }

    /**
     * in OOP way VS FP, it can work as a FunctionalInterface also.
     */
    public interface CheckTrait {
        boolean test(Animal a);
    }


    /**
     * OOP: duplicate declarations
     */
    static public class CheckIfHopper implements CheckTrait {

        public boolean test(Animal a) {
            return a.canHop();
        }
    }

    static public class CheckIfSwimmer implements CheckTrait {
        public boolean test(Animal a) {
            return a.canSwim();
        }
    }


    /*
     write codes in comment to get rid of IDE's help(compile check) first,
     then uncomment it to get the ease of IDE

    public interface Predicate<T> {
        boolean test (T t);
    }
     */

    /**
     * FP: declare interface first, write logic when you use;
     * make changing and writing logic more flexible and handy.
     */
    @Test
    public void Test_OOPvsFP() {
        List<Animal> animals = new ArrayList<Animal>(); // list of animals
        animals.add(new Animal("fish", false, true));
        animals.add(new Animal("kangaroo", true, false));
        animals.add(new Animal("rabbit", true, false));
        animals.add(new Animal("turtle", false, true));
        animals.add(new Animal("Orca", true, true));
        animals.add(new Animal("Bird", true, false, true));
        animals.add(new Animal("Duck", true, true, true));

        System.out.println("OOP way:");
        print(animals, new CheckIfHopper());
        print(animals, new CheckIfSwimmer());
        print(animals, new CheckTrait() {
            @Override
            public boolean test(Animal a) {
                return a.canFly();
            }
        });

        System.out.println("FP way:");
        print(animals, a -> a.canHop());
        print(animals, a -> a.canSwim());
        print(animals, a -> {
            boolean b = a.canSwim() && a.canHop();
            return b;
        });
        print(animals, a -> a.canFly());


    }


}
