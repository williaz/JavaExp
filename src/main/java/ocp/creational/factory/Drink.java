package ocp.creational.factory;

/**
 * Created by williaz on 11/24/16.
 */
public enum Drink {
    COKE(200), LOMENDATE(300), DIET_COKE(0);
    private int calories;
    private Drink(int calories) {
        this.calories = calories;
    }

    public int getCalories() {
        return calories;
    }
}
