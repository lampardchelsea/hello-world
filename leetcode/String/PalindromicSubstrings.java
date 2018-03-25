/**
 * Refer to
 * https://leetcode.com/problems/palindromic-substrings/description/
 * Given a string, your task is to count how many palindromic substrings in this string.

    The substrings with different start indexes or end indexes are counted as different substrings even they consist of same characters.

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
    
 * Solution
 * https://leetcode.com/problems/palindromic-substrings/discuss/105688/Very-Simple-Java-Solution-with-Detail-Explanation
*/
class Solution {
    int count = 0;
    public int countSubstrings(String s) {
        if(s == null || s.length() == 0) {
            return 0;
        }
        int len = s.length();
        for(int i = 0; i < len; i++) {
            expandFromCenter(s, i, i); //To check the palindrome of odd length palindromic sub-string
            expandFromCenter(s, i, i + 1); //To check the palindrome of even length palindromic sub-string
        }
        return count;
    } 
    
    private void expandFromCenter(String s, int c1, int c2) {
        int l = c1;
        int r = c2;
        while(l >= 0 && r < s.length() && s.charAt(l) == s.charAt(r)) {
            count++;
            l--;
            r++;
        }
    }
    
}
