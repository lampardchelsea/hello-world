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

// Solution 1: Native DFS style 1 with global variable
class Solution {
    int result = 0;
    public int findTargetSumWays(int[] nums, int S) {
        helper(nums, S, 0, 0);
        return result;
    }
    
    private void helper(int[] nums, int S, int index, int temp) {
        if(index == nums.length) {
            if(temp == S) {
                result++;               
            }
            return;
        }
        helper(nums, S, index + 1, temp + nums[index]);
        helper(nums, S, index + 1, temp - nums[index]);
    }
}

// Solution 2: Native DFS style 2 with no global variable and able to promote memoization
class Solution {
    public int findTargetSumWays(int[] nums, int S) {
        return helper(nums, S, 0, 0);
    }
    
    private int helper(int[] nums, int S, int index, int temp) {
        if(index == nums.length) {
            if(temp == S) {
                return 1;               
            }
            return 0;
        }
        int result = 0;
        result += helper(nums, S, index + 1, temp + nums[index]);
        result += helper(nums, S, index + 1, temp - nums[index]);
        return result;
    }
}

// Solution 3: 2D array top-down DFS memoization 
// Refer to
// https://leetcode.com/problems/target-sum/discuss/245073/Java-solution-in-Chinese
/**
 S2:缓存递归
上述递归很有可能会增加很多不必要的计算，如对于一个数组[1,1,1,1,1,1]，如果前两个数字的符号分别是[-,+]和[+,-]，
那么计算到第三个数字的时候，这两种符号添加方式指向了同一种情况，如果不加处理，肯定要造成后面的[1,1,1,1]的重复计算，
此时可以考虑给递归函数加一个缓存。

每个递归函数有两个变量：
当前位置
当前的运算结果
所以初步考虑缓存应该是一个二维数组，此时就要判断这两个变量各自的取值范围：

对于当前的位置，肯定是要在 nums 里面，也就是说，它的范围是 0～n-1
由于数组中所有的数字都是正数，那么必然所有符号取 + 结果最大，所有符号取 - 结果最小，题目里面也指出，所有的数字和是
不大于 1000 的，所以，范围取上下 1000 即可，也就是 2001
那么，添加了缓存之后就是：

public static int findTargetSumWays(int[] nums, int S) {
    int[][] saved = new int[nums.length][2001];
    for (int[] row : saved) {
        Arrays.fill(row, -1);
    }
    return ways2(nums, S, 0, 0, saved);
}
private static int ways2(int[] nums, int S, int pos, int cur, int[][] saved) {
    if (pos == nums.length) {
        return cur == S ? 1 : 0;
    } else {
        if (saved[pos][cur+1000] >= 0) return saved[pos][cur+1000];
        int ways = ways2(nums, S, pos+1, cur+nums[pos], saved)
                + ways2(nums, S, pos+1, cur-nums[pos], saved);
        saved[pos][cur+1000] = ways;
        return ways;
    }
}
1000 是数组中数字和的上限，如果想要缩减一下内存使用量，也可以直接计算出数字和。
*/
class Solution {
    public int findTargetSumWays(int[] nums, int S) {
        int totalSum = 0;
        for(int num : nums) {
            totalSum += num;
        }
        // Very important notice !!!
        // Should not be
        // Integer[][] memo = new Integer[nums.length][1 + S]; -> S not the maximum sum of the given array,
        // its just a target, but for memoization, it require the maximum sum to build 2nd dimension length
        // Also should not be
        // Integer[][] memo = new Integer[nums.length][1 + totalSum]; --> Because if all values in 'nums' array
        // added up with negative operation ('-'), then in case of range must be non-negative, we need to add
        // additional totalSum to make it up for this extreme case.
        Integer[][] memo = new Integer[nums.length][1 + totalSum * 2];
        return helper(nums, S, 0, 0, memo, totalSum);
    }
    
    private int helper(int[] nums, int S, int index, int temp, Integer[][] memo, int totalSum) {
        if(index == nums.length) {
            if(temp == S) {
                return 1;               
            }
            return 0;
        }
        // Very important notice !!!
        // Should not be 'memo[nums.length][temp]' and must be 'memo[index][temp + totalSum]'
        // since when we build memo, the real start position mapping to normal 0 start poisition
        // is 'temp + totalSum' in case of all value add up as negative but need to store in
        // non-negative indexed array, so in case of this extreme scenario, the real working
        // range is memo[index][totalSum + 0 -> totalSum + temp]
        if(memo[index][temp + totalSum] != null) {
            return memo[index][temp + totalSum];
        }
        int result = 0;
        result += helper(nums, S, index + 1, temp + nums[index], memo, totalSum);
        result += helper(nums, S, index + 1, temp - nums[index], memo, totalSum);
        memo[index][temp + totalSum] = result;
        return result;
    }
}

// Solution 4: DFS + Memoization (instead of using 2D array, using map to make it easy as no 
// need to consider array index range and target value mapping)
// Refer to
// https://leetcode.com/problems/target-sum/discuss/97333/Java-simple-DFS-with-memorization
class Solution {
    public int findTargetSumWays(int[] nums, int S) {
        if(nums == null || nums.length == 0) {
            return 0;
        }
        Map<String, Integer> map = new HashMap<String, Integer>();
        return helper(nums, 0, S, 0, map);
    }
    
