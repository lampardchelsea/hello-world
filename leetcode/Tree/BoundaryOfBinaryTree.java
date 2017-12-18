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
	
	public List<Integer> boundaryOfBinaryTree(TreeNode root) {  
	    List<Integer> res = new ArrayList<>();  
	    if (root == null) return res;  
	    if (root.left != null || root.right != null) res.add(root.val);  
	    left_bound(root.left, res);  
	    leaves(root, res);  
	    right_bound(root.right, res);  
	     
	    return res;  
	}  
	  
	private void left_bound(TreeNode root, List<Integer> res) {  
	    if (root == null || (root.left == null && root.right == null)) return;  
	    res.add(root.val);  
	    if (root.left == null) left_bound(root.right, res);  
	    else left_bound(root.left, res);  
	}  
	  
	private void right_bound(TreeNode root, List<Integer> res) {  
	    if (root == null || (root.left == null && root.right == null)) return;  
	    if (root.right == null) right_bound(root.left, res);  
	    else right_bound(root.right, res);  
	    res.add(root.val);  
	}  
	  
	private void leaves(TreeNode root, List<Integer> res) {  
	    if (root == null) return;  
	    if (root.left == null && root.right == null) res.add(root.val);  
	    leaves(root.left, res);  
	    leaves(root.right, res);  
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



