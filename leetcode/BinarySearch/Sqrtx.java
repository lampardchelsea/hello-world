/**
Refer to
https://leetcode.com/problems/sqrtx/
Given a non-negative integer x, compute and return the square root of x.

Since the return type is an integer, the decimal digits are truncated, and only the integer part of the result is returned.

Example 1:
Input: x = 4
Output: 2

Example 2:
Input: x = 8
Output: 2
Explanation: The square root of 8 is 2.82842..., and since the decimal part is truncated, 2 is returned.

Constraints:
0 <= x <= 231 - 1
*/

// Solution 1: Binary Search
// Refer to
// https://leetcode.com/discuss/general-discussion/786126/python-powerful-ultimate-binary-search-template-solved-many-problems
/**
69. Sqrt(x) [Easy]
Implement int sqrt(int x). Compute and return the square root of x, where x is guaranteed to be a non-negative integer. 
Since the return type is an integer, the decimal digits are truncated and only the integer part of the result is returned.

Example:
Input: 4
Output: 2
Input: 8
Output: 2

Easy one. First we need to search for minimal k satisfying condition k^2 > x, then k - 1 is the answer to the question. 
We can easily come up with the solution. Notice that I set right = x + 1 instead of right = x to deal with special input '
cases like x = 0 and x = 1.

def mySqrt(x: int) -> int:
    left, right = 0, x + 1
    while left < right:
        mid = left + (right - left) // 2
        if mid * mid > x:
            right = mid
        else:
            left = mid + 1
    return left - 1  # `left` is the minimum k value, `k - 1` is the answer
*/
