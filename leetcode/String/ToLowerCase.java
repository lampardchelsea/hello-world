/**
 Refer to
 https://leetcode.com/problems/to-lower-case/
 Implement function ToLowerCase() that has a string parameter str, and returns the same string in lowercase.

Example 1:
Input: "Hello"
Output: "hello"

Example 2:
Input: "here"
Output: "here"

Example 3:
Input: "LOVELY"
Output: "lovely"
*/
class Solution {
    public String toLowerCase(String str) {
        char[] chars = str.toCharArray();
        for(int i = 0; i < chars.length; i++) {
            if(chars[i] >= 'A' && chars[i] <= 'Z') {
                chars[i] = (char)(chars[i] - 'A' + 'a');
            }
        }
        return new String(chars);
    }
}
