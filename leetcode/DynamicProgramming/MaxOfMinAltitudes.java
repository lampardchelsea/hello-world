/**
 Refer to
 https://leetcode.com/discuss/interview-question/383669/
 Given a matrix with r rows and c columns, find the maximum score of a path starting at [0, 0] and ending at [r-1, c-1]. 
 The score of a path is the minimum value in that path. For example, the score of the path 8 → 4 → 5 → 9 is 4.

Don't include the first or final entry. You can only move either down or right at any point in time.

Example 1:
Input:
[[5, 1],
 [4, 5]]
Output: 4
Explanation:
Possible paths:
5 → 1 → 5 => min value is 1
5 → 4 → 5 => min value is 4
Return the max value among minimum values => max(4, 1) = 4.

Example 2:
Input:
[[1, 2, 3]
 [4, 5, 1]]
Output: 4
Explanation:
Possible paths:
1-> 2 -> 3 -> 1
1-> 2 -> 5 -> 1
1-> 4 -> 5 -> 1
So min of all the paths = [2, 2, 4]. Note that we don't include the first and final entry.
Return the max of that, so 4.

Related problems:
https://leetcode.com/problems/minimum-path-sum/
https://leetcode.com/problems/unique-paths-ii/
https://leetcode.com/problems/path-with-maximum-minimum-value (premium) is a different problem. In this problem we can only move in 2 directions.
*/

// Solution 1: DP
// Refer to
// https://leetcode.com/discuss/interview-question/383669/Amazon-or-OA-2019-or-Max-Of-Min-Altitudes/350117
/**
 Thought process:

Iterate over first row and column. The minimum value must be propagated all the way down the line.
Example:
6, 7, 8
5, 4, 2
8, 7, 6
The top row becomes 6, 6, 6 and the first column becomes 6, 5, 5. Resulting matrix:
6, 6, 6
5, 4, 2
5, 7, 6

Each of the internal elements in the grid will be the minimum of (1) itself, (2) the element above it in the grid, 
or (3) the element left of it in the grid. Therefore, we want to choose the maximum of two minimum comparisons. Example:
i = 1, j = 1, element = 4.
[i-1, j] = [0, 1] = 6
[i, j-1] = [1, 0] = 5
Therefore, we keep the element 4, since max(min(4, 6), min(4, 5)) == max(4, 4) == 4. For similar reasons, 
element [1, 2] will remain 2.

Element [2,1], however, will become 5. Note that position [2, 1] can be reached via 6 -> 5 -> 5 -> 7, so we select 
max(5, 4) and choose 5 as the new element:
i = 2, j = 1, element = 7.
[i-1, j] = [1, 1] = 4
[i, j-1] = [2, 0] = 5
max(min(7, 4), min(7, 5)) == max(4, 5) == 5.
For similar reasons, element [2, 2] will become 5.

We return the value in the bottom right. The answer is 5.

Edit: There is a minor error in this solution, please see the replies to this comment for potential fixes. (We are 
supposed to ignore the first and last element in the path, which we can achieve with only minor adjustments to this algorithm.)
*/

// Refer to
// https://leetcode.com/discuss/interview-question/383669/Amazon-or-OA-2019-or-Max-Of-Min-Altitudes/369694
// "static void main" must be defined in a public class.
public class Main {
  public static void main(String[] args) {
    int[][] grid1 = new int[][] { {5, 1}, {4, 5} };                        // 4
    int[][] grid2 = new int[][] { {5, 1, 7}, {4, 8, 5} };                  // 4
    int[][] grid3 = new int[][] { {1, 9, 9}, {9, 9, 9}, {9, 9, 9} };       // 1 (if the first entry is not considered)
    int[][] grid4 = new int[][] { {10, 7, 3}, {12, 11, 9}, {1, 2, 8} };    // 8 (same reason)
    int[][] grid5 = new int[][] { {20, 20, 3}, {20, 3, 20}, {3, 20, 20} }; // 3
    System.out.println("grid1: Expected: 4, Actual: " + maxScore2D(grid1));
    System.out.println("grid2: Expected: 4, Actual: " + maxScore2D(grid2));
    System.out.println("grid3: Expected: 1, Actual: " + maxScore2D(grid3) + " (if the first entry is not considered)");
    System.out.println("grid4: Expected: 8, Actual: " + maxScore2D(grid4) + " (same reason)");
    System.out.println("grid5: Expected: 3, Actual: " + maxScore2D(grid5));
  }
  
  // Define: dp[i][j] is the max score from [0][0] to [i][j]
  // Recurence Formula: dp[i][j] = max( min(dp[i-1][j], grid[i][j]), min(dp[i][j-1]), grid[i][j] )
  // Note: Init the first entry as Integer.MAX_VALUE
  
  // DP (2D)
  // Time: O(rc) Space: O(rc)
  private static int maxScore2D(int[][] grid) {
    // Assume there is at least one element
    int r = grid.length, c = grid[0].length;
    int[][] dp = new int[r][c];
    // Init
    dp[0][0] = Integer.MAX_VALUE; // first entry is not considered
    for (int i = 1; i < r; ++i) dp[i][0] = Math.min(dp[i - 1][0], grid[i][0]);
    for (int j = 1; j < c; ++j) dp[0][j] = Math.min(dp[0][j - 1], grid[0][j]);
    // DP
    for (int i = 1; i < r; ++i) { // row by row
      for (int j = 1; j < c; ++j) {
        if (i == r - 1 && j == c - 1) {
          dp[i][j] = Math.max(dp[i - 1][j], dp[i][j - 1]); // last entry is not considered
        } else {
          int score1 = Math.min(dp[i][j - 1], grid[i][j]); // left
          int score2 = Math.min(dp[i - 1][j], grid[i][j]); // up
          dp[i][j] = Math.max(score1, score2);
        }
      }
    }
    return dp[r - 1][c - 1];
  }
  
  // DP (One Row or Column)
  // Time: O(rc) Space: O(r or c)
  // DP (One Row or Column)
  private static int maxScore1D(int[][] grid) {
    // Assume there is at least one element
    int r = grid.length, c = grid[0].length;
    int[] dp = new int[c];
    // Init
    dp[0] = Integer.MAX_VALUE; // first entry is not considered
    for (int j = 1; j < c; ++j) dp[j] = Math.min(dp[j - 1], grid[0][j]);
    // DP (for each row)
    for (int i = 1; i < r; ++i) {
      // update the first element in each row
      dp[0] = Math.min(dp[0], grid[i][0]);
      for (int j = 1; j < c; ++j) {
        if (i == r - 1 && j == c - 1) {
          dp[j] = Math.max(dp[j - 1], dp[j]); // last entry is not considered
        } else {
          int score1 = Math.min(dp[j - 1], grid[i][j]); // left  dp[i][j-1]
          int score2 = Math.min(dp[j], grid[i][j]);     // up    dp[i-1][j]
          dp[j] = Math.max(score1, score2);
        }
      }
    }
    return dp[c - 1];
  }
}



