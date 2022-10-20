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
