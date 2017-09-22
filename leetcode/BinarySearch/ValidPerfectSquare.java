/**
 * Refer to
 * https://leetcode.com/problems/valid-perfect-square/description/
 * Given a positive integer num, write a function which returns True if num 
 * is a perfect square else False.

	Note: Do not use any built-in library function such as sqrt.
	
	Example 1:
	
	Input: 16
	Returns: True
	Example 2:
	
	Input: 14
	Returns: False
 * 
 * Solution
 * Binary Search
 * http://www.cnblogs.com/grandyang/p/5619296.html
 * Math
 * https://discuss.leetcode.com/topic/49325/a-square-number-is-1-3-5-7-java-code
 */
public class ValidPerfectSquare {
	// Solution 1: Binary Search
	// Caution: As we are using mid * mid as square expression,
	// we must use long instead of int
	// E.g Input: 808201
	//     Output: false
	//     Expected: true
	public boolean isPerfectSquare(int num) {
        if(num == 0) {
            return true;
        }
        long start = 1;
        long end = num;
        while(start + 1 < end) {
            long mid = start + (end - start) / 2;
            if(mid * mid == num) {
                return true;
            } else if(mid * mid > num) {
                end = mid;
            } else {
                start = mid;
            }
        }
        if(start * start == num) {
            return true;
        } else if(end * end == num) {
            return true;
        }
        return false;
    }
	
	// Solution 2: Math
	/**
	 * http://www.cnblogs.com/grandyang/p/5619296.html
	         下面这种方法就是纯数学解法了，利用到了这样一条性质，完全平方数是一系列奇数之和，例如：	
		1 = 1
		4 = 1 + 3
		9 = 1 + 3 + 5
		16 = 1 + 3 + 5 + 7
		25 = 1 + 3 + 5 + 7 + 9
		36 = 1 + 3 + 5 + 7 + 9 + 11
		....
		1+3+...+(2n-1) = (2n-1 + 1)n/2 = n*n		
	            这里就不做证明了，我也不会证明，知道了这条性质，就可以利用其来解题了，时间复杂度为O(sqrt(n))。
	 */
	public boolean isPerfectSquare_Math(int num) {
		if(num == 0) {
			return true;
		}
		int i = 1;
		while(num > 0) {
			num -= i;
			i += 2;
		}
		return num == 0;
	}
	
}

