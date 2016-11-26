package ocp;

import org.junit.Test;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.NavigableSet;
import java.util.Queue;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

import static org.junit.Assert.*;

/**
 * Created by williaz on 11/26/16.
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
     * # Wildcards: (1. unbound: ?; 2. upper bound: ? extends (any class that extends); )-> make logical immutable!
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
        //numbers.add(new Double(12.2)); // cannot apply for object type
        //numbers.add(new Integer(23)); // cannot for reference type
        numbers.remove(0); // but can remove
        assertEquals(43, numbers.get(0));

        //lower bound wildcard
        List<? super IOException> nums = new ArrayList<IOException>();
        //nums.add(new Exception());  // cannot for object type
        nums.add(new IOException());
        nums.add(new FileNotFoundException()); // only need to check object type actully

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
     * Queue: LinkedList
     * Stack: ArrayDeque
     *
     *
     */

    /**
     * Common collection method(6), Set:
     *
     * boolean add(E)
     * boolean remove(Object)
     * boolean isEmpty()
     *     int size()
     *    void clear()
     * boolean contains(Object)
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
    }

    @Test
    public void test_TreeMap() {
        Map<String, Double> prices = new TreeMap<>();
        prices.put("GOOG", 1100.7);
        prices.put("APPL", 90.7);
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



}
