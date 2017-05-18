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
