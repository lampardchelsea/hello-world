
/**
 * Refer to
 * https://leetcode.com/problems/house-robber-ii/description/
 * http://www.lintcode.com/en/problem/house-robber-ii/
 * Note: This is an extension of House Robber.
   After robbing those houses on that street, the thief has found himself a new place for 
   his thievery so that he will not get too much attention. This time, all houses at this 
   place are arranged in a circle. That means the first house is the neighbor of the last 
   one. Meanwhile, the security system for these houses remain the same as for those in 
   the previous street.
   Given a list of non-negative integers representing the amount of money of each house, 
   determine the maximum amount of money you can rob tonight without alerting the police.
 *
 * Solution
 * http://blog.csdn.net/xudli/article/details/45886721
 * http://www.cnblogs.com/grandyang/p/4518674.html
 * https://www.jiuzhang.com/solution/house-robber-ii/
 * https://discuss.leetcode.com/topic/14375/simple-ac-solution-in-java-in-o-n-with-explanation
 * Now the question is how to rob a circular row of houses. It is a bit complicated to solve like 
   the simpler question. It is because in the simpler question whether to rob num[lo] is entirely 
   our choice. But, it is now constrained by whether num[hi] is robbed.
   However, since we already have a nice solution to the simpler problem. We do not want to 
   throw it away. Then, it becomes how can we reduce this problem to the simpler one. Actually, 
   extending from the logic that if house i is not robbed, then you are free to choose whether 
   to rob house i + 1, you can break the circle by assuming a house is not robbed.
   For example, 1 -> 2 -> 3 -> 1 becomes 2 -> 3 if 1 is not robbed.
   Since every house is either robbed or not robbed and at least half of the houses are not robbed, 
   the solution is simply the larger of two cases with consecutive houses, i.e. house i not robbed, 
   break the circle, solve it, or house i + 1 not robbed. Hence, the following solution. I chose 
   i = n and i + 1 = 0 for simpler coding. But, you can choose whichever two consecutive ones.

    public int rob(int[] nums) {
        if (nums.length == 1) return nums[0];
        return Math.max(rob(nums, 0, nums.length - 2), rob(nums, 1, nums.length - 1));
    }
*/
// Solution 1:
/**
 这道题是之前那道House Robber 打家劫舍的拓展，现在房子排成了一个圆圈，则如果抢了第一家，就不能抢最后一家，
 因为首尾相连了，所以第一家和最后一家只能抢其中的一家，或者都不抢，那我们这里变通一下，如果我们把第一家和
 最后一家分别去掉，各算一遍能抢的最大值，然后比较两个值取其中较大的一个即为所求。那我们只需参考之前的
 House Robber 打家劫舍中的解题方法，然后调用两边取较大值
*/
class Solution {
    public int rob(int[] nums) {
        if(nums == null || nums.length == 0) {
            return 0;
        }
        if(nums.length == 1) {
            return nums[0];
        }
        // If not adding below case
        // Runtime Error Message:
        // Line 23: java.lang.ArrayIndexOutOfBoundsException: 1
        // Last executed input: [0,0]
        if(nums.length == 2) {
            return Math.max(nums[0], nums[1]);
        }
        return Math.max(robber(nums, 0, nums.length - 2), robber(nums, 1, nums.length - 1));
    }
    
    // Same as House Robber
    private int robber(int[] nums, int start, int end) {
        int n = end - start + 1;
        int[] dp = new int[n];
        dp[0] = nums[start];
        dp[1] = Math.max(nums[start], nums[start + 1]);
        for(int i = 2; i < n; i++) {
            // the additional one is nums[start + i], not nums[i]
            dp[i] = Math.max(dp[i - 2] + nums[start + i], dp[i - 1]);
        }
        return dp[n - 1];
    }
}
