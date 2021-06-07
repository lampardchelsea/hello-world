/**
Refer to
https://leetcode.com/problems/largest-substring-between-two-equal-characters/
Given a string s, return the length of the longest substring between two equal characters, excluding the two characters. If there is no such substring return -1.

A substring is a contiguous sequence of characters within a string.

Example 1:
Input: s = "aa"
Output: 0
Explanation: The optimal substring here is an empty substring between the two 'a's.

Example 2:
Input: s = "abca"
Output: 2
Explanation: The optimal substring here is "bc".

Example 3:
Input: s = "cbzxy"
Output: -1
Explanation: There are no characters that appear twice in s.

Example 4:
Input: s = "cabbac"
Output: 4
Explanation: The optimal substring here is "abba". Other non-optimal substrings include "bb" and "".

Constraints:
1 <= s.length <= 300
s contains only lowercase English letters.
*/

// Solution 1: One Pass
// Refer to
// https://leetcode.com/problems/largest-substring-between-two-equal-characters/discuss/899464/JavaPython-3-One-pass-O(n)-114-liners-w-brief-explanation-and-analysis.
/**
Loop through the input string s

Store the first index of the substring between same characters;
If current character seen before, compute the length of the sustring between them and update the max length;
    public int maxLengthBetweenEqualCharacters(String s) {
        int[] indices = new int[26];
        int maxLen = -1;
        for (int i = 0; i < s.length(); ++i) {
            int idx = s.charAt(i) - 'a';
            if (indices[idx] > 0) {
                maxLen = Math.max(maxLen, i - indices[idx]);
            }else {
                indices[idx] = i + 1;
            }
        }
        return maxLen;
    }
*/
class Solution {
    public int maxLengthBetweenEqualCharacters(String s) {
        int[] indexes = new int[26];
        int max = -1;
        for(int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if(indexes[c - 'a'] > 0) {
                max = Math.max(max, i - indexes[c - 'a']);
            } else {
                indexes[c - 'a'] = i + 1;
            }
        }
        return max;
    }
}
