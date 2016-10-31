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

    /**
     * Reverse a Linked List
     * be care of circle in list, break it!
     */
    public static <T> Node<T> reverse(Node<T> list) {
        Node<T> head = null;
        if (list == null) {
            return head;
        }
        head = list; // head of list
        Node<T> temp = list.getNext();
        head.setNext(null); // break node from the list, make sure it is only a node

        while (temp != null) {
            Node<T> cont = head; // preserve the reversed list
            head = temp;  // assign the current node
            temp = temp.getNext(); // pointer for loop
            head.setNext(cont); // append the reversed list
        }
        return head;

    }

    /**
     * Merge Two Linked Lists
     *
     * @param <T> any type implements Comparable or its super class has implemented
     * @return merged list, input data got dirtied.
     */
    public static <T extends Comparable<? super T>> Node<T> mergeTwoSortedList(Node<T> a, Node<T> b) {

        if (a == null && b == null) return null;
        else if (a == null && b != null) return b;
        else if (a != null && b == null) return a;
        // both no null
        Node<T> aTemp = a;
        Node<T> bTemp = b;
        Node<T> merge = new Node<>(null);
        Node<T> head = merge; // preserve the head of merged list

        while (aTemp != null && bTemp != null) {
            T va = aTemp.getValue();
            T vb = bTemp.getValue();
            if (va.compareTo(vb) <= 0) {
                merge.setNext(aTemp);
                aTemp = aTemp.getNext();
                merge = merge.getNext();

            } else {
                merge.setNext(bTemp);
                bTemp = bTemp.getNext();
                merge = merge.getNext();
            }
        }

        if (aTemp != null) {
            merge.setNext(aTemp);
        } else if (bTemp != null) {
            merge.setNext(bTemp);
        }
        head = head.getNext();
        return head;


    }

    //TODO revise
    /**
     * Middle of a Linked List
     *
     * @return For even length, the right side is the middle
     */
    public static <T> Node<T> getMiddleNode(Node<T> list) {
        // 0 node
        if (list == null) {
            return null;
        }
        // 1 node
        if (list.getNext() == null) {
            return list;
        }
        //2 node
        if (list.getNext().getNext() == null) {
            return list;
        }
        // odd or even
        Node<T> fast = list;
        Node<T> slow = list;
        while (fast != null) {
            fast = fast.getNext();
            if (fast != null) {
                fast = fast.getNext();
                slow = slow.getNext();
            } else {
                break;
            }

        }

        return slow;

    }
}
