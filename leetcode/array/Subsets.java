import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Refer to
 * https://leetcode.com/problems/subsets/#/description
 *  Given a set of distinct integers, nums, return all possible subsets.
 *  Note: The solution set must not contain duplicate subsets.	
	For example,
	If nums = [1,2,3], a solution is:
	
	[
	  [3],
	  [1],
	  [2],
	  [1,2,3],
	  [1,3],
	  [2,3],
	  [1,2],
	  []
	]
 * 
 *  Solution 1:
 *  https://segmentfault.com/a/1190000003498803
 *  深度优先搜索
 *  复杂度
 *  时间 O(NlogN) 空间 O(N) 递归栈空间
 *  思路
 *  这道题可以转化为一个类似二叉树的深度优先搜索。二叉树的根是个空集，它的左子节点是加上第一个元素产生的集合，右子节点不加上第一个元素所产生的集合。
 *  以此类推，左子节点的左子节点是加上第二个元素，左子节点的右子节点是不加上第二个元素。而解就是这个二叉树所有的路径，我们要做的就是根据加，或者
 *  不加下一元素，来产生一个新的集合，然后继续递归直到终点。另外需要先排序以满足题目要求。
 *  注意
 *  (1) 这里要先排序，不然过不了leetcode，但实际上是不用的
 *  (2) 如果递归之前先将空集加入结果，那么递归尽头就不需要再加一次空集。反之则需要。
 *  (3) LinkedList在这里效率要高于ArrayList。
 *  (4) 新的集合要new一个新的list，防止修改引用。
 *  
 *  Solution 2:
 *  https://discuss.leetcode.com/topic/46159/a-general-approach-to-backtracking-questions-in-java-subsets-permutations-combination-sum-palindrome-partitioning
 */
public class Subsets {
	// Solution 1: DFS
    public List<List<Integer>> subsets(int[] nums) {
        List<List<Integer>> result = new ArrayList<List<Integer>>();
        // Add an empty set first
        result.add(new ArrayList<Integer>());
        helper(result, new ArrayList<Integer>(), nums, 0);
        return result;
    }
    
    public void helper(List<List<Integer>> result, List<Integer> tmp, int[] nums, int index) {
        if(index >= nums.length) {
            return;
        }
        // Logic branch-1: Not add nums[index] into current subset and its inheritance chain sets
        helper(result, tmp, nums, index + 1);
        /**
         * Be careful, create a new list in case of change on reference, as branch-2 suppose
         * add different subset than 'tmp' as branch-1
         * E.g if we don't create a new subset, keep using 'tmp' as below:
         * tmp.add(nums[index]);
         * result.add(tmp);
         * helper(result, tmp, nums, index + 1);
         * Then as iteratively change on 'tmp' by both branch-1 & 2, it will cause problem, which
         * cannot identify difference between each other and branch-2 always polluted by branch-1
         * Input:[1,2,3]
         * Output:[[],[3,2,3,1,3,2,3],[3,2,3,1,3,2,3],[3,2,3,1,3,2,3],[3,2,3,1,3,2,3],[3,2,3,1,3,2,3],[3,2,3,1,3,2,3],[3,2,3,1,3,2,3]]
         * Expected:[[],[1],[2],[1,2],[3],[1,3],[2,3],[1,2,3]] 
         */
        // Create one subset contain nums[index]
        List<Integer> oneSubset = new ArrayList<Integer>(tmp);
        oneSubset.add(nums[index]);
        result.add(oneSubset);
        // Logic branch-2: Add nums[index] into current subset and its inheritance chain sets
        helper(result, oneSubset, nums, index + 1);
    }
    
    // Solution 2: Using backtrack template
    public List<List<Integer>> subsets2(int[] nums) {
        List<List<Integer>> result = new ArrayList<List<Integer>>();
        //Arrays.sort(nums); -- > This is NOT necessary
        helper2(result, new ArrayList<Integer>(), nums, 0);
        // Add empty set
        result.add(new ArrayList<Integer>()); 
        return result;
    }
    
