package lambda;

import org.junit.Test;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Created by williaz on 12/18/16.
 * Object-oriented programming is mostly about abstracting over data,
 * while functional programming is mostly about abstracting over behavior.
 */
public class LambdaTest {
    /**
     * lambda expressions—a compact way of passing around behavior.
     * code as data—using an object represents an action.
     *   -> anonymous inner class
     * lambda expressions capture values, not variables.
     *   -> effective final
     * target type inference(java 7 diamond)
     *
     */

    /**
     * Q2
     *
     * @see ThreadLocal
     * @see java.time.format.DateTimeFormatter
     */
    @Test
    public void test_ThreadSafeDateFormatter() {
        ThreadLocal<DateTimeFormatter> formatter = ThreadLocal.withInitial(() -> DateTimeFormatter.ofPattern("dd-MMM-yyyy"));
        System.out.println(formatter.get().format(LocalDateTime.now()));
    }


}
