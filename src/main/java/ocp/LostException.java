package ocp;

/**
 * Created by williaz on 12/5/16.
 */
public class LostException extends Exception {
    public LostException() {
    }

    public LostException(String message) { //to pass a custom error message
        super(message);
    }

    public LostException(Throwable cause) { // to wrap another exception in yours
        super(cause);
    }

}
