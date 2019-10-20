/**
 Refer to
 https://leetcode.com/problems/house-robber/
 You are a professional robber planning to rob houses along a street. Each house has a certain amount 
 of money stashed, the only constraint stopping you from robbing each of them is that adjacent houses 
 have security system connected and it will automatically contact the police if two adjacent houses 
 were broken into on the same night.

Given a list of non-negative integers representing the amount of money of each house, determine the 
maximum amount of money you can rob tonight without alerting the police.

Example 1:
Input: [1,2,3,1]
Output: 4
Explanation: Rob house 1 (money = 1) and then rob house 3 (money = 3).
             Total amount you can rob = 1 + 3 = 4.
Example 2:
Input: [2,7,9,3,1]
Output: 12
Explanation: Rob house 1 (money = 2), rob house 3 (money = 9) and rob house 5 (money = 1).
             Total amount you can rob = 2 + 9 + 1 = 12.
*/

// Solution 1: Native DFS (TLE)
// Refer to
// https://leetcode.com/problems/house-robber/discuss/156523/From-good-to-great.-How-to-approach-most-of-DP-problems.
/**
 There is some frustration when people publish their perfect fine-grained algorithms without sharing any information 
 abut how they were derived. This is an attempt to change the situation. There is not much more explanation but it's 
 rather an example of higher level improvements. Converting a solution to the next step shouldn't be as hard as 
 attempting to come up with perfect algorithm at first attempt.

This particular problem and most of others can be approached using the following sequence:
Find recursive relation
Recursive (top-down)
Recursive + memo (top-down)
Iterative + memo (bottom-up)
Iterative + N variables (bottom-up)

Step 1. Figure out recursive relation.
A robber has 2 options: a) rob current house i; b) don't rob current house.
If an option "a" is selected it means she can't rob previous i-1 house but can safely proceed to the one before 
previous i-2 and gets all cumulative loot that follows.
If an option "b" is selected the robber gets all the possible loot from robbery of i-1 and all the following buildings.
So it boils down to calculating what is more profitable:

robbery of current house + loot from houses before the previous
loot from the previous house robbery and any loot captured before that
rob(i) = Math.max(rob(i - 2) + currentHouseValue, rob(i - 1))
*/
class Solution {
    public int rob(int[] nums) {
        return helper(nums, nums.length - 1);
    }
    
    private int helper(int[] nums, int index) {
        if(index < 0) {
            return 0;
        }
        int notChooseCurrentRoom = helper(nums, index - 1);
        int chooseCurrentRoom = helper(nums, index - 2) + nums[index];
        return Math.max(notChooseCurrentRoom, chooseCurrentRoom);
    }
}

// Solution 2: Top down DP (DFS + Memoization)
// Much better, this should run in O(n) time. Space complexity is O(n) as well, 
// because of the recursion stack, let's try to get rid of it.
// Runtime: 0 ms, faster than 100.00% of Java online submissions for House Robber.
// Memory Usage: 34.5 MB, less than 100.00% of Java online submissions for House Robber.
class Solution {
    public int rob(int[] nums) {
        Integer[] memo = new Integer[nums.length + 1];
        return helper(nums, nums.length - 1, memo);
    }
    
    private int helper(int[] nums, int index, Integer[] memo) {
        if(index < 0) {
            return 0;
        }
        if(memo[index] != null) {
            return memo[index];
        }
        int notChooseCurrentRoom = helper(nums, index - 1, memo);
        int chooseCurrentRoom = helper(nums, index - 2, memo) + nums[index];
        int result = Math.max(notChooseCurrentRoom, chooseCurrentRoom);
        memo[index] = result;
        return result;
    }
}

// Solution 3: Bottom up DP
// Refer to
// https://leetcode.com/problems/house-robber/discuss/164056/Python-or-tm
// https://leetcode.com/problems/house-robber/discuss/156523/From-good-to-great.-How-to-approach-most-of-DP-problems.
class Solution {
    public int rob(int[] nums) {
        if(nums == null || nums.length == 0) {
            return 0;
        }
        if(nums.length == 1) {
            return nums[0];
        }
        // dp[i] means first i house max profit
        int[] dp = new int[nums.length + 1];
        dp[0] = 0;
        dp[1] = nums[0];
        for(int i = 2; i <= nums.length; i++) {
            dp[i] = Math.max(dp[i - 2] + nums[i - 1], dp[i - 1]);
        }
        return dp[nums.length];
    }
}

// Solution 5: Improve space bottom up DP
// Refer to
// https://leetcode.com/problems/house-robber/discuss/156523/From-good-to-great.-How-to-approach-most-of-DP-problems.
// We can notice that in the previous step we use only memo[i] and memo[i-1], so going just 2 steps back. 
// We can hold them in 2 variables instead. This optimization is met in Fibonacci sequence creation and 
// some other problems [to paste links].
class Solution {
    public int rob(int[] nums) {
        if(nums == null || nums.length == 0) {
            return 0;
        }
        if(nums.length == 1) {
            return nums[0];
        }
        // dp[i] means first i house max profit
        int[] dp = new int[nums.length + 1];
        int a = 0;
        int b = nums[0];
        int c = 0;
        for(int i = 2; i <= nums.length; i++) {
            c = Math.max(a + nums[i - 1], b);
            a = b;
            b = c;
        }
        return c;
    }
}
