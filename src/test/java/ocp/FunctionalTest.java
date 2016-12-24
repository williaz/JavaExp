package ocp;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.DoubleSummaryStatistics;
import java.util.HashMap;
import java.util.IntSummaryStatistics;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.OptionalDouble;
import java.util.OptionalInt;
import java.util.Random;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.concurrent.Future;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.BiPredicate;
import java.util.function.BinaryOperator;
import java.util.function.BooleanSupplier;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.IntBinaryOperator;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.Stream;

import javax.print.attribute.HashAttributeSet;

import static org.junit.Assert.*;
/**
 * Created by williaz on 11/29/16 - 12/2 4d.
 * functional interface without type -> Object, may cause ce for lambda expression.
 * Use generic wildcards for the return type of the final statement to debug whether ce is because of return type
 * Watch out:
 * 1. a stream can only call terminal once!
 * 2. terminal only be used in primitive stream: sum(), ..
 *
 */
public class FunctionalTest {
    /**
     * Lambdas use the same access rules as local inner classes.
     * Lambda expressions can access static variables, instance variables,
     *        effectively final method parameters, and effectively final local variables.
     */

    public String name = "FunctionalTest";
    public static double PI = 3.1415;
    {
        name = "Functional"; //do not need final
    }
    void playLambdaVarible(int r) {
        Character c = new Character('Y');
        //c = 'c'; // should effective final
        Supplier<String> accessName = () -> name;
        Supplier<Double> accessPi = () -> PI;
        Supplier<Integer> accessR = () -> r;
        Supplier<Character> accessC = () -> c;
        System.out.println(accessName.get() + " " + accessPi.get() + " " + accessR.get() + " " + accessC.get());
    }
    @Test
    public void test_VariableAccessedByLambda() {
        playLambdaVarible(5);
    }
    /**
     * Supplier<T> : T get();
     * A Supplier is often used when constructing new objects.
     */
    @Test
    public void test_Supplier() {
        int i = 1;
        Supplier<Integer> num = () -> new Integer(i) ;
        List<Integer> nums = new ArrayList<>();
        Predicate<Integer> addOne = nums::add;
        Consumer<Integer> addOne1 = nums::add;
        addOne.test(new Integer(34));
        addOne1.accept(new Integer(54));
        nums.add(num.get());
        nums.add(num.get());
        System.out.println(nums);

    }

    /**
     * Consumer<T> : void accept(T t);
     * BiConsumer<T, U> : void accept(T t, U u);
     */
    @Test
    public void test_Consumer() {
        Consumer<String> printStr = s -> System.out.println(s);
        printStr.accept("Williaz");
        BiConsumer<String, String> print2Str = (s,t) -> System.out.println(s + " and " + t);
        print2Str.accept("Williaz", "Bill");

        Map<String, Double> prices = new HashMap<>();
        BiConsumer<String, Double> addOne = prices::put; //particular instance method ref, do not care return type
        addOne.accept("AAPL",122.4);
        addOne.accept("FB",134.5);
        Consumer<Map<String, Double>> printPrices = System.out::println; //generic of generic
        printPrices.andThen(m -> System.out.println(m.get("FB"))).accept(prices);
    }

    /**
     * Predicate<T> : boolean test(T t);
     * BiPredicate<T, U> : boolean test(T t, U u);
     * # return boolean, no Boolean
     */
    @Test
    public void test_Predicate() {
        BiPredicate<String, String> startStr = String::startsWith; //general instance method ref
        BiPredicate<String, String> endStr = String::endsWith;
        //first parameter in the lambda is used as the instance on which to call the method
        assertEquals(true, startStr.test("Williaz", "Will"));
        assertNotEquals(true, startStr.test("Will", "Williaz"));

        assertTrue(startStr.and(endStr).test("WilliW", "W"));

        Predicate<Integer> is5 = Predicate.isEqual(new Integer(5));
        assertTrue(is5.test(5));

        BiPredicate<String, String> checkEnds = startStr.and(endStr.negate());
        assertFalse(checkEnds.test("willwill", "will"));
        assertTrue(checkEnds.test("williaz", "will"));
        BiPredicate<String, String> checkEnds1 = startStr.or(endStr);
        assertTrue(checkEnds.test("williaz", "will"));
    }

