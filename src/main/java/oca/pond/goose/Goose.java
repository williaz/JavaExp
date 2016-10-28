package oca.pond.goose;

import oca.pond.shore.Bird;

/**
 * Created by williaz on 10/28/16.
 * # private fields and methods cannot be call outside the same class
 * # protected memebers can't be call outside its subclass or its package
 */

public class Goose extends Bird {
    public void helpGooseSwim() {
        Goose other = new Goose();
        other.floatInWater();
        System.out.println(other.text);
    }

 /**
 * !!!! We are not allowed to refer to members of the Bird class
 * since we are not in the same package and Bird is not a subclass of Bird.
 */
    public void helpOtherGooseSwim() {
//        Bird other = new Goose();
//        other.floatInWater(); // DOES NOT COMPILE
//        System.out.println(other.text); // DOES NOT COMPILE
    }
}