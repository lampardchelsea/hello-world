// Unbounded Knapsack

/**
 * Refer to
 * https://leetcode.com/problems/combination-sum-iv/description/
 * Given an integer array with all positive numbers and no duplicates, find the number of possible 
   combinations that add up to a positive integer target.

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
    Follow up:
    What if negative numbers are allowed in the given array?
    How does it change the problem?
    What limitation we need to add to the question to allow negative numbers?
 *
 * 
 * Solution
 * https://discuss.leetcode.com/topic/52302/1ms-java-dp-solution-with-detailed-explanation
    Think about the recurrence relation first. How does the # of combinations of the target related to 
    the # of combinations of numbers that are smaller than the target?

    So we know that target is the sum of numbers in the array. Imagine we only need one more number 
    to reach target, this number can be any one in the array, right? So the # of combinations of target, 
    comb[target] = sum(comb[target - nums[i]]), where 0 <= i < nums.length, and target >= nums[i].

    In the example given, we can actually find the # of combinations of 4 with the # of combinations of 
    3(4 - 1), 2(4- 2) and 1(4 - 3). As a result, comb[4] = comb[4-1] + comb[4-2] + comb[4-3] = comb[3] + comb[2] + comb[1].

    Then think about the base case. Since if the target is 0, there is only one way to get zero, 
    which is using 0, we can set comb[0] = 1.

    EDIT: The problem says that target is a positive integer that makes me feel it's unclear to put 
    it in the above way. Since target == 0 only happens when in the previous call, target = nums[i], 
    we know that this is the only combination in this case, so we return 1.

    Now we can come up with at least a recursive solution.

    public int combinationSum4(int[] nums, int target) {
        if (target == 0) {
            return 1;
        }
        int res = 0;
        for (int i = 0; i < nums.length; i++) {
            if (target >= nums[i]) {
                res += combinationSum4(nums, target - nums[i]);
            }
        }
        return res;
    }
    Now for a DP solution, we just need to figure out a way to store the intermediate results, to avoid 
    the same combination sum being calculated many times. We can use an array to save those results, 
    and check if there is already a result before calculation. We can fill the array with -1 to indicate 
    that the result hasn't been calculated yet. 0 is not a good choice because it means there is no 
    combination sum for the target.

    private int[] dp;

    public int combinationSum4(int[] nums, int target) {
        dp = new int[target + 1];
        Arrays.fill(dp, -1);
        dp[0] = 1;
        return helper(nums, target);
    }

    private int helper(int[] nums, int target) {
        if (dp[target] != -1) {
            return dp[target];
        }
        int res = 0;
        for (int i = 0; i < nums.length; i++) {
            if (target >= nums[i]) {
                res += helper(nums, target - nums[i]);
            }
        }
        dp[target] = res;
        return res;
    }
    EDIT: The above solution is top-down. How about a bottom-up one?

    public int combinationSum4(int[] nums, int target) {
        int[] comb = new int[target + 1];
        comb[0] = 1;
        for (int i = 1; i < comb.length; i++) {
            for (int j = 0; j < nums.length; j++) {
                if (i - nums[j] >= 0) {
                    comb[i] += comb[i - nums[j]];
                }
            }
        }
        return comb[target];
}
 
* Follow Up
* https://discuss.leetcode.com/topic/52227/7-liner-in-python-and-follow-up-question
* The problem with negative numbers is that now the combinations could be potentially of 
  infinite length. Think about nums = [-1, 1] and target = 1. We can have all sequences of 
  arbitrary length that follow the patterns -1, 1, -1, 1, ..., -1, 1, 1 and 
  1, -1, 1, -1, ..., 1, -1, 1 (there are also others, of course, just to give an example). 
  So we should limit the length of the combination sequence, so as to give a bound to the problem.
  
* Can you please explain the difference between this solution ?
* Combination Sum IV VS Coin Change 2
* https://discuss.leetcode.com/topic/79071/knapsack-problem-java-solution-with-thinking-process-o-nm-time-and-o-m-space/6
* The difference is that if you update dp while:

      increasing i then the previous partial result dp[i - coin] is the result that has considered coin already
      decreasing i then the previous partial result dp[i - coin] is the result that has not considered coin yet

      @return number of ways to make sum s using repeated coins
      public static int coinrep(int[] coins, int s) {
          int[] dp = new int[s + 1]; 
          dp[0] = 1;          
          for (int coin : coins)      
              for (int i = coin; i <= s; i++)         
                  dp[i] += dp[i - coin];                                  
          return dp[s];
      }                                       

      @return number of ways to make sum s using non-repeated coins
      public static int coinnonrep(int[] coins, int s) {
          int[] dp = new int[s + 1];
          dp[0] = 1;  
          for (int coin : coins)
              for (int i = s; i >= coin; i--)
                  dp[i] += dp[i - coin];              
          return dp[s];                                                   
      }
      
* 
* Refer to
* https://discuss.leetcode.com/topic/52302/1ms-java-dp-solution-with-detailed-explanation/8
* Difference between bottom-up V.S top-down on Combination Sum IV ?
* Good question guys! Actually if you look closely at the implementation of bottom-up approach, 
  you will notice that it may have more "intermediate steps" than the top-down one.
  For example, given nums = [100], target = 100, the top-down approach will get the result directly, 
  while the bottom-up has to build comb[1] all the way to comb[100].
  Is it possible to calculate only necessary intermediate steps in bottom-up approach? It's possible 
  because you can find the next smallest sum with the nums given. But I think it's hard enough to 
  be another independent problem.
*/

