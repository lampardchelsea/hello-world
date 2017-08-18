/**
 * Refer to
 * https://leetcode.com/problems/combination-sum-ii/#/description
 *  Given a collection of candidate numbers (C) and a target number (T), 
 *  find all unique combinations in C where the candidate numbers sums to T.
 *  
 *  Each number in C may only be used once in the combination.
	Note:
	
	    All numbers (including target) will be positive integers.
	    The solution set must not contain duplicate combinations.
	
	For example, given candidate set [10, 1, 2, 7, 6, 1, 5] and target 8,
	A solution set is:
	
	[
	  [1, 7],
	  [1, 2, 5],
	  [2, 6],
	  [1, 1, 6]
	]
 * 
 * Different than Combination Sum I is given candidate set contains duplicates like '1' in example
 * 
 * Solution
 * https://discuss.leetcode.com/topic/46159/a-general-approach-to-backtracking-questions-in-java-subsets-permutations-combination-sum-palindrome-partitioning/2
*/
class Solution {
    public List<List<Integer>> combinationSum2(int[] candidates, int target) {
        List<List<Integer>> result = new ArrayList<List<Integer>>();
        if(target < 0 || candidates == null || candidates.length == 0) {
            return result;
        }
        List<Integer> combination = new ArrayList<Integer>();
        // Must sort first in case of skip duplicates later
        Arrays.sort(candidates);
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
                // skip duplicates
                if(i > startIndex && candidates[i] == candidates[i - 1]) {
                    continue;
                }
                combination.add(candidates[i]);
                helper(candidates, remain - candidates[i], result, combination, i + 1);
                combination.remove(combination.size() - 1);
            }
        }
    }
}
