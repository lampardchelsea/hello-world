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
