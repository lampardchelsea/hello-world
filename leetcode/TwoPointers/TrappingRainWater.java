/**
Refer to
https://leetcode.com/problems/trapping-rain-water/
Given n non-negative integers representing an elevation map where the width of each bar is 1, 
compute how much water it can trap after raining.

Example 1:
Input: height = [0,1,0,2,1,0,1,3,2,1,2,1]
Output: 6
Explanation: The above elevation map (black section) is represented by array [0,1,0,2,1,0,1,3,2,1,2,1]. 
In this case, 6 units of rain water (blue section) are being trapped.

Example 2:
Input: height = [4,2,0,3,2,5]
Output: 9

Constraints:
n == height.length
0 <= n <= 3 * 104
0 <= height[i] <= 105
*/

// Solution 1: Brute Force
// Refer to
// https://leetcode.com/problems/trapping-rain-water/solution/
/**
Approach 1: Brute force
Intuition
Do as directed in question. For each element in the array, we find the maximum level of water it can trap after the rain, 
which is equal to the minimum of maximum height of bars on both the sides minus its own height.

Algorithm
Initialize ans=0
Iterate the array from left to right:
Initialize left_max=0 and right_max=0
Iterate from the current element to the beginning of array updating:
left_max=max(left_max,height[j])
Iterate from the current element to the end of array updating:
right_max=max(right_max,height[j])
Add min(left_max,right_max)−height[i] to ans

Complexity Analysis
Time complexity: O(n^2). For each element of array, we iterate the left and right parts.
Space complexity: O(1) extra space.
*/
class Solution {
    public int trap(int[] height) {
        if(height == null || height.length == 0) {
            return 0;
        }
        int size = height.length;
        int sum = 0;
        for(int i = 0; i < size; i++) {
            int max_left = 0;
            int max_right = 0;
            for(int j = i; j >= 0; j--) {
                max_left = Math.max(height[j], max_left);
            }
            for(int j = i; j < size; j++) {
                max_right = Math.max(height[j], max_right);
            }
            sum += Math.min(max_left, max_right) - height[i];
        }
        return sum;
    }
}

// Solution 2: DP
// Refer to
// https://leetcode.com/problems/trapping-rain-water/solution/
/**

*/
