/**
 Refer to
 https://leetcode.com/problems/palindromic-substrings/
 Given a string, your task is to count how many palindromic substrings in this string.

The substrings with different start indexes or end indexes are counted as different 
substrings even they consist of same characters.

Example 1:
Input: "abc"
Output: 3
Explanation: Three palindromic strings: "a", "b", "c".
 
Example 2:
Input: "aaa"
Output: 6
Explanation: Six palindromic strings: "a", "a", "a", "aa", "aa", "aaa".
 
Note:
The input string length won't exceed 1000.
*/
// Solution 1: Same way as Longest Palindrome Substring, instead of find maximum length, find how many substrings
// Refer to
// https://leetcode.com/problems/palindromic-substrings/discuss/105707/Java-DP-solution-based-on-longest-palindromic-substring
// Runtime: 6 ms, faster than 37.86% of Java online submissions for Palindromic Substrings.
// Memory Usage: 36.1 MB, less than 45.57% of Java online submissions for Palindromic Substrings.
class Solution {
    public int countSubstrings(String s) {
        int len = s.length();
        boolean[][] dp = new boolean[len][len];
        int count = 0;
        for(int i = len - 1; i >= 0; i--) {
            for(int j = i; j < len; j++) {
                if(j - i <= 2) {
                    if(s.charAt(i) == s.charAt(j)) {
                        dp[i][j] = true;
                    }
                } else {
                    if(s.charAt(i) == s.charAt(j)) {
                        dp[i][j] = dp[i + 1][j - 1];
                    }
                }
                if(dp[i][j]) {
                    count++;
                }
            }
        }
        return count;
    }
}

// Solution 2: Expand from center
// Refer to
// https://leetcode.com/problems/palindromic-substrings/discuss/105688/Very-Simple-Java-Solution-with-Detail-Explanation
class Solution {
    int count = 1;
    public int countSubstrings(String s) {
        int len = s.length();
        for(int i = 0; i < len - 1; i++) {
            expand(s, i, i);
            expand(s, i, i + 1);
        }
        return count;
    }
    
    private void expand(String s, int i, int j) {
        while(i >= 0 && j < s.length() && s.charAt(i) == s.charAt(j)) {
            count++;
            i--;
            j++;
        }
    }
}
