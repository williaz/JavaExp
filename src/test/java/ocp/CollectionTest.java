package ocp;

import org.junit.Test;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.NavigableSet;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

import ocp.compare.Card;
import ocp.compare.RankFirstComparator;
import ocp.compare.SuitFirstComparator;

import static org.junit.Assert.*;

/**
 * Created by williaz on 11/26/16 - 11/28 3d.
 * watch out:
 * 1. collection without Generic, for looping use Object, else ce!
 * 2. Set<Number> set = new HashSet<Integer>(); -> ce!!!
 * 3. lambda no redefine.
 */
public class CollectionTest {
    @Test
    public void test_Autoboxing() {
        Float f = new Float(1.0);
        Byte b = new Byte((byte)1);
        byte bp = 1;
    }

    /**
     * Generic: parameterized types
     * Compiler actually replace generic with Object, for compatible - type erase,
     *          it will add relevant casts afterward
     * Generic interface: for implementing it, 1. specify the type in the class;
     *         2. use generic type also; 3. use noting, use Object
     * Limitations due to Non-reifiable type (Reifiable - info fully available at runtime):
     *    1. Call the constructor: X new T() (as == new Object())
     *    2. Create an array of that static type?
     *    3. Call instanceof
     *    4. Use primitive type
     *    5. Create a static generic variable
     * Unless a method is obtaining the generic formal type parameter from the class/interface,
     *    it is specified immediately before the return type of the method.
     * # Wildcards: (1. unbound: ?; 2. upper bound: ? extends (any class that extends); )-> make logical immutable!!
     *               3. lower bound: ? super (a superclass of)
     */
    @Test
    public void test_Wildcard() {
        Object[] objects = new Integer[4];
        //List<Object> list = new ArrayList<Integer>(); //Incompatible, with generic must exactly match

        ArrayList<Integer> arrInt = new ArrayList<>();
        arrInt.add(23);
        arrInt.add(43);
        arrInt.add(21);

        //Unbound Wildcard
        List<?> list = arrInt;
        //list.add(23);
        list = new ArrayList<String>(); // no type protection

        //upper bound wildcard
        List<? extends Number> numbers = arrInt;
        //numbers = new ArrayList<String>(); //Incompatible
        //numbers.add(new Double(12.2)); // cannot apply because of its object type
        //numbers.add(new Integer(23)); // cannot as its reference type
        numbers.remove(0); // but can remove
        assertEquals(43, numbers.get(0));

        //lower bound wildcard
        List<? super IOException> nums = new ArrayList<IOException>();
        //nums.add(new Exception());  // cannot for object type
        nums.add(new IOException());
        nums.add(new FileNotFoundException()); // only need to check object type actually

    }

    /**
     * Collection Framework:
     * List: an ordered collection of elements that allow duplicate entries;
     * Set: a collection does not allow duplicates
     * Queue: a collection that orders its elements in a specific order for processing, like FIFO
     * Map: a collection stores key - value pairs, with no duplicate key allowed.
     *
     * ArrayList - look up any element in constant time.
     * LinkedList - access, add, and remove from the beginning and end of the list in constant time.
     * HashSet - adding elements and checking if an element is in the set both have constant time.
     * TreeSet - always in sorted order
     * ArrayDeque - more efficient than a LinkedList.
     * HashMap - adding elements and retrieving the element by key both have constant time.
     * TreeMap - keys are always in sorted order
     *
     * Common Choice:
     * List: ArrayList
     * Set: HashSet
     * Queue: LinkedList(wth index)
     * Stack: ArrayDeque
     *
     * # NO null:
     *   The data structures that involve sorting do not allow nulls. -> TreeSet, TreeMap(null value OK)
     *   ArrayDeque: poll() uses null
     *   Hashtable: null key / value
     *
     */

    /**
     * Common collection method(6+1), Set:
     *
     * boolean add(E)
     * boolean remove(Object)
     * boolean isEmpty()
     *     int size()
     *    void clear()
     * boolean contains(Object)
     *
     * boolean removeIf(Predicate)
     *
     * Iterator<E> iterator()
     */
    @Test
    public void test_CollectionMethod() {
        Collection<String> collection = new HashSet<>();

        assertTrue(collection.isEmpty());
        assertEquals(0, collection.size());

        assertTrue(collection.add("Will"));
        assertTrue(collection.add("Bill"));
        assertFalse(collection.add("Will"));
        assertTrue(collection.add("Edison"));

        Iterator<String> iterator = collection.iterator();
        while(iterator.hasNext()) {
            System.out.println(iterator.next());
        }


        assertTrue(collection.contains("Bill"));
        assertTrue(collection.remove("Bill"));
        assertFalse(collection.contains("Bill"));
        assertFalse(collection.remove("Bill"));

        assertEquals(2, collection.size());
        collection.clear();
        assertEquals(0, collection.size());
        assertTrue(collection.isEmpty());

    }

