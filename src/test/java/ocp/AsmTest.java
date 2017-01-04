package ocp;

import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Statement;
import java.time.DateTimeException;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Period;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.time.temporal.UnsupportedTemporalTypeException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.ConcurrentModificationException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Consumer;
import java.util.stream.Stream;

import static org.junit.Assert.*;

/**
 * Created by williaz on 11/20/16.
 * OCP Begins. 1/5/2017
 *
 * abstract final
 * IO
 * concurrent
 * NIO Path
 * assert
 * Stream
 * ResourceBundle properties
 * Time Duration
 *
 * ---
 * IO, Console
 * Stream
 * assert enable or not!
 * no terminal operation
 */
public class AsmTest {

    @Test(expected = ConcurrentModificationException.class)
    public void test_List_UpdateWhileIterate() {
        List<Integer> l = new ArrayList<>(Arrays.asList(1, 2 , 5, 4));
        for (int i : l) {
            l.add(23);
        }
    }

    @Test(expected = ConcurrentModificationException.class)
    public void test_SynList_UpdateWhileIterate() {
        List<Integer> l = new ArrayList<>(Arrays.asList(1, 2 , 5, 4));
        List<Integer> list = Collections.synchronizedList(l);
        for (int i : list) {
            list.add(23);
        }
    }

    @Test
    public void test_ConcurrList_UpdateWhileIterate() {
        List<Integer> l = new ArrayList<>(Arrays.asList(1, 2 , 5, 4));
        List<Integer> list = new CopyOnWriteArrayList<>(l);
        for (int i : list) {
            list.add(23);
        }
    }

    @Test
    public void test_Null_Instanceof() {
        assertFalse(null instanceof Object);// can check, no ce
    }

    @Test
    public void test_InstantiateMememberInnerClass() {
        OuterClass outer = new OuterClass();
        OuterClass.InnerClass inner =outer.new InnerClass();
        assertEquals("Inner Outer", inner.getName());

    }

    @Test
    public void test_MapMerge() {
        Map<String, Integer> prices = new HashMap<>();
        prices.put("APPL", 125);
        prices.put("FB", null);
        prices.put("GPRO", 8);
        prices.merge("APPL", 130, (last, latest) -> Math.max(last, latest));
        prices.merge("FB", 100, (last, latest) -> Math.max(last, latest));
        prices.merge("GRPO", 100, (last, latest) -> null);
        assertTrue(130 == prices.get("APPL"));
        assertTrue(100 == prices.get("FB"));
        System.out.println(prices);

    }

    public void printList(List<Integer> ints) {
        System.out.println(ints);
    }

    @Test
    public void test_Generic_RawParam() {
        List list = new ArrayList();
        list.add("will");
        list.add(23);
        printList(list);
    }

