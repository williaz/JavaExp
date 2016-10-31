package dataStructure.linked;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by williaz on 10/30/16.
 * 1. Dummy Node in Linked List
 * 2. Basic Linked List Skills
 * 3. Two Pointers in Linked List (Fast-slow pointers)
 */
public class NodeUtil {

    /**
     * find Node by value
     *
     * @return found node
     */
    public static <T> Node<T> findByValue(Node<T> list, T value) {
        Node<T> temp = list;
        while (temp != null) {
            if (temp.getValue().equals(value)) {
                return temp;
            }
            temp = temp.getNext();
        }
        return null;
    }

    public static <T> List<T> asList(Node<T> root) {
        List<T> list = new ArrayList<T>();
        Node<T> temp = root;
        while (temp != null) {
            list.add(temp.getValue());
            temp = temp.getNext();
        }
        return list;
    }

    /**
     * insert a Node in Sorted List
     *
     * @param <T> implements Comparable, as using compareTo()
     * @return the inserted new list in case removed head
     */
    public static <T extends Comparable<? super T>> Node<T> insert(Node<T> list, Node<T> tgt) {
        T value = tgt.getValue();
        // if head greater than tgt
        if (value.compareTo(list.getValue()) <= 0) {
            tgt.setNext(list.getNext());
            return tgt;
        }
        Node<T> temp = list.getNext();
        Node<T> prev = list;
        // find the node greater than tgt
        while (temp != null) {
            if (value.compareTo(temp.getValue()) <= 0) {

                tgt.setNext(temp);
                prev.setNext(tgt);
                return list;
            }
            prev = temp;
            temp = temp.getNext();

        }
        // all less than tgt, temp = null
        prev.setNext(tgt);

        return list;
    }

    /**
     * Remove a Node from Linked List
     *
     * @return new list in case removed head
     */
    public static <T> Node<T> remove(Node<T> list, T value) {
        Node<T> temp = list.getNext();
        Node<T> prev = list;
        //head removal
        if (prev.getValue().equals(value)) {
            return temp;
        }

        while (temp != null) {
            if (value.equals(temp.getValue())) {
                prev.setNext(temp.getNext());
                return list;
            }
            prev = temp;
            temp = temp.getNext();
        }
        return list;
    }


    //TODO Reverse a Linked List
    //TODO Merge Two Linked Lists
    //TODO Middle of a Linked List
}