    /**
     * Function<T, R> : R apply(T t);
     * BiFunction<T, U, R> : R apply(T t, U u);
     * A Function is responsible for turning one parameter
     *   into a value of a potentially different type and returning it.
     */
    @Test
    public void test_Function() {
        Function<String, Integer> getSize = String::length;
        assertTrue(7 == getSize.apply("williaz"));
        assertTrue(5 == getSize.andThen(s -> s + 1).apply("will"));
        assertTrue(7 == getSize.compose(s -> s + "iam").apply("will"));
        Function<String, String> getParam = Function.identity();
        assertEquals("will", getParam.apply("will"));

        BiFunction<String, Integer, String> appendNum = (s, i) -> s + i;
        assertTrue(5 == appendNum.andThen(getSize).apply("will",8));

    }

    /**
     * UnaryOperator and BinaryOperator are a special case of a function.
     *    They require all type parameters to be the same type.
     * UnaryOperator<T> : T apply(T t);
     * BinaryOperator<T> : T apply(T t1, T, t2);
     */
    @Test
    public void test_UnaryOperator() {
        UnaryOperator<String> toUpper = String::toUpperCase;
        assertEquals("WILL", toUpper.apply("will"));
        BinaryOperator<String> concating = String::concat;
        assertEquals("williaz", concating.apply("will", "iaz"));
    }

    @Test
    public void test_BinaryOperator() {
        BinaryOperator<String> maxByLen = BinaryOperator.maxBy((s1, s2) -> s1.length() - s2.length());
        BinaryOperator<String> minByLen = BinaryOperator.minBy((s1, s2) -> s1.length() - s2.length());
        List<String> strs = Arrays.asList("Bb", "Will", "Bill", "William", "Eason");
        System.out.println(strs.stream().reduce("", maxByLen));
        System.out.println(strs.stream().reduce(minByLen));
    }

    /**
     * @see java.util.function.BooleanSupplier
     */
    @Test
    public void test_PrimitiveFunctionalInterface() {
        BooleanSupplier randomCheck = () -> Math.random() > .5;
        System.out.println(randomCheck.getAsBoolean());

    }

    /**
     * Optional vs null?
     * 1. null is not clear to express as a special value or not in certain situation
     * 2. Optional can be used in FP way
     * 3. Optional can be chained
     *
     *  static Optional<T> of(T)
     *  static Optional<T> ofNullable(T)
     *  static Optional<T> empty()
     *    void ifPresent(Consumer c)
     * boolean isPresent()
     * boolean equals(Object)
     *       T get()
     *       T orElse(T)
     *       T orElseGet(Supplier s)
     *       T orElseThrow(Supplier s)
     */
    @Test
    public void test_Optional() {
        Optional<Integer> value = max(45, 234, 124, 544);
        Optional<Integer> value1 = max();
        assertTrue(value.isPresent());
        assertFalse(value1.isPresent());
        assertTrue(544 == value.get());
        //assertTrue(544 == value1.get()); //NoSuchElementException
        assertTrue(42 == value1.orElse(42));
        assertEquals(new Integer(42), value1.orElseGet(() -> new Integer(42)));
        Consumer<Integer> print = System.out::print;
        value.ifPresent(print); // IF!!!

        assertTrue(Optional.of(new Integer(5)).equals(Optional.of(new Integer(5))));


    }

    public Optional<Integer> max(int ... nums) {
        if (nums.length == 0) return Optional.empty();
        int max = Integer.MIN_VALUE;
        for (int i : nums) {
            if (i > max) max = i;
        }
        return Optional.of(max);
    }

    public OptionalInt max1(int ... nums) {
        return IntStream.of(nums).reduce(Math::max);
    }

    public OptionalInt max2(int ... nums) {
        return Arrays.stream(nums).max();
    }


    /**
     * A stream in Java is a sequence of data. <- assembly line
     * A stream pipeline is the operations that run on a stream to produce a result.
     * Finite / Infinite stream
     * With streams, the data isn’t generated up front—it is created when needed.
     * Stream Operations
     * 1. Source: Where the stream comes from.
     * 2. Intermediate operations: do not run until the terminal operation runs.
     * 3. Terminal operation: Actually produces a result.
     *        Since streams can be used only once, the stream is no longer valid after a terminal operation completes.
     */

