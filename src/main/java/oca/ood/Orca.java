package oca.ood;

/**
 * Created by williaz on 11/1/16.
 */
public class Orca extends Whale {
    public Orca(String species, String name) {
        super(species);
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }
}
