/**
 * Refer to
 * https://leetcode.com/problems/minimum-size-subarray-sum/#/description
 *  Given an array of n positive integers and a positive integer s, find the minimal length 
 *  of a contiguous subarray of which the sum ≥ s. If there isn't one, return 0 instead.
 *  For example, given the array [2,3,1,2,4,3] and s = 7,
 *  the subarray [4,3] has the minimal length under the problem constraint.
 *  click to show more practice.
 *  
 *  More practice:
 *  If you have figured out the O(n) solution, try coding another solution of which the time 
 *  complexity is O(n log n).
 * 
 * Solution
 * https://discuss.leetcode.com/topic/18583/accepted-clean-java-o-n-solution-two-pointers
 */
public class MinimumSizeSubarraySum {
    public int minSubArrayLen(int s, int[] nums) {
        int len = nums.length;
        if(len == 0) return 0;
        int minWindow = Integer.MAX_VALUE;
        int left = 0;
        int right = 0;
        int sum = 0;
        while(right < nums.length) {
            sum += nums[right++];
            while(sum >= s) {
                minWindow = Math.min(minWindow, right - left);
                sum -= nums[left++];
            }
        }
        return minWindow == Integer.MAX_VALUE ? 0 : minWindow;
    }
    
    public static void main(String[] args) {
    	
    }
}
