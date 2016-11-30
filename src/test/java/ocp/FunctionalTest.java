package ocp;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.BiPredicate;
import java.util.function.BinaryOperator;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.Assert.*;
/**
 * Created by williaz on 11/29/16.
 * functional interface without type -> Object, may cause ce for lambda expression.
 */
public class FunctionalTest {
    /**
     * Lambdas use the same access rules as inner classes.
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
        printPrices.accept(prices);
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

        BiFunction<String, Integer, String> appendNum = (s, i) -> s+i;
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
     * boolean isPresent
     *       T get()
     *       T orElse()
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


    }

    public Optional<Integer> max(int ... nums) {
        if (nums.length == 0) return Optional.empty();
        int max = Integer.MIN_VALUE;
        for (int i : nums) {
            if (i > max) max = i;
        }
        return Optional.of(max);
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
     *           Stream.of(T ... t);
     *           Collection.stream();
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
     * The collect() method is a special type of reduction called a mutable reduction.
     * it lets us get data out of streams and into another form.
     * R collect(Supplier<R> s, BiConsumer<R, ? super T> c, BiConsumer<R, R> combiner)
     * R collect(Collector<? super T, A, R> c)
     */
    @Test
    public void test_Collect() {
        Stream<String> words = Stream.of("will", "is", "the", "best", "!");
        StringBuilder sb = words.collect(StringBuilder::new, StringBuilder::append, StringBuilder::append);
        System.out.println(sb);

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
     * Stream<R> map(Function<? super T, ? super R> f) : for transforming data
     * Stream<R> flatMap(Function<? super T, ? extends Stream<? extends R>> f)
     * Stream<T> sorted()
     * Stream<T> sorted(Comparator<? super T> c)
     * Stream<T> peek(Consumer<? super T> c) : no terminate stream, do not use it change data structure.
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




}
