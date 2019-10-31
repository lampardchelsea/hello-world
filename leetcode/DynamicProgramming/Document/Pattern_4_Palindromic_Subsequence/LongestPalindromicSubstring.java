/**
 Refer to
 https://leetcode.com/problems/longest-palindromic-substring/
 Given a string s, find the longest palindromic substring in s. 
 You may assume that the maximum length of s is 1000.

Example 1:
Input: "babad"
Output: "bab"
Note: "aba" is also a valid answer.

Example 2:
Input: "cbbd"
Output: "bb"
*/

// Solution 1: Brute Force
// Runtime: 1298 ms, faster than 5.00% of Java online submissions for Longest Palindromic Substring.
// Memory Usage: 35.9 MB, less than 100.00% of Java online submissions for Longest Palindromic Substring.
class Solution {
    public String longestPalindrome(String s) {
        int len = s.length();
        int maxLen = 0;
        int start = 0;
        // i is start index, j is (real end index + 1)
        // to get substring need s.substring(i, j)
        for(int i = 0; i < len; i++) {
            for(int j = i + 1; j <= len; j++) {
                if(isPalindrome(s, i, j - 1)) {
                    if(j - i > maxLen) {
                        maxLen = j - i;
                        start = i;
                    }
                }
            }
        }
        return s.substring(start, start + maxLen);
    }
    
    private boolean isPalindrome(String s, int m, int n) {
        while(m < n) {
            if(s.charAt(m) != s.charAt(n)) {
                return false;
            }
            m++;
            n--;
        }
        return true;
    }
}

// Solution 2: Spread From Center

// Solution 3: DP
