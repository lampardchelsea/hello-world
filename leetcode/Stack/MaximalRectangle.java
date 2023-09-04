
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
 * With fake column
 * https://discuss.leetcode.com/topic/1634/a-o-n-2-solution-based-on-largest-rectangle-in-histogram/22
 * Without fake column
 * https://discuss.leetcode.com/topic/1634/a-o-n-2-solution-based-on-largest-rectangle-in-histogram/27
 * http://www.geeksforgeeks.org/largest-rectangle-under-histogram/
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
    // For detail explain:
    // http://www.geeksforgeeks.org/largest-rectangle-under-histogram/
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


















































































https://leetcode.com/problems/maximal-rectangle/description/

Given a rows x cols binary matrix filled with 0's and 1's, find the largest rectangle containing only 1's and return its area.

Example 1:


```
Input: matrix = [["1","0","1","0","0"],["1","0","1","1","1"],["1","1","1","1","1"],["1","0","0","1","0"]]
Output: 6
Explanation: The maximal rectangle is shown in the above picture.
```

Example 2:
```
Input: matrix = [["0"]]
Output: 0
```

Example 3:
```
Input: matrix = [["1"]]
Output: 1
```
 
Constraints:
- rows == matrix.length
- cols == matrix[i].length
- 1 <= row, cols <= 200
- matrix[i][j] is '0' or '1'.
---
Attempt 1: 2023-08-28

Solution 1:  DP (360 min, its not a standard DP, but derive recurrence formula on 2D array)
```
class Solution {
    public int maximalRectangle(char[][] matrix) {
        int maxArea = 0;
        int m = matrix.length;
        int n = matrix[0].length;
        int[] heights = new int[n];
        // firstLeftLessHeightBarPos[i] 代表左边第一个比当前柱子矮的下标 
        int[] firstLeftLessHeightBarPos = new int[n];
        // firstRightLessHeightBarPos[i] 代表右边第一个比当前柱子矮的下标 
        int[] firstRightLessHeightBarPos = new int[n];
        for(int i = 0; i < n; i++) {
            // 初始化为 -1，也就是最左边
            firstLeftLessHeightBarPos[i] = -1;
            // 初始化为 n，也就是最右边
            firstRightLessHeightBarPos[i] = n;
        }
        for(int i = 0; i < m; i++) {
            // 更新所有高度
            for(int j = 0; j < n; j++) {
                if(matrix[i][j] == '1') {
                    heights[j] += 1;
                } else {
                    heights[j] = 0;
                }
            }
            // 更新所有firstLeftLessHeightBarPos
            // 记录上次出现 0 的位置
            int boundary = -1;
            for(int j = 0; j < n; j++) {
                // 和上次出现 0 的位置比较
                if(matrix[i][j] == '1') {
                    firstLeftLessHeightBarPos[j] = Math.max(firstLeftLessHeightBarPos[j], boundary);
                } else {
                    // 当前是 0 代表当前柱子高度是 0，所以重新初始化为 -1，意味着左边第一
                    // 个比当前高度为 0 的柱子矮的柱子不存在，下标重置为初始值-1防止对下次
                    // 循环(外层循环进入到下一行并循环到第j列的时候)的影响，因为下次循环
                    // 依然使用的是同一个firstLeftLessHeightBarPos数组，只是要更新数值
                    firstLeftLessHeightBarPos[j] = -1;
                    // 更新 0 的位置
                    boundary = j;
                }
            }
            // 右边同理
            boundary = n;
            for(int j = n - 1; j >= 0; j--) {
                if(matrix[i][j] == '1') {
                    firstRightLessHeightBarPos[j] = Math.min(firstRightLessHeightBarPos[j], boundary);
                } else {
                    firstRightLessHeightBarPos[j] = n;
                    boundary = j;
                }
            }
            // 更新所有面积
            for(int j = 0; j < n; j++) {
                maxArea = Math.max(maxArea, (firstRightLessHeightBarPos[j] - firstLeftLessHeightBarPos[j] - 1) * heights[j]);
            }
        }
        return maxArea;
    }
}

Time Complexity : O(M*N) 
Space Complexity : O(N)
```

Refer to
https://leetcode.wang/leetCode-85-Maximal-Rectangle.html

解法四 动态规划

参考这里，这是 leetcode Solution 中投票最高的，但比较难理解，但如果结合 84 题去想的话就很容易了。

解法二中，用了 84 题的两个解法，解法三中我们把栈糅合进了原算法，那么另一种可以一样的思路吗？不行！因为栈不要求所有的高度，可以边更新，边处理。而另一种，是利用两个数组， leftLessMin [ ] 和 rightLessMin [ ]。而这两个数组的更新，是需要所有高度的。

解法二中，我们更新一次 heights，就利用之前的算法，求一遍 leftLessMin [ ] 和 rightLessMin [ ]，然后更新面积。而其实，我们求 leftLessMin [ ] 和 rightLessMin [ ] 可以利用之前的 leftLessMin [ ] 和 rightLessMin [ ] 来更新本次的。

