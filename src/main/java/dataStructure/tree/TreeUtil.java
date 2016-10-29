package dataStructure.tree;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by williaz on 10/29/16. Ref Collections
 * Some basic operations on binary tree
 *
 */
public class TreeUtil<T> {

    /**
     * findNode in <tt>Binary Search Tree</tt>
     *
     * @param root target BST
     * @param value target value
     * @param <T> T could be any type that implements Comparable<? super T>,
     *            Comparable<? super T> means that type ? passed to Comparable could be T or any supertype of T.
     * @return TreeNode<T> or null
     */
    public static <T extends Comparable<? super T>> TreeNode<T> findNodeInBST(TreeNode<T> root, T value){
        // case 1: null or exit if no found
        if (root == null){
            return null;
        }

        // case 2: root is empty or non-empty
        T v = root.getValue();
        if( v.compareTo(value) == 0 ){
            return root;
        }else if (v.compareTo(value)> 0){
            return findNodeInBST(root.getLeft(), value);
        }else{
            return findNodeInBST(root.getRight(), value);
        }


    }

    //Pre-order traverse
    public static <T> List<T> preOrderTraverse(TreeNode<T> root){
        List<T> list = new ArrayList<T>();
        // exit
        if(root == null){
            return list;
        }

        // divide
        List<T> left = preOrderTraverse(root.getLeft());
        List<T> right = preOrderTraverse(root.getRight());

        // conquer
        list.addAll(left);
        list.add(root.getValue()); // put value
        list.addAll(right);

        return list;
    }

    //TODO In-order traverse

    //TODO Post-order traverse

    //TODO Breath First Search

    //TODO Depth First Search

    //TODO Height of  Tree


    /**
     * The <tt>height</tt> of a tree is the maximum distance
     * from the root node to any leaf node.
     *
     * @param root
     * @param <T>
     * @return
     */
    public static <T> int getHeight(TreeNode<T> root){

        int height = 0;
        // exit
        if(root == null){
            return height;
        }

        int left = getHeight(root.getLeft());
        int right = getHeight(root.getRight());

        height = 1 + (left >= right? left: right);// short circuit

        return height;


    }
}
