package dataStructure.tree;

/**
 * Created by williaz on 10/29/16.
 * Binary Tree
 *
 * <p>Binary search tree (BST).
 * <p>In a BST, the value held by a node’s left child is less than or equal to its own value,
 * and the value held by a node’s right child is greater than or equal to its value.
 * In effect, the data in a BST is sorted by value:
 * All the descendants to the left of a node are less than or equal to the node,
 * and all the descendants to the right of the node are greater than or equal to the node.
 *</p>
 * <p>Heap
 *
 * <p>Graph
 */
public class TreeNode<T> {
    private TreeNode left;
    private TreeNode right;
    private T value;

    public TreeNode(TreeNode left, TreeNode right, T value) {
        this.left = left;
        this.right = right;
        this.value = value;
    }

    public TreeNode getLeft() {
        return left;
    }

    public TreeNode getRight() {
        return right;
    }

    public T getValue() {
        return value;
    }
}