我们回想一下 leftLessMin [ ] 和 rightLessMin [ ] 的含义， leftLessMin [ i ] 代表左边第一个比当前柱子矮的下标，如下图橙色柱子时当前遍历的柱子。rightLessMin [ ] 时右边第一个。


left 和 right 是对称关系，下边只考虑 left 的求法。

如下图，如果当前新增的层全部是 1，当然这时最完美的情况，那么 leftLessMin [ ] 根本不需要改变。


然而事实是残酷的，一定会有 0 的出现。


我们考虑最后一个柱子的更新。上一层的 leftLessMin = 1，也就是蓝色 0 的位置是第一个比它低的柱子。但是在当前层，由于中间出现了 0。所以不再是之前的 leftLessMin ，而是和上次出现 0 的位置进行比较（因为 0 一定比当前柱子小），谁的下标大，更接近当前柱子，就选择谁。上图中出现 0 的位置是 2，之前的 leftLessMin 是 1，选一个较大的，那就是 2 了。
public int maximalRectangle4(char[][] matrix) { 
```
    if (matrix.length == 0) {
        return 0;
    }
    int maxArea = 0;
    int cols = matrix[0].length;
    //leftLessMin[i] 代表左边第一个比当前柱子矮的下标 
    int[] leftLessMin = new int[cols];
    //rightLessMin[i] 代表右边第一个比当前柱子矮的下标 
    int[] rightLessMin = new int[cols];
    Arrays.fill(leftLessMin, -1); //初始化为 -1，也就是最左边
    Arrays.fill(rightLessMin, cols); //初始化为 cols，也就是最右边
    int[] heights = new int[cols];
    for (int row = 0; row < matrix.length; row++) {
        //更新所有高度
        for (int col = 0; col < cols; col++) {
            if (matrix[row][col] == '1') {
                heights[col] += 1;
            } else {
                heights[col] = 0;
            }
        }
        //更新所有leftLessMin
        int boundary = -1; //记录上次出现 0 的位置
        for (int col = 0; col < cols; col++) {
            if (matrix[row][col] == '1') {
                //和上次出现 0 的位置比较
                leftLessMin[col] = Math.max(leftLessMin[col], boundary);
            } else {
                //当前是 0 代表当前高度是 0，所以初始化为 -1，防止对下次循环的影响
                leftLessMin[col] = -1; 
                //更新 0 的位置
                boundary = col;
            }
        }
        //右边同理
        boundary = cols;
        for (int col = cols - 1; col >= 0; col--) {
            if (matrix[row][col] == '1') {
                rightLessMin[col] = Math.min(rightLessMin[col], boundary);
            } else {
                rightLessMin[col] = cols;
                boundary = col;
            }
        }
        //更新所有面积
        for (int col = cols - 1; col >= 0; col--) {
            int area = (rightLessMin[col] - leftLessMin[col] - 1) * heights[col];
            maxArea = Math.max(area, maxArea);
        }
    }
    return maxArea;
}
```
时间复杂度：O（mn）。
空间复杂度：O（n）。

---
Solution 2:  Based on L84. Largest Rectangle in Histogram (120 min)
```
class Solution {
    public int maximalRectangle(char[][] matrix) {
        int maxArea = 0;
        int m = matrix.length;
        int n = matrix[0].length;
        int[] heights = new int[n];
        for(int i = 0; i < m; i++) {
            for(int j = 0; j < n; j++) {
                if(matrix[i][j] == '1') {
                    heights[j] += 1;
                } else {
                    heights[j] = 0;
                }
            }
            maxArea = Math.max(maxArea, largestRectangleArea(heights));
        }
        return maxArea;
    }
 
    private int largestRectangleArea(int[] heights) {
        Stack<Integer> stack = new Stack<Integer>();
        int maxArea = 0;
        for(int i = 0; i <= heights.length; i++) {
            int h = i < heights.length ? heights[i] : 0;
            if(stack.isEmpty() || h >= heights[stack.peek()]) {
                stack.push(i);
            } else {
                int height = heights[stack.pop()];
                int width = 0;
                if(stack.isEmpty()) {
                    width = i;
                } else {
                    width = i - 1 - stack.peek();
                }
                maxArea = Math.max(maxArea, height * width);
                i--;
            }
        }
        return maxArea;
    }
}

Time Complexity : O(M*N) 
Space Complexity : O(N)
```

Refer to
L84. Largest Rectangle in Histogram
```
class Solution { 
    public int largestRectangleArea(int[] heights) { 
        int maxArea = 0; 
        Stack<Integer> stack = new Stack<Integer>(); 
        // 'i <= heights.length' includes '==' condition   
        for(int i = 0; i <= heights.length; i++) { 
            // Corner case to add dummy height = 0 when i == heights.length 
            // which helps calculation of rectangle area close to right end 
            int h = (i == heights.length ? 0 : heights[i]); 
            if(stack.isEmpty() || h >= heights[stack.peek()]) { 
                stack.push(i); 
            } else { 
                int height = heights[stack.pop()]; 
                int width = 0; 
                if(stack.isEmpty()) { 
                    width = i; 
                } else { 
                    //int right_boundary = i - 1; 
                    //int left_boundary = stack.peek(); 
                    //width = right_boundary - left_boundary + 1; 
                    // Why left boundary is stack.peek() ? 
                    // Take {2,1,5,6,2,3} as example, when process the 2nd 2 
                    // which index = 4, originally we have stored index = {1,2,3}  
                    // on stack, corresponding to {1,5,6}, because 2 < 6, pop 
                    // peak index = 3, now we have {1,2} stored on stack, the 
                    // left boundary index is stack.peek() as 2, which represents 
                    // the rectangle height = 5, now why we say height as 5 rectangle 
                    // must be left boundary, because of maintaining an increasing 
                    // monotonic stack, if any rectangle before height as 5 rectangle 
                    // exist, its corresponding index would be popped out from the  
                    // stack already before we push current height as 5 rectangle's 
                    // index = 2 onto stack based on 'else branch logic', so the remain 
                    // height as 5 rectangle on index = 2 is definitely the "shortest" 
                    // rectangle still existing on stack, but also be the "highest"  
                    // rectangle pushing into stack till now, can be used as left boundary 
                    width = i - 1 - stack.peek(); 
                } 
                maxArea = Math.max(maxArea, height * width); 
                // Why we have to use i-- ? 
                // When we in the else branch, means the h (height[i]) is  
                // smaller than height[s.peek()], what we do is updating  
                // the maxArea, but the height[i] is still waiting to be  
                // put into the stack, we do i-- to counteract the i++  
                // statement in the for loop, so that we will get the same  
                // i in the next time 
                // Take {2,1,5,6,2,3} as example, when try tp push 1 onto stack, 
                // since 1 < 2 go into "else branch" but no push logic, only pop 
                // logic in "else branch", to push it we have to waiting till 
                // next iteration, to compensate auto i++, we preparatorily 
                // decrease as i-- in current iteration 
                i--; 
            } 
        } 
        return maxArea; 
    } 
}
```

Refer to
https://leetcode.wang/leetCode-85-Maximal-Rectangle.html

解法二

参考这里-solution-based-on-Largest-Rectangle-in-Histogram>)，接下来的解法，会让这道题变得异常简单。还记得 84 题吗？求一个直方图矩形的最大面积。


