package oca.ood;

/**
 * Created by williaz on 11/1/16.
 */
public abstract class Fish {
    protected String name;
    protected String species;

    // you can customize abstract class's constructor
    public Fish(String species) {
        this.species = species;
    }

    public abstract String getName();
    public abstract String getSpecies();
}
