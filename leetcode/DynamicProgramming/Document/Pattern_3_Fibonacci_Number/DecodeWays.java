/**
 Refer to
 https://leetcode.com/problems/decode-ways/
 A message containing letters from A-Z is being encoded to numbers using the following mapping:
'A' -> 1
'B' -> 2
...
'Z' -> 26

Given a non-empty string containing only digits, determine the total number of ways to decode it.

Example 1:
Input: "12"
Output: 2
Explanation: It could be decoded as "AB" (1 2) or "L" (12).

Example 2:
Input: "226"
Output: 3
Explanation: It could be decoded as "BZ" (2 26), "VF" (22 6), or "BBF" (2 2 6).
*/
// Solution 1: Native DFS
// https://massivealgorithms.blogspot.com/2014/06/leetcode-decode-ways-darrens-blog.html
// http://siyang2leetcode.blogspot.com/2015/03/decode-ways.html
// Runtime: 364 ms, faster than 5.02% of Java online submissions for Decode Ways.
// Memory Usage: 34.3 MB, less than 100.00% of Java online submissions for Decode Ways.
class Solution {
    public int numDecodings(String s) {
        return helper(s, 0);
    }
    
    private int helper(String s, int index) {
        // Base case
        if(index >= s.length()) {
            return 1;
        }
        int result = 0;
        // Decode as 2 digits
        if(index + 1 < s.length() && 
           (s.charAt(index) == '1' || s.charAt(index) == '2' 
            && s.charAt(index + 1) <= '6')) {
            result += helper(s, index + 2);
        }
        // Decode as 1 digit
        if(s.charAt(index) != '0') {
            result += helper(s, index + 1);
        }
        return result;
    }
}

// Solution 2: Top down DP (DFS + Memoization)
// Runtime: 1 ms, faster than 98.69% of Java online submissions for Decode Ways.
// Memory Usage: 35.2 MB, less than 100.00% of Java online submissions for Decode Ways.
class Solution {
    public int numDecodings(String s) {
        Integer[] memo = new Integer[s.length() + 1];
        return helper(s, 0, memo);
    }
    
    private int helper(String s, int index, Integer[] memo) {
        if(memo[index] != null) {
            return memo[index];
        }
        // Base case
        if(index >= s.length()) {
            return 1;
        }
        int result = 0;
        // Decode as 2 digits
        if(index + 1 < s.length() && 
           (s.charAt(index) == '1' || s.charAt(index) == '2' 
            && s.charAt(index + 1) <= '6')) {
            result += helper(s, index + 2, memo);
        }
        // Decode as 1 digit
        if(s.charAt(index) != '0') {
            result += helper(s, index + 1, memo);
        }
        memo[index] = result;
        return result;
    }
}

// Solution 3: Bottom up DP
// Refer to
// https://massivealgorithms.blogspot.com/2014/06/leetcode-decode-ways-darrens-blog.html
class Solution {
    public int numDecodings(String s) {
        // I used a dp array of size n + 1 to save subproblem solutions. 
        // dp[0] means an empty string will have one way to decode, 
        // dp[1] means the way to decode a string of size 1. 
        // I then check one digit and two digit combination and save the 
        // results along the way. In the end, dp[n] will be the end result.
        int[] dp = new int[s.length() + 1];
        dp[0] = 1;
        dp[1] = s.charAt(0) == '0' ? 0 : 1;
        for(int i = 2; i <= s.length(); i++) {
            int oneDigit = Integer.valueOf(s.substring(i - 1, i));
            int twoDigits = Integer.valueOf(s.substring(i - 2, i));
            if(oneDigit >= 1 && oneDigit <= 9) {
                dp[i] += dp[i - 1];
            }
            if(twoDigits >= 10 && twoDigits <= 26) {
                dp[i] += dp[i - 2];
            }
        }
        return dp[s.length()];
    }
}
