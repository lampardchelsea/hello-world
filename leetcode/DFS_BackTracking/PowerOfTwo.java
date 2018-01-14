/**
 * Refer to
 * https://leetcode.com/problems/power-of-two/description/
 * Given an integer, write a function to determine if it is a power of two.
 *
 * Solution
 * https://discuss.leetcode.com/topic/47195/4-different-ways-to-solve-iterative-recursive-bit-operation-math
*/
// Solution 1: Iterative
class Solution {
    public boolean isPowerOfTwo(int n) {
        if(n == 0) {
            return false;
        }
        while(n % 2 == 0) {
            n = n / 2;
        }
        return n == 1;
    }
}

// Solution 2: DFS




















