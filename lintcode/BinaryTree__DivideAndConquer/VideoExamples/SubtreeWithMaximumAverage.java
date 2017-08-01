/**
 * Refer to
 * http://blog.leanote.com/post/westcode/53751fcb4502
 * Given a binary tree, find the subtree with maximum average. Return the root of the subtree.
    Example
    Given a binary tree:

             1
           /   \
         -5     11
         / \   /  \
        1   2 4    -2 

    return the node 11.
 *
 * Solution
 * https://www.jiuzhang.com/solutions/subtree-with-maximum-average/
 * http://blog.leanote.com/post/westcode/53751fcb4502
 * 思路
 * 这题同样也是一道 divide&conquer 的题目，然后也是用到了 custom class ResultType 来进行信息的传输。这题和之前做过的 
 * Minimum Subtree 可以说是非常的类似，不过这里 ResultType 里包含的东西更多一些，而且相比较多了一个小问题。
 * 
 * Solution #1: Pure Divide & Conquer w/ ResultType
 * 一边讲整体的思路，一边讲 ResultType 里面应该放什么东西。
 * 开头是 DandC 的老思路，先 divide，假设 function 已经写好了能够传回来 两个 subtrees 的 resulttypes
 * 然后 conquer，对传回来的数据和当前 subroot 进行处理
 * 首先是要求出当前 subtree 的 average，那么我们就需要两个子树的 Node 数目，还有所有 node 的和
 * 接着就可以算出当前 subtree 的 average，需要注意的小问题是 因为用到了除法，要注意精确度的保留，
 * 写的时候就是因为这个问题有几个 cases 没有过九章上面的答案没有这个问题因为答案用的是 traverse+DandC 的方法，
 * 最大的 node 还有 result type 都是当成全局变量存起来的，然后就可以用分母对换的那个公式 (a/b > c/d => a*d > c*b)
 * 来比较 average 所以不需要考虑精确度的流失再接着就是找出最大的平均值了，我们已经有了当前 subtree 的 avg，
 * 那么就需要左右子树各自的最大平均值来进行比较了再往下就是根据找到的最大平均值来决定要 return 的 result type
 * 如果是 当前 subroot 最大，那么我们把新算出来的 avg 当成新 resultype 的 maxAverage， subroot 当成 maxNode。
 * 如果是 左或者右子树，把 maxAvg 和 maxNode 接着网上传。
 * 
 * Solution #2: Traverse + Divide & Conquer
 * 这个解法的不同之处在于用了全局变量来保存当前的候选node，然后在算 avg 的时候正好就更新了这两个值
 * 这个解法的 ResultType 变量也更少，只有 sum 和 size 两个变量
 * 因为每次更新只需要改 sum 和 size，所以比较两个平均值的时候可以用对换分母的方法，就不用担心损失精确度的问题
 * 这个做法相对于 纯 D&C 来说更简洁也可能更好理解一些
 */
public class SubtreeWithMaximumAverage {
	private class TreeNode {
		public int val;
		public TreeNode left, right;
		public TreeNode(int val) {
		    this.val = val;
		    this.left = this.right = null;
		}
    }
	
	private class ResultType {
		public int nodeNums;
		public int nodeSum;
		public ResultType(int nodeNums, int nodeSum) {
			this.nodeNums = nodeNums;
			this.nodeSum = nodeSum;
		}
	}
	
	private TreeNode subtree = null;
	private ResultType subtreeResult = null;
	
	public TreeNode findSubtree2(TreeNode root) {
		helper(root);
		return subtree;
	}
	
	// Traverse + Divide & Conquer
	private ResultType helper(TreeNode root) {
		// Base case
		if(root == null) {
			return new ResultType(0, 0);
		}
		// Divide
		ResultType leftResult = helper(root.left);
		ResultType rightResult = helper(root.right);
		// Merge
		// Note: Don't use divide here, must use multiple to get the final compare
		//         a       c      a * d - b * c
		//        ---  vs --- ==> ------------- ==> (a * d - b * c) vs 0
		//         b       d          b * d
		// a = result.nodeSum, b = result.nodeNums
		// c = subtreeResult.nodeSum, d = subtreeResult.nodeNums
		// when (a * d - b * c > 0) means new subtree has larger divided value 
		ResultType result = new ResultType(leftResult.nodeNums + rightResult.nodeNums + 1, leftResult.nodeSum + rightResult.nodeSum + root.val);
		// Compare new result with current stored one
		// The tricky part: need to initialize the first stored one for later compare (Condition as subtreeResult == null)
		if(subtreeResult == null || result.nodeSum * subtreeResult.nodeNums - result.nodeNums * subtreeResult.nodeSum > 0) {
			subtree = root;
			subtreeResult = result;
		}
		return result;
	}
	
	public static void main(String[] args) {
		/**
			     1
	           /   \
	         -5     11
	         / \   /  \
	        1   2 4    -2 
		 */
		SubtreeWithMaximumAverage s = new SubtreeWithMaximumAverage();
    	TreeNode one = s.new TreeNode(1);
    	TreeNode two = s.new TreeNode(-5);
    	TreeNode three = s.new TreeNode(11);
    	TreeNode four = s.new TreeNode(1);
    	TreeNode five = s.new TreeNode(2);
    	TreeNode six = s.new TreeNode(4);
    	TreeNode seven = s.new TreeNode(-2);
    	one.left = two;
    	one.right = three;
    	two.left = four;
    	two.right = five;
    	three.left = six;
    	three.right = seven;
    	TreeNode result = s.findSubtree2(one);
    	System.out.println(result.val);
	}
}

