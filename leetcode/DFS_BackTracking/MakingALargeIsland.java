/**
 Refer to
 https://leetcode.com/problems/making-a-large-island/
 In a 2D grid of 0s and 1s, we change at most one 0 to a 1.

After, what is the size of the largest island? (An island is a 4-directionally connected group of 1s).

Example 1:
Input: [[1, 0], [0, 1]]
Output: 3
Explanation: Change one 0 to 1 and connect two 1s, then we get an island with area = 3.

Example 2:
Input: [[1, 1], [1, 0]]
Output: 4
Explanation: Change the 0 to 1 and make the island bigger, only one island with area = 4.

Example 3:
Input: [[1, 1], [1, 1]]
Output: 4
Explanation: Can't change any 0 to 1, only one island with area = 4.

Notes:
1 <= grid.length = grid[0].length <= 50.
0 <= grid[i][j] <= 1.
*/

// Solution 1: Native DFS + Backtracking
// Refer to
// https://www.cnblogs.com/grandyang/p/11669063.html
/**
这道题在只有0和1的矩阵中用相连的1来表示小岛，现在说是有一个把0变为1的机会，这样原本不相邻的岛就有可能变的相邻了，
从而组成一个更大的岛，让求出可能组成的最大的岛屿的面积，也就是相连的1的个数。在 LeetCode 中关于岛屿的题其实也做
过许多，比如 [Number of Distinct Islands II](http://www.cnblogs.com/grandyang/p/8542820.html)，
[Max Area of Island](http://www.cnblogs.com/grandyang/p/7712724.html)，
[Number of Distinct Islands](http://www.cnblogs.com/grandyang/p/7698778.html)，
[Number of Islands II](http://www.cnblogs.com/grandyang/p/5190419.html)，
和 [Number of Islands](http://www.cnblogs.com/grandyang/p/4402656.html)。
其实大多题目的本质都是用 DFS 或者 BFS 去遍历所有相连的1，当然这道题也不例外。博主最开始的想法是首先用 DFS 来
找出每个岛屿，然后把同一个岛屿的位置坐标都放到同一个 HashSet 中，这样就有了很多 HashSet，然后遍历所有的0的位置，
对每个0位置，遍历其周围4个邻居，然后看邻居位置有没有属于岛屿的，有的话就把该岛屿的 HashSet 编号记录下来，遍历完
4个邻居后，在把所有的相连的岛屿中的1个数加起来（因为 HashSet 可以直接求出集合中数字的总个数），每次更新结果 res 
即可。这种方法是可以通过 OJ 的，速度还比下面展示的两种方法要快，就是代码比较长，没有下面方法的简洁，这里就不贴了。
*/

// https://leetcode.com/problems/making-a-large-island/discuss/127256/DFS-JAVA-AC-CONCISE-SOLUTION
// https://leetcode.com/problems/making-a-large-island/discuss/127256/DFS-JAVA-AC-CONCISE-SOLUTION/153727
// https://leetcode.com/problems/making-a-large-island/discuss/127256/DFS-JAVA-AC-CONCISE-SOLUTION/668315
// Time: O(grid.length * grid[0].length + numZeros * grid.length * grid[0].length) time, because we iterate 
// through the entire grid, for every '0' we do a dfs which at most iterates through the entire grid again.
// Space: O(grid.length * grid[0].length) space, because of the visited array space during the dfs.
// Runtime: 197 ms, faster than 13.19% of Java online submissions for Making A Large Island.
// Memory Usage: 39.3 MB, less than 5.06% of Java online submissions for Making A Large Island.
class Solution {
    public int largestIsland(int[][] grid) {
        int m = grid.length;
        int n = grid[0].length;
        // Default as 0 if no 0 cell that means all grid value as 1 as one island
        int result = 0;
        // Try all cases with start at cell == 0
        for(int i = 0; i < m; i++) {
            for(int j = 0; j < n; j++) {
                if(grid[i][j] == 0) {
                    grid[i][j] = 1;
                    result = Math.max(result, helper(i, j, grid, new boolean[m][n]));
                    grid[i][j] = 0; // backtrack for next cell == 0 to check again
                }
            }
        }
        return result == 0 ? m * n : result;
    }
    
