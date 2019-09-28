/**
 Refer to
 https://www.geeksforgeeks.org/subset-sum-problem-dp-25/
 Given a set of non-negative integers, and a value sum, determine if there 
 is a subset of the given set with sum equal to given sum.
 Example:
 Input:  set[] = {3, 34, 4, 12, 5, 2}, sum = 9
 Output:  True  //There is a subset (4, 5) with sum 9.
*/

/**
Let isSubSetSum(int set[], int n, int sum) be the function to find 
whether there is a subset of set[] with sum equal to sum. n is the 
number of elements in set[].
The isSubsetSum problem can be divided into two subproblems
(a) Include the last element, recur for n = n-1, sum = sum – set[n-1]
(b) Exclude the last element, recur for n = n-1.
If any of the above the above subproblems return true, then return true.
Following is the recursive formula for isSubsetSum() problem.
isSubsetSum(set, n, sum) = isSubsetSum(set, n-1, sum) || 
                           isSubsetSum(set, n-1, sum-set[n-1])
Base Cases:
isSubsetSum(set, n, sum) = false, if sum > 0 and n == 0
isSubsetSum(set, n, sum) = true, if sum == 0
*/
// Solution 1: Native DFS
class Solution {
    public boolean isSubsetSum(int[] nums, int target) {
        if(nums == null || nums.length == 0) {
            return false;
        }
        return helper(nums, target, 0);
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
class Solution {
    public boolean isSubsetSum(int[] nums, int target) {
        if(nums == null || nums.length == 0) {
            return false;
        }
        // We use Boolean because it requries return as true/false
        // not a integer value 0 can resolve, need Boolean to check
        // as null or true/false
        Boolean[][] dp = new Boolean[nums.length][1 + target];
        return helper(nums, target, 0, dp);
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
            if(helper(nums, target - nums[index], index + 1)) {
                dp[index][target] = true;
                return true;
            }
        }
        if(helper(nums, target, index + 1)) {
            dp[index][target] = true;
            return true;
        }
        dp[index][target] = false;
        return false;
    } 
}

// Solution 3: Bottom-up Dynamic Programming
class Solution {
    public boolean isSubsetSum(int[] nums, int target) {
        if(nums == null || nums.length == 0) {
            return false;
        }
        boolean[][] dp = new boolean[nums.length][1 + target];
        // populate the sum=0 columns, as we can always for '0' sum with an empty set
        for(int i = 0; i < nums.length; i++) {
            dp[i][0] = true;
        }
        // with only one number, we can form a subset only when the required sum 
        // is equal to its value
        for(int i = 1; i <= target; i++) {
            dp[0][i] = (nums[0] == i ? true : false);
        }
        // process all subsets for all sums
        for(int i = 1; i < nums.length; i++) {
            for(int j = 1; j <= target; j++) {
                // if we can get the sum 'j' without the number at index 'i'
                if(dp[i - 1][j]) {
                    dp[i][j] = dp[i - 1][j];
                } else if(j >= nums[i]) {
                    dp[i][j] = dp[i - 1][j - nums[i]];
                }
            }
        }
        return dp[nums.length - 1][target];
    }
}
