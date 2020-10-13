/**
 Refer to
 https://leetcode.com/problems/increasing-subsequences/
 Given an integer array, your task is to find all the different possible increasing subsequences of the given array, 
 and the length of an increasing subsequence should be at least 2.

Example:
Input: [4, 6, 7, 7]
Output: [[4, 6], [4, 7], [4, 6, 7], [4, 6, 7, 7], [6, 7], [6, 7, 7], [7,7], [4,7,7]]
 
Constraints:
The length of the given array will not exceed 15.
The range of integer in the given array is [-100,100].
The given array may contain duplicates, and two equal integers should also be considered as a special case of increasing sequence.
*/

// Solution 1: DFS + Backtracking
// Refer to
// https://leetcode.com/problems/increasing-subsequences/discuss/97147/Java-solution-beats-100
// https://leetcode.com/problems/increasing-subsequences/discuss/97130/Java-20-lines-backtracking-solution-using-set-beats-100./101617
/**
A possible improvement: instead of using a global set to remove duplication in the final results, we can maintain a local set at each step. 
The principle is that at each step, a new value can only be picked once.

The advantage of a local set is that it can filter out the potential repetitions just at the beginning instead of at the end of a sub-sequence 
building. For example, [1, 1, 9, 3, 6]. With a global set, we have to filter all the sequences starting at the 2nd 1 since they will certainly 
duplicate with the results beginning with the 1st 1. However, with a local set, at the first step, we will only choose the 1st 1 for sequence 
building and the 2nd 1 is excluded just at the first step.

Of course a local set at each step will lead to extra costs. However, I think it can improve the efficiency in general, especially for an 
array with many repetitions, such as [1, 1, 1, 1, 1, 1, 3].
*/
class Solution {
    public List<List<Integer>> findSubsequences(int[] nums) {
        List<List<Integer>> result = new ArrayList<List<Integer>>();
        // Since its a fixed order array --> Do not use Arrays.sort(nums) to change the order like what we did for Subsets problems
        // Refer to
        // https://github.com/lampardchelsea/hello-world/blob/master/lintcode/DFS/VideoExamples/SubsetsII.java
        helper(0, nums, result, new ArrayList<Integer>());
        return result;
    }
    
    private void helper(int index, int[] nums, List<List<Integer>> result, List<Integer> list) {
        if(list.size() >= 2) {
            result.add(new ArrayList<Integer>(list));
        }
        // Local set to filter possible duplicates
        /**
         e.g If not adding local set to filter out duplicates, duplicate combination will generate
         as 4 with first 7 is [4,7], 4 with second 7 is also [4,7], [4,7] happen twice
         Input: [4,6,7,7]
         Output: [[4,6],[4,6,7],[4,6,7,7],[4,6,7],[4,7],[4,7,7],[4,7],[6,7],[6,7,7],[6,7],[7,7]]
         Expected: [[4,6],[4,6,7],[4,6,7,7],[4,7],[4,7,7],[6,7],[6,7,7],[7,7]]
        */
        Set<Integer> visited = new HashSet<Integer>();
        for(int i = index; i < nums.length; i++) {
            if(!visited.contains(nums[i]) && (list.size() == 0 || nums[i] >= list.get(list.size() - 1))) {
                visited.add(nums[i]);
                list.add(nums[i]);
                helper(i + 1, nums, result, list);
                list.remove(list.size() - 1);
            }
        }
    }
}
