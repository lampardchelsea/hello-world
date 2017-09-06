/**
 * Refer to
 * https://aaronice.gitbooks.io/lintcode/content/high_frequency/2sum_closest.html
 * Given an array nums of n integers, find two integers in nums such that the sum is 
   closest to a given number, target.
    Return the difference between the sum of the two integers and the target.
    Example
    Given array nums = [-1, 2, 1, -4], and target = 4.
    The minimum difference is 1. (4 - (2 + 1) = 1).
 *
 *
 * Solution
 * https://aaronice.gitbooks.io/lintcode/content/high_frequency/2sum_closest.html
 * 与3 sum closest问题相似，通过先对数组排序，再用两个指针的方法，可以满足 O(nlogn) + O(n) ~ O(nlogn)的时间复杂度的要求
   不同的是这里要返回的是diff，所以只用记录minDiif即可。
*/

public class Solution {
    /**
     * @param nums an integer array
     * @param target an integer
     * @return the difference between the sum and the target
     */
    public int twoSumCloset(int[] nums, int target) {
        if(nums == null || nums.length < 2) {
            return -1;
        }   
        int start = 0;
        int end = nums.length - 1;
        int minDiff = Integer.MAX_VALUE;
        Arrays.sort(nums);
        while(start < end) {
            int diff = Math.abs(nums[start] + nums[end] - target);
            minDiff = Math.min(minDiff, diff);
            if(nums[start] + nums[end] > target) {
                end--;
            } else {
                start++;
            }
        }
        return minDiff;
    }
     
    public static void main(String[] args) {
        Solution t = new Solution();
        int[] nums = {-1, 2, 1, -4};
        int target = 4;
        int minDiff = t.twoSumCloset(nums, target);
        System.out.println(minDiff);
    }
}
