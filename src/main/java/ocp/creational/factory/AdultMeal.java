package ocp.creational.factory;

/**
 * Created by williaz on 11/24/16.
 */
public class AdultMeal extends Meal {
    AdultMeal(Burger burger, Fries fries, Drink drink) {
        super(burger, fries, drink);
    }

    @Override
    public void consume() {
        System.out.println("For this Adult Meal you got "
                + getTotoalCalories(Size.MEDIUM) + " calories");
    }
}
