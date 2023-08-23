/**
 Refer to
 https://leetcode.com/problems/minimum-path-sum/
 Given a m x n grid filled with non-negative numbers, find a path from top left to 
 bottom right which minimizes the sum of all numbers along its path.

Note: You can only move either down or right at any point in time.

Example:
Input:
[
  [1,3,1],
  [1,5,1],
  [4,2,1]
]
Output: 7
Explanation: Because the path 1→3→1→1→1 minimizes the sum.
*/

// Solution:
// Refer to
// https://leetcode.com/discuss/general-discussion/458695/dynamic-programming-patterns#Minimum-(Maximum)-Path-to-Reach-a-Target
/**
Minimum (Maximum) Path to Reach a Target
Problem list: https://leetcode.com/list/55ac4kuc

Generate problem statement for this pattern

Statement
Given a target find minimum (maximum) cost / path / sum to reach the target.

Approach
Choose minimum (maximum) path among all possible paths before the current state, then add value for the current state.

routes[i] = min(routes[i-1], routes[i-2], ... , routes[i-k]) + cost[i]
Generate optimal solutions for all values in the target and return the value for the target.

Top-Down
for (int j = 0; j < ways.size(); ++j) {
    result = min(result, topDown(target - ways[j]) + cost/ path / sum);
}
return memo[state parameters] = result;
Bottom-Up
for (int i = 1; i <= target; ++i) {
   for (int j = 0; j < ways.size(); ++j) {
       if (ways[j] <= i) {
           dp[i] = min(dp[i], dp[i - ways[j]] + cost / path / sum) ;
       }
   }
}
 
return dp[target]
*/

// Refer to
// https://leetcode.com/problems/minimum-path-sum/discuss/819102/Evolve-from-recursion-to-dp
/**
1. brute force O(2^min(m,n)), dfs returns the min cost from i, j to bottom right.
	public int minPathSum(int[][] grid) {
        return dfs(0,0,grid);
    }
    private int dfs(int i, int j, int[][] grid) {
        int r=grid.length, c=grid[0].length;
        if(i==r||j==c) return Integer.MAX_VALUE;
        if(i==r-1&&j==c-1) return grid[i][j];
        int down=dfs(i+1,j,grid), right=dfs(i,j+1,grid);
        return Math.min(down,right)+grid[i][j];
    }
    
2. Memoization O(mn)
	public int minPathSum(int[][] grid) {
        Integer[][] mem=new Integer[grid.length][grid[0].length];
        return dfs(0,0,grid,mem);
    }
    private int dfs(int i, int j, int[][] grid, Integer[][] mem) {
        int r=grid.length, c=grid[0].length;
        if(i==r||j==c) return Integer.MAX_VALUE;
        if(i==r-1&&j==c-1) return grid[i][j];
        if(mem[i][j]!=null) return mem[i][j];
        int down=dfs(i+1,j,grid,mem), right=dfs(i,j+1,grid,mem);
        return mem[i][j]=Math.min(down,right)+grid[i][j];
    }
    
3. dp O(mn) Time, O(mn) Space. From #1, f[i][j] = min(f[i+1][j],f[i][j+1]) + grid[i][j]
	 public int minPathSum(int[][] grid) {
        int r=grid.length, c=grid[0].length;
        int[][] dp=new int[r+1][c+1];
		for(int i=0;i<r-1;i++) dp[i][c]=Integer.MAX_VALUE;// dp[r-1][c]=0
        for(int i=0;i<c-1;i++) dp[r][i]=Integer.MAX_VALUE;// dp[r][c-1]=0
        for(int i=r-1;i>=0;i--)
            for(int j=c-1;j>=0;j--)
                dp[i][j]=Math.min(dp[i+1][j],dp[i][j+1])+grid[i][j];
        return dp[0][0];
    }
    
4. O(mn) Time, O(n) space dp
	 public int minPathSum(int[][] grid) {
        int r=grid.length, c=grid[0].length;
        int[] dp=new int[c+1];
		Arrays.fill(dp,Integer.MAX_VALUE);//row r
        dp[c-1]=0;
        for(int i=r-1;i>=0;i--)
            for(int j=c-1;j>=0;j--)
                dp[j]=Math.min(dp[j],dp[j+1])+grid[i][j];
        return dp[0];
    }
*/

// Solution 1: Native DFS(TLE)
class Solution {
    public int minPathSum(int[][] grid) {
        if(grid == null || grid.length == 0) {
            return 0;
        }
        return helper(grid, 0, 0);
    }
    
