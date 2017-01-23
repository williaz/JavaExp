package lambda;

import org.junit.Test;

import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;

import static org.junit.Assert.*;

/**
 * Created by williaz on 1/22/17.
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
}
