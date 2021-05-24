/**
Refer to
https://leetcode.com/problems/break-a-palindrome/
Given a palindromic string of lowercase English letters palindrome, replace exactly one character with any lowercase English 
letter so that the resulting string is not a palindrome and that it is the lexicographically smallest one possible.

Return the resulting string. If there is no way to replace a character to make it not a palindrome, return an empty string.

A string a is lexicographically smaller than a string b (of the same length) if in the first position where a and b differ, 
a has a character strictly smaller than the corresponding character in b. For example, "abcc" is lexicographically smaller 
than "abcd" because the first position they differ is at the fourth character, and 'c' is smaller than 'd'.

Example 1:
Input: palindrome = "abccba"
Output: "aaccba"
Explanation: There are many ways to make "abccba" not a palindrome, such as "zbccba", "aaccba", and "abacba".
Of all the ways, "aaccba" is the lexicographically smallest.

Example 2:
Input: palindrome = "a"
Output: ""
Explanation: There is no way to replace a single character to make "a" not a palindrome, so return an empty string.

Example 3:
Input: palindrome = "aa"
Output: "ab"

Example 4:
Input: palindrome = "aba"
Output: "abb"

Constraints:
1 <= palindrome.length <= 1000
palindrome consists of only lowercase English letters.
*/

// Solution 1: replace a non 'a' character to 'a' OR replace last character to 'b'
// Refer to
// https://leetcode.com/problems/break-a-palindrome/discuss/489774/JavaC%2B%2BPython-Easy-and-Concise
/**
Check half of the string,
replace a non 'a' character to 'a'.

If only one character, return empty string.
Otherwise repalce the last character to 'b'

Complexity
Time O(N)
Space O(N)

Java

    public String breakPalindrome(String palindrome) {
        char[] s = palindrome.toCharArray();
        int n = s.length;

        for (int i = 0; i < n / 2; i++) {
            if (s[i] != 'a') {
                s[i] = 'a';
                return String.valueOf(s);
            }
        }
        s[n - 1] = 'b'; //if all 'a'
        return n < 2 ? "" : String.valueOf(s);
    }
*/
class Solution {
    public String breakPalindrome(String palindrome) {
        char[] chars = palindrome.toCharArray();
        int n = palindrome.length();
        if(n < 2) {
            return "";
        }
        for(int i = 0; i < n / 2; i++) {
            if(chars[i] != 'a') {
                chars[i] = 'a';
                return new String(chars);
            }
        }
        chars[n - 1] = 'b';
        return new String(chars);
    }
}
