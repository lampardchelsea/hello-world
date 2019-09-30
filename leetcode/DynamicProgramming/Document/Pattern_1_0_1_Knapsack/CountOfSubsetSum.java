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

// Solution 4: 1D array Bottom-up Dynamic Programming
/**
 Refer to
 https://leetcode.com/problems/combination-sum-iv/discuss/85063/The-difference-of-two-java-DP-solution-of-Combination-Sum-IV
 solution 1 : (correct one)

public class Solution {
    public int combinationSum4(int[] nums, int target) {
        // 在递归过程中，如果状态集是有限的，我们可以采用动态规划的方法，把每个状态的结果记录下来，以减少计算量。
        int[] dp = new int[target + 1];
        dp[0] = 1;
        for (int i = 1; i < dp.length; i++) {        //  outer loop is  to  traversal  dp array 
            for (int j = 0; j < nums.length; j++) {    // inner loop is to  traversal  nums array  
                if (i - nums[j] >= 0) {
                    dp[i] += dp[i - nums[j]];
                }
            }
        }
        return dp[target];
    }   
}

solution 2:(wrong one )
public class Solution {
    public int combinationSum4(int[] nums, int target) {
        int [] dp = new int[target + 1];
        dp[0] = 1;
        for (int i = 0; i < nums.length; i++) {        //  outer loop is  to  traversal  nums array and nums array should be ASC ordered
            for (int j = nums[i]; j <= target; j++) {   //  inner loop is to  traversal  dp array  
                dp[j] += dp[j - nums[i]];
            }
        }
        return dp[target];
    }
}
when give a testcase:
nums = [1, 2, 3]
target = 4
solution 1 got the answer 7:
The possible combination ways are:
(1, 1, 1, 1)
(1, 1, 2)
(1, 2, 1)
(1, 3)
(2, 1, 1)
(2, 2)
(3, 1)
i wanna say this is Permutations not combination.
solution 2 give the answer 4, Obviously， delete the same combination from the above possible Permutations, 
eg (1, 1, 2) (1, 2, 1) (2, 1, 1) (1, 3) (3, 1), the answer is 4.
so, solution 1 could get the Permutation Sum, and solution 2 could get the Combination Sum. this is because 
the count order of dp and nums is different.

 https://stackoverflow.com/questions/40225304/dynamic-programming-with-combination-sum-inner-loop-and-outer-loop-interchangeab
 The two versions are indeed different, yielding different results.
 I'll use nums = {2, 3} for all examples below.

 Version 1 finds the number of combinations with ordering of elements from nums whose sum is total. 
 It does so by iterating through all "subtotals" and counting how many combinations have the right sum, 
 but it doesn't keep track of the elements. For example, the count for 5 will be 2. This is the result 
 of using the first element (with value 2) and finding 1 combination in nums[3] and another combination  
 for the second element (value 3) with the 1 combination in nums[2]. You should pay attention that both 
 combinations use a single 2 and a single 3, but they represent the 2 different ordered lists [2, 3] & [3, 2].

 Version 2 on the other hand find the number of combinations without ordering of elements from nums 
 whose sum is total. It does so by counting how many combinations have the right sum (fur each subtotal), 
 but contrary to version 1, it "uses" each element completely before moving on to the next element thus 
 avoiding different orderings of the same group. When counting subtotals with the first element (2), 
 all counts will initially be 0 (except the 0 sum sentinel), and any even subtotal will get the new 
 count of 1. When the next element used, it is as if it's coming after all 2's are already in the group, 
 so, contrary to version 1, only [2, 3] is counted, and not [3, 2].
 
 By the way, the order of elements in nums doesn't affect the results, as can be understood by the logic explained.
*/
// Correct solution:
class Solution {
    public int combinationSum4(int[] nums, int target) {
        if(nums == null || nums.length == 0) {
            return 0;
        }
        int[] dp = new int[1 + target];
        dp[0] = 1;
        for(int i = 0; i <= target; i++) {             //  outer loop is  to  traversal  dp array 
            for(int j = 0; j < nums.length; j++) {     // inner loop is to  traversal  nums array
                if(i >= nums[j]) {
                    dp[i] += dp[i - nums[j]];
                }
            }
        }
        return dp[target];
    }
}

// Wrong solution
public class Solution {
    public int combinationSum4(int[] nums, int target) {
        int [] dp = new int[target + 1];
        
        dp[0] = 1;
        for (int i = 0; i < nums.length; i++) {        //  outer loop is  to  traversal  nums array and nums array should be ASC ordered
            for (int j = nums[i]; j <= target; j++) {   //  inner loop is to  traversal  dp array  
                dp[j] += dp[j - nums[i]];
            }
        }
        return dp[target];
    }
}
