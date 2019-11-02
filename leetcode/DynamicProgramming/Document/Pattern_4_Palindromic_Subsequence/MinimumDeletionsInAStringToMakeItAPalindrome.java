/**
 Refer to
 https://www.techiedelight.com/find-minimum-number-deletions-convert-string-into-palindrome/
 Given a string A, compute the minimum number of characters you need to delete to make 
 resulting string a palindrome.
 
Examples:
Input : baca
Output : 1

Input : geek
Output : 2
*/
// Solution 1: Native DFS
// Refer to
// https://www.techiedelight.com/find-minimum-number-deletions-convert-string-into-palindrome/
/**
  The idea is to use recursion to solve this problem, the idea is compare the last character of
  the string X[i...j] with its first character, there are two possibilities -
  1. If the character of the string is same as the first character, no deletion is 
     needed and we recur for the remaining substring X[i + 1, j - 1]
  2. If last character of string is different from the first character, then we return 
     one plus maximum of the two values we get by
     (1) delete the last character and recusing for the remaining substring X[i...j - 1]
     (2) delete the first character and recusing for the remaining substring X[i + 1...j]
     This yeilds the below recursive relation:
     T[i...j] = | T[i + 1...j - 1]                       (if X[i] == X[j])
                | 1 + max(T[i + 1...j], T[i...j - 1])    (if X[i] 1= X[j])
  The worst case time complexity of above solution is exponenetial O(2^n) and auxiliary 
  space used by the program is O(1).
  The worst case happens when there is no repeated character present in X and each recursive 
  call will end up with two recursive calls.       
*/
class Solution {
    public int minDeletions(String s) {
        return helper(s, 0, s.length() - 1);
    }

    public int helper(String s, int i, int j) {
        // Base condition
        if(i >= j) {
            return 0;
        }
        // If last character of the String is same as the first character
        // no need to remove anything character, just checking next level
        if(s.charAt(i) == s.charAt(j)) {
            return helper(s, i + 1, j - 1);
        }
        return 1 + Math.min(helper(s, i, j - 1), helper(s, i + 1, j));
    }
}

// Solution 2: Top down DP (DFS + Memoization)
// Refer to
// https://www.techiedelight.com/find-minimum-number-deletions-convert-string-into-palindrome/
/**
 The problem has an optimal substructure, we have seen that the problem can be broken down into smaller 
 subproblems which can further be broken down into yet smaller subproblems, and so on, the problem also 
 exhibits overlapping subproblems we will end up solving the same subproblem over and over again, let us 
 consider recursion tree for sequence of length 6 having all distinct characters (say ABCDEF)
                                                  (0,5)
		            (1,5)                                        (0,4)
	        (2,5)   	    (1,4)	       	        (1,4)            (0,3)
	  (3,5)       (2,4)    (2,4)     (1,3)               ()   (1,3)      (1,3)   (0,2)
       (4,5) (3,4) (3,4) (2,3) ()  () (2,3)  (1,2)                () ()      () () (1,2) (0,1)

As we can see, the same subproblems are getting computed again and again, we know that problems
having optimal substructure and overlapping subproblems can be solved by dynamic programming, in which 
subproblem solutions are Memoized rather than computed again and again.
Time complexity is O(n^2) and auxiliary space used by the program is O(n^2)
*/
class Solution {
    public int minDeletions(String s) {
        int len = s.length();
        Integer[][] memo = new Integer[len][len];
        return helper(s, 0, len - 1, memo);
    }

    public int helper(String s, int i, int j, Integer[][] memo) {
        if (memo[i][j] != null) {
            return memo[i][j];
        }
        // Base condition
        if (i >= j) {
            return 0;
        }
        // If last character of the String is same as the first character
        // no need to remove anything character, just checking next level
        int result = 0;
        if (s.charAt(i) == s.charAt(j)) {
            result = helper(s, i + 1, j - 1, memo);
        } else {
            result = 1 + Math.min(helper(s, i, j - 1, memo), helper(s, i + 1, j, memo));
        }
        memo[i][j] = result;
        return result;
    }
}

// Solution 3: Full length minus Longest Common Subsequence length
// Refer to
// https://www.techiedelight.com/find-minimum-number-deletions-convert-string-into-palindrome/
// https://github.com/lampardchelsea/hello-world/blob/master/leetcode/DynamicProgramming/Document/Pattern_4_Palindromic_Subsequence/LongestPalindromicSubsequence.java
/**
 The problem is also a classic variation of Longest Common Subsequence(LCS) problem, the idea is to find 
 the Longest Palindromic Substring of given string, then the minimum number of deletions required will 
 be size of the string minus size of the longest palindromic subsequence. We can easily find the longest
 palindromic substring by taking Longest Common Subsequence of a given string with its reverse 
 i.e. call LCS(X, reverse(X))
 Time complexity is O(n^2) and auxiliary space used by the program is O(n^2)
*/
class Solution {
    public int minDeletions(String s) {
        return s.length() - longestPalindromeSubseq(s);
    }	
	
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
