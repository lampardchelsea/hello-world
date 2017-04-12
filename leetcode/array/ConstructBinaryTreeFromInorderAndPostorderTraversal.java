import java.util.HashMap;
import java.util.Map;

/**
 * Refer to
 * https://leetcode.com/problems/construct-binary-tree-from-inorder-and-postorder-traversal/#/description
 * Given inorder and postorder traversal of a tree, construct the binary tree.
 * Note:
 * You may assume that duplicates do not exist in the tree.
 */
public class ConstructBinaryTreeFromInorderAndPostorderTraversal {
	private class TreeNode {
		int val;
		TreeNode left;
		TreeNode right;
		TreeNode(int x) {
			val = x;
		}
	}
	
	/**
	 * Refer to
	 * https://en.wikipedia.org/wiki/Tree_traversal
	 * E.g                    
	 *                     F
	 *                   /   \
	 *                  B     G
	 *                 / \     \
	 *                A   D     I
	 *                   / \   / 
	 *                  C  E  H
	 *  In-order
	 *  (1) Check if the current node is empty / null.
	 *  (2) Traverse the left subtree by recursively calling the in-order function.
	 *  (3) Display the data part of the root (or current node).
	 *  (4) Traverse the right subtree by recursively calling the in-order function.
	 *  
	 * 
	 *  Post-order
	 *  (1) Check if the current node is empty / null.
	 *  (2) Traverse the left subtree by recursively calling the post-order function.
	 *  (3) Traverse the right subtree by recursively calling the post-order function.
	 *  (4) Display the data part of the root (or current node).
	 *  
	 *  Refer to
	 *  http://www.cnblogs.com/grandyang/p/4296193.html
	 *  https://siddontang.gitbooks.io/leetcode-solution/content/tree/construct_binary_tree.html
	 *  这道题要求从中序和后序遍历的结果来重建原二叉树，我们知道中序的遍历顺序是左-根-右，后序的顺序是
	 *  左-右-根，对于这种树的重建一般都是采用递归来做，可参见我之前的一篇博客Convert Sorted Array 
	 *  to Binary Search Tree 将有序数组转为二叉搜索树。针对这道题，由于后序的顺序的最后一个肯定是根，
	 *  所以原二叉树的根节点可以知道，题目中给了一个很关键的条件就是树中没有相同元素，有了这个条件我们
	 *  就可以在中序遍历中也定位出根节点的位置，并以根节点的位置将中序遍历拆分为左右两个部分，分别对其
	 *  递归调用原函数: 通过后续遍历找到根节点，然后在中序遍历数据中根据根节点拆分成两个部分，同时将对
	 *  应的后序遍历的数据也拆分成两个部分，重复递归，就可以得到整个二叉树了。
	 *  
	 *  Refer to
	 *  http://www.geeksforgeeks.org/construct-a-binary-tree-from-postorder-and-inorder/
	 *  Input : 
		in[]   = {4, 8, 2, 5, 1, 6, 3, 7}
		post[] = {8, 4, 5, 2, 6, 7, 3, 1} 
		
		Output : Root of below tree
		          1
		       /     \
		     2        3
		   /    \   /   \
		  4     5   6    7
		    \
		      8
	 *  
	 *  Let us see the process of constructing tree from in[] = {4, 8, 2, 5, 1, 6, 3, 7} 
	 *  and post[] = {8, 4, 5, 2, 6, 7, 3, 1}
	 *  1) We first find the last node in post[]. The last node is “1”, we know this value 
	 *  is root as root always appear in the end of postorder traversal.
	 *  2) We search “1” in in[] to find left and right subtrees of root. Everything on left 
	 *  of “1” in in[] is in left subtree and everything on right is in right subtree.
		         1
		       /    \
		[4,8,2,5]   [6,3,7] 
		3) We recur the above process for following two.
		….b) Recur for in[] = {6,3,7} and post[] = {6,7,3}
		…….Make the created tree as right child of root.
		….a) Recur for in[] = {4,8,2,5} and post[] = {8,4,5,2}.
		…….Make the created tree as left child of root.
	 *  One important observation is, we recursively call for right subtree before left subtree 
	 *  as we decrease index of postorder index whenever we create a new node.
	 */
	
	// Solution 1: Without Hashmap using recursive
	/**
	 *  Refer to
	 *  https://discuss.leetcode.com/topic/3296/my-recursive-java-code-with-o-n-time-and-o-n-space/2
	 */
	int inorderIndex;
	int postorderIndex;
	public TreeNode buildTree(int[] inorder, int[] postorder) {
		inorderIndex = inorder.length - 1;
		postorderIndex = postorder.length - 1;
		return buildTreeHelper(inorder, postorder, null);
	}
	
