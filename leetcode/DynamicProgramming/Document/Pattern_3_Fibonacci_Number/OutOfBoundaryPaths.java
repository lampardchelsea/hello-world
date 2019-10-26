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
// Time complexity : O(4^n) Size of recursion tree will be 4^n. Here, n refers to the number of moves allowed.
// Space complexity : O(n). The depth of the recursion tree can go upto n.
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
// https://leetcode.com/articles/out-of-boundary-paths/
/**
 In the brute force approach, while going through the various branches of the recursion tree, we could reach 
 the same position with the same number of moves left. Thus, a lot of redundant function calls are made with 
 the same set of parameters leading to a useless increase in runtime. We can remove this redundancy by making 
 use of a memoization array, memo. memo[i][j][k] is used to store the number of possible moves leading to 
 a path out of the boundary if the current position is given by the indices (i, j) and number of moves left is k.
 Thus, now if a function call with some parameters is repeated, the memo array will already contain valid 
 values corresponding to that function call resulting in pruning of the search space.
*/
// Complexity Analysis
// Time complexity : O(m*n*N). We need to fill the memo array once with dimensions m x n x N. Here, m, n refer 
// to the number of rows and columns of the given grid respectively. N refers to the total number of allowed moves.
// Space complexity : O(m*n*N). memo array of size m*n*N is used.
class Solution {
    int M = 1000000007;
    public int findPaths(int m, int n, int N, int i, int j) {
        Integer[][][] memo = new Integer[m][n][N + 1];
        return helper(m, n, N, i, j, memo);
    }
    
    int[] dx = new int[]{0,0,1,-1};
    int[] dy = new int[]{1,-1,0,0};
    private int helper(int m, int n, int N, int i, int j, Integer[][][] memo) {
        // Before use up N steps and out of grid will get 1 more solution
        if(i < 0 || i >= m || j < 0 || j >= n) {
            return 1;
        }
        // Use up N steps and not able get abord get no solution
        if(N == 0) {
            return 0;
        }
        if(memo[i][j][N] != null) {
            return memo[i][j][N];
        }
        int result = 0;
        for(int k = 0; k < 4; k++) {
            result += helper(m, n, N - 1, i + dx[k], j + dy[k], memo) % M;
            result = result % M;
        }
        memo[i][j][N] = result;
        return result;
    }
}

