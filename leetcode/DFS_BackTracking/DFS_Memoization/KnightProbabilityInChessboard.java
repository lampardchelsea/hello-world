/**
 Refer to
 https://leetcode.com/problems/knight-probability-in-chessboard/
 On an NxN chessboard, a knight starts at the r-th row and c-th column and attempts to make 
 exactly K moves. The rows and columns are 0 indexed, so the top-left square is (0, 0), and 
 the bottom-right square is (N-1, N-1).

A chess knight has 8 possible moves it can make, as illustrated below. Each move is two squares 
in a cardinal direction, then one square in an orthogonal direction.

Each time the knight is to move, it chooses one of eight possible moves uniformly at random 
(even if the piece would go off the chessboard) and moves there.

The knight continues moving until it has made exactly K moves or has moved off the chessboard. 
Return the probability that the knight remains on the board after it has stopped moving.

Example:
Input: 3, 2, 0, 0
Output: 0.0625
Explanation: There are two moves (to (1,2), (2,1)) that will keep the knight on the board.
From each of those positions, there are also two moves that will keep the knight on the board.
The total probability the knight stays on the board is 0.0625.

Note:
N will be between 1 and 25.
K will be between 0 and 100.
The knight always initially starts on the board.
*/

// Solution 1: LTE
// Refer to
// https://leetcode.com/problems/knight-probability-in-chessboard/discuss/113954/Evolve-from-recursive-to-dpbeats-94
class Solution {
    int[] dx = new int[]{1,2,2,1,-1,-2,-2,-1};
    int[] dy = new int[]{2,1,-1,-2,-2,-1,1,2};
    public double knightProbability(int N, int K, int r, int c) {
        return helper(N, K, r, c);
    }
    
    private double helper(int N, int K, int r, int c) {
        if(r < 0 || r > N - 1 || c < 0 || c > N - 1) {
            return 0;
        }
        if(K == 0) {
            return 1;
        }
        double result = 0;
        // 0.125 equal to 1/8 means every step you divide 8 to instead of
        // calculating all possible paths and divide 8^K as final course
        for(int i = 0; i < 8; i++) {
            result += 0.125 * helper(N, K - 1, r + dx[i], c + dy[i]);
        }
        return result;
    }
}

// Solution 2: DPS with memoization
// Refer to
// https://leetcode.com/problems/knight-probability-in-chessboard/discuss/113954/Evolve-from-recursive-to-dpbeats-94
class Solution {
    int[] dx = new int[]{1,2,2,1,-1,-2,-2,-1};
    int[] dy = new int[]{2,1,-1,-2,-2,-1,1,2};
    double[][][] dp;
    public double knightProbability(int N, int K, int r, int c) {
        dp = new double[N][N][K + 1];
        return helper(N, K, r, c, dp);
    }
    
    private double helper(int N, int K, int r, int c, double[][][] dp) {
        if(r < 0 || r > N - 1 || c < 0 || c > N - 1) {
            return 0;
        }
        if(K == 0) {
            return 1;
        }
        if(dp[r][c][K] != 0) {
            return dp[r][c][K];
        }
        double result = 0;
        for(int i = 0; i < 8; i++) {
            result += 0.125 * helper(N, K - 1, r + dx[i], c + dy[i], dp);
        }
        dp[r][c][K] = result;
        return result;
    }
}
