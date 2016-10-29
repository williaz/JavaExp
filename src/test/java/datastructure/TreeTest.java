package datastructure;

import dataStructure.tree.TreeNode;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static dataStructure.tree.TreeUtil.*;
import static org.junit.Assert.*;

/**
 * Created by williaz on 10/29/16.
 */
public class TreeTest {
    private TreeNode<Integer> bst; // binary search tree
    @Before
    public void setUp() throws Exception {
        // instantiate and initialize bst
        TreeNode<Integer> t3 = new TreeNode<Integer>(null, null, 3);
        TreeNode<Integer> t8 = new TreeNode<Integer>(null, null, 8);
        TreeNode<Integer> t20 = new TreeNode<Integer>(null, null, 20);

        TreeNode<Integer> t5 = new TreeNode<Integer>(t3, t8, 5);
        TreeNode<Integer> t13 = new TreeNode<Integer>(null, t20, 13);

        bst = new TreeNode<Integer>(t5, t13, 10);

    }

    @Test
    public void Test_findNodeInBST(){

        assertTrue(findNodeInBST(bst, 5).getValue() == 5);
        assertEquals(findNodeInBST(bst, 45), null);
        assertTrue(findNodeInBST(bst, 8).getValue() == 8);

    }

    @Test
    public void Test_PreOrderTraverse(){
        List<Integer> list = preOrderTraverse(bst);
        System.out.println(list);
    }

    @Test
    public void Test_GetHeight(){
        TreeNode<Integer> t27 = new TreeNode<Integer>(null, null, 27);
        TreeNode<Integer> t42 = new TreeNode<Integer>(bst, t27, 42);
        assertEquals(4, getHeight(t42));
    }

    @After
    public void tearDown() throws Exception {
        bst = null;

    }
}
