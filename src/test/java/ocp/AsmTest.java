package ocp;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.ConcurrentModificationException;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

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



}
