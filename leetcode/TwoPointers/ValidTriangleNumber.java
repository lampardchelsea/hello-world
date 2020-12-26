/**
Refer to
https://leetcode.com/problems/valid-triangle-number/
Given an array consists of non-negative integers, your task is to count the number of triplets chosen from 
the array that can make triangles if we take them as side lengths of a triangle.

Example 1:
Input: [2,2,3,4]
Output: 3
Explanation:
Valid combinations are: 
2,3,4 (using the first 2)
2,3,4 (using the second 2)
2,2,3
Note:
The length of the given array won't exceed 1000.
The integers in the given array are in the range of [0, 1000]
*/

// Solution 1: Two Pointers
// Refer to
// https://leetcode.com/problems/valid-triangle-number/discuss/104174/Java-O(n2)-Time-O(1)-Space
class Solution {
    public int triangleNumber(int[] nums) {
        Arrays.sort(nums);
        int count = 0;
        int n = nums.length;
        // i --> the longest side, after sort can only be choosen
        // after index >= 2
        // l, r --> other 2 sides
        for(int i = n - 1; i >= 2; i--) {
            int l = 0;
            int r = i - 1;
            while(l < r) {
                // If satisfy triangle build relation, all combinations
                // between l and r can be treat as able to build triangle
                if(nums[l] + nums[r] > nums[i]) {
                    count += r - l;
                    r--;
                } else {
                    l++;
                }
            }
        }
        return count;
    }
}
