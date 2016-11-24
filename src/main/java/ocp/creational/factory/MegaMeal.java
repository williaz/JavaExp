package ocp.creational.factory;

/**
 * Created by williaz on 11/24/16.
 */
public class MegaMeal extends Meal{
    MegaMeal(Burger burger, Fries fries, Drink drink) {
        super(burger, fries, drink);
    }

    @Override
    public void consume() {
        System.out.println("For this Mega Meal you got "
                + getTotoalCalories(Size.LARGE) + " calories");
    }
}
