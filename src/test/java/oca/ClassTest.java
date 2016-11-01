package oca;

import org.junit.Test;

import oca.ood.Animal;
import oca.ood.Fish;
import oca.ood.Lion;
import oca.ood.Orca;

import static org.junit.Assert.*;

/**
 * Created by williaz on 10/31/16.
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
 * 1. inserting code into any class you write that doesn’t extend a specific class.
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
 * A concrete class is the fi st nonabstract subclass that extends an abstract class
 * and is required to implement all inherited abstract methods.
 * <p></p>
 * The exam creators are fond of questions like this one,
 * which mixes nonabstract classes with abstract methods.
 * They are also fond of questions with methods marked as abstract
 * for which an implementation is also defined.
 * <P>
 * a concrete subclass is not required to provide an implementation for an abstract method
 * if an intermediate abstract class provides the implementation.
 * </P>
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

}