大家可以先做 84 题，然后回来考虑这道题。

再想一下这个题，看下边的橙色的部分，这完全就是上一道题呀！


算法有了，就是求出每一层的 heights[] 然后传给上一题的函数就可以了。

利用上一题的栈解法。
```
public int maximalRectangle(char[][] matrix) {
    if (matrix.length == 0) {
        return 0;
    }
    int[] heights = new int[matrix[0].length];
    int maxArea = 0;
    for (int row = 0; row < matrix.length; row++) {
        //遍历每一列，更新高度
        for (int col = 0; col < matrix[0].length; col++) {
            if (matrix[row][col] == '1') {
                heights[col] += 1;
            } else {
                heights[col] = 0;
            }
        }
        //调用上一题的解法，更新函数
        maxArea = Math.max(maxArea, largestRectangleArea(heights));
    }
    return maxArea;
}
 
public int largestRectangleArea(int[] heights) {
    int maxArea = 0;
    Stack<Integer> stack = new Stack<>();
    int p = 0;
    while (p < heights.length) {
        //栈空入栈
        if (stack.isEmpty()) {
            stack.push(p);
            p++;
        } else {
            int top = stack.peek();
            //当前高度大于栈顶，入栈
            if (heights[p] >= heights[top]) {
                stack.push(p);
                p++;
            } else {
                //保存栈顶高度
                int height = heights[stack.pop()];
                //左边第一个小于当前柱子的下标
                int leftLessMin = stack.isEmpty() ? -1 : stack.peek();
                //右边第一个小于当前柱子的下标
                int RightLessMin = p;
                //计算面积
                int area = (RightLessMin - leftLessMin - 1) * height;
                maxArea = Math.max(area, maxArea);
            }
        }
    }
    while (!stack.isEmpty()) {
        //保存栈顶高度
        int height = heights[stack.pop()];
        //左边第一个小于当前柱子的下标
        int leftLessMin = stack.isEmpty() ? -1 : stack.peek();
        //右边没有小于当前高度的柱子，所以赋值为数组的长度便于计算
        int RightLessMin = heights.length;
        int area = (RightLessMin - leftLessMin - 1) * height;
        maxArea = Math.max(area, maxArea);
    }
    return maxArea;
}
```
时间复杂度：O（mn）。
空间复杂度：O（n）。