    private int helper(int[][] grid, int i, int j) {
        int r = grid.length;
        int c = grid[0].length;
        if(i == r || j == c) {
            return Integer.MAX_VALUE;
        }
        if(i == r - 1 && j == c - 1) {
            return grid[i][j];
        }
        int down = helper(grid, i + 1, j);
        int right = helper(grid, i, j + 1);
        return Math.min(down, right) + grid[i][j];
    }
}

// Solution 2: Top Down DP Memoization
/**
Previous version
// Refer to
// https://loveforprogramming.quora.com/Backtracking-Memoization-Dynamic-Programming
// https://leetcode.com/problems/minimum-path-sum/discuss/23493/My-Java-solution-using-DP-with-memorization-(beats-about-98-submissions)/273665
// Runtime: 1 ms, faster than 99.80% of Java online submissions for Minimum Path Sum.
// Memory Usage: 35.4 MB, less than 100.00% of Java online submissions for Minimum Path Sum.
class Solution {
    public int minPathSum(int[][] grid) {
        if(grid == null || grid.length == 0) {
            return 0;
        }
        int m = grid.length;
        int n = grid[0].length;
        // Initial memo to store already computed back tracking
        // path value on each corresponding position =>
        // Each cell in memo mapping to cell in given grid and
        // store the minimum path sum calculated backwards from
        // bottom right corner cell to this cell
        int[][] memo = new int[m][n];
        return helper(grid, 0, 0, memo);
    }
    
    private int helper(int[][] grid, int i, int j, int[][] memo) {
        if(i < grid.length && j < grid[0].length) {
            if(i == grid.length - 1 && j == grid[0].length - 1) {
                // Store current cell value in memo if we reach
                // to bottom right and return it
                memo[i][j] = grid[i][j];
                return memo[i][j];
            }
            // Consult memo in case we have already calculated routes
            // for a particular cell, if the result not as initial as
            // 0, return it, otherwise use the usual recursion on
            // bottom and right direction
            if(memo[i][j] != 0) {
                return memo[i][j];
            }
            int bottom = helper(grid, i + 1, j, memo);
            int right = helper(grid, i, j + 1, memo);
            // Take the minimum value for a cell on a decision tree
            int min = Math.min(bottom, right);
            // Add cell value to the minimum value from left or right child
            memo[i][j] = min + grid[i][j];
            return memo[i][j];
        }
        return Integer.MAX_VALUE;
    }
}
*/
class Solution {
    public int minPathSum(int[][] grid) {
        if(grid == null || grid.length == 0) {
            return 0;
        }
        Integer[][] memo = new Integer[grid.length][grid[0].length];
        return helper(grid, 0, 0, memo);
    }
    
    private int helper(int[][] grid, int i, int j, Integer[][] memo) {
        int r = grid.length;
        int c = grid[0].length;
        if(i == r || j == c) {
            return Integer.MAX_VALUE;
        }
        if(i == r - 1 && j == c - 1) {
            return grid[i][j];
        }
        if(memo[i][j] != null) {
            return memo[i][j];
        }
        int down = helper(grid, i + 1, j, memo);
        int right = helper(grid, i, j + 1, memo);
        memo[i][j] = Math.min(down, right) + grid[i][j];
        return memo[i][j];
    }
}

// Solution 3: Bottom Up DP
class Solution {
    public int minPathSum(int[][] grid) {
        if(grid == null || grid.length == 0) {
            return 0;
        }
        int m = grid.length;
        int n = grid[0].length;
        int[][] dp = new int[m][n];
        dp[0][0] = grid[0][0];
        for(int i = 1; i < m; i++) {
            dp[i][0] = dp[i - 1][0] + grid[i][0];
        }
        for(int i = 1; i < n; i++) {
            dp[0][i] = dp[0][i - 1] + grid[0][i];
        }
        for(int i = 1; i < m; i++) {
            for(int j = 1; j < n; j++) {
                dp[i][j] = Math.min(dp[i - 1][j], dp[i][j - 1]) + grid[i][j];
            }
        }
        return dp[m - 1][n - 1];
    }
}

