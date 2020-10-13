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
 * Solution
 * https://www.jiuzhang.com/solutions/subsets-ii/
 * https://discuss.leetcode.com/topic/46159/a-general-approach-to-backtracking-questions-in-java-subsets-permutations-combination-sum-palindrome-partitioning
 */
class Solution {
    public List<List<Integer>> subsetsWithDup(int[] nums) {
        if(nums == null) {
            return null;
        }
        List<List<Integer>> result = new ArrayList<List<Integer>>();
        if(nums.length == 0) {
            return result;
        }
        List<Integer> list = new ArrayList<Integer>();
        // Before calling DFS, must sort the nums, then duplicate
        // check as 'nums[i] == nums[i - 1]' applicable
        Arrays.sort(nums);
        helper(nums, result, list, 0);
        return result;
    }
    
    private void helper(int[] nums, List<List<Integer>> result, List<Integer> list, int startIndex) {
        // Deep Copy
        result.add(new ArrayList<Integer>(list));
        for(int i = startIndex; i < nums.length; i++) {
            // Skip the duplicate number when create subset
            // Important: i > startIndex is necessary check
            // E.g if no check on i > startIndex, given
            // [1, 2, 2], will ArrayIndexOutOfBoundsException: -1
            if(i > startIndex && nums[i] == nums[i - 1]) {
                continue;
            }
            list.add(nums[i]);
            helper(nums, result, list, i + 1);
            list.remove(list.size() - 1);
        }
    }
}

// Re-work
// Refer to
// https://github.com/lampardchelsea/hello-world/blob/master/leetcode/DFS_BackTracking/IncreasingSubsequences.java
// Use the same way as 491. Increasing Subsequences
/**
A possible improvement: instead of using a global set to remove duplication in the final results, we can maintain a local set at each step. 
The principle is that at each step, a new value can only be picked once.

The advantage of a local set is that it can filter out the potential repetitions just at the beginning instead of at the end of a sub-sequence 
building. For example, [1, 1, 9, 3, 6]. With a global set, we have to filter all the sequences starting at the 2nd 1 since they will certainly 
duplicate with the results beginning with the 1st 1. However, with a local set, at the first step, we will only choose the 1st 1 for sequence 
building and the 2nd 1 is excluded just at the first step.

Of course a local set at each step will lead to extra costs. However, I think it can improve the efficiency in general, especially for an 
array with many repetitions, such as [1, 1, 1, 1, 1, 1, 3].

Create a local set to filter possible duplicates
e.g If not adding local set to filter out duplicates, duplicate combination will generate
as 4 with first 7 is [4,7], 4 with second 7 is also [4,7], [4,7] happen twice
Input: [4,6,7,7]
Output: [[4,6],[4,6,7],[4,6,7,7],[4,6,7],[4,7],[4,7,7],[4,7],[6,7],[6,7,7],[6,7],[7,7]]
Expected: [[4,6],[4,6,7],[4,6,7,7],[4,7],[4,7,7],[6,7],[6,7,7],[7,7]]
After adding set, in each level, we will filter duplicate values after first present,
like here, we will only add first 7 for combination [4,7], the second 7 will filter out,
but this is just a local set, when process to next level, we will create a new set and
it will not block to add same value again but in next level, like here after [4,7] the
next level we can add 7 again as [4,7,7]
*/
class Solution {
    public List<List<Integer>> subsetsWithDup(int[] nums) {
        List<List<Integer>> result = new ArrayList<List<Integer>>();
        // Add sort to pass test since all test cases order matters,
        // but this requirement not explicitly mentioned in problem,
        // so actually no need
        Arrays.sort(nums);
        helper(0, nums, result, new ArrayList<Integer>());
        return result;
    }
    
    private void helper(int index, int[] nums, List<List<Integer>> result, List<Integer> list) {
        result.add(new ArrayList<Integer>(list));
        // The alternative way we don't use visited array + nums[i] == nums[i - 1]
        // to judge if duplicate, we just use hashset
        Set<Integer> visited = new HashSet<Integer>();
        for(int i = index; i < nums.length; i++) {
            if(!visited.contains(nums[i])) {
                visited.add(nums[i]);
                list.add(nums[i]);
                helper(i + 1, nums, result, list);
                list.remove(list.size() - 1);
            }
        }
    }
}
