package dataStructure.hashtable;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by williaz on 10/28/16.
 *
 * this map is an array of Buckets, every Bucket store a LinkedList of Entry, Entry stores the key and value pair
 * allow null as a key, by using short circuit of || in if (equals())
 *
 * map use its hash function -- getIndexOfTable() -- on key's hashcode() to get the its Entry's location in buckets
 *
 * get() first uses hash function get the bucket location; check bucket null or not; then use key.equals to get the Entry
 *
 * put() first get the bucket location; check bucket got initialized or not; if key already exists, delete the Entry;
 * add the Entry
 *
 */
public class MyMap<K, V> {
    //Map<String, Integer> scores = new HashMap<>(); // sample

    // using fix array currently
    private final static int SIZE_OF_TABLE = 5;
    LinkedList< Entry<K, V> > [] buckets= new LinkedList[SIZE_OF_TABLE];// the table


    /**
     * item
     * @param <K> key type
     * @param <V> value type
     */
    private static class Entry<K, V>{
        K key = null;
        V value = null;

        public Entry(K key, V value) {
            this.key = key;
            this.value = value;
        }
    }

    /**
     * find the bucket location where to store entry
     *
     * @param key key of Entry, will use its hashcode() method
     * @return index of buckets
     */
    private int getIndexOfTable(K key){
        if (key == null) return 0;

        int index = Math.abs(key.hashCode() % SIZE_OF_TABLE);
        return index;
    }

    /**
     * store, Create or Update, guarantee unqiue key
     *
     * @param key key of Entry, uses its hashcode(), equals
     * @param value value in Entry
     * @return value in Entry
     */
    public V put(K key, V value){
        int index = getIndexOfTable(key);

        if(buckets[index] == null){
            buckets[index] = new LinkedList<>();
        }

        // if key already in it
        for(Entry<K, V> e : buckets[index]){
            if(key == null || key.equals(e.key)){
                buckets[index].remove(e);
                break;
            }

        }

        buckets[index].add(new Entry<>(key, value));

        return value;

    }

    /**
     * retrieve
     *
     * @param key key of the entry you want to get, uses its hashcode(), equals
     * @return value stored in this entry or null
     */
    public V get(K key){
        int index = getIndexOfTable(key);

        if(buckets[index] == null){
            return null;
        }

        for(Entry<K, V> e : buckets[index]){
            if(key == null || key.equals(e.key) )
                return e.value;
        }

        return null;
    }

    /**
     *
     * @param key key of the entry you want to remove, uses its hashcode(), equals
     * @return
     */
    public V remove(K key){
        int index = getIndexOfTable(key);

        if(buckets[index] == null){
            return null;
        }

        // if key in it
        for(Entry<K, V> e : buckets[index]){
            if(key == null || key.equals(e.key) ){
                buckets[index].remove(e);
                return e.value;
            }

        }

        return null;
    }

    /**
     * for display
     *
     * @return Set of keys of entries stored
     */
    public Set<K> getKeySet(){

        Set<K> keys = new HashSet<K>(); //-_-!!

        for (int i=0; i< SIZE_OF_TABLE; i++){
            if(buckets[i] !=null){
               // keys.addAll((buckets[i]).stream().map(e -> e.key).collect(Collectors.toList()));
                for(Entry<K, V> e : buckets[i]){
                    keys.add(e.key);
                }
            }
        }

        return keys;
    }




}
