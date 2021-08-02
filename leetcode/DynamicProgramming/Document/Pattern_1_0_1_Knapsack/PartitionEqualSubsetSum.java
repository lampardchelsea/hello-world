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

// Solution 3: Bottom Up DP (2D-DP)
// Refer to
// https://leetcode.com/problems/partition-equal-subset-sum/discuss/90592/01-knapsack-detailed-explanation
/**
 This problem is essentially let us to find whether there are several numbers in a set which are 
 able to sum to a specific value (in this problem, the value is sum/2).
Actually, this is a 0/1 knapsack problem, for each number, we can pick it or not. Let us assume 
dp[i][j] means whether the specific sum j can be gotten from the first i numbers. If we can pick 
such a series of numbers from 0-i whose sum is j, dp[i][j] is true, otherwise it is false.
Base case: dp[0][0] is true; (zero number consists of sum 0 is true)
Transition function: For each number, if we don't pick it, dp[i][j] = dp[i-1][j], which means 
if the first i-1 elements has made it to j, dp[i][j] would also make it to j (we can just ignore 
nums[i]). If we pick nums[i]. dp[i][j] = dp[i-1][j-nums[i]], which represents that j is composed 
of the current value nums[i] and the remaining composed of other previous numbers. Thus, the 
transition function is dp[i][j] = dp[i-1][j] || dp[i-1][j-nums[i]]
*/
// Style 1:
class Solution {
    public boolean canPartition(int[] nums) {
        int sum = 0;
        for(int i = 0; i < nums.length; i++) {
            sum += nums[i];
        }
        // Caution: Do not forget check sum must be even
        if(sum % 2 == 1) {
            return false;
        }
        int target = sum / 2;
        int len = nums.length;
        boolean[][] dp = new boolean[len + 1][target + 1];
        dp[0][0] = true;
        // For target as 0(sum/2), given any numbers in nums 
        // array we just need to not pick them up will make it
        for(int i = 1; i < len + 1; i++) {
            dp[i][0] = true; 
        }
        // For len as 0(no number in nums array pick up), 
        // given any target, we can not make it
        for(int j = 1; j < target + 1; j++) {
            dp[0][j] = false;
        }
        // Below two conditions can be written as dp[i][j] = (dp[i-1][j] || dp[i-1][j-nums[i-1]]); 
        // but since j - nums[i - 1] only works when j >= nums[i - 1], we have to split the formula 
        // into two conditions
        for(int i = 1; i < len + 1; i++) {
            for(int j = 1; j < target + 1; j++) {
                // If we exclude the current number from the set, which is we just use the value 
                // from the cell above which is dp[i-1][j]                
                dp[i][j] = dp[i - 1][j];
                // If we include the current value in the previous calculated set which is 
                // dp[i-1][j-nums[i-1]]. Current value will be nums[i-1] because "i" starts 
                // from 1 but the array is a 0-based index
                if(j >= nums[i - 1]) {
                    dp[i][j] = (dp[i][j] || dp[i - 1][j - nums[i - 1]]);    
                }
            }
        }
        return dp[len][target];
    }
}

// Style 2:
class Solution {
    public boolean canPartition(int[] nums) {
        int n = nums.length;
        int sum = 0;
        for(int num : nums) {
            sum += num;
        }
        if(sum % 2 != 0) {
            return false;
        }
        boolean[][] dp = new boolean[n + 1][sum / 2 + 1];
        for(int i = 0; i <= n; i++) {
            dp[i][0] = true;
        }
        for(int i = 1; i <= sum / 2; i++) {
            dp[0][i] = false;
        }
        for(int i = 1; i <= n; i++) {
            for(int j = 1; j <= sum / 2; j++) {
                dp[i][j] = dp[i - 1][j] || (j - nums[i - 1] >= 0 && dp[i - 1][j - nums[i - 1]]);
            }
        }
        return dp[n][sum / 2];
    }
}

// Solution 4: Bottom Up DP (1D-DP)
// Refer to
// 
