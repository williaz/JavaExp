package dataStructure.tree;

import java.util.*;

/**
 * Created by williaz on 10/29/16. Ref Collections
 * <p>Some basic operations on binary tree
 * <p>Recursion is most useful when moving through multiple branches of a tree or examining some special pattern of nodes.</p>
 * <p>DFS(Depth First Search): pre, in, post order
 * <p>BFS(Breadth First Search): level order
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


    /**
     * DFS: Pre-Oder traverse: root, left, right
     *
     * @param root
     * @param <T>
     * @return
     */
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
        list.add(root.getValue()); // put value
        list.addAll(left);
        list.addAll(right);

        return list;
    }


    /**
     * DFS: pre-order no recursion, to show the understanding of recursion
     *
     * <p>A more complete and consistent set of LIFO stack operations is provided by the <tt>Deque</> interface
     * and its implementations, which should be used in preference to stack.</p>
     *
     * @param root
     * @param <T>
     * @return
     */
    public static <T> List<T> preOrderIteration(TreeNode<T> root){
        Deque<TreeNode<T>> stack = new ArrayDeque<>();
        List<T> list = new ArrayList<T>();

        if (root == null){
            return list;
        }

        stack.push(root);

        while(!stack.isEmpty()){
            TreeNode<T> node =stack.pop();
            list.add(node.getValue());

            if(node.getRight() != null){
                stack.push(node.getRight());
            }

            if(node.getLeft() != null){
                stack.push(node.getLeft());
            }
        }

        return list;

    }

    //TODO DFS: In-order traverse

    //TODO DFS: Post-order traverse

    //TODO Breath First Search

    //TODO Depth First Search



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


    /**
     * get numbers of leaves
     *
     * <p>The <tt>leaves</tt> are nodes that do not have any children.</p>
     *
     * @param root
     * @param <T>
     * @return
     */
    public static <T> int getLeavesNums(TreeNode<T> root){
        int leaves = 0;
        if(root == null){
            return leaves;
        }

        if(root.getLeft()== null && root.getRight() == null){
            leaves++;
        }else {
            leaves += getLeavesNums(root.getLeft()) + getLeavesNums(root.getRight());
        }

        return leaves;

    }

    //TODO lowest common ancestor on BST
    //An ancestor of a node is any other node for which the node is a descendant
    /*
    your target values are both less than the current node, you go left.
    When they are both greater, you go right.
    The first node you encounter that is between your target values is the lowest common ancestor.
     */
    public static<T extends Comparable<? super T>> TreeNode<T> getLcaInBST (TreeNode<T> root, T a, T b){

        T value = null, min = null, max = null;

        if(a.compareTo( b ) <=0 ){
            min = a;
            max = b;
        }else{
            min = b;
            max = a;
        }

        while(root != null) {
            value = root.getValue();

            if(max.compareTo(root.getValue()) <=0 ){
                root = root.getLeft();
            }else if(min.compareTo(root.getValue()) >=0 ){
                root = root.getRight();
            }else{
                return root;
            }

        }

        return null;

    }

}
