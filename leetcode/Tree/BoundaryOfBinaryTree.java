/**
 * Refer to
 * http://www.cnblogs.com/grandyang/p/6833459.html
 * Given a binary tree, return the values of its boundary in anti-clockwise direction starting from root. 
   Boundary includes left boundary, leaves, and right boundary in order without duplicate nodes.

    Left boundary is defined as the path from root to the left-most node. Right boundary is defined as 
    the path from root to the right-most node. If the root doesn't have left subtree or right subtree, 
    then the root itself is left boundary or right boundary. Note this definition only applies to the 
    input binary tree, and not applies to any subtrees.

    The left-most node is defined as a leaf node you could reach when you always firstly travel to 
    the left subtree if exists. If not, travel to the right subtree. Repeat until you reach a leaf node.

    The right-most node is also defined by the same way with left and right exchanged.

    Example 1

    Input:
      1
       \
        2
       / \
      3   4

    Ouput:
    [1, 3, 4, 2]

    Explanation:
    The root doesn't have left subtree, so the root itself is left boundary.
    The leaves are node 3 and 4.
    The right boundary are node 1,2,4. Note the anti-clockwise direction means you should output reversed right boundary.
    So order them in anti-clockwise without duplicates and we have [1,3,4,2].


    Example 2

    Input:
        ____1_____
       /          \
      2            3
     / \          / 
    4   5        6   
       / \      / \
      7   8    9  10  

    Ouput:
    [1,2,4,7,8,9,10,6,3]

    Explanation:
    The left boundary are node 1,2,4. (4 is the left-most node according to definition)
    The leaves are node 4,7,8,9,10.
    The right boundary are node 1,3,6,10. (10 is the right-most node).
    So order them in anti-clockwise without duplicate nodes we have [1,2,4,7,8,9,10,6,3].
 *
 *
 * Solution
 * http://www.cnblogs.com/grandyang/p/6833459.html
   这道题给了我们一棵二叉树，让我们以逆时针的顺序来输出树的边界，按顺序分别为左边界，叶结点和右边界。
   题目中给的例子也能让我们很清晰的明白哪些算是边界上的结点。那么最直接的方法就是分别按顺序求出左边界结点，
   叶结点，和右边界结点。那么如何求的，对于树的操作肯定是用递归最简洁啊，所以我们可以写分别三个递归函数来
   分别求左边界结点，叶结点，和右边界结点。首先我们先要处理根结点的情况，当根结点没有左右子结点时，其也是
   一个叶结点，那么我们一开始就将其加入结果res中，那么再计算叶结点的时候又会再加入一次，这样不对。所以我
   们判断如果根结点至少有一个子结点，我们才提前将其加入结果res中。然后再来看求左边界结点的函数，如果当前
   结点不存在，或者没有子结点，我们直接返回。否则就把当前结点值加入结果res中，然后看如果左子结点存在，
   就对其调用递归函数，反之如果左子结点不存在，那么对右子结点调用递归函数。而对于求右边界结点的函数就
   反过来了，如果右子结点存在，就对其调用递归函数，反之如果右子结点不存在，就对左子结点调用递归函数，
   注意在调用递归函数之后才将结点值加入结果res，因为我们是需要按逆时针的顺序输出。最后就来看求叶结点
   的函数，没什么可说的，就是看没有子结点存在了就加入结果res，然后对左右子结点分别调用递归即可
 
 
 * http://blog.csdn.net/sundawei2016/article/details/73649430
 * https://discuss.leetcode.com/topic/84275/java-12ms-left-boundary-left-leaves-right-leaves-right-boundary
 * http://www.geeksforgeeks.org/tree-traversals-inorder-preorder-and-postorder/
*/
public class BoundaryOfBinaryTree {
       private class TreeNode {
		TreeNode left;
		TreeNode right;
		int val;
		public TreeNode(int x) {
			this.val = x;
		}
	}
	
        // 这道题给了我们一棵二叉树，让我们以逆时针的顺序来输出树的边界，按顺序分别为左边界，
	// 叶结点和右边界。题目中给的例子也能让我们很清晰的明白哪些算是边界上的结点。那么最直接
	// 的方法就是分别按顺序求出左边界结点，叶结点，和右边界结点。那么如何求的，对于树的操作
	// 肯定是用递归最简洁啊，所以我们可以写分别三个递归函数来分别求左边界结点，叶结点，和右边界结点。
	public List<Integer> boundaryOfBinaryTree(TreeNode root) {  
		List<Integer> result = new ArrayList<Integer>();
		if(root == null) {
			return result;
		}
		// 首先我们先要处理根结点的情况，当根结点没有左右子结点时，其也是一个叶结点，
		// 那么我们一开始就将其加入结果res中，那么再计算叶结点的时候又会再加入一次，
		// 这样不对。所以我们判断如果根结点至少有一个子结点，我们才提前将其加入结果res中。
		if(root.left != null || root.right != null) {
			result.add(root.val);
		}
		left_bound(root.left, result);
		leaves(root, result);
		right_bound(root.right, result);
		return result;
	}  
	  
	// pre-order traverse
	// 然后看如果左子结点存在，就对其调用递归函数，反之如果左子结点不存在，那么对右子结点调用递归函数。
	private void left_bound(TreeNode root, List<Integer> result) {  
        if(root == null || (root.left == null && root.right == null)) {
        	return;
        }
        result.add(root.val);
        if(root.left == null) {
        	left_bound(root.right, result);
        } else {
        	left_bound(root.left, result);
        }
	}  
	  
	// post-order traverse
	// 那么对右子结点调用递归函数。而对于求右边界结点的函数就反过来了，如果右子结点存在，就对其调用递归函数，
	// 反之如果右子结点不存在，就对左子结点调用递归函数，注意在调用递归函数之后才将结点值加入结果res，
	// 因为我们是需要按逆时针的顺序输出。
	private void right_bound(TreeNode root, List<Integer> result) {  
        if(root == null || (root.left == null && root.right == null)) {
        	return;
        }
        if(root.right == null) {
        	right_bound(root.left, result);
        } else {
        	right_bound(root.right, result);
        }
        result.add(root.val);
	}  
	
	// 最后就来看求叶结点的函数，没什么可说的，就是看没有子结点存在了就加入结果res，然后对左右子结点分别调用递归即可
	private void leaves(TreeNode root, List<Integer> result) {  
        if(root == null) {
        	return;
        }
        if(root.left == null && root.right == null) {
        	result.add(root.val);
        }
        leaves(root.left, result);
        leaves(root.right, result);
	}
   
   public static void main(String[] args) {
		BoundaryOfBinaryTree b = new BoundaryOfBinaryTree();
		TreeNode one = b.new TreeNode(1);
		TreeNode two = b.new TreeNode(2);
		TreeNode three = b.new TreeNode(3);
		TreeNode four = b.new TreeNode(4);
		TreeNode five = b.new TreeNode(5);
		TreeNode six = b.new TreeNode(6);
		TreeNode seven = b.new TreeNode(7);
		TreeNode eight = b.new TreeNode(8);
		TreeNode nine = b.new TreeNode(9);
		TreeNode ten = b.new TreeNode(10);
		one.left = two;
		one.right = three;
		two.left = four;
		two.right = five;
		five.left = seven;
		five.right = eight;
		three.left = six;
		six.left = nine;
		six.right = ten;
		List<Integer> result = b.boundaryOfBinaryTree(one);
		for(int i : result) {
			System.out.print(i + " ");
		}


}



