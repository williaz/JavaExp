package oca.ood;

/**
 * Created by williaz on 11/2/16.
 */
public interface CanFly {
    int MAXIMUM_Height = 100;
    final static boolean INSKY = true;
    public static final String TYPE = "flyable";

    public int getSpeed();

    public default void fly() {
        System.out.println(" I believe I can fly! ");
    }

    public default String getAbility() {
        return "flyer";
    }
    static String printAction() {
        return "Flying";
    }

    public static String call(Bird bird) {
        return bird.getName() + " is flying";
    }


}
