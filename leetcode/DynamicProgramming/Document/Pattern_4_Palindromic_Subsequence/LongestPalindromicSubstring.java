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
// Style 1:
// Refer to
// Basic idea
// https://leetcode.com/problems/longest-palindromic-substring/discuss/2921/Share-my-Java-solution-using-dynamic-programming
/**
  dp(i, j) represents whether s(i ... j) can form a palindromic substring, dp(i, j) is true when s(i) equals to s(j) 
  and s(i+1 ... j-1) is a palindromic substring. When we found a palindrome, check if it's the longest one. 
  Time complexity O(n^2).
*/
// Useful comments which helped me understand
// https://leetcode.com/problems/longest-palindromic-substring/discuss/2921/Share-my-Java-solution-using-dynamic-programming/3570
// Why we loop down i and loop up j ?
// https://leetcode.com/problems/longest-palindromic-substring/discuss/2921/Share-my-Java-solution-using-dynamic-programming/264539
/**
  A little explanation for why the indices in the for loops are set the way they are (I was really confused for a long time):
  j must be greater than or equal i at all times. Why? i is the start index of the substring, j is the end index of the substring. 
  It makes no sense for i to be greater than j. Visualization helps me, so if you visualize the dp 2d array, think of a diagonal 
  that cuts from top left to bottom right. We are only filling the top right half of dp.
  Why are we counting down for i, but counting up for j? 
  Each sub-problem dp[i][j] depends on dp[i+1][j-1] (dp[i+1][j-1] must be true and s.charAt(i) must equal s.charAt(j) 
  for dp[i][j] to be true).
*/
// What is j - i <= 2 ?
// https://leetcode.com/problems/longest-palindromic-substring/discuss/2921/Share-my-Java-solution-using-dynamic-programming/147908
/**
 j-i == 0, only a character is a palindrome, 
 j-i == 1 and s.charAt(i) == s.charAt(j), ij is a palindrome, 
 j-i == 2 and s.charAt(i) == s.charAt(j), no matter what between i and j, i#j is a palindrome
 and if j - i > 2, then the internal string between i and j must be palindrome
*/
class Solution {
    public String longestPalindrome(String s) {
        int len = s.length();
        boolean[][] dp = new boolean[len][len];
        int maxLen = 0;
        int start = 0;
        for(int i = len - 1; i >= 0; i--) {
            for(int j = i; j < len; j++) {
                if(j - i <= 2) {
                    dp[i][j] = (s.charAt(i) == s.charAt(j));
                } else {
                    dp[i][j] = (s.charAt(i) == s.charAt(j) && dp[i + 1][j - 1]);
                }
                if(dp[i][j]) {
                    if(j - i + 1 > maxLen) {
                        start = i;
                        maxLen = j - i + 1;
                    }
                }
            }
        }
        return s.substring(start, start + maxLen);
    }
}
