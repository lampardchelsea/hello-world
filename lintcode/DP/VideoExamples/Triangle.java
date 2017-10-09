/**
 * Refer to
 * http://www.lintcode.com/en/problem/triangle/
 * Given a triangle, find the minimum path sum from top to bottom. Each step you may move 
   to adjacent numbers on the row below.

   Notice
  Bonus point if you are able to do this using only O(n) extra space, where n is the total 
  number of rows in the triangle.

  Have you met this question in a real interview? Yes
  Example
  Given the following triangle:

  [
       [2],
      [3,4],
     [6,5,7],
    [4,1,8,3]
  ]
  The minimum path sum from top to bottom is 11 (i.e., 2 + 3 + 5 + 1 = 11).
 *
 * Solution
 * http://www.jiuzhang.com/solutions/triangle/
*/
public class Solution {
    /*
     * @param triangle: a list of lists of integers
     * @return: An integer, minimum path sum
     */
    public int minimumTotal(int[][] triangle) {
       // Bottom up
       if(triangle == null || triangle.length == 0) {
           return -1;
       }
       if(triangle[0].length == 0) {
           return -1;
       }
       // state: f[x][y] = minimum path value from x,y to bottom
       int n = triangle.length;
       int[][] f = new int[n][n];
       // initialize (bottom row)
       for(int i = 0; i < n; i++) {
           f[n - 1][i] = triangle[n - 1][i];
       }
       // bottom up function
       for(int i = n - 2; i >= 0; i--) {
           for(int j = 0; j <= i; j++) {
               f[i][j] = Math.min(f[i + 1][j], f[i + 1][j + 1]) + triangle[i][j];
           }
       }
       // answer (final status)
       return f[0][0];
    }
}
