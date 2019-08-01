/**
 Refer to
 https://leetcode.com/problems/permutations/
 Given a collection of distinct integers, return all possible permutations.

Example:
Input: [1,2,3]
Output:
[
  [1,2,3],
  [1,3,2],
  [2,1,3],
  [2,3,1],
  [3,1,2],
  [3,2,1]
]
*/

// Solution 1:
class Solution {
    public List<List<Integer>> permute(int[] nums) {
        List<List<Integer>> result = new ArrayList<List<Integer>>();
        helper(nums, result, new ArrayList<Integer>(), 0);
        return result;
    }
    
    private void helper(int[] nums, List<List<Integer>> result, List<Integer> list, int startIndex) {
        if(list.size() == nums.length) {
            result.add(new ArrayList<Integer>(list));
        }        
        for(int i = startIndex; i < nums.length; i++) {
            if(list.contains(nums[i])) {
                continue;
            }
            list.add(nums[i]);
            helper(nums, result, list, startIndex);
            list.remove(list.size() - 1);
        }
    }
}



// Solution 2:
// Refer to
// https://leetcode.com/problems/subsets/discuss/27281/A-general-approach-to-backtracking-questions-in-Java-(Subsets-Permutations-Combination-Sum-Palindrome-Partitioning)