    private int helper(int[] nums, int currVal, int S, int index, Map<String, Integer> map) {
        String str = index + "_" + currVal;
        if(map.containsKey(str)) {
            return map.get(str);
        }
        if(index == nums.length) {
            if(currVal == S) {
                return 1;
            } else {
                return 0;
            }
        }
        int add = helper(nums, currVal + nums[index], S, index + 1, map);
        int minus = helper(nums, currVal - nums[index], S, index + 1, map);
        map.put(str, add + minus);
        return add + minus;
    }
}

// Solution 5: 2D array bottom-up DP
/**
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
*/
// The starnge part is if initialize as dp = [nums.length][1 + target] style won't work ???
// Only work on dp = [1 + nums.length][1 + target] style ?
// Refer to
// https://github.com/lampardchelsea/hello-world/blob/master/leetcode/DynamicProgramming/Document/Pattern_1_0_1_Knapsack/EqualSubsetSumPartition.java
// https://leetcode.com/problems/target-sum/discuss/278526/Java-1D-and-2D-DP-Solution/355363
// Wrong solution which not able to pass
// Input [0,0,0,0,0,0,0,0,1]  1
// Output 0
// Expected 256
class Solution {
    public int findTargetSumWays(int[] nums, int S) {
        int sum = 0;
        for(int i = 0; i < nums.length; i++) {
            sum += nums[i];
        }
        if(sum < S || (sum + S) % 2 == 1) {
            return 0;
        }
        int target = (sum + S) / 2;
        return helper(nums, target);
    }
    
    private int helper(int[] nums, int target) {
        int[][] dp = new int[nums.length][1 + target];
        for(int i = 0; i < nums.length; i++) {
            dp[i][0] = 1;
        }
        for(int i = 1; i <= target; i++) {
            dp[0][i] += (nums[0] == i ? 1 : 0);
        }
        for(int i = 1; i < nums.length; i++) {
            for(int j = 0; j <= target; j++) {
                dp[i][j] = dp[i - 1][j];
                if(j >= nums[i - 1]) {
                    dp[i][j] += dp[i - 1][j - nums[i - 1]];
                }
            }
        }
        return dp[nums.length - 1][target];
    }
}

/**
 Explain why we need 1 more dummy row when creating 2D array ?
 Refer to
 https://leetcode.com/problems/target-sum/discuss/278526/Java-1D-and-2D-DP-Solution
 https://leetcode.com/problems/target-sum/discuss/278526/Java-1D-and-2D-DP-Solution/355606
 The problem is that our previous solution (for 2D) works for positive numbers. 
 However the problem (and the test case you mentioned has non-negative numbers, 
 which includes 0's). That changes the calculation as shown below :

 With old solution, input\sum : [0,0,1]\1, here is the table :
 
 i\s         0 1
 [0]         1 0
 [0,0]       1 0
 [0,0,1]     1 1
 
 Here adding a new 0, doesn't change the number of subsets to achieve 0's. 
 Also, an empty set can achieve 0. A set with just {0} can achieve sum of 
 0 in 2 ways : by choosing empty set and by choosing {0}. Similarly if we 
 have another 0, it should change the number of subsets.

 With new solution, input\sum : [0,0,0,1]\1, here is the table :
 
 i\s             0 1
 []              1 0
 [0]             2 0
 [0,0]           4 0
 [0,0,0]         8 0
 [0,0,0,1]       8 8
 
 =========================================================================
 great catch for the root cause, so if we adding 1 row as [1 + nums.length] 
 will good for handling 0 (non-negative) presented in array case

 i\s             0 1
 []              1 0  -> The dummy row ('1' in [1 + nums.length]) will handle the empty set option
 [0]             2 0
 
 Like you said, especially compare to 416. Partition Equal Subset Sum, the condition 
 there is only positive integer in array, but here we have 0 which enable empty set option.
 So just use int[][] dp = new int[1 + nums.length][1 + target] to initialize the dp array
*/

// Correct solution
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
        for(int i = 0; i < len + 1; i++) {
            dp[i][0] = 1;
        }
        for(int i = 1; i <= len; i++) {
            for(int j = 0; j <= target; j++) { // Here j range start from 0 not 1
                dp[i][j] = dp[i - 1][j];
                if(j >= nums[i - 1]) {
                    dp[i][j] += dp[i - 1][j - nums[i - 1]];    
                }
            }
        }
        return dp[len][target];
    }
}

// Solution 6: 1D DP array
class Solution {
    public int findTargetSumWays(int[] nums, int S) {
        int sum = 0;
        for(int i = 0; i < nums.length; i++) {
            sum += nums[i];
        }
        if(sum < S || (sum + S) % 2 == 1) {
            return 0;
        }
        int target = (sum + S) / 2;
        return helper(nums, target);
    }
    
    private int helper(int[] nums, int target) {
        int[] dp = new int[1 + target];
        dp[0] = 1;
        for(int i = 0; i < nums.length; i++) {
            for(int j = target; j >= 0; j--) {
                if(j >= nums[i]) {
                    dp[j] += dp[j - nums[i]];                    
                }
            }
        }
        return dp[target];
    }
}

