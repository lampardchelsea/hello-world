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
                // 而已，因为如果以第二个1开头会部分重复以第一个1开头所得的组合。为了能连续跳过重复的数，这里我们必须先排序。
                // 实际上并不是同一条dfs的path上不能出现重复的1，比如{1，1，1}就是一个解而且dfs的path上重复了1，
                // 而是后面重复的1不能再和之前已经使用过的1出现在组合的同一个位置了
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

// Re-work
// Refer to
// Best video explain:
// https://www.youtube.com/watch?v=j9_qWJClp64&feature=emb_logo --> Use [1,2,3,1] -> sort to [1,1,2,3] and target = 3 
// as example to explain why the 2nd 1 will generate duplicate combination if put it as same as 1st 1 at the first
// position of combination, because like [1,2] as a success combination the first position 1 comes from 1st 1 in
// given array is good, but if we not filter the 2nd 1 out, then if this first position 1 comes from 2nd 1 in given
// array will generate a duplicate [1,2]
// https://leetcode.com/problems/combination-sum-ii/discuss/16861/Java-solution-using-dfs-easy-understand/16652
// https://leetcode.com/problems/combination-sum-ii/discuss/16861/Java-solution-using-dfs-easy-understand/16666
class Solution {
    public List<List<Integer>> combinationSum2(int[] candidates, int target) {
        List<List<Integer>> result = new ArrayList<List<Integer>>();
        Arrays.sort(candidates);
        helper(candidates, result, target, new ArrayList<Integer>(), 0);
        return result;
    }
    
    private void helper(int[] candidates, List<List<Integer>> result, int target, List<Integer> list, int index) {
        if(target < 0) {
            return;
        }
        if(target == 0) {
            result.add(new ArrayList<Integer>(list));
            return;
        }
        for(int i = index; i < candidates.length; i++) {
            /**
            Why we need i > index && candidates[i - 1] == candidates[i] ?
            https://leetcode.com/problems/combination-sum-ii/discuss/16861/Java-solution-using-dfs-easy-understand/16652
            Search in [1, 1, 1, 2, 2] for target 4, without the expression, you will get three identical combinations:
            [1, 1, 2, 2] from index [0, 1, 3, 4] of the candidates;
            [1, 1, 2, 2] from index [0, 2, 3, 4] of the candidates;
            [1, 1, 2, 2] from index [1, 2, 3, 4] of the candidates.
            
	    https://leetcode.com/problems/combination-sum-ii/discuss/16861/Java-solution-using-dfs-easy-understand/16666
            For those who don't understand how to avoid duplicate by:
            if "(i > cur && cand[i] == cand[i-1]) continue;
            when we should skip a number? not just it's the same as previous number, but also when it's previous number haven't been added!
            i > cur means cand[i - 1] is not added to the path (you should know why if you understand the algorithm), 
            so if cand[i] == cand[i-1], then we shouldn't add cand[i].
            */
            if(i > index && candidates[i - 1] == candidates[i]) {
                continue;
            }
            list.add(candidates[i]);
            helper(candidates, result, target - candidates[i], list, i + 1);
            list.remove(list.size() - 1);
        }
    }
}
