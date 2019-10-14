/**
 Refer to
 https://leetcode.com/problems/ones-and-zeroes/
 In the computer world, use restricted resource you have to generate maximum benefit is 
 what we always want to pursue.
For now, suppose you are a dominator of m 0s and n 1s respectively. On the other hand, 
there is an array with strings consisting of only 0s and 1s.
Now your task is to find the maximum number of strings that you can form with given m 0s 
and n 1s. Each 0 and 1 can be used at most once.

Note:
The given numbers of 0s and 1s will both not exceed 100
The size of given string array won't exceed 600.

Example 1:
Input: Array = {"10", "0001", "111001", "1", "0"}, m = 5, n = 3
Output: 4
Explanation: This are totally 4 strings can be formed by the using of 5 0s and 3 1s, which are “10,”0001”,”1”,”0”

Example 2:
Input: Array = {"10", "0", "1"}, m = 1, n = 1
Output: 2
Explanation: You could form "10", but then you'd have nothing left. Better form "0" and "1".
*/

// Solution 1: Native DFS -> Style same as SolveKnapsack.java -> TLE
// Refer to
// https://github.com/lampardchelsea/hello-world/blob/master/leetcode/DynamicProgramming/Document/Pattern_1_0_1_Knapsack/SolveKnapsack.java
// https://leetcode.com/problems/ones-and-zeroes/discuss/297910/Java-Brute-Force-With-Memoization
class Solution {
    public int findMaxForm(String[] strs, int m, int n) {
        return helper(strs, m, n, 0);
    }
    
    private int helper(String[] strs, int m, int n, int index) {
        if(index == strs.length) {
            return 0;
        }        
        String str = strs[index];
        int ones = 0;
        int zeros = 0;
        for(int i = 0; i < str.length(); i++) {
            if(str.charAt(i) == '1') {
                ones++;
            } else {
                zeros++;
            }
        }
        int choose = 0;
        if(m >= zeros && n >= ones) {
            choose = 1 + helper(strs, m - zeros, n - ones, index + 1);
        }
        int notChoose = helper(strs, m, n, index + 1);
        return Math.max(choose, notChoose);
    }
}

// Solution 2: DFS + Memoization (Use 3D array or Map<String, Integer> to store index, m, n together as key)
// Refer to
// https://leetcode.com/problems/ones-and-zeroes/discuss/297910/Java-Brute-Force-With-Memoization
// https://leetcode.com/problems/ones-and-zeroes/discuss/376956/Very-Very-Easy-CPP-TOP-DOWN-with-Memoization
// Style 1: Use 3D array
class Solution {
    public int findMaxForm(String[] strs, int m, int n) {
        Integer[][][] memo = new Integer[strs.length + 1][m + 1][n + 1];
        return helper(strs, m, n, 0, memo);
    }
    
    private int helper(String[] strs, int m, int n, int index, Integer[][][] memo) {
        if(memo[index][m][n] != null) {
            return memo[index][m][n];
        }
        if(index == strs.length) {
            return 0;
        }        
        String str = strs[index];
        int ones = 0;
        int zeros = 0;
        for(int i = 0; i < str.length(); i++) {
            if(str.charAt(i) == '1') {
                ones++;
            } else {
                zeros++;
            }
        }
        int choose = 0;
        if(m >= zeros && n >= ones) {
            choose = 1 + helper(strs, m - zeros, n - ones, index + 1, memo);
        }
        int notChoose = helper(strs, m, n, index + 1, memo);
        int result = Math.max(choose, notChoose);
        memo[index][m][n] = result;
        return result;
    }
}

// Style 2: Use Map 
class Solution {
    public int findMaxForm(String[] strs, int m, int n) {
        Map<String, Integer> memo = new HashMap<String, Integer>();
        return helper(strs, m, n, 0, memo);
    }
    
    private int helper(String[] strs, int m, int n, int index, Map<String, Integer> memo) {
        String key = index + "_" + m + "_" + n;
        if(memo.containsKey(key)) {
            return memo.get(key);
        }
        if(index == strs.length) {
            return 0;
        }        
        String str = strs[index];
        int ones = 0;
        int zeros = 0;
        for(int i = 0; i < str.length(); i++) {
            if(str.charAt(i) == '1') {
                ones++;
            } else {
                zeros++;
            }
        }
        int choose = 0;
        if(m >= zeros && n >= ones) {
            choose = 1 + helper(strs, m - zeros, n - ones, index + 1, memo);
        }
        int notChoose = helper(strs, m, n, index + 1, memo);
        int result = Math.max(choose, notChoose);
        memo.put(key, result);
        return result;
    }
}

