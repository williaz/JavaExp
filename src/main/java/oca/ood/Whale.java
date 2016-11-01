package oca.ood;

/**
 * Created by williaz on 11/1/16.
 */
public abstract class Whale extends Fish {
    public Whale(String species) {
        super(species);
    }
    @Override
    public String getSpecies() {
        return "Whale";
    }

}
