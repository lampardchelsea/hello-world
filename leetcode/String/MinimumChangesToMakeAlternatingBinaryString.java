/**
Refer to
https://leetcode.com/problems/minimum-changes-to-make-alternating-binary-string/
You are given a string s consisting only of the characters '0' and '1'. In one operation, you can change any '0' to '1' or vice versa.

The string is called alternating if no two adjacent characters are equal. For example, the string "010" is alternating, while the string "0100" is not.

Return the minimum number of operations needed to make s alternating.

Example 1:
Input: s = "0100"
Output: 1
Explanation: If you change the last character to '1', s will be "0101", which is alternating.

Example 2:
Input: s = "10"
Output: 0
Explanation: s is already alternating.

Example 3:
Input: s = "1111"
Output: 2
Explanation: You need two operations to reach "0101" or "1010".

Constraints:
1 <= s.length <= 104
s[i] is either '0' or '1'.
*/

// Solution 1: Create actual array to compare
class Solution {
    public int minOperations(String s) {
        int next1 = 0;
        int i = 0;
        StringBuilder sb = new StringBuilder();
        while(i < s.length()) {
            sb.append(next1);
            next1 = 1 - next1;
            i++;
        }
        String target1 = sb.toString();
        int count1 = 0;
        for(int j = 0; j < s.length(); j++) {
            if(s.charAt(j) != target1.charAt(j)) {
                count1++;
            }
        }
        int next2 = 1;
        sb.setLength(0);
        i = 0;
        while(i < s.length()) {
            sb.append(next2);
            next2 = 1 - next2;
            i++;
        }
        String target2 = sb.toString();
        int count2 = 0;
        for(int j = 0; j < s.length(); j++) {
            if(s.charAt(j) != target2.charAt(j)) {
                count2++;
            }
        }
        return Math.min(count1, count2);
    }
}


// Solution 2: No actual array create
class Solution {
    public int minOperations(String s) {
        int count1 = 0;
        int next1 = 0;
        int i = 0;
        while(i < s.length()) {
            int cur = s.charAt(i) - '0';
            if(cur != next1) {
                count1++;
            }
            next1 = 1 - next1;
            i++;
        }
        int count2 = 0;
        int next2 = 1;
        i = 0;
        while(i < s.length()) {
            int cur = s.charAt(i) - '0';
            if(cur != next2) {
                count2++;
            }
            next2 = 1 - next2;
            i++;
        }
        return Math.min(count1, count2);
    }
}


// Solution 3: res V.S. n - res
// Refer to
// https://leetcode.com/problems/minimum-changes-to-make-alternating-binary-string/discuss/1064511/JavaC%2B%2BPython-Easy-and-Concise
/**
Explanation
Small observation that the sequence of index is [0,1,2,3..],
if we get its module by 2, we get [0,1,0,1,0..],
which is the alternating binary sequence we want.

So we iterate the string,
check if the characters[i] is same as the i % 2.
Note that s[i] is a character,
and s[i] - '0' making it to integer.

We accumulate the number of difference,
which is the number of operation to make it into 01 string.

We can do the same to find out res,
the number of operation for 10 string.
But we don't have to,
becaue this equals to s.length - res.

Complexity
Time O(n)
Space O(1)

Java
    public int minOperations(String s) {
        int res = 0, n = s.length();
        for (int i = 0; i < n; ++i)
            if (s.charAt(i) - '0' != i % 2)
                res++;
        return Math.min(res, n - res);
    }
*/
class Solution {
    public int minOperations(String s) {
        int res = 0, n = s.length();
        for (int i = 0; i < n; ++i)
            if (s.charAt(i) - '0' != i % 2)
                res++;
        return Math.min(res, n - res);
    }
}
