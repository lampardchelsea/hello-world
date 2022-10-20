/**
 * Refer to
 * https://leetcode.com/problems/permutations/description/
 * Given a collection of distinct numbers, return all possible permutations.

    For example,
    [1,2,3] have the following permutations:
    [
      [1,2,3],
      [1,3,2],
      [2,1,3],
      [2,3,1],
      [3,1,2],
      [3,2,1]
    ]

 *
 * Solution
 * https://discuss.leetcode.com/topic/46159/a-general-approach-to-backtracking-questions-in-java-subsets-permutations-combination-sum-palindrome-partitioning
 * https://www.jiuzhang.com/solutions/permutations/
*/
public class Solution {
    public List<List<Integer>> permute(int[] nums) {
        List<List<Integer>> result = new ArrayList<List<Integer>>();
        if(nums == null || nums.length == 0) {
            return result;
        }
        List<Integer> combination = new ArrayList<Integer>();
        helper(nums, result, combination);
        return result;
    }
    
    // We can also use 'boolean[] visited' instead of usage of 'combination.contains()' check
    private void helper(int[] nums, List<List<Integer>> result, List<Integer> combination) {
        if(combination.size() == nums.length) {
            result.add(new ArrayList<Integer>(combination));
        }
        for(int i = 0; i < nums.length; i++) {
            // If element already exist, skip
            if(combination.contains(nums[i])) {
                continue;
            }
            combination.add(nums[i]);
            helper(nums, result, combination);
            combination.remove(combination.size() - 1);
        }
    }
}


/**
 * Check with boolean visited
*/
public class Solution {
    public List<List<Integer>> permute(int[] nums) {
        List<List<Integer>> result = new ArrayList<List<Integer>>();
        if(nums == null) {
            return result;
        }
        if(nums.length == 0) {
            result.add(new ArrayList<Integer>());
            return result;
        }
        List<Integer> combination = new ArrayList<Integer>();
        helper(nums, result, combination, new boolean[nums.length]);
        return result;
    }
    
    private void helper(int[] nums, List<List<Integer>> result, List<Integer> combination, boolean[] visited) {
        if(combination.size() == nums.length) {
            result.add(new ArrayList<Integer>(combination));
        }
        for(int i = 0; i < nums.length; i++) {
            if(visited[i]) {
                continue;
            }
            combination.add(nums[i]);
            visited[i] = true;
            helper(nums, result, combination, visited);
            combination.remove(combination.size() - 1);
            visited[i] = false;
        }
    }
}












https://leetcode.com/problems/permutations/

Given an array nums of distinct integers, return all the possible permutations. You can return the answer in any order.

Example 1:
```
Input: nums = [1,2,3]
Output: [[1,2,3],[1,3,2],[2,1,3],[2,3,1],[3,1,2],[3,2,1]]
```

Example 2:
```
Input: nums = [0,1]
Output: [[0,1],[1,0]]
```

Example 3:
```
Input: nums = [1]
Output: [[1]]
```

Constraints:
- 1 <= nums.length <= 6
- -10 <= nums[i] <= 10
- All the integers of nums are unique.
---
Attempt 1: 2022-10-18

Solution 1: Backtracking style 1 (10min)
```
class Solution { 
    public List<List<Integer>> permute(int[] nums) { 
        List<List<Integer>> result = new ArrayList<List<Integer>>(); 
        helper(nums, result, new ArrayList<Integer>(), 0); 
        return result; 
    } 
     
    private void helper(int[] nums, List<List<Integer>> result, List<Integer> tmp, int index) { 
        if(tmp.size() == nums.length) { 
            result.add(new ArrayList<Integer>(tmp)); 
            return; 
        } 
        for(int i = index; i < nums.length; i++) { 
            if(tmp.contains(nums[i])) { 
                continue; 
            } 
            tmp.add(nums[i]);
            // Differ than L77.Combinations statement which pass local variable 'i' 
            // plus 1('i + 1') into next recursion
            helper(nums, result, tmp, index); 
            tmp.remove(tmp.size() - 1); 
        } 
    } 
}
```

