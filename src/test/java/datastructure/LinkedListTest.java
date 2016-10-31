package datastructure;

import org.junit.Before;
import org.junit.Test;

import dataStructure.linked.Node;

import static org.junit.Assert.*;
import static dataStructure.linked.NodeUtil.*;

/**
 * Created by williaz on 10/30/16.
 */
public class LinkedListTest {
    Node<Integer> list;
    Node<Integer> sorted;
    Node<Integer> sorted1;

    @Before
    public void setUp() throws Exception {
        Node<Integer> n7 = new Node<Integer>(7, null);
        Node<Integer> n6 = new Node<Integer>(6, n7);
        Node<Integer> n8 = new Node<Integer>(8, n6);
        Node<Integer> n4 = new Node<Integer>(4, n8);
        Node<Integer> n3 = new Node<Integer>(3, n4);
        Node<Integer> n5 = new Node<Integer>(5, n3);
        list = n5;

        Node<Integer> n42 = new Node<Integer>(42, null);
        Node<Integer> n23 = new Node<Integer>(23, n42);
        Node<Integer> n16 = new Node<Integer>(16, n23);
        Node<Integer> n15 = new Node<Integer>(15, n16);
        Node<Integer> n14 = new Node<Integer>(14, n15);
        Node<Integer> n11 = new Node<Integer>(11, n14);
        sorted = n11;

        Node<Integer> n89 = new Node<Integer>(89, null);
        Node<Integer> n52 = new Node<Integer>(52, n89);
        Node<Integer> n44 = new Node<Integer>(44, n52);
        Node<Integer> n36 = new Node<Integer>(36, n44);
        Node<Integer> n28 = new Node<Integer>(28, n36);
        Node<Integer> n17 = new Node<Integer>(17, n28);
        Node<Integer> n12 = new Node<Integer>(12, n17);
        Node<Integer> n10 = new Node<Integer>(10, n12);
        Node<Integer> n2 = new Node<Integer>(2, n10);
        sorted1 = n2;

    }

    @Test
    public void Test_findByValue() {
        System.out.println(asList(list));
        assertTrue(8 == findByValue(list, 8).getValue());
        assertTrue(7 == findByValue(list, 7).getValue());
        assertTrue(5 == findByValue(list, 5).getValue());
    }

    @Test
    public void Test_insert() {
        System.out.println(asList(sorted));
        Node<Integer> tgt = new Node<Integer>(20); // middle
        Node<Integer> inserted = insert(sorted, tgt);
        assertTrue(20 == findByValue(inserted, 20).getValue());
        System.out.println(asList(inserted));

        Node<Integer> tgt1 = new Node<Integer>(10); // head
        inserted = insert(sorted, tgt1);
        assertTrue(10 == findByValue(inserted, 10).getValue());
        System.out.println(asList(inserted));

        Node<Integer> tgt2 = new Node<Integer>(50); // tail
        inserted = insert(sorted, tgt2);
        assertTrue(50 == findByValue(inserted, 50).getValue());
        System.out.println(asList(inserted));


    }

    @Test
    public void Test_remove() {
        System.out.println(asList(list));
        list = remove(list, 4);     //m
        System.out.println(asList(list));
        list = remove(list, 5);     //h
        System.out.println(asList(list));
        list = remove(list, 7);     //t
        System.out.println(asList(list));

    }

    @Test
    public void Test_Reverse(){
        System.out.println(asList(list));
        System.out.println(asList(reverse(list)));
        System.out.println(asList(list));
    }

    @Test
    public void Test_Merge(){
        System.out.println(asList(sorted));
        System.out.println(asList(sorted1));
        System.out.println(asList(mergeTwoSortedList(sorted, sorted1)));

    }

    @Test
    public void Test_GetMiddleNode(){

        assertTrue(28 == getMiddleNode(sorted1).getValue());
        assertTrue(8 == getMiddleNode(list).getValue());
        Node<Integer> n7 = new Node<Integer>(7, null);
        Node<Integer> n6 = new Node<Integer>(6, n7);
        Node<Integer> n8 = new Node<Integer>(8, n6);
        Node<Integer> n4 = new Node<Integer>(4, n8);
        assertTrue(6 == getMiddleNode(n8).getValue()); //3
        assertTrue(6 == getMiddleNode(n4).getValue());  //4
        assertTrue(6 == getMiddleNode(n6).getValue());  //2

    }
}

