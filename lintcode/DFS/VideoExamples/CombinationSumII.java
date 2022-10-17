/**
 * Refer to
 * https://leetcode.com/problems/combination-sum-ii/#/description
 *  Given a collection of candidate numbers (C) and a target number (T), 
 *  find all unique combinations in C where the candidate numbers sums to T.
 *  
 *  Each number in C may only be used once in the combination.
	Note:
	
	    All numbers (including target) will be positive integers.
	    The solution set must not contain duplicate combinations.
	
	For example, given candidate set [10, 1, 2, 7, 6, 1, 5] and target 8,
	A solution set is:
	
	[
	  [1, 7],
	  [1, 2, 5],
	  [2, 6],
	  [1, 1, 6]
	]
 * 
 * Different than Combination Sum I is given candidate set contains duplicates like '1' in example
 * 
 * Solution
 * https://discuss.leetcode.com/topic/46159/a-general-approach-to-backtracking-questions-in-java-subsets-permutations-combination-sum-palindrome-partitioning/2
*/
class Solution {
    public List<List<Integer>> combinationSum2(int[] candidates, int target) {
        List<List<Integer>> result = new ArrayList<List<Integer>>();
        if(target < 0 || candidates == null || candidates.length == 0) {
            return result;
        }
        List<Integer> combination = new ArrayList<Integer>();
        // Must sort first in case of skip duplicates later
        Arrays.sort(candidates);
        helper(candidates, target, result, combination, 0);
        return result;
    }
    
    private void helper(int[] candidates, int remain, List<List<Integer>> result, List<Integer> combination, int startIndex) {
        if(remain < 0) {
            return;
        } else if(remain == 0) {
            result.add(new ArrayList<Integer>(combination));
        } else {
            for(int i = startIndex; i < candidates.length; i++) {
                // skip duplicates
                if(i > startIndex && candidates[i] == candidates[i - 1]) {
                    continue;
                }
                combination.add(candidates[i]);
                helper(candidates, remain - candidates[i], result, combination, i + 1);
                combination.remove(combination.size() - 1);
            }
        }
    }
}

// New try with comprehensive detail explaination:
// Refer to
// https://www.youtube.com/watch?v=RSatA4uVBDQ
// 花花酱 LeetCode 40. Combination Sum II - 刷题找工作 EP88
class Solution {
    public List<List<Integer>> combinationSum2(int[] candidates, int target) {
        List<List<Integer>> result = new ArrayList<List<Integer>>();
        if(candidates == null || candidates.length == 0) {
            return result;
        }
        Arrays.sort(candidates);
        // Why we need to sort ? Because we need to skip the duplicate
        // e.g [10,1,2,7,6,1,5] -> [1,1,2,5,6,7,10]
        // if the target is 8, you will able to use 1 at index = 0
        // or 1 at index = 1 to add with 7 to create 8
        // but in result it will generate duplicate combination as two 
        // [1 (index = 0), 7] and [1 (index = 1), 7]
        // So to make sure we don't use the 1 at index = 1, introduce
        // judgement if(i > startIndex && candidates[i] == candidates[i - 1]) -> skip
        helper(target, candidates, result, new ArrayList<Integer>(), 0);
        return result;
    }
    
    private void helper(int remain, int[] candidates, List<List<Integer>> result, List<Integer> list, int startIndex) {
        if(remain < 0) {
            return;
        }
        if(remain == 0) {
            result.add(new ArrayList<Integer>(list));
        }
        for(int i = startIndex; i < candidates.length; i++) {
            if(i > startIndex && candidates[i] == candidates[i - 1]) {
                continue;
            }
            if(remain >= candidates[i]) {
                list.add(candidates[i]);
                // i + 1 to make sure each element only use once
                helper(remain - candidates[i], candidates, result, list, i + 1);
                list.remove(list.size() - 1);
            }
        }
    }
}

