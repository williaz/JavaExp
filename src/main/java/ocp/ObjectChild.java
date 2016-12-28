package ocp;

/**
 * Created by williaz on 12/27/16.
 */
public class ObjectChild {
    private String name;
    private int id;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ObjectChild)) return false;

        ObjectChild that = (ObjectChild) o;

        return id == that.id;

    }

    @Override
    public int hashCode() {
        return id;  // directly use
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