	public TreeNode buildTreeHelper(int[] inorder, int[] postorder, TreeNode end) {
		if(postorderIndex < 0) {
			return null;
		}
		// Create root based on postorder array
		// Tricky tip: postorderIndex will decrease 1 every recursion from root
		// node which always at end of array
		TreeNode root = new TreeNode(postorder[postorderIndex--]);
		// If right node exist, create right subtree
		// inorder: left -> root -> right
		// postorder: left -> right -> root
		// Tricky tip: root -> right / right -> root, the order difference help
		// us find current item on inorder has right child tree, because only
		// one case will not influenced by order difference, as this item already 
		// seat as leave node on binary tree has the same value as root node
		// generated from postorder array
		if(inorder[inorderIndex] != root.val) {
			root.right = buildTreeHelper(inorder, postorder, root);
		}
		inorderIndex--;
		// If left node exist, create left subtree
		// Be careful, here the condition change from 'root' to 'end'
		if(end == null || inorder[inorderIndex] != end.val) {
			root.left= buildTreeHelper(inorder, postorder, end);
		}
		return root;
	}
	
	
	// Solution 2: Using Hashmap with recursive
	/**
	 * Refer to
	 * https://discuss.leetcode.com/topic/3296/my-recursive-java-code-with-o-n-time-and-o-n-space
	 * The the basic idea is to take the last element in postorder array as the root, find the position of 
	 * the root in the inorder array; then locate the range for left sub-tree and right sub-tree and do 
	 * recursion. Use a HashMap to record the index of root in the inorder array.
	 */
    public TreeNode buildTree2(int[] inorder, int[] postorder) {
        if(inorder == null || postorder == null || inorder.length != postorder.length) {
            return null;
        }
        Map<Integer, Integer> map = new HashMap<Integer, Integer>();
        for(int i = 0; i < inorder.length; i++) {
            map.put(inorder[i], i);
        }
        return buildTreeHelper2(inorder, 0, inorder.length - 1, postorder, 0, postorder.length - 1, map);
    }
    
    public TreeNode buildTreeHelper2(int[] inorder, int inorderStart, int inorderEnd, int[] postorder, int postorderStart, int postorderEnd, Map<Integer, Integer> map) {
        if(inorderStart > inorderEnd || postorderStart > postorderEnd) {
            return null;
        }
        TreeNode root = new TreeNode(postorder[postorderEnd]);
        int rootIndexOnInorder = map.get(postorder[postorderEnd]);
        /**
         * Refer to
         * https://discuss.leetcode.com/topic/3296/my-recursive-java-code-with-o-n-time-and-o-n-space/11
         * https://discuss.leetcode.com/topic/3296/my-recursive-java-code-with-o-n-time-and-o-n-space/21
         * The post order array will give you the root, the last one.
         * With the root, you can go to the in order array, notice the traverse sequence: left, root, right.
         * Then we know the left child array size, right child array size.
         * With the size, we can then divide the post order array: left, right, root.
         * Then, we have everything!
         * The beauty is the root, with the root, you are able to divide two arrays~
         * E.g 
         * in[] = {4, 8, 2, 5, 1, 6, 3, 7} 
         * post[] = {8, 4, 5, 2, 6, 7, 3, 1}
         * In first iteration, the index of root "1" on in[] array is 4, left child array on 'inorder' size is
         * 4(rootIndexOnInorder - inorderStart = 4 - 0), to find left child array section on 'postorder' requires
         * {postorderStart + (rootIndexOnInorder - inorderStart) - 1} as section end, the additional "- 1" for get index. 
         * right child array on 'inorder' size is 3(inorderEnd - rootIndexOnInorder = 7 - 4), to find right
         * child array section on 'postorder' requires {postorderStart + (rootIndexOnInorder - inorderStart) - 1 + 1} 
         * as section start, the additional "+ 1" for next index after left child array section, also requires
         * {postorderEnd - 1} as section end, the additional "- 1" for previous index of final item as root in 'postorder'
         */
        TreeNode leftChild = buildTreeHelper2(inorder, inorderStart, rootIndexOnInorder - 1, postorder, postorderStart, postorderStart + (rootIndexOnInorder - inorderStart) - 1, map);
        TreeNode rightChild = buildTreeHelper2(inorder, rootIndexOnInorder + 1, inorderEnd, postorder, postorderStart + (rootIndexOnInorder - inorderStart), postorderEnd - 1, map);
        root.left = leftChild;
        root.right = rightChild;
        return root;
    }
	
	public static void main(String[] args) {
		ConstructBinaryTreeFromInorderAndPostorderTraversal c = new ConstructBinaryTreeFromInorderAndPostorderTraversal();
		int[] inorder = {4, 8, 2, 5, 1, 6, 3, 7};
		int[] postorder = {8, 4, 5, 2, 6, 7, 3, 1};
		TreeNode result = c.buildTree(inorder, postorder);
		//TreeNode result = c.buildTree2(inorder, postorder);
		System.out.println(result.val);
	}
}
