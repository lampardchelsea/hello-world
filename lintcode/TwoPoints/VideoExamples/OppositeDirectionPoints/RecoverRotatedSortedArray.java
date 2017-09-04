/**
 * Refer to
 * http://www.lintcode.com/en/problem/recover-rotated-sorted-array/
 * Given a rotated sorted array, recover it to sorted array in-place.
    Have you met this question in a real interview? Yes
    Clarification
    What is rotated array?

    For example, the orginal array is [1,2,3,4], The rotated array of it can be [1,2,3,4], [2,3,4,1], [3,4,1,2], [4,1,2,3]
    Example
    [4, 5, 1, 2, 3] -> [1, 2, 3, 4, 5]
 *  In-place, O(1) extra space and O(n) time.
 * 
 * Solution
 * http://www.jiuzhang.com/solutions/recover-rotated-sorted-array/
 * http://ryanleetcode.blogspot.com/2015/04/recover-rotated-sorted-array.html
 * Challenge
 * In-place, O(1) extra space and O(n) time.
 * 『三步翻转法』，以[4, 5, 1, 2, 3]为例。
    首先找到分割点5和1
    翻转前半部分4, 5为5, 4，后半部分1, 2, 3翻转为3, 2, 1。整个数组目前变为[5, 4, 3, 2, 1]
    最后整体翻转即可得[1, 2, 3, 4, 5]
    由以上3个步骤可知其核心为『翻转』的in-place实现。使用两个指针，一个指头，一个指尾，使用for循环移位交换即可。
    注意：arraylist 里面存取数值要用 arraylist.get()/.set() 
*/
public class Solution {
    /*
     * @param nums: An integer
     * @return: 
     */
    public void recoverRotatedSortedArray(List<Integer> nums) {
        if(nums == null || nums.size() == 0) {
            return;
        }
        int k = 0;
        for(int i = 1; i < nums.size(); i++) {
            if(nums.get(i) - nums.get(i - 1) < 0) {
                k = i;
                break;
            }
        }
        reverse(nums, 0, k - 1);
        reverse(nums, k, nums.size() - 1);
        reverse(nums, 0, nums.size() - 1);
    }
    
    private void reverse(List<Integer> nums, int start, int end) {
        while(start < end) {
            int temp = nums.get(start);
            nums.set(start, nums.get(end));
            nums.set(end, temp);
            start++;
            end--;
        }
    }
}
