/**
Refer to
https://leetcode.com/problems/find-nearest-point-that-has-the-same-x-or-y-coordinate/
You are given two integers, x and y, which represent your current location on a Cartesian grid: (x, y). 
You are also given an array points where each points[i] = [ai, bi] represents that a point exists at (ai, bi). 
A point is valid if it shares the same x-coordinate or the same y-coordinate as your location.

Return the index (0-indexed) of the valid point with the smallest Manhattan distance from your current location. 
If there are multiple, return the valid point with the smallest index. If there are no valid points, return -1.

The Manhattan distance between two points (x1, y1) and (x2, y2) is abs(x1 - x2) + abs(y1 - y2).

Example 1:
Input: x = 3, y = 4, points = [[1,2],[3,1],[2,4],[2,3],[4,4]]
Output: 2
Explanation: Of all the points, only [3,1], [2,4] and [4,4] are valid. Of the valid points, [2,4] and [4,4] have the 
smallest Manhattan distance from your current location, with a distance of 1. [2,4] has the smallest index, so return 2.

Example 2:
Input: x = 3, y = 4, points = [[3,4]]
Output: 0
Explanation: The answer is allowed to be on the same location as your current location.

Example 3:
Input: x = 3, y = 4, points = [[2,3]]
Output: -1
Explanation: There are no valid points.

Constraints:
1 <= points.length <= 104
points[i].length == 2
1 <= x, y, ai, bi <= 104
*/

// Solution 1:
// Refer to
// https://leetcode.com/problems/find-nearest-point-that-has-the-same-x-or-y-coordinate/discuss/1096346/JavaPython-3-Straight-forward-codes.
/**
Explanation by @lionkingeatapple

Because we want A point that shares the same x-coordinate or the same y-coordinate as your location, dx * dy == 0 
indicate either dx equals zero or dy equals zero, so we can make the product of dx and dy to be zero. dx and dy means 
the difference of x-coordinate and y-coordinate respectively. If the difference is zero, then they must be equal or 
shares the same x/y-coordinate.

Also, credit to @KellyBundy for improvement.

    public int nearestValidPoint(int x, int y, int[][] points) {
        int index = -1; 
        for (int i = 0, smallest = Integer.MAX_VALUE; i < points.length; ++i) {
            int dx = x - points[i][0], dy = y - points[i][1];
            if (dx * dy == 0 && Math.abs(dy + dx) < smallest) {
                smallest = Math.abs(dx + dy);
                index = i;
            }
        }
        return index;
    }
*/
class Solution {
    public int nearestValidPoint(int x, int y, int[][] points) {
        int index = -1;
        int min = Integer.MAX_VALUE;
        for(int i = 0; i < points.length; i++) {
            int dx = points[i][0] - x;
            int dy = points[i][1] - y;
            if(dx * dy == 0 && Math.abs(dx + dy) < min) {
                min = Math.abs(dx + dy);
                index = i;
            }
        }
        return index;
    }
}
