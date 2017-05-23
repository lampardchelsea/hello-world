/**
 * Refer to
 * https://leetcode.com/problems/jump-game/#/description 
 *  Given an array of non-negative integers, you are initially positioned at the first index of the array.
 *  Each element in the array represents your maximum jump length at that position.
 *  Determine if you are able to reach the last index.
 *  For example:
 *  A = [2,3,1,1,4], return true.
 *  A = [3,2,1,0,4], return false. 
 * 
 * Solution
 * https://segmentfault.com/a/1190000003488956
 * 贪心法
 * 复杂度
 * 时间 O(N) 空间 O(1)
 * 思路
 * 如果只是判断能否跳到终点，我们只要在遍历数组的过程中，更新每个点能跳到最远的范围就行了，如果最后这个范围大于等于终点，就是可以跳到。
*/
public class Solution {
    public boolean canJump(int[] nums) {
        int max = 0, i = 0;
        for(i = 0; i <= max && i < nums.length; i++){
            max = Math.max(max, nums[i] + i);
        }
        return i == nums.length;
    }
}
