/**
Refer to
https://leetcode.com/problems/count-number-of-homogenous-substrings/
Given a string s, return the number of homogenous substrings of s. Since the answer may be too large, return it modulo 109 + 7.

A string is homogenous if all the characters of the string are the same.

A substring is a contiguous sequence of characters within a string.

Example 1:
Input: s = "abbcccaa"
Output: 13
Explanation: The homogenous substrings are listed as below:
"a"   appears 3 times.
"aa"  appears 1 time.
"b"   appears 2 times.
"bb"  appears 1 time.
"c"   appears 3 times.
"cc"  appears 2 times.
"ccc" appears 1 time.
3 + 1 + 2 + 1 + 3 + 2 + 1 = 13.

Example 2:
Input: s = "xy"
Output: 2
Explanation: The homogenous substrings are "x" and "y".

Example 3:
Input: s = "zzzzz"
Output: 15

Constraints:
1 <= s.length <= 105
s consists of lowercase letters.
*/

// Solution 1: Scan all chars in one pass
// Refer to
// https://leetcode.com/problems/count-number-of-homogenous-substrings/discuss/1064530/JavaC%2B%2BPython-Straight-Forward
/**
Explanation
cur is the previous character in type integer,
count the number of continuous same character.

We iterate the whole string character by character,
if it's same as the previous,
we increment the count,
otherwise we set it to 1.

There are count characters to start with,
ending at this current character,
in order to construct a homogenous string.
So increment our result res = (res + count) % mod.

Complexity
Time O(n)
Space O(1)

Solution 1
Java

    public int countHomogenous(String s) {
        int res = 0, cur = 0, count = 0, mod = 1_000_000_007;
        for (int i = 0; i < s.length(); ++i) {
            count = s.charAt(i) == cur ? count + 1 : 1;
            cur = s.charAt(i);
            res = (res + count) % mod;
        }
        return res;
    }
*/
class Solution {
    int mod = 1000000007;
    int result = 0;
    int count = 0;
    char cur = '*';
    public int countHomogenous(String s) {
        for(int i = 0; i < s.length(); i++) {
            count = cur == s.charAt(i) ? count + 1 : 1;
            cur = s.charAt(i);
            result = (result + count) % mod;
        }
        return result;
    }
}

