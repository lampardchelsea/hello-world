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
// Solution 1:
// Runtime: 5 ms, faster than 38.29% of Java online submissions for Fibonacci Number.
// Memory Usage: 31.7 MB, less than 100.00% of Java online submissions for Fibonacci Number.
class Solution {
    public int fib(int N) {
        if(N == 0) {
            return 0;
        } else if(N <= 2) {
            return 1;
        } else {
            return fib(N - 1) + fib(N - 2);
        }
    }
}

// Solution 2: Optimized on time spending to beat 100% since sharply decrease recursive calls
// Runtime: 0 ms, faster than 100.00% of Java online submissions for Fibonacci Number.
// Memory Usage: 31.9 MB, less than 100.00% of Java online submissions for Fibonacci Number.
class Solution {
    public int fib(int N) {
        if(N == 0) {
            return 0;
        } else if(N <= 2) {
            return 1;
        } else {
            return fibHelper(1, 0, N);
        }
    }
    
    // Initialize base case for 1st and 2nd (if applicable) entry
	   // as 1 and 0 (1 - 1 = 0), then recursive from tail to head by
	   // counting down 1 each recursion and update current entry value
	   // to next with next entry value to (current + next)
    private int fibHelper(int curr, int next, int times) {
        if(count == 0) {
            return 1;
        } else if(count == 1) {
            return curr + next;
        } else {
            return fibHelper(next, curr + next, times - 1);
        }
    }
}
