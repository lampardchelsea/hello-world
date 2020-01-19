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

// Solution 2: DP
