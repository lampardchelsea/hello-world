/**
 Refer to
 https://leetcode.com/problems/target-sum/
 You are given a list of non-negative integers, a1, a2, ..., an, and a target, S. 
 Now you have 2 symbols + and -. For each integer, you should choose one from + and - as its new symbol.

Find out how many ways to assign symbols to make sum of integers equal to target S.

Example 1:
Input: nums is [1, 1, 1, 1, 1], S is 3. 
Output: 5

Explanation: 
-1+1+1+1+1 = 3
+1-1+1+1+1 = 3
+1+1-1+1+1 = 3
+1+1+1-1+1 = 3
+1+1+1+1-1 = 3

There are 5 ways to assign symbols to make the sum of nums be target 3.

Note:
The length of the given array is positive and will not exceed 20.
The sum of elements in the given array will not exceed 1000.
Your output answer is guaranteed to be fitted in a 32-bit integer.
*/
// Solution 1: 2D array DP with Math analysis (similar to 416. Partition Equal Subset Sum)
// Refer to
// How to handle subsum to target value ?
// https://leetcode.com/problems/partition-equal-subset-sum/
// Math analysis and transfer question to subsum to target value
// https://leetcode.com/problems/target-sum/discuss/97334/Java-(15-ms)-C%2B%2B-(3-ms)-O(ns)-iterative-DP-solution-using-subset-sum-with-explanation
/**
 The recursive solution is very slow, because its runtime is exponential
 
The original problem statement is equivalent to:
Find a subset of nums that need to be positive, and the rest of them negative, such that the sum is equal to target

Let P be the positive subset and N be the negative subset
For example:
Given nums = [1, 2, 3, 4, 5] and target = 3 then one possible solution is +1-2+3-4+5 = 3
Here positive subset is P = [1, 3, 5] and negative subset is N = [2, 4]

Then let's see how this can be converted to a subset sum problem:

                  sum(P) - sum(N) = target
sum(P) + sum(N) + sum(P) - sum(N) = target + sum(P) + sum(N)
                       2 * sum(P) = target + sum(nums)
So the original problem has been converted to a subset sum problem as follows:
Find a subset P of nums such that sum(P) = (target + sum(nums)) / 2

Note that the above formula has proved that target + sum(nums) must be even
We can use that fact to quickly identify inputs that do not have a solution (Thanks to @BrunoDeNadaiSarnaglia for the suggestion)
For detailed explanation on how to solve subset sum problem, you may refer to Partition Equal Subset Sum
*/

// Some tips refer to
// Why cannot pass [0,0,0,0,0,0,0,1] and 1 testcase ?
// https://leetcode.com/problems/target-sum/discuss/97334/Java-(15-ms)-C++-(3-ms)-O(ns)-iterative-DP-solution-using-subset-sum-with-explanation/101863
// Answer
// https://leetcode.com/problems/target-sum/discuss/97334/Java-(15-ms)-C++-(3-ms)-O(ns)-iterative-DP-solution-using-subset-sum-with-explanation/101842
// but range of j should be [0, target], the following code got accepted.
class Solution {
    public int findTargetSumWays(int[] nums, int S) {
        int sum = 0;
        for(int i = 0; i < nums.length; i++) {
            sum += nums[i];
        }
        if(sum < S || (sum + S) % 2 == 1) {
            return 0;
        }
        return subsetSum(nums, (sum + S) / 2);
    }
    
    private int subsetSum(int[] nums, int target) {
        int len = nums.length;
        // dp[i][j] means number of ways to get sum j with first i elements from nums.
        int[][] dp = new int[len + 1][target + 1];
        dp[0][0] = 1;
        for(int i = 1; i < len + 1; i++) {
            dp[i][0] = 1;
        }
        // for(int j = 1; j < target + 1; j++) {
        //     dp[0][j] = 0;
        // }
        for(int i = 1; i < len + 1; i++) {
            for(int j = 0; j < target + 1; j++) { // Here j range start from 0 not 1
                dp[i][j] = dp[i - 1][j];
                if(j >= nums[i - 1]) {
                    dp[i][j] += dp[i - 1][j - nums[i - 1]];    
                }
            }
        }
        return dp[len][target];
    }
}

