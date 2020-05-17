/**
 Refer to
 https://leetcode.com/problems/rectangle-area/
 Find the total area covered by two rectilinear rectangles in a 2D plane.

 Each rectangle is defined by its bottom left corner and top right corner as shown in the figure.

 Rectangle Area

 Example:
 Input: A = -3, B = 0, C = 3, D = 4, E = 0, F = -1, G = 9, H = 2
 Output: 45
 
 Note:
 Assume that the total area is never beyond the maximum possible value of int.
*/

// Solution 1: Math
// Refer to
// https://leetcode.com/problems/rectangle-area/discuss/62138/My-Java-solution-Sum-of-areas-Overlapped-area
class Solution {
    public int computeArea(int A, int B, int C, int D, int E, int F, int G, int H) {
        int area1 = (C - A) * (D - B);
        int area2 = (G - E) * (H - F);
        int left = Math.max(A, E);
        int right = Math.min(C, G);
        int bottom = Math.max(B, F);
        int top = Math.min(D, H);
        int overlap = 0;
        if(right > left && top > bottom) {
            overlap = (right - left) * (top - bottom);
        }
        return area1 + area2 - overlap;
    }
}
