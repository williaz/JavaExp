package ocp.creational.factory;

/**
 * Created by williaz on 11/24/16.
 * Bad design
 * enum VS factory
 * @see java.time.LocalDate
 * @see java.util.concurrent.Executors
 */
public class MealFactory {
    public static Meal getMeal(int num) {
        switch (num) {
            case 1: return new ChildMeal(Burger.CHEESE_BURGER, Fries.FRECH, Drink.LOMENDATE);
            case 2: return new ChildMeal(Burger.GRILLED_TURKEY, Fries.FRECH, Drink.LOMENDATE);
            case 3: return new ChildMeal(Burger.SPICY_CHICKEN, Fries.FRECH, Drink.LOMENDATE);

            case 11: return new AdultMeal(Burger.CHEESE_BURGER, Fries.FRECH, Drink.DIET_COKE);
            case 12: return new AdultMeal(Burger.GRILLED_TURKEY, Fries.FRECH, Drink.DIET_COKE);
            case 13: return new AdultMeal(Burger.SPICY_CHICKEN, Fries.FRECH, Drink.DIET_COKE);

            case 21: return new MegaMeal(Burger.CHEESE_BURGER, Fries.ONION, Drink.COKE);
            case 22: return new MegaMeal(Burger.GRILLED_TURKEY, Fries.ONION, Drink.COKE);
            case 23: return new MegaMeal(Burger.SPICY_CHICKEN, Fries.ONION, Drink.COKE);
        }
        throw new UnsupportedOperationException("Unsupport Meal:" + num);
    }
}
