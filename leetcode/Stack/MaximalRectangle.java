
import java.util.Stack;

/**
 * Refer to
 * https://leetcode.com/problems/maximal-rectangle/description/
 * http://www.lintcode.com/en/problem/maximal-rectangle/
 * Given a 2D binary matrix filled with 0's and 1's, find the largest rectangle 
 * containing only 1's and return its area.

	For example, given the following matrix:
	
	1 0 1 0 0
	1 0 1 1 1
	1 1 1 1 1
	1 0 0 1 0
	Return 6.
 *  
 * 
 * Solution
 * https://discuss.leetcode.com/topic/1634/a-o-n-2-solution-based-on-largest-rectangle-in-histogram/23?page=2 
 * https://siddontang.gitbooks.io/leetcode-solution/content/array/maximal_rectangle.html
 * 这题是一道难度很大的题目，至少我刚开始的时候完全不知道怎么做，也是google了才知道的。

	这题要求在一个矩阵里面求出全部包含1的最大矩形面积，譬如这个：
	
	    0 0 0 0
	    1 1 1 1
	    1 1 1 0
	    0 1 0 0
	我们可以知道，最大的矩形面积为6。也就是下图中虚线包围的区域。那么我们如何得到这个区域呢？
	
	    0  0  0  0
	   |--------|
	   |1  1  1 |1
	   |1  1  1 |0
	   |--------|
	    0  1  0  0
	对于上面哪一题，我们先去掉最下面的一行，然后就可以发现，它可以转化成一个直方图，数据为[2, 2, 2, 0]，我们认为1就是高度，
	如果碰到0，譬如上面最右列，则高度为0，而计算这个直方图最大矩形面积就很容易了，
	我们已经在Largest Rectangle in Histogram处理了。
	
	所以我们可以首先得到每一行的直方图，分别求出改直方图的最大区域，最后就能得到结果了。 
	
	Refer to
	https://github.com/lampardchelsea/hello-world/blob/master/lintcode/DataStructure/VideoExamples/Stack/LargestRectanguleInHistogram.java
	Solution 3 is suitable
 *
 * Note: Handle fake column
 * https://discuss.leetcode.com/topic/1634/a-o-n-2-solution-based-on-largest-rectangle-in-histogram/22
 * https://discuss.leetcode.com/topic/1634/a-o-n-2-solution-based-on-largest-rectangle-in-histogram/27
 */
public class MaximalRectangle {
    // Solution 1: Fake column introduced
    // Style 1: Increase a fake column
    public int maximalRectangle(char[][] matrix) {
        if(matrix == null || matrix.length == 0 || matrix[0].length == 0) {
            return 0;
        }
        int rows = matrix.length;
        int cols = matrix[0].length;
	// Increase a fake column
	// Why add fake column and how to handle it?
        // Handle fake column case, when it is fake column, set height to random non-positive 
	// value (e.g here set as -1 or any other value in range [0, Integer.MIN_VALUE]),
	// because just need to use this column index as target rectangle right bound mark
        int[] heights = new int[cols + 1];
        // Set up global variable 'max' to record final maximum rectangle after compare each row
        int max = 0;
        for(int i = 0; i < rows; i++) {
        	// Set up stack to find maximum rectangle in current row
            Stack<Integer> stack = new Stack<Integer>();
            for(int j = 0; j < heights.length; j++) {
            	// Set up the height for each column, if contains '0', height drop back to 0
                if(j < cols) {
                    if(matrix[i][j] == '1') {
                        heights[j] += 1;
                    } else {
                        heights[j] = 0;
                    }                    
                }
                // Largest Rectangle In Histogram section
                while(!stack.isEmpty() && heights[j] <= heights[stack.peek()]) {
                    int h = heights[stack.pop()];
                    int w = stack.isEmpty() ? j : j - stack.peek() - 1;
                    max = Math.max(max, h * w);
                }
                stack.push(j);
            }
        }
        return max;
    }
	
    // Style 2: Not increase array size but separate handling its case	
    public int maximalRectangle(char[][] matrix) {
        if(matrix == null || matrix.length == 0 || matrix[0].length == 0) {
            return 0;
        }
        int rows = matrix.length;
        int cols = matrix[0].length;
        int[] heights = new int[cols];
        int max = 0;
        for(int i = 0; i < rows; i++) {
            Stack<Integer> stack = new Stack<Integer>();
            for(int j = 0; j <= heights.length; j++) {
                int curH;
                if(j == heights.length) {
                    curH = Integer.MIN_VALUE;
                } else {
                   if(matrix[i][j] == '1') {
                        heights[j] += 1;
                    } else {
                        heights[j] = 0;
                    }
                    curH = heights[j];
                }
                while(!stack.isEmpty() && curH <= heights[stack.peek()]) {
                    int h = heights[stack.pop()];
                    int w = stack.isEmpty() ? j : j - stack.peek() - 1;
                    max = Math.max(max, h * w);
                }
                stack.push(j);
            }
        }
        return max;
    }
    
    // Solution 2: Not introduce fake column
    // Refer to
    // https://discuss.leetcode.com/topic/1634/a-o-n-2-solution-based-on-largest-rectangle-in-histogram/27
    // This is the unsimplified approach, notice how we had to process the elements in the stack twice. 
    // Shichaotan reused the same segment of code by making the row array 1 element larger than the actual 
    // size. And inside the forloop, he made it so that you modify the height array only if (j < n-1) so 
    // you don't get an out of bound error. Brilliant!
    public int maximalRectangle(char[][] matrix) {
        if(matrix.length == 0) return 0;
        int[] height = new int[matrix[0].length];
        int maxArea = 0;
        Stack<Integer> stack = new Stack<Integer>();
        for(int i = 0; i < matrix.length; i++) {
            for(int j = 0; j < matrix[0].length; j++) {
                if(matrix[i][j] == '1') height[j]++;
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
