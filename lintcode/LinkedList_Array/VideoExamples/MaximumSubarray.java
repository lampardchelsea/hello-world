/**
 * Refer to
 * http://www.lintcode.com/en/problem/maximum-subarray/ 
 * Find the contiguous subarray within an array (containing at least one number) which has the largest sum.
   For example, given the array [-2,1,-3,4,-1,2,1,-5,4],
   the contiguous subarray [4,-1,2,1] has the largest sum = 6.
   click to show more practice.
   More practice:
   If you have figured out the O(n) solution, try coding another solution using the divide and conquer approach, 
   which is more subtle.
 *
 * Solution
 * http://www.jiuzhang.com/solutions/maximum-subarray/
 * The preSum[] thought is very similar to DP
*/
public class Solution {
    /*
     * @param nums: A list of integers
     * @return: A integer indicate the sum of max subarray
     */
    public int maxSubArray(int[] nums) {
        if(nums == null || nums.length == 0) {
            return 0;
        }
        int sum = 0;
        int minSum = 0;
        int max = Integer.MIN_VALUE;
        for(int i = 0; i < nums.length; i++) {
            sum += nums[i];
            max = Math.max(max, sum - minSum);
            minSum = Math.min(minSum, sum);
        }
        return max;
    }
}
