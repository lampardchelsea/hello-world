/**
 * Given a mountain sequence of n integers which increase firstly and then decrease, find the mountain top.
    Example:
    Given nums = [1, 2, 4, 8, 6, 3] return 8
    Given nums = [10, 9, 8, 7], return 10
 * 
 * Solution
 * http://blog.leanote.com/post/westcode/c0469ec79225
 * 思路
 * 这题跟之前做过的题（Find Peak）非常类似，同样是使用二分法。考的是二分指针的移动方式。这里的移动方式就是判断当前元素处于上坡或者下坡。
*/
public int mountainSequence(int[] nums) {
    if(nums == null || nums.length == 0) {
        return -1;
    }
    int start = 0;
    int end = nums.length - 1;
    while(start + 1 < end) {
        int mid = start + (end - start) / 2;
        // Up hill
        if(nums[mid] < nums[mid + 1]) {
            start = mid;
        // Down hill
        } else {
            end = mid;
        }
    }
    // Also need to change model here than template,
    // peak will only happen between one of 'start' and 'end'
    if(nums[start] > nums[end]) {
        return nums[start];
    } else {
        return nums[end];
    }
}
