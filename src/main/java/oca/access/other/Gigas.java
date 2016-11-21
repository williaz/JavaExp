package oca.access.other;

import oca.access.animal.Frog;

/**
 * Created by williaz on 11/16/16.
 */
public class Gigas extends Frog {
    //public void count() {System.out.println("in Gigas method");}; //cannot override, must both static or not
    public void count(String s) {System.out.println(s+" in Gigas overloading method");}; // but can overloading
    @Override
    protected void ribbit() {
        //super.ribbit();  // can call parent protected method
        System.out.println("Gigas's public ribbit()");
    }

    @Override
    public void shout() {
        //super.shout();
        System.out.println("Gigas's public shout()");
    }

//    protected void ribbit()
//    void jump()
//    private void run()
//    public void shout()
    public static void main(String[] args) {
        Frog f = new Frog();
        f.shout();
        //f.ribbit();
        //f.jump();
        //f.run();
        System.out.println("Frog -> Gigas ------");
        Frog g = new Gigas();
        g.shout();
        //g.ribbit(); // no same package, parent ref cannot call itself protected method
        //g.jump(); // no exist
        //g.run();  // no exist
        System.out.println("Gigas -> Gigas ------");
        Gigas g1 = new Gigas();
        g1.shout();
        g1.ribbit(); // same package
       // g1.jump(); //no inherited
       //g1.run();  // private, no exist, only in own class
//

        g1.count();
        g1.count("You");

    }

}