    @Test
    public void test_LoopCollection() {
        Set<Integer> set = new HashSet<>();
        set.add(12);
        set.add(null);
        set.forEach(System.out::println);
        Iterator i = set.iterator();
        while (i.hasNext()) System.out.println(i.next());

        Iterator<Integer> it = set.iterator(); // OK for null
        while (it.hasNext()) System.out.println(it.next());

    }

    /**
     * List: ArrayList, LinkedList, (Vector, Stack)
     * List common methods(6+5):
     *
     *    void add(int, E)
     *       E get(int)
     *     int indexOf(E)
     *     int lastIndexOf(E)
     * boolean remove(int)
     *       E set(int, E): return previous element !!
     *
     *    void repalceAll(UnaryOperator<E>): default
     */
    @Test
    public void test_ListMethod() {
        List<String> states = new ArrayList<>();
        states.add("NY");
        states.add("NJ");
        states.add("MI");
        states.add("MA");
        states.add(0, "OH");
        assertEquals("MA", states.set(4, "NY"));
        assertEquals("NJ", states.get(2));
        assertEquals(1, states.indexOf("NY"));
        assertEquals(4, states.lastIndexOf("NY"));
        assertEquals("MI", states.remove(3));
    }

    /**
     * NavigableSet: TreeSet
     * E lower(E) <
     * E floor(E) <=, parameter's floor
     * E higher(E) >
     * E ceiling(E) >=, parameter's ceiling
     *
     */
    @Test
    public void test_NavigableSet() {
        NavigableSet<Integer> ngSet = new TreeSet<>();
        for(int i = 0; i < 20 ; i++) ngSet.add(i);

        assertEquals(new Integer(10), ngSet.floor(10));
        assertEquals(new Integer(10), ngSet.lower(11));
        assertEquals(new Integer(10), ngSet.higher(9));
        assertEquals(new Integer(10), ngSet.ceiling(10));
    }

    /**
     * Queue: LinkedList, ArrayDeque
     * Stack peek(), push(), pop() exception
     * When talking about LIFO (stack), people say push/poll/peek.
     * When talking about FIFO (single-ended queue), people say offer/poll/peek.
     *
     * ArrayDeque: implements all methods in Stack
     * boolean add(E): back, true or NullPointerException if e
     * boolean offer(E): back, true or NullPointerException
     * void push(E): front (queue excluded)
     *
     * E element(): next, exception if empty
     * E peek(): next, null if empty
     *
     * E remove(): remove and return next, exception
     * E poll(): remove and return next, null
     *
     */
    @Test(expected = NullPointerException.class)
    public void test_Queue() {
        Queue<Integer> queue = new ArrayDeque<>();
        queue.offer(3);
        assertTrue(queue.offer(4));
        queue.offer(5);
        assertTrue(3 == queue.poll());
        assertTrue(4 == queue.peek());
        queue.poll();
        assertTrue(5 ==queue.poll());
        assertTrue(null == queue.peek());
        queue.offer(null);

    }

    @Test
    public void test_Stack() {
        Deque<Integer> stack = new ArrayDeque<>();
        stack.push(3);
        stack.push(4);
        stack.push(5);
        assertTrue(5 == stack.poll());
        assertTrue(4 == stack.peek());

    }

    /**
     * Map: HashMap, LinkedHashMap, TreeMap, (Hashtable)
     * # no contains(), add() !!!
     *
     * void clear()
     * int size()
     * boolean isEmpty()
     *
     * V get(Object key)
     * V put(K, V): return previous !! List's set()
     * V remove(Object key)
     *
     * boolean containsKey(Object)
     * boolean containsValue(Object)
     *
     * Set<K> keySet()
     * Collection<V> values()
     */
    @Test
    public void test_Map() {
        Map<String, Double> prices = new HashMap<>();
        prices.put("APPL", 90.7);
        assertTrue(90.7 == prices.put("APPL", 110.5));
        prices.put("FB", 120.4);
        prices.put("NFLX", 140.2);
        assertEquals(new Double("120.4"), prices.get("FB"));
        assertTrue(prices.containsKey("NFLX"));
        assertTrue(prices.containsValue(110.5));
        assertFalse(prices.containsKey("GILD"));

        // random order
        Set<String> ticks = prices.keySet();
        ticks.forEach(System.out::println);

        Collection<Double> values = prices.values();
        values.forEach(System.out::println);

        Map map = new HashMap();
        map.put(123, "www"); // int -> Integer
        assertTrue(map.containsKey(123));
    }

