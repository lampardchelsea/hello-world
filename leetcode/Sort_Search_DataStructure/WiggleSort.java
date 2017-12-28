/**
 * Refer to
 * http://www.cnblogs.com/grandyang/p/5177285.html
 * Given an unsorted array nums, reorder it in-place such that nums[0] <= nums[1] >= nums[2] <= nums[3]....
 * For example, given nums = [3, 5, 2, 1, 6, 4], one possible answer is [1, 6, 2, 5, 3, 4].
 *
 * Solution
 * http://www.cnblogs.com/grandyang/p/5177285.html
 * 
*/
public class Solution {
    // Solution 1: Time Complexity O(nlogn)
    // Refer to
    // https://discuss.leetcode.com/topic/33418/simple-java-solution-o-n-log-n
    // http://www.cnblogs.com/grandyang/p/5177285.html
    /**
      * 这道题让我们求摆动排序，跟Wiggle Sort II相比起来，这道题的条件宽松很多，只因为多了一个等号。由于等号的存在，
      * 当数组中有重复数字存在的情况时，也很容易满足题目的要求。这道题我们先来看一种时间复杂度为O(nlgn)的方法，
      * 思路是先给数组排个序，然后我们只要每次把第三个数和第二个数调换个位置，第五个数和第四个数调换个位置，
      * 以此类推直至数组末尾，这样我们就能完成摆动排序了
    */
    public void wiggleSort(int[] nums) {
        Arrays.sort(nums);
        for(int i = 0; i < nums.length - 1; i++) {
            if(i % 2 == 1) {
                int temp = nums[i];
                nums[i] = nums[i + 1];
                nums[i + 1] = temp;
            }
        }
    }
    
    // Solution 2: Time Complexity O(n)
    // Refer to
    // http://www.cnblogs.com/grandyang/p/5177285.html
    // https://discuss.leetcode.com/topic/23871/java-o-n-solution
    /**
     * 这道题还有一种O(n)的解法，根据题目要求的nums[0] <= nums[1] >= nums[2] <= nums[3]....，我们可以总结出如下规律：
     * 当i为奇数时，nums[i] >= nums[i - 1]
     * 当i为偶数时，nums[i] <= nums[i - 1]
     * 那么我们只要对每个数字，根据其奇偶性，跟其对应的条件比较，如果不符合就和前面的数交换位置即可
    */
    public void wiggleSort(int[] nums) {
        for(int i = 0; i < nums.length; i++) {
            if((i % 2 == 1 && nums[i] < nums[i - 1]) || (i % 2 == 0 && nums[i] > nums[i - 1])) {
                int temp = nums[i];
                nums[i] = nums[i - 1];
                nums[i - 1] = temp;
            }
        }
    }
}
