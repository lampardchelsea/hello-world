/**
Refer to
https://leetcode.com/problems/maximum-product-of-two-elements-in-an-array/
Given the array of integers nums, you will choose two different indices i and j of that array. 
Return the maximum value of (nums[i]-1)*(nums[j]-1).

Example 1:
Input: nums = [3,4,5,2]
Output: 12 
Explanation: If you choose the indices i=1 and j=2 (indexed from 0), you will get the maximum value, 
that is, (nums[1]-1)*(nums[2]-1) = (4-1)*(5-1) = 3*4 = 12. 

Example 2:
Input: nums = [1,5,4,5]
Output: 16
Explanation: Choosing the indices i=1 and j=3 (indexed from 0), you will get the maximum value of (5-1)*(5-1) = 16.

Example 3:
Input: nums = [3,7]
Output: 12

Constraints:
2 <= nums.length <= 500
1 <= nums[i] <= 10^3
*/

// Solution 1: Find two maximum
// Refer to
// https://leetcode.com/problems/maximum-product-of-two-elements-in-an-array/discuss/661687/JavaPython-3-Find-the-max-2-numbers.
class Solution {
    public int maxProduct(int[] nums) {
        int max = 0;
        int second_max = 0;
        for(int i = 0; i < nums.length; i++) {
            if(nums[i] > max) {
                second_max = max;
                max = nums[i];
            } else if(nums[i] > second_max) {
                second_max = nums[i];
            }
        }
        return (max - 1) * (second_max - 1);
    }
}
