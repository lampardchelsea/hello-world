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
 * https://leetcode.com/articles/minimum-size-subarray-sum/
 * http://likesky3.iteye.com/blog/2229475
 * http://www.cnblogs.com/grandyang/p/4501934.html
 * https://lefttree.gitbooks.io/leetcode-categories/twoPointer/minSizeSubarraySum.html
 */
// Solution 1:
class Solution {
    /**
     Approach #1 Brute force [Time Limit Exceeded]
     Intuition
     Do as directed in question. Find the sum for all the possible subarrays and update 
     the ans as and when we get a better subarray that fulfill the requirements sum ≥ s.
     
     Algorithm
     Initialize ans=INT_MAX
     Iterate the array from left to right using i:
     Iterate from the current element to the end of vector using j:
     Find the sum of elements from index i to j
     If sum is greater then s:
     Update ans=min(ans,(j−i+1))
     Start the next iith iteration, since, we got the smallest subarray with
     sum≥s starting from the current index.
    */
    public int minSubArrayLen(int s, int[] nums) {
        if(nums == null || nums.length == 0) {
            return 0;
        }
        int result = Integer.MAX_VALUE;
        for(int i = 0; i < nums.length; i++) {
            for(int j = i; j < nums.length; j++) {
                int sum = 0;
                for(int k = i; k <= j; k++) {
                    sum += nums[k];
                }
                if(sum >= s) {
                    result = Math.min(result, j - i + 1);
                    // Must break here to get the minimum length
                    // as from now on sum always >= s, and the
                    // first case means minimum length
                    break;
                }
            }
        }
        // Return 0 if not able to reach target >= s
        // e.g s = 3, nums = {1,1}
        return result == Integer.MAX_VALUE ? 0 : result;
    }
}


// Solution 2:

// Solution 3:

// Solution 4: Two Points
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