    @Test(expected = UnsupportedTemporalTypeException.class)
    public void test_DateTimeFormatter() {
        DateTimeFormatter formatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT);
        System.out.println(formatter.format(LocalDate.now()));
    }

    /**
     * ofLocalizedXxx must narrower than LocalXxx
     */
    @Test
    public void test_DateTimeFormatter1() {
        LocalDateTime d = LocalDateTime.of(2015, 5, 10, 11, 22, 33);
        DateTimeFormatter f = DateTimeFormatter.
                ofLocalizedTime(FormatStyle.SHORT);
        System.out.println(f.format(LocalDateTime.now()));//ofLocalizedXxx must narrower than LocalXxx
        System.out.println(d.format(f));
    }

    @Test
    public void test_CalculateGMT() {
        ZonedDateTime time1 = ZonedDateTime.of(LocalDateTime.now(), ZoneId.of("Asia/Shanghai"));
        ZonedDateTime time2 = ZonedDateTime.now();
        System.out.println(time1); // minus offset
        System.out.println(time2);
        System.out.println(Duration.between(time1, time2));

    }

    /**
     * relativize(): must same type, both absolute or both  relative
     */
    @Test
    public void test_Path_Relativize() {
        Path windows = Paths.get("\\Users\\williaz\\IdeaProjects");
        Path absolute = Paths.get("/Users/williaz/IdeaProjects/JavaExp");
        Path absolute1 = Paths.get("/Users/williaz/IdeaProjects/JavaExp/nio");
        Path relative = Paths.get("./io");
        Path relative1 = Paths.get("./nio");
        System.out.println(absolute.relativize(absolute1));
        System.out.println(relative.relativize(relative1));
        //System.out.println(relative.relativize(absolute1)); // must same type, both absolute or both  relative
        //System.out.println(absolute.relativize(relative1));
    }

    @Test
    public void test_Path_Resolve() { //append path: if relative path as the param, just add it;
                                      // if absolute path as the param, just return it;
        Path absolute = Paths.get("/Users/williaz/IdeaProjects/JavaExp");
        Path absolute1 = Paths.get("/Users/williaz/IdeaProjects/JavaExp/nio");
        Path relative = Paths.get("./io");
        Path relative1 = Paths.get("./nio");
        System.out.println(absolute.resolve(relative1));
        System.out.println(relative.resolve(relative1));
        assertEquals(absolute, relative.resolve(absolute));
        assertEquals(absolute1, absolute.resolve(absolute1));

    }

    @Test
    public void test_GenericType() {
        //List<Number> nums = new ArrayList<Integer>(); //must same type
        List<? extends Number> nums = new ArrayList<Integer>(); //or use wildcard
    }

    @Test(expected = UnsupportedOperationException.class)
    public void test_Arrays_asList() {
        List<Integer> ints = Arrays.asList(23, 46, 19, 20);
        ints.add(44); // cannot change size
    }

    @Test
    public void test_Arrays_asList1() {
        List<Integer> ints = Arrays.asList(23, 46, 19, 20);
        Collections.sort(ints); //but can be sorted

    }

    @Test(expected = ArrayIndexOutOfBoundsException.class)
    public void test_PrintWithOutOfException() {
        int[] arr = new int[2];
        arr[0] = 4;
        arr[1] = 5;
        System.out.println(arr[0] + arr[1] + arr[2]); // throw exception without any print out

    }

    /**
     * Suppressed exception is only caused by try-with-resource's close().
     */
    @Test(expected = ArrayIndexOutOfBoundsException.class)
    public void test_NoSuppressedException() {
        try {
            throw new IOException();
        } catch (IOException ie) {
            try {
                throw new RuntimeException();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } finally {
            throw new ArrayIndexOutOfBoundsException();
        }
    }

    @Test
    public void test_Stream_Iterate() {
        Stream.iterate(1, x -> ++x).limit(10).forEach(System.out::println);//1, 2, 3, 4, ...
        Stream.iterate(1, x -> x++).limit(10).forEach(System.out::println);//1, 1, 1, 1, ...
    }

    @Test
    public void test_ResourceBundle_LookUpOrder() {
        Locale fr = new Locale("fr");
        Locale.setDefault(Locale.ENGLISH);
        ResourceBundle rb = ResourceBundle.getBundle("ocp.orca", fr);
        assertEquals("Frc", rb.getString("name")); //in orca_fr
        assertEquals("12f", rb.getString("age")); // in orca.properties
    }

    //private static int counter = 0;
    private int counter = 0;

    /**
     * lambda can only access final or effectively final local variables,
     *        but can access any static or instance variables.
     */
    @Test
    public void test_Runnable() {
        ExecutorService executorService = null;
        //int counter = 0;
        try {
            executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
            executorService.execute(() -> counter++);
        } finally {
            if (executorService != null) executorService.shutdown();
        }
        System.out.print(counter);
    }

    @Test
    public void test_GenericWithRaw() {
        List<? extends Statement> list = new ArrayList(); //only warning, no ce
    }

    @Test(expected = RuntimeException.class)
    public void test_FinallyThrowExceptionNoSuppressed() {
        try {
            throw new RuntimeException();
        } finally {
            try {
                throw new IOException();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Test
    public void test_CorrectLambda() {
        Consumer<Integer> consumer = (Integer i) -> System.out.println(i); // must has the parentheses

    }

    @Test
    public void Path_Subpath() {
        Path local = Paths.get("io").toAbsolutePath();
        for (int i = 0; i < local.getNameCount(); i++) {
            System.out.print(local.getName(i) + " "); //Path.getName() does not include root directory
        }
        System.out.println("\n" + local.subpath(0, 1)); //return a relative path
        System.out.println(local.getParent());
        assertEquals("/", local.getRoot().toString());
        assertEquals("/", Paths.get("/").getRoot().toString());
        assertEquals(null, Paths.get("/").getParent());


    }

    //TODO GMT convertion, 24 compare

    @Test
    public void test_List_Sort() {
        List<Integer> ints = Arrays.asList(23, 12, 19, 98, 44, 100, 261, 23942, 9999);
        Comparator<Integer> sortByFirstDigit = (i1, i2) -> {
            int a = 0, b = 0;
            while ((i1/=10) > 10);
            while ((i2/=10) > 10);
            a = i1 % 10;
            b = i2 % 10;
            return a - b;
        };
        ints.sort(sortByFirstDigit);
        System.out.println(ints);

    }

    @Test
    public void test_FlatMap() {
        Stream<Integer> i1 = Stream.iterate(1, i -> i*2).limit(5);
        Stream<Integer> i2 = Stream.generate(() -> 2).limit(5);
        Stream<Integer> i3 = Stream.of(91, 12, 34, 56, 11);
        Stream.of(i1, i2, i3).flatMap(i -> i).forEach(i -> System.out.print(i + " "));
        System.out.println();
        Stream.of(i1, i2, i3).map(i -> i).forEach(i -> System.out.print(i + " "));
    }

    /**
     * file.mkdirs()
     * createDirectory(Path)
     */
    @Test
    public void test_Mkdirs() throws IOException {
        Path local = Paths.get("io/newDir/ww").toAbsolutePath();
        File f = new File("io/n2/ww");
        Files.deleteIfExists(local);
        f.delete();
        Files.createDirectories(local);
        f.mkdirs();
    }

    /**
     * LocalDate.of(2016, 4, 32)
     */
    @Test(expected = DateTimeException.class)
    public void test_LocalDate_Of() {
        LocalDate d1 = LocalDate.of(2016, 4, 30);
        d1 = d1.plusDays(5);
        System.out.println(d1); //5-5
        LocalDate d = LocalDate.of(2016, 4, 32); // re
        System.out.println(d);
    }

    @Test
    public void test_Subpath_ToAbsolutePath() {
        Path local = Paths.get("").toAbsolutePath();
        System.out.println(local );
        System.out.println(local.subpath(1, 3).toAbsolutePath() );

    }

}
