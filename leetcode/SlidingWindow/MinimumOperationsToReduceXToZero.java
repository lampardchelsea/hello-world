/**
Refer to
https://leetcode.com/problems/minimum-operations-to-reduce-x-to-zero/
You are given an integer array nums and an integer x. In one operation, you can either remove the leftmost or the rightmost element 
from the array nums and subtract its value from x. Note that this modifies the array for future operations.

Return the minimum number of operations to reduce x to exactly 0 if it's possible, otherwise, return -1.

Example 1:
Input: nums = [1,1,4,2,3], x = 5
Output: 2
Explanation: The optimal solution is to remove the last two elements to reduce x to zero.

Example 2:
Input: nums = [5,6,7,8,9], x = 4
Output: -1

Example 3:
Input: nums = [3,2,20,1,1,3], x = 10
Output: 5
Explanation: The optimal solution is to remove the last three elements and the first two elements (5 operations in total) to reduce x to zero.

Constraints:
1 <= nums.length <= 105
1 <= nums[i] <= 104
1 <= x <= 109
*/

// Solution 1: Not fixed length slidnig window + 1423. Maximum Points You Can Obtain from Cards
// https://github.com/lampardchelsea/hello-world/blob/master/leetcode/SlidingWindow/MaximumPointsYouCanObtainFromCards.java

// Refer to
// https://leetcode.com/problems/minimum-operations-to-reduce-x-to-zero/discuss/936074/JavaPython-3-Sliding-window%3A-Longest-subarray-sum-to-the-target-sum(nums)-x.
/**
Using sliding window to find the longest subarry that sums to sum(nums) - x.

    public int minOperations(int[] nums, int x) {
        int target = Arrays.stream(nums).sum() - x, size = -1, n = nums.length;
        for (int lo = -1, hi = 0, winSum = 0; hi < n; ++hi) {
            winSum += nums[hi];
            while (lo + 1 < nums.length && winSum > target) {
                winSum -= nums[++lo];
            }
            if (winSum == target) {
                size = Math.max(size, hi - lo);
            }
        }
        return size < 0 ? -1 : n - size;
    }

Analysis:
Time: O(n), space: O(1), where n = nums.length.

Similar problems:
918. Maximum Sum Circular Subarray
1423. Maximum Points You Can Obtain from Cards
*/
class Solution {
    public int minOperations(int[] nums, int x) {
        int n = nums.length;
        int sum = 0;
        for(int j = 0; j < n; j++) {
            sum += nums[j];
        }
        int target = sum - x;
        int maxLen = -1;
        int i = 0;
        sum = 0;
        for(int j = 0; j < n; j++) {
            sum += nums[j];
            // i can equal to j which means use up all elements currently have, if only set as i < j, test out by below case:
            // Input: nums = [8828,9581,49,9818,9974,9869,9991,10000,10000,10000,9999,9993,9904,8819,1231,6309] and x = 134365
            // Output: -1
            // Expected: 16
            while(i <= j && sum > target) {
                sum -= nums[i];
                i++;
            }
            // check 'sum == target' must after while loop, test out by below case:
            // Input: nums = [3,2,20,1,1,3] and x = 10
            // Output: -1
            // Expected: 5
            if(sum == target) {
                maxLen = Math.max(maxLen, j - i + 1);
            }
        }
        return maxLen < 0 ? -1 : n - maxLen;
    }
}
