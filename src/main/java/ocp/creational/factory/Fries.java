package ocp.creational.factory;

/**
 * Created by williaz on 11/24/16.
 */
enum Fries {
    FRECH(300),ONION(400);
    private int calories;
    private Fries(int calories) {
        this.calories = calories;
    }

    public int getCalories() {
        return calories;
    }
}
