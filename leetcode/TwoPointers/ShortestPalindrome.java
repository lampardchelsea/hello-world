/**
Refer to
https://leetcode.com/problems/shortest-palindrome/
Given a string s, you can convert it to a palindrome by adding characters in front of it. Find and return the shortest palindrome 
you can find by performing this transformation.

Example 1:
Input: s = "aacecaaa"
Output: "aaacecaaa"

Example 2:
Input: s = "abcd"
Output: "dcbabcd"

Constraints:
0 <= s.length <= 5 * 104
s consists of lowercase English letters only.
*/

// Solution 1: Find the longest palindrome start from 1st character of s
// Refer to
// https://github.com/lampardchelsea/hello-world/blob/master/leetcode/String/ShortestPalindrome.java
// TLE -> Time Complexity (O(n*n))
class Solution {
    public String shortestPalindrome(String s) {
        if(s == null || s.length() == 0) {
            return "";
        }
        // Find the longest palindrome start from 1st character of s
        int len = s.length();
        int i = 0;
        int cutPoint = 0;
        for(int j = 0; j < len; j++) {
            if(isPalindrome(s, i, j)) {
                cutPoint = j + 1; // j - i+ 1 and i = 0 
            }
        }
        // Reverse string start from cut point and attach ahead
        // of original string
        StringBuffer sb = new StringBuffer(s.substring(cutPoint));
        return sb.reverse().toString() + s;
    }
    
    private boolean isPalindrome(String s, int i, int j) {
        while(i <= j) {
            if(s.charAt(i) != s.charAt(j)) {
                return false;
            }
            i++;
            j--;
        }
        return true;
    }
}

// Solution 2: Improvement by 2 pointers
// Refer to
// https://leetcode.com/problems/shortest-palindrome/discuss/60230/Easy-Java-solution
/**
First, we can find the longest palindrome which include the first character in s, 
then we just need to reverse the suffix and add it to the front of string s.
When looking for the longest palindrome, we start from the center and traverse to left and right, 
and we need to think the length of the palindrome could be odd or even.
*/


