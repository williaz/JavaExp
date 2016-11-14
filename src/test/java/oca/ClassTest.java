package oca;

import org.junit.Test;

import oca.ood.Animal;
import oca.ood.Bird;
import oca.ood.CanFly;
import oca.ood.CanWalk;
import oca.ood.CanadaGoose;
import oca.ood.Fish;
import oca.ood.Lion;
import oca.ood.Orca;
import oca.ood.Swan;
import oca.ood.Whale;

import static org.junit.Assert.*;

/**
 * Created by williaz on 10/31/16 - 11/4 5D
 * <p>
 * At its core, proper Java class design is about code
 * reusability, increased functionality, and standardization.
 * <p> <tt>Inheritance<tt/> is the
 * process by which the new child subclass automatically includes any public or protected
 * primitives, objects, or methods defined in the parent class. single inheritance - multiple
 * levels of inheritance
 * <p>
 * It is possible in Java to prevent a class from being extended by
 * marking the class with the final modifier.
 * <p>
 * public and default package-level class access modifiers,
 * because these are the only ones that can be applied to top-level classes within a Java file.
 * The protected and private modifiers can only be applied to inner classes,
 * which are classes that are defined within other classes,
 * <p>
 * Compiler will automatically:
 * 1. inserting extends Object into any class you write that doesn’t extend a specific class.
 * 2. insert a default no-argument constructor in the case that no constructor is declared
 * 3. inserts a call to the no-argument constructor super() if the first statement is not a call to the parent constructor.
 * <p>
 * In Java, the <b>first statement</b> of every constructor is
 * either a call to another constructor within the class, using this(),
 * or a call to a constructor in the direct parent class, using super().
 * <p>
 *     You should be wary of any exam question in which the parent class defines a constructor
 *     that takes arguments and doesn’t define a no-argument constructor.
 *     Be sure to check that the code compiles before answering a question about it.
 *<p>
 *     In Java, the parent constructor is always executed before the child constructor.
 *<p> !!!Be care of access modifier of methods in parent class that are supposed to be overridden.</>
 * <p>Any time you see a method on the exam with the same name as a method in the parent class,
 * determine whether the method is being overloaded or overridden first;</p>
 * <p>
 *     override for non-private methods
 * </p>
 * <p>a child method may hide or eliminate a parent method’s exception without issue.</p>
 * <p>
 *     <b>method hidding VS overriding<b/>
 *     A hidden method occurs when a child class defines a static method
 *     with the same name and signature as a static method defined in a parent class.<br>
 *     5. must static on both parent and child methods<br>
 *     avoid hiding static methods in your own work
 *
 * <P>
 * final - 1. you forbid a child class from overriding this method.
 *         2. you cannot hide a static method in a parent class if it is marked as final.
 *         3. For guarantee certain behavior of a method in the parent class,
 *         regardless of which child is invoking the method.
 * </P>
 *. An abstract class is a class that is marked with the abstract keyword and cannot be instantiated.
 * An abstract method is a method marked with the abstract keyword defined in an abstract class,
 * for which no implementation is provided in the class in which it is declared.
 * # we note that an abstract class / methods cannot be marked as final.
 * # a method may not be marked as both abstract and private.
 * A concrete class is the first non-abstract subclass that extends an abstract class
 * and is required to implement all inherited abstract methods.
 * <p></p>
 * The exam creators are fond of questions like this one,
 * which mixes non-abstract classes with abstract methods.
 * They are also fond of questions with methods marked as abstract
 * for which an implementation is also defined.
 * <P>
 * a concrete subclass is not required to provide an implementation for an abstract method
 * if an intermediate abstract class provides the implementation.
 * </P>
 * An interface is an abstract data type that defines a list of abstract public methods
 * that any class implementing the interface must provide.
 * An interface can also include a list of constant variables and default methods
 * use uppercase letters to denote constant values within a class.
 * <p>
 *
 */
public class ClassTest {
    @Test
    public void Test_ScopeInClass() {
        Animal animal = new Animal(4);
        animal.setAge(10);
        Lion lion = new Lion(4);
        lion.setAge(12);
        lion.setDefName("Anmy");
        lion.setName("Honer");
        lion.roar();
        Animal lion1 = new Lion(21);
        lion1.setAge(13);
        /*
        # Overriding a method: a child method replaces the parent method in calls defined in both the parent and child.
        # At runtime the child version of an overridden method is always executed for an instance
        regardless of whether the method call is defined in a parent or child class method. -> toString()
         */

        System.out.println(lion);
        System.out.println(lion1);

        lion1.testInAnimal();
        lion.testInLion();


    }

