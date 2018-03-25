/**
 * Refer to
 * https://leetcode.com/problems/shortest-palindrome/description/
 * Given a string S, you are allowed to convert it to a palindrome by adding characters in front of it. 
   Find and return the shortest palindrome you can find by performing this transformation.

    For example:

    Given "aacecaaa", return "aaacecaaa".

    Given "abcd", return "dcbabcd".
 *
 *
 * Solution
 * https://leetcode.com/problems/shortest-palindrome/discuss/60106/My-9-lines-three-pointers-Java-solution-with-explanation
*/
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
