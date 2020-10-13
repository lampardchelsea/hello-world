/**
 Refer to
 https://leetcode.com/problems/partition-to-k-equal-sum-subsets/
 Given an array of integers nums and a positive integer k, find whether it's possible to divide this array into k non-empty subsets whose sums are all equal.

 Example 1:
 Input: nums = [4, 3, 2, 3, 5, 2, 1], k = 4
 Output: True
 Explanation: It's possible to divide it into 4 subsets (5), (1, 4), (2,3), (2,3) with equal sums.
 
 Note:
 1 <= k <= len(nums) <= 16.
 0 < nums[i] < 10000.
*/

// Solution 1: DFS + backtracking
// Refer to
// https://github.com/lampardchelsea/hello-world/blob/master/leetcode/DFS_BackTracking/MatchsticksToSquare.java
// Runtime: 2173 ms, faster than 5.03% of Java online submissions for Partition to K Equal Sum Subsets.
// Memory Usage: 36.2 MB, less than 7.06% of Java online submissions for Partition to K Equal Sum Subsets.
class Solution {
    public boolean canPartitionKSubsets(int[] nums, int k) {
        int sum = 0;
        for(int i = 0; i < nums.length; i++) {
            sum += nums[i];
        }
        if(sum % k != 0) {
            return false;
        }
        return helper(0, nums, new int[k], sum / k);
    }
    
    private boolean helper(int index, int[] nums, int[] sums, int target) {
        if(index == nums.length) {
            int temp = sums[0];
            for(int i = 1; i < sums.length; i++) {
                if(sums[i] != temp) {
                    return false;
                }
            }
            return true;
        }
        for(int i = 0; i < sums.length; i++) {
            if(sums[i] + nums[index] <= target) {
                sums[i] += nums[index];
                if(helper(index + 1, nums, sums, target)) {
                    return true;
                }
                sums[i] -= nums[index];
            }
        }
        return false;
    }
}

// Solution 2: Optimization by sorting the array first
// Refer to
// 1. 473. Matchsticks to Square is special case for 698. Partition to K Equal Sum Subsets
// https://github.com/lampardchelsea/hello-world/blob/master/leetcode/DFS_BackTracking/MatchsticksToSquare.java
/**
 Sorting the input array DESC will make the DFS process run much faster. Reason behind this is we always try 
 to put the next matchstick in the first subset. If there is no solution, trying a longer matchstick first 
 will get to negative conclusion earlier. Following is the updated code. Runtime is improved from more than 
 1000ms to around 40ms. A big improvement.
 We can either reverse array as one more function or we can traverse indexes from end to start index
 Here we choose the 2nd way since it will take even less time and boost the time from 2173ms to 22ms
*/
// 2. Java beat 100%
// https://leetcode.com/problems/partition-to-k-equal-sum-subsets/discuss/108741/Solution-with-Reference
// 3. We need to use if(index == -1) instead of (index == 0) for the terminate case
// https://leetcode.com/problems/matchsticks-to-square/discuss/95729/Java-DFS-Solution-with-Explanation/100200
// Runtime: 22 ms, faster than 30.64% of Java online submissions for Partition to K Equal Sum Subsets.
// Memory Usage: 36.4 MB, less than 7.06% of Java online submissions for Partition to K Equal Sum Subsets.
class Solution {
    public boolean canPartitionKSubsets(int[] nums, int k) {
        int sum = 0;
        for(int i = 0; i < nums.length; i++) {
            sum += nums[i];
        }
        if(sum % k != 0) {
            return false;
        }
        Arrays.sort(nums);
        return helper(nums.length - 1, nums, new int[k], sum / k);
    }
    
    private boolean helper(int index, int[] nums, int[] sums, int target) {
        // Must index == -1 instead of index == 0, test case: [4,3,2,3,5,2,1] and k = 4
        if(index == -1) {
            int temp = sums[0];
            for(int i = 1; i < sums.length; i++) {
                if(sums[i] != temp) {
                    return false;
                }
            }
            return true;
        }
        for(int i = 0; i < sums.length; i++) {
            if(sums[i] + nums[index] <= target) {
                sums[i] += nums[index];
                if(helper(index - 1, nums, sums, target)) {
                    return true;
                }
                sums[i] -= nums[index];
            }
        }
        return false;
    }
}
