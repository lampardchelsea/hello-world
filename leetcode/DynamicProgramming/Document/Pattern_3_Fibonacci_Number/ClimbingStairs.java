/**
 Refer to
 https://leetcode.com/problems/climbing-stairs/
 You are climbing a stair case. It takes n steps to reach to the top.
 Each time you can either climb 1 or 2 steps. In how many distinct ways can you climb to the top?
 Note: Given n will be a positive integer.

Example 1:
Input: 2
Output: 2
Explanation: There are two ways to climb to the top.
1. 1 step + 1 step
2. 2 steps

Example 2:
Input: 3
Output: 3
Explanation: There are three ways to climb to the top.
1. 1 step + 1 step + 1 step
2. 1 step + 2 steps
3. 2 steps + 1 step
*/

// Solution 1: Native DFS (TLE)
// Refer to
// https://leetcode.com/problems/climbing-stairs/discuss/163347/Python-3000DP-or-tm
// https://leetcode.com/problems/climbing-stairs/discuss/25299/Basically-it's-a-fibonacci.
class Solution {
    public int climbStairs(int n) {
        return helper(n);
    }
    
    private int helper(int n) {
        if(n <= 2) {
            return n;
        }
        return helper(n - 1) + helper(n - 2);
    }
}

// Solution 2: Top-down DP (DFS + Memoization)
// Refer to
// https://github.com/lampardchelsea/hello-world/blob/master/leetcode/DynamicProgramming/Document/Pattern_3_Fibonacci_Number/FibonacciNumber.java
// Runtime: 0 ms, faster than 100.00% of Java online submissions for Climbing Stairs.
// Memory Usage: 32.9 MB, less than 5.26% of Java online submissions for Climbing Stairs.
class Solution {
    public int climbStairs(int n) {
        Integer[] memo = new Integer[n + 1];
        return helper(n, memo);
    }
    
    private int helper(int n, Integer[] memo) {
        if(memo[n] != null) {
            return memo[n];
        }
        if(n <= 2) {
            return n;
        }
        int result = helper(n - 1, memo) + helper(n - 2, memo);
        memo[n] = result;
        return result;
    }
}

// Solution 3: 
