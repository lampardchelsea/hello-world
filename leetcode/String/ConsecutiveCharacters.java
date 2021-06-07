/**
Refer to
https://leetcode.com/problems/consecutive-characters/
Given a string s, the power of the string is the maximum length of a non-empty substring that contains only one unique character.

Return the power of the string.

Example 1:
Input: s = "leetcode"
Output: 2
Explanation: The substring "ee" is of length 2 with the character 'e' only.

Example 2:
Input: s = "abbcccddddeeeeedcba"
Output: 5
Explanation: The substring "eeeee" is of length 5 with the character 'e' only.

Example 3:
Input: s = "triplepillooooow"
Output: 5

Example 4:
Input: s = "hooraaaaaaaaaaay"
Output: 11

Example 5:
Input: s = "tourist"
Output: 1

Constraints:
1 <= s.length <= 500
s contains only lowercase English letters.
*/

class Solution {
    public int maxPower(String s) {
        int max = 0;
        int count = 1;
        char cur = s.charAt(0);
        for(int i = 1; i < s.length(); i++) {
            char c = s.charAt(i);
            if(cur != c) {
                max = Math.max(max, count);
                cur = c;
                count = 1;
            } else {
                count++;
            }
        }
        // In case the maximum length getting at the end section
        // since we could not update max inside "cur != c" condition
        // e.g "hooraaaaaaaaaaaa"
        max = Math.max(max, count);
        return max;
    }
}
