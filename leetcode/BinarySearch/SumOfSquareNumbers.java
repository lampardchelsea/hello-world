/**
 * Refer to
 * https://leetcode.com/problems/sum-of-square-numbers/description/
 * Given a non-negative integer c, your task is to decide whether there're two integers a and b such that a2 + b2 = c.
    Example 1:
    Input: 5
    Output: True
    Explanation: 1 * 1 + 2 * 2 = 5
    Example 2:
    Input: 3
    Output: False
 *
 * Solution
 * https://leetcode.com/problems/sum-of-square-numbers/discuss/104930/Java-Two-Pointers-Solution
*/

class Solution {
    public boolean judgeSquareSum(int c) {
        // Since Non-negative, start with 0
        int start = 0;
        int end = (int)Math.sqrt(c);
        // Two integers could be same value
        while(start <= end) {
            if(start * start + end * end < c) {
                start++;   
            } else if(start * start + end * end > c) {
                end--;
            } else {
                return true;
            }
        }
        return false;
    }
}
