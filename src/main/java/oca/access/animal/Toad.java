package oca.access.animal;

/**
 * Created by williaz on 11/16/16.
 * virtual method means @Override
 */
public class Toad extends Frog {
    @Override
    protected void ribbit() {
        //super.ribbit();
        System.out.println("Toad's protected ribbit()");
    }

    @Override
    void jump() {
        //super.jump();
        System.out.println("Toad's package jump()");
    }

    @Override
    public void shout() {
        super.shout();
    }

    public static void main(String[] args) {
        Frog f = new Frog();
        f.jump();
        Frog t = new Toad();
        t.ribbit(); // same package
        t.jump();
    }
}
