/**
 Refer to
 http://leetcode.liangjiateng.cn/leetcode/paint-house/description
 There are a row of n houses, each house can be painted with one of the three colors: 
 red, blue or green. The cost of painting each house with a certain color is different. 
 You have to paint all the houses such that no two adjacent houses have the same color.

The cost of painting each house with a certain color is represented by a n x 3 cost matrix. 
For example, costs[0][0] is the cost of painting house 0 with color red; costs[1][2] is 
the cost of painting house 1 with color green, and so on... Find the minimum cost to paint all houses.

Note:
All costs are positive integers.

Example:
Input: [[17,2,17],[16,16,5],[14,3,19]]
Output: 10
Explanation: Paint house 0 into blue, paint house 1 into green, paint house 2 into blue. 
             Minimum cost: 2 + 5 + 3 = 10
*/

// Solution 1:
// Refer to
// https://github.com/lampardchelsea/hello-world/blob/master/lintcode/DP/PaintHouse.java
class Solution {
    // A more clear way
    public int minCost_2(int[][] costs) {
        if (costs == null || costs.length == 0) {
            return 0;
        }
        for (int i = 1; i < costs.length; i++) {
            // 涂第一种颜色的话，上一个房子就不能涂第一种颜色，这样我们要在上一个
            // 房子的第二和第三个颜色的最小开销中找最小的那个加上
            costs[i][0] = costs[i][0] + Math.min(costs[i - 1][1], costs[i - 1][2]);
            // 涂第二或者第三种颜色同理
            costs[i][1] = costs[i][1] + Math.min(costs[i - 1][0], costs[i - 1][2]);
            costs[i][2] = costs[i][2] + Math.min(costs[i - 1][0], costs[i - 1][1]);
        }
        // 返回涂三种颜色中开销最小的那个
        return Math.min(Math.min(costs[costs.length - 1][0], costs[costs.length - 1][1]), costs[costs.length - 1][2]);
    }
}
