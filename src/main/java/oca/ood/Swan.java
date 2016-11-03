package oca.ood;

/**
 * Created by williaz on 11/2/16.
 */
public class Swan extends Bird {
    @Override
    public int getSpeed() {
        return 30;
    }

    public String getColor() {
        return "White";
    }

    @Override
    public String getName() {
        return "Swan";
    }
}
