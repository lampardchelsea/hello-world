/**
 Refer to
 https://leetcode.com/problems/shortest-common-supersequence/
  Given two strings str1 and str2, return the shortest string that has both str1 and str2 as subsequences.  
 If multiple answers exist, you may return any of them.
(A string S is a subsequence of string T if deleting some number of characters from T (possibly 0, 
and the characters are chosen anywhere from T) results in the string S.)
Example 1:
Input: str1 = "abac", str2 = "cab"
Output: "cabac"
Explanation: 
str1 = "abac" is a subsequence of "cabac" because we can delete the first "c".
str2 = "cab" is a subsequence of "cabac" because we can delete the last "ac".
The answer provided is the shortest such string that satisfies these properties.
Note:
1 <= str1.length, str2.length <= 1000
str1 and str2 consist of lowercase English letters.
*/

// Solution 1: Find LCS
// Refer to
// https://leetcode.com/problems/shortest-common-supersequence/discuss/312710/C++Python-Find-the-LCS/290904 
class Solution {
    public String shortestCommonSupersequence(String str1, String str2) {
        int len1 = str1.length();
        int len2 = str2.length();
        String result = "";
        String lcs = longestCommonSubsequence(str1, str2);
        int i = 0;
        int j = 0;
        for(int k = 0; k < lcs.length(); k++) {
            char c = lcs.charAt(k);
            while(i < len1 && str1.charAt(i) != c) {
                result += str1.charAt(i);
                i++;
            }
            while(j < len2 && str2.charAt(j) != c) {
                result += str2.charAt(j);
                j++;
            }
            result += c;
            i++;
            j++;
        }
        result += str1.substring(i);
        result += str2.substring(j);
        return result;
    }
    
    private String longestCommonSubsequence(String s1, String s2) {
        int len1 = s1.length();
        int len2 = s2.length();
        String[][] dp = new String[len1 + 1][len2 + 1];
        for(int i = 0; i <= len1; i++) {
            for(int j = 0; j <= len2; j++) {
                dp[i][j] = "";
            }
        }
        for(int i = 1; i <= len1; i++) {
            for(int j = 1; j <= len2; j++) {
                if(s1.charAt(i - 1) == s2.charAt(j - 1)) {
                    dp[i][j] = dp[i - 1][j - 1] + s1.charAt(i - 1);
                } else {
                    dp[i][j] = dp[i - 1][j].length() > dp[i][j - 1].length() ? dp[i - 1][j] : dp[i][j - 1];
                }
            }
        }
        return dp[len1][len2];
    }
}
