package ocp;

import java.util.concurrent.RecursiveTask;

/**
 * Created by williaz on 12/9/16.
 */
public class RecursiveFactorialTask extends RecursiveTask<Long> {
    private long end;
    private long start;

    public RecursiveFactorialTask(long start, long end) {
        this.end = end;
        this.start = start;
    }

    @Override
    protected Long compute() {
        if ((end - start) < 5) {
            long factorial = 1;
            for (long i = end; i > start; i--) {
                factorial *= i;
            }
            return factorial;
        } else {
            long mid = start + (end - start) / 2;
            RecursiveFactorialTask another = new RecursiveFactorialTask(start, mid);
            another.fork();
            return new RecursiveFactorialTask(mid, end).compute() * another.join();
        }
    }
}
