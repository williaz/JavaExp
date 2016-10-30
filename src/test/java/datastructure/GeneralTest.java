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

    /**
     * every Object uses new to instantiate but without initialization, will get the default value
     *
     * <p>String, primitive uses literal in local scope, should be guarantee be initialized
     */
    @Test
    public void Test_LocalArrayDefaultValue(){
        int[] arr = new int[5];
        assertEquals(0, arr[4]);

        boolean[] bs = new boolean[4];
        assertEquals(false, bs[3]);

    }

    //TODO Window Sum
    public int[] getWindowSum(int[] arr, int range){
        final int SIZE = arr.length - range + 1;
        if(SIZE <= 0){
            return null;
        }
        int[] sum = new int[SIZE];

        for(int j = 0; j < range; j++){
            sum[0] += arr[j];
        }

        for(int i = 1; i < SIZE; i++){
            sum[i] = sum[i - 1] - arr[i - 1] + arr[i + range - 1];

        }//end i

        return sum;
    }

    @Test
    public void Test_GetWindowSum(){
        int[] arr = {4, 2, 73, 11, -5};
        int[] sum2 = {6, 75, 84, 6};

        assertArrayEquals(sum2, getWindowSum(arr, 2));
    }
}