    public void helper2(List<List<Integer>> result, List<Integer> tmp, int[] nums, int index) {
        // Move this line into for loop require adding empty set initially before start
    	// backtrack on main method 'subsets2'
    	//result.add(new ArrayList<Integer>(tmp));
        for(int i = index; i < nums.length; i++) {
            tmp.add(nums[i]);
            // Create new ArrayList in case to avoid change on original 'tmp' reference
            result.add(new ArrayList<Integer>(tmp));
            helper(result, tmp, nums, i + 1);
            tmp.remove(tmp.size() - 1);
        }
    }
    
    
    public static void main(String[] args) {
    	int[] nums = {1, 2, 3};
    	Subsets s = new Subsets();
    	List<List<Integer>> result = s.subsets(nums);
    	for(List<Integer> a : result) {
    		System.out.println("------------");
    		for(Integer i : a) {
    			System.out.println(i);
    		}
    		System.out.println("------------");
    	}
    }
}









Attempt 1: 2022-10-7

Solution 1: Backtracking style 1
```
class Solution { 
    public List<List<Integer>> subsets(int[] nums) { 
        List<List<Integer>> result = new ArrayList<List<Integer>>(); 
        helper(nums, result, new ArrayList<Integer>(), 0); 
        return result; 
    } 
     
    private void helper(int[] nums, List<List<Integer>> result, List<Integer> tmp, int index) { 
        result.add(new ArrayList<Integer>(tmp)); 
        for(int i = index; i < nums.length; i++) { 
            tmp.add(nums[i]); 
            helper(nums, result, tmp, i + 1); 
            tmp.remove(tmp.size() - 1); 
        } 
    } 
}
```

Refer to
https://leetcode.com/problems/subsets/solution/

Power set is all possible combinations of all possible lengths, from 0 to n.
Given the definition, the problem can also be interpreted as finding the power set from a sequence.
So, this time let us loop over the length of combination, rather than the candidate numbers, and generate all combinations for a given length with the help of backtracking technique.



Backtracking is an algorithm for finding all solutions by exploring all potential candidates. If the solution candidate turns to be not a solution (or at least not the last one), backtracking algorithm discards it by making some changes on the previous step, i.e. backtracks and then try again.


Algorithm
We define a backtrack function named backtrack(first, curr) which takes the index of first element to add and a current combination as arguments.
- If the current combination is done, we add the combination to the final output.
- Otherwise, we iterate over the indexes i from first to the length of the entire sequence n.
	- Add integer nums[i] into the current combination curr.
	- Proceed to add more integers into the combination : backtrack(i + 1, curr).
	- Backtrack by removing nums[i] from curr.

Implementation
```
class Solution { 
  List<List<Integer>> output = new ArrayList(); 
  int n, k; 
  public void backtrack(int first, ArrayList<Integer> curr, int[] nums) { 
    // if the combination is done 
    if (curr.size() == k) { 
      output.add(new ArrayList(curr)); 
      return; 
    } 
    for (int i = first; i < n; ++i) { 
      // add i into the current combination 
      curr.add(nums[i]); 
      // use next integers to complete the combination 
      backtrack(i + 1, curr, nums); 
      // backtrack 
      curr.remove(curr.size() - 1); 
    } 
  } 
  public List<List<Integer>> subsets(int[] nums) { 
    n = nums.length; 
    for (k = 0; k < n + 1; ++k) { 
      backtrack(0, new ArrayList<Integer>(), nums); 
    } 
    return output; 
  } 
}

Complexity Analysis 
Time complexity: O(N×2^N) to generate all subsets and then copy them into output list. 
Space complexity: O(N). We are using O(N) space to maintain curr, and are modifying curr in-place with backtracking. Note that for space complexity analysis, we do not count space that is only used for the purpose of returning output, so the output array is ignored.
```

---
Solution 2: Backtracking style 2
```
class Solution { 
    public List<List<Integer>> subsets(int[] nums) { 
        List<List<Integer>> result = new ArrayList<List<Integer>>(); 
        helper(nums, result, new ArrayList<Integer>(), 0); 
        return result; 
    } 
     
    private void helper(int[] nums, List<List<Integer>> result, List<Integer> tmp, int index) { 
        // Base Case 
        if(index >= nums.length) { 
            result.add(new ArrayList<Integer>(tmp)); 
            return; 
        }
        // Not pick element / Pick element order can switch
        // Not pick element at index 
        helper(nums, result, tmp, index + 1); 
        // Pick element at index 
        tmp.add(nums[index]); 
        helper(nums, result, tmp, index + 1); 
        tmp.remove(tmp.size() - 1); 
    } 
}

Space Complexity: O(1)            
Time Complexity: O(n * 2^n) as every element has two options-to pick or not pick and n bcz we are copying the list of size n in base case.
```

Refer to
https://leetcode.com/problems/subsets/discuss/1766675/Java-Intuition-of-Approach-or-Backtracking

