/**
Refer to
https://leetcode.com/problems/ugly-number-iii/
Given four integers n, a, b, and c, return the nth ugly number.

Ugly numbers are positive integers that are divisible by a, b, or c.

Example 1:
Input: n = 3, a = 2, b = 3, c = 5
Output: 4
Explanation: The ugly numbers are 2, 3, 4, 5, 6, 8, 9, 10... The 3rd is 4.

Example 2:
Input: n = 4, a = 2, b = 3, c = 4
Output: 6
Explanation: The ugly numbers are 2, 3, 4, 6, 8, 9, 10, 12... The 4th is 6.

Example 3:
Input: n = 5, a = 2, b = 11, c = 13
Output: 10
Explanation: The ugly numbers are 2, 4, 6, 8, 10, 11, 12, 13... The 5th is 10.

Example 4:
Input: n = 1000000000, a = 2, b = 217983653, c = 336916467
Output: 1999999984

Constraints:
1 <= n, a, b, c <= 109
1 <= a * b * c <= 1018
It is guaranteed that the result will be in range [1, 2 * 109].
*/

// Solution 1:
// Refer to
// https://leetcode.com/discuss/general-discussion/786126/python-powerful-ultimate-binary-search-template-solved-many-problems
/**
1201. Ugly Number III [Medium]
Write a program to find the n-th ugly number. Ugly numbers are positive integers which are divisible by a or b or c.

Example :

Input: n = 3, a = 2, b = 3, c = 5
Output: 4
Explanation: The ugly numbers are 2, 3, 4, 5, 6, 8, 9, 10... The 3rd is 4.
Input: n = 4, a = 2, b = 3, c = 4
Output: 6
Explanation: The ugly numbers are 2, 3, 4, 6, 8, 9, 10, 12... The 4th is 6.
Nothing special. Still finding the Kth-Smallest. We need to design an enough function, given an input num, 
determine whether there are at least n ugly numbers less than or equal to num. Since a might be a multiple 
of b or c, or the other way round, we need the help of greatest common divisor to avoid counting duplicate numbers.

def nthUglyNumber(n: int, a: int, b: int, c: int) -> int:
    def enough(num) -> bool:
        total = mid//a + mid//b + mid//c - mid//ab - mid//ac - mid//bc + mid//abc
        return total >= n

    ab = a * b // math.gcd(a, b)
    ac = a * c // math.gcd(a, c)
    bc = b * c // math.gcd(b, c)
    abc = a * bc // math.gcd(a, bc)
    left, right = 1, 10 ** 10
    while left < right:
        mid = left + (right - left) // 2
        if enough(mid):
            right = mid
        else:
            left = mid + 1
    return left
*/

// https://leetcode.com/problems/ugly-number-iii/discuss/387582/1ms-Java-Binary-Search
/**
https://www.geeksforgeeks.org/find-the-nth-term-divisible-by-a-or-b-or-c/
to understand it better read this post of GFG

Our range ~ 10^9
so replace int to long

public static int nthUglyNumber(int n, int a, int b, int c) {
        int low = 1, high = Integer.MAX_VALUE, mid;

        while (low < high) {
            mid = low + (high - low) / 2;
            if (divTermCount(a, b, c, mid) < n)
                low = mid + 1;
            else
                high = mid;
        }
        return low;
    }

    static long gcd(long a, long b) {
        if (a == 0)
            return b;
        return gcd(b % a, a);
    }

    static long lcm(long a, long b) {
        return (a * b) / gcd(a, b);
    }

    static long divTermCount(long a, long b, long c, long num) {
        return ((num / a) + (num / b) + (num / c)
                - (num / lcm(a, b))
                - (num / lcm(b, c))
                - (num / lcm(a, c))
                + (num / lcm(a, lcm(b, c))));
    }
*/
class Solution {
    public int nthUglyNumber(int n, int a, int b, int c) {
        int lo = 1;
        int hi = Integer.MAX_VALUE;
        while(lo < hi) {
            int mid = lo + (hi - lo) / 2;
            if(countNum(a, b, c, mid) >= n) {
                hi = mid;
            } else {
                lo = mid + 1;
            }
        }
        return lo;
    }
    
    private long countNum(long a, long b, long c, long num) {
        return num / a + num / b + num / c - (num / lcm(a, b) + num / lcm(b, c) + num / lcm(a, c)) + (num / lcm(a, lcm(b, c)));
    }
    
    private long gcd(long x, long y) {
        if(x == 0) {
            return y;
        }
        return gcd(y % x, x);
    }
    
    private long lcm(long x, long y) {
        return x * y / gcd(x, y);
    }
}
