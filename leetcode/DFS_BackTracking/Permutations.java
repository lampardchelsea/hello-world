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

// Why we are doing backtracking ?
// Refer to
// https://leetcode.com/problems/permutations/discuss/18239/A-general-approach-to-backtracking-questions-in-Java-(Subsets-Permutations-Combination-Sum-Palindrome-Partioning)/260832
// cuz java "pass by reference", after you pass "tempList" in the recursion, the "tempList" are changed, 
// when go back we must undo the change. For example, [] -> [1], we have to remove to undo the add behavior [1] -> []. 
// Then we can [] -> [2]. Without remove, it will be [1] -> [1, 2]

// How to generate all permutations by backtracking ?
// Refer to
// https://leetcode.com/problems/permutations/discuss/18239/A-general-approach-to-backtracking-questions-in-Java-(Subsets-Permutations-Combination-Sum-Palindrome-Partioning)/18291
// To generate all possible permutations, we need to remove the least recently added element while we are going up the recursive call stack.
// In the first iteration of the for loop we add all permutations, that start with nums[0]. Then, before we can begin building all permutations 
// starting with nums[1], we need to clear the tempList (which currently contains permutations from the first iteration of the for loop) - that's 
// exactly what tempList.remove(tempList.size() - 1) line does.


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


// Solution 2: No need for startIndex
// Refer to
// https://leetcode.com/problems/subsets/discuss/27281/A-general-approach-to-backtracking-questions-in-Java-(Subsets-Permutations-Combination-Sum-Palindrome-Partitioning)
class Solution {
    public List<List<Integer>> permute(int[] nums) {
        List<List<Integer>> result = new ArrayList<List<Integer>>();
        helper(nums, result, new ArrayList<Integer>());
        return result;
    }
    
    private void helper(int[] nums, List<List<Integer>> result, List<Integer> list) {
        if(list.size() == nums.length) {
            result.add(new ArrayList<Integer>(list));
        }
        // Every time it should start from index 0 but need to check
        // if current value already in list
        for(int i = 0; i < nums.length; i++) {
            if(list.contains(nums[i])) {
                continue;
            }
            list.add(nums[i]);
            helper(nums, result, list);
            list.remove(list.size() - 1);
        }
    }
}
