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
// Solution 1:
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

// Solution 2: Change 2D array DP to 1D array DP
// Refer to
// https://leetcode.com/problems/partition-equal-subset-sum/discuss/90627/Java-Solution-similar-to-backpack-problem-Easy-to-understand
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
        boolean[] dp = new boolean[target + 1];
        dp[0] = true;
        /**
         Note 1: Why we can downgrade 2D array DP to 1D array DP ?
         Refer to
         https://leetcode.com/problems/partition-equal-subset-sum/discuss/90592/01-knapsack-detailed-explanation/188673
         In my understanding the transition to 1d solution can 
         happen here because 2d solution always depends on values 
         from row above and it doesn't depend on other rows.
         As a result, we can only fill up one array on every 
         iteration over 'nums' and then reuse it on the next 
         iteration (as if we would be moving to next row in 2d table).
         
         Note 2: Why the inner loop can not start from j = 1 ?
         Refer to
         https://leetcode.com/problems/partition-equal-subset-sum/discuss/90592/01-knapsack-detailed-explanation/140416
         Because dp[j] = dp[j] || dp[j - nums[i - 1]] uses smaller index value dp[j - nums[i]].
         When the current iteration begins, the values in dp[] are the result of previous iteration.
         Current iteration's result should only depend on the values of previous iteration.
         If you iterate from j = 1, then dp[j - nums[i - 1]] will be overwritten before you use it, 
         which is wrong. You can avoid this problem by iterating from j = target
        */
        for(int i = 1; i <= nums.length; i++) {
            for(int j = target; j > 0; j--) {
                if(j >= nums[i - 1]) {
                    dp[j] = dp[j] || dp[j - nums[i - 1]];
                }
            }
        }
        return dp[target];
    }
}
