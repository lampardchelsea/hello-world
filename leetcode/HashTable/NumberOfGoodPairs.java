/**
Refer to
https://leetcode.com/problems/number-of-good-pairs/
Given an array of integers nums.
A pair (i,j) is called good if nums[i] == nums[j] and i < j.
Return the number of good pairs.
Example 1:
Input: nums = [1,2,3,1,1,3]
Output: 4
Explanation: There are 4 good pairs (0,3), (0,4), (3,4), (2,5) 0-indexed.
Example 2:
Input: nums = [1,1,1,1]
Output: 6
Explanation: Each pair in the array are good.
Example 3:
Input: nums = [1,2,3]
Output: 0
Constraints:
1 <= nums.length <= 100
1 <= nums[i] <= 100
*/

// Count how many times each number appears. If a number appears n times, then n * (n – 1) // 2 good pairs can be made with this number.
class Solution {
    public int numIdenticalPairs(int[] nums) {
        Map<Integer, Integer> map = new HashMap<Integer, Integer>();
        for(int num : nums) {
            map.put(num, map.getOrDefault(num, 0) + 1);
        }
        int result = 0;
        for(Map.Entry<Integer, Integer> entry : map.entrySet()) {
            int val = entry.getValue();
            result += val * (val - 1) / 2;
        }
        return result;
    }
}
