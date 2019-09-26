/**
 Refer to
 https://www.educative.io/courses/grokking-dynamic-programming-patterns-for-coding-interviews/3jEPRo5PDvx
 Given a set of positive numbers, find if we can partition it into two subsets such that the sum of 
 elements in both the subsets is equal.
 Example 1:
 Input: {1, 2, 3, 4}
 Output: True
 Explanation: The given set can be partitioned into two subsets with equal sum: {1, 4} & {2, 3}
 Example 2:
 Input: {1, 1, 3, 4, 7}
 Output: True
 Explanation: The given set can be partitioned into two subsets with equal sum: {1, 3, 4} & {1, 7}
 Example 3:
 Input: {2, 3, 4, 6}
 Output: False
 Explanation: The given set cannot be partitioned into two subsets with equal sum.
*/

// Solution 1: Native DFS (TLE)
/**
 Basic Solution
 This problem follows the 0/1 Knapsack pattern. A basic brute-force solution could be to try all combinations 
 of partitioning the given numbers into two sets to see if any pair of sets has an equal sum. 
 Assume if S represents the total sum of all the given numbers, then the two equal subsets must have a sum 
 equal to S/2. This essentially transforms our problem to: "Find a subset of the given numbers that has 
 a total sum of S/2".
*/
class Solution {
    public boolean canPartition(int[] nums) {
        if(nums.length == 0) {
            return false;
        }
        int sum = 0;
        for(int i = 0; i < nums.length; i++) {
            sum += nums[i]; 
        }         
        // if 'sum' is a an odd number, we can't have two subsets with equal sum
        if(sum % 2 != 0) {
            return false;  
        }
        return helper(nums, sum/2, 0);
    }
    
    private boolean helper(int[] nums, int target, int index) {
        if(target == 0) {
            return true;
        }
        if(target != 0 && index >= nums.length) {
            return false;
        }
        if(nums[index] <= target) {
            if(helper(nums, target - nums[index], index + 1)) {
                return true;
            }
        }
        if(helper(nums, target, index + 1)) {
            return true;
        }
        return false;
    }
}

// Solution 2: Top-down Dynamic Programming with Memoization
/**
 Since we need to store the results for every subset and for every possible sum, therefore we 
 will be using a two-dimensional array to store the results of the solved sub-problems. The 
 first dimension of the array will represent different subsets and the second dimension will 
 represent different ‘target’ that we can calculate from each subset. These two dimensions of 
 the array can also be inferred from the two changing values (target and index) in our 
 recursive function helper().
 The above algorithm has time and space complexity of O(N*S), where ‘N’ represents total 
 numbers and ‘S’ is the total sum of all the numbers.
 Runtime: 3 ms, faster than 86.55% of Java online submissions for Partition Equal Subset Sum.
 Memory Usage: 38 MB, less than 50.79% of Java online submissions for Partition Equal Subset Sum.
*/
class Solution {
    public boolean canPartition(int[] nums) {
        if(nums.length == 0) {
            return false;
        }
        int sum = 0;
        for(int i = 0; i < nums.length; i++) {
            sum += nums[i]; 
        }         
        // if 'sum' is a an odd number, we can't have two subsets with equal sum
        if(sum % 2 != 0) {
            return false;  
        }
        Boolean[][] dp = new Boolean[1 + nums.length][1 + sum / 2];
        return helper(nums, sum/2, 0, dp);
    }
    
    private boolean helper(int[] nums, int target, int index, Boolean[][] dp) {
        if(target == 0) {
            return true;
        }
        if(target != 0 && index >= nums.length) {
            return false;
        }
        if(dp[index][target] != null) {
            return dp[index][target];
        }
        if(nums[index] <= target) {
            if(helper(nums, target - nums[index], index + 1, dp)) {
                dp[index][target] = true;
                return true;
            }
        }
        if(helper(nums, target, index + 1, dp)) {
            dp[index][target] = true;
            return true;
        }
        dp[index][target] = false;
        return false;
    }
}

// Solution 3: Bottom-up Dynamic Programming
/**
 
*/
