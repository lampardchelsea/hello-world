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
