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
public class Solution {
    /**
     * @param nums:  an array of n integers
     * @param target: a target
     * @return: the number of index triplets satisfy the condition nums[i] + nums[j] + nums[k] < target
     */
    public int threeSumSmaller(int[] nums, int target) {
        int count = 0;
        for(int i = 0; i < nums.length - 2; i++) {
            int sum = target - nums[i];
            for(int j = i + 1; j < nums.length - 1; j++) {
                for(int k = j + 1; k < nums.length; k++) {
                    if(nums[j] + nums[k] < sum) {
                        count++;
                    }
                }
            }            
        }
        return count;
    }
}

// Solution 2: Two Pointers
// https://www.cnblogs.com/grandyang/p/5235086.html
/**
Since we only care about number of triplet combinations, we don't care about the indexes value, we can sort the array
题目中的 Follow up 让我们在 O(n^2) 的时间复杂度内实现，那么借鉴之前那两道题 3Sum Closest 和 3Sum 中的方法，采用双指针来做，
这里面有个 trick 就是当判断三个数之和小于目标值时，此时结果应该加上 right-left，因为数组排序了以后，如果加上 num[right] 
小于目标值的话，那么加上一个更小的数必定也会小于目标值，然后将左指针右移一位，否则将右指针左移一位，参见代码如下：
// O(n^2)
class Solution {
public:
    int threeSumSmaller(vector<int>& nums, int target) {
        if (nums.size() < 3) return 0;
        int res = 0, n = nums.size();
        sort(nums.begin(), nums.end());
        for (int i = 0; i < n - 2; ++i) {
            int left = i + 1, right = n - 1;
            while (left < right) {
                if (nums[i] + nums[left] + nums[right] < target) {
                    res += right - left;
                    ++left;
                } else {
                    --right;
                }
            }
        }
        return res;
    }
};
*/

// Refer to
// http://buttercola.blogspot.com/2015/08/leetcode.html
/**
Understand the problem:
The problem looks quite similar to the 3-sum. Thus we could still sort the array first and use two pointers. 

The only thing needs to take special care of is how to move the pointers. There are two cases to handle: 
  -- If A[i] + A[j] + A[k] < target, which means the numbers between j and k are all less than target, 
  because the array is sorted. Then we move the j pointer forward. 
  -- If A[i] + A[j] + A[k] >= target, we move k pointer backward.
public class Solution {
    public int threeSumSmaller(int[] nums, int target) {
        if (nums == null || nums.length < 3) {
            return 0;
        }
         
        int result = 0;
        Arrays.sort(nums);
         
        for (int i = 0; i < nums.length - 2; i++) {
            int j = i + 1;
            int k = nums.length - 1;
            while (j < k) {
                if (nums[i] + nums[j] + nums[k] < target) {
                    result += (k - j);
                    j++;
                } else {
                    k--;
                }
            }
        }
         
        return result;
    }
}
*/


