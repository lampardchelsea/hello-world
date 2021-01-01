/**
Refer to
https://leetcode.com/problems/special-array-with-x-elements-greater-than-or-equal-x/
You are given an array nums of non-negative integers. nums is considered special if there exists a number x 
such that there are exactly x numbers in nums that are greater than or equal to x.

Notice that x does not have to be an element in nums.

Return x if the array is special, otherwise, return -1. It can be proven that if nums is special, the value for x is unique.

Example 1:
Input: nums = [3,5]
Output: 2
Explanation: There are 2 values (3 and 5) that are greater than or equal to 2.

Example 2:
Input: nums = [0,0]
Output: -1
Explanation: No numbers fit the criteria for x.
If x = 0, there should be 0 numbers >= x, but there are 2.
If x = 1, there should be 1 number >= x, but there are 0.
If x = 2, there should be 2 numbers >= x, but there are 0.
x cannot be greater since there are only 2 numbers in nums.

Example 3:
Input: nums = [0,4,3,0,4]
Output: 3
Explanation: There are 3 values that are greater than or equal to 3.

Example 4:
Input: nums = [3,6,7,7,0]
Output: -1

Constraints:
1 <= nums.length <= 100
0 <= nums[i] <= 1000
*/

// Solution 1: Binary Search
// Refer to
// https://leetcode.com/problems/special-array-with-x-elements-greater-than-or-equal-x/discuss/882910/Java-Beats-100-Two-Binary-Search-methods-and-detailed-explanation
/**
First, we can think out if we sort the array, that will be easier for us to find the X. If the array is sorted, 
for nums[i] there will be len - i numbers that are greater or equal to nums[i].
So one idea by intution is to enumerate each X and find the first position that nums[i] >= X . Then check 
len - i = X to see whether it is the correct X to return.

1. Binary Search to find the first position >= nums[i]
Since the array is already sorted, we can use binary search instead of for loop.

class Solution {
    public int specialArray(int[] nums) {
        Arrays.sort(nums);
        int len = nums.length;
        // enumerate all possible number i
        for (int x = 0; x <= nums[len - 1]; x++) {
            // find the first index that nums[idx] >= i
            int idx = findFirstGreaterOrEqual(x, nums);
            if (len - idx == x) {
                return x;
            }
        }
        return -1;
    }

    private int findFirstGreaterOrEqual(int target, int[] nums) {
        int left = 0;
        int right = nums.length - 1;
        while (left + 1 < right) {
            int mid = left + (right - left) / 2;
            if (nums[mid] >= target) {
                right = mid;
            } else {
                left = mid;
            }
        }

        if (nums[left] >= target) {
            return left;
        }
        return right; // whether right >= target or right > target
    }
}
Beats 89.04% time and 93.46% space.

Time Complexity: O((N+L)logN) where N is array length, L is data range.
Sort will take O(NlogN)
enumerate will have L iterations and O(logN) for each iteration.
Space Complexity: O(N)
Space complexity depending the sorting algorithm. Usually O(n).
2. Binary Search to find the first position nums[i] >= len - i
For the first method we spend a lot of time to enumerate all possible X . However, we don't need to repeat binary search so many times. 
We can use another way to solve this problem.
We can direactly to find the nums[i] not the X . In method 1, we try to find the first position that nums[i] >= X and X = len - i . 
Thus here we can directly find first position i that nums[i] >= len - i .
Similar to classical binary search, but we need ensure first index by check whether nums[i - 1] < len - i when we find the number that nums[i] >= len - i.

Example:
nums = {0, 0, 3, 3, 4}
              i

We should match this two condition at the same time:
1. nums[i] >= len - i ✅
2. nums[i - 1] < len - i ✅

Otherwise:
nums = {3, 3, 3, 4}
		   i
1. nums[i] >= len - i ✅
2. nums[i - 1] >= len - i ❌

Not first index.
Java Code:

class Solution {
    public int specialArray(int[] nums) {
        Arrays.sort(nums);
        int len = nums.length;
        int left = 0;
        int right = len - 1;
        // binary search to find the first position that
        // nums[i] >= len - i and nums[i - 1] < len - i
        while (left <= right) {
            int mid = left + (right - left) / 2;
            if (nums[mid] >= len - mid) {
                // treat index 0 to avoid IndexOutOfBoundError
                if (mid == 0 || nums[mid - 1] < len - mid) { // correct index
                    return len - mid;
                } else { // not the first position, shrink right bound
                    right = mid - 1;
                }
            } else { // otherwisem, shrink the left bound to increase nums[mid]
                left = mid + 1;
            }
        }
        return -1;
    }
}

Beats 100% time and 99.63% space.
Time Complexity: O(NlogN) where N is array length.
O(NlogN) for sort
O(logN) for one binary search.
Space Complexity: O(N)
Space complexity depending the sorting algorithm. Usually O(n).
*/
class Solution {
    public int specialArray(int[] nums) {
        Arrays.sort(nums);
        int len = nums.length;
        for(int x = 0; x <= nums[len - 1]; x++) {
            // Find the first index 'idx' that nums[idx] >= x
            int idx = binarySearch(x, nums);
            // The actual numbers larger or equal to x is
            // (len - 1 - idx + 1 == x) --> len - idx == x
            if(len - 1 - idx + 1 == x) {
                return x;
            }
        }
        return -1;
    }
    
    private int binarySearch(int x, int[] nums) {
        int lo = 0;
        int hi = nums.length - 1;
        while(lo + 1 < hi) {
            int mid = lo + (hi - lo) / 2;
            if(nums[mid] < x) {
                lo = mid;
            } else {
                hi = mid;
            }
        }
        if(nums[lo] >= x) {
            return lo;
        }
        // Whether nums[hi] >= target or nums[hi] > target
        return hi;
    }
}
