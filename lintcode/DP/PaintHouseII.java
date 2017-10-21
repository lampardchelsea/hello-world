/**
 * Refer to
 * http://www.lintcode.com/en/problem/paint-house-ii/
 * There are a row of n houses, each house can be painted with one of the k colors. 
   The cost of painting each house with a certain color is different. You have to paint 
   all the houses such that no two adjacent houses have the same color.

   The cost of painting each house with a certain color is represented by a n x k cost matrix. 
   For example, costs[0][0] is the cost of painting house 0 with color 0; costs[1][2] is the 
   cost of painting house 1 with color 2, and so on... Find the minimum cost to paint all houses.
 *
 * Solution
 * http://www.cnblogs.com/grandyang/p/5322870.html
 * https://segmentfault.com/a/1190000003903965
 * https://discuss.leetcode.com/topic/25489/fast-dp-java-solution-runtime-o-nk-space-o-1
 * https://discuss.leetcode.com/topic/22580/ac-java-solution-without-extra-space
*/
// Solution 1:
// Refer to
// https://github.com/lampardchelsea/hello-world/blob/master/lintcode/DP/PaintHouse.java
public class Solution {
    /*
     * @param costs: n x k cost matrix
     * @return: an integer, the minimum cost to paint all houses
     */
    public int minCostII(int[][] costs) {
        if(costs == null || costs.length == 0) {
            return 0;
        }
        // This method is fully Brute Force and just replace Paint House 3 colors into k colors
        int n = costs.length;
        int k = costs[0].length;
        int[][] dp = costs;
        for(int i = 1; i < n; i++) {
            for(int j = 0; j < k; j++) {
                int min = Integer.MAX_VALUE;
                for(int v = 1; v < k; v++) {
                    min = Math.min(dp[i - 1][(j + v) % k], min);
                }
                dp[i][j] += min;
            }
        }
        int res = Integer.MAX_VALUE;
        for(int i = 0; i < k; i++) {
            res = Math.min(res, dp[n - 1][i]);
        }
        return res;
    }
}


// Solution 2:

