/**
 Refer to
 https://leetcode.com/problems/combinations/
 Given two integers n and k, return all possible combinations of k numbers out of 1 ... n.

Example:
Input: n = 4, k = 2
Output:
[
  [2,4],
  [3,4],
  [2,3],
  [1,2],
  [1,3],
  [1,4],
]
*/

// Solution 1: No need to create numbers array
// Refer to
// https://leetcode.com/problems/combinations/discuss/27002/Backtracking-Solution-Java
class Solution {
    public List<List<Integer>> combine(int n, int k) {
        List<List<Integer>> result = new ArrayList<List<Integer>>();
        helper(result, new ArrayList<Integer>(), n, k, 1);
        return result;
    }
    
    private void helper(List<List<Integer>> result, List<Integer> list, int n, int k, int index) {
        if(k == 0) {
            result.add(new ArrayList<Integer>(list));
            return;
        }
        for(int i = index; i <= n; i++) {
            list.add(i);
            helper(result, list, n, k - 1, i + 1);
            list.remove(list.size() - 1);
        }
    }
}


// Solution 2: Create numbers array
class Solution {
    public List<List<Integer>> combine(int n, int k) {
        List<List<Integer>> result = new ArrayList<List<Integer>>();
        // Create numbers array and use it together with index increasing
        // in recursive helper method
        int[] nums = new int[n];
        for(int i = 0; i < n; i++) {
            nums[i] = i + 1;
        }
        helper(result, new ArrayList<Integer>(), nums, k, 0);
        return result;
    }
    
    private void helper(List<List<Integer>> result, List<Integer> list, int[] nums, int k, int index) {
        if(list.size() == k) {
            result.add(new ArrayList<Integer>(list));
        }
        for(int i = index; i < nums.length; i++) {
            list.add(nums[i]);
            helper(result, list, nums, k, i + 1);
            list.remove(list.size() - 1);
        }
    }
}
