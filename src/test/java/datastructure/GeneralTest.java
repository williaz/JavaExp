package datastructure;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by williaz on 10/29/16.
 */
public class GeneralTest {

    public int[] swapWithoutCache(int a, int b){
        a = a - b;
        b = b + a; //a
        a = b - a;

        return new int[] {a, b};
    }

    @Test
    public void Test_SwapWithoutCache(){
        int a = 10;
        int b = 13;

        int[] x = swapWithoutCache(a, b);

        assertEquals(b, x[0]);
        assertEquals(a, x[1]);

    }

    //TODO Window Sum
}