// Wrong way to compuate a combination but using permutation style
// Because that's the way for computing Permutation, since change the order will be recognize as different solution
// e.g if target is 8, [2,6] and [6,2] is one combination but two permutation 
// Refer to 
// https://github.com/lampardchelsea/hello-world/blob/master/leetcode/DFS_BackTracking/PermutationsII.java
// https://leetcode.com/problems/permutations-ii/
class Solution {
    public List<List<Integer>> combinationSum2(int[] candidates, int target) {
        List<List<Integer>> result = new ArrayList<List<Integer>>();
        Arrays.sort(candidates);
        boolean[] visited = new boolean[candidates.length];
        helper(result, candidates, target, new ArrayList<Integer>(), visited);
        return result;
    }
    
    private void helper(List<List<Integer>> result, int[] candidates, int remain, List<Integer> list, boolean[] visited) {
        if(remain < 0) {
            return;
        }
        if(remain == 0) {
            result.add(new ArrayList<Integer>(list));
        }
        for(int i = 0; i < candidates.length; i++) {
            if(visited[i] || (i > 0 && !visited[i - 1] && candidates[i] == candidates[i - 1])) {
                continue;
            }
            list.add(candidates[i]);
            visited[i] = true;
            helper(result, candidates, remain - candidates[i], list, visited);
            list.remove(list.size() - 1);
            visited[i] = false;
        }
    }
}




















https://leetcode.com/problems/combination-sum-ii/

Given a collection of candidate numbers (candidates) and a target number (target), find all unique combinations in candidates where the candidate numbers sum to target.

Each number in candidates may only be used once in the combination.

Note: The solution set must not contain duplicate combinations.

Example 1:
```
Input: candidates = [10,1,2,7,6,1,5], target = 8
Output: 
[
[1,1,6],
[1,2,5],
[1,7],
[2,6]
]
```

Example 2:
```
Input: candidates = [2,5,2,1,2], target = 5
Output: 
[
[1,2,2],
[5]
]
```

Constraints:
- 1 <= candidates.length <= 100
- 1 <= candidates[i] <= 50
- 1 <= target <= 30
---
Attempt 1: 2022-10-13

Solution 1: Backtracking style 1 (10min)
```
class Solution { 
    public List<List<Integer>> combinationSum2(int[] candidates, int target) { 
        List<List<Integer>> result = new ArrayList<List<Integer>>(); 
        Arrays.sort(candidates); 
        helper(target, candidates, result, new ArrayList<Integer>(), 0); 
        return result; 
    } 
     
    private void helper(int target, int[] candidates, List<List<Integer>> result, List<Integer> tmp, int index) { 
        if(target < 0) { 
            return; 
        } 
        if(target == 0) { 
            result.add(new ArrayList<Integer>(tmp)); 
            return; 
        } 
        for(int i = index; i < candidates.length; i++) { 
            if(i > index && candidates[i] == candidates[i - 1]) { 
                continue; 
            } 
            tmp.add(candidates[i]); 
            helper(target - candidates[i], candidates, result, tmp, i + 1); 
            tmp.remove(tmp.size() - 1); 
        } 
    } 
}
```

Video explain why and how to skip duplicate elements only on particular "Not pick" branch 
Combination Sum II - Backtracking - Leetcode 40 - Python
https://www.youtube.com/watch?v=rSA3t6BDDwgExample for candidates={10,1,2,7,6,1}, target=8, the for loop backtracking style 1 final status as below:


Another video explain 
LeetCode Tutorial 40. Combination Sum II
https://www.youtube.com/watch?v=j9_qWJClp64Example for candidates={1,3,2,1}, target=3, the for loop backtracking style 1 final status as below:


What's the meaning of if (i > cur && candidates[i] == candidates[i-1]) continue;?
https://leetcode.com/problems/combination-sum-ii/discuss/16861/Java-solution-using-dfs-easy-understand/977097


---
Solution 2: Backtracking style 2 (10min)

Keep the same style as L90/P11.2 Subsets II to skip duplicate elements only on particular "Not pick" branch