// Solution 4: Bottom Up DP without extra space
class Solution {
    public int minPathSum(int[][] grid) {
        if(grid == null || grid.length == 0) {
            return 0;
        }
        int m = grid.length;
        int n = grid[0].length;
        for(int i = 1; i < m; i++) {
            grid[i][0] = grid[i - 1][0] + grid[i][0];
        }
        for(int i = 1; i < n; i++) {
            grid[0][i] = grid[0][i - 1] + grid[0][i];
        }
        for(int i = 1; i < m; i++) {
            for(int j = 1; j < n; j++) {
                grid[i][j] = Math.min(grid[i - 1][j], grid[i][j - 1]) + grid[i][j];
            }
        }
        return grid[m - 1][n - 1];
    }
}



























































































https://leetcode.com/problems/minimum-path-sum/description/

Given a m x n grid filled with non-negative numbers, find a path from top left to bottom right, which minimizes the sum of all numbers along its path.

Note: You can only move either down or right at any point in time.

Example 1:


```
Input: grid = [[1,3,1],[1,5,1],[4,2,1]]
Output: 7
Explanation: Because the path 1 → 3 → 1 → 1 → 1 minimizes the sum.
```

Example 2:
```
Input: grid = [[1,2,3],[4,5,6]]
Output: 12
```

Constraints:
- m == grid.length
- n == grid[i].length
- 1 <= m, n <= 200
- 0 <= grid[i][j] <= 200
---
Attempt 1: 2023-08-22

Solution 1: Native DFS (10 min, TLE 25/61)
```
class Solution {
    public int minPathSum(int[][] grid) {
        return helper(grid, 0, 0);
    }
 
    private int helper(int[][] grid, int i, int j) {
        if(i >= grid.length || j >= grid[0].length) {
            return Integer.MAX_VALUE;
        }
        if(i == grid.length - 1 && j == grid[0].length - 1) {
            return grid[i][j];
        }
        int go_down = helper(grid, i + 1, j);
        int go_right = helper(grid, i, j + 1);
        return Math.min(go_down, go_right) + grid[i][j];
    }
}
```

Solution 2: DFS + Memoization (10 min)
```
class Solution {
    public int minPathSum(int[][] grid) {
        Integer[][] memo = new Integer[grid.length][grid[0].length];
        return helper(grid, 0, 0, memo);
    }
 
    private int helper(int[][] grid, int i, int j, Integer[][] memo) {
        if(i >= grid.length || j >= grid[0].length) {
            return Integer.MAX_VALUE;
        }
        if(i == grid.length - 1 && j == grid[0].length - 1) {
            return grid[i][j];
        }
        if(memo[i][j] != null) {
            return memo[i][j];
        }
        int go_down = helper(grid, i + 1, j, memo);
        int go_right = helper(grid, i, j + 1, memo);
        return memo[i][j] = Math.min(go_down, go_right) + grid[i][j];
    }
}
```

Solution 3: 2D DP (60 min)

Style 1: Initial 2D dp array as same size of 2D grid array

Note: Fully based on recursion "top" as (0, 0) and recursion "bottom" as (grid.length - 1, grid[0].length - 1) standard which exactly reflect the relation from Solution 1 Native DFS, in dp traversal, since compare to DFS solution, the dp solution will save much more time as no recursion stack push from "top" to "bottom" procedure like DFS, instead it directly process from "bottom" to "top", we should start with "bottom" and trace back to "top", the final solution will come out from dp[0][0]
```
class Solution {
    public int minPathSum(int[][] grid) {
        int m = grid.length;
        int n = grid[0].length;
        int[][] dp = new int[m][n];
        dp[m - 1][n - 1] = grid[m - 1][n - 1];
        // The last column special handle as from bottom to top
        // only based on cell from downwards
        for(int i = m - 2; i >= 0; i--) {
            dp[i][n - 1] = dp[i + 1][n - 1] + grid[i][n - 1];
        }
        // The last row special handle as from right to left
        // only based on cell from rightwards
        for(int j = n - 2; j >= 0; j--) {
            dp[m - 1][j] = dp[m - 1][j + 1] + grid[m - 1][j];
        }
        for(int i = m - 2; i >= 0; i--) {
            for(int j = n - 2; j >= 0; j--) {
                dp[i][j] = Math.min(dp[i + 1][j], dp[i][j + 1]) + grid[i][j];
            }
        }
        return dp[0][0];
    }
}
```

