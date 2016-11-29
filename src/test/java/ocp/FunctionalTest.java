package ocp;

import org.junit.Test;

import java.util.ArrayList;
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


}