// Solution 3: Bottom up DP
// Refer to
// https://leetcode.com/articles/out-of-boundary-paths/
// https://leetcode.com/problems/out-of-boundary-paths/discuss/346250/C%2B%2B-DP-Solution-with-Proper-Explanation-and-Intuition
// https://leetcode.com/problems/out-of-boundary-paths/discuss/325330/Java-3D-DP-solution(Very-easy-to-understand)/302632
/**
 Dynamic Programming Approach
Let's represent initial position by start_x, start_y
Adjacent Cells of i, j = (i + 1, j) , (i - 1, j) , (i, j + 1), (i, j - 1)
Let's define the state first
dp[k][i][j] = Num of Ways in which I can end up at position i, j in k moves from starting position. 
That also means, Num of Ways I can end up at adjacent cells of i, j in k-1 moves from starting position 
and then take the kth step to reach cell i, j.

Now, If Previous Position was on Board then 2 cases arise:
1. If curr Pos is on board then we store the Num of ways to reach i, j.
2. Else, if curr Pos is not on board but prev pos was on board then we can reach the curr Pos which is 
out of board in the num of ways in which prev adjacent cells. This will contribute to final answer that 
is the number of paths which go out of Boundary.

If previous Position was out of board
Then, we don't do anything as we only count the first time ball moves out of board.

So, basically, If the ball is inside board at prev position which we can arrive at from starting position 
using k-1 moves and the current position is also inside board then it contributes to a path that takes k moves. 
If we were inside the board at previous position but at current position we falls off the board, then it contributes 
to the final answer.
Count when first time we fall off board.

//Initialization
dp[0][start_x][start_y] = 1 becoz without making any move we are already in position. Num of Ways in which 
I can end up at starting position in 0 moves from starting position. There is 1 way which is no Move.

The other values of table in initialization are set to 0 becoz without making any move we can't reach any other 
place on grid apart from start pos.
*/
class Solution {
    public int findPaths(int m, int n, int N, int i, int j) {
        int M = 1000000007;
        int[][][] dp = new int[N + 1][m][n];
        int[] dx = new int[]{0,0,1,-1};
        int[] dy = new int[]{1,-1,0,0};
        // Be careful, the outside for loop must be control for N
        // which means in each step, we will have full choice on 
        // each direction of current position in board, and will
        // repeat this for N times.
        for(int k = 1; k <= N; k++) {
            for(int r = 0; r < m; r++) {
                for(int c = 0; c < n; c++) {
                    for(int t = 0; t < 4; t++) {
                        int x = r + dx[t];
                        int y = c + dy[t];
                        // If next position [x, y] out of the board, will have 
                        // 1 more solution for current position [r, c]
                        if(x < 0 || x >= m || y < 0 || y >= n) {
                            dp[k][r][c] += 1;
                        } else {
                            // Or if next position [x, y] still in the board, then
                            // previous step as [k - 1] at previous position at 
                            // [x, y] should reversely contribute to next step [k] 
                            // at position [r, c]
                            /**
                             Also refer to
                             https://leetcode.com/problems/out-of-boundary-paths/discuss/325330/Java-3D-DP-solution(Very-easy-to-understand)/302632
                             We can see the move in the opposite direction, from boundary to [r, c]. This can make the solution 
                             easier to understand. At move (k-1) in [x, y] we have dp[k-1][x][y] paths, then at move k in [r, c], 
                             we add the value of dp[k-1][x][y] for all four directions. For example, at move (k-1) we have N paths 
                             to the point on the left of [r, c] (This point is one out of four possible adjcent positions for [x, y], 
                             because the relation as if at step (k-1) at [r, c] and step k at [x, y] based on previous code as 
                             int x = r + dx[t], int y = c + dy[t], reversely, if at step (k-1) at [x, y] certainly will have step k 
                             at [r, c]), then for each of the N path, we can add 1 more move to reach [r, c]. So we totally add 
                             N * 1 = N to dp[k][r][c]. Which means dp[k][r][c] reversely can present by using dp[k - 1][x][y].
                            */
                            dp[k][r][c] = (dp[k][r][c] + dp[k - 1][x][y]) % M;
                        }
                    }                    
                }
            }
        }
        return dp[N][i][j];
    }
}

/**
 One more solution as reference for Solution 3
 https://leetcode.com/problems/out-of-boundary-paths/discuss/102983/JAVA-DP-solution-O(N*m*n)-Excellent-DP-Question!
 public int findPaths(int m, int n, int N, int i, int j) {
        int TAG = 1000000007;
        if (N == 0) return 0;
        if (m == 1 && n == 1) {
            return 4;
        }
        if (m ==1) {
            return solve(n, N, j, TAG);
        }
        if (n == 1) {
            return solve(m, N, i, TAG);
        }
        long[][][] dp = new long[N + 1][m][n];
        for (int k = 1; k <= N; k ++) {
            for (int p = 0; p < m; p ++) {
                for (int q = 0; q < n; q ++) {
                    if (p == 0) {
                        if (q == 0) {
                            dp[k][p][q] = dp[k - 1][p][q + 1] + dp[k - 1][p + 1][q] + 2;
                        }else if(q == n - 1) {
                            dp[k][p][q] = dp[k - 1][p][q - 1] + dp[k - 1][p + 1][q] + 2;
                        }else{
                            dp[k][p][q] = dp[k - 1][p][q + 1] + dp[k - 1][p + 1][q] + dp[k - 1][p][q - 1] + 1;
                        }
                    }else if (p == m - 1) {
                        if (q == 0) {
                            dp[k][p][q] = dp[k - 1][p][q + 1] + dp[k - 1][p - 1][q] + 2;
                        }else if(q == n - 1) {
                            dp[k][p][q] = dp[k - 1][p][q - 1] + dp[k - 1][p - 1][q] + 2;
                        }else{
                            dp[k][p][q] = dp[k - 1][p][q + 1] + dp[k - 1][p - 1][q] + dp[k - 1][p][q - 1] + 1;
                        }
                    }else if (q == 0) {
                        dp[k][p][q] = dp[k - 1][p][q + 1] + dp[k - 1][p - 1][q] + dp[k - 1][p + 1][q] + 1;
                    }else if (q == n - 1){
                        dp[k][p][q] = dp[k - 1][p + 1][q] + dp[k - 1][p - 1][q] + dp[k - 1][p][q - 1] + 1;
                    }else{
                        dp[k][p][q] = dp[k - 1][p + 1][q] + dp[k - 1][p - 1][q] + dp[k - 1][p][q - 1] + dp[k - 1][p][q + 1];
                    }
                    if (dp[k][p][q] > TAG) dp[k][p][q] %= TAG;
                }
            }
        }
        return (int) (dp[N][i][j] % TAG);
    }

    private int solve(int n, int N, int j, int TAG) {
        long[][] dp = new long[N + 1][n];
        for (int p = 1; p <= N; p ++) {
            for (int q = 0; q < n; q ++) {
                if (q == 0) {
                    dp[p][q] = dp[p - 1][q + 1] + 3;
                }else if (q == n - 1) {
                    dp[p][q] = dp[p - 1][q - 1] + 3;
                }else{
                    dp[p][q] = dp[p - 1][q - 1] + dp[p - 1][q + 1] + 2;
                }
            }
        }
        return (int) (dp[N][j] % TAG);
    }
*/

