/**
 * Refer to
 * https://yeqiuquan.blogspot.com/2017/04/lintcode-609-two-sum-less-than-or-equal.html
 * Description
   Given an array of integers, find how many pairs in the array such that their sum is less than
   or equal to a specific target number. Please return the number of pairs.

    Example
    Given nums = [2, 7, 11, 15], target = 24.
    Return 5.
    2 + 7 < 24
    2 + 11 < 24
    2 + 15 < 24
    7 + 11 < 24
    7 + 15 < 25
 * 
 * Solution
 * https://yeqiuquan.blogspot.com/2017/04/lintcode-609-two-sum-less-than-or-equal.html
 * 先排序。
   左右两个指针，一个往右走，一个往左走。
   假设我们发现nums[i] + nums[j] <= target，由于这个数组是排序的，所以nums[i] + i 到 j之间的任何数，
   一定也是小于等于target的。那么我们就不用重复计算了。

*/
public class Solution {
    /**
     * @param nums an array of integer
     * @param target an integer
     * @return an integer
     */
    public int twoSum5(int[] nums, int target) {
        // Write your code here
        
        if (nums == null || nums.length < 2) {
            return 0;
        }
        
        Arrays.sort(nums);
        
        int left = 0;
        int right = nums.length - 1;
        int count = 0;
        
        while (left < right) {
            if (nums[left] + nums[right] <= target) {
                count += right - left;
                left++;
            }
            else {
                right--;
            }
        }
        
        return count;
    }
}
