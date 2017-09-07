/**
 * Refer to
 * http://www.lintcode.com/en/problem/partition-array-by-odd-and-even/
 * Partition an integers array into odd number first and even number second.

    Have you met this question in a real interview? Yes
    Example
    Given [1, 2, 3, 4], return [1, 3, 2, 4]
 *
 * Solution
 * https://zhengyang2015.gitbooks.io/lintcode/partition_array_by_odd_and_even_373.html
 * 
*/
public class Solution {
    /*
     * @param nums: an array of integers
     * @return: nothing
     */
    public void partitionArray(int[] nums) {
        if(nums == null || nums.length == 0) {
            return;
        }
        int start = 0;
        int end = nums.length - 1;
        while(start < end) {
            // 前后指针。前指针从前往后遇到偶数停下，后指针从后往前遇到奇数停下，
            // 交换，继续，直到前后指针交叠。注意的地方是前指针从前往后走的时候
            // 要注意不要越界，后指针也一样。
            while(nums[start] % 2 != 0) {
                start++;
            }
            while(nums[end] % 2 == 0) {
                end--;
            }
            if(start < end) {
                int temp = nums[start];
                nums[start] = nums[end];
                nums[end] = temp;
            }
        }
    }
}