For why skip only on "Not pick" branch, refer L90/P11.2 Subsets II
Video explain why and how to skip duplicate elements only on particular "Not pick" branch 
Subsets II - Backtracking - Leetcode 90 - Python
https://www.youtube.com/watch?v=Vn2v6ajA7U0
Correct solution 2.1 with local variable 'i' to skip duplicate elements on particular "Not pick" branch, since go with "Not pick" branch before "Pick" branch, if no separate variable 'i' to isolate  skip only on "Not pick" branch and not on "Pick" branch, skip will impact "Pick" branch accidently
```
class Solution { 
    public List<List<Integer>> combinationSum2(int[] candidates, int target) { 
        List<List<Integer>> result = new ArrayList<List<Integer>>(); 
        Arrays.sort(candidates); 
        helper(target, candidates, result, new ArrayList<Integer>(), 0); 
        return result; 
    } 
     
    private void helper(int target, int[] candidates, List<List<Integer>> result, List<Integer> tmp, int index) { 
        if(target == 0) { 
            result.add(new ArrayList<Integer>(tmp)); 
            return; 
        } 
        if(target < 0 || index >= candidates.length) { 
            return; 
        } 
        // Not pick 
        int i = index; 
        while(i + 1 < candidates.length && candidates[i] == candidates[i + 1]) { 
            i++; 
        } 
        helper(target, candidates, result, tmp, i + 1); 
        // Pick 
        tmp.add(candidates[index]); 
        helper(target - candidates[index], candidates, result, tmp, index + 1); 
        tmp.remove(tmp.size() - 1); 
    } 
}
```

How the skip duplicate elements  on "Not pick" branch prune branches ?
Note: Yellow highlighted branches are the remain branches, all other branches pruned




Above snapshots based on not skip duplicate elements Backtracking style 2 recursion step by step (to view stretch on Notepad++)
```
candidates={1,2,2,2,5},target=5(tg5),index=0(idx0)  
branch ending condition: (1)index >=5 -> idx5 OR (2)target < 0 -> tg(<0)   
combination found: target == 0(tg0)  
---------------------------------------------------------------------------------  
What will happen if not skip duplicate elements Backtracking style 2 recursion step by step (to view stretch on Notepad++) ?  
(1) tg0 -> one {5} on not take 1 branch, three {1,2,2} on take 1 branch 
(2) Both not take and take certain element branch needs skip duplicates as we can see duplicate subtrees happen on both not take and take branch, so no need to create local variable 'i' to isolate skip duplicates process only on not take element branch like L90 Backtracking style 2
---------------------------------------------------------------------------------  
                                                                                                                                                                                                                 { }tg5,idx0                 
                                                                                                                /nt1                                                                                                                                                                                                 \t1  
                                                                                                            { }tg5,idx1                                                                                                                                                                                              {1}tg4,idx1  
                                                   /nt2                                                                                                              \t2                                                                                                  /nt2                                                                         \t2  
                                              { }tg5,idx2                                                                                                            {2}tg3,idx2                                                                                       {1}tg4,idx2                                                                      {1,2}tg2,idx2  
                      /nt2                                                  \t2	                                                    /nt2                                                           \t2                                          /nt2                                                    \t2                                      /nt2             \t2  
                    { }tg5,idx3                                             {2}tg3,idx3	                                          {2}tg3,idx3                                                  {2,2}tg1,idx3                                {1}tg4,idx3                                             {1,2}tg2,idx3                         {1,2}tg2,idx3       {1,2,2}tg0,idx3  
           /nt2                \t2                               /nt2                   \t2                              /nt2                     \t2                                /nt2                 \t2                     /nt2                   \t2                                  /nt2                \t2                  /nt2      \t2  
       { }tg5,idx4             {2}tg3,idx4                   {2}tg3,idx4               {2,2}tg1,idx4                   {2}tg3,idx4                {2,2}tg1,idx4                    {2,2}tg1,idx4         {2,2,2}tg-1,idx4       {1}tg4,idx4              {1,2}tg2,idx4                    {1,2}tg2,idx4     {1,2,2}tg0,idx4     {1,2}tg2,idx4 {1,2,2}tg0,idx4  
    /nt5     \t5            /nt5     \t5                 /nt5     \t5               /nt5     \t5                    /nt5    \t5                 /nt5       \t5                /nt5         \t5                              /nt5    \t5	            /nt5         \t5                /nt5        \t5	                         /nt5      \t5  
{ }tg5,idx5	{5}tg0,idx5	{2}tg3,idx5	{2,5}tg-2,idx5 {2}tg3,idx5 {2,5}tg-2,idx5 {2,2}tg1,idx5 {2,2,5}tg-4,idx5  {2}tg3,idx5 {2,5}tg-2,idx5  {2,2}tg1,idx5 {2,2,5}tg-4,idx5  {2,2}tg1,idx5	{2,2,5}tg-4,idx5             {1}tg4,idx5 {1,5}tg-1,idx5	{1,2}tg2,idx5 {1,2,5}tg-3,idx5   {1,2}tg2,idx5 {1,2,5}tg-3,idx5       {1,2}tg2,idx5 {1,2,5}tg-3,idx5
```