Intuition
This falls into a set of classic problems that can be solved using "pick"- "don't pick" approach.

As through observation, it is clear that we start with having a choice to pick or not pick from the first element and then, we propagate this choice down the tree, So
1. we either pick the element and move ahead ( increment the index ) or,
2. we don't pick the element and move ahead
```
class Solution { 
    List<List<Integer>> result; 
    public List<List<Integer>> subsets(int[] nums) { 
        result = new ArrayList(); 
        if(nums==null || nums.length==0) return result;       
        subsets(nums,new ArrayList<Integer>(), 0); 
        return result; 
    } 
     
    private void subsets(int[] nums, ArrayList<Integer> temp, int index) { 
        // base condition 
        if(index >= nums.length) { 
            result.add(new ArrayList<>(temp)); 
            return; 
        }        
        // main logic 
        // case 1 : we pick the element 
        temp.add(nums[index]); 
        subsets(nums, temp, index+1); // move ahead 
        temp.remove(temp.size()-1);          
	// case 2 : we don't pick the element ( notice, we did not add the current element in our temporary list 
        subsets(nums, temp, index+1); // move ahead 
    } 
}
```

Refer to
https://leetcode.com/problems/subsets/discuss/388568/Subsets-I-and-II-Java-Solution-with-Detailed-Explanation-and-Comments-(Recursion-and-Iteration)
The power set of a set S is the set of all subsets of S, including both the empty set emptyset and S itself. The power set of {1, 2, 3} is graphically illustrated as follows.





Backtracking

The idea is that we loop over the number list. For each number, we have two choices: pick it, or not. For example, in [1, 2, 3], we pick 1 and then do the same thing for the subproblem [2, 3]; and we don't pick 1 and then do the same thing for the subproblem [2, 3].

The size of subproblems is decreasing. When picking 2, the subproblem becomes [3] instead of [1, 3].

Consider the following questions:
- What is the base case?
- When do we add the list to the result?

Here is an illustration of recursive process on [1, 2, 3].


Note: Remember to add empty set manually.
```
public List<List<Integer>> subsets(int[] nums) { 
  List<List<Integer>> result = new ArrayList<>(); 
  List<Integer> numList = new ArrayList<>(); 
   
  result.add(new ArrayList<>()); // empty set 
  subsets(0, nums, numList, result); 
  return result; 
} 
private void subsets(int offset, int[] nums, List<Integer> numList, List<List<Integer>> result) { 
  if (offset >= nums.length) { 
    return; 
  } 
  int val = nums[offset]; 
  // pick 
  numList.add(val); 
  subsets(offset + 1, nums, numList, result); 
  // add to result 
  result.add(new ArrayList<>(numList)); 
  // not pick 
  numList.remove(numList.size() - 1); 
  subsets(offset + 1, nums, numList, result); 
}
```

---
Solution 3: No Backtracking but deep copy required to avoid modify original object
```
class Solution { 
    public List<List<Integer>> subsets(int[] nums) { 
        List<List<Integer>> result = new ArrayList<List<Integer>>(); 
        // Manually add empty set 
        result.add(new ArrayList<Integer>()); 
        helper(nums, result, new ArrayList<Integer>(), 0); 
        return result; 
    } 
     
    private void helper(int[] nums, List<List<Integer>> result, List<Integer> tmp, int index) { 
        // Base Case 
        if(index >= nums.length) { 
            return; 
        }
        // Not pick element / Pick element order can switch 
        // Not pick element at index 
        helper(nums, result, tmp, index + 1); 
        // Pick element at index 
        // Create a deep copy instead of modifying 'tmp' then no need backtrack 
        List<Integer> copy = new ArrayList<Integer>(tmp); 
        copy.add(nums[index]); 
        result.add(copy); 
        helper(nums, result, copy, index + 1); 
    } 
}
```

Refer to
https://segmentfault.com/a/1190000003498803

深度优先搜索


复杂度

时间 O(NlogN) 空间 O(N) 递归栈空间


思路

这道题可以转化为一个类似二叉树的深度优先搜索。二叉树的根是个空集，它的左子节点是加上第一个元素产生的集合，右子节点不加上第一个元素所产生的集合。以此类推，左子节点的左子节点是加上第二个元素，左子节点的右子节点是不加上第二个元素。而解就是这个二叉树所有的路径，我们要做的就是根据加，或者不加下一元素，来产生一个新的集合，然后继续递归直到终点。另外需要先排序以满足题目要求。


