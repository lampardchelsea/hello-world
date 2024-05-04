/**
 * Refer to
 * https://leetcode.com/problems/subarray-product-less-than-k/description/
 * Your are given an array of positive integers nums.

  Count and print the number of (contiguous) subarrays where the product of all the elements in the subarray is less than k.

  Example 1:
  Input: nums = [10, 5, 2, 6], k = 100
  Output: 8
  Explanation: The 8 subarrays that have product less than 100 are: [10], [5], [2], [6], [10, 5], [5, 2], [2, 6], [5, 2, 6].
  Note that [10, 5, 2] is not included as the product of 100 is not strictly less than k.
  Note:

  0 < nums.length <= 50000.
  0 < nums[i] < 1000.
  0 <= k < 10^6.
 *
 * Solution
 * https://leetcode.com/problems/subarray-product-less-than-k/discuss/108861/JavaC++-Clean-Code-with-Explanation
*/
class Solution {
    /**
        1. The idea is always keep an max-product-window less than K;
        2. Every time shift window by adding a new number on the right(j), if the product is greater than k, 
        then try to reduce numbers on the left(i), until the subarray product fit less than k again, (subarray could be empty);
        3. Each step introduces x new subarrays, where x is the size of the current window (j + 1 - i);
        example:
        for window (5, 2), when 6 is introduced, it add 3 new subarray: (5, (2, (6)))
                (6)
             (2, 6)
          (5, 2, 6)
    */
    public int numSubarrayProductLessThanK(int[] nums, int k) {
        // a * b * c < k -> [a,b,c][a,b][b,c][a][b][c] -> 6 entries
        // a * b * c * d < k -> [a,b,c,d][a,b,c][b,c,d][a,b][b,c][c,d][a][b][c][d] -> 10 entries
        int count = 0;
        int prod = 1;
        int i = 0;
        for(int j = 0; j < nums.length; j++) {
            prod *= nums[j];
            while(i <= j && prod >= k) {
                prod /= nums[i++];
            }
            count += (j - i + 1); 
        }
        return count;
    }
}



















































https://leetcode.com/problems/subarray-product-less-than-k/
Given an array of integers nums and an integer k, return the number of contiguous subarrays where the product of all the elements in the subarray is strictly less than k.

Example 1:
Input: nums = [10,5,2,6], k = 100
Output: 8
Explanation: The 8 subarrays that have product less than 100 are:
[10], [5], [2], [6], [10, 5], [5, 2], [2, 6], [5, 2, 6]
Note that [10, 5, 2] is not included as the product of 100 is not strictly less than k.

Example 2:
Input: nums = [1,2,3], k = 0
Output: 0

Constraints:
- 1 <= nums.length <= 3 * 10^4
- 1 <= nums[i] <= 1000
- 0 <= k <= 10^6
--------------------------------------------------------------------------------
Attempt 1: 2022-09-09 (30min, spend time to figure out condition avoid forever loop)
class Solution { 
    public int numSubarrayProductLessThanK(int[] nums, int k) { 
        int product = 1; 
        int count = 0; 
        int len = nums.length; 
        int i = 0; 
        // e.g nums = {10,5,2,6}, k = 100 
        // i = 0, j = 0 -> product = 10 < 100, count += (0 - 0 + 1) = 1 
        // {10} 
        // i = 0, j = 1 -> product = 10 * 5 < 100, count += (1 - 0 + 1) = 3 
        // {10} || new add {10,5}, {5} 
        // i = 0, j = 2 -> product = 10 * 5 * 2 >= 100, product = 100 / 10 = 10, i = 1 
        // count += (2 - 1 + 1) = 5 
        // {10}, {10,5}, {5} || new add {2}, {5,2} 
        // i = 1, j = 3 -> product = 5 * 2 * 6 < 100, count += (3 - 1 + 1) = 8 
        // {10}, {10,5}, {5}, {2}, {5,2} || new add {5,2,6}, {2,6}, {6} 
        // ------------------------------------------------------------ 
        // e.g nums = {1,2,3}, k = 0 
        // i = 0, j = 0 -> product = 1 >= 0, product = 1 / 1 = 1, i = 1 
        // count += (0 - 1 + 1) = 0 
        // i = 1, j = 1 -> product = 2 >= 0, product = 2 / 2 = 1, i = 2 
        // count += (1 - 2 + 1) = 0 
        // i = 2, j = 2 -> product = 3 >= 0, product = 3 / 3 = 1, i = 3 
        // count += (2 - 3 + 1) = 0 
        for(int j = 0; j < len; j++) { 
            product *= nums[j]; 
            // Must include i <= j, test out by nums = {1,2,3}, k = 0 
            // if no i <= j, for product /= nums[i], i will increase to 3 
            // and index out of boundary as product >= k always satisfied 
            // and loop forever result into i keep increasing  
            while(i <= j && product >= k) { 
                product /= nums[i]; 
                i++; 
            } 
            // The distance between left and right end pointer equal to newly 
            // add number of subarrays 
            count += (j - i + 1); 
        } 
        return count; 
    } 
}

Space Complexity: O(1) 
Time Complexity: O(n)
