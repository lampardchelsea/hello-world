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
// Runtime: 8 ms, faster than 66.62% of Java online submissions for Longest Palindromic Substring.
// Memory Usage: 36.9 MB, less than 98.79% of Java online submissions for Longest Palindromic Substring.
class Solution {
    public String longestPalindrome(String s) {
        int len = s.length();
        String result = "";
        for(int i = 0; i < len; i++) {
            String s1 = extend(s, i, i);
            String s2 = extend(s, i, i + 1);
            if(s1.length() > result.length()) {
                result = s1;
            }
            if(s2.length() > result.length()) {
                result = s2;
            }
        }
        return result;
    }
    
    private String extend(String s, int i, int j) {
        while(i >= 0 && j < s.length()) {
            if(s.charAt(i) != s.charAt(j)) {
                break;
            }
            i--;
            j++;
        }
        return s.substring(i + 1, j);
    }
}

// Solution 3: DP
