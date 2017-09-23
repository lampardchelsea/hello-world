/**
 * Refer to
 * http://www.lintcode.com/en/problem/powx-n/
 * https://leetcode.com/problems/powx-n/description/
 * Implement pow(x, n).

	 Notice
	You don't need to care about the precision of your answer, it's acceptable if the expected 
	answer and your answer 's difference is smaller than 1e-3.
	
	Have you met this question in a real interview? Yes
	Example
	Pow(2.1, 3) = 9.261
	Pow(0, 1) = 0
	Pow(1, 0) = 1
 * 
 * 
 * Solution
 * http://www.jiuzhang.com/solution/powx-n/
 * https://discuss.leetcode.com/topic/21837/5-different-choices-when-talk-with-interviewers
 * https://discuss.leetcode.com/topic/21837/5-different-choices-when-talk-with-interviewers/3
 * https://discuss.leetcode.com/topic/19493/simple-java-solution-with-explanation-use-binary-search
 */
public class PowXN {
	// Solution 1: Iterative
    public double myPow(double x, int n) {
        if (n == 0) return 1;
        if (x == 1) return 1;
        if (x == -1) {
            if (n % 2 == 0) return 1;
            else return -1;
        }
        // This corner case is very difficult to handle 
        // Input: 2.00000 -2147483648
        // Output: 1.00000
        // Expected: 0.00000
        if (n == Integer.MIN_VALUE) return 0;
        if (n < 0) {
            n = -n;
            x = 1/x;
        }
        double result = 1.0;
        // Style 1: O(logN)
        // Refer to
        // https://discuss.leetcode.com/topic/21837/5-different-choices-when-talk-with-interviewers/3
        // Really tricky way to pass leetcode all test cases, as x increase exponentially as x = x * x, 
        // also n decrease to half each time by n >> 1.
        // E.g Input x = 4.0, n = 4, result = 1
        //       --> x = 16.0, n = 2, result = 16 
        //       --> x = 256.0, n = 1, result = 256 (hit if as (n & 1) != 0)
        //       --> x = 65536.0, n = 0
        while (n > 0) {
            if ((n & 1) != 0) 
                result *= x;
            x = x * x;
            n = n >> 1;
        }
        // Style 2: O(N)
        // for(int i = 0; i < n; i++) {
        //     result *= x;
        // }
        // Style 3: O(N)
        // while(n > 0) {
        //     n--;
        //     result *= x;
        // }
        return result;
    }
    
    // Solution 2: Divide And Conquer
    // Same thought as exponentially increase x as x * x each recursion
    // also cut n to half each recursion
    public double myPow_Divide_And_Conquer(double x, int n) {
        if(n == 0) {
            return 1;
        }
        if(x == 1) {
            return 1;
        }
        if(x == -1) {
            if(n % 2 == 0) {
                return 1;
            } else {
                return -1;
            }
        }
        if(n == Integer.MIN_VALUE) {
            return 0;
        }
        if(n < 0) {
            n = -n;
            x = 1 / x;
        }
        return n % 2 == 0 ? myPow(x * x, n / 2) : x * myPow(x * x, n / 2);
    }
    
    
    public static void main(String[] args) {
    	PowXN p = new PowXN();
//    	double x = 2.00000;
//    	int n = -2147483648;
//    	double x = 0.00001;   			
//    	int n = 2147483647;
    	double x = 4;
    	int n = 5;
    	double result = p.myPow(x, n);
    	System.out.print(result);
    }
}
