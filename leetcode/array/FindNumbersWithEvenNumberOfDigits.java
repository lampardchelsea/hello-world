/**
Refer to
https://leetcode.com/problems/find-numbers-with-even-number-of-digits/
Given an array nums of integers, return how many of them contain an even number of digits.

Example 1:
Input: nums = [12,345,2,6,7896]
Output: 2
Explanation: 
12 contains 2 digits (even number of digits). 
345 contains 3 digits (odd number of digits). 
2 contains 1 digit (odd number of digits). 
6 contains 1 digit (odd number of digits). 
7896 contains 4 digits (even number of digits). 
Therefore only 12 and 7896 contain an even number of digits.

Example 2:
Input: nums = [555,901,482,1771]
Output: 1 
Explanation: 
Only 1771 contains an even number of digits.

Constraints:
1 <= nums.length <= 500
1 <= nums[i] <= 10^5
*/

// Solution 1: Hard code with given constrains
// Refer to
// https://leetcode.com/problems/find-numbers-with-even-number-of-digits/discuss/459489/JAVA-solution-with-100-better-space-and-Time
class Solution {
    public int findNumbers(int[] nums) {
        int result = 0;
        int len = nums.length;
        for(int i = 0; i < len; i++) {
            if(nums[i] > 9 && nums[i] < 100 || nums[i] > 999 && nums[i] < 10000 || nums[i] == 100000) {
                result++;
            }
        }
        return result;
    }
}

// Solution 2: Brute Force
// Refer to
// https://leetcode.com/problems/find-numbers-with-even-number-of-digits/discuss/458937/JAVA-MUST-READ-Solution-with-EXPLANATION-and-EXAMPLE-%3A)
class Solution {
    public int findNumbers(int[] nums) {
        int len =  nums.length;
        int result = 0;
        for(int i = 0; i < len; i++) {
            if(numDigits(nums[i]) % 2 == 0) {
                result++;
            }
        }
        return result;
    }
    
    private int numDigits(int num) {
        int count = 1;
        while(num / 10 > 0) {
            count++;
            num /= 10;
        }
        return count;
    }
}
