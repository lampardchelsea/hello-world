/**
 Refer to
 https://leetcode.com/problems/house-robber-ii/
 You are a professional robber planning to rob houses along a street. Each house has a certain amount of money stashed. 
 All houses at this place are arranged in a circle. That means the first house is the neighbor of the last one. 
 Meanwhile, adjacent houses have security system connected and it will automatically contact the police if two 
 adjacent houses were broken into on the same night.

Given a list of non-negative integers representing the amount of money of each house, determine the maximum amount 
of money you can rob tonight without alerting the police.

Example 1:

Input: [2,3,2]
Output: 3
Explanation: You cannot rob house 1 (money = 2) and then rob house 3 (money = 2),
             because they are adjacent houses.
Example 2:

Input: [1,2,3,1]
Output: 4
Explanation: Rob house 1 (money = 1) and then rob house 3 (money = 3).
             Total amount you can rob = 1 + 3 = 4.
*/
// Solution 1: Native DFS (TLE)
// Refer to
// https://massivealgorithms.blogspot.com/2015/05/leetcode-213-house-robber-ii-csdnnet.html
/**
 这道题是之前那道 House Robber 的拓展，现在房子排成了一个圆圈，则如果抢了第一家，就不能抢最后一家，因为首尾相连了，
 所以第一家和最后一家只能抢其中的一家，或者都不抢，那我们这里变通一下，如果我们把第一家和最后一家分别去掉，各算一遍
 能抢的最大值，然后比较两个值取其中较大的一个即为所求。那我们只需参考之前的 House Robber 中的解题方法，然后调用两边取较大值
 Suppose there are n houses, since house 0 and n - 1 are now neighbors, we cannot rob them together and thus 
 the solution is now the maximum of
 Rob houses 0 to n - 2;
 Rob houses 1 to n - 1.
*/
class Solution {
    public int rob(int[] nums) {
        if(nums.length == 1) {
            return nums[0];  
        }
        // Rob houses 0 to n - 2        
        int method1 = helper(nums, 0, nums.length - 2, nums.length - 2);
        // Rob houses 1 to n - 1
        int method2 = helper(nums, 1, nums.length - 1, nums.length - 1);
        return Math.max(method1, method2);
    }
    
    // Similar process as House Robber I
    private int helper(int[] nums, int m, int n, int index) {
        if(index < m || index > n) {
            return 0;
        }
        int notChooseCurrentRoom = helper(nums, m, n, index - 1);
        int chooseCurrentRoom = helper(nums, m, n, index - 2) + nums[index];
        return Math.max(notChooseCurrentRoom, chooseCurrentRoom);
    }
}

// Solution 2:
// Refer to
// 
