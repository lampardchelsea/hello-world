/**
Refer to
https://leetcode.com/problems/max-difference-you-can-get-from-changing-an-integer/
You are given an integer num. You will apply the following steps exactly two times:

Pick a digit x (0 <= x <= 9).
Pick another digit y (0 <= y <= 9). The digit y can be equal to x.
Replace all the occurrences of x in the decimal representation of num by y.
The new integer cannot have any leading zeros, also the new integer cannot be 0.
Let a and b be the results of applying the operations to num the first and second times, respectively.

Return the max difference between a and b.

Example 1:
Input: num = 555
Output: 888
Explanation: The first time pick x = 5 and y = 9 and store the new integer in a.
The second time pick x = 5 and y = 1 and store the new integer in b.
We have now a = 999 and b = 111 and max difference = 888

Example 2:
Input: num = 9
Output: 8
Explanation: The first time pick x = 9 and y = 9 and store the new integer in a.
The second time pick x = 9 and y = 1 and store the new integer in b.
We have now a = 9 and b = 1 and max difference = 8

Example 3:
Input: num = 123456
Output: 820000

Example 4:
Input: num = 10000
Output: 80000
Example 5:

Input: num = 9288
Output: 8700

Constraints:
1 <= num <= 10^8
*/

// Solution 1:
// Refer to
// https://leetcode.com/problems/max-difference-you-can-get-from-changing-an-integer/discuss/608804/Python-Clean-greedy-solution-with-explanation
// https://leetcode.com/problems/max-difference-you-can-get-from-changing-an-integer/discuss/608804/Python-Clean-greedy-solution-with-explanation/534721
/**
We need to maximize the difference of two numbers a and b. Therefore we should maximize a and minimize b.

To maximize a we need to find the left-most digit in it that is not a 9 and replace all occurences of that digit with 9.
9 is the maximum possible digit and the more left we find a candidate for replacement the bigger the result gets.

To minimize b we need to find the left-most digit in it that is not a 0 and replace all occurences of that digit with 0.
But we have to watch out: We are not allowed to convert the first digit to a 0 as we should not create a number with trailing zeroes.
Therefore we can only replace the first digit with a 1. All other digits can be replaced with a 0 if they are not a 1 as that 
would also replace the trailing 1 with a 0.

class Solution:
    def maxDiff(self, num: int) -> int:
        a = b = str(num)

        for digit in a:
            if digit != "9":
                a = a.replace(digit, "9")
                break
        
        if b[0] != "1":
            b = b.replace(b[0], "1")
        else:
            for digit in b[1:]:
                if digit not in "01":
                    b = b.replace(digit, "0")
                    break
        
        return int(a) - int(b)
*/
class Solution {
    public int maxDiff(int num) {
        String str1 = String.valueOf(num);
        String str2 = String.valueOf(num);
        // Finding out the first character which is not '9' 
        // and replacing all it's occurrences
        for(int i = 0; i < str1.length(); i++) {
            if(str1.charAt(i) != '9') {
                str1 = str1.replaceAll("" + str1.charAt(i), "9");
                break;
            }
        }
        // If the first digit itself not '1' then replace the occurrences 
        // of it with '1'. As there are no trailing zeros allowed.
        if(str2.charAt(0) != '1') {
            str2 = str2.replaceAll("" + str2.charAt(0), "1");	
        } else {
            // Find out the first occurrence which is neither 1 nor 0. 
            // and replace it with zero
            for(int i = 1; i < str2.length(); i++) {
                if(!"01".contains("" + str2.charAt(i))) {
                    str2 = str2.replaceAll("" + str2.charAt(i), "0");
                    break;
                }
            }
        }
        return Integer.valueOf(str1) - Integer.valueOf(str2);
    }
}
