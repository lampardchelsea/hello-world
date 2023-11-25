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

























































https://leetcode.ca/all/256.html

There are a row of n houses, each house can be painted with one of the three colors: red, blue or green. The cost of painting each house with a certain color is different. You have to paint all the houses such that no two adjacent houses have the same color.

The cost of painting each house with a certain color is represented by a n x 3 cost matrix. For example, costs[0][0] is the cost of painting house 0 with color red; costs[1][2] is the cost of painting house 1 with color green, and so on... Find the minimum cost to paint all houses.

Note: All costs are positive integers.

Example:
```
Input: [[17,2,17],
        [16,16,5],
        [14,3,19]]
Output: 10
Explanation: Paint house 0 into blue, paint house 1 into green, paint house 2 into blue.
             Minimum cost: 2 + 5 + 3 = 10.
```

---
Attempt 1: 2023-11-24

Solution 1: DP + Fibonacci (10 min)

Style 1: Since only 3 colors means 2nd dimension of dp array as 3, start with 3 different colors make 3 different branches
```
class Solution {
    public int minCost(int[][] costs) {
        if(costs.length == 0 || costs[0].length == 0) {
            return 0;
        }
        int n = costs.length;
        int[][] dp = new int[n + 1][3];
        for(int i = 1; i < n; i++) {
            dp[i][0] += Math.min(dp[i - 1][1], dp[i - 1][2]);
            dp[i][1] += Math.min(dp[i - 1][0], dp[i - 1][2]);
            dp[i][2] += Math.min(dp[i - 1][0], dp[i - 1][1]);
        }
        return Math.min(Math.min(dp[n - 1][0], dp[n - 1][1]), dp[n - 1][2]);
    }
}

Time Complexity: O(N)
Space Complexity: O(N*3)
```

Style 2: More general
```
class Solution {
    public int minCost(int[][] costs) {
        if(costs.length == 0 || costs[0].length == 0) {
            return 0;
        }
        int n = costs.length;
        int[][] dp = new int[n + 1][3];
        for(int i = 1; i < n; i++) {
            for(int j = 0; j < 3; j++) {
                dp[i][j] += Math.min(dp[i - 1][(j + 1) % 3], dp[i - 1][(j + 2) % 3]);
            }
        }
        return Math.min(Math.min(dp[n - 1][0], dp[n - 1][1]), dp[n - 1][2]);
    }
}

Time Complexity: O(N)
Space Complexity: O(N*3)
```

Refer to
https://grandyang.com/leetcode/256/ 
这道题说有n个房子，每个房子可以用红绿蓝三种颜色刷，每个房子的用每种颜色刷的花费都不同，限制条件是相邻的房子不能用相同的颜色来刷，现在让求刷完所有的房子的最低花费是多少。这题跟 House Robber II 和 House Robber 很类似，不过那题不是每个房子都抢，相邻的房子不抢，而这道题是每个房子都刷，相邻的房子不能刷同一种颜色，而 Paint Fence 那道题主要考察有多少种刷法。这几道题很类似，但都不一样，需要分别区分。但是它们的解题思想都一样，需要用动态规划 Dynamic Programming 来做，这道题需要维护一个二维的动态数组 dp，其中 dp[i][j] 表示刷到第 i+1 房子用颜色j的最小花费，状态转移方程为:
```
dp[i][j] = dp[i][j] + min(dp[i - 1][(j + 1) % 3], dp[i - 1][(j + 2) % 3])；
```

这个也比较好理解，如果当前的房子要用红色刷，则上一个房子只能用绿色或蓝色来刷，那么要求刷到当前房子，且当前房子用红色刷的最小花费就等于当前房子用红色刷的钱加上刷到上一个房子用绿色和刷到上一个房子用蓝色中的较小值，这样当算到最后一个房子时，只要取出三个累计花费的最小值即可，参见代码如下：
```
    class Solution {
        public:
        int minCost(vector<vector<int>>& costs) {
            if (costs.empty() || costs[0].empty()) return 0;
            vector<vector<int>> dp = costs;
            for (int i = 1; i < dp.size(); ++i) {
                for (int j = 0; j < 3; ++j) {
                    dp[i][j] += min(dp[i - 1][(j + 1) % 3], dp[i - 1][(j + 2) % 3]);
                }
            }
            return min(min(dp.back()[0], dp.back()[1]), dp.back()[2]);
        }
    };
```

由于只有红绿蓝三张颜色，所以就可以分别写出各种情况，这样写可能比上面的写法更加一目了然一些，更容易理解一点吧：
```
    class Solution {
        public:
        int minCost(vector<vector<int>>& costs) {
            if (costs.empty() || costs[0].empty()) return 0;
            vector<vector<int>> dp = costs;
            for (int i = 1; i < dp.size(); ++i) {
                dp[i][0] += min(dp[i - 1][1], dp[i - 1][2]);
                dp[i][1] += min(dp[i - 1][0], dp[i - 1][2]);
                dp[i][2] += min(dp[i - 1][0], dp[i - 1][1]);
            }
            return min(min(dp.back()[0], dp.back()[1]), dp.back()[2]);
        }
    };
```

Refer to
https://segmentfault.com/a/1190000003903965 
直到房子i，其最小的涂色开销是直到房子i-1的最小涂色开销，加上房子i本身的涂色开销。但是房子i的涂色方式需要根据房子i-1的涂色方式来确定，所以我们对房子i-1要记录涂三种颜色分别不同的开销，这样房子i在涂色的时候，我们就知道三种颜色各自的最小开销是多少了。我们在原数组上修改，可以做到不用空间。
```
public class Solution {
    public int minCost(int[][] costs) {
        if(costs != null && costs.length == 0) return 0;
        for(int i = 1; i < costs.length; i++){
            // 涂第一种颜色的话，上一个房子就不能涂第一种颜色，这样我们要在上一个房子的第二和第三个颜色的最小开销中找最小的那个加上
            costs[i][0] = costs[i][0] + Math.min(costs[i - 1][1], costs[i - 1][2]);
            // 涂第二或者第三种颜色同理
            costs[i][1] = costs[i][1] + Math.min(costs[i - 1][0], costs[i - 1][2]);
            costs[i][2] = costs[i][2] + Math.min(costs[i - 1][0], costs[i - 1][1]);
        }
        // 返回涂三种颜色中开销最小的那个
        return Math.min(costs[costs.length - 1][0], Math.min(costs[costs.length - 1][1], costs[costs.length - 1][2]));
    }
}
```
