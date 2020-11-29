/**
Refer to
https://www.lintcode.com/problem/3sum-smaller/note/179580
Given an array of n integers nums and a target, find the number of index triplets i, j, k 
with 0 <= i < j < k < n that satisfy the condition nums[i] + nums[j] + nums[k] < target.

Example1
Input:  nums = [-2,0,1,3], target = 2
Output: 2
Explanation:
Because there are two triplets which sums are less than 2:
[-2, 0, 1]
[-2, 0, 3]

Example2
Input: nums = [-2,0,-1,3], target = 2
Output: 3
Explanation:
Because there are three triplets which sums are less than 2:
[-2, 0, 1]
[-2, 0, 3]
[-2, -1, 3]

Challenge
Could you solve it in O(n2) runtime?
*/

// Solution 1: Brute Force O(n^3)
// Refer to
// https://www.cnblogs.com/grandyang/p/5235086.html
/**
Since we only care about number of triplet combinations, we don't care about the indexes value, we can sort the array
这道题是 3Sum 问题的一个变形，让我们求三数之和小于一个目标值，那么最简单的方法就是穷举法，将所有的可能的三个数字的组合都遍历一遍，
比较三数之和跟目标值之间的大小，小于的话则结果自增1，参见代码如下:
// O(n^3)
class Solution {
public:
    int threeSumSmaller(vector<int>& nums, int target) {
        int res = 0;
        sort(nums.begin(), nums.end());
        for (int i = 0; i < int(nums.size() - 2); ++i) {
            int left = i + 1, right = nums.size() - 1, sum = target - nums[i];
            for (int j = left; j <= right; ++j) {
                for (int k = j + 1; k <= right; ++k) {
                    if (nums[j] + nums[k] < sum) ++res;
                }
            }
        }
        return res;
    }
};
*/