注意

- 不需要先排序
- 如果递归之前先将空集加入结果，那么递归尽头就不需要再加一次空集。反之则需要。后面详细叙述在How to avoid manually add empty set ?
- LinkedList在这里效率要高于ArrayList。
- 新的集合要new一个新的list，防止修改引用。


代码

```
public class Solution { 
    public List<List<Integer>> subsets(int[] S) { 
        Arrays.sort(S); 
        List<List<Integer>> res = new ArrayList<List<Integer>>(); 
        List<Integer> tmp = new ArrayList<Integer>(); 
        //先加入空集 
        res.add(tmp); 
        helper(S, 0, res, tmp); 
        return res; 
    } 
     
    private void helper(int[] S,int index,List<List<Integer>> res, List<Integer> tmp){ 
        if(index>=S.length) return; 
        // 不加入当前元素产生的集合，然后继续递归 
        helper(S, index+1, res, tmp); 
        List<Integer> tmp2 = new ArrayList<Integer>(tmp); 
        tmp2.add(S[index]); 
        res.add(tmp2); 
        // 加入当前元素产生的集合，然后继续递归 
        helper(S, index+1, res, tmp2); 
    } 
}
```

---
How to avoid manually add empty set ?
The critical statement added to avoid manually add empty set before recursion is happening in recursion base case, when first condition match happening (index == nums.length), the empty 'tmp' list we pass through the whole recursion from // Not pick element at index  helper(nums, result, tmp, index + 1) statement will  keep the empty 'tmp' list as is (no change still keep as empty list) and then result.add(new ArrayList<Integer>(tmp)) will add a deep copy of 'tmp' into result
```
        // Base Case  
        if(index >= nums.length) { 
            // Here is the critical statement added to avoid manually add empty set before recursion 
            result.add(new ArrayList<Integer>(tmp));  
            return; 
        }
```
Full code based on Solution 3 without manually add empty set
```
class Solution { 
    public List<List<Integer>> subsets(int[] nums) {  
        List<List<Integer>> result = new ArrayList<List<Integer>>();  
        // Manually add empty set not required 
        //result.add(new ArrayList<Integer>());  
        helper(nums, result, new ArrayList<Integer>(), 0);  
        return result;  
    }  
      
    private void helper(int[] nums, List<List<Integer>> result, List<Integer> tmp, int index) {  
        // Base Case  
        if(index >= nums.length) {
            // The critical statement added to avoid manually add empty set before recursion
            result.add(new ArrayList<Integer>(tmp)); 
            return; 
        } 
        // Not pick element / Pick element order can switch  
        // Not pick element at index  
        helper(nums, result, tmp, index + 1);  
        // Pick element at index  
        // Create a deep copy instead of modifying 'tmp' then no need backtrack  
        List<Integer> copy = new ArrayList<Integer>(tmp);  
        copy.add(nums[index]); 
        // When add critical statement in base case, have to remove below statement to avoid
        // duplicate results
        //result.add(copy);  
        helper(nums, result, copy, index + 1);  
    } 
}
```

---
The difference between Backtracking style 1 with for loop and style 2 without for loop

