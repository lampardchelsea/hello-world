/**
 * Refer to
 * https://leetcode.com/problems/permutations/description/
 * Given a collection of distinct numbers, return all possible permutations.

    For example,
    [1,2,3] have the following permutations:
    [
      [1,2,3],
      [1,3,2],
      [2,1,3],
      [2,3,1],
      [3,1,2],
      [3,2,1]
    ]

 *
 * Solution
 * https://discuss.leetcode.com/topic/46159/a-general-approach-to-backtracking-questions-in-java-subsets-permutations-combination-sum-palindrome-partitioning
 * https://www.jiuzhang.com/solutions/permutations/
*/
public class Solution {
    public List<List<Integer>> permute(int[] nums) {
        List<List<Integer>> result = new ArrayList<List<Integer>>();
        if(nums == null || nums.length == 0) {
            return result;
        }
        List<Integer> combination = new ArrayList<Integer>();
        helper(nums, result, combination);
        return result;
    }
    
    private void helper(int[] nums, List<List<Integer>> result, List<Integer> combination) {
        if(combination.size() == nums.length) {
            result.add(new ArrayList<Integer>(combination));
        }
        for(int i = 0; i < nums.length; i++) {
            // If element already exist, skip
            if(combination.contains(nums[i])) {
                continue;
            }
            combination.add(nums[i]);
            helper(nums, result, combination);
            combination.remove(combination.size() - 1);
        }
    }
}
