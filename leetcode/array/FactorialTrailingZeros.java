/**
 * Refer to
 * https://leetcode.com/problems/factorial-trailing-zeroes/description/
 * Given an integer n, return the number of trailing zeroes in n!.
   Note: Your solution should be in logarithmic time complexity.
 *
 * Solution
 * https://discuss.leetcode.com/topic/6513/simple-c-c-solution-with-detailed-explaination
 * https://discuss.leetcode.com/topic/6513/simple-c-c-solution-with-detailed-explaination/4
 * http://www.purplemath.com/modules/factzero.htm
*/
public class FactorialTrailingZeros {
	public int trailingZeros(int n) {
        int divisor = 5;
        int result = 0;
        // Directly use 'n', don't need to calculate n!
        while(n / divisor > 0) {
            result += n / divisor;
            // Don't use multiply, as n > 1808548329 will cause overflow issue
            // on 'divisor * 5' because of 'divisor * 5 > Integer.MAX_VALUE / 5'
            // and it is still valid.
            //divisor *= 5; 
            n = n / divisor;
        }
        return result;
	}
	
	public static void main(String[] args) {
		FactorialTrailingZeros f = new FactorialTrailingZeros();
		int n = 3;
		int result = f.trailingZeros(n);
		System.out.println(result);
	}
}