For Backtracking style 1 Tree Structure Analysis
```
Tree Structure Analysis   
e.g.   
Input: n = 3, k = 3   
Output: {{1,2,3}{1,3,2}{2,1,3}{2,3,1}{3,1,2}{3,2,1}}

                            { } 
                   /         |        \ 
                 {1}        {2}       {3} 
                /   \      /   \      /   \ 
            {1,2} {1,3} {2,1} {2,3} {3,1} {3,2} 
              |     |     |     |     |     | 
         {1,2,3}{1,3,2}{2,1,3}{2,3,1}{3,1,2}{3,2,1}
```

Refer to
https://medium.com/algorithms-and-leetcode/backtracking-e001561b9f28


The traversal and backtrack process is below


Compare to L77.Combinations Solution 1: Backtracking style 1, the critical difference is L46. Permutations Solution 1: Backtracking style 1 pass 'index' into next recursion level, whereas L77. Combinations pass local variable 'i' plus 1 as 'i + 1' into next recursion level 
```
// L77. Combination pass local variable 'i' plus 1 as 'i + 1' into next recursion level instead of passing 'index'
class Solution {  
    public List<List<Integer>> combine(int n, int k) {  
        List<List<Integer>> result = new ArrayList<List<Integer>>();  
        int[] candidates = new int[n + 1];  
        for(int i = 1; i <= n; i++) {  
            candidates[i] = i;  
        }  
        // Since range [1,n], start index not 0 but 1  
        helper(candidates, result, new ArrayList<Integer>(), k, 1);  
        return result;  
    }  
      
    private void helper(int[] candidates, List<List<Integer>> result, List<Integer> tmp, int k, int index) {  
        if(tmp.size() == k) {  
            result.add(new ArrayList<Integer>(tmp));  
            return;  
        }  
        for(int i = index; i < candidates.length; i++) {  
            tmp.add(candidates[i]);  
            helper(candidates, result, tmp, k, i + 1);  
            tmp.remove(tmp.size() - 1);  
        }  
    }  
}
```

---
Solution 2: Backtracking style 2 (10min, instead of contains() method check, use boolean array)
```
class Solution { 
    public List<List<Integer>> permute(int[] nums) { 
        List<List<Integer>> result = new ArrayList<List<Integer>>(); 
        helper(nums, result, new ArrayList<Integer>(), 0, new boolean[nums.length]); 
        return result; 
    } 
     
    private void helper(int[] nums, List<List<Integer>> result, List<Integer> tmp, int index, boolean[] visited) { 
        if(tmp.size() == nums.length) { 
            result.add(new ArrayList<Integer>(tmp)); 
            return; 
        } 
        for(int i = index; i < nums.length; i++) { 
            if(visited[i]) { 
                continue; 
            } 
            tmp.add(nums[i]); 
            visited[i] = true; 
            helper(nums, result, tmp, index, visited); 
            visited[i] = false; 
            tmp.remove(tmp.size() - 1); 
        } 
    } 
}
```

Refer to
https://leetcode.com/problems/permutations/discuss/179932/Beats-100-Java-with-Explanations

Thought
We think about a searching tree when we apply Backtracking.
```
e.g.[1, 2, 3]
    1 -2 -3
      -3 -2

    2 -1 -3
      -3 -1

    3 -1 -2
      -2 -1
```

If we exhausted the current branch, currResult.size() == nums.length, we will backtrack. To make sure each element is used once, we establish boolean[] used.Code

```
    public List<List<Integer>> permute(int[] nums) {

        if (nums == null || nums.length == 0)
            return new ArrayList<>();

        List<List<Integer>> finalResult = new ArrayList<>();
        permuteRecur(nums, finalResult, new ArrayList<>(), new boolean[nums.length]);
        return finalResult;
    }

    private void permuteRecur(int[] nums, List<List<Integer>> finalResult, List<Integer> currResult, boolean[] used) {

        if (currResult.size() == nums.length) {
            finalResult.add(new ArrayList<>(currResult));
            return;
        }

        for (int i = 0; i < nums.length; i++) {
            if (used[i])
                continue;
            currResult.add(nums[i]);
            used[i] = true;
            permuteRecur(nums, finalResult, currResult, used);
            used[i] = false;
            currResult.remove(currResult.size() - 1);
        }
    }
```

---
No "Not pick" and "Pick" branch available for this problem yet
