/**
 * Refer to
 * http://www.lintcode.com/en/problem/paint-house-ii/
 * There are a row of n houses, each house can be painted with one of the k colors. 
   The cost of painting each house with a certain color is different. You have to paint 
   all the houses such that no two adjacent houses have the same color.

   The cost of painting each house with a certain color is represented by a n x k cost matrix. 
   For example, costs[0][0] is the cost of painting house 0 with color 0; costs[1][2] is the 
   cost of painting house 1 with color 2, and so on... Find the minimum cost to paint all houses.
   
   Example
   Given n = 3, k = 3, costs = [[14,2,11],[11,14,5],[14,3,10]] return 10
   house 0 is color 2, house 1 is color 3, house 2 is color 2, 2 + 5 + 3 = 10
 *
 * Solution
 * http://www.cnblogs.com/grandyang/p/5322870.html
 * https://segmentfault.com/a/1190000003903965
 * https://discuss.leetcode.com/topic/25489/fast-dp-java-solution-runtime-o-nk-space-o-1
 * https://discuss.leetcode.com/topic/22580/ac-java-solution-without-extra-space
 * https://discuss.leetcode.com/topic/22580/ac-java-solution-without-extra-space/12
*/
// Solution 1:
// Refer to
// https://github.com/lampardchelsea/hello-world/blob/master/lintcode/DP/PaintHouse.java
// Time Complexity: O(n * k * k)
// Space Complexity: O(n * k)
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
// Refer to
// https://segmentfault.com/a/1190000003903965
// 和I的思路一样，不过这里我们有K个颜色，不能简单的用Math.min方法了。如果遍历一遍颜色数组就找出除了自身外最小的颜色呢？
// 我们只要把最小和次小的都记录下来就行了，这样如果和最小的是一个颜色，就加上次小的开销，反之，则加上最小的开销。
// 
// http://www.cnblogs.com/grandyang/p/5322870.html
// 这道题是之前那道Paint House的拓展，那道题只让用红绿蓝三种颜色来粉刷房子，而这道题让我们用k种颜色，
// 这道题不能用之前那题的解法，会TLE。这题的解法的思路还是用DP，但是在找不同颜色的最小值不是遍历所有不同颜色，
// 而是用min1和min2来记录之前房子的最小和第二小的花费的颜色，如果当前房子颜色和min1相同，那么我们用min2对应
// 的值计算，反之我们用min1对应的值，这种解法实际上也包含了求次小值的方法，感觉也是一种很棒的解题思路
public class Solution {
    /*
     * @param costs: n x k cost matrix
     * @return: an integer, the minimum cost to paint all houses
     */
    public int minCostII(int[][] costs) {
        if(costs == null || costs.length == 0) {
            return 0;
        }
        int n = costs.length;
        int k = costs[0].length;
        // initialize
        // Caution: we initialize as dp = costs, later statement
        // of '+=' don't need dp[i][j] = cost[i][j] + dp[i - 1][...],
        // as dp[i][j] already represent cost[i][j]
        int[][] dp = costs;
        // min1 is index of 1st minimum cost till the previous house
        // min2 is index of 2nd minimum cost till the previous house
        int min1 = -1;
        int min2 = -1;
        for(int i = 0; i < n; i++) {
            // For each row, you need last1 and last2 to keep track of the 
            // least and second least costs of previous row, while min1 
            // min2 to get the least and second least values of current row.
            int last1 = min1;
            int last2 = min2;
            min1 = -1;
            min2 = -1;
            for(int j = 0; j < k; j++) {
                // For first row
                if(last1 == -1) {
                    dp[i][j] += 0;
                } else if(j == last1) {
                    // If same as previous index(means same color), only plus second least cost
                    dp[i][j] += dp[i - 1][last2];
                } else {
                    // If not same as previous index(means same color), plus first least cost
                    dp[i][j] += dp[i - 1][last1];
                }
                // If index j can get first minimum value, update original first minimum
                // index to j and second minimum index to first minimum index
                if(min1 == -1 || dp[i][j] < dp[i][min1]) {
                    min2 = min1;
                    min1 = j;
                } else if(min2 == -1 || dp[i][j] < dp[i][min2]) {
                    min2 = j;
                }
            }
        }
        return dp[n - 1][min1];
    }
}

