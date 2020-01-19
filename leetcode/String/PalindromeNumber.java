/**
  Refer to
  https://leetcode.com/problems/palindrome-number/
  Determine whether an integer is a palindrome. An integer is a palindrome when it reads the same backward as forward.

Example 1:

Input: 121
Output: true
Example 2:

Input: -121
Output: false
Explanation: From left to right, it reads -121. From right to left, it becomes 121-. Therefore it is not a palindrome.
Example 3:

Input: 10
Output: false
Explanation: Reads 01 from right to left. Therefore it is not a palindrome.
Follow up:

Coud you solve it without converting the integer to a string?
*/
// Solution 1: Convert int to String
// Refer to
// https://leetcode.com/problems/palindrome-number/solution/
class Solution {
    public boolean isPalindrome(int x) {
        String str = String.valueOf(x);
        int start = 0;
        int end = str.length() - 1;
        while(start < end){
            if(str.charAt(start++) != str.charAt(end--)) return false;
        }
        return true;
    }
}

// Solution 2: No convert
// Refer to
// https://leetcode.com/problems/palindrome-number/discuss/5127/9-line-accepted-Java-code-without-the-need-of-handling-overflow/5915
// Hi guys. I just don't know why we need to concern the overflow. When the reversed number overflows, 
// it will becomes negative number which will return false when compared with x. Here is my AC code.
class Solution {
    public boolean isPalindrome(int x) {
        // compare half of the digits in x, so don't need to deal with overflow.
        if(x < 0) {
            return false;
        }
        int y = x;
        int result = 0;
        while(y != 0) {
            result = result * 10 + y % 10;
            y /= 10;
        }
        return x == result;
    }
}
