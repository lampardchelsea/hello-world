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

// Solution 4: Bottom-up DP
