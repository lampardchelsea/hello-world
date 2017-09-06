/**
 * Refer to
 * http://www.lintcode.com/en/problem/partition-array/
 * Given an array nums of integers and an int k, partition the array (i.e move the elements in "nums") such that:
  
    All elements < k are moved to the left
    All elements >= k are moved to the right
    Return the partitioning index, i.e the first index i nums[i] >= k.
    Notice
    You should do really partition in array nums instead of just counting the numbers of integers smaller than k.
    If all elements in nums are smaller than k, then return nums.length
    Have you met this question in a real interview? Yes
    Example
    If nums = [3,2,2,1] and k=2, a valid answer is 1.
 *
 *
 * Solution
 * https://www.jiuzhang.com/solutions/partition-array/
 * Similar template as QuickSort
 * https://www.jiuzhang.com/solutions/quick-sort/
 * http://www.code123.cc/docs/leetcode-notes/integer_array/partition_array.html
   源码分析
   大循环能正常进行的条件为 left<=right, 对于左边索引，向右搜索直到找到小于 k 的索引为止；
   对于右边索引，则向左搜索直到找到大于等于 k 的索引为止。注意在使用while循环时务必进行越界检查！
   找到不满足条件的索引时即交换其值，并递增left, 递减right. 紧接着进行下一次循环。最后返回left即可，
   当nums为空时包含在left = 0之中，不必单独特殊考虑，所以应返回left而不是right.
*/

public class Solution {
    /*
     * @param nums: The integer array you should partition
     * @param k: An integer
     * @return: The index after partition
     */
    public int partitionArray(int[] nums, int k) {
        if(nums == null || nums.length == 0) {
            return 0;
        }
        int left = 0;
        int right = nums.length - 1;
        // Every time you compare left & right, it should be 
        // left <= right not left < right
        while(left <= right) {
            while(left <= right && nums[left] < k) {
                left++;
            }
            // Be careful: nums[right] >= k not only >
            while(left <= right && nums[right] >= k) {
                right--;
            }
            if(left < right) {
                int temp = nums[left];
                nums[left] = nums[right];
                nums[right] = temp;
                left++;
                right--;
            }
        }
        return left;
    }
}




// This solution's while loop condition not same as template, better follow template way as 'left <= right'
public class Solution {
    /*
     * @param nums: The integer array you should partition
     * @param k: An integer
     * @return: The index after partition
     */
    public int partitionArray(int[] nums, int k) {
        if(nums == null || nums.length == 0) {
            return 0;
        }
        int left = 0;
        int right = nums.length - 1;
        while(left < right) {
            while(left < right && nums[left] < k) {
                left++;
            }
            while(left < right && nums[right] >= k) {
                right--;
            }
            if(left < right) {
                int temp = nums[left];
                nums[left] = nums[right];
                nums[right] = temp;
                left++;
                right--;
            }
        }
        // Refer to
        // http://www.cnblogs.com/EdwardLiu/p/4385823.html
        // 第二种做法（推荐）： 
        // 只要left, right都动过，left停的位置就是first index that nums[i] >= k, 一般情况
        // return left就好了
        // 单独讨论left或者right没有动过的情况，left没有动过的情况还是return left, right
        // 没有动过的情况return right + 1
        // E.g if still treat as normal as just return left, if right never moved, need
        // to return left + 1
        // Input
        // [7,7,9,8,6,6,8,7,9,8,6,6]
        // 10
        // Output
        // 11
        // Expected
        // 12
        if(nums[left] < k) {
            // 0 ... left
            return left + 1;
        }
        // 0 ... left - 1
        return left;
    }
}





