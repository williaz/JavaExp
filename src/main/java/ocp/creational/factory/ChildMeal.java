package ocp.creational.factory;

/**
 * Created by williaz on 11/24/16.
 */
public class ChildMeal extends Meal {
    ChildMeal(Burger burger, Fries fries, Drink drink) {
        super(burger, fries, drink);
    }

    @Override
    public void consume() {
        System.out.println("For this Child Meal you got "
                + getTotoalCalories(Size.SMALL) + " calories");
    }
}
