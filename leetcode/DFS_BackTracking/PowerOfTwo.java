/**
 * Refer to
 * https://leetcode.com/problems/power-of-two/description/
 * Given an integer, write a function to determine if it is a power of two.
 *
 * Solution
 * https://discuss.leetcode.com/topic/47195/4-different-ways-to-solve-iterative-recursive-bit-operation-math
*/
// Solution 1: Iterative
// Time complexity = O(log n)
class Solution {
    public boolean isPowerOfTwo(int n) {
        if(n == 0) {
            return false;
        }
        while(n % 2 == 0) {
            n = n / 2;
        }
        return n == 1;
    }
}

// Solution 2: DFS
// Time complexity = O(log n)
class Solution {
    public boolean isPowerOfTwo(int n) {
        if(n == 0) {
            return false;
        }
        return helper(n);
    }
    
    private boolean helper(int n) {
        if(n == 1) {
            return true;
        } else {
            return n % 2 == 0 && helper(n / 2);
        }
    }
}


// Solution 3: Bit Operation
// Time complexity = O(1)
/**
    If n is the power of two:
    n = 2 ^ 0 = 1 = 0b0000...00000001, and (n - 1) = 0 = 0b0000...0000.
    n = 2 ^ 1 = 2 = 0b0000...00000010, and (n - 1) = 1 = 0b0000...0001.
    n = 2 ^ 2 = 4 = 0b0000...00000100, and (n - 1) = 3 = 0b0000...0011.
    n = 2 ^ 3 = 8 = 0b0000...00001000, and (n - 1) = 7 = 0b0000...0111.
    we have n & (n-1) == 0b0000...0000 == 0
    Otherwise, n & (n-1) != 0.
    For example, n =14 = 0b0000...1110, and (n - 1) = 13 = 0b0000...1101.
*/
class Solution {
    public boolean isPowerOfTwo(int n) {
        return n>0 && ((n & (n-1)) == 0);
    }
}


// Solution 4: Math
// Time complexity = O(1)
/**
 Because the range of an integer = -2147483648 (-2^31) ~ 2147483647 (2^31-1), the max possible power of two = 2^30 = 1073741824.

(1) If n is the power of two, let n = 2^k, where k is an integer.

We have 2^30 = (2^k) * 2^(30-k), which means (2^30 % 2^k) == 0.

(2) If n is not the power of two, let n = j*(2^k), where k is an integer and j is an odd number.

We have (2^30 % j*(2^k)) == (2^(30-k) % j) != 0.

return n>0 && (1073741824 % n == 0);
Time complexity = O(1)
*/


















