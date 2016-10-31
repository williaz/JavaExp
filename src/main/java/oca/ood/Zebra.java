package oca.ood;

/**
 * Created by williaz on 10/31/16.
 * <p></p>
 * if the parent class doesn’t have a no-argument constructor ->
 * you must create at least one constructor in your child class
 * that explicitly calls a parent constructor via the super() command.
 */
public class Zebra extends Animal {

    // to match parent constructor
    public Zebra(int age) {
        super(age);
    }
    public Zebra(){
        this(4);
    }
}
