/**
 * Refer to
 * https://leetcode.com/problems/permutations-ii/description/
 * Given a collection of numbers that might contain duplicates, return all possible unique permutations.

    For example,
    [1,1,2] have the following unique permutations:
    [
      [1,1,2],
      [1,2,1],
      [2,1,1]
    ]
 * 
 * 
 * Solution
 * https://leetcode.com/submissions/detail/114437976/
*/
public class Solution {
    public List<List<Integer>> permuteUnique(int[] nums) {
        List<List<Integer>> result = new ArrayList<List<Integer>>();
        if(nums == null || nums.length == 0) {
            return result;
        }
        Arrays.sort(nums);
        List<Integer> combination = new ArrayList<Integer>();
        helper(nums, result, combination, new boolean[nums.length]);
        return result;
    }
    
    private void helper(int[] nums, List<List<Integer>> result, List<Integer> combination, boolean[] used) {
        if(combination.size() == nums.length) {
            result.add(new ArrayList<Integer>(combination));
        }
        for(int i = 0; i < nums.length; i++) {
            /*
            判断主要是为了去除重复元素影响。
            比如，给出一个排好序的数组，[1,2,2]，那么第一个2和第二2如果在结果中互换位置，
            我们也认为是同一种方案，所以我们强制要求相同的数字，原来排在前面的，在结果
            当中也应该排在前面，这样就保证了唯一性。所以当前面的2还没有使用的时候，就
            不应该让后面的2使用。
            */
            if(used[i] || (i > 0 && !used[i - 1] && nums[i] == nums[i - 1])) {
                continue;
            }
            used[i] = true;
            combination.add(nums[i]);
            helper(nums, result, combination, used);
            combination.remove(combination.size() - 1);
            // Don't forget to reset 'used' boolean flag back to false
            used[i] = false;
        }        
    }
}

// The solution for 40. Combination Sum II is the wrong way to solve this issue
// Because that's the way for computing Permutation, since change the order will be recognize as different solution
// e.g if target is 8, [2,6] and [6,2] is one combination but two permutation 
// Refer to
// https://leetcode.com/problems/combination-sum-ii/
/**
 Input
[1,1,2]

Output
[[1,1,2]]

Expected
[[1,1,2],[1,2,1],[2,1,1]]
*/
class Solution {
    public List<List<Integer>> permuteUnique(int[] nums) {
        List<List<Integer>> result = new ArrayList<List<Integer>>();
        Arrays.sort(nums);
        helper(result, new ArrayList<Integer>(), nums, 0);
        return result;
    }
    
    private void helper(List<List<Integer>> result, List<Integer> list, int[] nums, int index) {
        if(list.size() == nums.length) {
            result.add(new ArrayList<Integer>(list));
        }
        for(int i = index; i < nums.length; i++) {
            if(i > index && nums[i] == nums[i - 1]) {
                continue;
            }
            list.add(nums[i]);
            helper(result, list, nums, i + 1);
            list.remove(list.size() - 1);
        }
    }
}
