package oca.ood;

/**
 * Created by williaz on 11/3/16.
 */
public class CanadaGoose extends Swan {
    public double getWarmTemp() {
        return 40.1;
    }
    @Override
    public String getName() {
        return "Canada goose";
    }

//    void is not covariant return
//    @Override
//    public void displayInfo() {
//        System.out.println(getName());
//    }

    public static void main(String[] args) {
        Bird goose = new CanadaGoose();
        System.out.println(goose.MAXIMUM_Height);
        System.out.println(CanFly.call(goose));
    }
}
