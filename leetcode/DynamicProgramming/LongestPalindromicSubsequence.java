/**
 * Refer to
 * https://leetcode.com/problems/longest-palindromic-subsequence/description/
 * Given a string s, find the longest palindromic subsequence's length in s. You may assume that the maximum length of s is 1000.

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
 *
 *
 * Solution
 * https://leetcode.com/problems/longest-palindromic-subsequence/discuss/99101/Straight-forward-Java-DP-solution
 * dp[i][j]: the longest palindromic subsequence’s length of substring(i, j)
    State transition:
    dp[i][j] = dp[i+1][j-1] + 2 if s.charAt(i) == s.charAt(j)
    otherwise, dp[i][j] = Math.max(dp[i+1][j], dp[i][j-1])
    Initialization: dp[i][i] = 1
*/

class Solution {
    public int longestPalindromeSubseq(String s) {
        if(s == null || s.length() == 0) {
            return 0;
        }
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
