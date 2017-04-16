// Refer to
// https://leetcode.com/problems/remove-duplicates-from-sorted-array-ii/#/description
/**
 * Follow up for "Remove Duplicates":
 * What if duplicates are allowed at most twice?
 * For example,
 * Given sorted array nums = [1,1,1,2,2,3],
 * Your function should return length = 5, with the first five elements of nums being 
 * 1, 1, 2, 2 and 3. It doesn't matter what you leave beyond the new length.
*/
// Solution 1
// Refer to
// https://discuss.leetcode.com/topic/46519/short-and-simple-java-solution-easy-to-understand
/**
 * Question wants us to return the length of new array after removing duplicates and that 
 * we don't care about what we leave beyond new length , hence we can use i to keep track 
 * of the position and update the array. Taking full advantage of the sorted nature.
*/
public class Solution {
    public int removeDuplicates(int[] nums) {
        int i = 0;
        for(int n : nums) {
            if(i < 2 || n > nums[i - 2]) {
                nums[i++] = n;
            }
        }
        return i;
    }
}

// Solution 2
// Refer to
// https://segmentfault.com/a/1190000003752035
/**
 * 双指针法
 * 复杂度
 * 时间 O(N) 空间 O(1)
 * 思路
 * 思路和上题一样，区别在于记录前两个遍历到的数字来帮助我们判断是否出现了第三遍。
 * 如果当前数字和前一个数字的前一个一样的话，说明出现了第三次。
*/