    /**
     * Source Operation:
     * Stream<T> Stream.empty()
     *           Stream.of(T... t);
     *           Collection.stream();
     *           Arrays.stream(arr)
     *           Stream.generate(Supplier<T> s);
     *           Stream.iterate(T seed, UnaryOperator<T> f)
     *
     */
    @Test
    public void test_StreamSource() {
        Stream<Integer> nums = Stream.empty();
        Stream<Integer> nums1 = Stream.of(12, 434, 23, 33);
        Stream<Integer> nums2 = Arrays.asList(23,22, 11, 43).stream();
        System.out.println(nums.count() + " " + nums1.count() + " " + nums2.count());
        Stream<Double> floats = Stream.generate(Math::random);
        //floats.forEach(System.out::println); // keeping generating
        Stream<Integer> evens = Stream.iterate(0, i -> i + 2);
        //System.out.println(evens.count()); // hangs as infinite stream
        //evens.forEach(System.out::println); // keeping generating
        Stream<Integer> doubler = Stream.iterate(2, i -> i * 2).limit(4);
        doubler.forEach(System.out::println);
    }

    /**
     * Terminal: a stream can only call terminal operation once! Reduction, infinite, terminate?
     * Reductions are a special type of terminal operation where all of the contents of the stream
     *     are combined into a single primitive or Object .
     *
     *        long count() : Reduction
     * Optional<T> min(<? super T> comparator) : Reduction
     * Optional<T> max(<? super T> comparator) : Reduction
     * Optional<T> findAny() : can work with infinite stream
     * Optional<T> findFirst() : can work with infinite stream
     *     boolean anyMatch(Predicate<T> p)
     *     boolean allMatch(Predicate<T> p)
     *     boolean nonMatch(Predicate<T> p)
     *        void forEach(Consumer<T> c): If you want something to happen, you have to make it happen in the loop.
     *           T reduce(T init, BinaryOperator<T> f) : The most common way of doing a reduction is to start with an initial value
     *                                                   and keep merging it with the next value.
     * Optional<T> reduce(BinaryOperator<T> f)
     * Optional<U> U reduce(U init, BiFunction<U, ? super T, U> f1, BinaryOperator<U> f2) : It allows Java to create intermediate reductions
     *                                                                                      and then combine them at the end.
     */
    @Test
    public void test_StreamTerminal() {
        Stream<Integer> nums = Stream.of(34, 54, 12, 66, 99, 31);
       // assertEquals(6L, nums.count()); //IllegalStateException: stream has already been operated upon or closed
        Optional<Integer> min = nums.min((a, b) -> a - b);
        assertTrue(min.isPresent());
        assertEquals(Integer.valueOf(12), min.get());

        Stream<String> names = Stream.of("Will", "Edison", "Bill", "Harrison");
        //Optional<String> findSon = names.filter(s -> s.endsWith("son")).findFirst();
        //assertEquals("Edison", findSon.get());
        //boolean findSon1 = names.anyMatch(s -> s.endsWith("son"));
        //boolean findSon2 = names.allMatch(s -> s.contains("i"));
        //boolean findSon3 = names.noneMatch(s -> s.contains("xx")); //!
        //assertTrue(findSon3);

        String combined = names.reduce("", (a, b) -> a + b);
        System.out.println(combined);

        Stream<Integer> ints = Stream.iterate(1, i -> ++i).limit(10); //if use i -> i ++ generate 1111111
        //ints.forEach(System.out::println);
        //Integer sum = ints.reduce(0, (a, b) -> a + b);
        //System.out.println(sum);
        Optional<Integer> sum1 = ints.reduce((a, b) -> a + b);
        System.out.println(sum1.get());

        Stream<String> stream = Stream.of("w", "o", "l", "f");
        String word = stream.reduce("", String::concat);
        System.out.println(word); // wolf

        //TODO back after concurrency, types
        Stream<Integer> intnums = Stream.iterate(1, i -> ++i).limit(6);
        BiFunction<Integer, Integer, Integer> f1 = (a, b) -> a + b;
        BinaryOperator<Integer> f2 = (a, b) -> a * b;
        System.out.println(intnums.parallel().reduce(Integer.valueOf(0), f1, f2));

    }

