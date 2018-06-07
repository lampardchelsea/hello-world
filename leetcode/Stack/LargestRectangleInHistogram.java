import java.util.Stack;

/**
 * Refer to
 * http://www.lintcode.com/en/problem/largest-rectangle-in-histogram/
 * https://leetcode.com/problems/largest-rectangle-in-histogram/description/
 * Given n non-negative integers representing the histogram's bar height where 
 * the width of each bar is 1, find the area of largest rectangle in the histogram.
 * Above is a histogram where width of each bar is 1, given height = [2,1,5,6,2,3].
 * The largest rectangle is shown in the shaded area, which has area = 10 unit.

	For example,
	Given heights = [2,1,5,6,2,3],
	return 10.

 * 
 * Solution
 * https://gist.github.com/pengqianhu/2371fdb2972844ac40ff
 * 
 * http://www.jiuzhang.com/solutions/largest-rectangle-in-histogram/
 *
 * For detail explain (Without fake column)
 * http://www.geeksforgeeks.org/largest-rectangle-under-histogram/
 */
public class LargestRectangleInHistogram {
    // Solution 1: Brute Force
	// Refer to
	// https://gist.github.com/pengqianhu/2371fdb2972844ac40ff
	// O(n^2)
	// TLE (when given input too long)
	public int largestRectangleArea(int[] heights) {
        if(heights == null || heights.length == 0) {
            return 0;
        }
        int maxArea = 0;
        /**
         * Caution: This is wrong way ->
         * Input:[2,1,5,6,2,3]
         * Output:12
         * Expected:10
         * 
	        for(int i = 0; i < heights.length; i++) {
	            for(int j = i; j < heights.length; j++) {
	                int height = Math.min(heights[i], heights[j]);
	                int curMax = height * (j - i + 1);
	                max = Math.max(max, curMax);
	            }
	        }
         */
        for(int i = 0; i < heights.length; i++) {
            int height = heights[i];
            maxArea = Math.max(height * 1, maxArea);   // --> This is a tricky way: Explain later
            for(int j = i; j < heights.length; j++) {
                height = Math.min(height, heights[j]);
                maxArea = Math.max(maxArea, height * (j - i + 1));
            }
        }
        return maxArea;
    }
	
    /**
      Explain for tricky part
      Even this looks like a brute force, but ideally, this is not a good practise, since if write
      as wrong way showing above, we actually not get the real minimum height into each inner loop,
      for example, if given {2,1,5,6,2,3}, when i = 0, j = 2, the height[i] = 2, height[j] = 5, if
      write as wrong way, the maxArea = (2 - 0 + 1) * Math.min(2, 5) = 6, which definitely wrong result,
      as the actual minimum height should based on index = 1, which between i = 0 and j = 2, and
      because of height[1] = 1, so the right answer here is (2 - 0 + 1) * 1 = 6, which not reflect by
      Math.min(2, 5), we need to find as minimum height through index i to j, not only i and j.
      The brute force list here give a dummy maxArea to recording each height in heights array as
      external loop, then compare it with inner loop result, which approach to target to record minimum
      height.
      
      The formal style to find minimum height each round will take O(n ^ 3) time complexity as below
      and TLE
    */
	class Solution {
	    public int largestRectangleArea(int[] heights) {
		if(heights == null || heights.length < 1) {
		    return 0;
		}
		int maxArea = 0;
		for(int i = 0; i < heights.length; i++) {
		    for(int j = i; j < heights.length; j++) {
			// Find minimum height through i to j
			int minHeight = findMinHeight(i, j, heights);
			maxArea = Math.max(minHeight * (j - i + 1), maxArea);
		    }
		}
		return maxArea;
	    }

	    private int findMinHeight(int i, int j, int[] heights) {
		int minHeight = heights[i];
		// Don't forget k could equal to j
		for(int k = i; k <= j; k++) {
		    if(heights[k] < minHeight) {
			minHeight = heights[k];
		    }
		}
		return minHeight;
	    }
	}
	
	
	
