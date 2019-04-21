/**
 Refer to
 https://leetcode.com/problems/non-decreasing-array/
 Given an array with n integers, your task is to check if it could become 
 non-decreasing by modifying at most 1 element.

We define an array is non-decreasing if array[i] <= array[i + 1] holds for every i (1 <= i < n).

Example 1:
Input: [4,2,3]
Output: True
Explanation: You could modify the first 4 to 1 to get a non-decreasing array.

Example 2:
Input: [4,2,1]
Output: False
Explanation: You can't get a non-decreasing array by modify at most one element.

Note: The n belongs to [1, 10,000].
*/
// Wrong solution, still wrong with [2,3,3,2,4]
// Expected true, but return false, if we swap 3 at index 1 and 2 at index 3 will make it as non-decreasing array
class Solution {
    public boolean checkPossibility(int[] nums) {
        int count = 0;
        for(int i = 0; i < nums.length - 1; i++) {
            if(nums[i] > nums[i + 1]) {
                count++;
                int temp = nums[i + 1];
                nums[i] = nums[i + 1];
                nums[i + 1] = temp;
                // [3,4,2,3]
                if(i >= 1 && nums[i - 1] > temp) {
                    return false;
                }
            }
            if(count == 2) {
                return false;
            }
        }
        return true;
    }
}

// Solution 1:
// Refer to
// https://leetcode.com/problems/non-decreasing-array/discuss/106826/JavaC++-Simple-greedy-like-solution-with-explanation/205227
/**
提供一种略有不同的方法。
顺序检查凹变段和逆序检查凸变段。
如果满足，则asc和desc中的较小值必然不大于1。
时间开销O(n)，空间开销O(1)，缺点是双向检查，优点是便于理解
*/
class Solution {
    public boolean checkPossibility(int[] nums) {
        int m = 0;
        int n = nums.length - 1;
        int asc = 0;
        int desc = 0;
        for(int i = 0; i < nums.length; i++) {
            if(nums[m] <= nums[i]) {
                m = i;
            } else {
                asc++;
            }
            if(nums[n] >= nums[nums.length - 1 - i]) {
                n = nums.length - 1 - i;
            } else {
                desc++;
            }
            if(asc > 1 && desc > 1) {
                return false;
            }
        }
        return true;
    }
}
