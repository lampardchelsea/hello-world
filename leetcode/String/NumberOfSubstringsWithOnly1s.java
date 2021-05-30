/**
Refer to
https://leetcode.com/problems/number-of-substrings-with-only-1s/
Given a binary string s (a string consisting only of '0' and '1's).

Return the number of substrings with all characters 1's.

Since the answer may be too large, return it modulo 10^9 + 7.

Example 1:
Input: s = "0110111"
Output: 9
Explanation: There are 9 substring in total with only 1's characters.
"1" -> 5 times.
"11" -> 3 times.
"111" -> 1 time.

Example 2:
Input: s = "101"
Output: 2
Explanation: Substring "1" is shown 2 times in s.

Example 3:
Input: s = "111111"
Output: 21
Explanation: Each substring contains only 1's characters.

Example 4:
Input: s = "000"
Output: 0

Constraints:
s[i] == '0' or s[i] == '1'
1 <= s.length <= 10^5
*/

// Solution 1: Same as 1759. Count Number of Homogenous Substrings
// Refer to
// https://leetcode.com/problems/number-of-substrings-with-only-1s/discuss/731580/JavaC%2B%2BPython-Count
/**
Explanation
count the current number of consecutive "1".
For each new element,
there will be more count substring,
with all characters 1's.

Complexity
Time O(N)
Space O(1)

Java:

    public int numSub(String s) {
        int res = 0, count = 0, mod = (int)1e9 + 7;
        for (int i = 0; i < s.length(); ++i) {
            count = s.charAt(i) == '1' ? count + 1 : 0;
            res = (res + count) % mod;
        }
        return res;
    }
*/

// Refer to
// https://leetcode.com/problems/number-of-substrings-with-only-1s/discuss/731757/JavaPython-3-Count-continuous-'1's-O(n)-ONE-liner-codes-w-brief-explanation-and-analysis.
// For k continuous 1s, we have 1, 2, ..., k substrings ending at 1st, 2nd, ..., kth 1s, respectively; 
// Therefore, there are k * (k + 1) / 2 substrings for any k continuous 1.
/**
 public int numSub(String s) {
        int sum = 0;
        for (int i = 0, cnt = 0; i < s.length(); ++i) {
            if (s.charAt(i) == '1') {
                sum += ++cnt;
                sum %= 1_000_000_007;
            }else {
                cnt = 0;
            }
        }
        return sum;
    }
*/

class Solution {
    public int numSub(String s) {
        int mod = 1000000007;
        int result = 0;
        int count = 0;
        int n = s.length();
        for(int i = 0; i < n; i++) {
            if(s.charAt(i) == '1') {
                count++;
                result = (result + count) % mod;
            } else {
                count = 0;
            }
        }
        return result;
    }
}
