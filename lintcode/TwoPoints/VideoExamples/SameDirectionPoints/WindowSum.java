/**
 * Refer to
 * https://yeqiuquan.blogspot.com/2017/03/lintcode-604-window-sum.html
 * Description
   Given an array of n integer, and a moving window(size k), move the window at each iteration 
   from the start of the array, find the sum of the element inside the window at each moving.
   
    Example
    For array [1,2,7,8,5], moving window size k = 3.
    1 + 2 + 7 = 10
    2 + 7 + 8 = 17
    7 + 8 + 5 = 20
    return [10,17,20]
 * 
 * Solution
 * https://yeqiuquan.blogspot.com/2017/03/lintcode-604-window-sum.html
*/
public class Solution {
    /**
     * @param nums a list of integers.
     * @return the sum of the element inside the window at each moving.
     */
    public int[] winSum(int[] nums, int k) {
        // write your code here
       int[] sum = new int[nums.length - k + 1];
       for(int i = 0; i < k; i++) {
           sum[0] += nums[i];
       }
       for(int i = 1; i < nums.length; i++) {
           sum[i] = sum[i - 1] - nums[i - 1] + nums[i + k - 1];
       }
       return sums;
    }
}
