package ocp;

import org.junit.Test;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.time.temporal.UnsupportedTemporalTypeException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.ConcurrentModificationException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

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
        prices.merge("APPL", 130, (last, latest) -> Math.max(last, latest));
        prices.merge("FB", 100, (last, latest) -> Math.max(last, latest));
        assertTrue(130 == prices.get("APPL"));
        assertTrue(100 == prices.get("FB"));

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

    @Test
    public void test_CalculateGMT() {
        ZonedDateTime time1 = ZonedDateTime.of(LocalDateTime.now(), ZoneId.of("Asia/Shanghai"));
        ZonedDateTime time2 = ZonedDateTime.now();
        System.out.println(time1); // minus offset
        System.out.println(time2);
        System.out.println(Duration.between(time1, time2));

    }

    @Test
    public void test_FilesRelativize() {
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

    //Suppressed exception is only caused by try-with-resource's close().
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




}