    @Test
    public void test_TreeMap() {
        Map<String, Double> prices = new TreeMap<>();
        prices.put("GOOG", 1100.7);
        prices.put("APPL", 90.7); // cannot be 90, int -> double -> Double X
        assertTrue(90.7 == prices.put("APPL", 110.5));
        prices.put("FB", 120.4);
        prices.put("NFLX", 140.2);
        assertEquals(new Double("120.4"), prices.get("FB"));
        assertTrue(prices.containsKey("NFLX"));
        assertTrue(prices.containsValue(110.5));
        assertFalse(prices.containsKey("GILD"));

        // natural ordered
        Set<String> ticks = prices.keySet();
        ticks.forEach(System.out::println);

        // in same order with key
        Collection<Double> values = prices.values();
        values.forEach(System.out::println);
    }

    /**
     * The point of Comparable is to implement it inside the object being compared.
     * numbers sort before letters and uppercase letters sort before lowercase letters.
     * Make compareTo() consistent with equals()
     * It is common to decide that nulls sort before any other values.
     * Comparator  let you separate sort order from the object to be sorted.
     */

    @Test
    public void test_Comparable() {
        Card c11 = new Card(Card.Suit.CLUB, 11);
        Card s13 = new Card(Card.Suit.SPADE, 13);
        Card h1 = new Card(Card.Suit.HEART, 1);
        Card d7 = new Card(Card.Suit.DIAMOND, 7);
        Card h7 = new Card(Card.Suit.HEART, 7);
        NavigableSet<Card> deck = new TreeSet<>();
        deck.add(c11);
        deck.add(h1);
        deck.add(s13);
        deck.add(d7);
        deck.add(h7);
        deck.forEach(System.out::println);
    }

    @Test
    public void test_Comparator() {
        Comparator<Card> byRankFirst = Comparator.comparingInt(c -> c.getRank());
        byRankFirst = byRankFirst.thenComparingInt(c -> c.getSuit().ordinal());

        PriorityQueue<Card> deck = new PriorityQueue<>(byRankFirst);
        Card c11 = new Card(Card.Suit.CLUB, 11);
        Card s13 = new Card(Card.Suit.SPADE, 13);
        Card h1 = new Card(Card.Suit.HEART, 1);
        Card d7 = new Card(Card.Suit.DIAMOND, 7);
        Card h7 = new Card(Card.Suit.HEART, 7);
        deck.add(c11);
        deck.add(h1);
        deck.add(s13);
        deck.add(d7);
        deck.add(h7);
        while (!deck.isEmpty()) {
            System.out.println(deck.poll());
        }

        System.out.println("----- Old way -------");
        PriorityQueue<Card> deck1 = new PriorityQueue<>(new RankFirstComparator());
        deck1.add(c11);
        deck1.add(h1);
        deck1.add(s13);
        deck1.add(d7);
        deck1.add(h7);
        while (!deck1.isEmpty()) {
            System.out.println(deck1.poll());
        }

        System.out.println("----- Rev natural -------");
        Comparator<Card> byRankFirstRev = Comparator.reverseOrder();
        PriorityQueue<Card> deck4 = new PriorityQueue<>(byRankFirstRev);
        deck4.add(c11);
        deck4.add(h1);
        deck4.add(s13);
        deck4.add(d7);
        deck4.add(h7);
        while (!deck4.isEmpty()) {
            System.out.println(deck4.poll());
        }

        System.out.println("----- Natual copy -------");
        PriorityQueue<Card> deck2 = new PriorityQueue<>(new SuitFirstComparator());
        deck2.add(c11);
        deck2.add(h1);
        deck2.add(s13);
        deck2.add(d7);
        deck2.add(h7);
        while (!deck2.isEmpty()) {
            System.out.println(deck2.poll());
        }


        PriorityQueue<Card> deck3 = new PriorityQueue<>();
        deck3.add(c11);
        deck3.add(h1);
        deck3.add(s13);
        deck3.add(d7);
        deck3.add(h7);

        System.out.println("----- forEach -------");
        deck3.forEach(System.out::println);
        //The Iterator provided in method iterator() is not guaranteed to traverse the elements of the priority queue in any particular order.
        System.out.println("----- Natual way -------");
        while (!deck3.isEmpty()) {
            System.out.println(deck3.poll());
        }
    }