Progress from Solution 1: Backtracking style 1
```
class Solution {  
    public List<List<Integer>> subsets(int[] nums) {  
        List<List<Integer>> result = new ArrayList<List<Integer>>();  
        helper(nums, result, new ArrayList<Integer>(), 0);  
        return result;  
    }  
      
    private void helper(int[] nums, List<List<Integer>> result, List<Integer> tmp, int index) {  
        result.add(new ArrayList<Integer>(tmp));  
        for(int i = index; i < nums.length; i++) {  
            tmp.add(nums[i]);  
            helper(nums, result, tmp, i + 1);  
            tmp.remove(tmp.size() - 1);  
        }  
    }  
}

nums={1,2,3} 
--------------------------------------------- 
Round 1: 
index=0,tmp={} 
helper(nums,result,{},0) 
result={{}} 
i=index=0 
tmp.add(nums[0]) -> tmp={1} 
--------------------------------------------- 
Round 2: 
index=0+1=1,tmp={1} 
helper(nums,result,{1},1) 
result={{},{1}} 
i=index=1 
tmp.add(nums[1]) -> tmp={1,2} 
--------------------------------------------- 
Round 3: 
index=1+1=2,tmp={1,2} 
helper(nums,result,{1,2},2) 
result={{},{1},{1,2}} 
i=index=2 
tmp.add(nums[2]) -> tmp={1,2,3} 
--------------------------------------------- 
Round 4: 
index=2+1=3,tmp={1,2,3} 
helper(nums,result,{1,2,3},3) 
result={{},{1},{1,2},{1,2,3}} 
i=2 -> i++=3 == nums.length skip for loop 
--------------------------------------------- 
Round 5: Backtrack to Round 3 statistics 
index=2,tmp={1,2,3} -> tmp.size()-1=3-1=2 
tmp.remove(2) -> tmp={1,2} 
i=2 -> i++=3 == nums.length skip for loop 
--------------------------------------------- 
Round 6: Backtrack to Round 2 statistics 
index=1,tmp={1,2} -> tmp.size()-1=2-1=1 
tmp.remove(1) -> tmp={1} 
i=1 -> i++=2 
tmp.add(nums[2]) -> tmp={1,3} 
--------------------------------------------- 
Round 7: 
index=3,tmp={1,3} 
helper(nums,result,{1,3},3) 
result={{},{1},{1,2},{1,2,3},{1,3}} 
i=index=3 -> i == nums.length skip for loop 
--------------------------------------------- 
Round 8: Backtrack to Round 2 statistics 
index=1,tmp={1,3} -> tmp.size()-1=2-1=1 
tmp.remove(1) -> tmp={1} 
i=2 -> i++=3 == nums.length skip for loop 
--------------------------------------------- 
Round 9: Backtrack to Round 1 statistics 
index=0,tmp={1} -> tmp.size()-1=1-1=0 
tmp.remove(0) -> tmp={} 
i=0 -> i++=1 
tmp.add(nums[1]) -> tmp={2} 
--------------------------------------------- 
Round 10: 
index=2,tmp={2} 
helper(nums,result,{2},2) 
result={{},{1},{1,2},{1,2,3},{1,3},{2}} 
i=index=2 
tmp.add(nums[2]) -> tmp={2,3} 
--------------------------------------------- 
Round 11: 
index=3,tmp={2,3} 
helper(nums,result,{2,3},3) 
result={{},{1},{1,2},{1,2,3},{1,3},{2},{2,3}} 
i=index=3 == nums.length skip for loop 
--------------------------------------------- 
Round 12: Backtrack to Round 2 statistics 
index=2,tmp={2,3} -> tmp.size()-1=2-1=1 
tmp.remove(1) -> tmp={2} 
i=2 -> i++=3 == nums.length skip for loop 
--------------------------------------------- 
Round 13: Backtrack to Round 1 statistics 
index=0,tmp={2} -> tmp.size()-1=1-1=0 
tmp.remove(0) -> tmp={} 
i=1 -> i++=2 
tmp.add(nums[2]) -> tmp={3} 
--------------------------------------------- 
Round 14: 
index=3,tmp={3} 
helper(nums,result,{3},3) 
result={{},{1},{1,2},{1,2,3},{1,3},{2},{2,3},{3}} 
i=index=3 == nums.length skip for loop 
--------------------------------------------- 
Round 15: Backtrack to Round 1 statistics 
index=0,tmp={3} -> tmp.size()-1=1-1=0 
tmp.remove(0) -> tmp={} 
i=2 -> i++=3 == nums.length skip for loop 
--------------------------------------------- 
End 
============================================= 
Result time elapsed statistics: 
result={{}} 
result={{},{1}} 
result={{},{1},{1,2}} 
result={{},{1},{1,2},{1,2,3}} 
result={{},{1},{1,2},{1,2,3},{1,3}} 
result={{},{1},{1,2},{1,2,3},{1,3},{2}} 
result={{},{1},{1,2},{1,2,3},{1,3},{2},{2,3}} 
result={{},{1},{1,2},{1,2,3},{1,3},{2},{2,3},{3}}
```

