package ocp;

import java.io.IOException;

/**
 * Created by williaz on 12/5/16.
 */
public class AutoCloseableResource implements AutoCloseable {
    @Override
    public void close() throws IOException { // more specific one than Exception
        System.out.println("AutoCloseableResource Closing"); // no side effect, idempotent
        throw new IOException("Cannot close");
    }
}
