package oca;

import org.junit.Test;

import oca.ood.Animal;
import oca.ood.Lion;

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
 */
public class ClassTest {
    @Test
    public void Test_PrivateField() {
        Animal animal = new Animal(4);
        animal.setAge(10);
        Lion lion = new Lion(4);
        lion.setAge(12);
        lion.setDefName("Anmy");
        lion.setName("Honer");
        lion.roar();
        Animal lion1 = new Lion(21);
        lion1.setAge(13);


    }
}
