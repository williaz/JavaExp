package lambda;

import org.junit.Test;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.Assert.*;

/**
 * Created by williaz on 1/22/17.
 *
 * a <b>downstream</b> collector is a recipe for building a part of that value, which is then used by the main collector.
 * An <b>accumulator</b> is a function to fold the current element into the collector
 */
public class StreamTest {
    /**
     * method references automatically support multiple parameters,
     *   as long as you have the right functional interface
     */
    @Test
    public void test_MethodRef() {
        PriorityQueue<Integer> heap = getHeap(PriorityQueue<Integer>::new);
        heap.add(34);
        heap.add(13);
        heap.add(52);
        assertTrue(52 == heap.poll());
        assertTrue(34 == heap.poll());
    }

    public PriorityQueue<Integer> getHeap(BiFunction<Integer, Comparator<Integer>, PriorityQueue<Integer>> app) {
        return app.apply(Integer.valueOf(15), Comparator.reverseOrder());
    }

    /**
     * Encounter Order - A Stream intuitively presents an order because each element is operated upon, or encountered, in turn.
     * How the encounter order is defined depends on both the source of the data and the operations performed on the Stream.
     */

    /**
     * partition it into two collections of values:
     * partitioningBy() over filter()
     */

    /**
     * The mapping collector allows you to perform a map-like operation over your collector’s container.
     */

    /**
     * A characteristic is a Set of objects that describes the Collector,
     *  allowing the framework to perform certain optimizations.
     */
    //TODO custom Collector

    @Test
    public void test_getLongestName() {
        assertEquals("Stuart Sutcliffe", getLongestName(getNames()));
        assertEquals("Stuart Sutcliffe", getLongestName1(getNames()));
    }

    public Stream<String> getNames() {
        List<String> names = Arrays.asList("John Lennon", "Paul McCartney",
                "George Harrison", "Ringo Starr", "Pete Best", "Stuart Sutcliffe");
        return names.stream();
    }

    public String getLongestName(Stream<String> names) {
        return names.reduce((a, b) -> a.length() > b.length() ? a : b).get(); // left compare with right
    }

    public String getLongestName1(Stream<String> names) {
        return names.collect(Collectors.maxBy(Comparator.comparingInt(s -> s.length()))).get();
    }
























}