// Solution 4: Bottom up DP optimization
// Refer to
// https://leetcode.com/articles/out-of-boundary-paths/
/**
 The idea behind this approach is that if we can reach some position in x moves, we can reach all its adjacent 
 positions in x+1 moves. Based on this idea, we make use of a 2-D dp array to store the number of ways in which 
 a particular position can be reached. dp[i][j] refers to the number of ways the position corresponding to the 
 indices (i,j) can be reached given some particular number of moves.

Now, if the current dp array stores the number of ways the various positions can be reached by making use of x-1 
moves, in order to determine the number of ways the position (i,j) can be reached by making use of xx moves, we 
need to update the corresponding dpdp entry as dp[i][j] = dp[i-1][j] + dp[i+1][j] + dp[i][j-1] + dp[i][j+1] taking 
care of boundary conditions. This happens because we can reach the index (i,j) from any of the four adjacent 
positions and the total number of ways of reaching the index (i,j) in x moves is the sum of the ways of reaching 
the adjacent positions in x-1 moves.

But, if we alter the dp array, now some of the entries will correspond to x-1 moves and the updated ones will 
correspond to x moves. Thus, we need to find a way to tackle this issue. So, instead of updating the dp array for 
the current(x) moves, we make use of a temporary 2-D array temptemp to store the updated results for x moves, 
making use of the results obtained for dp array corresponding to x-1 moves. After all the entries for all the 
positions have been considered for x moves, we update the dp array based on temp. Thus, dp now contains the 
entries corresponding to x moves.

Thus, we start off by considering zero move available for which we make an initial entry of dp[x][y] = 1
((x,y)is the initial position), since we can reach only this position in zero move. Then, we increase the number 
of moves to 1 and update all the dp entries appropriately. We do so for all the moves possible from 1 to N.

In order to update count, which indicates the total number of possible moves which lead an out of boundary path, 
we need to perform the update only when we reach the boundary. We update the count as count = count + dp[i][j], 
where (i,j) corresponds to one of the boundaries. But, if (i,j) is simultaneously a part of multiple boundaries, 
we need to add the dp[i][j] factor multiple times(same as the number of boundaries to which (i,j) belongs).

After we are done with all the N moves, count gives the required result.
*/
// Runtime: 9 ms, faster than 46.75% of Java online submissions for Out of Boundary Paths.
// Memory Usage: 36.3 MB, less than 25.00% of Java online submissions for Out of Boundary Paths.
class Solution {
    public int findPaths(int m, int n, int N, int x, int y) {
        int M = 1000000007;
        int dp[][] = new int[m][n];
        dp[x][y] = 1;
        int count = 0;
        for (int moves = 1; moves <= N; moves++) {
            int[][] temp = new int[m][n];
            for (int i = 0; i < m; i++) {
                for (int j = 0; j < n; j++) {
                    if (i == m - 1)
                        count = (count + dp[i][j]) % M;
                    if (j == n - 1)
                        count = (count + dp[i][j]) % M;
                    if (i == 0)
                        count = (count + dp[i][j]) % M;
                    if (j == 0)
                        count = (count + dp[i][j]) % M;
                    temp[i][j] = (((i > 0 ? dp[i - 1][j] : 0) + (i < m - 1 ? dp[i + 1][j] : 0)) % M 
                                  + ((j > 0 ? dp[i][j - 1] : 0) + (j < n - 1 ? dp[i][j + 1] : 0)) % M) % M;
                }
            }
            dp = temp;
        }
        return count;
    }
}
