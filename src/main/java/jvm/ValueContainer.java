package jvm;

/**
 * Created by williaz on 10/9/16.
 */
public class ValueContainer {
    private final String value;
    public ValueContainer(final String value) {
        this.value = value;
    }
    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        System.out.printf("Finalizing for [%s]%n", toString());
    }


    @Override
    public String toString() {
        return "ValueContainer{" +
                "value='" + value + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ValueContainer)) return false;

        ValueContainer that = (ValueContainer) o;

        return value.equals(that.value);

    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
