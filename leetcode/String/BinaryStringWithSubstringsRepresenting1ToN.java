/**
Refer to
https://leetcode.com/problems/binary-string-with-substrings-representing-1-to-n/
Given a binary string s (a string consisting only of '0' and '1's) and a positive integer n, return true if 
and only if for every integer x from 1 to n, the binary representation of x is a substring of s.

Example 1:
Input: s = "0110", n = 3
Output: true

Example 2:
Input: s = "0110", n = 4
Output: false

Note:
1 <= s.length <= 1000
1 <= n <= 109
*/

// Solution 1: Brute Force but able to only count n/2 half instead of n
// Refer to
// https://leetcode.com/problems/binary-string-with-substrings-representing-1-to-n/discuss/260847/JavaC++Python-O(S)/252984
/**
The solution can be improve a half by checking from N to N/2.
The reason is simply for every i < N/2, the binary string of 2*i will contain binary string of i. Thus we don't need to check for i < N/2
Java

 public boolean queryString(String S, int N) {
        for (int i = N; i >= N/2; --i)
            if (!S.contains(Integer.toBinaryString(i)))
                return false;
        return true;
    }
*/

// https://leetcode.com/problems/binary-string-with-substrings-representing-1-to-n/discuss/260847/JavaC%2B%2BPython-O(S)
/**
Solution 1
Intuition:
The construction of S is a NP problem,
it's time consuming to construct a short S.
I notice S.length <= 1000, which is too small to make a big N.

This intuition lead me to the following 1-line python solution,
which can be implemented very fast.


Explanation:
Check if S contains binary format of N,
If so, continue to check N - 1.


Time Complexity
Have a look at the number 1001 ~ 2000 and their values in binary.

1001 0b1111101001
1002 0b1111101010
1003 0b1111101011
...
1997 0b11111001101
1998 0b11111001110
1999 0b11111001111
2000 0b11111010000

The number 1001 ~ 2000 have 1000 different continuous 10 digits.
The string of length S has at most S - 9 different continuous 10 digits.
So S <= 1000, the achievable N <= 2000.
So S * 2 is a upper bound for achievable N.
If N > S * 2, we can return false directly in O(1)

Note that it's the same to prove with the numbers 512 ~ 1511, or even smaller range.

N/2 times check, O(S) to check a number in string S.
The overall time complexity has upper bound O(S^2).

Java:

    public boolean queryString(String S, int N) {
        for (int i = N; i > N / 2; --i)
            if (!S.contains(Integer.toBinaryString(i)))
                return false;
        return true;
    }
*/
// Count n
class Solution {
    public boolean queryString(String s, int n) {
        for(int i = n; i > 0; i--) {
            if(!s.contains(Integer.toBinaryString(i))) {
                return false;
            }                
        }
        return true;
    }
}

// Only count n/2
class Solution {
    public boolean queryString(String s, int n) {
        for(int i = n; i > n/2; i--) {
            if(!s.contains(Integer.toBinaryString(i))) {
                return false;
            }                
        }
        return true;
    }
}
