/**
Refer to
https://leetcode.com/problems/replace-all-digits-with-characters/
You are given a 0-indexed string s that has lowercase English letters in its even indices and digits in its odd indices.

There is a function shift(c, x), where c is a character and x is a digit, that returns the xth character after c.

For example, shift('a', 5) = 'f' and shift('x', 0) = 'x'.
For every odd index i, you want to replace the digit s[i] with shift(s[i-1], s[i]).

Return s after replacing all digits. It is guaranteed that shift(s[i-1], s[i]) will never exceed 'z'.

Example 1:
Input: s = "a1c1e1"
Output: "abcdef"
Explanation: The digits are replaced as follows:
- s[1] -> shift('a',1) = 'b'
- s[3] -> shift('c',1) = 'd'
- s[5] -> shift('e',1) = 'f'

Example 2:
Input: s = "a1b2c3d4e"
Output: "abbdcfdhe"
Explanation: The digits are replaced as follows:
- s[1] -> shift('a',1) = 'b'
- s[3] -> shift('b',2) = 'd'
- s[5] -> shift('c',3) = 'f'
- s[7] -> shift('d',4) = 'h'

Constraints:
1 <= s.length <= 100
s consists only of lowercase English letters and digits.
shift(s[i-1], s[i]) <= 'z' for all odd indices i.
*/
class Solution {
    public String replaceDigits(String s) {
        StringBuilder sb = new StringBuilder();
        int n = s.length();
        if(n == 1) {
            return s;
        }
        for(int i = 0; i + 1 < n; i += 2) {
            char c = s.charAt(i);
            //char next = (char)(c + Integer.parseInt(String.valueOf(s.charAt(i + 1))));
            char next = (char)(c + s.charAt(i + 1) - '0');
            sb.append(c).append(next);
        }
        // Test out by "a1b2c3d4e" for odd length, last char need to add on
        if(n % 2 == 1) {
            sb.append(s.charAt(n - 1));
        }
        return sb.toString();
    }
}