Refer to
https://leetcode.com/problems/combination-sum-ii/discuss/1671782/C%2B%2B-100-backtrack
```
class Solution { 
public: 
    vector<vector<int>> combinationSum2(vector<int>& candidates, int target) { 
        vector<vector<int>> ans; 
        vector<int> curr; 
         
        sort(candidates.begin(), candidates.end()); 
        backtrack(candidates, target, ans, curr, 0); 
        return ans; 
    } 
    void backtrack(vector<int>& candidates, int target, vector<vector<int>>& ans, vector<int>& curr, int index) { 
        if(target == 0) { 
            ans.push_back(curr); 
            return; 
        } 
        if(index >= candidates.size()) return; 
        if(target >= candidates[index]) { 
            curr.push_back(candidates[index]); 
            backtrack(candidates, target - candidates[index], ans, curr, index + 1); 
            curr.pop_back(); 
            while(index + 1 < candidates.size() && candidates[index + 1] == candidates[index]) ++index; 
            backtrack(candidates, target, ans, curr, index + 1); 
        } 
    } 
};
```

Correct solution 2.2, same as 2.1, different style on how to skip duplicate elements
```
class Solution { 
    public List<List<Integer>> combinationSum2(int[] candidates, int target) { 
        List<List<Integer>> result = new ArrayList<List<Integer>>(); 
        Arrays.sort(candidates); 
        helper(target, candidates, result, new ArrayList<Integer>(), 0); 
        return result; 
    } 
     
    private void helper(int target, int[] candidates, List<List<Integer>> result, List<Integer> tmp, int index) { 
        if(target == 0) { 
            result.add(new ArrayList<Integer>(tmp)); 
            return; 
        } 
        if(target < 0 || index >= candidates.length) { 
            return; 
        } 
        // Not pick 
        int i = index; 
        while(i < candidates.length && candidates[i] == candidates[index]) { 
            i++; 
        } 
        helper(target, candidates, result, tmp, i); 
        // Pick 
        tmp.add(candidates[index]); 
        helper(target - candidates[index], candidates, result, tmp, index + 1); 
        tmp.remove(tmp.size() - 1); 
    } 
}
```

Refer to
https://leetcode.com/problems/subsets-ii/discuss/169226/Java-Two-Way-of-Recursive-thinking
```
class Solution { 
    public List<List<Integer>> subsetsWithDup(int[] nums) { 
        Arrays.sort(nums); 
        List<List<Integer>> res = new ArrayList<>(); 
        helper(res,new ArrayList<>(),nums,0,false); 
        return res; 
    } 
     
    public void helper(List<List<Integer>> res, List<Integer> ls, int[] nums, int pos, boolean choosePre) { 
        if(pos==nums.length) { 
            res.add(new ArrayList<>(ls)); 
            return; 
        } 
        helper(res,ls,nums,pos+1,false); 
        if(pos>=1&&nums[pos]==nums[pos-1]&&!choosePre) return; 
        ls.add(nums[pos]); 
        helper(res,ls,nums,pos+1,true); 
        ls.remove(ls.size()-1); 
    } 
}
```

