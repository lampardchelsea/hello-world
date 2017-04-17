// Refer to
// https://leetcode.com/problems/product-of-array-except-self/#/description
/**
 * Given an array of n integers where n > 1, nums, return an array output such that output[i] 
 * is equal to the product of all the elements of nums except nums[i].
 * Solve it without division and in O(n).
 * For example, given [1,2,3,4], return [24,12,8,6].
 * Follow up:
 * Could you solve it with constant space complexity? (Note: The output array does not count 
 * as extra space for the purpose of space complexity analysis.)
*/

// Solution
// Refer to
// https://segmentfault.com/a/1190000003768224
/**
 * 复杂度
 * 时间 O(N) 空间 O(1)
 * 思路
 * 实际上，我们可以用结果数组自身来存储left和right数组的信息。首先还是同样的算出每个点左边所有数的乘积，
 * 存入数组中。然而在反向算右边所有数的乘积时，我们不再把它单独存入一个数组，而是直接乘到之前的数组中，
 * 这样乘完后结果就已经出来了。另外，因为我们不再单独开辟一个数组来存储右边所有数，不能直接根据数组上一
 * 个来得知右边所有数乘积，所以我们需要额外一个变量来记录右边所有数的乘积。这里为了清晰对称，遍历左边的
 * 时候也加入了一个额外变量来记录。
 * 注意
 * 因为第一位在第一轮从左向右乘的时候乘不到，结果数组中会得到0，所以要先将第一位置为1，即res[0] = 1，其他的不用初始化
 * 因为涉及左右两边的数，所有数组长度为1的时候就直接返回自身就行了
*/
public class Solution {
    public int[] productExceptSelf(int[] nums) {
        int len = nums.length;
        if(len <= 1) {
            return nums;
        }
        int[] result = new int[len];
        result[0] = 1;
        int left = 1;
        int right = 1;
        // Computer every point's left multiple 
        for(int i = 1; i < len; i++) {
            left = left * nums[i - 1];
            result[i] = left;
        }
        // Computer every point's right multiple
        for(int i = len - 2; i >= 0; i--) {
            right = right * nums[i + 1];
            result[i] = result[i] * right;
        }
        return result;
    }
}
