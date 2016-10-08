package jvm;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by williaz on 10/9/16.
 */
public class WeakReferenceStack<E> {
    private final List<WeakReference<E>> stackReferences;
    private int stackPointer = 0;
    public WeakReferenceStack() {
        this.stackReferences = new ArrayList<>();
    }
    public void push(E element) {
        this.stackReferences.add(
                stackPointer, new WeakReference<>(element));
        stackPointer++;
    }
    public E pop() {
        stackPointer--;
        return this.stackReferences.get(stackPointer).get();
    }
    public E peek() {
        return this.stackReferences.get(stackPointer-1).get();
    }
}

