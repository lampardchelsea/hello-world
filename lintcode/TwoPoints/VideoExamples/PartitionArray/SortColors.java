/**
 * Refer to
 * http://www.lintcode.com/en/problem/sort-colors/
 * Given an array with n objects colored red, white or blue, sort them so that objects 
   of the same color are adjacent, with the colors in the order red, white and blue.

    Here, we will use the integers 0, 1, and 2 to represent the color red, white, and blue respectively.

     Notice

    You are not suppose to use the library's sort function for this problem. 
    You should do it in-place (sort numbers in the original array).

    Have you met this question in a real interview? Yes
    Example
    Given [1, 0, 1, 2], sort it in-place to [0, 1, 1, 2].
 *
 * Solution
 * http://www.jiuzhang.com/solutions/sort-colors/
 * https://github.com/lampardchelsea/hello-world/blob/a807022744ebfd4cfa59df8f7b99f9098bf20e8e/leetcode/array/SortColors.java
*/
class Solution {
    /**
     * @param nums: A list of integer which is 0, 1 or 2 
     * @return: nothing
     */
    public void sortColors(int[] nums) {
        if(nums == null || nums.length <= 1) {
            return;
        }
        int left = 0;
        int right = nums.length - 1;
        int i = 0;
        while(i <= right) {
            if(nums[i] == 0) {
                swap(nums, i, left);
                left++;
                i++;
            } else if(nums[i] == 1) {
                i++;
            } else {
                // Think about why we not increase i at this time
                // Refer to
                // https://github.com/lampardchelsea/hello-world/blob/a807022744ebfd4cfa59df8f7b99f9098bf20e8e/leetcode/array/SortColors.java
                swap(nums, i, right);
                right--;
            }
        }
    }
    
    private void swap(int[] nums, int i, int j) {
        int temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
    }
}
