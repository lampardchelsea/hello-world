
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
/**
 * Refer to
 * http://www.cnblogs.com/grandyang/p/5340305.html
 * Given a nested list of integers, return the sum of all integers in the list weighted by their depth.

	Each element is either an integer, or a list -- whose elements may also be integers or other lists.
	
	Example 1:
	Given the list [[1,1],2,[1,1]], return 10. (four 1's at depth 2, one 2 at depth 1)
	
	Example 2:
	Given the list [1,[4,[6]]], return 27. (one 1 at depth 1, one 4 at depth 2, 
	and one 6 at depth 3; 1 + 4*2 + 6*3 = 27)
 * 
 * // This is the interface that allows for creating nested lists.
 * // You should not implement it, or speculate about its implementation
 * public interface NestedInteger {
 *
 *     // @return true if this NestedInteger holds a single integer,
 *     // rather than a nested list.
 *     public boolean isInteger();
 *
 *     // @return the single integer that this NestedInteger holds,
 *     // if it holds a single integer
 *     // Return null if this NestedInteger holds a nested list
 *     public Integer getInteger();
 *
 *     // @return the nested list that this NestedInteger holds,
 *     // if it holds a nested list
 *     // Return null if this NestedInteger holds a single integer
 *     public List<NestedInteger> getList();
 * }
 */
public class NestedListWeightSum {
	// dummy class
	private class NestedInteger {
		// dummy methods
		public boolean isInteger() {}
		public int getInteger() {}
		public List<NestedInteger> getList() {}
	}
	
	// Solution 1: DFS
	// Refer to
	// https://discuss.leetcode.com/topic/41357/2ms-easy-to-understand-java-solution
	public int depthSum(List<NestedInteger> nestedList) {
        return helper(nestedList, 1);
	}
	
	private int helper(List<NestedInteger> list, int depth) {
		int result = 0;
		for(NestedInteger e : list) {
			if(e.isInteger()) {
				result += e.getInteger() * depth;
			} else {
				helper(e.getList(), depth + 1);
			}
		}
		return result;
	}
	
	// Solution 2: BFS (level order traverse)
	// Refer to
	// https://discuss.leetcode.com/topic/41495/java-solution-similar-to-tree-level-order-traversal
	public int depthSum2(List<NestedInteger> nestedList) {
		if(nestedList == null) {
			return 0;
		}
		int sum = 0;
		int level = 1;
		Queue<NestedInteger> queue = new LinkedList<NestedInteger>(nestedList);
		while(!queue.isEmpty()) {
			int size = queue.size();
			for(int i = 0; i < size; i++) {
				NestedInteger ni = queue.poll();
				if(ni.isInteger()) {
					sum += ni.getInteger() * level;
				} else {
					queue.addAll(ni.getList());
				}
			}
			level++;
		}
		return sum;
	}
}
