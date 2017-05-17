/**
 * Refer to
 * https://leetcode.com/problems/find-all-duplicates-in-an-array/#/description
 * Given an array of integers, 1 ≤ a[i] ≤ n (n = size of array), some elements appear 
 * twice and others appear once.
 * Find all the elements that appear twice in this array.
 * Could you do it without extra space and in O(n) runtime?
 * Example:
	Input:
	[4,3,2,7,8,2,3,1]
	
	Output:
	[2,3]
 * 
 * Solution
 * https://discuss.leetcode.com/topic/18583/accepted-clean-java-o-n-solution-two-pointers
 */
public class FindAllDuplicatesInAnArray {
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

