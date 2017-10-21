/**
 * Refer to
 * http://www.lintcode.com/en/problem/paint-house/
 * There are a row of n houses, each house can be painted with one of the three colors: 
   red, blue or green. The cost of painting each house with a certain color is different. 
   You have to paint all the houses such that no two adjacent houses have the same color.
   The cost of painting each house with a certain color is represented by a n x 3 cost 
   matrix. For example, costs[0][0] is the cost of painting house 0 with color red; 
   costs[1][2] is the cost of painting house 1 with color green, and so on... Find the 
   minimum cost to paint all houses.
   Example
   Given costs = [[14,2,11],[11,14,5],[14,3,10]] return 10
   house 0 is blue, house 1 is green, house 2 is blue, 2 + 5 + 3 = 10
 *
 * Solution
 * http://www.cnblogs.com/grandyang/p/5319384.html
 * https://discuss.leetcode.com/topic/27057/simple-15-line-code-with-o-n-time-and-o-1-memory-solution-java
 * https://discuss.leetcode.com/topic/21311/simple-java-dp-solution
 * https://segmentfault.com/a/1190000003903965
*/
// Solution 1:
/**
 这道题说有n个房子，每个房子可以用红绿蓝三种颜色刷，每个房子的用每种颜色刷的花费都不同，限制条件是
 相邻的房子不能用相同的颜色来刷，现在让我们求刷完所有的房子的最低花费是多少。这题跟House Robber II和
 House Robber很类似，不过那题不是每个房子都抢，相邻的房子不抢，而这道题是每个房子都刷，相邻的房子
 不能刷同一种颜色。而Paint Fence那道题主要考察我们有多少种刷法，这几道题很类似，但都不一样，需要
 我们分别区分。但是它们的解题思想都一样，需要用动态规划Dynamic Programming来做，这道题我们需要
 维护一个二维的动态数组dp，其中dp[i][j]表示刷到第i+1房子用颜色j的最小花费，递推式为:

 dp[i][j] = dp[i][j] + min(dp[i - 1][(j + 1) % 3], dp[i - 1][(j + 2) % 3])；

 这个也比较好理解，如果当前的房子要用红色刷，那么上一个房子只能用绿色或蓝色来刷，那么我们要求刷到当前房子，
 且当前房子用红色刷的最小花费就等于当前房子用红色刷的钱加上刷到上一个房子用绿色和刷到上一个房子用蓝色
 的较小值，这样当我们算到最后一个房子时，我们只要取出三个累计花费的最小值即可
*/

public class PaintHouse {
	public int minCost(int[][] costs) {
        if(costs == null || costs.length == 0) {
            return 0;
        }
        // state
        int n = costs.length;
        //int[][] dp = new int[n][3];
        // intialize
        // Caution: not only for 1st row, we should keep all
        // values from costs for later '+=' option
        // for(int i = 0; i < 3; i++) {
        //     dp[0][i] = costs[0][i];
        // }
        int[][] dp = costs;
        // function
        for(int i = 1; i < n; i++) {
            for(int j = 0; j < 3; j++) {
                // Caution: use '+=' to record each level value till final level
                dp[i][j] += Math.min(dp[i - 1][(j + 1) % 3], dp[i - 1][(j + 2) % 3]);   
            }
        }
        // answer
        return Math.min(Math.min(dp[n - 1][0], dp[n - 1][1]), dp[n - 1][2]);
    }
	
	// A more clear way
    public int minCost_2(int[][] costs) {
        if(costs == null || costs.length == 0) {
            return 0;
        }
        for(int i = 1; i < costs.length; i++) {
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
	
	public static void main(String[] args) {
		PaintHouse p = new PaintHouse();
		int[][] costs = {{14,2,11},{11,14,5},{14,3,10}};
		int result = p.minCost(costs);
		System.out.print(result);
	}
}
