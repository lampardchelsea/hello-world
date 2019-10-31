/**
 Refer to
 https://leetcode.com/problems/longest-palindromic-subsequence/submissions/
 Given a string s, find the longest palindromic subsequence's length in s. 
 You may assume that the maximum length of s is 1000.

Example 1:
Input:
"bbbab"
Output:
4
One possible longest palindromic subsequence is "bbbb".

Example 2:
Input:
"cbbd"
Output:
2
One possible longest palindromic subsequence is "bb".
*/
// Solution 1: Native DFS (TLE)
// Refer to
// https://leetcode.com/problems/longest-palindromic-subsequence/discuss/99101/Straight-forward-Java-DP-solution
class Solution {
    public int longestPalindromeSubseq(String s) {
        return helper(s, 0, s.length() - 1);
    }
    
    private int helper(String s, int i, int j) {
        if(i > j) {
            return 0;
        }
        if(i == j) {
            return 1;
        }
        int result = 0;
        if(s.charAt(i) == s.charAt(j)) {
            result += helper(s, i + 1, j - 1) + 2;
        } else {
            result += Math.max(helper(s, i + 1, j), helper(s, i, j - 1));
        }
        return result;
    }
}


// Solution 2: Top down DP (DFS + Memoization)
// Refer to
// https://leetcode.com/problems/longest-palindromic-subsequence/discuss/99101/Straight-forward-Java-DP-solution
// Runtime: 18 ms, faster than 89.66% of Java online submissions for Longest Palindromic Subsequence.
// Memory Usage: 52.3 MB, less than 5.55% of Java online submissions for Longest Palindromic Subsequence.
class Solution {
    public int longestPalindromeSubseq(String s) {
        Integer[][] memo = new Integer[s.length()][s.length()];
        return helper(s, 0, s.length() - 1, memo);
    }
    
    private int helper(String s, int i, int j, Integer[][] memo) {
        if(memo[i][j] != null) {
            return memo[i][j];
        }
        if(i > j) {
            return 0;
        }
        if(i == j) {
            return 1;
        }
        int result = 0;
        if(s.charAt(i) == s.charAt(j)) {
            result += helper(s, i + 1, j - 1, memo) + 2;
        } else {
            result += Math.max(helper(s, i + 1, j, memo), helper(s, i, j - 1, memo));
        }
        memo[i][j] = result;
        return result;
    }
}


// Solution 3: DP
// Style 1: Similar to Longest Palindrome Substring way, loop i down and loop j up
// The return dp[0][len - 1] means pick up the start index i = 0 to the end index j = len - 1 dp result
// which cover the full length of the string
// Refer to
// https://leetcode.com/problems/longest-palindromic-subsequence/discuss/99101/Straight-forward-Java-DP-solution
class Solution {
    public int longestPalindromeSubseq(String s) {
        int len = s.length();
        int[][] dp = new int[len][len];
        for(int i = len - 1; i >= 0; i--) {
            dp[i][i] = 1;
            for(int j = i + 1; j < len; j++) {
                if(s.charAt(i) == s.charAt(j)) {
                    dp[i][j] = dp[i + 1][j - 1] + 2;
                } else {
                    dp[i][j] = Math.max(dp[i + 1][j], dp[i][j - 1]);
                }
            }
        }
        return dp[0][len - 1];
    }
}

// Style 2: Loop i up and loop j down
// The return dp[len - 1][0] means pick up the end index i = len - 1 to the start index j = 0 dp result
// which cover the full length of the string
// Refer to
// More readable since index i start from 1 to the end of string
// https://leetcode.com/problems/longest-palindromic-subsequence/discuss/99101/Straight-forward-Java-DP-solution/196860
class Solution {
    public int longestPalindromeSubseq(String s) {
        int len = s.length();
        int[][] dp = new int[len][len];
        for(int i = 0; i < len; i++) {
            dp[i][i] = 1;
        }
        // i start from 1
        for(int i = 1; i < len; i++) {
            for(int j = i - 1; j >= 0; j--) {
                if(s.charAt(i) == s.charAt(j)) {
                    dp[i][j] = dp[i - 1][j + 1] + 2;
                } else {
                    dp[i][j] = Math.max(dp[i - 1][j], dp[i][j + 1]);
                }
            }
        }
        return dp[len - 1][0];
    }
}