	// Solution 2: Stack
	/**
	 * Refer to
	 * https://discuss.leetcode.com/topic/7599/o-n-stack-based-java-solution
	 * 
	 * Can someone explain why it is i - 1 - s.peek() instead of i - s.peek() ? What's the extra -1 for?
	 * https://discuss.leetcode.com/topic/7599/o-n-stack-based-java-solution/13
	 * index i and s.peek() are the right bound (exclusive) and left bound (exclusive) of the range 
	 * which we use to compute the width of the rectangle, so i - s.peek() -1 is the correct value. 
	 * i - s.peek() does not exclude the right bound.
	 * 
	 * Can someone tell why we need i--?
	 * https://discuss.leetcode.com/topic/7599/o-n-stack-based-java-solution/15
	 * when we in the else branch, means the h (height[i]) is smaller than height[s.peek()], what 
	 * we do is updating the maxArea, but the height[i] is still waiting to be put into the stack, 
	 * we do i-- to counteract the i++ statement in the for loop, so that we will get the same i 
	 * in the next time 
	 * 
	 * https://segmentfault.com/a/1190000003498304
	 * 复杂度
	 * 时间 O(N) 空间 O(N)
	 * 思路
	 * 遍历数组（直方图），如果后一个竖条高于或等于前一个竖条，则将其下标push进栈，如果后一个竖条（假设它的下标为i）较矮，说明可以开始计算前一个竖条(i-1)
	 * 及之前那块上升区域最大的长方形面积了，这时将栈顶的竖条的下标pop出来，计算该竖条的面积。然后再看pop过后栈顶竖条(i-2)和后一个竖条(i)的大小关系，
	 * 如果栈顶竖条(i-2)较矮，说明又构成了一个连续上升区域，则将后一个竖条(i)push进栈，否则继续计算栈顶竖条(i-2)的面积，这里要注意，因为(i-2)竖条
	 * 比(i-1)竖条要靠左，所以i-2竖条能构成的最大长方形的宽度可以达到2，宽度的计算方法是用后一个竖条的下标i，减去再pop一个元素后栈顶竖条的下标(i-3)，
	 * 再加上1。以此类推，如果一直pop到栈为空时，说明刚pop出来的竖条之前的所有竖条都比它自己高，不然不可能栈为空，那我们以左边全部的宽度作为长方形的宽度。
	 * 这里计算宽度时，要减去上一个竖条的位置，而不是减去当前竖条的位置，因为有可能上一个竖条和当前竖条之间已经有些竖条被pop掉了，但他们肯定是高于当前竖条的，
	 * 所以可以计算到宽度中。
	 * 
	 */
	public int largestRectangleArea2(int[] heights) {
		if(heights == null || heights.length == 0) {
            return 0;
        }
        int maxArea = 0;
        Stack<Integer> stack = new Stack<Integer>();
        /**
         e.g Input = [2,1,5,6,2,3]
         When i = 0, first push 0 onto stack(bar height = 2)
         When i = 1, because heights[0] = 2 > h(heights[1] = 1), we need to pop out i = 0
         and stack is empty now, we pick up rectangule width as i, i drop back to i - 1 = 0
         When i = 0 again, in next for loop, it increases to i = 1 again, and because stack
         is empty now, we push i = 1 onto stack
         When i = 2, because heights[1] = 2 <= h(heights[2] = 5), push 2 onto stack
         When i = 3, because heights[2] = 5 <= h(heights[3] = 6), push 3 onto stack
         When i = 4, because heights[3] = 6 > h(heights[4] = 2), we need to pop out i = 3,
         now stack.peek() = 2, the width should be calculated as (i - stack.peek() - 1) = 
         4 - 2 - 1 = 1 (our target is only indexed i = 3, width = 1, height = 6 bar),
         width = 1 is correct value to approach this target, we can recognize, stack.peek() = 2
         is the left bound (exclusive) of the range, and i = 4 is the right bound (exclusive)
         of the range when we use to compuate the width of the rectangle, so only i - s.peek()
         = 4 - 2 = 2 not exclude the right bound, we must minus 1 more to exclude it.
         now i drop back to i - 1 = 3
         When i = 3 again, in next for loop, it increases to i = 4 again, but because stack
         is not empty and heights[stack.peek()] = 5 > h(heights[4] = 2), we need to continuously
         pop out i = 4, and i drop back to i - 1 = 3
         When i = 3 again, in next for loop, it increases to i = 4 again, and because 
         heights[stack.peek()] = 2 <= h(heights[3] = 6), push 4 onto stack
         ......
         */
        // Caution: include '=' for fake column
        for(int i = 0; i <= heights.length; i++) {
            // Corner case as when i == heights.length, we need a dummy value present
            // its height can be any non-positive value, either 0, -1 or Integer.MIN_VALUE...
            int h = (i == heights.length ? -1 : heights[i]);
            // If current stack empty or peek's value indexed bar height <= candidate heights[i],
            // we push candidate's index i onto stack
            if(stack.isEmpty() || heights[stack.peek()] <= h) {
                stack.push(i);
            } else {
                int top = stack.pop();
                // If stack is empty means width i can all used for construct rectangle,
                // also for (i - stack.peek() - 1), the additional '-1' because index i and s.peek() 
                // are the right bound (exclusive) and left bound (exclusive) of the range 
                // which we use to compute the width of the rectangle, so i - s.peek() -1 is the 
                // correct value. i - s.peek() does not exclude the right bound.
                maxArea = Math.max(maxArea, heights[top] * (stack.isEmpty() ? i : i - stack.peek() - 1));
                // The heights[i] candidate has not been pushed onto stack because its value
                // still smaller than current stack peek's value indexed bar height(heights[stack.peek()]), 
                // since it relate to continuously compare and go into else branch, i keep on increase
                // in new loop, but we need to hold on candidate heights[i]'s original value 
                // to compare with new value as heights[stack.peek()] in possible continuous comparison,
                // so each time we drop into else branch need to revert i back to keep original
                // candidate heights[i] value not changed for next loop
                i--;
            }
        }
        return maxArea;
	}
	
	// Solution 3: Introduce fake column at the end of height array
	// Template from JiuZhang --> Seems more clean and no i-- tricky part
	public int largestRectangleArea3(int[] height) {
        if (height == null || height.length == 0) {
            return 0;
        }
        Stack<Integer> stack = new Stack<Integer>();
        int max = 0;
        for (int i = 0; i <= height.length; i++) {
	    // Handle fake column case, when it is fake column, set height to random non-positive 
	    // value (e.g here set as -1 or any other value in range [0, Integer.MIN_VALUE]),
	    // because just need to use this column index as target rectangle right bound mark
            int curt = (i == height.length) ? -1 : height[i];
            while (!stack.isEmpty() && curt <= height[stack.peek()]) {
                int h = height[stack.pop()];
                int w = stack.isEmpty() ? i : i - stack.peek() - 1;
                max = Math.max(max, h * w);
            }
            stack.push(i);
        }
        
        return max;
    }
	
	// Solution 4: Without fake column
	// Refer to
	// http://www.geeksforgeeks.org/largest-rectangle-under-histogram/
	
	
	public static void main(String[] args) {
		LargestRectangleInHistogram l = new LargestRectangleInHistogram();
		int[] heights = {2,1,5,6,2,3};
		int result = l.largestRectangleArea3(heights);
		System.out.print(result);
	}
}

