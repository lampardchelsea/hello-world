/**
 Refer to
 https://leetcode.com/problems/minimum-area-rectangle/
 Given a set of points in the xy-plane, determine the minimum area of a rectangle formed 
 from these points, with sides parallel to the x and y axes.

If there isn't any rectangle, return 0.
Example 1:
Input: [[1,1],[1,3],[3,1],[3,3],[2,2]]
Output: 4

Example 2:
Input: [[1,1],[1,3],[3,1],[3,3],[4,1],[4,3]]
Output: 2
 
Note:
1 <= points.length <= 500
0 <= points[i][0] <= 40000
0 <= points[i][1] <= 40000
All points are distinct.
*/
// Solution 1: Find diagonal points first then use Hashmap to find other two points
// Refer to
// https://leetcode.com/problems/minimum-area-rectangle/discuss/192025/Java-N2-Hashmap
class Solution {
    public int minAreaRect(int[][] points) {
        Map<Integer, Set<Integer>> map = new HashMap<Integer, Set<Integer>>();
        for(int[] p : points) {
            if(!map.containsKey(p[0])) {
                map.put(p[0], new HashSet<Integer>());
            }
            map.get(p[0]).add(p[1]);
        }
        int min = Integer.MAX_VALUE;
        for(int[] p1 : points) {
            for(int[] p2 : points) {
                // Here, the two points p1 and p2 are considered as diagonal of 
                // the rectangle. Since sides of the rectangle are parallel to 
                // the x-axis and y-axis, the two points of diagonal cannot have 
                // the same x or y. So if consider p1 as one of the diagonal point,
                // another diagonal point p2 should not have same x or same y as p1.
                // E.g Input: [[1,1],[1,3],[3,1],[3,3],[2,2]]
                // if p1 = [1,1], to find a p2, will skip [1,3],[3,1], and p2 = [3,3]
                if(p1[0] == p2[0] || p1[1] == p2[1]) {
                    continue;
                }
                // After locate two diagonal points p1 and p2, need to locate other
                // two points, if able to found these two other points, then we can
                // build a rectangle, now you need help from previous build map, which
                // store all points as key-value pair and key based on x axis
                // Still use same input as example
                // {1={1,3}, 2={2}, 3={1,3}}
                // if p1 = [1,1], p2 = [3,3], only when we find [1,3] and [3,1] will
                // build the rectangle
                // use map to find [1,3] as map.get(x = 1).contains(y = 3) is true
                // use map to find [3,1] as map.get(x = 3).contains(x = 1) is true
                // then we can build a rectangle
                if(map.get(p1[0]).contains(p2[1]) && map.get(p2[0]).contains(p1[1])) {
                    min = Math.min(min, Math.abs(p1[0] - p2[0]) * Math.abs(p1[1] - p2[1]));
                }
            }
        }
        return min == Integer.MAX_VALUE ? 0 : min;
    }
}
