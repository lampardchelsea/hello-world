
/**
 * Refer to
 * https://leetcode.com/problems/longest-harmonious-subsequence/#/description
 * We define a harmonious array is an array where the difference between its maximum 
 * value and its minimum value is exactly 1.
 * Now, given an integer array, you need to find the length of its longest harmonious 
 * subsequence among all its possible subsequences.
    Example 1:
    Input: [1,3,2,2,5,2,3,7]
    Output: 5
    Explanation: The longest harmonious subsequence is [3,2,2,2,3].
    Note: The length of the input array will not exceed 20,000. 
 *
 * Solution
 * https://leetcode.com/articles/longest-harmonious-subsequence/
*/

public class Solution {
    public int findLHS(int[] nums) {
        if(nums == null || nums.length == 0) {
            return 0;
        }
        Map<Integer, Integer> map = new HashMap<Integer, Integer>();
        for(int i = 0; i < nums.length; i++) {
            if(!map.containsKey(nums[i])) {
                map.put(nums[i], 1);
            } else {
                map.put(nums[i], map.get(nums[i]) + 1);
            }
        }
        
        int maxLen = 0;
        for(Integer key : map.keySet()) {
            if(map.containsKey(key + 1)) {
                maxLen = Math.max(maxLen, map.get(key) + map.get(key + 1));
            }
        }
        return maxLen;
    }
}
