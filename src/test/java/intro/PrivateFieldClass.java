package intro;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by williaz on 10/7/16.
 */
public class PrivateFieldClass {
    private String name;
    private int number;

    public PrivateFieldClass(String name, int number) {
        this.name = name;
        this.number = number;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public void setOtherName(PrivateFieldClass other,String name){
        other.name=name;

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PrivateFieldClass)) return false;

        PrivateFieldClass that = (PrivateFieldClass) o;

        return name.equals(that.name);

    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }
}