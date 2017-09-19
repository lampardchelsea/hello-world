/**
 * Refer to
 * http://www.lintcode.com/en/problem/maximal-rectangle/
 * Given a 2D boolean matrix filled with False and True, find the largest rectangle 
   containing all True and return its area.

    Have you met this question in a real interview? Yes
    Example
    Given a matrix:

    [
      [1, 1, 0, 0, 1],
      [0, 1, 0, 0, 1],
      [0, 0, 1, 1, 1],
      [0, 0, 1, 1, 1],
      [0, 0, 0, 0, 1]
    ]
    return 6.
 *
 * Solution
 * https://discuss.leetcode.com/topic/1634/a-o-n-2-solution-based-on-largest-rectangle-in-histogram/22
 * https://discuss.leetcode.com/topic/1634/a-o-n-2-solution-based-on-largest-rectangle-in-histogram/27
*/

// Solution 1: With fake last column
// Refer to
// https://discuss.leetcode.com/topic/1634/a-o-n-2-solution-based-on-largest-rectangle-in-histogram/22
// https://github.com/lampardchelsea/hello-world/edit/master/lintcode/DataStructure/VideoExamples/Stack/LargestRectanguleInHistogram.java (Solution 3)
// Style 1: Keep original length array
public class Solution {
    /*
     * @param matrix: a boolean 2D matrix
     * @return: an integer
     */
    public int maximalRectangle(boolean[][] matrix) {
        if(matrix == null || matrix.length == 0 || matrix[0].length == 0) {
            return 0;
        }
        
        int rows = matrix.length;
        int cols = matrix[0].length;
        int[] heights = new int[cols];
        int max = 0;
        for(int i = 0; i < rows; i++) {
            Stack<Integer> stack = new Stack<Integer>();
            // Caution: Use 
            for(int j = 0; j <= heights.length; j++) {
                int curH;
                if(j == heights.length) {
                    curH = 0;
                } else {
                    if(matrix[i][j]) {
                        heights[j] += 1;
                    } else {
                        heights[j] = 0;
                    }
                    curH = heights[j];
                }
                while(!stack.isEmpty() && curH <= heights[stack.peek()]) {
                    int top = stack.pop();
                    int h = heights[top];
                    int w = stack.isEmpty() ? j : j - stack.peek() - 1;
                    max = Math.max(max, h * w);
                }
                stack.push(j);
            }
        }
        return max;
    }
}

// Style 2: Increase 1 more length on heights array
public class Solution {
    /*
     * @param matrix: a boolean 2D matrix
     * @return: an integer
     */
    public int maximalRectangle(boolean[][] matrix) {
        if(matrix == null || matrix.length == 0 || matrix[0].length == 0) {
            return 0;
        }
        
        int rows = matrix.length;
        int cols = matrix[0].length;
        int[] heights = new int[cols + 1];
        int max = 0;
        for(int i = 0; i < rows; i++) {
            Stack<Integer> stack = new Stack<Integer>();
            for(int j = 0; j < heights.length; j++) {
                if(j < cols) {
                    if(matrix[i][j]) {
                        heights[j] += 1;
                    } else {
                        heights[j] = 0;
                    }                    
                }
                while(!stack.isEmpty() && heights[j] <= heights[stack.peek()]) {
                    int top = stack.pop();
                    int h = heights[top];
                    int w = stack.isEmpty() ? j : j - stack.peek() - 1;
                    max = Math.max(max, h * w);
                }
                stack.push(j);
            }
        }
        return max;
    }
}



// Solution 2: Without fake column
// Refer to
// https://discuss.leetcode.com/topic/1634/a-o-n-2-solution-based-on-largest-rectangle-in-histogram/27
// This is the unsimplified approach, notice how we had to process the elements in the stack twice. 
// Shichaotan reused the same segment of code by making the row array 1 element larger than the actual 
// size. And inside the forloop, he made it so that you modify the height array only if (j < n-1) so 
// you don't get an out of bound error. Brilliant!
public class Solution {
    /*
     * @param matrix: a boolean 2D matrix
     * @return: an integer
     */
    public int maximalRectangle(boolean[][] matrix) {
        if(matrix.length == 0) return 0;
        int[] height = new int[matrix[0].length];
        int maxArea = 0;
        Stack<Integer> stack = new Stack<Integer>();
        for(int i = 0; i < matrix.length; i++) {
            for(int j = 0; j < matrix[0].length; j++) {
                if(matrix[i][j]) height[j]++;
                else height[j] = 0;

                while(!stack.isEmpty() && height[j] <= height[stack.peek()]) {
                    int pop = stack.pop();
                    int width = stack.isEmpty() ? j : j - 1 - stack.peek();
                    maxArea = Math.max(maxArea, height[pop] * width);
                }

                stack.push(j);
            }
            while(!stack.isEmpty()) {
                int pop = stack.pop();
                int width = stack.isEmpty() ? height.length : height.length - 1 - stack.peek();
                maxArea = Math.max(maxArea, height[pop] *width);
            } 
        }
        return maxArea;
    }	
}




