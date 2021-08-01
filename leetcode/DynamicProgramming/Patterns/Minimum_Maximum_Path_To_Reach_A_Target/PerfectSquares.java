/**
 * Refer to
 * https://leetcode.com/problems/perfect-squares/description/
 * http://www.lintcode.com/en/problem/perfect-squares/
 * Given a positive integer n, find the least number of perfect square numbers 
   (for example, 1, 4, 9, 16, ...) which sum to n.
   For example, given n = 12, return 3 because 12 = 4 + 4 + 4; given n = 13, 
   return 2 because 13 = 4 + 9.
*/

// DFS Solution but not able to apply memoization and also TLE
class Solution {
    public int numSquares(int n) {
        return helper(n, n, 0);
    }
    
    private int helper(int n, int cur, int step) {
        if(cur == 0) {
            return step;
        }
        if(cur < 0) {
            return Integer.MAX_VALUE;
        }
        int min = n + 1;
        for(int i = 1; i * i <= cur; i++) {
            min = Math.min(min, helper(n, cur - i * i, step + 1));
        }
        return min;
    }
}

// Solution 1: Native DFS (TLE)
// Refer to
// https://leetcode.com/problems/perfect-squares/discuss/1173545/Java-or-All-3-Solutions%3A-Recursive-Memoized-and-DP
class Solution {
    public int numSquares(int n) {
        return helper(n);
    }
    
    private int helper(int cur) {
        if(cur < 0) {
            return Integer.MAX_VALUE;
        }
        if(cur == 0) {
            return 0;
        }
        int min = cur + 1;
        for(int i = 1; i * i <= cur; i++) {
            min = Math.min(min, helper(cur - i * i) + 1);
        }
        return min;
    }
}

// Solution 2: Top Down DP Memoization
// Refer to
// https://leetcode.com/problems/perfect-squares/discuss/1173545/Java-or-All-3-Solutions%3A-Recursive-Memoized-and-DP
// Style 1:
class Solution {
    public int numSquares(int n) {
        int[] memo = new int[n + 1];
        return helper(n, memo);
    }
    
    private int helper(int cur, int[] memo) {
        if(cur < 0) {
            return Integer.MAX_VALUE;
        }
        if(cur == 0) {
            return 0;
        }
        if(memo[cur] > 0) {
            return memo[cur];
        }
        int min = cur + 1;
        for(int i = 1; i * i <= cur; i++) {
            min = Math.min(min, helper(cur - i * i, memo));
        }
        memo[cur] = min + 1;
        return min + 1;
    }
}

// Style 2:
class Solution {
    public int numSquares(int n) {
        int[] memo = new int[n + 1];
        return helper(n, memo);
    }
    
    private int helper(int cur, int[] memo) {
        if(memo[cur] > 0) {
            return memo[cur];
        }
        if(cur < 0) {
            return Integer.MAX_VALUE;
        }
        if(cur == 0) {
            return 0;
        }
        int min = cur + 1;
        for(int i = 1; i * i <= cur; i++) {
            min = Math.min(min, helper(cur - i * i, memo) + 1);
        }
        memo[cur] = min;
        return min;
    }
}

// Solution 3: Bottom Up DP
// Refer to
// https://leetcode.com/problems/perfect-squares/discuss/71495/An-easy-understanding-DP-solution-in-Java/73784
/**
dp[n] indicates that the perfect squares count of the given n, and we have:

dp[0] = 0 
dp[1] = dp[0]+1 = 1
dp[2] = dp[1]+1 = 2
dp[3] = dp[2]+1 = 3
dp[4] = Min{ dp[4-1*1]+1, dp[4-2*2]+1 } 
      = Min{ dp[3]+1, dp[0]+1 } 
      = 1				
dp[5] = Min{ dp[5-1*1]+1, dp[5-2*2]+1 } 
      = Min{ dp[4]+1, dp[1]+1 } 
      = 2
						.
						.
						.
dp[13] = Min{ dp[13-1*1]+1, dp[13-2*2]+1, dp[13-3*3]+1 } 
       = Min{ dp[12]+1, dp[9]+1, dp[4]+1 } 
       = 2
						.
						.
						.
dp[n] = Min{ dp[n - i*i] + 1 },  n - i*i >=0 && i >= 1
*/
// Style 1:
class Solution {
    public int numSquares(int n) {    
        // State: represent how many numbers to construct number 'n'
        int[] dp = new int[n + 1];
        // Initialize
        for(int i = 0; i < n + 1; i++) {
            dp[i] = Integer.MAX_VALUE;
        }
        // n is positive integer, so dp[0] is 0 means no combination to reach this target
        dp[0] = 0;
        // function
        for(int i = 1; i < n + 1; i++) {
            int j = 1;
            int min = Integer.MAX_VALUE;
            while(i - j * j >= 0) {
                min = Math.min(dp[i - j * j] + 1, min);
                j++;
            }
            dp[i] = min;
        }
        // answer
        return dp[n];
    }
}

// Style 2:
class Solution {
    public int numSquares(int n) {
        int[] dp = new int[n + 1];
        Arrays.fill(dp, Integer.MAX_VALUE);
        dp[0] = 0;
        for(int i = 1; i <= n; i++) {
            for(int j = 1; j * j <= i; j++) {
                dp[i] = Math.min(dp[i], dp[i - j * j] + 1);
            }
        }
        return dp[n];
    }
}
