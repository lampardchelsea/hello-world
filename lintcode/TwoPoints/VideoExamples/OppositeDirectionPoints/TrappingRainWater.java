
/**
 * Refer to
 * https://leetcode.com/problems/trapping-rain-water/description/
 * http://www.lintcode.com/en/problem/trapping-rain-water/
 * Given n non-negative integers representing an elevation map where the width 
 * of each bar is 1, compute how much water it is able to trap after raining.
 * 
	Trapping Rain Water
	Have you met this question in a real interview? Yes
	Example
	Given [0,1,0,2,1,0,1,3,2,1,2,1], return 6.
 * 
 * 
 * Solution
 * https://aaronice.gitbooks.io/lintcode/content/two_pointers/trapping_rain_water.html
 * 对于每一个bar来说，能装水的容量取决于左右两侧bar的最大值。扫描两次，一次从左向右，记录对于每一个bar来说其左侧的bar的最大
 * 高度left[i]，一次从右向左，记录每一个bar右侧bar的最大高度right[i]。第三次扫描，则对于每一个bar，计算（1）左侧最大
 * height和当前bar的height的差值（left[i] - heights[i]） （2）右侧最大height和当前bar的height的差值
 * （right[i] - heights[i]），取（1），（2）中结果小的那个作为当前bar的蓄水量。最终求和得到总蓄水量。
 * 
 * http://www.cnblogs.com/grandyang/p/4402392.html
 * 这道收集雨水的题跟之前的那道 Largest Rectangle in Histogram 直方图中最大的矩形 有些类似，但是又不太一样，
 * 我们先来看一种方法，这种方法是基于动态规划Dynamic Programming的，我们维护一个一维的dp数组，这个DP算法需要遍
 * 历两遍数组，第一遍遍历dp[i]中存入i位置左边的最大值，然后开始第二遍遍历数组，第二次遍历时找右边最大值，然后和左边最大
 * 值比较取其中的较小值，然后跟当前值A[i]相比，如果大于当前值，则将差值存入结果
 * 
 * 最后我们来看一种只需要遍历一次即可的解法，这个算法需要left和right两个指针分别指向数组的首尾位置，从两边向中间扫描，
 * 在当前两指针确定的范围内，先比较两头找出较小值，如果较小值是left指向的值，则从左向右扫描，如果较小值是right指向的值，
 * 则从右向左扫描，若遇到的值比当较小值小，则将差值存入结果，如遇到的值大，则重新确定新的窗口范围，以此类推直至left
 * 和right指针重合
 * 
 * https://leetcode.com/articles/trapping-rain-water/
 */
public class TrappingRainWater {
	// Solution 1: Brute Force
	/**
	 * Intuition
	 * Do as directed in question. For each element in the array, we find the maximum level of water 
	 * it can trap after the rain, which is equal to the minimum of maximum height of bars on both 
	 * the sides minus its own height.

		Algorithm
		Initialize ans=0
		Iterate the array from left to right:
		Initialize max_left=0 and max_right=0
		Iterate from the current element to the beginning of array updating: max_left=max(max_left,height[j])
		Iterate from the current element to the end of array updating: max_right=max(max_right,height[j])
		Add min(max_left,max_right)−height[i] to ans
		
		Complexity Analysis
		Time complexity: O(n^2) For each element of array, we iterate the left and right parts.
		Space complexity: O(1) extra space.
	 *  
	 */
	public int trap(int[] height) {
        if(height == null || height.length == 0) {
            return 0;
        }
        int size = height.length;
        int sum = 0;
        for(int i = 0; i < size; i++) {
            int max_left = 0;
            int max_right = 0;
            for(int j = i; j >= 0; j--) {
                max_left = Math.max(max_left, height[j]);
            }
            for(int j = i; j < size; j++) {
                max_right = Math.max(max_right, height[j]);
            }
            sum += Math.min(max_left, max_right) - height[i];
        }
        return sum;
    }
	
