import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
 * Solution
 * https://segmentfault.com/a/1190000003743112
 * 深度优先搜索
 * 复杂度
 * 时间 O(N!) 空间 O(N) 递归栈空间
 * 思路
 * 这题和I的区别在于同一个数只能取一次，比如数组中只有3个1，那结果中也最多只有3个1，而且结果也不能重复。
 * 所以我们在递归时首先要把下标加1，这样下轮搜索中就排除了自己。其次，对一个数完成了全部深度优先搜索后，
 * 比如对1完成了搜索，那么我们要把后面的1都跳过去。当然，跳过只是针对本轮搜索的，在对第一个1的下一轮的
 * 搜索中，我们还是可以加上第二个1。只是我们不能再以第二个1开头了而已。为了能连续跳过重复的数，这里我们
 * 必须先排序。
 */
public class CombinationSumII {
    public List<List<Integer>> combinationSum2(int[] candidates, int target) {
    	// Must sort first to skip redundant same values
    	// e.g If given {1, 1, 1, 1} for target 3, the last '1' is redundant,
    	// the combination is only {1, 1, 1} set
    	Arrays.sort(candidates);
        List<List<Integer>> result = new ArrayList<List<Integer>>();
        helper(result, new ArrayList<Integer>(), target, candidates, 0);
        return result;
    }
    
    public void helper(List<List<Integer>> result, List<Integer> tmp, int target, int[] candidates, int i) {
        if(target < 0) {
            return;
        } else if(target == 0) {
            List<Integer> oneComb = new ArrayList<Integer>(tmp);
            result.add(oneComb);
            return;
        } else {
            for(int j = i; j < candidates.length; j++) {
                tmp.add(candidates[j]);
                // The difference than Combination Sum I is
                // increase 'j' for next recursion which disable
                // multiple times using 'nums[j]' to approach target
                helper(result, tmp, target - candidates[j], candidates, j + 1);
                tmp.remove(tmp.size() - 1);
                // Continuously skip all redundant items have same value as the item 
                // which already used in current loop for combination(Must sort first)
                // 比如对1完成了搜索，那么我们要把后面的1都跳过去。当然，跳过只是针对本轮搜索的， 在对
                // 第一个1的下一轮的搜索中，我们还是可以加上第二个1。只是我们不能再以第二个1开头了
                // 而已，因为如果以第二个1开头会部分重复以第一个1开头所得的组合。为了能连续跳过重复的数，这里我们必须先排序。
                // 实际上并不是同一条dfs的path上不能出现重复的1，比如{1，1，1}就是一个解而且dfs的path上重复了1，
                // 而是后面重复的1不能再和之前已经使用过的1出现在组合的同一个位置了
                // E.g int[] candidates = {1, 1, 1, 1}, target = 3, if not skip
                // redundant '1' for each round, will give result as 
                // {{1, 1, 1}, {1, 1, 1}, {1, 1, 1}, {1, 1, 1}}, this is wrong
                while(j < candidates.length - 1 && candidates[j + 1] == candidates[j]) {
                	j++;
                }
            }
        }
    }	
    
    public static void main(String[] args) {
    	CombinationSumII c = new CombinationSumII();
//    	int[] candidates = {10, 1, 2, 7, 6, 1, 5};
    	int[] candidates = {1, 1, 1, 1};
    	int n = 3;
    	List<List<Integer>> result = c.combinationSum2(candidates, n);
    	for(List<Integer> r : result) {
    		System.out.println("---------");
    		for(Integer i : r) {
    			System.out.println(i);
    		}
        	System.out.println("---------");
    	}
    }
}

// Re-work
// Refer to
// Best video explain:
// https://www.youtube.com/watch?v=j9_qWJClp64&feature=emb_logo --> Use [1,2,3,1] -> sort to [1,1,2,3] and target = 3 
// as example to explain why the 2nd 1 will generate duplicate combination if put it as same as 1st 1 at the first
// position of combination, because like [1,2] as a success combination the first position 1 comes from 1st 1 in
// given array is good, but if we not filter the 2nd 1 out, then if this first position 1 comes from 2nd 1 in given
// array will generate a duplicate [1,2]
// https://leetcode.com/problems/combination-sum-ii/discuss/16861/Java-solution-using-dfs-easy-understand/16652
// https://leetcode.com/problems/combination-sum-ii/discuss/16861/Java-solution-using-dfs-easy-understand/16666
class Solution {
    public List<List<Integer>> combinationSum2(int[] candidates, int target) {
        List<List<Integer>> result = new ArrayList<List<Integer>>();
        Arrays.sort(candidates);
        helper(candidates, result, target, new ArrayList<Integer>(), 0);
        return result;
    }
    
    private void helper(int[] candidates, List<List<Integer>> result, int target, List<Integer> list, int index) {
        if(target < 0) {
            return;
        }
        if(target == 0) {
            result.add(new ArrayList<Integer>(list));
            return;
        }
        for(int i = index; i < candidates.length; i++) {
            /**
            Why we need i > index && candidates[i - 1] == candidates[i] ?
            https://leetcode.com/problems/combination-sum-ii/discuss/16861/Java-solution-using-dfs-easy-understand/16652
            Search in [1, 1, 1, 2, 2] for target 4, without the expression, you will get three identical combinations:
            [1, 1, 2, 2] from index [0, 1, 3, 4] of the candidates;
            [1, 1, 2, 2] from index [0, 2, 3, 4] of the candidates;
            [1, 1, 2, 2] from index [1, 2, 3, 4] of the candidates.
            
	    https://leetcode.com/problems/combination-sum-ii/discuss/16861/Java-solution-using-dfs-easy-understand/16666
            For those who don't understand how to avoid duplicate by:
            if "(i > cur && cand[i] == cand[i-1]) continue;
            when we should skip a number? not just it's the same as previous number, but also when it's previous number haven't been added!
            i > cur means cand[i - 1] is not added to the path (you should know why if you understand the algorithm), 
            so if cand[i] == cand[i-1], then we shouldn't add cand[i].
            */
            if(i > index && candidates[i - 1] == candidates[i]) {
                continue;
            }
            list.add(candidates[i]);
            helper(candidates, result, target - candidates[i], list, i + 1);
            list.remove(list.size() - 1);
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
