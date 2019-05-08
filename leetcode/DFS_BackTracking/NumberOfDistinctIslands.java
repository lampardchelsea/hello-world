/**
 Refer to
 https://www.cnblogs.com/grandyang/p/7698778.html
 Given a non-empty 2D array grid of 0's and 1's, an island is a group of 1's (representing land) 
 connected 4-directionally (horizontal or vertical.) You may assume all four edges of the grid 
 are surrounded by water.

Count the number of distinct islands. An island is considered to be the same as another if and 
only if one island can be translated (and not rotated or reflected) to equal the other.

Example 1:
11000
11000
00011
00011
Given the above grid map, return 1.

Example 2:
11011
10000
00001
11011
Given the above grid map, return 3.

Notice that:
11
1
and
 1
11
are considered different island shapes, because we do not consider reflection / rotation.

Note: The length of each dimension in the given grid does not exceed 50.
*/
// Solution 1:
// Refer to
// https://www.cnblogs.com/grandyang/p/7698778.html
/**
 这道题让我们求不同岛屿的个数，是之前那道Number of Islands的拓展，这道题的难点是如何去判断两个岛屿是否是不同的岛屿，
 首先1的个数肯定是要相同，但是1的个数相同不能保证一定是相同的岛屿，比如例子2中的那两个岛屿的就不相同，就是说两个相同
 的岛屿通过平移可以完全重合，但是不能旋转。那么我们如何来判断呢，我们发现可以通过相对位置坐标来判断，比如我们使用岛屿
 的最左上角的1当作基点，那么基点左边的点就是(0,-1)，右边的点就是(0,1), 上边的点就是(-1,0)，下面的点就是(1,0)。
 那么例子1中的两个岛屿都可以表示为[(0,0), (0,1), (1,0), (1,1)]，点的顺序是基点-右边点-下边点-右下点。通过这样就
 可以判断两个岛屿是否相同了，下面这种解法我们没有用数组来存，而是encode成了字符串，比如这四个点的数组就存为
 "0_0_0_1_1_0_1_1_"，然后把字符串存入集合unordered_set中，利用其自动去重复的特性，就可以得到不同的岛屿的数量啦
*/
// https://gist.github.com/BiruLyu/807f3960d6ea16f933a7de5bd4058a06
/**
 We have print for 2nd input to check how the set works
 i = 0, base_i = 0, j = 0, base_j = 0
[0 0]
i = 0, base_i = 0, j = 1, base_j = 0
[0 0, 0 1]
i = 1, base_i = 0, j = 0, base_j = 0
[0 0, 0 1, 1 0]
compose one set
i = 0, base_i = 0, j = 3, base_j = 3
[0 0]
i = 0, base_i = 0, j = 4, base_j = 3
[0 0, 0 1]
compose one set
i = 2, base_i = 2, j = 4, base_j = 4
[0 0]
i = 3, base_i = 2, j = 4, base_j = 4
[0 0, 1 0]
i = 3, base_i = 2, j = 3, base_j = 4
[0 0, 1 0, 1 -1]
compose one set
i = 3, base_i = 3, j = 0, base_j = 0
[0 0]
i = 3, base_i = 3, j = 1, base_j = 0
[0 0, 0 1] --> Duplicate one and removed by Set automatically
compose one set
3

*/
public class Post {
    public static void main(String[] args) throws Exception {
        //int[][] grid = new int[][]{{1,1,0,0,0}, {1,1,0,0,0}, {0,0,0,1,1}, {0,0,0,1,1}};
        int[][] grid = new int[][]{{1,1,0,1,1}, {1,0,0,0,0}, {0,0,0,0,1}, {1,1,0,1,1}};        };
        System.out.println(numDistinctIslands(grid));
    }
    
    static int[] dx = new int[]{0,0,1,-1};
	   static int[] dy = new int[]{1,-1,0,0};
    public static int numDistinctIslands(int[][] grid) {
        if (grid == null || grid[0].length == 0) {
            return 0;
        }
        Set < String > result = new HashSet < String > ();
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                Set < String > set = new HashSet < String > ();
                if (grid[i][j] == 1) {
                    helper(grid, i, j, i, j, set);
                    System.out.println("compose one set");
                    result.add(set.toString());
                }
            }
        }
        return result.size();
    }

    private static void helper(int[][] grid, int i, int j, int base_i, int base_j, Set < String > set) {
        if (i < 0 || i >= grid.length || j < 0 || j >= grid[0].length || grid[i][j] == 0) {
            return;
        }
        grid[i][j] = 0;
        set.add((i - base_i) + " " + (j - base_j));
        System.out.println("i = " + i + ", base_i = " + base_i + ", j = " + j + ", base_j = " + base_j);
        System.out.println(set.toString());
        for (int k = 0; k < 4; k++) {
            helper(grid, i + dx[k], j + dy[k], base_i, base_j, set);
        }
    }
}
