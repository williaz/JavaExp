package dataStructure.linked;

/**
 * Created by williaz on 10/30/16.
 * <p>
 * Single - linked list
 */
public class Node<T> {
    T value;
    Node<T> next;

    public Node(T value) {
        this(value, null);
    }

    public Node(T value, Node<T> next) {
        this.value = value;
        this.next = next;
    }

    public void setNext(Node<T> next) {
        this.next = next;
    }

    public T getValue() {
        return value;
    }

    public Node<T> getNext() {
        return next;
    }
}
