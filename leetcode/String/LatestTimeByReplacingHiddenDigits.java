/**
Refer to
https://leetcode.com/problems/latest-time-by-replacing-hidden-digits/
You are given a string time in the form of hh:mm, where some of the digits in the string are hidden (represented by ?).

The valid times are those inclusively between 00:00 and 23:59.

Return the latest valid time you can get from time by replacing the hidden digits.

Example 1:
Input: time = "2?:?0"
Output: "23:50"
Explanation: The latest hour beginning with the digit '2' is 23 and the latest minute ending with the digit '0' is 50.

Example 2:
Input: time = "0?:3?"
Output: "09:39"

Example 3:
Input: time = "1?:22"
Output: "19:22"

Constraints:
time is in the format hh:mm.
It is guaranteed that you can produce a valid time from the given string.
*/

class Solution {
    public String maximumTime(String time) {
        char[] chars = time.toCharArray();
        for(int i = 0; i < 5; i++) {
            if(chars[i] == '?') {
                if(i == 0) {
                    if(chars[1] <= '3' || chars[1] == '?') {
                        chars[0] = '2';
                    } else {
                        chars[0] = '1';
                    }
                } else if(i == 1) {
                    if(chars[0] == '2') {
                        chars[1] = '3';
                    } else {
                        chars[1] = '9';
                    }
                } else if(i == 3) {
                    chars[3] = '5';
                } else if(i == 4) {
                    chars[4] = '9';
                }
            }
        }
        return new String(chars);
    }
}