    /**
     * Optional<T>	reduce(BinaryOperator<T> accumulator)
     * T reduce(T identity, BinaryOperator<T> accumulator)
     * <U> U reduce(U identity, BiFunction<U,? super T,U> accumulator, BinaryOperator<U> combiner)
     */
    @Test
    public void test_Reduce() {
        Stream<String> names = Stream.of("William", "Will", "Bill", "Edison", "Harry");
        //System.out.println(names.reduce("", String::concat));
        //System.out.println(names.reduce(String::concat).get());
        System.out.println(names.reduce(0, (i, s) -> i + s.length(), (a, b) -> a+b));

    }

    /**
     * The collect() method is a special type of reduction called a mutable reduction.
     * it lets us get data out of streams and into another form.
     * R collect(Supplier<R> s, BiConsumer<R, ? super T> c, BiConsumer<R, R> combiner)
     * R collect(Collector<? super T, A, R> c)
     */
    @Test
    public void test_Collect() {
        Stream<String> words = Stream.of("will", "is", "the", "best", "!");
        //StringBuilder sb = words.collect(StringBuilder::new, StringBuilder::append, StringBuilder::append);
        //System.out.println(sb);
        words.collect(HashMap::new, (Map m, String s) -> m.put(s.length(), s), Map::putAll)
                .forEach((k, v) -> System.out.println(k + " " + v));

        Stream<Integer> ints = Stream.of(23, 14, 45, 66, 19, 67);
        //List<Integer>list = ints.collect(ArrayList::new, ArrayList::add, ArrayList::addAll);
        List<Integer>list1 = ints.collect(Collectors.toList());
        list1.forEach(System.out::println);
    }

    /**
     * Intermediate:
     * Stream<T> filter(Predicate<? super T> p)
     * Stream<T> distinct() : remove duplicate
     * Stream<T> limit(int size)
     * Stream<T> skip(int num) : skip first num
     * Stream<R> map(Function<? super T, ? super R> f) : for transforming data, add Optional wrapper
     * Stream<R> flatMap(Function<? super T, ? extends Stream<? extends R>> f), no add Optional wrapper;
     *                                                 Chaining calls to flatMap() is useful when you want to transform one
     * Optional type to another
     * Stream<T> sorted()
     * Stream<T> sorted(Comparator<? super T> c)
     * Stream<T> peek(Consumer<? super T> c) : no terminate stream, do not use it change data structure.
     *
     * @see Stream
     */
    @Test
    public void test_Intermediate() {
        Stream<Integer> ints = Stream.iterate(1, i -> i + 2);
        ints.skip(4).limit(3).forEach(System.out::println);

        Stream<String> names = Stream.of("Will", "Wilson", "Edison");
        names.map(String::length).forEach(System.out::println);

        List<Integer> list = Arrays.asList();
        List<Integer> list1 = Arrays.asList(23, 55);
        List<Integer> list2 = Arrays.asList(14, 24, 65);
        Stream<List<Integer>> listStream = Stream.of(list, list1, list2);
        //listStream.flatMap(l -> l.stream()).sorted().forEach(System.out::println);
        //listStream.flatMap(l -> l.stream()).sorted((a, b) -> b - a).forEach(System.out::println);
        listStream.flatMap(l -> l.stream()).peek(System.out::print).sorted(Comparator.reverseOrder()).forEach(System.out::println);
        //listStream.flatMap(l -> l.stream()).sorted(Comparator::reverseOrder).forEach(System.out::println); // argument

    }

    @Test
    public void test_MapVsFlatMap() {
        Stream<List<Integer>> sList = Stream.of(Arrays.asList(13, 21, 34), Arrays.asList(11, 22, 33, 44));
        //sList.map(a -> a.size()).forEach(System.out::println);
        sList.flatMap(List::stream).sorted().forEach(System.out::println);
    }

    /**
     * Streams allow you to use chaining and express what you want to accomplish rather than how to do so.
     */
    @Test
    public void test_Pipeline() {
        List<String> list = Arrays.asList("Toby", "Anna", "Leroy", "Alex");
        list.stream().filter(s -> s.length() == 4).sorted().limit(2).forEach(System.out::println); //shorter, briefer, and clearer code
        Stream<Integer> ints = Stream.iterate(1, i -> i + 2);
        ints.forEach(System.out::println);
    }

