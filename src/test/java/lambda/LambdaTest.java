package lambda;

import org.junit.Test;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.Assert.*;

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

    /**
     * Streams allow us to write collections-processing code at a higher level of abstraction.
     * external iteration: using iterator explicitly.
     * internal iteration: for-each; stream()
     * TODO use stream replace if-else
     *
     * 1. Methods such as filter that build up the Stream recipe but don’t force a new value to be generated
     * at the end are referred to as lazy.
     * 2. Methods such as count that generate a final value out of the Stream sequence are called eager.
     * # whether an operation is eager or lazy: look at what it returns.
     * If it gives you back a Stream, it’s lazy; if it gives you back another value or void, then it’s eager.
     * stream pipe - builder
     */

    @Test
    public void test_FirstNoDuplicateCharInString() {

        String name = "tweetrwgg";
        assertEquals('r', getFirstNoDupChar(name));
    }

    public char getFirstNoDupChar(String str) {
        return str.chars()
                .mapToObj(i -> (char) i)
                .collect(Collectors.groupingBy(Function.identity(), LinkedHashMap::new, Collectors.counting()))
                .entrySet()
                .stream()
                .filter(e -> e.getValue() == 1)
                .findFirst()
                .get() //TODO must contain the char, revise!
                .getKey();
    }


}
