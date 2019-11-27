/**
 Refer to
 https://leetcode.com/problems/distinct-subsequences/
 Given a string S and a string T, count the number of distinct subsequences of S which equals T.

A subsequence of a string is a new string which is formed from the original string by deleting some (can be none) 
of the characters without disturbing the relative positions of the remaining characters. (ie, "ACE" is a 
subsequence of "ABCDE" while "AEC" is not).

Example 1:
Input: S = "rabbbit", T = "rabbit"
Output: 3
Explanation:
As shown below, there are 3 ways you can generate "rabbit" from S.
(The caret symbol ^ means the chosen letters)

rabbbit
^^^^ ^^
rabbbit
^^ ^^^^
rabbbit
^^^ ^^^

Example 2:
Input: S = "babgbag", T = "bag"
Output: 5
Explanation:
As shown below, there are 5 ways you can generate "bag" from S.
(The caret symbol ^ means the chosen letters)

babgbag
^^ ^
babgbag
^^    ^
babgbag
^    ^^
babgbag
  ^  ^^
babgbag
    ^^^
*/

// Solution 1: DP
// Refer to
// https://github.com/lampardchelsea/hello-world/blob/master/leetcode/String/DistinctSubsequences.java
/**
 * https://segmentfault.com/a/1190000003481216
 * 动态规划法
 * 复杂度
 * 时间 O(NM) 空间 O(NM)
 * 思路
 * 这题的思路和EditDistance有些相似，我们需要一个二维数组dp(i)(j)来记录长度为i的字串在长度为j的母串中出现的次数，这里长度都是从头算起的，
 * 而且遍历时，保持子串长度相同，先递增母串长度，母串最长时再增加一点子串长度重头开始计算母串。
 * 首先我们先要初始化矩阵，当子串长度为0时，所有次数都是1，当母串长度为0时，所有次数都是0.当母串子串都是0长度时，次数是1（因为都是空，相等）。
 * 接着，如果子串的最后一个字母和母串的最后一个字母不同，说明新加的母串字母没有产生新的可能性，可以沿用该子串在较短母串的出现次数，所以
 * dp(i)(j) = dp(i)(j-1)。如果子串的最后一个字母和母串的最后一个字母相同，说明新加的母串字母带来了新的可能性，我们不仅算上dp(i)(j-1)，
 * 也要算上新的可能性。那么如何计算新的可能性呢，其实就是在既没有最后这个母串字母也没有最后这个子串字母时，子串出现的次数，我们相当于为所有
 * 这些可能性都添加一个新的可能。所以，这时dp(i)(j) = dp(i)(j-1) + dp(i-1)(j-1)。下图是以rabbbit和rabbit为例的矩阵示意图。计算元素值时，
 * 当末尾字母一样，实际上是左方数字加左上方数字，当不一样时，就是左方的数字
*/
// For below solution just switch x-axis and y-axis
class Solution {
    public int numDistinct(String s, String t) {
        int m = s.length() + 1;
        int n = t.length() + 1;
        int[][] dp = new int[m][n];
        dp[0][0] = 1;
        for(int i = 1; i < m; i++) {
            dp[i][0] = 1;
        }
        for(int i = 1; i < n; i++) {
            dp[0][i] = 0;
        }
        for(int i = 1; i < m; i++) {
            for(int j = 1; j < n; j++) {
                if(s.charAt(i - 1) == t.charAt(j - 1)) {
                    dp[i][j] = dp[i - 1][j - 1] + dp[i - 1][j];
                } else {
                    dp[i][j] = dp[i - 1][j];
                }
            }
        }
        return dp[m - 1][n - 1];
    }
}

