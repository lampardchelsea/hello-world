import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
 * Solution 1
 * https://segmentfault.com/a/1190000003498803
 * 深度优先搜索
 * 复杂度
 * 时间 O(NlogN) 空间 O(N) 递归栈空间
 * 思路
 * 思路和上题一样，区别在于如果有重复的只能加一次。好在我们已经先将数组排序（数组中有重复一般都可以用排序解决），所以重复元素是相邻的，
 * 我们为了保证重复元素只加一次，要把这些重复的元素在同一段逻辑中一起处理，而不是在递归中处理，不然就太麻烦了。所以我们可以先统计好
 * 重复的有n个，然后分别在集合中加上0个，1个，2个...n个，然后再分别递归
 * 
 * Solution 2
 * https://discuss.leetcode.com/topic/46159/a-general-approach-to-backtracking-questions-in-java-subsets-permutations-combination-sum-palindrome-partitioning/2
 */
public class SubsetsII {
	// Solution 1: DFS
    public List<List<Integer>> subsetsWithDup(int[] nums) {
        List<List<Integer>> result = new ArrayList<List<Integer>>();
        Arrays.sort(nums);
        result.add(new ArrayList<Integer>());
        helper(result, new ArrayList<Integer>(), nums, 0);
        return result;
    }
    
    public void helper(List<List<Integer>> result, List<Integer> tmp, int[] nums, int index) {
        if(index >= nums.length) {
            return;
        }
        int oldIndex = index;
        // Skip duplicates, and find how many duplicates of current number[index]
        while(index < nums.length - 1 && nums[index] == nums[index + 1]) {
            index++;
        }
        // Logic branch-1: Not add nums[index] into current subset and its inheritance chain sets
        helper(result, tmp, nums, index + 1);
        for(int i = oldIndex; i <= index; i++) {
            List<Integer> oneSubset = new ArrayList<Integer>(tmp);
            // Important: Must contain this loop to make sure adding all duplicate
            // items of current loop.
            // E.g If given {2, 2} as initial input, then oldIndex = 0, index = 1,
            // in the first loop, if without this loop, we only add one 2 into
            // oneSubset, and result is also {2}, this is wrong, since next loop
            // will skip the first 2 of {2, 2} and only left the second 2, which
            // result in next loop we still only add one 2 into oneSubset, we finally
            // miss the case of adding both 2 into oneSubset, so the question now is
            // how we make sure adding all duplicates, the answer is adding a loop
            // here, it will make sure to add numbers of duplicates descend, e.g
            // add two 2 first, in next loop(outside) when oldIndex increase, will
            // add one 2 later.
            for(int j = i; j <= index; j++) {
            	// Either we can use nums[index], as nums[j] always equals to nums[index]
            	// since they are duplicates
                oneSubset.add(nums[j]);               
            }
            result.add(oneSubset);
            // Logic branch-2: Add nums[index] into current subset and its inheritance chain sets
            helper(result, oneSubset, nums, index + 1);
        }
    }
	
    // Solution 2: Using backtrack template
    public List<List<Integer>> subsetsWithDup2(int[] nums) {
        List<List<Integer>> result = new ArrayList<List<Integer>>();
        // This sort is required to remove duplicates
        Arrays.sort(nums);
        result.add(new ArrayList<Integer>());
        helper2(result, new ArrayList<Integer>(), nums, 0);
        return result;
    }
    
    public void helper2(List<List<Integer>> result, List<Integer> tmp, int[] nums, int index) {
        for(int i = index; i < nums.length; i++) {
            if(i > index && nums[i] == nums[i - 1]) {
                continue;
            }
            tmp.add(nums[i]);
            // Create new ArrayList in case to avoid change on original 'tmp' reference
            result.add(new ArrayList<Integer>(tmp));
            helper(result, tmp, nums, i + 1);
            tmp.remove(tmp.size() - 1);
        }
    }
      
    public static void main(String[] args) {
    	int[] nums = {1, 2, 2};
    	SubsetsII s = new SubsetsII();
    	List<List<Integer>> result = s.subsetsWithDup(nums);
    	for(List<Integer> a : result) {
    		System.out.println("------------");
    		for(Integer i : a) {
    			System.out.println(i);
    		}
    		System.out.println("------------");
    	}
    }
}
