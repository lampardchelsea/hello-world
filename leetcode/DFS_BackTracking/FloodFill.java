/**
 * Refer to
 * https://leetcode.com/problems/flood-fill/description/
 * An image is represented by a 2-D array of integers, each integer representing the pixel value of the image (from 0 to 65535).

    Given a coordinate (sr, sc) representing the starting pixel (row and column) of the flood fill, and a pixel value newColor, 
    "flood fill" the image.

    To perform a "flood fill", consider the starting pixel, plus any pixels connected 4-directionally to the starting pixel of 
    the same color as the starting pixel, plus any pixels connected 4-directionally to those pixels (also with the same color 
    as the starting pixel), and so on. Replace the color of all of the aforementioned pixels with the newColor.

    At the end, return the modified image.

    Example 1:
    Input: 
    image = [[1,1,1],[1,1,0],[1,0,1]]
    sr = 1, sc = 1, newColor = 2
    Output: [[2,2,2],[2,2,0],[2,0,1]]
    Explanation: 
    From the center of the image (with position (sr, sc) = (1, 1)), all pixels connected 
    by a path of the same color as the starting pixel are colored with the new color.
    Note the bottom corner is not colored 2, because it is not 4-directionally connected
    to the starting pixel.
    Note:

    The length of image and image[0] will be in the range [1, 50].
    The given starting pixel will satisfy 0 <= sr < image.length and 0 <= sc < image[0].length.
    The value of each color in image[i][j] and newColor will be an integer in [0, 65535].
 *
 *
 * Solution
 * https://leetcode.com/problems/flood-fill/discuss/109584/Java-9-liner-DFS
 * 
*/
class Solution {
    /**
      1 1 1                             2 2 2
      1 1 0  -> (1,1) with color = 2 -> 2 2 0
      1 0 1                             2 0 1
    */
    int[] dx = {1,0,0,-1};
    int[] dy = {0,1,-1,0};
    public int[][] floodFill(int[][] image, int sr, int sc, int newColor) {
        if(image == null || image.length == 0 || image[0] == null || image[0].length == 0) {
            return new int[0][0];
        }
        int rows = image.length;
        int cols = image[0].length;
        int iniColor = image[sr][sc];
        boolean[][] visited = new boolean[rows][cols];
        dfs(sr, sc, image, visited, iniColor, newColor);
        return image;
    }
    
    private void dfs(int i, int j, int[][] image, boolean[][] visited, int iniColor, int newColor) {
        if(i >= 0 && i < image.length && j >= 0 && j < image[0].length && !visited[i][j] && image[i][j] == iniColor) {
            image[i][j] = newColor;
            visited[i][j] = true;
            for(int k = 0; k < 4; k++) {
                dfs(i + dx[k], j + dy[k], image, visited, iniColor, newColor);
            }
        }
    }
}


















https://leetcode.com/problems/flood-fill/

An image is represented by an m x n integer grid image where image[i][j] represents the pixel value of the image.

You are also given three integers sr, sc, and color. You should perform a flood fill on the image starting from the pixel image[sr][sc].

To perform a flood fill, consider the starting pixel, plus any pixels connected 4-directionally to the starting pixel of the same color as the starting pixel, plus any pixels connected 4-directionally to those pixels (also with the same color), and so on. Replace the color of all of the aforementioned pixels with color.

Return the modified image after performing the flood fill.

Example 1:


```
Input: image = [[1,1,1],[1,1,0],[1,0,1]], sr = 1, sc = 1, color = 2
Output: [[2,2,2],[2,2,0],[2,0,1]]
Explanation: From the center of the image with position (sr, sc) = (1, 1) (i.e., the red pixel), all pixels connected by a path of the same color as the starting pixel (i.e., the blue pixels) are colored with the new color.
Note the bottom corner is not colored 2, because it is not 4-directionally connected to the starting pixel.
```

Example 2:
```
Input: image = [[0,0,0],[0,0,0]], sr = 0, sc = 0, color = 0
Output: [[0,0,0],[0,0,0]]
Explanation: The starting pixel is already colored 0, so no changes are made to the image.
```

Constraints:
- m == image.length
- n == image[i].length
- 1 <= m, n <= 50
- 0 <= image[i][j], color < 216
- 0 <= sr < m
- 0 <= sc < n
---
Attempt 1: 2022-12-16

Solution 1:  DFS (10 min)
```
class Solution {
    int[] dx = new int[]{0,0,1,-1};
    int[] dy = new int[]{1,-1,0,0};
    public int[][] floodFill(int[][] image, int sr, int sc, int color) {
        helper(image, sr, sc, image[sr][sc], color);
        return image;
    }

    private void helper(int[][] image, int sr, int sc, int initial_color, int color) {
        // image[sr][sc] != initial_color --> stop because of different initial color
        // image[sr][sc] == color --> stop because of already visited and updated to same color
        if(sr < 0 || sr >= image.length || sc < 0 || sc >= image[0].length || image[sr][sc] != initial_color || image[sr][sc] == color) {
            return;
        }
        image[sr][sc] = color;
        for(int i = 0; i < 4; i++) {
            int new_sr = sr + dx[i];
            int new_sc = sc + dy[i];
            helper(image, new_sr, new_sc, initial_color, color);
        }
    }
}



Time Complexity: O(N), where N is the number of pixels in the image. We might process every pixel. 

Space Complexity: O(N), the size of the implicit call stack when calling dfs.
```

