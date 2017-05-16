import java.util.ArrayList;
import java.util.List;

/**
 * Refer to
 * https://leetcode.com/problems/combination-sum/#/description
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
 * https://segmentfault.com/a/1190000003743112
 * 深度优先搜索
 * 复杂度
 * 时间 O(N!) 空间 O(N) 递归栈空间
 * 思路
 * 因为我们可以任意组合任意多个数，看其和是否是目标数，而且还要返回所有可能的组合，所以我们必须遍历所有可能性才能求解。
 * 为了避免重复遍历，我们搜索的时候只搜索当前或之后的数，而不再搜索前面的数。因为我们先将较小的数计算完，所以到较大的数
 * 时我们就不用再考虑有较小的数的情况了。这题是非常基本且典型的深度优先搜索并返回路径的题。本题需要先排序，不然过不了Leetcode。
 * 
 * 注意
 * 要先问清楚什么样的组合是有效的，比如该题，是可以连续选择同一个数加入组合的。
 * 
 * https://discuss.leetcode.com/topic/46161/a-general-approach-to-backtracking-questions-in-java-subsets-permutations-combination-sum-palindrome-partitioning
 */
public class CombinationSumI {
    public List<List<Integer>> combinationSum(int[] candidates, int target) {
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
                // The difference than Combination Sum III is NOT
                // increase 'j' for next recursion which enable
                // multiple times using 'nums[j]' to approach target
                helper(result, tmp, target - candidates[j], candidates, j);
                tmp.remove(tmp.size() - 1);
            }
        }
    } 
    
    public static void main(String[] args) {
    	CombinationSumI c = new CombinationSumI();
    	int[] candidates = {2, 3, 7};
    	int n = 7;
    	List<List<Integer>> result = c.combinationSum(candidates, n);
    	for(List<Integer> r : result) {
    		System.out.println("---------");
    		for(Integer i : r) {
    			System.out.println(i);
    		}
        	System.out.println("---------");
    	}
    }
}

