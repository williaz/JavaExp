package ocp;

import java.util.concurrent.RecursiveAction;

/**
 * Created by williaz on 12/9/16.
 */
public class BinaryRecursiveSearch extends RecursiveAction{
    private int start;
    private int end;
    private int[] randoms;
    private int target;

    public BinaryRecursiveSearch(int start, int end, int[] randoms, int target) {
        this.start = start;
        this.end = end;
        this.randoms = randoms;
        this.target = target;
    }

    @Override
    protected void compute() {
        if ((end - start) < 100) {
            for (int i = start; i < end; i++) {
                if (randoms[i] == target)
                    System.out.println("Found : at index of " + i);
            }
        } else {
            int mid = start + (end - start) / 2;
            invokeAll(new BinaryRecursiveSearch(start, mid, randoms, target),
                    new BinaryRecursiveSearch(mid, end, randoms, target));
        }

    }
}