Different styles to skip duplicate elements in correct solution 2.1 and 2.2?
```
e.g 
For sorted array nums={1,2,2,2,5}, index=1, all duplicate '2' stored continuously in array 
------------------------------------- 
For solution 2.1
int i = index; 
while(i < nums.length && nums[i] == nums[index]) {i++;} 
helper(nums, result, tmp, i); 
=> while loop ending when i=4, nums[4]=5 != nums[1]=2, not pick up branch skip all duplicate 2 and start from 5 requires pass i(=4) to next recursion 
------------------------------------- 
For solution 2.2
int i = index; 
while(i + 1 < nums.length && nums[i] == nums[i + 1]) {i++;} 
helper(nums, result, tmp, i + 1); 
=> while loop ending when i=3, nums[3]=2 != nums[4]=5, not pick up branch skip all duplicate 2 and start from 5 requires pass i + 1(=4) to next recursion
```

Also switch order of "Not pick" and "Pick" doesn't matter when have a local variable 'i' to handle skip duplicate elements on "Not pick" branch only, but it matters if no local variable 'i',  then "Pick" branch must comes before "Not pick" branch because if not comes this order, "Not pick" branch update 'index' will impact "Pick" branch who comes later, without local variable 'i' solution refer to 2.3
```
class Solution { 
    public List<List<Integer>> combinationSum2(int[] candidates, int target) { 
        List<List<Integer>> result = new ArrayList<List<Integer>>(); 
        Arrays.sort(candidates); 
        helper(target, candidates, result, new ArrayList<Integer>(), 0); 
        return result; 
    } 
     
    private void helper(int target, int[] candidates, List<List<Integer>> result, List<Integer> tmp, int index) { 
        if(target == 0) { 
            result.add(new ArrayList<Integer>(tmp)); 
            return; 
        } 
        if(target < 0 || index >= candidates.length) { 
            return; 
        }
        // If have a local variable 'i' to handle skip duplicate elements on "Not pick" branch only, 
        // we can switch order of "Pick" and "Not pick" branch
        // Pick 
        tmp.add(candidates[index]); 
        helper(target - candidates[index], candidates, result, tmp, index + 1); 
        tmp.remove(tmp.size() - 1); 
        // Not pick 
        int i = index; 
        while(i < candidates.length && candidates[i] == candidates[index]) { 
            i++; 
        } 
        helper(target, candidates, result, tmp, i); 
    } 
}
```

Correct solution 2.3 without local variable 'i' to skip duplicate elements on particular "Not pick" branch, since go with "Pick" branch before "Not pick" branch, even no separate variable 'i' to isolate, skip will only impact "Not pick" branch
```
class Solution { 
    public List<List<Integer>> combinationSum2(int[] candidates, int target) { 
        List<List<Integer>> result = new ArrayList<List<Integer>>(); 
        Arrays.sort(candidates); 
        helper(target, candidates, result, new ArrayList<Integer>(), 0); 
        return result; 
    } 
     
    private void helper(int target, int[] candidates, List<List<Integer>> result, List<Integer> tmp, int index) { 
        if(target == 0) { 
            result.add(new ArrayList<Integer>(tmp)); 
            return; 
        } 
        if(target < 0 || index >= candidates.length) { 
            return; 
        } 
        // Without local variable 'i', "Pick" branch must comes before "Not pick" branch
        // Pick 
        tmp.add(candidates[index]); 
        helper(target - candidates[index], candidates, result, tmp, index + 1); 
        tmp.remove(tmp.size() - 1); 
        // Not pick 
        while(index + 1 < candidates.length && candidates[index] == candidates[index + 1]) { 
            index++; 
        } 
        helper(target, candidates, result, tmp, index + 1); 
    } 
}
```

---
Questions:
1. Why "target == 0 result.add, return" must come before "target < 0 || index >= candidates.length" ?

