package dataStructure.array;

/**
 * Created by williaz on 10/30/16. <p> A DP is an algorithmic technique which is usually based on a
 * recurrent formula and one (or some) starting states. A sub-solution of the problem is constructed
 * from previously found ones. DP solutions have a polynomial complexity <p></p>
 */
public class DP {

    /**
     * 1. State is a way to describe a situation, a sub-solution for the problem<br> Note that in
     * most cases the states rely on lower states and are independent from greater states. <p> Given
     * a list of N coins, their values (V1, V2, … , VN), and the total sum S. Find the [minimum]
     * number of coins the sum of which is S (we can use as many coins of one type as we want), or
     * report that it’s not possible to select coins in such a way that they sum up to S.
     */
    public static int getMinNumsOfCoins(int[] coinComb, int sum) {

        int[] nums = new int[sum + 1];

        nums[0] = 0; // initial state

        for (int i = 1; i <= sum; i++) { // i stands for all the possible values under sum - [state]
            nums[i] = Integer.MAX_VALUE; // initialize to MAX
            for (int j = 0; j < coinComb.length; j++) {
                int coin = coinComb[j];
                if (coin <= i && nums[i - coin] <= nums[i]) {
                    nums[i] = Math.min(nums[i - coin] + 1, nums[i]);
                }// end if

            }// end j

        }// end i

        return nums[sum];

    }// end method

    /**
     * 2.  recurrent relation, which makes a connection between a lower and a greater state.
     * <p>
     * Given a sequence of N numbers – A[1] , A[2] , …, A[N] .
     * Find the length of the longest non-decreasing sequence.
     */
    public static int getLengthOfLIS(int[] nums) {
        if (nums == null) {
            return 0;
        }
        int[] state = new int[nums.length];
        state[0] = 1;
        for (int i = 1; i < nums.length; i++) {
            if (nums[i] >= nums[i - 1]) {
                state[i] = state[i - 1] + 1;
            } else {
                state[i] = 1;
            }
        }
        int max = 0;
        for (int i : state) {
            max = Math.max(max, i);
        }

        return max;

    }

}