    /**
     * Primitive stream: IntStream, LongStream, DoubleStream
     * mapToObj(), mapToInt(), mapToLong(), mapToDouble()
     * OptionalInt, OptionalDouble, OptionalLong
     * BooleanSupplier - only one for boolean
     *
     */
    @Test
    public void test_PrimitiveStream() {
        DoubleStream doubles = DoubleStream.generate(Math::random);
        IntStream rInts = new Random().ints();
        IntStream ints = IntStream.of(23, 121, 58, 99);
        LongStream longs = LongStream.iterate(1200L, i -> i * 2);
        LongStream range = LongStream.range(342, 1234);
        LongStream range1 = LongStream.rangeClosed(342, 1234);

        doubles.mapToObj(a -> a / 2).map(i -> i + " ").peek(System.out::print).limit(5).forEach(System.out::println);
        OptionalInt oi = ints.max();
        assertEquals(121, oi.getAsInt());
        Long sumLong = range.sum();
        System.out.println(sumLong);
        System.out.println(range1.average().orElseGet(() -> Double.NaN));

        IntStream range2 = IntStream.rangeClosed(1, 100).filter( i -> (i % 2 != 0) && (i % 3 != 0)).peek(System.out::println);
        System.out.println(percent(range2));

        rInts.limit(10).mapToDouble(i -> i * 0.5).forEach(System.out::println);

    }

    public static double percent(IntStream intStream) {
        IntSummaryStatistics summary = intStream.summaryStatistics();
        if (summary.getCount() == 0) return -1;
        return summary.getAverage() / (summary.getMax() - summary.getMin());
    }

    /**
     * Remember that streams are lazily evaluated.
     */
    @Test
    public void test_AdvPipeline() {
        List<String> names = new ArrayList<>();
        names.add("Will");
        names.add("Bill");
        Stream<String> nameStream = names.stream(); //connected but no collect data yet
        names.add("Metuchen");
        assertEquals(3, nameStream.count()); // start looking for data

        IntStream ints = IntStream.iterate(-1, i -> i * 2);
        IntStream absInts = ints.map(Math::abs).limit(10);

        Stream<Integer> integerStream = Stream.iterate(10, i -> i + 10).limit(10);
        OptionalDouble value = integerStream.mapToDouble(Math::sin).max();
    }

    /**
     * Collectors: Int, Double, Long
     *               double averageInt(ToIntFunction f), averageDouble(ToDoubleFunction f), averageLong(ToLongFunction f)
     *                 long counting()
     *               String joining(), joining(CharSequence dilimiter)
     * XxxSummaryStatistics summarizingXxx(ToXxxFunction f)
     *                Xxxxx summingXxx(ToXxxFunction f)
     *                 List toList()
     *                  Set toSet()
     *           Collection toCollection(Supplier s)
     *
     * @see Collectors
     */
    @Test
    public void test_BasicCollector() {
        Stream<String> name = Stream.of("Will", "Edward", "Learner", "Wang");
        String fullname = name.collect(Collectors.joining(" "));
        System.out.println(fullname);

        Stream<Double> scores = Stream.of(86.2, 90.5, 46.0, 56.4, 89.11, 77.2);
        List<Double> report = scores.filter(i -> i > 60).sorted().collect(Collectors.toCollection(ArrayList::new));
        System.out.println(report);

        Stream<Double> scores1 = Stream.of(86.2, 90.5, 46.0, 56.4, 89.11, 77.2);
        DoubleSummaryStatistics report1 = scores1.collect(Collectors.summarizingDouble(i -> i));
        System.out.println(report1.getAverage());
    }

