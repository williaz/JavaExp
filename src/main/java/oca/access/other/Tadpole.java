package oca.access.other;

import oca.access.animal.Frog;

/**
 * Created by williaz on 11/16/16.
 */
public class Tadpole extends Frog {
    /*
    protected void ribbit(){};
    public void shout() {};
     */
    public static void main(String[] args) {
        Tadpole t = new Tadpole();
        t.ribbit();
        t.shout();
        //t.jump(); //default, no exist
        Frog f = new Tadpole();
        f.shout();
        //f.ribbit(); // protected, parent referent type only have the memory to store parent object's protected method,
                    // no memory to store child object's protested method
       // f.jump(); //default, no exist

    }
}