Wrong solution (based on Correction solution 2.3):
```
class Solution {
    public List<List<Integer>> combinationSum2(int[] candidates, int target) {
        List<List<Integer>> result = new ArrayList<List<Integer>>();
        Arrays.sort(candidates);
        helper(target, candidates, result, new ArrayList<Integer>(), 0);
        return result;
    }
    
    private void helper(int target, int[] candidates, List<List<Integer>> result, List<Integer> tmp, int index) {
        // Return when "index >= candidates.length" first before "target == 0" when get one combination is wrong
        if(target < 0 || index >= candidates.length) {
            return;
        }
        if(target == 0) {
            result.add(new ArrayList<Integer>(tmp));
            return;
        }
        // Pick
        tmp.add(candidates[index]);
        helper(target - candidates[index], candidates, result, tmp, index + 1);
        tmp.remove(tmp.size() - 1);
        // Not pick
        while(index + 1 < candidates.length && candidates[index] == candidates[index + 1]) {
            index++;
        }
        helper(target, candidates, result, tmp, index + 1);
    }
}

Test out by:
Input: [2,5,2,1,2], 5 
Output: [[1,2,2]] 
Expected: [[1,2,2],[5]]
```
e.g. 
candidates={2,5,2,1,2} -> sorted candidates={1,2,2,2,5},target=5(tg5),index=0(idx0)
```
/nt1                                                                                                                                                                                                 \t1 
                                                                                                            { }tg5,idx1                                                                                                                                                                                              {1}tg4,idx1 
                                                   /nt2                                                                                                              \t2                                                                                                  /nt2                                                                         \t2 
                                              { }tg5,idx2                                                                                                            {2}tg3,idx2                                                                                       {1}tg4,idx2                                                                      {1,2}tg2,idx2 
                      /nt2                                                  \t2	                                                    /nt2                                                           \t2                                          /nt2                                                    \t2                                      /nt2             \t2 
                    { }tg5,idx3                                             {2}tg3,idx3	                                          {2}tg3,idx3                                                  {2,2}tg1,idx3                                {1}tg4,idx3                                             {1,2}tg2,idx3                         {1,2}tg2,idx3       {1,2,2}tg0,idx3 
           /nt2                \t2                               /nt2                   \t2                              /nt2                     \t2                                /nt2                 \t2                     /nt2                   \t2                                  /nt2                \t2                  /nt2      \t2 
       { }tg5,idx4             {2}tg3,idx4                   {2}tg3,idx4               {2,2}tg1,idx4                   {2}tg3,idx4                {2,2}tg1,idx4                    {2,2}tg1,idx4         {2,2,2}tg-1,idx4       {1}tg4,idx4              {1,2}tg2,idx4                    {1,2}tg2,idx4     {1,2,2}tg0,idx4     {1,2}tg2,idx4 {1,2,2}tg0,idx4 
    /nt5     \t5            /nt5     \t5                 /nt5     \t5               /nt5     \t5                    /nt5    \t5                 /nt5       \t5                /nt5         \t5                              /nt5    \t5	            /nt5         \t5                /nt5        \t5	                         /nt5      \t5 
{ }tg5,idx5	{5}tg0,idx5	{2}tg3,idx5	{2,5}tg-2,idx5 {2}tg3,idx5 {2,5}tg-2,idx5 {2,2}tg1,idx5 {2,2,5}tg-4,idx5  {2}tg3,idx5 {2,5}tg-2,idx5  {2,2}tg1,idx5 {2,2,5}tg-4,idx5  {2,2}tg1,idx5	{2,2,5}tg-4,idx5             {1}tg4,idx5 {1,5}tg-1,idx5	{1,2}tg2,idx5 {1,2,5}tg-3,idx5   {1,2}tg2,idx5 {1,2,5}tg-3,idx5       {1,2}tg2,idx5 {1,2,5}tg-3,idx5
```


