/** 
 Refer to
 https://leetcode.com/problems/out-of-boundary-paths/
 There is an m by n grid with a ball. Given the start coordinate (i,j) of the ball, you can move the 
 ball to adjacent cell or cross the grid boundary in four directions (up, down, left, right). 
 However, you can at most move N times. Find out the number of paths to move the ball out of grid 
 boundary. The answer may be very large, return it after mod 109 + 7.
 
Example 1:
Input: m = 2, n = 2, N = 2, i = 0, j = 0
Output: 6
Explanation:
*/

// Solution 1: Native DFS (TLE)
// Refer to
// https://leetcode.com/articles/out-of-boundary-paths/
/**
 Approach #1 Brute Force [Time Limit Exceeded]
In the brute force approach, we try to take one step in every direction and decrement the number of 
pending moves for each step taken. Whenever we reach out of the boundary while taking the steps, 
we deduce that one extra path is available to take the ball out.
In order to implement the same, we make use of a recursive function findPaths(m,n,N,i,j) which takes 
the current number of moves(NN) along with the current position((i,j)(i,j) as some of the parameters 
and returns the number of moves possible to take the ball out with the current pending moves from the 
current position. Now, we take a step in every direction and update the corresponding indices involved 
along with the current number of pending moves.
Further, if we run out of moves at any moment, we return a 0 indicating that the current set of moves 
doesn't take the ball out of boundary.
*/
// Complexity Analysis
Time complexity : O(4^n) Size of recursion tree will be 4^n. Here, nn refers to the number of moves allowed.
Space complexity : O(n). The depth of the recursion tree can go upto n.
class Solution {
    public int findPaths(int m, int n, int N, int i, int j) {
        return helper(m, n, N, i, j);
    }
    
    int[] dx = new int[]{0,0,1,-1};
    int[] dy = new int[]{1,-1,0,0};
    private int helper(int m, int n, int N, int i, int j) {
        // Before use up N steps and out of grid will get 1 more solution
        if(i < 0 || i >= m || j < 0 || j >= n) {
            return 1;
        }
        // Use up N steps and not able get abord get no solution
        if(N == 0) {
            return 0;
        }
        int result = 0;
        for(int k = 0; k < 4; k++) {
            result += helper(m, n, N - 1, i + dx[k], j + dy[k]);
        }
        return result;
    }
}

// Solution 2: Top down DP (DFS + Memoization)
// Refer to
// 