    @Test
    public void test_BasicCollector1() {
        assertEquals( Double.valueOf(74.235), Stream.of(86.2, 90.5, 46.0, 56.4, 89.11, 77.2)
                .collect(Collectors.averagingDouble(Double::doubleValue)) );

        assertEquals(Long.valueOf(6), Stream.of(86.2, 90.5, 46.0, 56.4, 89.11, 77.2)
                .collect(Collectors.counting()) );

        System.out.println(Stream.of("Will", "Edward", "Learner", "Wang").collect(Collectors.joining(", ")) );
        System.out.println(Stream.of("Will", "Edward", "Learner", "Wang").collect(Collectors.joining()) );

        IntSummaryStatistics s = Stream.of("Will", "Edward", "Learner", "Wang")
                .collect(Collectors.summarizingInt(String::length));
        assertEquals(7, s.getMax());
        assertEquals(4, s.getMin());
        assertEquals(4, s.getCount());
        assertEquals(21, s.getSum());
        assertEquals(5.25, s.getAverage(), 0.001);
        System.out.println(s);

        assertEquals(Integer.valueOf(21), Stream.of("Will", "Edward", "Learner", "Wang")
                .collect(Collectors.summingInt(String::length)) );

        List<String> name = Stream.of("Will", "Edward", "Learner", "Wang").collect(Collectors.toList());
        System.out.println(name);

        Set<String> name1 = Stream.of("Will", "Edward", "Learner", "Wang").collect(Collectors.toSet());
        System.out.println(name1);

        TreeSet<String> name2 = Stream.of("Will", "Edward", "Learner", "Wang").collect(Collectors.toCollection(TreeSet::new));
        System.out.println(name2);

        assertEquals("Learner", Stream.of("Will", "Edward", "Learner", "Wang")
                .collect(Collectors.maxBy((a, b) -> a.length() - b.length())).get() );

        assertEquals("Will", Stream.of("Will", "Edward", "Learner", "Wang")
                .collect(Collectors.minBy((a, b) -> a.length() - b.length())).get() );

        assertEquals("Edward", Stream.of("Will", "Edward", "Learner", "Wang")
                .collect(Collectors.minBy(Comparator.naturalOrder())).get());

    }

    /**
     *                  Map toMap(Function k, Function v), toMap(Function k, Function v, BinaryOperator b), toMap(Function k, Function v, BinaryOperator b, Supplier s)
     *      Map<K, List<T>> groupBy(Function k), groupBy(Function k, Collector c), groupBy(Function k, Supplier s, Collector c)
     * Map<Boolean, List<T> partitionBy(Predicate p), partitionBy(Predicate p, Collector c)
     *            Collector mapping(Function f, Collector c)
     *          Optional<T> maxBy(Comparator c), minBy(Comparator c)
     *
     * With partitioning, there are only two possible groups—true and false.
     * Downstream collector to specify the type
     * mapping() collector that lets us go down a level and add another collector.
     */
    @Test
    public void test_MapCollector() {
        Stream<String> name = Stream.of("Will", "Edward", "Learner", "Wang", "Will", "Will", "Will", "Wang");
        //Map<String, Integer> counter = name.collect(Collectors.toMap(Function.identity(), s -> 1,(s1, s2) -> s1 + s2));
        Map<Integer, List<String>> length = name.collect(Collectors.groupingBy(s -> s.length()));
        System.out.println(length);
        //Map<String, Long> counter = name.collect(Collectors.groupingBy(s -> s, Collectors.counting()));
        //System.out.println(counter);

        Stream<String> animals = Stream.of("lions", "tigers", "bears");
        Map<Integer, Set<String>> size = animals.collect(Collectors.groupingBy(String::length, HashMap::new, Collectors.toSet()));
        System.out.println(size);

        Stream<String> zoo = Stream.of("lions", "tigers", "bears", "dog", "cat", "elephant");
        Map<Boolean, List<String>> exhibit = zoo.collect(Collectors.partitioningBy(s -> s.length() < 4));
        System.out.println(exhibit);

        Stream<String> zoo1 = Stream.of("lions", "tigers", "bears", "dog", "cat", "elephant");
        Map<Integer, Optional<Character>> map = zoo1.collect(Collectors.groupingBy(String::length,
                Collectors.mapping(s -> s.charAt(0), Collectors.minBy((c1, c2) -> c1 - c2))));
        System.out.println("map: "+ map);

        Stream<String> zoo2 = Stream.of("lions", "tigers", "bears", "dog", "cat", "elephant");
        Map<Integer, Optional<Character>> map1 = zoo2.collect(Collectors.groupingBy(String::length,
                Collectors.mapping((String s) -> s.charAt(0), Collectors.minBy(Comparator.naturalOrder()))));
        //:: operator tells Java to pass the parameters automatically
        System.out.println("map1: "+ map1);
    }

