/**
 * Refer to
 * https://leetcode.com/problems/climbing-stairs/description/
 * http://www.lintcode.com/en/problem/climbing-stairs/#
 *
 * Solution
 * https://leetcode.com/articles/climbing-stairs/
*/
class Solution {
    public int climbStairs(int n) {
        // state
        int[] f = new int[n + 1];
        
        // intialize
        if(n <= 1) {
            return 1;
        }
        f[1] = 1;
        f[2] = 2;
        
        // function
        for(int i = 3; i <= n; i++) {
            f[i] = f[i - 1] + f[i - 2];
        }
        
        // answer
        return f[n];
    }
}
