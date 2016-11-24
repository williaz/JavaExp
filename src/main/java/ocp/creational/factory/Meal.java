package ocp.creational.factory;

/**
 * Created by williaz on 11/24/16.
 */
public abstract class Meal {
    protected enum Size { SMALL, MEDIUM, LARGE }

    private Burger burger;
    private Fries fries;
    private Drink drink;

    Meal(Burger burger, Fries fries, Drink drink) { // can factory in the same package can instantiate it.
        this.burger = burger;
        this.fries = fries;
        this.drink = drink;
    }

    public Burger getBurger() {
        return burger;
    }

    public Fries getFries() {
        return fries;
    }

    public Drink getDrink() {
        return drink;
    }

    public int getTotoalCalories(Size size) {
        return getBurger().getCalories() + getFries().getCalories()*size.ordinal() + getDrink().getCalories()*size.ordinal();
    }
    public abstract void consume();
}
