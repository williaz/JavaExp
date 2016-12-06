package ocp;

/**
 * Created by williaz on 12/6/16.
 */
public class ResourceOrder implements AutoCloseable {
    int num;

    public ResourceOrder(int num) {
        this.num = num;
    }

    @Override
    public void close() {
        System.out.println("Closed " + num);
    }
}
