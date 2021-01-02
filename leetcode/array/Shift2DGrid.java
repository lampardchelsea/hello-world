/**
Refer to
https://leetcode.com/problems/shift-2d-grid/
Given a 2D grid of size m x n and an integer k. You need to shift the grid k times.

In one shift operation:
Element at grid[i][j] moves to grid[i][j + 1].
Element at grid[i][n - 1] moves to grid[i + 1][0].
Element at grid[m - 1][n - 1] moves to grid[0][0].
Return the 2D grid after applying shift operation k times.

Example 1:
Input: grid = [[1,2,3],[4,5,6],[7,8,9]], k = 1
Output: [[9,1,2],[3,4,5],[6,7,8]]

Example 2:
Input: grid = [[3,8,1,9],[19,7,2,5],[4,6,11,10],[12,0,21,13]], k = 4
Output: [[12,0,21,13],[3,8,1,9],[19,7,2,5],[4,6,11,10]]

Example 3:
Input: grid = [[1,2,3],[4,5,6],[7,8,9]], k = 9
Output: [[1,2,3],[4,5,6],[7,8,9]]

Constraints:
m == grid.length
n == grid[i].length
1 <= m <= 50
1 <= n <= 50
-1000 <= grid[i][j] <= 1000
0 <= k <= 100
*/

// Solution 1: Native O(m * n * k) solution
// Refer to
// https://leetcode.com/problems/shift-2d-grid/solution/
/**
Approach 1: Simulation
Intuition
We are given instructions on how to apply the transformation. Therefore, we could just repeat applying the transformation kk times.
Algorithm
Reading explanations with lots of notation and array indexes can be confusing, and there is a lot of room for misunderstanding. 
A good first step might be to draw a picture (a rough sketch on a whiteboard would be fine) of each of the 3 cases to be certain 
that you understand them.
Element at grid[i][j] moves to grid[i][j + 1].
Element at grid[i][n - 1] moves to grid[i + 1][0].
Element at grid[m - 1][n - 1] moves to grid[0][0].
Then, kk times, we need to create a new 2D array and follow the given rules to move the values. If we're using Java, we'll also 
need to then convert the output from a 2D Array to a 2D list.
Complexity Analysis

Time Complexity: O(n⋅m⋅k), where the grid size is n \cdot mn⋅m and we need to apply the transform k times.

Space Complexity: O(n⋅m). We are creating a new array in each iteration of the loop. We only keep track of at most 2 
arrays at a time, though. The rest are garbage collected/ free'd.
*/
class Solution {
    public List<List<Integer>> shiftGrid(int[][] grid, int k) {
        int rows = grid.length;
        int cols = grid[0].length;
        for(int i = 0; i < k; i++) {
            int[][] cache = new int[rows][cols];
            // Case 1: Element at grid[i][j] moves to grid[i][j + 1].
            for(int j = 0; j < rows; j++) {
                for(int l = 0; l < cols - 1; l++) {
                    cache[j][l + 1] = grid[j][l]; 
                }
            }
            // Case 2: Element at grid[i][n - 1] moves to grid[i + 1][0].
            for(int j = 0; j < rows - 1; j++) {
                cache[j + 1][0] = grid[j][cols - 1];
            }
            // Case 3: Element at grid[m - 1][n - 1] moves to grid[0][0].
            cache[0][0] = grid[rows - 1][cols - 1];
            // Update grid with cache
            grid = cache;
        }
        List<List<Integer>> result = new ArrayList<List<Integer>>();
        for(int[] row : grid) {
            List<Integer> list = new ArrayList<Integer>();
            for(int a : row) {
                list.add(a);
            }
            result.add(list);
        }
        return result;
    }
}

// Solution 2: ZigZag
// Style 1: Shift one cell k times
// https://leetcode.com/problems/shift-2d-grid/solution/
/**
Approach 2: Simulation, Recycling Same Array
Intuition
The previous approach created k new arrays. We can simplify it to do the movements in-place. To do this, 
let's look at how an individual value moves around the grid. Looking at the movement of a single value is 
a good strategy for getting started on 2D grid translation problems. The value we're looking at is the 
yellow square. The numbers show the order in which it moved into each cell.
The movement is a straightforward pattern. The value moves in "reading" order, and then when it gets to 
the bottom right, it wraps around to the top left.
We can simulate this wrapping in-place by repeatedly moving each value "forward" by one place.

Algorithm
For each step, we'll need to keep track of the current value we're moving forward. For Java, we'll need 
to finish by copying the input into a 2D list. If you were writing this algorithm in your own Java code 
and wanted it to be in-place, you would choose the same input and output type. Shift start from the last
cell (position at matrix[rows - 1][cols - 1]) which shift to first cell (position at matrix[0][0]), and
this process repeat k times.
*/
class Solution {
    public List<List<Integer>> shiftGrid(int[][] grid, int k) {
        int rows = grid.length;
        int cols = grid[0].length;
        for(int i = 0; i < k; i++) {
            int prev = grid[rows - 1][cols - 1];
            for(int j = 0; j < rows; j++) {
                for(int l = 0; l < cols; l++) {
                    int temp = grid[j][l];
                    grid[j][l] = prev;
                    prev = temp;
                }
            }
        }
        List<List<Integer>> result = new ArrayList<List<Integer>>();
        for(int[] row : grid) {
            List<Integer> list = new ArrayList<Integer>();
            for(int a : row) {
                list.add(a);
            }
            result.add(list);
        }
        return result;
    }
}

// Style 2: Shift k cells one time
// https://leetcode.com/problems/shift-2d-grid/discuss/431102/JavaPython-3-2-simple-codes-using-mod-space-O(1).
/**
Number the cells from 0 to m * n - 1;
In case k >= m * n, use k % (m * n) to avoid those whole cycles of m * n operations;
Method 1: space O(1) - excluding return list
Since shifting right will put the last k cells in grid on the first k cells, we start from the kth cells from last, 
the index of which is m * n - k % (m * n).

    public List<List<Integer>> shiftGrid(int[][] grid, int k) {
        int m = grid.length, n = grid[0].length; 
        int start = m * n - k % (m * n);
        LinkedList<List<Integer>> ans = new LinkedList<>();
        for (int i = start; i < m * n + start; ++i) {
            int j = i % (m * n), r = j / n, c = j % n;
            if ((i - start) % n == 0)
                ans.add(new ArrayList<>());
            ans.peekLast().add(grid[r][c]);
        }
        return ans;
    }
*/
class Solution {
    public List<List<Integer>> shiftGrid(int[][] grid, int k) {
        int m = grid.length, n = grid[0].length;
        int start = m * n - k % (m * n);
        LinkedList<List<Integer>> ans = new LinkedList<>();
        for (int i = start; i < m * n + start; ++i) {
            int j = i % (m * n), r = j / n, c = j % n;
            if ((i - start) % n == 0)
                ans.add(new ArrayList<>());
            ans.peekLast().add(grid[r][c]);
        }
        return ans;
    }
}