    @Test
    public void test_MapCollector1() {
        Map<Boolean, List<String> > map = Stream.of("Will", "Edward", "Learner", "Wang", "Will", "Will", "Will", "Wang")
                .collect(Collectors.partitioningBy(a -> a.length() > 4));
        System.out.println(map);

        Map<Boolean, Set<String> > map1 = Stream.of("Will", "Edward", "Learner", "Wang", "Will", "Will", "Will", "Wang")
                .collect(Collectors.partitioningBy(a -> a.length() > 4, Collectors.toSet()));
        System.out.println(map1);

        Map<Boolean, Long > map2 = Stream.of("Will", "Edward", "Learner", "Wang", "Will", "Will", "Will", "Wang")
                .collect(Collectors.partitioningBy(a -> a.length() > 4, Collectors.counting()));
        System.out.println(map2);

        Map<String, Integer> name3 = Stream.of("Will", "Edward", "Learner", "Wang")
                .collect(Collectors.toMap(Function.identity(), String::length));
        System.out.println(name3);

        Map<Integer, String> name4 = Stream.of("Will", "Edward", "Learner", "Wang")
                .collect(Collectors.toMap(String::length, Function.identity(), (s1, s2) -> s1 + " " + s2));
        //merge function to solve duplicate values
        System.out.println(name4);

        TreeMap<Integer, String> name5 = Stream.of("Will", "Edward", "Learner", "Wang")
                .collect(Collectors.toMap(String::length, Function.identity(), (s1, s2) -> s1 + "," + s2, TreeMap::new));
        //merge function to solve duplicate values
        System.out.println(name5);

        Map<Integer, List<String>> name6 = Stream.of("Will", "Edward", "Learner", "Wang")
                .collect(Collectors.groupingBy(String::length)); //list
        System.out.println("6: " + name6);

        Map<Integer, Set<String>> name9 = Stream.of("Will", "Edward", "Learner", "Wang")
                .collect(Collectors.groupingBy(String::length, Collectors.toSet())); //set
        System.out.println("9: " + name9);

        TreeMap<Integer, Set<String>> name10 = Stream.of("Will", "Edward", "Learner", "Wang")
                .collect(Collectors.groupingBy(String::length, TreeMap::new, Collectors.toSet())); //TreeMap
        System.out.println("10: " + name10);

        Map<Boolean, List<String>> name7 = Stream.of("Will", "Edward", "Learner", "Wang")
                .collect(Collectors.partitioningBy( s -> s.length() >4)); //list
        System.out.println("7: "+ name7);

        Map<Boolean, Set<String>> name8 = Stream.of("Will", "Edward", "Learner", "Wang")
                .collect(Collectors.partitioningBy( s -> s.length() >4, Collectors.toSet())); //set
        System.out.println("8: " + name8);

        Map<Boolean, Long> name11 = Stream.of("Will", "Edward", "Learner", "Wang")
                .collect(Collectors.partitioningBy( s -> s.length() >4, Collectors.counting()));
        System.out.println("11: " + name11);

        Map<Boolean, Set<Character> > name12 = Stream.of("Will", "Edward", "Learner", "Wang")
                .collect(Collectors.partitioningBy( s -> s.length() >4,
                        Collectors.mapping(s -> s.charAt(0), Collectors.toSet())));
        System.out.println("12: " + name12);

        Set<Character> init = Stream.of("Will", "Edward", "Learner", "Wang")
                .collect(Collectors.mapping(s -> s.charAt(0), Collectors.toSet()));
        System.out.println(init);

        Optional<Character> init1 = Stream.of("Will", "Edward", "Learner", "Wang")
                .collect(Collectors.mapping(s -> s.charAt(0), Collectors.minBy((c1, c2) -> c1 - c2)));
        System.out.println(init1);

        Optional<Character> init2 = Stream.of("Will", "Edward", "Learner", "Wang")
                .collect(Collectors.mapping((String s) -> s.charAt(0), Collectors.minBy(Comparator.naturalOrder())));
        System.out.println(init2);

    }




}
