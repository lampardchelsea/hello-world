/**
 * Refer to
 * https://leetcode.com/problems/container-with-most-water/#/description
 * Given n non-negative integers a1, a2, ..., an, where each represents a point at coordinate (i, ai). 
 * n vertical lines are drawn such that the two endpoints of line i is at (i, ai) and (i, 0). 
 * Find two lines, which together with x-axis forms a container, such that the container contains 
 * the most water.
 * Note: You may not slant the container and n is at least 2. 
 * 
 * Solution
 * https://discuss.leetcode.com/topic/25004/easy-concise-java-o-n-solution-with-proof-and-explanation
 * https://discuss.leetcode.com/topic/3462/yet-another-way-to-see-what-happens-in-the-o-n-algorithm
 * https://segmentfault.com/a/1190000003815582
 * 栈法
 * 复杂度
 * 时间 O(N) 空间 O(N)
 * 思路
 * 最大盛水量取决于两边中较短的那条边，而且如果将较短的边换为更短边的话，盛水量只会变少。所以我们可以用两个头尾指针，
 * 计算出当前最大的盛水量后，将较短的边向中间移，因为我们想看看能不能把较短的边换长一点。这样一直计算到左边大于右边为止就行了。
 * 注意
 * 如果将较短的边向中间移后，新的边还更短一些，其实可以跳过，减少一些计算量
 */
public class ContainerWithMostWater {
    public int maxArea(int[] height) {
        int left = 0, right = height.length - 1, maxArea = 0;
        while(left < right){
            // 每次更新最大面积（盛水量）
            maxArea = Math.max(maxArea, Math.min(height[left], height[right]) * (right - left));
            // 如果左边较低，则将左边向中间移一点
            if(height[left] < height[right]){
                left++;
            // 如果右边较低，则将右边向中间移一点
            } else {
                right--;
            }
        }
        return maxArea;
    }
    
    public static void main(String[] args) {
    	
    }
}

