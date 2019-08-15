/**
 * Refer to
 *  https://leetcode.com/problems/combination-sum/#/description
 *  Given a set of candidate numbers (C) (without duplicates) and a target number (T), 
 *  find all unique combinations in C where the candidate numbers sums to T.
 *  
 *  The same repeated number may be chosen from C unlimited number of times.
 *  Note:
    All numbers (including target) will be positive integers.
    The solution set must not contain duplicate combinations.
 * 
 *  For example, given candidate set [2, 3, 6, 7] and target 7,
 *  A solution set is:
	[
	  [7],
	  [2, 2, 3]
	]
*  
* Solution
* https://discuss.leetcode.com/topic/46159/a-general-approach-to-backtracking-questions-in-java-subsets-permutations-combination-sum-palindrome-partitioning/2
*/
class Solution {
    public List<List<Integer>> combinationSum(int[] candidates, int target) {
        List<List<Integer>> result = new ArrayList<List<Integer>>();
        if(target < 0 || candidates == null || candidates.length == 0) {
            return result;
        }
	// No need to sort first
        // Arrays.sort(candidates);
        List<Integer> combination = new ArrayList<Integer>();
        helper(candidates, target, result, combination, 0);
        return result;
    }
    
    private void helper(int[] candidates, int remain, List<List<Integer>> result, List<Integer> combination, int startIndex) {
        if(remain < 0) {
            return;
        } else if(remain == 0) {
            result.add(new ArrayList<Integer>(combination));    
        } else {
            for(int i = startIndex; i < candidates.length; i++) {
                combination.add(candidates[i]);
                // (1)Not i + 1 because we can reuse same elements
                // If use i + 1, then given [2, 3, 6, 7] and target = 7, will only
                // have combination as [7], and miss the combination [2, 2, 3]
		// (2)Not index because we don't want to go back from the initial
		// position to pick up elements, which will create duplicate,
		// if using helper(candidates, remain - candidates[i], result, combination, index);
		// the wrong result will be below:
	        // Input: [2,3,6,7], 7
		// Output: [[2,2,3],[2,3,2],[3,2,2],[7]]
		// Expected: [[2,2,3],[7]]
		// Refer to the counter part is Combination Sum IV, which should put startIndex
		// instead of i, since we need to go back to initial position to pick up elements.
                helper(candidates, remain - candidates[i], result, combination, i);
                combination.remove(combination.size() - 1);
            }
        }
    }
}
