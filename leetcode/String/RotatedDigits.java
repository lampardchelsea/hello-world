/**
Refer to
https://leetcode.com/problems/rotated-digits/
x is a good number if after rotating each digit individually by 180 degrees, we get a valid number that is different from x. 
Each digit must be rotated - we cannot choose to leave it alone.

A number is valid if each digit remains a digit after rotation. 0, 1, and 8 rotate to themselves; 2 and 5 rotate to each other 
(on this case they are rotated in a different direction, in other words 2 or 5 gets mirrored); 6 and 9 rotate to each other, 
and the rest of the numbers do not rotate to any other number and become invalid.

Now given a positive number n, how many numbers x from 1 to n are good?

Example:
Input: 10
Output: 4

Explanation: 
There are four good numbers in the range [1, 10] : 2, 5, 6, 9.
Note that 1 and 10 are not good numbers, since they remain unchanged after rotating.

Note:
n will be in range [1, 10000].
*/

// Solution 1: Intuitive
// Refer to
// https://leetcode.com/problems/rotated-digits/discuss/116547/Easily-Understood-Java-Solution
/**
class Solution {
    public int rotatedDigits(int N) {
        int count = 0;
        for (int i = 1; i <= N; i ++) {
            if (isValid(i)) count ++;
        }
        return count;
    }
    
    public boolean isValid(int N) {
         // Valid if N contains ATLEAST ONE 2, 5, 6, 9 AND NO 3, 4 or 7s
        boolean validFound = false;
        while (N > 0) {
            if (N % 10 == 2) validFound = true;
            if (N % 10 == 5) validFound = true;
            if (N % 10 == 6) validFound = true;
            if (N % 10 == 9) validFound = true;
            if (N % 10 == 3) return false;
            if (N % 10 == 4) return false;
            if (N % 10 == 7) return false;
            N = N / 10;
        }
        return validFound;
    }
}
*/
class Solution {
    public int rotatedDigits(int n) {
        int count = 0;
        for(int i = 1; i <= n; i++) {
            if(isValid(i)) {
                count++;
            }
        }
        return count;
    }
    
    private boolean isValid(int n) {
        boolean result = false;
        while(n > 0) {
            if(n % 10 == 2 || n % 10 == 5 || n % 10 == 6 || n % 10 == 9) {
                result = true;
            }
            if(n % 10 == 3 || n % 10 == 4 || n % 10 == 7) {
                return false;
            }
            n /= 10;
        }
        return result;
    } 
}

// Solution 2: Improvement
// Refer to
// https://leetcode.com/problems/rotated-digits/discuss/116547/Easily-Understood-Java-Solution/116844
class Solution {
    public int rotatedDigits(int n) {
        int count = 0;
        for(int i = 1; i <= n; i++) {
            if(isValid(i)) {
                count++;
            }
            i += incrementIfNeeded(i);
        }
        return count;
    }
    
    /**
    Improved efficiency. Increments i if the leading digit of i is 3, 4 or 7.
    For example, if i = 300, then we know 300, 301, 302 ... 399 are all invalid.
    IncrementIfNeeded(int i) returns 99 to set i to 399 and 400 after i++ from loop increment.
    The same thing happens for 400. 
    Therefore, we only check 300 and 400, rather than 300, 301, 302, ..., 399, 400, 401, 402, ..., 499.
    Improved efficiency. Previous improvement checked all values between say 
    1,700,000 and 1,799,999 because 7 was not the most significant digit. New
    incrementIfNeeded method finds the FIRST occurrence of 3 or 7 and returns an 
    increment accordingly. 4 is not checked because we can skip it when a 3 is found.
    */
    private int incrementIfNeeded(int i) {
        int inc = 1;
        while(i >= 10) {
            int mod = i % 10;
            if(mod == 3) {
                return 2 * inc - 1;
            }
            if(mod == 7) {
                return inc - 1;
            }
            inc *= 10;
            i /= 10;
        }
        if(i == 3) {
            return 2 * inc - 1;
        } else if(i == 7) {
            return inc - 1;
        } else {
            return 0;
        }
    }
    
    private boolean isValid(int n) {
        boolean result = false;
        while(n > 0) {
            if(n % 10 == 2 || n % 10 == 5 || n % 10 == 6 || n % 10 == 9) {
                result = true;
            }
            if(n % 10 == 3 || n % 10 == 4 || n % 10 == 7) {
                return false;
            }
            n /= 10;
        }
        return result;
    } 
}
