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
 * 
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
