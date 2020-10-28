// New try with similar way as Combination I and II
class Solution {
    public List<List<Integer>> combinationSum3(int k, int n) {
        List<List<Integer>> result = new ArrayList<List<Integer>>();
        // Create a combination to present 1 to 9 similar to combination
        // definition on Combination I and II
        int[] combination = new int[] {1,2,3,4,5,6,7,8,9};
        helper(n, result, combination, new ArrayList<Integer>(), k, 0);
        return result;
    }
    
    private void helper(int remain, List<List<Integer>> result, int[] combination, List<Integer> list, int k, int startIndex) {
        if(remain < 0 || k < 0) {
            return;
        }
        if(k == 0 && remain == 0) {
            result.add(new ArrayList<Integer>(list));
        }
        for(int i = startIndex; i < combination.length; i++) {
            if(remain >= combination[i]) {
                list.add(combination[i]);
                // i + 1 to make sure each element only use once
                helper(remain - combination[i], result, combination, list, k - 1, i + 1);
                list.remove(list.size() - 1);
            }
        }
    }
}









import java.util.ArrayList;
import java.util.List;

/**
 * Refer to
 * https://leetcode.com/problems/combination-sum-iii/#/description
 * Find all possible combinations of k numbers that add up to a number n, 
 * given that only numbers from 1 to 9 can be used and each combination 
 * should be a unique set of numbers.
	Example 1:	
	Input: k = 3, n = 7	
	Output:	
	[[1,2,4]]
	
	Example 2:
	Input: k = 3, n = 9	
	Output:	
	[[1,2,6], [1,3,5], [2,3,4]]
 * 
 * Solution
 * https://segmentfault.com/a/1190000003743112
 * 深度优先搜索
 * 复杂度
 * 时间 O(9!) 空间 O(9) 递归栈空间
 * 思路
 * 这题其实是II的简化版，设想一个[1,2,3,4,5,6,7,8,9]的数组，同样一个元素只能取一次，
 * 但是已经预先确定没有重复了。所以可以省去跳过重复元素的部分。不过，我们在递归的时候
 * 要加一个额外的变量k来控制递归的深度，一旦超过了预设深度，就停止该分支的搜索。本质
 * 上是有限深度优先搜索。
 */
public class CombinationSumIII {
    public List<List<Integer>> combinationSum3(int k, int n) {
        List<List<Integer>> result = new ArrayList<List<Integer>>();
        helper(result, new ArrayList<Integer>(), k, n, 1);
        return result;
    }
    
    // Additional control variable 'k' used for control depth, if over given depth, e.g k < 0
    // we will stop further search on this branch
    public void helper(List<List<Integer>> result, List<Integer> tmp, int k, int target, int i) {
        // Base case
    	// These condition redundant
	//if(target < 0 || k < 0) {
        //	return;
        //} else if(target == 0 && k == 0) {
        if(target == 0 && k == 0) {
        	List<Integer> oneComb = new ArrayList<Integer>(tmp);
        	result.add(oneComb);
        	return;
        } else {
        	for(int j = i; j <= 9; j++) {
        		tmp.add(j);
        		helper(result, tmp, k - 1, target - j, j + 1);
        		// If current 'j' not satisfied, should remove it
        		// from the tmp array list, and it always positioned
        		// at last index
        		tmp.remove(tmp.size() - 1);
        	}
        }
    }
    
    public static void main(String[] args) {
    	CombinationSumIII c = new CombinationSumIII();
    	int k = 3, n = 9;
    	List<List<Integer>> result = c.combinationSum3(k, n);
    	for(List<Integer> r : result) {
    		System.out.println("---------");
    		for(Integer i : r) {
    			System.out.println(i);
    		}
        	System.out.println("---------");
    	}

    }
}

// Another way of no need to build nums array
// Refer to
// https://leetcode.com/problems/combination-sum-iii/discuss/60614/Simple-and-clean-Java-code-backtracking.
class Solution {
    public List<List<Integer>> combinationSum3(int k, int n) {
        List<List<Integer>> result = new ArrayList<List<Integer>>();
        helper(result, new ArrayList<Integer>(), 1, k, n);
        return result;
    }
    
    private void helper(List<List<Integer>> result, List<Integer> list,
                        int index, int k, int remain) {
        if(k == 0 && remain == 0) {
            result.add(new ArrayList<Integer>(list));
            return;
        }
        if(k == 0 || remain == 0) {
            return;
        }
        for(int i = index; i <= 9; i++) {
            list.add(i);
            helper(result, list, i + 1, k - 1, remain - i);
            list.remove(list.size() - 1);
        }
    }
}

// Re-work
class Solution {
    public List<List<Integer>> combinationSum3(int k, int n) {
        List<List<Integer>> result = new ArrayList<List<Integer>>();
        helper(1, result, new ArrayList<Integer>(), k, n);
        return result;
    }
    
    private void helper(int start, List<List<Integer>> result, List<Integer> list, int k, int target) {
        if(k < 0 || target < 0) {
            return;
        }
        if(k == 0 && target == 0) {
            result.add(new ArrayList<Integer>(list));
            return;
        }
        for(int i = start; i <= 9; i++) {
            list.add(i);
            helper(i + 1, result, list, k - 1, target - i);
            list.remove(list.size() - 1);
        }
    }
}