// Solution 1: DFS (TLE)
class Solution {
    // TLE 
    // E.g nums = {1,2,3} target = 32
    public int combinationSum4(int[] nums, int target) {
        if(nums == null || nums.length == 0 || target < 0) {
            return 0;
        }
        if(target == 0) {
            return 1;
        }
        int res = 0;
        for(int num : nums) {
            if(target >= num) {
                res += combinationSum4(nums, target - num);
            }
        }
        return res;
    }
}

// Solution 2: Top-Down DP (kind of Knapsack problem + memorized search)
/**
 Now for a DP solution, we just need to figure out a way to store the intermediate results, 
 to avoid the same combination sum being calculated many times. We can use an array to save 
 those results, and check if there is already a result before calculation. We can fill the 
 array with -1 to indicate that the result hasn't been calculated yet. 0 is not a good choice 
 because it means there is no combination sum for the target.
*/
class Solution {
    public int combinationSum4(int[] nums, int target) {
        if(nums == null || nums.length == 0 || target < 0) {
            return 0;
        }
        // state
        int[] dp = new int[target + 1];
        // initialize
        dp[0] = 1;
        for(int i = 1; i < dp.length; i++) {
            // -1 is a better choice -> indicate that the result hasn't been calculated yet
            // 0 is not a good choice -> means there is no combination sum for the target.
            dp[i] = -1;
        }
        // function
        return helper(dp, nums, target);
    }
    
    private int helper(int[] dp, int[] nums, int remained) {
        if(dp[remained] != -1) {
            return dp[remained];
        }
        int res = 0;
        for(int num : nums) {
            if(remained >= num) {
                res += helper(dp, nums, remained - num);
            }
        }
        dp[remained] = res;
        return res;
    }
}


// Solution 3: Bottom-Up DP
/**
* Refer to
* https://discuss.leetcode.com/topic/52302/1ms-java-dp-solution-with-detailed-explanation/8
* Difference between bottom-up V.S top-down on Combination Sum IV ?
* Good question guys! Actually if you look closely at the implementation of bottom-up approach, 
  you will notice that it may have more "intermediate steps" than the top-down one.
  For example, given nums = [100], target = 100, the top-down approach will get the result directly, 
  while the bottom-up has to build comb[1] all the way to comb[100].
  Is it possible to calculate only necessary intermediate steps in bottom-up approach? It's possible 
  because you can find the next smallest sum with the nums given. But I think it's hard enough to 
  be another independent problem.
*/
class Solution {
    public int combinationSum4(int[] nums, int target) {
        // state
        int[] dp = new int[target + 1];
        // initialize
        dp[0] = 1;
        // function
        for(int i = 1; i < dp.length; i++) {
            for(int j = 0; j < nums.length; j++) {
                if(i - nums[j] >= 0) {
                    dp[i] += dp[i - nums[j]]; 
                } 
            }
        }
        // answer
        return dp[target];
    }
}