// Solution 3: 3D Bottom Up DP
// Refer to
// https://leetcode.com/problems/ones-and-zeroes/discuss/95807/0-1-knapsack-detailed-explanation.
// https://leetcode.com/problems/ones-and-zeroes/discuss/95814/c%2B%2B-DP-solution-with-comments
// https://leetcode.com/problems/ones-and-zeroes/discuss/95814/c++-DP-solution-with-comments/100383
class Solution {
    public int findMaxForm(String[] strs, int m, int n) {
        // dp[i][j][k] means the maximum number of strings we can get from the 
        // first i argument strs using limited j number of '0's and k number of '1's.
        int[][][] dp = new int[strs.length + 1][m + 1][n + 1];
        // If not pick any strings, as i = 0, dp[i][j][k] = 0; no need to initialize
        // start with i = 1
        for(int i = 1; i <= strs.length; i++) {
            int ones = countOnes(strs[i - 1]);
            int zeros = strs[i - 1].length() - ones;
            /**
             Refer to
             https://leetcode.com/problems/ones-and-zeroes/discuss/95814/c++-DP-solution-with-comments/100383
             There are two possible ways to form the max number of strings 
             with j 0's and k 1's regarding s: we either form s or skip it.
             If we skip s, memo[j][k] shouldn't change. Otherwise, we form 
             s with numZeroes 0's and numOnes 1's, which leaves us 
             j - numZeroes 0's and j - numOnes 1's to work with for all previous 
             strings. How many strings can we form with j - numZeroes 0's 
             and k - numOnes 1's? It's memo[j - numZeroes][k - numOnes] which 
             was calculated in previous rounds, so just add 1 to that. We choose 
             to form s or skip it based on which gives us a larger memo[j][k]
            */
            for(int j = 0; j <= m; j++) {
                for(int k = 0; k <= n; k++) {
                    dp[i][j][k] = dp[i - 1][j][k];
                    if(j >= zeros && k >= ones) {
                        dp[i][j][k] = Math.max(dp[i][j][k], 1 + dp[i - 1][j - zeros][k - ones]); 
                    }
                }
            }
        }
        return dp[strs.length][m][n];  
    }
    
    private int countOnes(String str) {
        int ones = 0;
        for(int i = 0; i < str.length(); i++) {
            if(str.charAt(i) == '1') {
                ones++;
            }
        }
        return ones;
    }
}

// Solution 4: 1D Bottom up DP
// Refer to
// https://leetcode.com/problems/ones-and-zeroes/discuss/95807/0-1-knapsack-detailed-explanation.
// https://github.com/lampardchelsea/hello-world/blob/master/leetcode/DynamicProgramming/Document/Pattern_1_0_1_Knapsack/EqualSubsetSumPartition.java
class Solution {
    public int findMaxForm(String[] strs, int m, int n) {
        int[][] dp = new int[m + 1][n + 1];
        for(int i = 1; i <= strs.length; i++) {
            int ones = countOnes(strs[i - 1]);
            int zeros = strs[i - 1].length() - ones;
            // Why we need to loop backwards when down-grade 3D DP to 2D DP
            // Refer to
            // https://github.com/lampardchelsea/hello-world/blob/master/leetcode/DynamicProgramming/Document/Pattern_1_0_1_Knapsack/EqualSubsetSumPartition.java
            for(int j = m; j >= 0; j--) {
                for(int k = n; k >= 0; k--) {
                    if(j >= zeros && k >= ones) {
                        dp[j][k] = Math.max(dp[j][k], 1 + dp[j - zeros][k - ones]); 
                    }
                }
            }
        }
        return dp[m][n];
    }
    
    private int countOnes(String str) {
        int ones = 0;
        for(int i = 0; i < str.length(); i++) {
            if(str.charAt(i) == '1') {
                ones++;
            }
        }
        return ones;
    }
}


