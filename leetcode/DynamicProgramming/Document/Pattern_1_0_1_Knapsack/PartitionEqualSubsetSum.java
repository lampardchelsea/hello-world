/**
 Refer to
 https://leetcode.com/problems/partition-equal-subset-sum/
 Given a non-empty array containing only positive integers, find if the array can be partitioned 
 into two subsets such that the sum of elements in both subsets is equal.
Note:
Each of the array element will not exceed 100.
The array size will not exceed 200.
 
Example 1:
Input: [1, 5, 11, 5]
Output: true
Explanation: The array can be partitioned as [1, 5, 5] and [11].
 
Example 2:
Input: [1, 2, 3, 5]
Output: false
Explanation: The array cannot be partitioned into equal sum subsets.
*/

// Solution 1: Native DFS (TLE)
// Refer to
// https://leetcode.com/problems/partition-equal-subset-sum/discuss/725791/JAVA-Recursion-to-Memoization-to-DP
// Style 1
class Solution {
    public boolean canPartition(int[] nums) {
        int sum = 0;
        for(int num : nums) {
            sum += num;
        }
        if(sum % 2 != 0) {
            return false;
        }
        return helper(nums, sum / 2, 0);
    }
    
    private boolean helper(int[] nums, int target, int index) {
        if(target == 0) {
            return true;
        }
        if(target != 0 && index >= nums.length) {
            return false;
        }
        // Pick up current number
        if(target >= nums[index]) {
            if(helper(nums, target - nums[index], index + 1)) {
                return true;
            }
        }
        // Not pick up current number
        if(helper(nums, target, index + 1)) {
            return true;
        }
        return false;
    }
}

// Style 2: Strictly follow the standard template from
// http://www.mathcs.emory.edu/~cheung/Courses/253/Syllabus/DynProg/knapsack3.html
class Solution {
    public boolean canPartition(int[] nums) {
        int sum = 0;
        for(int num : nums) {
            sum += num;
        }
        if(sum % 2 != 0) {
            return false;
        }
        return helper(nums, sum / 2, 0);
    }
    
    private boolean helper(int[] nums, int target, int index) {
        if(target == 0) {
            return true;
        }
        if(target != 0 && index >= nums.length) {
            return false;
        }
        if(nums[index] > target) {
            // Not able to pick up since nums[index] > target
            return helper(nums, target, index + 1);
        } else {
            // Able to pick up but we have two options as either pick up OR not pick up
            boolean pickUp = helper(nums, target - nums[index], index + 1);
            boolean notPickUp = helper(nums, target, index + 1);
            return pickUp || notPickUp;
        }
    }
}

// Solution 2: Top Down DP Memoization (2D-DP)
// Refer to
// https://leetcode.com/problems/partition-equal-subset-sum/discuss/725791/JAVA-Recursion-to-Memoization-to-DP
class Solution {
    public boolean canPartition(int[] nums) {
        int sum = 0;
        for(int num : nums) {
            sum += num;
        }
        if(sum % 2 != 0) {
            return false;
        }
        // Must initialize memo as 2D-DP, because if only use 'index'
        // e.g (memo[index]) to track is not enough, because the boolean
        // result for each index may need to represent multiple potential
        // target is possible or not to come up with subsets of numbers
        // used till current index, but its impossible. So we have to
        // introduce another dimension 'target' to track on the potential
        // of subsets of numbers used till certain index to reach the target,
        // and target range between 0 to sum / 2
        Boolean[][] memo = new Boolean[nums.length][sum / 2 + 1];
        return helper(nums, sum / 2, 0, memo);
    }
    
    private boolean helper(int[] nums, int target, int index, Boolean[][] memo) {
        if(target == 0) {
            return true;
        }
        if(target != 0 && index >= nums.length) {
            return false;
        }
        if(memo[index][target] != null) {
            return memo[index][target];
        }
        boolean result = false;
        if(nums[index] > target) {
            // Not able to pick up since nums[index] > target
            result = helper(nums, target, index + 1, memo);
        } else {
            // Able to pick up but we have two options as either pick up OR not pick up
            boolean pickUp = helper(nums, target - nums[index], index + 1, memo);
            boolean notPickUp = helper(nums, target, index + 1, memo);
            result = pickUp || notPickUp;
        }
        memo[index][target] = result;
        return result;
    }
}
