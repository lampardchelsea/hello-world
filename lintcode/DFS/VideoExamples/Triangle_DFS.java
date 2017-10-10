/**
 * Refer to
 * http://www.lintcode.com/en/problem/triangle/
 * 
 * Solution
 * https://github.com/lampardchelsea/hello-world/blob/master/lintcode/DP/VideoExamples/Triangle.java
*/
// Purely DFS with Time Complexity O(2^N)
public class Triangle {
    public int minPath;
    public int minimumTotal(int[][] triangle) {
        minPath = Integer.MAX_VALUE;
        traverse(triangle, 0, 0, 0);
        return minPath;
    }
  
    private void traverse(int[][] triangle, int x, int y, int sum) {
        if(x == triangle.length) {
            minPath = Math.min(minPath, sum);
            return minPath;
        }
        traverse(triangle, x + 1, y, sum + triangle[x + 1][y]);
        traverse(triangle, x + 1, y + 1, sum + triangle[x + 1][y + 1]);
    }
}
