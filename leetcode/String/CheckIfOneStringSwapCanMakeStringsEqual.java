/**
Refer to
https://leetcode.com/problems/check-if-one-string-swap-can-make-strings-equal/
You are given two strings s1 and s2 of equal length. A string swap is an operation where you choose two indices 
in a string (not necessarily different) and swap the characters at these indices.

Return true if it is possible to make both strings equal by performing at most one string swap on exactly one 
of the strings. Otherwise, return false.

Example 1:
Input: s1 = "bank", s2 = "kanb"
Output: true
Explanation: For example, swap the first character with the last character of s2 to make "bank".

Example 2:
Input: s1 = "attack", s2 = "defend"
Output: false
Explanation: It is impossible to make them equal with one string swap.

Example 3:
Input: s1 = "kelb", s2 = "kelb"
Output: true
Explanation: The two strings are already equal, so no string swap operation is required.

Example 4:
Input: s1 = "abcd", s2 = "dcba"
Output: false

Constraints:
1 <= s1.length, s2.length <= 100
s1.length == s2.length
s1 and s2 consist of only lowercase English letters.
*/

class Solution {
    public boolean areAlmostEqual(String s1, String s2) {
        int n = s1.length();
        int dif1 = -1;
        int dif2 = -1;
        for(int i = 0; i < n; i++) {
            // Must include dif1 == -1 to make sure we only set this flag once
            // Test case: "attack" and "defend"
            if(dif1 == -1 && s1.charAt(i) != s2.charAt(i)) {
                dif1 = i;
                continue;
            }
            // If we already find first difference index
            if(dif1 != -1) {
                // If we find second difference index
                // Must include dif2 == -1 to make sure we only set this flag once
                // Test case: "baaa" and "abbb"
                if(dif2 == -1 && s1.charAt(i) != s2.charAt(i)) {
                    // If value match the switched ones on previous difference index
                    // otherwise mismatch rules, return false
                    if(s1.charAt(i) == s2.charAt(dif1) && s1.charAt(dif1) == s2.charAt(i)) {
                        dif2 = i;
                        continue;
                    } else {
                        return false;
                    }
                }
            }
            // If we already find two difference indexes and they match switched values
            if(dif1 != -1 && dif2 != -1) {
                // If we find third different index, mismatch rules, return false
                if(s1.charAt(i) != s2.charAt(i)) {
                    return false;
                }
            }
        }
        // If we only find first difference index but not find second one 
        // try to make it up, mismatch rules, return false
        // Test case: "aa" and "ac"
        if(dif1 != -1 && dif2 == -1) {
            return false;
        }
        return true;
    }
}
