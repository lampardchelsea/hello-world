/**
 * Refer to
 * https://leetcode.com/problems/perfect-squares/description/
 * http://www.lintcode.com/en/problem/perfect-squares/
 * Given a positive integer n, find the least number of perfect square numbers 
   (for example, 1, 4, 9, 16, ...) which sum to n.
   For example, given n = 12, return 3 because 12 = 4 + 4 + 4; given n = 13, 
   return 2 because 13 = 4 + 9.
 *
 * Solution
 * https://discuss.leetcode.com/topic/26400/an-easy-understanding-dp-solution-in-java
*/
class Solution {
    public int numSquares(int n) {    
        // State: represent how many numbers to construct number 'n'
        int[] dp = new int[n + 1];
        // Initialize
        for(int i = 0; i < n + 1; i++) {
            dp[i] = Integer.MAX_VALUE;
        }
        // n is positive integer, so dp[0] is 0 means no
        // combination to reach this target
        dp[0] = 0;
        // function
        for(int i = 1; i < n + 1; i++) {
            int j = 1;
            int min = Integer.MAX_VALUE;
            while(i - j * j >= 0) {
                min = Math.min(dp[i - j * j] + 1, min);
                j++;
            }
            dp[i] = min;
        }
        // answer
        return dp[n];
    }
}
