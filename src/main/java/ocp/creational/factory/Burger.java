package ocp.creational.factory;

/**
 * Created by williaz on 11/24/16.
 * you cannot extends Enum
 */
enum Burger {
    CHEESE_BURGER(300), GRILLED_TURKEY(150), SPICY_CHICKEN(200);
    private int calories;

    private Burger(int calories) {
        this.calories = calories;
    }

    public int getCalories() {
        return calories;
    }
}
