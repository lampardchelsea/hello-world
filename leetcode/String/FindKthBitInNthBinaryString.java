/**
Refer to
https://leetcode.com/problems/find-kth-bit-in-nth-binary-string/
Given two positive integers n and k, the binary string  Sn is formed as follows:

S1 = "0"
Si = Si-1 + "1" + reverse(invert(Si-1)) for i > 1
Where + denotes the concatenation operation, reverse(x) returns the reversed string x, and invert(x) 
inverts all the bits in x (0 changes to 1 and 1 changes to 0).

For example, the first 4 strings in the above sequence are:
S1 = "0"
S2 = "011"
S3 = "0111001"
S4 = "011100110110001"

Return the kth bit in Sn. It is guaranteed that k is valid for the given n.

Example 1:
Input: n = 3, k = 1
Output: "0"
Explanation: S3 is "0111001". The first bit is "0".

Example 2:
Input: n = 4, k = 11
Output: "1"
Explanation: S4 is "011100110110001". The 11th bit is "1".

Example 3:
Input: n = 1, k = 1
Output: "0"

Example 4:
Input: n = 2, k = 3
Output: "1"

Constraints:
1 <= n <= 20
1 <= k <= 2n - 1
*/

// Solution 1: Brute Force
// Refer to
// How to reverse a string ?
// https://stackoverflow.com/questions/7569335/reverse-a-string-in-java
// Inverting bits of binary string java
// https://stackoverflow.com/questions/22628724/inverting-bits-of-binary-string-java
class Solution {
    public char findKthBit(int n, int k) {
        String s = "0";
        for(int i = 2; i <= n; i++) {
            s = s + "1" + reverse(invert(s));
        }
        return s.charAt(k - 1);
    }
    
    private String reverse(String s) {
        return new StringBuilder(s).reverse().toString();
    }
    
    private String invert(String s) {
        return s.replaceAll("0", "x").replaceAll("1", "0").replaceAll("x", "1");
    }
}

// Solution 2:
// Refer to
// https://leetcode.com/problems/find-kth-bit-in-nth-binary-string/discuss/781181/JavaC%2B%2BPython-Iterative-Solutions
/**
Explanation
If k is on the left part of the string, we do nothing.

If K is right in the middle, and the middle is 1.
We flip count times, we return count % 2 == 0 ? '1' : '0'

If k is on the right part of the string,
find it's symeric postion k = l + 1 - k.
Also we increment count++.

Complexity
Time O(n)
Space O(1)

Solution 0
Same code as explained above.
Java

    public char findKthBit(int n, int k) {
        int count = 0, l = (1 << n) - 1;
        while (k > 1) {
            if (k == l / 2 + 1)
                return count % 2 == 0 ? '1' : '0';
            if (k > l / 2) {
                k = l + 1 - k;
                count++;
            }
            l /= 2;
        }
        return count % 2 == 0 ? '0' : '1';
    }

Solution 2
Remove the count, just use a flip boolean, (short for isFlipped).
Java:

    public char findKthBit(int n, int k) {
        int flip = 0, l = (1 << n) - 1;
        while (k > 1) {
            if (k == l / 2 + 1)
                return flip == 0 ? '1' : '0';
            if (k > l / 2) {
                k = l + 1 - k;
                flip ^= 1;
            }
            l /= 2;
        }
        return flip == 0 ? '0' : '1';
    }
*/



