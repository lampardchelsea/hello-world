import java.math.BigInteger;

/**
 * Refer to
 * https://leetcode.com/problems/additive-number/description/
 * Additive number is a string whose digits can form additive sequence.

    A valid additive sequence should contain at least three numbers. Except for the first two numbers, 
    each subsequent number in the sequence must be the sum of the preceding two.

    For example:
    "112358" is an additive number because the digits can form an additive sequence: 1, 1, 2, 3, 5, 8.

    1 + 1 = 2, 1 + 2 = 3, 2 + 3 = 5, 3 + 5 = 8
    "199100199" is also an additive number, the additive sequence is: 1, 99, 100, 199.
    1 + 99 = 100, 99 + 100 = 199
    Note: Numbers in the additive sequence cannot have leading zeros, so sequence 1, 2, 03 or 1, 02, 3 is invalid.

    Given a string containing only digits '0'-'9', write a function to determine if it's an additive number.

    Follow up:
    How would you handle overflow for very large input integers?
 *
 * Solution 
 * https://discuss.leetcode.com/topic/29856/java-recursive-and-iterative-solutions
 * https://www.youtube.com/watch?v=LziJZT2uRwc
*/
public class AdditiveNumber {
public boolean isAdditiveNumber(String num) {
        if(num == null || num.length() < 3) {
            return false;
        }
        int n = num.length();
        // The idea is quite straight forward. Generate the first and second of the sequence, 
        // check if the rest of the string match the sum recursively. i and j are length 
        // of the first and second number. i should in the range of [0, n/2]. The length 
        // of their sum should >= max(i,j)
        // Caution: Must i <= n / 2, if only i < n / 2, then num = 123 not pass
        for(int i = 1; i <= n / 2; i++) {
            // additive sequence cannot have leading zeros
            if(num.charAt(0) == '0' && i > 1) {
                return false;
            }
            BigInteger x1 = new BigInteger(num.substring(0, i));
            // The length of their sum should >= max(i,j)
            for(int j = 1; Math.max(i, j) <= n - i - j; j++) {
                if(num.charAt(i) == '0' && j > 1) {
                    //return false;
                    // Refer to 
                    // https://www.youtube.com/watch?v=LziJZT2uRwc
                    // Why here is 'break' not 'return false' ?
                    // Because when we dealing with 1st number, if 1st number not right,
                    // we remove that solution directly by return false; but for the 2nd
                    // number we can set it back to initial status as start after 1st
                    // number again, so, don't directly return false, just break out to
                    // outside to 1st number level and start new loop
                    break;
                }
                BigInteger x2 = new BigInteger(num.substring(i, i + j));
                if(isValid(x1, x2, i + j, num)) {
                    return true;
                }
            }
        }
        return false;
    }
    
    private boolean isValid(BigInteger x1, BigInteger x2, int start, String num) {
        if(start == num.length()) {
            return true;
        }
        // Update x2 as new sum (x2 + x1), update x1 as new original x2
        x2 = x2.add(x1);
        x1 = x2.subtract(x1);
        // Change to string for later check if num start with sum at index = 'start'
        String sum = x2.toString(); 
        return num.startsWith(sum, start) && isValid(x1, x2, start + sum.length(), num);
    }

}
