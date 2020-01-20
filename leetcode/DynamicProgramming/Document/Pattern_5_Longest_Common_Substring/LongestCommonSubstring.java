/**
 Refer to
 https://www.geeksforgeeks.org/longest-common-substring-dp-29/
 Given two strings ‘X’ and ‘Y’, find the length of the longest common substring.
Examples :

Input : X = “GeeksforGeeks”, y = “GeeksQuiz”
Output : 5
The longest common substring is “Geeks” and is of length 5.

Input : X = “abcdxyz”, y = “xyzabcd”
Output : 4
The longest common substring is “abcd” and is of length 4.

Input : X = “zxabcdezy”, y = “yzabcdezx”
Output : 6
The longest common substring is “abcdez” and is of length 6.
*/

// Solution 1: Native Solution
// Refer to
// https://www.geeksforgeeks.org/longest-common-substring-dp-29/
// https://algorithms.tutorialhorizon.com/dynamic-programming-longest-common-substring/
/**
 A simple solution is to one by one consider all substrings of first string and for every substring check 
 if it is a substring in second string. Keep track of the maximum length substring. There will be O(m^2) 
 substrings and we can find whether a string is subsring on another string in O(n) time (See this). 
 So overall time complexity of this method would be O(n * m^2)
*/
class Solution {
    public static int longestCommonSubstring(String s1, String s2) {
        // Check all the substrings from first string with second string 
        // and keep track of the maximum.
        // Find all substring of s1
        List < String > list = new ArrayList < String > ();
        for (int i = 0; i < s1.length(); i++) {
            for (int j = i + 1; j <= s1.length(); j++) {
                list.add(s1.substring(i, j));
            }
        }
        int max = 0;
        // Check if a string is substring of another
        for (String s: list) {
            if (isSubstring(s, s2)) {
                if (max < s.length()) {
                    max = s.length();
                }
            }
        }
        return max;
    }

    private static boolean isSubstring(String s1, String s2) {
        int m = s1.length();
        int n = s2.length();
        for (int i = 0; i <= n - m; i++) {
            int j;
            for (j = 0; j < m; j++) {
                if (s2.charAt(i + j) != s1.charAt(j)) {
                    break;
                }
            }
            if (j == m) {
                return true;
            }
        }
        return false;
    }
}

// Solution 2: Bottom Up DP
// Refer to
// https://algorithms.tutorialhorizon.com/dynamic-programming-longest-common-substring/
/**
 Base Cases: If any of the string is null then LCS will be 0.
 Check if ith character in one string A is equal to jth character in string B
 Case 1: both characters are same
 LCS[i][j] = 1 + LCS[i-1][j-1] (add 1 to the result and remove the last character from both the strings 
 and check the result for the smaller string.)
 Case 2: both characters are not same.
 LCS[i][j] = 0
 At the end, traverse the matrix and find the maximum element in it, This will the length of Longest Common Substring.
*/
class Solution {
    public static int longestCommonSubstring(String s1, String s2) {
        int m = s1.length() + 1;
        int n = s2.length() + 1;
        int[][] dp = new int[m][n];
        // If s2 is null then dp of s1, s2 = 0
        for (int i = 0; i < n; i++) {
            dp[0][i] = 0;
        }
        // If s1 is null then dp of s1, s2 = 0
        for (int j = 0; j < m; j++) {
            dp[j][0] = 0;
        }
        // Fill the rest of the dp matrix
        for (int i = 1; i < m; i++) {
            for (int j = 1; j < n; j++) {
                if (s1.charAt(i - 1) == s2.charAt(j - 1)) {
                    dp[i][j] = dp[i - 1][j - 1] + 1;
                } else {
                    dp[i][j] = 0;
                }
            }
        }
        // Find the maximum value
        int result = -1;
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (result < dp[i][j]) {
                    result = dp[i][j];
                }
            }
        }
        return result;
    }
}
