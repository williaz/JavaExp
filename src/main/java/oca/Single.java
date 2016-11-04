package oca;

/**
 * Created by williaz on 11/4/16.
 */
public class Single {
    private String name;

    public Single(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    private static Single s;
    public static synchronized Single getSingle() {
        if (s == null) {
            s = new Single("Samuel");
        }

        return s;
    }

}
