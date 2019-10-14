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
 Time complexity O(2n) and space complexity is also O(2n) for all stack calls.
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
