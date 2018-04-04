/**
 * Refer to
 * https://leetcode.com/problems/valid-palindrome-ii/description/
 * Given a non-empty string s, you may delete at most one character. Judge whether you can make it a palindrome.

    Example 1:
    Input: "aba"
    Output: True
    Example 2:
    Input: "abca"
    Output: True
    Explanation: You could delete the character 'c'.
    Note:
    The string will only contain lowercase characters a-z. The maximum length of the string is 50000.
 *
 * Solution
 * https://leetcode.com/problems/valid-palindrome-ii/discuss/107714/Java-solution-isPalindrome
*/
class Solution {
    public boolean validPalindrome(String s) {
        if(s == null || s.length() == 0) {
            return true;
        }
        int i = 0;
        int j = s.length() - 1;
        while(i < j) {
            if(s.charAt(i) != s.charAt(j)) {
                break;
            }
            i++;
            j--;
        }
        // Skip either char at position i or j
        return isPalindrome(s, i + 1, j) || isPalindrome(s, i, j - 1);
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
