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

// Solution 3: Top-down DFS memoization
