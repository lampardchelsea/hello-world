import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
 * Solution
 * https://segmentfault.com/a/1190000003743112
 * 深度优先搜索
 * 复杂度
 * 时间 O(N!) 空间 O(N) 递归栈空间
 * 思路
 * 这题和I的区别在于同一个数只能取一次，比如数组中只有3个1，那结果中也最多只有3个1，而且结果也不能重复。
 * 所以我们在递归时首先要把下标加1，这样下轮搜索中就排除了自己。其次，对一个数完成了全部深度优先搜索后，
 * 比如对1完成了搜索，那么我们要把后面的1都跳过去。当然，跳过只是针对本轮搜索的，在对第一个1的下一轮的
 * 搜索中，我们还是可以加上第二个1。只是我们不能再以第二个1开头了而已。为了能连续跳过重复的数，这里我们
 * 必须先排序。
 */
public class CombinationSumII {
    public List<List<Integer>> combinationSum2(int[] candidates, int target) {
    	// Must sort first to skip redundant same values
    	// e.g If given {1, 1, 1, 1} for target 3, the last '1' is redundant,
    	// the combination is only {1, 1, 1} set
    	Arrays.sort(candidates);
        List<List<Integer>> result = new ArrayList<List<Integer>>();
        helper(result, new ArrayList<Integer>(), target, candidates, 0);
        return result;
    }
    
    public void helper(List<List<Integer>> result, List<Integer> tmp, int target, int[] candidates, int i) {
        if(target < 0) {
            return;
        } else if(target == 0) {
            List<Integer> oneComb = new ArrayList<Integer>(tmp);
            result.add(oneComb);
            return;
        } else {
            for(int j = i; j < candidates.length; j++) {
                tmp.add(candidates[j]);
                // The difference than Combination Sum I is
                // increase 'j' for next recursion which disable
                // multiple times using 'nums[j]' to approach target
                helper(result, tmp, target - candidates[j], candidates, j + 1);
                tmp.remove(tmp.size() - 1);
                // Continuously skip all redundant items have same value as the item 
                // which already used in current loop for combination(Must sort first)
                // 比如对1完成了搜索，那么我们要把后面的1都跳过去。当然，跳过只是针对本轮搜索的， 在对
                // 第一个1的下一轮的搜索中，我们还是可以加上第二个1。只是我们不能再以第二个1开头了
                // 而已。为了能连续跳过重复的数，这里我们必须先排序。
                // E.g int[] candidates = {1, 1, 1, 1}, target = 3, if not skip
                // redundant '1' for each round, will give result as 
                // {{1, 1, 1}, {1, 1, 1}, {1, 1, 1}, {1, 1, 1}}, this is wrong
                while(j < candidates.length - 1 && candidates[j + 1] == candidates[j]) {
                	j++;
                }
            }
        }
    }	
    
    public static void main(String[] args) {
    	CombinationSumII c = new CombinationSumII();
//    	int[] candidates = {10, 1, 2, 7, 6, 1, 5};
    	int[] candidates = {1, 1, 1, 1};
    	int n = 3;
    	List<List<Integer>> result = c.combinationSum2(candidates, n);
    	for(List<Integer> r : result) {
    		System.out.println("---------");
    		for(Integer i : r) {
    			System.out.println(i);
    		}
        	System.out.println("---------");
    	}
    }
}

