/**
 This issue similar to leetcode
 Combination Sum IV
 This Combination Sum question a little different than I, II, III, since it only requires counting the numbers of result,
 not like I, II, III must use backtracking DFS to getting combination sets, so we can use DP (top down and bottom up) here
 Refer to
 https://leetcode.com/problems/combination-sum-iv/
 Given an integer array with all positive numbers and no duplicates, find the number of possible combinations 
 that add up to a positive integer target.

Example:

nums = [1, 2, 3]
target = 4

The possible combination ways are:
(1, 1, 1, 1)
(1, 1, 2)
(1, 2, 1)
(1, 3)
(2, 1, 1)
(2, 2)
(3, 1)

Note that different sequences are counted as different combinations.
Therefore the output is 7.
*/

// Solution 1: Native DFS (TLE)
class Solution {
    public int combinationSum4(int[] nums, int target) {
        if(nums == null || nums.length == 0) {
            return 0;
        }
        return helper(nums, target);
    }
    
    private int helper(int[] nums, int target) {
        if(target == 0) {
            return 1;
        }
        int result = 0;
        for(int i = 0; i < nums.length; i++) {
            if(target >= nums[i]) {
                result += helper(nums, target - nums[i]);                
            }
        }
        return result;
    }
}

// Solution 2: 2D array top down DP (DFS + Memoization)
/**
 Since from given example we found sequence a and b are consider as different combination (but not permutation),
 we always able to go back to consider previous choice, e.g when you already loop to 2, you can still consider
 1 as choice, that's how we build [2,1,1], so each recursive time need to loop start at index = 0 to make sure
 this happen, here we add additional parameter as 'index' to declare this part, but since always need to start
 from index = 0, this parameter is not necessary, hence no need 2D array as memo as remove index dimemsion. 
 => Check Solution 3
 nums = [1, 2, 3]
 target = 4
 The possible combination ways are:
 (1, 1, 1, 1)
 (1, 1, 2) -> sequence a
 (1, 2, 1)
 (1, 3) 
 (2, 1, 1) -> sequence b
 (2, 2)
 (3, 1)
*/
class Solution {
    public int combinationSum4(int[] nums, int target) {
        if(nums == null || nums.length == 0) {
            return 0;
        }
        Integer[][] dp = new Integer[nums.length][1 + target];
        return helper(nums, target, 0, dp);
    }
    
    private int helper(int[] nums, int target, int index, Integer[][] dp) {
        if(dp[index][target] != null) {
            return dp[index][target];
        }
        if(target == 0) {
            return 1;
        }
        int result = 0;
        for(int i = 0; i < nums.length; i++) {
            if(target >= nums[i]) {
                result += helper(nums, target - nums[i], 0, dp);                
            }
        }
        dp[index][target] = result;
        return result;
    }
}

// Solution 3: 1D array top down DP (DFS + Memoization) without index as dimension
class Solution {
    public int combinationSum4(int[] nums, int target) {
        if(nums == null || nums.length == 0) {
            return 0;
        }
        Integer[] dp = new Integer[1 + target];
        // Refer to
        // https://leetcode.com/problems/combination-sum-iv/discuss/85036/1ms-Java-DP-Solution-with-Detailed-Explanation/89680
        // Why dp[0] = 1 ? for{1,2,3},if target = 0,I think the answer is 0.
        // dp[0] is actually target = 1. Note that dp is initialized as
        // new Integer[target + 1].
        // Also: given an integer array with all positive numbers
        // add up to a positive integer target, so target won't be 0.
        dp[0] = 1;
        return helper(nums, target, dp);
    }
    
    private int helper(int[] nums, int target, Integer[] dp) {
        if(dp[target] != null) {
            return dp[target];
        }
        if(target == 0) {
            return 1;
        }
        int result = 0;
        for(int i = 0; i < nums.length; i++) {
            if(target >= nums[i]) {
                result += helper(nums, target - nums[i], dp);                
            }
        }
        dp[target] = result;
        return result;
    }
}
