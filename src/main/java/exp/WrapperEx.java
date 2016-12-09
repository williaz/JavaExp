package exp;

import java.util.function.Supplier;

/**
 * Created by williaz on 11/16/16.
 */
public class WrapperEx {
    public static boolean isError = false;
    public static <T> T wrap(Supplier<T> execute) {
        if (isError) { return null;}
        try {
            T t = null;
            synchronized (execute) {
                t = execute.get();
            }
            return t;
        } catch(Exception e) {
            isError = true;
            return null;
        }
    }
}
