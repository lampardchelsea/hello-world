/**
 * Refer to
 * https://yeqiuquan.blogspot.com/2017/03/lintcode-443-two-sum-greater-than-target.html
 * Description
   Given an array of integers, find how many pairs in the array such that their sum is 
   bigger than a specific target number. Please return the number of pairs.
    Example
    Given numbers = [2, 7, 11, 15], target = 24. Return 1. (11 + 15 is the only pair)

    Challenge
    Do it in O(1) extra space and O(nlogn) time.
 *
 * Solution
 * https://yeqiuquan.blogspot.com/2017/03/lintcode-443-two-sum-greater-than-target.html
 * 思路
    我们把数组先排个序。把这个数组变成一个升序的序列。
    然后用两个指针从数组的头和尾往当中走。
    看例子：
    排序以后的数组是[1, 3, 5, 7, 9]。我们的target是8。
    我们当前的指针是这样的[1, 3, 5, 7, 9]。这两个数相加大于target了。说明什么？要和9相加大于target，
    1已经可以了，那么1右边所有的数加上9都一定会大于target。这样的数有几个呢？有j - i个。
    和9配对的我们都算过了，那么我们把j往左移动一格开始看[1, 3, 5, 7, 9]。看所有和7相加大于target的数有几个。
    一看这个1不行，那么试试看1右边的数字(i++)。一看可以[1, 3, 5, 7, 9]。那么和7相加大于target的数字，也有j - i个。
    两个指针一个只能往左，一个只能往右，一直到相会。这时我们得到所有满足条件的解的个数。

    时间复杂度O(nlogn)。
    空间复杂度O(1)。
*/
public class Solution {
    /**
     * @param nums an array of integer
     * @param target an integer
     * @return an integer
     */
    public int twoSum6(int[] nums, int target) {
        if(nums == null || nums.length == 0) {
            return 0;
        }
        Arrays.sort(nums);
        int start = 0;
        int end = nums.length - 1;
        int count = 0;
        while(start < end) {
            if(nums[start] + nums[end] > target) {
                count += end - start;
                start++;
            } else {
                end--;
            }
        }
        return count;
    }
}