    @Test
    public void Test_AbstractAndConcreteClass() {
        Fish killer = new Orca("Dolphin", "Will");
        assertEquals("Will", killer.getName());
        assertEquals("Whale", killer.getSpecies());


    }

    /**
     * All methods in interfaces are assumed to be public.
     *  abstract methods
     * Interface variable - public static final
     * <p>
     * A default method is a method defined  an abstract method provide a default implementation
     * which you can choose to override, but not require to
     * within an interface with the default keyword in which a method body is provided.
     * <p></p>
     * A default method is not assumed to be static, final, or abstract,
     * as it may be used or overridden by a class that implements the interface.
     * # They can not be marked as final or abstract,
     * because they are allowed to be overridden in subclasses
     * but are not required to be overridden.
     * # the interface may redeclare the method as abstract,
     * requiring classes that implement the new interface to explicitly provide a method body
     * # having a class that implements or inherits two duplicate default methods
     * forces the class to implement a new version of the method, or the code will not compile.
     * <p>
     * A static method defined in an interface is not inherited in any classes that implement the interface.
     * -> a class that implements two interfaces containing static methods with the same signature
     * will still compile at runtime,
     * </p>
     */

    @Test
    public void Test_Interface() {
        Bird swan = new Swan();
        Swan swan1 = new Swan();
        assertEquals(100, swan.MAXIMUM_Height); // access interface constant
        assertEquals(100, swan1.MAXIMUM_Height); // access interface constant
        assertEquals(30, swan.getSpeed()); // override for two interface
        assertEquals("flyer and walker", swan.getAbility()); // default methods in two interface
        assertEquals("Flying", CanFly.printAction());
        assertEquals("Walking", CanWalk.printAction());  // Both interface get implements in Bird, no conflict for static methods
    }


    /**
     * Java supports polymorphism, the property of an object to take on many different forms.<br>
     * a Java object may be accessed (passed) using a reference with the same type as the object,
     * a reference that is a superclass of the object,
     * or a reference that def nes an interface the object implements, either directly or through a superclass
     * <p>access scope
     * 1. The type of the object determines which properties exist within the object in memory.
     * 2. The type of the reference to the object determines which methods and variables are accessible to the Java program.
     * explicit casting: no related - cannot compile
     * <p>A virtual method( can be overridden) is a method in which the specific implementation is not determined until runtime
     * if you call a method on an object that overrides a method, you get the overridden method,
     * even if the call to the method is on a parent reference or within the parent class.
     * </p>
     * polymorphic parameters of a method
     * use interface type as the polymorphic parameter type - reusable
     * access overridden methods using a reference to the parent class
     * -> override: 1. broader access, 2. stricter exception, 3. covariant return type
     * <p>
     * static methods in interface must have body.
     * </p>
     * 1. interface itself doesn't inherit Object, only class.<br>
     * 2. watch out final for overriding<br>
     * 3. private method can only be hidden, not overriden, just it is used depend on the reference type.
     * 4. watch out abstract method should not have {}.
     *
     */

    @Test
    public void Test_Polymorphism() {
        Bird goose = new CanadaGoose();
        assertEquals("Canada goose", goose.displayInfo());
        feed(new Swan());
        feed(new CanadaGoose());
        Swan swan = new Swan();
        Swan goose1 = new Swan();
        swan.getFlawNum();
        assertEquals(3, goose1.getFlawNum());

    }

    //polymorphic parameters
    public static void feed (Bird bird){
        System.out.println("Feeding " + bird.displayInfo());
    }

    @Test(expected = ClassCastException.class)
    public void Test_DownCasting() {
        Bird swan = new Swan();
        CanadaGoose goose = (CanadaGoose) swan;
        /*
        instanceof operator can be used to check whether an object belongs to a particular class
        and to prevent ClassCastExceptions at runtime.

        if (swan instanceof CanadaGoose) {
            CanadaGoose goose = (CanadaGoose) swan;
        }
        */

    }

    @Test
    public void Test_Singleton() {
        assertEquals("Samuel", Single.getSingle().getName());
        assertTrue(Single.getSingle() == Single.getSingle());
    }



}
