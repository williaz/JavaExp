package oca.access.animal;

import oca.access.other.Gigas;
import oca.access.other.Tadpole;

/**
 * Created by williaz on 11/16/16.
 */
public class Frog {
    protected void ribbit(){ System.out.println("Frog's protected ribbit()");};
    void jump(){System.out.println("Frog's package jump()");};
    private void run() {System.out.println("Frog's private run()");};
    public void shout() {System.out.println("Frog's public shout()");};

    public static void count() {System.out.println("in Frog static");};


    public static void main(String[] args) {
        Frog f = new Frog();
        f.shout();
        f.ribbit();
        f.jump();
        f.run();
        System.out.println("Frog -> Gigas ------");
        Frog g = new Gigas();
        g.shout();
        g.ribbit();
        g.jump(); //hidding
        g.run();  //frog's
        System.out.println("Gigas -> Gigas ------");
        Gigas g1 = new Gigas();
        g1.shout();
       // g1.ribbit(); //in animal package & can ref using own reference
       // g1.jump(); //cause in animal package
       // g1.run();  // private, only in own class
        System.out.println("Frog -> Gigas ------");
        Frog t = new Tadpole();
        t.jump();
        t.run();
        t.ribbit();
        t.shout();

    }
}
