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

// Solution 3: 2D Bottom-up Dynamic Programming
// Style 1: initialize dp as boolean[][] dp = new boolean[nums.length][1 + target];
/**
 Let’s try to populate our dp[][] array from the above solution, working in a bottom-up fashion. 
 Essentially, we want to find if we can make all possible sums with every subset. This means, 
 dp[i][s] will be ‘true’ if we can make sum ‘s’ from the first ‘i’ numbers.
 So, for each number at index ‘i’ (0 <= i < num.length) and sum ‘s’ (0 <= s <= S/2), we have two options:
 Exclude the number. In this case, we will see if we can get ‘s’ from the subset excluding this 
 number: dp[i-1][s]
 Include the number if its value is not more than ‘s’. In this case, we will see if we can find 
 a subset to get the remaining sum: dp[i-1][s-num[i]]
 If either of the two above scenarios is true, we can find a subset of numbers with a sum equal to ‘s’.
 The above algorithm has time and space complexity of O(N*S), where ‘N’ represents total 
 numbers and ‘S’ is the total sum of all the numbers.
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
        int target = sum / 2;
        boolean[][] dp = new boolean[nums.length][1 + target];
        // populate the sum=0 columns, as we can always for '0' sum with an empty set
        for(int i = 0; i < nums.length; i++) {
            dp[i][0] = true;
        }
        // with only one number, we can form a subset only when the required sum 
        // is equal to its value
        for(int i = 1; i<= target; i++) {
            dp[0][i] = (nums[0] == i ? true : false);
        }
        // process all subsets for all sums
        for(int i = 1; i < nums.length; i++) {
            for(int j = 1; j <= target; j++) {
                // if we can get the sum 'j' without the number at index 'i'
                if(dp[i - 1][j]) {
                    dp[i][j] = dp[i - 1][j];
                // else if we can find a subset to get the remaining sum
                } else if(j >= nums[i]) {
                    dp[i][j] = dp[i - 1][j - nums[i]];
                }
            }
        }
        // the bottom-right corner will have our answer.
        return dp[nums.length - 1][target];
    }
}

// Style 2: initialize boolean[][] dp = new boolean[1 + nums.length][1 + target];
// The big difference is happen on initialize first row, style 1's first row
// actually depends on index = 0 element on given array, style 2's first row
// is dummy row which has no element pick up from given array
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
        int target = sum / 2;
        boolean[][] dp = new boolean[1 + nums.length][1 + target];
        // populate the sum=0 columns, as we can always for '0' sum with an empty set
        for(int i = 0; i <= nums.length; i++) {
            dp[i][0] = true;
        }
        // For len as 0(no number in nums array pick up), 
        // given any target, we can not make it
        for(int i = 1; i <= target; i++) {
            dp[0][i] = false;
        }
        // process all subsets for all sums
        for(int i = 1; i <= nums.length; i++) {
            for(int j = 1; j <= target; j++) {
                // if we can get the sum 'j' without the number at index 'i'
                if(dp[i - 1][j]) {
                    dp[i][j] = dp[i - 1][j];
                // else if we can find a subset to get the remaining sum
                } else if(j >= nums[i - 1]) {
                    dp[i][j] = dp[i - 1][j - nums[i - 1]];
                }
            }
        }
        // the bottom-right corner will have our answer.
        return dp[nums.length][target];
    }
}


// Solution 4: 1D array Bottom-up Dynamic Programming
/**
 Explaination of why we can degrade 2D to 1D array and why we need loop backwards
 Refer to
 https://leetcode.com/problems/partition-equal-subset-sum/discuss/90592/01-knapsack-detailed-explanation
 https://leetcode.com/problems/partition-equal-subset-sum/discuss/90592/01-knapsack-detailed-explanation/241664
Yes, the magic is observation from the induction rule/recurrence relation!
For this problem, the induction rule:
If not picking nums[i - 1], then dp[i][j] = dp[i-1][j]
if picking nums[i - 1], then dp[i][j] = dp[i - 1][j - nums[i - 1]]
You can see that if you point them out in the matrix, it will be like:

			  j
	. . . . . . . . . . . . 
	. . . . . . . . . . . .  
	. . ? . . ? . . . . . .  ?(left): dp[i - 1][j - nums[i], ?(right): dp[i - 1][j]
i	. . . . . # . . . . . .  # dp[i][j]
	. . . . . . . . . . . . 
	. . . . . . . . . . . . 
	. . . . . . . . . . . . 
	. . . . . . . . . . . . 
	. . . . . . . . . . . . 
 
Optimize to O(2*n): you can see that dp[i][j] only depends on previous row, so you can 
optimize the space by only using 2 rows instead of the matrix. Let's say array1 and array2. 
Every time you finish updating array2, array1 have no value, you can copy array2 to array1 
as the previous row of the next new row.
Optimize to O(n): you can also see that, the column indices of dp[i - 1][j - nums[i]] and 
dp[i - 1][j] are <= j. The conclusion you can get is: the elements of previous row whose 
column index is > j(i.e. dp[i - 1][j + 1 : n - 1]) will not affect the update of dp[i][j] 
since we will not touch them:

			  j
	. . . . . . . . . . . . 
	. . . . . . . . . . . .  
	. . ? . . ? x x x x x x  you will not touch x for dp[i][j]
i	. . . . . # . . . . . .  # dp[i][j]
	. . . . . . . . . . . . 
	. . . . . . . . . . . . 
	. . . . . . . . . . . . 
	. . . . . . . . . . . . 
	. . . . . . . . . . . . 
 
Thus if you merge array1 and array2 to a single array array, if you update array backwards, 
all dependencies are not touched!

    (n represents new value, i.e. updated)
	. . ? . . ? n n n n n n n
              #  
However if you update forwards, dp[j - nums[i - 1]] is updated already, you cannot use it:

    (n represents new value, i.e. updated)
	n n n n n ? . . . . . .  where another ? goes? Oops, it is overriden, we lost it :(
              #  
Conclusion:
So the rule is that observe the positions of current element and its dependencies in the matrix. 
Mostly if current elements depends on the elements in previous row(most frequent case)/columns, 
you can optimize the space.
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
        int target = sum / 2;
        boolean[] dp = new boolean[1 + target];
        dp[0] = true;
        // process all subsets for all sums
        for(int i = 0; i < nums.length; i++) {
             // Must loop backwards
             for(int j = target; j >= 0; j--) {
                if(j >= nums[i]) {
                    dp[j] = dp[j] || dp[j - nums[i]];    
                }
            }
        }
        // the bottom-right corner will have our answer.
        return dp[target];
    }
}
