package datastructure;

import dataStructure.hash.MyMap;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by williaz on 10/28/16.
 */
public class HashMapTest {
    @Test
    public void Test_MyMap_StringIntegerPair(){
        MyMap<String, Integer> scores = new MyMap<>();
        scores.put("Math", 99); // create in put()
        scores.put("Math", 97); // same key, update in put()
        scores.put("English", 89);
        scores.put("Chemistry", 99);
        scores.put("Calculus", 95);
        scores.put("Tennis", 100);
        scores.put("Life", 80);
        scores.remove("Calculus");

        Set<String> keys = scores.getKeySet();
        for(String key : keys){
            System.out.println(key + " "+ scores.get(key));
        }
    }

    @Test
    public void Test_HashMap_StringIntegerPair(){
        Map<String, Integer> scores = new HashMap<>();
        scores.put("Math", 99); // create in put()
        scores.put("Math", 97); // same key, update in put()
        scores.put("English", 89);
        scores.put("Chemistry", 99);
        scores.put("Calculus", 95);
        scores.put("Tennis", 100);
        scores.put("Life", 80);
        scores.remove("Calculus");

        Set<String> keys = scores.keySet();
        for(String key : keys){
            System.out.println(key + " "+ scores.get(key));
        }
    }

    @Test
    public void Test_MyMap_IntegerStringPair(){

        MyMap<Integer, String> scores1 = new MyMap<>();
        scores1.put(1, "Math"); // create in put()
        scores1.put(1,"Geo"); // same key, update in put()
        scores1.put(2, "English");
        scores1.put(3, "Chemistry");
        scores1.put(4,"Calculus");
        scores1.put(5,"Tennis");
        scores1.put(6,"Swim");
        scores1.put(7,"Life");
        scores1.put(null, "whatever");
        scores1.put(0, "Health");
        scores1.remove(5);

        Set<Integer> keys1 = scores1.getKeySet();
        for(Integer key : keys1){
            System.out.println(key + " "+ scores1.get(key));
        }
    }

    @Test
    public void Test_HashMap_IntegerStringPair(){
        Map<Integer, String> scores1 = new HashMap<>();
        scores1.put(1, "Math"); // create in put()
        scores1.put(1,"Geo"); // same key, update in put()
        scores1.put(2, "English");
        scores1.put(3, "Chemistry");
        scores1.put(4,"Calculus");
        scores1.put(5,"Tennis");
        scores1.put(6,"Swim");
        scores1.put(7,"Life");
        scores1.put(null, "whatever");
        scores1.put(0, "Health");
        scores1.remove(5);

        Set<Integer> keys1 = scores1.keySet();
        for(Integer key : keys1){
            System.out.println(key + " "+ scores1.get(key));
        }
    }


}
