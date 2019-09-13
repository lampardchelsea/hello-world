/**
 * Refer to
 * https://leetcode.com/problems/subsets/description/
 * Given a set of distinct integers, nums, return all possible subsets.
  Note: The solution set must not contain duplicate subsets.

  For example,
  If nums = [1,2,3], a solution is:

  [
    [3],
    [1],
    [2],
    [1,2,3],
    [1,3],
    [2,3],
    [1,2],
    []
  ]
 * 
 * Solution
 * http://www.jiuzhang.com/solutions/subsets/
 * https://discuss.leetcode.com/topic/46159/a-general-approach-to-backtracking-questions-in-java-subsets-permutations-combination-sum-palindrome-partitioning
*/
public class Solution {
    public List<List<Integer>> subsets(int[] nums) {
        if(nums == null) {
            return null;
        }
        List<List<Integer>> result = new ArrayList<List<Integer>>();
        if(nums.length == 0) {
            return result;
        }
        List<Integer> list = new ArrayList<Integer>();
        helper(nums, result, list, 0);
        return result;
    }
    
    // 递归三要素
    // 1. 递归的定义：在 Nums 中找到所有以 list 开头的的集合，并放到 result
    private void helper(int[] nums, List<List<Integer>> result, List<Integer> list, int startIndex) {
        // 2. 递归的拆解
        // Deep copy
        result.add(new ArrayList<Integer>(list));
        for(int i = startIndex; i < nums.length; i++) {
            // [1] -> [1,2]
            list.add(nums[i]);
            // 寻找所有以 [1,2] 开头的集合，并扔到 result
            helper(nums, result, list, i + 1);
            // [1,2] -> [1]  回溯
            list.remove(list.size() - 1);
        }
    }
}

// Style 2:
// Refer to
// https://leetcode.com/problems/subsets/discuss/27550/Very-simple-and-fast-java-solution-with-explanation
class Solution {
    public List<List<Integer>> subsets(int[] nums) {
        List<List<Integer>> result = new ArrayList<List<Integer>>();
        helper(result, new ArrayList<Integer>(), nums, 0);
        return result;
    }
    
    private void helper(List<List<Integer>> result, List<Integer> list, int[] nums, int index) {
        // The idea is use pos to keep track of the index of the array. 
        // Compare to other backracking problem like combinations, the 
        // condition that each single List adds to the List<List> is when 
        // the index of the array is valid. Meanwhile, after adding to 
        // the List<List> , keeping going for the for loop.
        if(index <= nums.length) {
            result.add(new ArrayList<Integer>(list));
        }
        for(int i = index; i < nums.length; i++) {
            list.add(nums[i]);
            helper(result, list, nums, i + 1);
            list.remove(list.size() - 1);
        }
    }
}