    /**
     * sort() method need the class implements Comparable, otherwise ce! or add Comparator to argument.
     * comparator as the precondition for binarySearching!! meet or negative value
     */
    @Test
    public void test_BinarySearchNoMeetComparator() {
        Card c11 = new Card(Card.Suit.CLUB, 11);
        Card s13 = new Card(Card.Suit.SPADE, 13);
        Card h1 = new Card(Card.Suit.HEART, 1);
        Card d7 = new Card(Card.Suit.DIAMOND, 7);
        Card h7 = new Card(Card.Suit.HEART, 7);
        Comparator<Card> rev = Comparator.reverseOrder();
        List<Card> deck = new ArrayList<>();
        deck.add(c11);
        deck.add(h1);
        deck.add(s13);
        deck.add(d7);
        deck.add(h7);
        Collections.sort(deck, rev);
        deck.forEach(System.out::println);
        System.out.println(Collections.binarySearch(deck, h1, rev));
    }

    /**
     * Method reference - :: operator tells Java to pass the parameters automatically
     * :: is like lambdas, and it is typically used for deferred execution.
     *
     * There are four formats for method references:
     * 1. Static methods
     * 2. Instance methods on a particular instance
     * 3. Instance methods on an instance to be determined at runtime
     * 4. Constructors - constructor reference
     */

    @Test
    public void test_MethodRef() {
        //1. static
        List<Double> list = Arrays.asList(-23.2, -0.4, 5.0, -2.2);
        Function<Double, Double> abs = Math::abs;
        Function<Double, Double> absL = x -> Math.abs(x); // lambda form
        list.stream().map(abs).forEach(System.out::println);
        list.stream().map(absL).forEach(System.out::println);

        //2. particular instance
        String name = "wilson";
        Predicate<String> endStr = name::endsWith;
        Predicate<String> endStrL = str -> name.endsWith(str);
        List<String> postfix = Arrays.asList("ton","son", "iam");
        postfix.stream().filter(endStr).forEach(System.out::println);
        postfix.stream().filter(endStrL).forEach(System.out::println);

        //3. instance runtime
        Function<String, Integer> endStr1 = String::length;
        Function<String, Integer> endStr1L = x -> x.length();
        Predicate<String> methodRef3 = String::isEmpty;
        postfix.stream().map(endStr1).forEach(System.out::println);
        postfix.stream().map(endStr1L).forEach(System.out::println);

        //4. constructor - constructor reference
        Supplier<String> newStr = String::new;
        Supplier<String> newStrL = () -> new String("will");
    }

    @Test
    public void test_RemoveIf() {
        Set<String> names = new HashSet<>(Arrays.asList("Will","Bill", "Billy", "Wilson"));
        names.removeIf(s -> s.startsWith("Bill"));
        assertFalse(names.contains("Billy"));
    }

    @Test
    public void test_ReplaceAll() {
        //a UnaryOperator, which takes one parameter and returns a value of the same type.
        List<Double> list = Arrays.asList(-23.2, -0.4, 5.0, -2.2);
        list.replaceAll(x -> x + 10);
        list.forEach(System.out::println);

    }

    /**
     * V merge(K, V) : If the specified key is not already associated with a value
     * or is associated with null,
     *  associates it with the given non-null value.
     *
     *  If the mapping function is called and returns null, the key is removed from the map for computeIfPresent().
     *  For computeIfAbsent(), the key is never added to the map in the first place,
     */
    @Test
    public void test_MapNewApi() {
        Map<String, Double> prices = new HashMap<>();
        prices.put("GOOG", 1100.7);
        prices.put("APPL", 90.7);
        prices.put("FB", 120.4);
        prices.put("NFLX", 140.2);

        prices.putIfAbsent("FB", 122.5);
        prices.putIfAbsent("TWWT", 18.7);


        BiFunction<Double, Double, Double> remapper = (vFormer, vLatter) -> Math.max(vFormer, vLatter);
        prices.put("GPRO", null);
        prices.merge("GS", 200.4, remapper); // add new price
        prices.merge("GPRO", 8.9, remapper); // for price with null
        prices.merge("APPL", 130.45, remapper); // exist with smaller price
        prices.merge("FB", 110.85, remapper); //exist with larger price

        BiFunction<Double, Double, Double> renull = (vFormer, vLatter) -> null;
        prices.merge("MS", 40.32, renull);  //added
        prices.merge("GPRO", 12.32, renull); //deleted

        BiFunction<String, Double, Double> uptrend = (k, v) -> v += 10;
        prices.computeIfPresent("FB", uptrend); //120.4+10
        prices.computeIfPresent("C", uptrend);
        prices.put("QQQ", null);
        prices.computeIfPresent("QQQ", uptrend); // do noting

        Function<String, Double> ipo = k -> k.length() * 5.0;
        prices.computeIfAbsent("AMAZ", ipo);
        prices.computeIfAbsent("FB", ipo);

        System.out.println(prices);

    }


}
