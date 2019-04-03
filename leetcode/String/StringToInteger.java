/**
 * Refer to
 * 
 * Implement atoi to convert a string to an integer.
 * Hint: Carefully consider all possible input cases. If you want a challenge, please do not see below and ask yourself 
 * what are the possible input cases.
 * Notes: It is intended for this problem to be specified vaguely (ie, no given input specs). You are responsible to 
 * gather all the input requirements up front.
 * Update (2015-02-10):
 * The signature of the C++ function had been updated. If you still see your function signature accepts a const 
 * char * argument, please click the reload button to reset your code definition.
 * spoilers alert... click to show requirements for atoi.
 * Requirements for atoi:
 * The function first discards as many whitespace characters as necessary until the first non-whitespace character 
 * is found. Then, starting from this character, takes an optional initial plus or minus sign followed by as many 
 * numerical digits as possible, and interprets them as a numerical value.
 * The string can contain additional characters after those that form the integral number, which are ignored and have 
 * no effect on the behavior of this function.
 * If the first sequence of non-whitespace characters in str is not a valid integral number, or if no such sequence 
 * exists because either str is empty or it contains only whitespace characters, no conversion is performed.
 * If no valid conversion could be performed, a zero value is returned. If the correct value is out of the range of 
 * representable values, INT_MAX (2147483647) or INT_MIN (-2147483648) is returned.
 * 
 * Solution
 * https://discuss.leetcode.com/topic/2666/my-simple-solution/14
 * 
*/
public class Solution {
    public int myAtoi(String str) {
        // I think we only need to handle four cases:
        // (1) discards all leading whitespaces
        // (2) sign of the number
        // (3) overflow
        // (4) invalid input
        int i = 0;
        int sign = 1;
        int base = 0;
        if(str.isEmpty()) {
           return 0; 
        }
        while(str.charAt(i) == ' ') {
            i++;
        }
        if(str.charAt(i) == '-' || str.charAt(i) == '+') {
            sign = str.charAt(i++) == '-' ? -1 : 1;
        }
        while (i < str.length() && str.charAt(i) >= '0' && str.charAt(i) <= '9') {
            if(base > Integer.MAX_VALUE / 10 || (base == Integer.MAX_VALUE / 10 && str.charAt(i) - '0' > 7)) {
                return sign == 1 ? Integer.MAX_VALUE : Integer.MIN_VALUE;
            }
            base = base * 10 + (str.charAt(i++) - '0');
        }
        return base * sign;
    }
}

// Another style (More easy to understand for checking Integer Max / Min overflow part)
// Refer to
// https://www.programcreek.com/2012/12/leetcode-string-to-integer-atoi/
class Solution {
    public int myAtoi(String str) {
        // Situation need to handle
        // (1) Remove leading white space
        // (2) Check significant
        // (3) Check char valid or not
        // (4) Integer max or min overflow (by using double first then convert to int)
        if(str == null || str.length() < 1) {
            return 0;
        }
        // trim white spaces
        str = str.trim();
        // In case after removing leading and backing space left as empty string
        if(str.length() > 0) {
            char flag = '+';
            // check negative or positive
            int i = 0;
            if(str.charAt(0) == '-') {
                flag = '-';
                i++;
            } else if(str.charAt(0) == '+') {
                i++;
            }
            // Why was double used for result?
            // Integer has range [-2147483648 , 2147483647] in Java. If the number is
            // -214748364, the algorithm processes it as a positive integer and then change
            // sign. The number would exceed the integer range if an integer is used.
            // use double to store result
            double result = 0;
            // calculate value
            while(str.length() > i && str.charAt(i) >= '0' && str.charAt(i) <= '9') {
                result = result * 10 + (str.charAt(i) - '0');
                i++;
            }
            if(flag == '-') {
                result = -result;
            }
            // handle max and min
            if(result > Integer.MAX_VALUE) {
                return Integer.MAX_VALUE;
            }
            if(result < Integer.MIN_VALUE) {
                return Integer.MIN_VALUE;
            }
            return (int) result;  
        }
        return 0;
    }
}
