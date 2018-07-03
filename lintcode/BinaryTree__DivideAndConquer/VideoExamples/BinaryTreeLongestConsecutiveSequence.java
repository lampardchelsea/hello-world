/**
 * Refer to
 * http://blog.leanote.com/post/westcode/%5B%E5%88%B7%E9%A2%98%E7%AC%94%E8%AE%B0%5D-LeetCode-LintCode-5
 * Given a binary tree, find the length of the longest consecutive sequence path.
 * The path refers to any sequence of nodes from some starting node to any node in the tree along the 
 * parent-child connections. The longest consecutive path need to be from parent to child (cannot be the reverse).
 * 
    For example,

           1
            \
             3
            / \
           2   4
                \
                 5

    Longest consecutive sequence path is 3-4-5, so return 3.

           2
            \
             3
            / 
           2    
          / 
         1

    Longest consecutive sequence path is 2-3,not3-2-1, so return 2.
 * 
 * 
 * Solution
 * http://blog.leanote.com/post/westcode/%5B%E5%88%B7%E9%A2%98%E7%AC%94%E8%AE%B0%5D-LeetCode-LintCode-5
 * 思路
 * 这道题有了两种解法，分别是 Divide Conquer 还有 Traverse 的组合， 以及纯 Divide Conquer。虽说是算作两种方法，
 * 但是实际上思路都是一样的，只是传递数据的方式改变了而已。就以第一种方法来解释吧。
 * 因为是 traverse，我们把最后要的结果longest全局存起来跟着分治的老套路来走，但是我们需要定义的一点是递归返回的东西是什么，
 * 这里我们把返回值定义为 以这个 subroot 为起点的最长path的长度check null 之后按老套路无脑丢给左右子树，也就是我们的 divide
 * 进行 conquer，我们要看的是当前 subroot 能否加到左右子树为起点的 path 里面，然后找到这几个 paths 之中的最大值
 * 首先确定的是至少 subroot 自己可以算一个长度1的 path
 * 然后看左子树是否存在和为 consecutive，如果是 那么（左子树 path 长度+1）便成为了我们当前 path 的最大长度
 * 同样方法处理右子树，然后得到当前 subroot 下的最大长度
 * 把这个长度和我们全局的最大长度进行比较和更新
 * 最后返回全局最大长度
 * 纯分治法的做法也是一样的，不过保存和传递两个数据
 * 1.全局最大长度
 * 2.当前子树为起点的最大长度
 * 使用了单独建立的 ResultType class
 * 
 * 复杂度分析
 * 时间 O(n)
 * 两种方法都是每个 node 都访问一次，每次做 O(1) 工作
 * 空间
 * Traverse + Divide Conquer: O(1)
 * Pure Divide Conquer： O(n) 每个 node 都创建了新的 ResultType
 * 
 * http://www.jiuzhang.com/solutions/binary-tree-longest-consecutive-sequence/
 * https://discuss.leetcode.com/topic/28234/easy-java-dfs-is-there-better-time-complexity-solution
 *
 */
public class BinaryTreeLongestConsecutiveSequence {
	private class TreeNode {
		public int val;
		public TreeNode left, right;
		public TreeNode(int val) {
		    this.val = val;
		    this.left = this.right = null;
		}
    }
	
	// Solution 1: Purely Divide and Conquer
	class ResultType {
		int maxInSubtree;
		int maxFromRoot;
		public ResultType(int maxInSubtree, int maxFromRoot) {
			// E.g For given root = 1, the maxInSubtree finally is 3, maxFromRoot is 1 as
			// 1 and 3 are not consecutive nodes
			this.maxInSubtree = maxInSubtree;
			this.maxFromRoot = maxFromRoot;
		}
	}

	
	public int longestConsecutive(TreeNode root) {
	    return helper(root).maxInSubtree;	
	}

	/**
	 * The process for given example here is
	 * 	   1
            \
             3
            / \
           2   4
                \
                 5
     * We first go to check subroot = 3 and its left subtree, after process
     * which we first reach node = 2, and calculate if subroot = 3 has
     * maxFromRoot = 1 and maxInSubtree = 1, then we go to process subroot 3's
     * right subtree, after process which we first reach node = 5, then node
     * = 4, the maxFromRoot change as 1 to 2 to 3, also same for maxInSubtree
	 */
	public ResultType helper(TreeNode root) {
		// Base case
		if(root == null) {
			return new ResultType(0, 0);
		}
		// Divide
		ResultType left = helper(root.left);
		ResultType right = helper(root.right);
		// Conquer (Merge)
		// Result of length 1 for current subroot
		ResultType result = new ResultType(0, 1);
		// Check whether left and right subtree can connect
		// with subroot and find the max path from
		// [1 (subroot itself), left + 1 or right + 1]
		if(root.left != null && root.val + 1 == root.left.val) {
			result.maxFromRoot = Math.max(result.maxFromRoot, left.maxFromRoot + 1);
		}
		if(root.right != null && root.val + 1 == root.right.val) {
			result.maxFromRoot = Math.max(result.maxFromRoot, right.maxFromRoot + 1);
		}
		// Compare and update maxInSubtree
		result.maxInSubtree = Math.max(result.maxFromRoot, Math.max(left.maxInSubtree, right.maxInSubtree));
		return result;
	}
	
	// Similar implementation as Path Sum III, Longest Univalue Path
	// Refer to
	// https://github.com/lampardchelsea/hello-world/blob/master/leetcode/Tree/PathSumIII.java
	// https://github.com/lampardchelsea/hello-world/blob/master/leetcode/Tree/LongestUnivaluePath.java
	// Solution 2: Traverse + Divide and Conquer
	// Global variable for longest path
	private int longest = 0;
	public int longestConsecutive2(TreeNode root) {
		helper2(root);
		return longest;
	}
	
	public int helper2(TreeNode root) {
		// Base case
		if(root == null) {
			return 0;
		}
		// Divide
		// Get the longest path from two subtrees
		int left = helper2(root.left);
		int right = helper2(root.right);
		
		// Conquer
		// Already known subroot can calculate as 1
		int subtreeLongest = 1;
		if(root.left != null && root.val + 1 == root.left.val) {
			subtreeLongest = Math.max(subtreeLongest, left + 1);
		}
		if(root.right != null && root.val + 1 == root.right.val) {
			subtreeLongest = Math.max(subtreeLongest, right + 1);
		}
		if(subtreeLongest > longest) {
			longest = subtreeLongest;
		}
		return subtreeLongest;
	}
	
	
	public static void main(String[] args) {
		/**
		   1
            \
             3
            / \
           2   4
                \
                 5
		 */
		BinaryTreeLongestConsecutiveSequence b = new BinaryTreeLongestConsecutiveSequence();
    	TreeNode one = b.new TreeNode(1);
    	TreeNode two = b.new TreeNode(2);
    	TreeNode three = b.new TreeNode(3);
    	TreeNode four = b.new TreeNode(4);
    	TreeNode five = b.new TreeNode(5);
    	one.right = three;
    	three.left = two;
    	three.right = four;
    	four.right = five;
    	int result = b.longestConsecutive2(one);
    	System.out.println(result);
	}
}
