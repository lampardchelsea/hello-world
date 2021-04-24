/**
Refer to
https://leetcode.com/problems/split-a-string-in-balanced-strings/
Balanced strings are those that have an equal quantity of 'L' and 'R' characters.

Given a balanced string s, split it in the maximum amount of balanced strings.

Return the maximum amount of split balanced strings.

Example 1:
Input: s = "RLRRLLRLRL"
Output: 4
Explanation: s can be split into "RL", "RRLL", "RL", "RL", each substring contains same number of 'L' and 'R'.

Example 2:
Input: s = "RLLLLRRRLR"
Output: 3
Explanation: s can be split into "RL", "LLLRRR", "LR", each substring contains same number of 'L' and 'R'.

Example 3:
Input: s = "LLLLRRRR"
Output: 1
Explanation: s can be split into "LLLLRRRR".

Example 4:
Input: s = "RLRRRLLRLL"
Output: 2
Explanation: s can be split into "RL", "RRRLLRLL", since each substring contains an equal number of 'L' and 'R'

Constraints:
1 <= s.length <= 1000
s[i] is either 'L' or 'R'.
s is a balanced string.
*/

// Solution 1: Balance counter
// Loop from left to right maintaining a balance variable when it gets an L increase it by one otherwise decrease it by one.
// Whenever the balance variable reaches zero then we increase the answer by one.
class Solution {
    public int balancedStringSplit(String s) {
        int balance = 0;
        int count = 0;
        for(char c : s.toCharArray()) {
            if(c == 'L') {
                balance++;
            } else {
                balance--;
            }
            if(balance == 0) {
                count++;
            }
        }
        return count;
    }
}
