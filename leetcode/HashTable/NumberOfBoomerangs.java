/**
 * Given n points in the plane that are all pairwise distinct, a "boomerang" is a tuple of points (i, j, k) 
 * such that the distance between i and j equals the distance between i and k (the order of the tuple matters).
 * Find the number of boomerangs. You may assume that n will be at most 500 and coordinates of points are all 
 * in the range [-10000, 10000] (inclusive).
 * Example:
 * Input: [[0,0],[1,0],[2,0]]
 * Output: 2
 * Explanation:
 * The two boomerangs are [[1,0],[0,0],[2,0]] and [[1,0],[2,0],[0,0]]
*/

// Refer to
// https://discuss.leetcode.com/topic/66587/clean-java-solution-o-n-2-166ms
// http://www.cnblogs.com/grandyang/p/6049382.html
// http://blog.csdn.net/mebiuw/article/details/53096120
public class Solution {
    public int numberOfBoomerangs(int[][] points) {
        int res = 0;
        int n = points.length;
        Map<Integer, Integer> map = new HashMap<Integer, Integer>();
        for(int i = 0; i < n; i++) {
            for(int j = 0; j < n; j++) {
                if(i == j) {
                    continue;
                }
                int d = getDistance(points[i], points[j]);
                map.put(d, map.getOrDefault(d, 0) + 1);
            }
            for(Integer val : map.values()) {
                res += val * (val - 1);
            }
            map.clear();
        }
        return res;
    }
    
    public int getDistance(int[] a, int[] b) {
        int dx = a[0] - b[0];
        int dy = a[1] - b[1];
        return dx * dx + dy * dy;
    }
}