	// Solution 2: DP
	// Refer to
	// https://aaronice.gitbooks.io/lintcode/content/two_pointers/trapping_rain_water.html
	/**
	 * Intuition
	 * In brute force, we iterate over the left and right parts again and again just to 
	 * find the highest bar size upto that index. But, this could be stored. Voila, 
	 * dynamic programming
	 * 
	 * Algorithm
		Find maximum height of bar from the left end upto an index i in the array left_max.
		Find maximum height of bar from the right end upto an index i in the array right_max.
		Iterate over the height array and update ans:
		Add min(max_left[i],max_right[i])−height[i] to ans
		
		Complexity analysis
		Time complexity: O(n).
		We store the maximum heights upto a point using 2 iterations of O(n) each.
		We finally update ans using the stored values in O(n).
		Space complexity: O(n) extra space.
		Additional O(n) space for left_max and right_max arrays than in Approach #1.
	 * 
	 */
    public int trap2(int[] height) {
        if(height == null || height.length == 0) {
            return 0;
        }
        int size = height.length;
        int sum = 0;
        int[] max_left = new int[size];
        int[] max_right = new int[size];
        max_left[0] = 0;
        max_right[size - 1] = height[size - 1];
        // Keep track of the max height on the left of height[i]
        for(int i = 1; i < size; i++) {
            max_left[i] = Math.max(max_left[i - 1], height[i - 1]);
        }
        // Keep track of the max height on the right of height[i]
        for(int i = size - 2; i >= 0; i--) {
            max_right[i] = Math.max(max_right[i + 1], height[i + 1]);
        }
        for(int i = 0; i < size; i++) {
        	// Caution: Need to check > 0
        	// height = {2,0,2}
        	// max_left = {0,2,2}
        	// max_right = {2,2,2}
        	// if i = 0, Math.min(max_right[i], max_left[i]) - height[i] = -2,
        	// which is not area size
        	if(Math.min(max_right[i], max_left[i]) - height[i] > 0) {
                sum += Math.min(max_right[i], max_left[i]) - height[i];        		
        	}
        }
        return sum;
    }
    
    // Solution 3: Two Points
    /**
     * Refer to
     * http://www.cnblogs.com/grandyang/p/4402392.html
	 * 最后我们来看一种只需要遍历一次即可的解法，这个算法需要left和right两个指针分别指向数组的首尾位置，从两边向中间扫描，
	 * 在当前两指针确定的范围内，先比较两头找出较小值，如果较小值是left指向的值，则从左向右扫描，如果较小值是right指向的值，
	 * 则从右向左扫描，若遇到的值比当较小值小，则将差值存入结果，如遇到的值大，则重新确定新的窗口范围，以此类推直至left
	 * 和right指针重合
	 * 
	 * Refer to
	 * https://leetcode.com/articles/trapping-rain-water/
	 * Intuition As in Approach #2, instead of computing the left and right parts separately, 
	 * we may think of some way to do it in one iteration. From the figure in dynamic 
	 * programming approach, notice that as long as right_max[i]>left_max[i](from element 0 to 6), 
	 * the water trapped depends upon the left_max, and similar is the case when left_max[i]>right_max[i]
	 * (from element 8 to 11). So, we can say that if there is a larger bar at one end(say right), 
	 * we are assured that the water trapped would be dependent on height of bar in current 
	 * direction(from left to right). As soon as we find the bar at other end(right) is smaller, 
	 * we start iterating in opposite direction(from right to left). We must maintain left_max 
	 * and right_max during the iteration, but now we can do it in one iteration using 2 
	 * pointers, switching between the two.

		Algorithm
		Initialize left pointer to 0 and right pointer to size-1
		While left<right, do:
		If height[left] is smaller than height[right]
		If height[left]>=left_max, update left_max
		Else add left_max−height[left] to ans
		Add 1 to left.
		Else
		If height[right]>=right_max, update right_max
		Else add right_max−height[right] to ans
		Subtract 1 from right.
		
		Complexity analysis
		Time complexity: O(n). Single iteration of O(n).
		Space complexity: O(1) extra space. Only constant space required for left, right, left_max and right_max.
		int trap(vector<int>& height)
		{
		    int left = 0, right = height.size() - 1;
		    int ans = 0;
		    int left_max = 0, right_max = 0;
		    while (left < right) {
		        if (height[left] < height[right]) {
		            height[left] >= left_max ? (left_max = height[left]) : ans += (left_max - height[left]);
		            ++left;
		        }
		        else {
		            height[right] >= right_max ? (right_max = height[right]) : ans += (right_max - height[right]);
		            --right;
		        }
		    }
		    return ans;
		}
     */
    public int trap3(int[] height) {
        if(height == null || height.length == 0) {
            return 0;
        }
        int size = height.length;
        int sum = 0;
        int left = 0;
        int right = size - 1;
        int left_max = height[left];
        int right_max = height[right];
        while(left < right) {
            if(left_max < right_max) {
                left++;
                if(left_max > height[left]) {
                    sum += left_max - height[left];
                } else {
                    left_max = height[left];
                }
            } else {
                right--;
                if(right_max > height[right]) {
                    sum += right_max - height[right];
                } else {
                    right_max = height[right];
                }
            }
        }
        return sum;
    }
    
    public static void main(String[] args) {
    	TrappingRainWater t = new TrappingRainWater();
    	//int[] height = {0,1,0,2,1,0,1,3,2,1,2,1};
    	int[] height = {2,0,2};
    	int result = t.trap2(height);
    	System.out.print(result);
    }

}
