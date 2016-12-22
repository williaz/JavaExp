package ocp;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import ocp.creational.Immutable;
import ocp.creational.LazySingleton;
import ocp.creational.MyUrl;
import ocp.creational.MyUrlBuilder;
import ocp.creational.Singleton;
import ocp.creational.factory.ChildMeal;
import ocp.creational.factory.Meal;
import ocp.creational.factory.MealFactory;

import static org.junit.Assert.*;

/**
 * Created by williaz on 11/23/16 - 11/25 3d.
 *   a data model is the representation of our objects and their properties within our application
 * and how they relate to items in the real world.
 * <p>OOP:</p>
 * 1. <br>Polymorphism</br> is the ability of a single interface to support multiple underlying forms.
 * 2. <br>Encapsulation</br> is the idea of combining fields and methods in a class such that the methods operate on the data,
 *    as opposed to the users of the class accessing the fields directly.
 *    -> establish data rules to keep invariant
 * 3. <br>Inheritance<br> is the process by which the new child subclass automatically includes
 *    any public or protected primitives, objects, or methods defined in the parent class.
 *    -> is-a; overriding, automatic include public and protected member
 * 4. <br>Composition<br> as the property of constructing a class using references to other classes
 *    in order to reuse the functionality of the other classes
 *    ->an alternate to inheritance
 *      to simulate polymorphic behavior that cannot be achieved via single inheritance.-> reusable
 * <p>
 * <br>Design Pattern<br/>
 * Design patterns are often written to help prevent anti‐patterns from forming.
 * An anti‐pattern is a common solution to a reoccurring problem
 *    that tends to lead to unmanageable or difficult‐to‐use code.
 * #watch out:
 * 1. default, static method in interface must have body!
 * 2. lambda must have right hand side expression, X ()->
 * 3. interface cannot final!
 */

public class DesignTest {
    /**
     * For function interface, it has only one abstract method,
     *  # there is no restriction on num of default or static methods;
     *  compatible return type of FI's abstract method ---
     *  # When one parameter has a data type listed, though,
     *    all parameters must provide a data type.
     *  Not allow to redeclare lambda parameters.
     */

    /**
     * If you use a variable to refer to an object,
     * then only the methods or variables that are part of the variable’s reference type
     * can be called without an explicit cast.
     * <p>
     * 1. The type of the object determines which properties exist within the object in memory.
     * 2. The type of the reference to the object determines which methods and variables are accessible to the Java program.
     * <p>
     * 1. Casting an object from a subclass to a superclass doesn’t require an explicit cast.
     * 2. Casting an object from a superclass to a subclass requires an explicit cast.
     * 3. The compiler will not allow casts to unrelated types.
     *    # not related through any class hierarchy
     * 4. Even when the code compiles without issue, an exception may be thrown at runtime
     *    if the object being cast is not actually an instance of that class.
     *
     */

    /**
     * JavaBean:
     * is for boolean, get for Boolean
     *
     */

    /**
     * Singleton:
     * 1. private static final instance
     * 2. public static getInstance()
     * 3. private constructor -> implicit final
     */
    @Test
    public void test_Singleton() {

            Thread t1 = new Thread() {
                @Override
                public void run() {
                    Singleton single = Singleton.getInstance();
                    single.addQuantity(50);
                    single.consume(20);
                }
            };

            Thread t2 = new Thread() {
                @Override
                public void run() {
                    Singleton single = Singleton.getInstance();
                    single.addQuantity(100);
                    single.consume(30);
                }
            };


            try {
                t1.start();
                t2.start();
                t1.join();
                t2.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }


        Singleton single1 = Singleton.getInstance();
        single1.addQuantity(100);
        single1.consume(30);

        assertEquals(170, single1.getQuantity());
    }

    @Test
    public void test_LazySingleton() {
        Thread t1 = new Thread() {
            @Override
            public void run() {
                LazySingleton single = LazySingleton.getInstance();
                single.addQuantity(50);

                single.consume(20);
            }
        };

        Thread t2 = new Thread() {
            @Override
            public void run() {
                LazySingleton single = LazySingleton.getInstance();
                single.addQuantity(100);
                single.consume(30);
            }
        };


        try {
            t1.start();
            t2.start();
            t1.join();
            t2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        LazySingleton single1 = LazySingleton.getInstance();
        single1.addQuantity(100);
        single1.consume(30);

        assertEquals(170, single1.getQuantity());
    }

    /**
     * Immutable:
     * 1. Use a constructor to set all properties of the object.
     * 2. Mark all of the instance variables private and final .
     * 3. Don’t define any setter methods.
     * 4. Don’t allow referenced mutable objects to be modified or accessed directly.
     * 5. Prevent methods from being overridden.
     */
    @Test(expected = UnsupportedOperationException.class)
    public void test_Immutable() {
        List<String> foods = new ArrayList<>();
        foods.add("fish");
        foods.add("orange");
        foods.add("apple");
        foods.add("steak");
        foods.add("chopstick");

        Immutable imt = new Immutable("will", 25, foods);

        Immutable other = new Immutable(imt);
        other.getFood().forEach(System.out::println);


        List<String> list = imt.getFood();
        list.set(1, "KFC"); // cannot modify
    }

    /**
     * Builder:
     * tight- coupling
     * Avoid too many constructor
     * Why do not use setters instead? 1. for immutable class; 2. attributes depend on each other
     * Oftentimes, builder objects are used once and then discarded.
     * build() method to throw an exception if certain required fields are not set. or set default value
     * In practice, a builder class is often packaged alongside its target class,
     *    either as a static inner class within the target class or within the same Java package.
     * make target class's constructor a private or default package, forcing
     */
    @Test
    public void test_Builder() {
        MyUrlBuilder mub = new MyUrlBuilder();
        MyUrl url = mub.setScheme("http").setAuthority("github").setPath("williaz").builder();
        System.out.println(url);
        MyUrl url1 = mub.setScheme("https").setAuthority("github").setPath("williaz").setQuery("repo=JavaExp").builder();
        System.out.println(url1);
    }

    /**
     * Factory:
     * static method -> Factory Method Pattern
     * let the object package access constructor
     */
    @Test
    public void test_Factory() {
        Meal meal1 = MealFactory.getMeal(1);
        meal1.consume();

        Meal meal12 = MealFactory.getMeal(12);
        meal12.consume();

        Meal meal23 = MealFactory.getMeal(23);
        meal23.consume();

    }
}
