
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * Refer to
 * http://www.cnblogs.com/grandyang/p/5615583.html
 * Given a nested list of integers, return the sum of all integers in the list weighted by their depth.

	Each element is either an integer, or a list -- whose elements may also be integers or other lists.
	
	Different from the previous question where weight is increasing from root to leaf, now the weight 
	is defined from bottom up. i.e., the leaf level integers have weight 1, and the root level integers 
	have the largest weight.
	
	Example 1:
	Given the list [[1,1],2,[1,1]], return 8. (four 1's at depth 1, one 2 at depth 2)
	
	Example 2:
	Given the list [1,[4,[6]]], return 17. (one 1 at depth 3, one 4 at depth 2, and one 6 at depth 1; 
	1*3 + 4*2 + 6*1 = 17) 
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
 * 
 * Solution
 * https://discuss.leetcode.com/topic/49041/no-depth-variable-no-multiplication
 * https://discuss.leetcode.com/topic/49488/java-ac-bfs-solution
 * http://www.cnblogs.com/grandyang/p/5615583.html
 */
public class NestedListWeightSumII {
// dummy class
	private class NestedInteger {
		// dummy methods
		public boolean isInteger() {}
		public int getInteger() {}
		public List<NestedInteger> getList() {}
	}
	
	// Solution 1: DFS
	// Refer to
	// http://www.cnblogs.com/grandyang/p/5615583.html
	/**
	 * 这道题是之前那道Nested List Weight Sum的拓展，与其不同的是，这道题的深度越深，
	 * 权重越小，和之前刚好相反。但是解题思路没有变，还可以用DFS来做，那么由于遍历的时候
	 * 不知道最终的depth有多深，则不能遍历的时候就直接累加结果，我最开始的想法是在遍历
	 * 的过程中建立一个二维数组，把每层的数字都保存起来，然后最后知道了depth后，再来计算
	 * 权重和，比如题目中给的两个例子，建立的二维数组分别为：

		[[1,1],2,[1,1]]：
		1 1 1 1
		2
		
		[1,[4,[6]]]：
		1
		4
		6
		
		这样我们就能算出权重和了
	 */
	public int depthSumInverse(List<NestedInteger> nestedList) {
		// Build two dimensional list which contains numbers in each level
		List<List<Integer>> levels = new ArrayList<List<Integer>>();
		helper(nestedList, 0, levels);
		int result = 0;
		for(int i = 0; i < levels.size(); i++) {
			int levelSum = 0;
			for(int j = 0; j < levels.get(i).size(); j++) {
				levelSum += levels.get(i).get(j);
			}
			// The index of levels list means depth, so levels.size() is total
			// depth, and (total depth - index) is current levelSum coefficient
			result += levelSum * (levels.size() - i);
		}
		return result;
	}
	
	private void helper(List<NestedInteger> nestedList, int depth, List<List<Integer>> levels) {
		for(NestedInteger e : nestedList) {
			if(e.isInteger()) {
				// Store integer to current depth level
				if(levels.get(depth) == null) {
					List<Integer> level = new ArrayList<Integer>();
					level.add(e.getInteger());
					levels.add(level);
				} 
				levels.get(depth).add(e.getInteger());
			} else {
				// Continue on next depth level
			    helper(e.getList(), depth + 1, levels);
			}
		}
	}
	
	// Solution 2: Optimization on Solution 1 -> DFS
	// Refer to
	// http://www.cnblogs.com/grandyang/p/5615583.html
	/**
	 * 其实上面的方法可以简化，由于每一层的数字不用分别保存，每个数字分别乘以深度再相加，
	 * 跟每层数字先相加起来再乘以深度是一样的，这样我们只需要一个一维数组就可以了，只要
	 * 把各层的数字和保存起来，最后再计算权重和即可
	 */
	public int depthSumInverse2(List<NestedInteger> nestedList) {
		List<Integer> levelSums = new ArrayList<Integer>();
		helper2(nestedList, 0, levelSums);
		int result = 0;
		for(int i = 0; i < levelSums.size(); i++) {
			result += levelSums.get(i) * (levelSums.size() - i);
		}
		return result;
	}
	
	private void helper2(List<NestedInteger> nestedList, int depth, List<Integer> levelSums) {
	    for(NestedInteger e : nestedList) {
	    	if(e.isInteger()) {
	    	    if(levelSums.get(depth) == null) {
	    	    	levelSums.set(depth, e.getInteger());
	    	    }
	    	    levelSums.set(depth, levelSums.get(depth) + e.getInteger());
	    	} else {
	    		helper2(nestedList, depth + 1, levelSums);
	    	}
	    }
	}
	
	
	// Solution 3: Unweighted + Weighted
	// Refer to
	// http://www.cnblogs.com/grandyang/p/5615583.html
	// https://discuss.leetcode.com/topic/49041/no-depth-variable-no-multiplication
	/**
	 * 下面这个方法就比较巧妙了，由史蒂芬大神提出来的，这个方法用了两个变量unweighted和weighted，
	 * 非权重和跟权重和，初始化均为0，然后如果nestedList不为空开始循环，先声明一个空数组nextLevel，
	 * 遍历nestedList中的元素，如果是数字，则非权重和加上这个数字，如果是数组，就加入nextLevel，
	 * 这样遍历完成后，第一层的数字和保存在非权重和unweighted中了，其余元素都存入了nextLevel中，
	 * 此时我们将unweighted加到weighted中，将nextLevel赋给nestedList，这样再进入下一层计算，
	 * 由于上一层的值还在unweighted中，所以第二层计算完将unweighted加入weighted中时，相当于
	 * 第一层的数字和被加了两次，这样就完美的符合要求了，这个思路又巧妙又牛B，大神就是大神啊
	 */
	public int depthSumInverse3(List<NestedInteger> nestedList) {
		int unweighted = 0;
		int weighted = 0;
		while(!nestedList.isEmpty()) {
			List<NestedInteger> nextLevel = new ArrayList<NestedInteger>();
			for(NestedInteger e : nestedList) {
				if(e.isInteger()) {
					unweighted += e.getInteger();
				} else {
					nextLevel.addAll(e.getList());
				}
			}
			weighted += unweighted;
			nestedList = nextLevel;
		}
		return weighted;
	}
	
	// Solution 4: BFS
	// Refer to
	// http://www.cnblogs.com/grandyang/p/5615583.html
	// https://discuss.leetcode.com/topic/49488/java-ac-bfs-solution
	/**
	 * 下面这种算法是常规的BFS解法，利用上面的建立两个变量unweighted和weighted的思路，大体上没什么区别
	 */
	public int depthSumInverse4(List<NestedInteger> nestedList) {
		if(nestedList == null) {
			return 0;
		}
		int prev = 0;
		int total = 0;
		Queue<NestedInteger> queue = new LinkedList<NestedInteger>();
		queue.addAll(nestedList);
		while(!queue.isEmpty()) {
			int size = queue.size();
			int levelSum = 0;
			for(int i = 0; i < size; i++) {
				NestedInteger e = queue.poll();
				if(e.isInteger()) {
					levelSum += e.getInteger();
				} else {
					queue.addAll(e.getList());
				}
			}
			prev += levelSum;
			total += prev;
		}
		return total;
	}
}



