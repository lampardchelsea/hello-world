/**
Refer to
https://www.lintcode.com/problem/next-closest-time/description
Description
Given a time represented in the format "HH:MM", form the next closest time by reusing the current digits. 
There is no limit on how many times a digit can be reused.

You may assume the given input string is always valid. For example, "01:34", "12:09" are valid. "1:34", "12:9" are invalid.

Example
Example 1:
Input: "19:34"
Output: "19:39"
Explanation:
The next closest time choosing from digits 1, 9, 3, 4, is 19:39, which occurs 5 minutes later.  
It is not 19:33, because this occurs 23 hours and 59 minutes later.

Example 2:
Input: "23:59"
Output: "22:22"
Explanation: It may be assumed that the returned time is next day's time since it is smaller than the input time numerically.

Tags
Company
Google
*/

// Solution 1: Simulation
// Refer to
// https://aaronice.gitbook.io/lintcode/string/next-closest-time
/**
Simulation
Simulate the clock going forward by one minute. Each time it moves forward, if all the digits are allowed, then return the current time.
The natural way to represent the time is as an integertin the range0 <= t < 24 * 60. Then the hours aret / 60, 
the minutes aret % 60, and each digit of the hours and minutes can be found byhours / 10, hours % 10etc.
Complexity Analysis
Time Complexity: O(1). We try up to 24 * 60 possible times until we find the correct time.
Space Complexity: O(1).
class Solution {
    public String nextClosestTime(String time) {
        int cur = 60 * Integer.parseInt(time.substring(0, 2));
        cur += Integer.parseInt(time.substring(3));
        Set<Integer> allowed = new HashSet();
        for (char c: time.toCharArray()) if (c != ':') {
            allowed.add(c - '0');
        }

        while (true) {
            cur = (cur + 1) % (24 * 60);
            int[] digits = new int[]{cur / 60 / 10, cur / 60 % 10, cur % 60 / 10, cur % 60 % 10};
            search : {
                for (int d: digits) if (!allowed.contains(d)) break search;
                return String.format("%02d:%02d", cur / 60, cur % 60);
            }
        }
    }
}
*/

public class Solution {
    /**
     * @param time: the given time
     * @return: the next closest time
     */
    public String nextClosestTime(String time) {
        String[] strs = time.split(":");
        String hour = strs[0];
        String min = strs[1];
        Set<Integer> allow = new HashSet<Integer>();
        allow.add(hour.charAt(0) - '0');
        allow.add(hour.charAt(1) - '0');
        allow.add(min.charAt(0) - '0');
        allow.add(min.charAt(1) - '0');
        int cur_min = Integer.valueOf(hour) * 60 + Integer.valueOf(min);
        String result = "";
        while(true) {
            cur_min = (cur_min + 1) % (24 * 60);
            int[] digits = new int[] {cur_min / 60 / 10, cur_min / 60 % 10, cur_min % 60 / 10, cur_min % 60 % 10};
            int i;
            for(i = 0; i < 4; i++) {
                if(!allow.contains(digits[i])) {
                    break;
                }
            }
            if(i == 4) {
                result = "" + digits[0] + digits[1] + ":" + digits[2] + digits[3];
                break;
            }
        }
        return result;
    }
}