    int[] dx = new int[] {0,0,-1,1};
    int[] dy = new int[] {1,-1,0,0};
    private int helper(int x, int y, int[][] grid, boolean[][] visited) {
        if(x >= 0 && x < grid.length && y >= 0 && y < grid[0].length && grid[x][y] == 1 && !visited[x][y]) {
            visited[x][y] = true;
            int count = 1;
            for(int i = 0; i < 4; i++) {
                int new_x = x + dx[i];
                int new_y = y + dy[i];
                count += helper(new_x, new_y, grid, visited);
            }
            return count;
        }
        return 0;
    }
}

// Solution 2: Coloring islands into different color and sum up
// Refer to
// https://leetcode.com/problems/making-a-large-island/discuss/127015/C%2B%2B-with-picture-O(n*m)
// https://leetcode.com/problems/making-a-large-island/discuss/127015/C++-with-picture-O(n*m)/134932
/**
For each 1 in the grid, we paint all connected 1 with the next available color (2, 3, and so on). 
We also remember the size of the island we just painted with that color.
Then, we analyze all 0 in the grid, and sum sizes of connected islands (based on the island color). 
Note that the same island can connect to 0 more than once. The example below demonstrates this idea 
(the answer is highlighted):
*/
// Time Complexity: O(m * n)
// Space Complexity: 
// Runtime: 8 ms, faster than 75.09% of Java online submissions for Making A Large Island.
// Memory Usage: 39 MB, less than 5.06% of Java online submissions for Making A Large Island.
class Solution {
    public int largestIsland(int[][] grid) {
        // (key, value) -> color, island size
        Map<Integer, Integer> map = new HashMap<Integer, Integer>();
        // We won't paint island 0, hence make its size 0, we will use this value later  
        map.put(0, 0);
        int m = grid.length;
        int n = grid[0].length;
        // 0 and 1 is already used in grid, hence we start colorIndex from 2 
        int colorIndex = 2;
        // DFS to identify all groups of 1(island) and set up as different color
        for(int i = 0; i < m; i++) {
            for(int j = 0; j < n; j++) {
                if(grid[i][j] == 1) {
                    // Set up current island cells to number = 'color'
                    int islandSize = helper(i, j, grid, colorIndex);
                    map.put(colorIndex, islandSize);
                    // Update colorIndex to next valid number
                    colorIndex++;
                }
            }
        }
        // If there is no island 0 from grid, result should be the size of islands of first color
        // If there is no island 1 from grid, result should be 0 
        int result = map.getOrDefault(2, 0);
        // Try all cell == 0 as candidates
        for(int i = 0; i < m; i++) {
            for(int j = 0; j < n; j++) {
                if(grid[i][j] == 0) {
                    // We use a set to avoid repeatly adding islands with the same color
                    Set<Integer> set = new HashSet<Integer>();
                    // If current island is at the boundary, we add 0 to the set, whose 
                    // value is 0 in the map
                    set.add(i > 0 ? grid[i - 1][j] : 0);
                    set.add(i < m - 1 ? grid[i + 1][j] : 0);
                    set.add(j > 0 ? grid[i][j - 1] : 0);
                    set.add(j < n - 1 ? grid[i][j + 1] : 0);
                    // We need to count current island as well, hence we initital size with 1
                    int size = 1;
                    for(int color : set) {
                        size += map.get(color);
                    }
                    result = Math.max(result, size);
                }
            }
        }
        return result;
    }
    
    // Helper method to paint current island and all its connected neighbors
    // Return the size of all painted islands at the end
    int[] dx = new int[] {0,0,1,-1};
    int[] dy = new int[] {1,-1,0,0};
    private int helper(int x, int y, int[][] grid, int color) {
        if(x >= 0 && x < grid.length && y >= 0 && y < grid[0].length && grid[x][y] == 1) {
            grid[x][y] = color;
            int count = 1;
            for(int i = 0; i < 4; i++) {
                int new_x = x + dx[i];
                int new_y = y + dy[i];
                count += helper(new_x, new_y, grid, color);
            }
            return count;
        }
        return 0;
    }
}
