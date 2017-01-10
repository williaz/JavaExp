package lambda;

import org.junit.Test;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
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

    class Node {
        private int val;
        private Node next;

        public Node(int val) {
            this.val = val;
        }

        public int getVal() {
            return val;
        }

        public void setVal(int val) {
            this.val = val;
        }

        public Node getNext() {
            return next;
        }

        public void setNext(Node next) {
            this.next = next;
        }
    }

    @Test
    public void test_Max_With_Comparator() {
        List<Node> nodes = Arrays.asList(new Node(45), new Node(88), new Node(14), new Node(99),
                new Node(11), new Node(34), new Node(65));
        assertEquals(99, nodes.stream().max(Comparator.comparing(n -> n.getVal())).get().getVal());
    }

    /**
     * Use the reduce operation when you’ve got a collection of values and you want to generate a single result.
     * Optional<T> reduce(BinaryOperator<T> accumulator);
     * T reduce(T identity, BinaryOperator<T> accumulator);
     * <U> U reduce(U identity, BiFunction<U, ? super T, U> accumulator, BinaryOperator<U> combiner);
     */
    @Test
    public void test_Reduce() {
        List<Integer> ints = Arrays.asList(34, 13, 99, 56, 10);

        //mimic sum()
        int x = ints.stream().reduce((a, b) -> a+b).get();
        int y = ints.stream().mapToInt(i -> i).sum();
        assertEquals(x, y);

        //mimic max()
        assertEquals(ints.stream().max(Comparator.naturalOrder()).get(),
                ints.stream().reduce(0, (a, b) -> Math.max(a, b)));

        //mimic count()
        int count = Arrays.asList("Will", "Bill", "Edison").stream()
                .reduce(0, (i, s) -> i+1, (a, b) -> a+b).intValue();
        assertEquals(3, count);

    }

    /**
     * getter return Stream -> better encapsulation <- immutable
     */
    @Test
    public void test_GetterReturnStream() {
        ModernObject object = new ModernObject();
        object.addName("Will");
        object.addName("Eason");
        object.addName("Wilson");
        object.addName("Eidson");
        object.getName().filter(i -> i.endsWith("son")).map(s -> s + "y").forEach(s -> System.out.print(s + " "));
        System.out.println();
        object.getName().forEach(s -> System.out.print(s + " "));
    }

}
