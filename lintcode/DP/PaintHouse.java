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
