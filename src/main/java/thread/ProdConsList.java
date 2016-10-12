package thread;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by williaz on 10/11/16.
 */
public class ProdConsList {
    private static class WorkSpace{
        private List<Integer> queue = new LinkedList<>();

        synchronized public void produce(){

        }
    }
}
