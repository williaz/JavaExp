package ocp;

/**
 * Created by williaz on 11/23/16.
 *   a data model is the representation of our objects and their properties within our application
 * and how they relate to items in the real world.
 * <p>OOP:</p>
 * 1. <br>Polymorphism</br> is the ability of a single interface to support multiple underlying forms.
 * 2. <br>Encapsulation</br> is the idea of combining fi elds and methods in a class such that the methods operate on the data,
 *    as opposed to the users of the class accessing the fi elds directly.
 *    -> establish data rules to keep invariant
 * 3. <br>Inheritance<br> is the process by which the new child subclass automatically includes
 *    any public or protected primitives, objects, or methods defined in the parent class.
 *    -> is-a; overriding, automatic include public and protected member
 * 4. <br>Composition<br> as the property of constructing a class using references to other classes
 *    in order to reuse the functionality of the other classes
 *    ->an alternate to inheritance
 *      to simulate polymorphic behavior that cannot be achieved via single inheritance.-> reusable
 */

public class DesignTest {
    /**
     * For function interface, it has only one abstract method,
     *  # there is no restriction on num of default or static methods;
     *  compatible return type of FI's abstract methodm ---
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

}