Progress from Solution 2: Backtracking style 2
```
class Solution {  
    public List<List<Integer>> subsets(int[] nums) {  
        List<List<Integer>> result = new ArrayList<List<Integer>>();  
        helper(nums, result, new ArrayList<Integer>(), 0);  
        return result;  
    }  
      
    private void helper(int[] nums, List<List<Integer>> result, List<Integer> tmp, int index) {  
        // Base Case  
        if(index >= nums.length) {  
            result.add(new ArrayList<Integer>(tmp));  
            return;  
        } 
        // Not pick element / Pick element order can switch 
        // Not pick element at index  
        helper(nums, result, tmp, index + 1);  
        // Pick element at index  
        tmp.add(nums[index]);  
        helper(nums, result, tmp, index + 1);  
        tmp.remove(tmp.size() - 1);  
    }  
}

nums={1,2,3} 
--------------------------------------------- 
Round 1: 
index=0,tmp={} 
helper(nums,result,{},0) 
index=0 < nums.length=3 
--------------------------------------------- 
Round 2: 
index=1,tmp={} 
helper(nums,result,{},0+1) 
index=1 < nums.length=3 
--------------------------------------------- 
Round 3: 
index=2,tmp={} 
helper(nums,result,{},1+1) 
index=2 < nums.length=3 
--------------------------------------------- 
Round 4: 
index=3,tmp={} 
helper(nums,result,{},2+1) 
index=3 == nums.length=3 
result={{}} 
Return to Round 3 statistics 
index=2,tmp={} 
tmp.add(nums[2]) -> tmp={3} 
--------------------------------------------- 
Round 5: 
helper(nums,result,{3},2+1) 
index=3 == nums.length=3 
result={{},{3}} 
Return to Round 3 statistics 
tmp.remove(tmp.size()-1)=tmp.remove(1-1)=tmp.remove(0)={} 
Finish last statement in helper and return to Round 2 statistics 
--------------------------------------------- 
Round 6: 
index=1,tmp={} 
tmp.add(nums[1]) -> tmp={2} 
helper(nums,result,{2},1+1) 
index=2 < nums.length=3 
--------------------------------------------- 
Round 7: 
helper(nums,result,{2},2+1) 
index=3 == nums.length=3 
result={{},{3},{2}} 
Return to Round 6 statistics 
--------------------------------------------- 
Round 8: 
index=2,tmp={2} 
tmp.add(nums[2]) -> tmp={2,3} 
helper(nums,result,{2,3},2+1) 
--------------------------------------------- 
Round 9: 
index=3,tmp={2,3} 
index=3 == nums.length=3 
result={{},{3},{2},{2,3}} 
Return to Round 8 statistics 
tmp.remove(tmp.size()-1)=tmp.remove(2-1)=tmp.remove(1)={2} 
Finish last statement in helper and return to Round 6 statistics 
--------------------------------------------- 
Round 10: 
index=1,tmp={2} 
tmp.remove(tmp.size()-1)=tmp.remove(1-1)=tmp.remove(0)={} 
Finish last statement in helper and return to Round 1 statistics 
--------------------------------------------- 
Round 11: 
index=0,tmp={} 
tmp.add(nums[0]) -> tmp={1} 
helper(nums,result,{1},0+1) 
index=1 < nums.length=3 
--------------------------------------------- 
Round 12: 
index=2,tmp={1} 
helper(nums,result,{1},1+1) 
index=2 < nums.length=3 
...... 
result={{},{3},{2},{2,3},{1}} 
...... 
result={{},{3},{2},{2,3},{1},{1,3}} 
...... 
result={{},{3},{2},{2,3},{1},{1,3},{1,2}} 
...... 
result={{},{3},{2},{2,3},{1},{1,3},{1,2},{1,2,3}} 
--------------------------------------------- 
End 
============================================= 
Result time elapsed statistics: 
result={{}} 
result={{},{3}} 
result={{},{3},{2}} 
result={{},{3},{2},{2,3}} 
result={{},{3},{2},{2,3},{1}} 
result={{},{3},{2},{2,3},{1},{1,3}} 
result={{},{3},{2},{2,3},{1},{1,3},{1,2}} 
result={{},{3},{2},{2,3},{1},{1,3},{1,2},{1,2,3}}
```

We can see the result element order is quite different, for style 1 it start from {1}, but for style 2 it start from {3}, the reason behind is for style 1 we don't have if (index == nums.length) condition when attempt to add 'tmp' list into result, but in style 2 we have if (index == nums.length) as condition, and because of this condition, only when the DFS go into deepest node (leave node), it will match the condition and able to add into result first, that's why result first add {3} instead of {1}.

And if we check more on style 2 pattern, it more close to "Divide and Conquer" style, which means
1. base case (only when index == nums.length means current recursion combination done, add into result)
2. recursive into smallest problem (pick current element + not pick current element)
3. handle smallest problem (handle pick + handle not pick)

But strictly saying neither style 1 or style 2 is not "Divide and Conquer" style of recursion, it is also kind of "Traversal" style of recursion.
