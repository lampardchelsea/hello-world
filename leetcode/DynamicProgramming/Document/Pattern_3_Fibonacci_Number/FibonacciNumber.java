/**
 Refer to
 https://leetcode.com/problems/fibonacci-number/
 The Fibonacci numbers, commonly denoted F(n) form a sequence, called the Fibonacci sequence, 
 such that each number is the sum of the two preceding ones, starting from 0 and 1. That is,

F(0) = 0,   F(1) = 1
F(N) = F(N - 1) + F(N - 2), for N > 1.
Given N, calculate F(N).

Example 1:
Input: 2
Output: 1
Explanation: F(2) = F(1) + F(0) = 1 + 0 = 1.

Example 2:
Input: 3
Output: 2
Explanation: F(3) = F(2) + F(1) = 1 + 1 = 2.

Example 3:
Input: 4
Output: 3
Explanation: F(4) = F(3) + F(2) = 2 + 1 = 3.

Note:
0 ≤ N ≤ 30.
*/

// Solution 1: Native DFS
// Refer to
// https://dev.to/rattanakchea/dynamic-programming-in-plain-english-using-fibonacci-as-an-example-37m1
// Runtime: 9 ms, faster than 26.20% of Java online submissions for Fibonacci Number.
// Memory Usage: 32.9 MB, less than 5.51% of Java online submissions for Fibonacci Number.
/**
 This implementation is concise and easy to understand.
 We just need have base case when n <=2 and do recursive calls on n-1 & n-2.
 The drawback is 1 call becomes 2 calls. 2 calls becomes 4. etc. It is exponential.
 Time complexity O(2^n) and space complexity is also O(2^n) for all stack calls.
*/
class Solution {
    public int fib(int N) {
        if(N < 2) {
            return N;
        }
        return fib(N - 1) + fib(N - 2);
    }
}

// Solution 2: 
// Refer to
// https://leetcode.com/problems/fibonacci-number/discuss/329680/Here-is-why-this-question-is-kinda-important-and-this-is-what-you-should-take-away
// Runtime: 0 ms, faster than 100.00% of Java online submissions for Fibonacci Number.
// Memory Usage: 32.8 MB, less than 5.51% of Java online submissions for Fibonacci Number.
/**
 This implementation makes use of mem as an array (or hash) to store value of an already computed num. 
 This will greatly reduce the number of call stack and duplicated computation in the call stack.
 Time complexity O(n) and space complexity is also O(n) for all stack calls.
*/
class Solution {
    public int fib(int N) {
        if(N < 2) {
            return N;
        }
        Integer[] memo = new Integer[N + 1];
        return helper(N, memo);
    }
    
    private int helper(int N, Integer[] memo) {
        // Base case
        if(N == 0) {
            return 0;
        }
        if(N == 1) {
            return 1;
        }
        // Return fast if already stored
        if(memo[N] != null) {
            return memo[N];
        }
        int result = helper(N - 1, memo) + helper(N - 2, memo);
        memo[N] = result;
        return result;
    }
}

// Solution 3: DP Bottom Up approach (Optimized runtime)
// Refer to
// https://dev.to/rattanakchea/dynamic-programming-in-plain-english-using-fibonacci-as-an-example-37m1
// https://leetcode.com/problems/fibonacci-number/discuss/218301/C%2B%2B-3-Solutions-Explained-Recursive-or-Iterative-with-DP-or-Imperative
// Runtime: 0 ms, faster than 100.00% of Java online submissions for Fibonacci Number.
// Memory Usage: 33.3 MB, less than 5.51% of Java online submissions for Fibonacci Number.
/**
 This implementation makes use of mem as an array (or hash) to store value of an already 
 computed num. This will greatly reduce the number of call stack and duplicated computation 
 in the call stack.
 For example
 fib(4) = fib(3) + fib(2)
 fib(2), fib(3) were already saved into mem, so will fib(4)
 fib(5) = fib(4) + fib(3)
 The previously saved fib(3) and fib(4) will be used to avoid duplicated calculation and call stacks
*/
class Solution {
    public int fib(int N) {
        if(N < 2) {
            return N;
        }
        int[] dp = new int[N + 1];
        dp[0] = 0;
        dp[1] = 1;
        for(int i = 2; i <= N; i++) {
            dp[i] = dp[i - 1] + dp[i - 2];
        }
        return dp[N];
    }
}

