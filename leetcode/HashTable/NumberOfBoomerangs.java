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
 *
 * Solution:
 * 这道题定义了一种类似回旋镖形状的三元组结构，要求第一个点和第二个点之间的距离跟第一个点和第三个点之间的距离相等。
 * 现在给了我们n个点，让我们找出回旋镖的个数。那么我们想，如果我们有一个点a，还有两个点b和c，如果ab和ac之间的距离相等，
 * 那么就有两种排列方法abc和acb；如果有三个点b，c，d都分别和a之间的距离相等，那么有六种排列方法，abc, acb, acd, adc, 
 * abd, adb，那么是怎么算出来的呢，很简单，如果有n个点和a距离相等，那么排列方式为n(n-1)，这属于最简单的排列组合问题了，
 * 我大天朝中学生都会做的。那么我们问题就变成了遍历所有点，让每个点都做一次点a，然后遍历其他所有点，统计和a距离相等的
 * 点有多少个，然后分别带入n(n-1)计算结果并累加到res中
*/

// Refer to
// https://discuss.leetcode.com/topic/66587/clean-java-solution-o-n-2-166ms
// http://www.cnblogs.com/grandyang/p/6049382.html
// http://blog.csdn.net/mebiuw/article/details/53096120
public class Solution {
    // Two dimensions array, first dimension represent which point, 
    // second dimension represent point's coordinate.
    // e.g point[i][0] = x and point[i][1] = y reprsent the ith point's
    // coordinate as (x, y)
    public int numberOfBoomerangs(int[][] points) {
        int res = 0;
        int n = points.length;
        Map<Integer, Integer> map = new HashMap<Integer, Integer>();
        // Scan every point and put it as i in [i, j, k]
        for(int i = 0; i < n; i++) {
            // Scan remained points and record each pair's(other point
            // like point[j] to current point[i]) distance, record
            // each distance and how many pairs equal to this distance
            // into map.
            for(int j = 0; j < n; j++) {
                if(i == j) {
                    continue;
                }
                int d = getDistance(points[i], points[j]);
                map.put(d, map.getOrDefault(d, 0) + 1);
            }
            // Calculate all possible tuple combinations based on point[i],
            // and add onto result
            for(Integer val : map.values()) {
                res += val * (val - 1);
            }
            // After accumulate to result, clear point[i]'s information on 
            // map to prepare next point
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
