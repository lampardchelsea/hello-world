/**
 * Refer to
 * http://www.lintcode.com/en/problem/container-with-most-water/
 * Given n non-negative integers a1, a2, ..., an, where each represents a point at 
   coordinate (i, ai). n vertical lines are drawn such that the two endpoints of 
   line i is at (i, ai) and (i, 0). Find two lines, which together with x-axis 
   forms a container, such that the container contains the most water.

   Notice
    You may not slant the container.
    Have you met this question in a real interview? Yes
    Example
    Given [1,3,2], the max area of the container is 2.
 *
 * Solution
 * https://leetcode.com/problems/container-with-most-water/solution/
 * How this approach works?
 * Initially we consider the area constituting the exterior most lines. Now, to maximize 
   the area, we need to consider the area between the lines of larger lengths. If we try 
   to move the pointer at the longer line inwards, we won't gain any increase in area, 
   since it is limited by the shorter line. But moving the shorter line's pointer could 
   turn out to be beneficial, as per the same argument, despite the reduction in the width. 
   This is done since a relatively longer line obtained by moving the shorter line's pointer 
   might overcome the reduction in area caused by the width reduction.
*/
public class Solution {
    /*
     * @param heights: a vector of integers
     * @return: an integer
     */
    public int maxArea(int[] heights) {
        if(heights == null || heights.length == 0) {
            return 0;
        }
        int left = 0;
        int right = heights.length - 1;
        int maxArea = 0;
        while(left < right) {
            int left_max = heights[left];
            int right_max = heights[right];
            maxArea = Math.max(maxArea, Math.min(left_max, right_max) * (right - left));
            if(left_max < right_max) {
                left++;
            } else {
                right--;
            }
        }
        return maxArea;
    }
}
