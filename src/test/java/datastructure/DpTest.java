package datastructure;

import org.junit.Test;

import static org.junit.Assert.*;
import static dataStructure.array.DP.*;
/**
 * Created by williaz on 10/30/16.
 */
public class DpTest {
    @Test
    public void Test_MinNumsCoin(){
        int[] coins = {1, 3, 5};
        assertEquals(3, getMinNumsOfCoins(coins, 11));
        assertEquals(3, getMinNumsOfCoins(coins, 13));
        assertEquals(1, getMinNumsOfCoins(coins, 5));
    }

    @Test
    public void Test_LIS(){
        int[] nums = {5, 3, 4, 8, 6, 7};
        int[] nums1 = new int[5]; // 0s
        assertEquals(3, getLengthOfLIS(nums));
        assertEquals(0, getLengthOfLIS(null));
        assertEquals(5, getLengthOfLIS(nums1));

    }
}
