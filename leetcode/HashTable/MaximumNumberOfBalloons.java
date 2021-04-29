/**
Refer to
https://leetcode.com/problems/maximum-number-of-balloons/
Given a string text, you want to use the characters of text to form as many instances of the word "balloon" as possible.

You can use each character in text at most once. Return the maximum number of instances that can be formed.

Example 1:
Input: text = "nlaebolko"
Output: 1

Example 2:
Input: text = "loonbalxballpoon"
Output: 2

Example 3:
Input: text = "leetcode"
Output: 0

Constraints:
1 <= text.length <= 104
text consists of lower case English letters only.
*/
class Solution {
    public int maxNumberOfBalloons(String text) {
        if(text.length() < 7) {
            return 0;
        }
        int[] index = new int[26];
        for(char c : text.toCharArray()) {
            index[c - 'a']++;
        }
        int min = Integer.MAX_VALUE;
        String s = "balloon";
        for(char c : s.toCharArray()) {
            if(c == 'b') {
                min = Math.min(min, index[1]);
            } else if(c == 'a') {
                min = Math.min(min, index[0]);
            } else if(c == 'l') {
                min = Math.min(min, index['l' - 'a'] / 2);
            } else if(c == 'o') {
                min = Math.min(min, index['o' - 'a'] / 2);
            } else if(c == 'n') {
                min = Math.min(min, index['n' - 'a']);
            }
        }
        return min;
    }
}