Refer to
https://leetcode.com/problems/minimum-path-sum/solutions/856314/sequential-thought-recursion-memo-dp-faster-easy-understanding/
```
//3. Dynamic Programming [ faster than 71.65% ] [ TC: (m*n) ]
class Solution {
public:
    int minCost(vector<vector<int>> &cost,int m, int n,vector<vector<int>> dp) { 
      dp[m-1][n-1]=cost[m-1][n-1];
      for(int i=n-2;i>=0;i--){
          dp[m-1][i]=dp[m-1][i+1]+cost[m-1][i];
      } 
      for(int i=m-2;i>=0;i--){
          dp[i][n-1]=dp[i+1][n-1]+cost[i][n-1];
      }
      for(int i=m-2;i>=0;i--){
          for(int j=n-2;j>=0;j--){
              dp[i][j]=cost[i][j]+min(dp[i+1][j],dp[i][j+1]);
          }
      }
      return dp[0][0];
    }
```

Style 2: Initial 2D dp array as one more column and row than original grid array

Note: Create one more row and one more column which helps uniform the formula make it even able to apply to last column and last row, even it strictly follow the conditions in Native DFS, still need to handle original last column and row specially, the difference between int[][] dp = new int[m][n] style is here the additional last column and row provide a way to do Math.min() as a uniform style as it always have a rightwards, downwards one to compare, which also mapping to base condition 1 in Native DFS
```
class Solution {
    public int minPathSum(int[][] grid) {
        int m = grid.length;
        int n = grid[0].length;
        // Create one more row and one more column which helps
        // uniform the formula make it even able to apply to
        // last column and last row
        int[][] dp = new int[m + 1][n + 1];
        // But this condition still required which also mapping
        // to the DFS solution 2nd base condition
        dp[m - 1][n - 1] = grid[m - 1][n - 1];
        // Initialize additional last column
        for(int i = m; i >= 0; i--) {
            dp[i][n] = Integer.MAX_VALUE;
        }
        // Initialize additional last row
        for(int j = n; j >= 0; j--) {
            dp[m][j] = Integer.MAX_VALUE;
        }
        // e.g Until now for input {{1,3,1},{1,5,1},{4,2,1}}
        // 2D DP array is below:
        // [0,          0,          0,          2147483647]
        // [0,          0,          0,          2147483647]
        // [0,          0,          1,          2147483647]
        // [2147483647, 2147483647, 2147483647, 2147483647]
        // Still need to handle original last column and row specially, the
        // difference between int[][] dp = new int[m][n] style is here the
        // additional last column and row provide a way to do Math.min()
        // as a uniform style as it always have a rightwards, downwards one
        // to compare, which also mapping to base condition 1 in Native DFS
        // Speical handle for original last column, start with i = m - 2 
        // because i = m - 1 plus n - 1 on 2nd dimension reserved for 
        // dp[m - 1][n - 1] which setup as grid[m - 1][n - 1]
        for(int i = m - 2; i >= 0; i--) {
            dp[i][n - 1] = Math.min(dp[i + 1][n - 1], dp[i][n]) + grid[i][n - 1];
        }
        // Speical handle for original last row, start with j = n - 2 
        // because j = n - 1 plus m - 1 on 1st dimension reserved for 
        // dp[m - 1][n - 1] which setup as grid[m - 1][n - 1]
        for(int j = n - 2; j >= 0; j--) {
            dp[m - 1][j] = Math.min(dp[m - 1][j + 1], dp[m][j]) + grid[m - 1][j];
        }
        for(int i = m - 2; i >= 0; i--) {
            for(int j = n - 2; j >= 0; j--) {
                dp[i][j] = Math.min(dp[i + 1][j], dp[i][j + 1]) + grid[i][j];
            }
        }
        return dp[0][0];
    }
}
```

Refer to
https://leetcode.wang/leetCode-64-Minimum-PathSum.html

解法二

这里我们直接用 grid 覆盖存，不去 new 一个 n 的空间了。
```
public int minPathSum(int[][] grid) {
    int m = grid.length;
    int n = grid[0].length;
    //由于第一行和第一列不能用我们的递推式，所以单独更新
    //更新第一行的权值
    for (int i = 1; i < n; i++) {
        grid[0][i] = grid[0][i - 1] + grid[0][i];
    }
    //更新第一列的权值
    for (int i = 1; i < m; i++) {
        grid[i][0] = grid[i - 1][0] + grid[i][0];
    }
    //利用递推式更新其它的
    for (int i = 1; i < m; i++) {
        for (int j = 1; j < n; j++) {
            grid[i][j] = Math.min(grid[i][j - 1], grid[i - 1][j]) + grid[i][j];
        }
    }
    return grid[m - 1][n - 1];
}
```
时间复杂度：O（m * n）。
空间复杂度：O（1）。


总

依旧是62题的扩展，理解了 62 题的话，很快就写出来了。
