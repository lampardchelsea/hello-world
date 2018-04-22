/**
 * Refer to
 * https://leetcode.com/problems/degree-of-an-array/description/
 * Given a non-empty array of non-negative integers nums, the degree of this array is defined as the maximum 
   frequency of any one of its elements.

    Your task is to find the smallest possible length of a (contiguous) subarray of nums, that has the same degree as nums.

    Example 1:
    Input: [1, 2, 2, 3, 1]
    Output: 2
    Explanation: 
    The input array has a degree of 2 because both elements 1 and 2 appear twice.
    Of the subarrays that have the same degree:
    [1, 2, 2, 3, 1], [1, 2, 2, 3], [2, 2, 3, 1], [1, 2, 2], [2, 2, 3], [2, 2]
    The shortest length is 2. So return 2.
    Example 2:
    Input: [1,2,2,3,1,4,2]
    Output: 6
    Note:

    nums.length will be between 1 and 50,000.
    nums[i] will be an integer between 0 and 49,999.
 *
 *
 * Solution
 * https://leetcode.com/articles/degree-of-an-array/
*/
class Solution {
    public int findShortestSubArray(int[] nums) {
        if(nums == null || nums.length == 0) {
            return 0;
        }
        Map<Integer, Integer> left = new HashMap<Integer, Integer>();
        Map<Integer, Integer> right = new HashMap<Integer, Integer>();
        Map<Integer, Integer> count = new HashMap<Integer, Integer>();
        for(int i = 0; i < nums.length; i++) {
            int x = nums[i];
            // Left only store the 1st time happen for certain value
            if(left.get(x) == null) {
                left.put(x, i);
            }
            // Right always update for same value in left
            right.put(x, i);
            count.put(x, count.getOrDefault(x, 0) + 1);
        }
        int degree = 0;
        for(Map.Entry<Integer, Integer> entry : count.entrySet()) {
            degree = Math.max(degree, entry.getValue());
        }
        int result = Integer.MAX_VALUE;
        for(Map.Entry<Integer, Integer> entry : count.entrySet()) {
            int key = entry.getKey();
            int keyFrequence = entry.getValue();
            if(keyFrequence == degree) {
                result = Math.min(right.get(key) - left.get(key) + 1, result);    
            }            
        }
        return result;
    }
}