{5}tg0,idx5 missing explain all, when index == candidates.length = 5, if we directly return based on "index >= candidates.length" like below
```
        if(target < 0 || index >= candidates.length) {
            return;
        }
        if(target == 0) {
            result.add(new ArrayList<Integer>(tmp));
            return;
        }
```
We will miss to add solution {5} into result for target == 0, one solution is switching the order of above two base cases which means when target == 0 we find a solution and avoid check on index against candidates.length.
The hard part is we cannot remove check on index against candidates.length, it will cause index out of boundary issue. The similar problem L90.Subsets II doesn't have this trouble, since it not check target, when index >= nums.length, means the one combination found.

2. What will happen if not skip duplicate elements Backtracking style 2 recursion step by step (to view stretch on Notepad++) ?
```
candidates={1,2,2,2,5},target=5(tg5),index=0(idx0) 
branch ending condition: (1)index >=5 -> idx5 OR (2)target < 0 -> tg(<0)  
combination found: target == 0(tg0) 
--------------------------------------------------------------------------------- 
What will happen if not skip duplicate elements Backtracking style 2 recursion step by step (to view stretch on Notepad++) ? 
(1) tg0 -> one {5} on not take 1 branch, three {1,2,2} on take 1 branch
(2) Both not take and take certain element branch needs skip duplicates as we can see duplicate subtrees happen on both not take and take branch, so no need to create local variable 'i' to isolate skip duplicates process only on not take element branch like L90 Backtracking style 2
--------------------------------------------------------------------------------- 
                                                                                                                                                                                                                 { }tg5,idx0                
                                                                                                                /nt1                                                                                                                                                                                                 \t1 
                                                                                                            { }tg5,idx1                                                                                                                                                                                              {1}tg4,idx1 
                                                   /nt2                                                                                                              \t2                                                                                                  /nt2                                                                         \t2 
                                              { }tg5,idx2                                                                                                            {2}tg3,idx2                                                                                       {1}tg4,idx2                                                                      {1,2}tg2,idx2 
                      /nt2                                                  \t2	                                                    /nt2                                                           \t2                                          /nt2                                                    \t2                                      /nt2             \t2 
                    { }tg5,idx3                                             {2}tg3,idx3	                                          {2}tg3,idx3                                                  {2,2}tg1,idx3                                {1}tg4,idx3                                             {1,2}tg2,idx3                         {1,2}tg2,idx3       {1,2,2}tg0,idx3 
           /nt2                \t2                               /nt2                   \t2                              /nt2                     \t2                                /nt2                 \t2                     /nt2                   \t2                                  /nt2                \t2                  /nt2      \t2 
       { }tg5,idx4             {2}tg3,idx4                   {2}tg3,idx4               {2,2}tg1,idx4                   {2}tg3,idx4                {2,2}tg1,idx4                    {2,2}tg1,idx4         {2,2,2}tg-1,idx4       {1}tg4,idx4              {1,2}tg2,idx4                    {1,2}tg2,idx4     {1,2,2}tg0,idx4     {1,2}tg2,idx4 {1,2,2}tg0,idx4 
    /nt5     \t5            /nt5     \t5                 /nt5     \t5               /nt5     \t5                    /nt5    \t5                 /nt5       \t5                /nt5         \t5                              /nt5    \t5	            /nt5         \t5                /nt5        \t5	                         /nt5      \t5 
{ }tg5,idx5	{5}tg0,idx5	{2}tg3,idx5	{2,5}tg-2,idx5 {2}tg3,idx5 {2,5}tg-2,idx5 {2,2}tg1,idx5 {2,2,5}tg-4,idx5  {2}tg3,idx5 {2,5}tg-2,idx5  {2,2}tg1,idx5 {2,2,5}tg-4,idx5  {2,2}tg1,idx5	{2,2,5}tg-4,idx5             {1}tg4,idx5 {1,5}tg-1,idx5	{1,2}tg2,idx5 {1,2,5}tg-3,idx5   {1,2}tg2,idx5 {1,2,5}tg-3,idx5       {1,2}tg2,idx5 {1,2,5}tg-3,idx5
```
