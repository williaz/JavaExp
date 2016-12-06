package ocp;

import java.io.Closeable;
import java.io.IOException;

/**
 * Created by williaz on 12/6/16.
 */
public class CloseableResource implements Closeable {
    @Override
    public void close() throws IOException {
        System.out.println("closing IO");
    }
}
