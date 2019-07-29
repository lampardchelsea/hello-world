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

// New try with comprehensive detail explaination:
// Refer to
// https://www.youtube.com/watch?v=RSatA4uVBDQ
// 花花酱 LeetCode 40. Combination Sum II - 刷题找工作 EP88
class Solution {
    public List<List<Integer>> combinationSum2(int[] candidates, int target) {
        List<List<Integer>> result = new ArrayList<List<Integer>>();
        if(candidates == null || candidates.length == 0) {
            return result;
        }
        Arrays.sort(candidates);
        // Why we need to sort ? Because we need to skip the duplicate
        // e.g [10,1,2,7,6,1,5] -> [1,1,2,5,6,7,10]
        // if the target is 8, you will able to use 1 at index = 0
        // or 1 at index = 1 to add with 7 to create 8
        // but in result it will generate duplicate combination as two 
        // [1 (index = 0), 7] and [1 (index = 1), 7]
        // So to make sure we don't use the 1 at index = 1, introduce
        // judgement if(i > startIndex && candidates[i] == candidates[i - 1]) -> skip
        helper(target, candidates, result, new ArrayList<Integer>(), 0);
        return result;
    }
    
    private void helper(int remain, int[] candidates, List<List<Integer>> result, List<Integer> list, int startIndex) {
        if(remain < 0) {
            return;
        }
        if(remain == 0) {
            result.add(new ArrayList<Integer>(list));
        }
        for(int i = startIndex; i < candidates.length; i++) {
            if(i > startIndex && candidates[i] == candidates[i - 1]) {
                continue;
            }
            if(remain >= candidates[i]) {
                list.add(candidates[i]);
                // i + 1 to make sure each element only use once
                helper(remain - candidates[i], candidates, result, list, i + 1);
                list.remove(list.size() - 1);
            }
        }
    }
}


