/**
 * Refer to
 * https://leetcode.com/problems/subsets-ii/#/description
 *  Given a collection of integers that might contain duplicates, nums, return all possible subsets.
 *  Note: The solution set must not contain duplicate subsets.
	For example,
	If nums = [1,2,2], a solution is:
	[
	  [2],
	  [1],
	  [1,2,2],
	  [2,2],
	  [1,2],
	  []
	]
 *
 * Solution
 * https://www.jiuzhang.com/solutions/subsets-ii/
 * https://discuss.leetcode.com/topic/46159/a-general-approach-to-backtracking-questions-in-java-subsets-permutations-combination-sum-palindrome-partitioning
 */
class Solution {
    public List<List<Integer>> subsetsWithDup(int[] nums) {
        if(nums == null) {
            return null;
        }
        List<List<Integer>> result = new ArrayList<List<Integer>>();
        if(nums.length == 0) {
            return result;
        }
        List<Integer> list = new ArrayList<Integer>();
        // Before calling DFS, must sort the nums, then duplicate
        // check as 'nums[i] == nums[i - 1]' applicable
        Arrays.sort(nums);
        helper(nums, result, list, 0);
        return result;
    }
    
    private void helper(int[] nums, List<List<Integer>> result, List<Integer> list, int startIndex) {
        // Deep Copy
        result.add(new ArrayList<Integer>(list));
        for(int i = startIndex; i < nums.length; i++) {
            // Skip the duplicate number when create subset
            // Important: i > startIndex is necessary check
            // E.g if no check on i > startIndex, given
            // [1, 2, 2], will ArrayIndexOutOfBoundsException: -1
            if(i > startIndex && nums[i] == nums[i - 1]) {
                continue;
            }
            list.add(nums[i]);
            helper(nums, result, list, i + 1);
            list.remove(list.size() - 1);
        }
    }
}
