package lambda;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;

/**
 * Created by williaz on 1/25/17.
 *
 * <li>creation of a new result container ({@link #supplier()})</li>
 * <li>incorporating a new data element into a result container ({@link #accumulator()})</li>
 * <li>combining two result containers into one ({@link #combiner()})</li>
 * <li>performing an optional final transform on the container ({@link #finisher()})</li>
 *
 * @see Collector
 */
public class GroupBy<T, K> implements Collector<T,  Map<K, List<T>>, Map<K, List<T>>> {
    @Override
    public Supplier<Map<K, List<T>>> supplier() {
        return null;
    }

    @Override
    public BiConsumer<Map<K, List<T>>, T> accumulator() {
        return null;
    }

    @Override
    public BinaryOperator<Map<K, List<T>>> combiner() {
        return null;
    }

    @Override
    public Function<Map<K, List<T>>, Map<K, List<T>>> finisher() {
        return null;
    }

    @Override
    public Set<Characteristics> characteristics() {
        return null;
    }
}
