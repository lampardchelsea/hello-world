/**
Refer to
https://leetcode.com/problems/replace-all-s-to-avoid-consecutive-repeating-characters/
Given a string s containing only lower case English letters and the '?' character, convert all the '?' characters into lower 
case letters such that the final string does not contain any consecutive repeating characters. You cannot modify the non '?' characters.

It is guaranteed that there are no consecutive repeating characters in the given string except for '?'.

Return the final string after all the conversions (possibly zero) have been made. If there is more than one solution, 
return any of them. It can be shown that an answer is always possible with the given constraints.

Example 1:
Input: s = "?zs"
Output: "azs"
Explanation: There are 25 solutions for this problem. From "azs" to "yzs", all are valid. Only "z" is an invalid modification as 
the string will consist of consecutive repeating characters in "zzs".

Example 2:
Input: s = "ubv?w"
Output: "ubvaw"
Explanation: There are 24 solutions for this problem. Only "v" and "w" are invalid modifications as the strings will consist of 
consecutive repeating characters in "ubvvw" and "ubvww".

Example 3:
Input: s = "j?qg??b"
Output: "jaqgacb"

Example 4:
Input: s = "??yw?ipkj?"
Output: "acywaipkja"

Constraints:
1 <= s.length <= 100
s contains only lower case English letters and '?'.
*/

// Solution 1: Intuitive scan from 'a' to 'z'
class Solution {
    public String modifyString(String s) {
        int n = s.length();
        char[] chars = s.toCharArray();
        for(int i = 0; i < n; i++) {
            if(chars[i] == '?') {
                for(char c = 'a'; c <= 'z'; c++) {
                    boolean valid = true;
                    // Check left
                    if(i > 0 && chars[i - 1] == c) {
                        valid = false;
                    }
                    // Check right
                    if(i < n - 1 && chars[i + 1] != '?' && chars[i + 1] == c) {
                        valid = false;
                    }
                    if(valid) {
                        chars[i] = c;
                        break;
                    }
                }
            }
        }
        return new String(chars);
    }
}

// Solution 2: No need more than 3 characters
// Refer to
// https://leetcode.com/problems/replace-all-s-to-avoid-consecutive-repeating-characters/discuss/831480/C%2B%2BPython-O(n)
// https://leetcode.com/problems/replace-all-s-to-avoid-consecutive-repeating-characters/discuss/831483/Java-Simple-O(n)-loop
/**
We do not need more than 3 letters to build a non-repeating character sequence.
For Python, we can use set difference to determine which one to use.
for each char, just try ‘a’, ‘b’, ‘c’, and select the one not the same as neighbors.

    public String modifyString(String s) {
        char[] arr = s.toCharArray();
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] == '?') {
                for (int j = 0; j < 3; j++) {
                    if (i > 0 && arr[i - 1] - 'a' == j) continue;
                    if (i + 1 < arr.length && arr[i + 1] - 'a' == j) continue;
                    arr[i] = (char) ('a' + j);
                    break;
                }
            }
        }
        return String.valueOf(arr);
    }
*/
class Solution {
    public String modifyString(String s) {
        int n = s.length();
        char[] chars = s.toCharArray();
        for(int i = 0; i < n; i++) {
            if(chars[i] == '?') {
                for(char c = 'a'; c <= 'c'; c++) {
                    boolean valid = true;
                    // Check left
                    if(i > 0 && chars[i - 1] == c) {
                        valid = false;
                    }
                    // Check right
                    if(i < n - 1 && chars[i + 1] != '?' && chars[i + 1] == c) {
                        valid = false;
                    }
                    if(valid) {
                        chars[i] = c;
                        break;
                    }
                }
            }
        }
        return new String(chars);
    }
}
