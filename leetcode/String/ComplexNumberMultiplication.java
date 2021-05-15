/**
Refer to
https://leetcode.com/problems/complex-number-multiplication/
A complex number can be represented as a string on the form "real+imaginaryi" where:

real is the real part and is an integer in the range [-100, 100].
imaginary is the imaginary part and is an integer in the range [-100, 100].
i2 == -1.
Given two complex numbers num1 and num2 as strings, return a string of the complex number that represents their multiplications.

Example 1:
Input: num1 = "1+1i", num2 = "1+1i"
Output: "0+2i"
Explanation: (1 + i) * (1 + i) = 1 + i2 + 2 * i = 2i, and you need convert it to the form of 0+2i.

Example 2:
Input: num1 = "1+-1i", num2 = "1+-1i"
Output: "0+-2i"
Explanation: (1 - i) * (1 - i) = 1 + i2 - 2 * i = -2i, and you need convert it to the form of 0+-2i.

Constraints:
num1 and num2 are valid complex numbers.
*/

// Solution 1: Split by "\\+" and sepcial handle of 0 in real and imaginary part
class Solution {
    public String complexNumberMultiply(String num1, String num2) {
        String[] one = num1.split("\\+");
        int real_one = Integer.valueOf(one[0]);
        int img_one = Integer.valueOf(one[1].substring(0, one[1].length() - 1));
        String[] two = num2.split("\\+");
        int real_two = Integer.valueOf(two[0]);
        int img_two = Integer.valueOf(two[1].substring(0, two[1].length() - 1));
        int real_result = real_one * real_two - img_one * img_two;
        int img_result = real_one * img_two + real_two * img_one;
        String result = "";
        if(real_result == 0) {
            result += "0";
        } else {
            result += real_result;
        }
        if(img_result == 0) {
            return result += "+0i";
        } else {
            return result += "+" + img_result + "i";
        }
    }
}
