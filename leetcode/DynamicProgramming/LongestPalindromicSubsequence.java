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

// Refer to
// Good explaination
// https://leetcode.com/problems/longest-palindromic-subsequence/discuss/99101/Straight-forward-Java-DP-solution/103147
/**
 Great solution, very concise. At first I see the 2-D array and think matrix (rows and columns) this confused me. 
 But then I realized the 2-D array is actually left and right indexes in the string and that led me to see the 
 solution clearly. If anyone else had trouble understanding this here is some more explanation and code.

You will be considering substrings starting at left and ending at right (inclusive). To do this you will iterate 
over all lengths from 1 to n and within each length iterate over staring (or left) position. The key is that you 
get the answers for a single length at all start positions before going to the next length because the dp depends 
on the answers from shorter lengths. If you do it this way you will have 3 cases to consider on every iteration, 
pick the one with the highest value.

the answer from removing the left edge char
the answer from removing the right edge char
and if the left and right chars are equal, 2 plus the answer from removing both left and right
the 3rd case is how the answer grows. After iterating through all you will have performed O(n^2) checks and used 
O(n^2) memory, the answer is where left is 0 and right is n-1 which will be your very last calculation.

To show the 3 cases more clearly I break out lengths 1 and 2 because their logic is simplified.
*/
