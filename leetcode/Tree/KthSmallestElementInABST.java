import java.util.Stack;

/**
 * Refer to
 * https://leetcode.com/problems/kth-smallest-element-in-a-bst/discuss/ 
 * Given a binary search tree, write a function kthSmallest to find the kth smallest element in it.
 * Note: 
 * You may assume k is always valid, 1 ≤ k ≤ BST's total elements.
 * Follow up:
 * What if the BST is modified (insert/delete operations) often and you need to find the kth smallest 
 * frequently? How would you optimize the kthSmallest routine?
 * 
 * Solution
 * https://discuss.leetcode.com/topic/17810/3-ways-implemented-in-java-python-binary-search-in-order-iterative-recursive
 * https://discuss.leetcode.com/topic/17810/3-ways-implemented-in-java-python-binary-search-in-order-iterative-recursive/35?page=2
 */
public class KthSmallestElementInABST {
	private class TreeNode {
        int val;
		TreeNode left;
		TreeNode right;
		TreeNode(int x) { val = x; }
    }
	
	// Solution 1: DFS in-order recursive
    int result = 0;
    int count = 0;
    public int kthSmallest(TreeNode root, int k) {
        count = k;
        helper(root);
        return result;
    }
    
    private void helper(TreeNode root) {
        //count--;
        if(root.left != null) {
            helper(root.left);
            // Also not put 'count--' here because besides left subtree,
            // the root itself also need to be calculated when visit
            //count--;
        }
        // Inspire by below comments to explain why put 'count--' here
        // https://discuss.leetcode.com/topic/17810/3-ways-implemented-in-java-python-binary-search-in-order-iterative-recursive/18
        /**
         * The idea is to maintain rank of each node. We can keep track of elements in a subtree of any 
         * node while building the tree. Since we need K-th smallest element, we can maintain number of 
         * elements of left subtree in every node.
         * Assume that the root is having N nodes in its left subtree. If K = N + 1, root is K-th node. 
         * If K < N, we will continue our search (recursion) for the Kth smallest element in the left 
         * subtree of root. If K > N + 1, we continue our search in the right subtree for the 
         * (K – N – 1)-th smallest element. 
         * Note that we need the count of elements in left subtree only.
         * Time complexity: O(h) where h is height of tree.
         */
        // Caution: To put 'count--' ONLY after find root's left node
        // is because if there are (k - 1) node on root's left subtree,
        // then root itself is the kth node, we can count down all
        // left subtree node one by one after each time visit
        count--;
        if(count == 0) {
            result = root.val;
        }
        //count--;
        if(root.right != null) {
            helper(root.right);
        }
        //count--;
    }
    
    
    // Solution 2: DFS in-order iterative
    public int kthSmallest2(TreeNode root, int k) {
    	int count = 0;
    	Stack<TreeNode> stack = new Stack<TreeNode>();
    	TreeNode p = root;
    	while(!stack.isEmpty() || p != null) {
    		if(p != null) {
    			stack.push(p);
    			p = p.left;
    		} else {
    			TreeNode node = stack.pop();
    			count++;
    			if(count == k) {
    				return node.val;
    			}
    			p = node.right;
    		}
    	}
    	return -1;
    }
    
    public static void main(String[] args) {
    	KthSmallestElementInABST ks = new KthSmallestElementInABST();
//    	TreeNode root = ks.new TreeNode(2);
//    	TreeNode one = ks.new TreeNode(1);
//    	root.left = one;
//    	int k = 2;
    	/**
    	 * Refer to
    	 * http://www.geeksforgeeks.org/binary-search-tree-set-1-search-and-insertion/
    	 * Binary Search Tree, is a node-based binary tree data structure which has the following properties:
    	 * The left subtree of a node contains only nodes with keys less than the node’s key.
    	 * The right subtree of a node contains only nodes with keys greater than the node’s key.
    	 * The left and right subtree each must also be a binary search tree.
    	 * There must be no duplicate nodes.
    	 * The above properties of Binary Search Tree provide an ordering among keys so that the operations 
    	 * like search, minimum and maximum can be done fast. If there is no ordering, then we may have 
    	 * to compare every key to search a given key.
    	 * 
    	 * 
	              4
	             / \
	            2   5 
	           / \ 
	          1   3
    	 */
    	TreeNode root = ks.new TreeNode(4);
    	TreeNode one = ks.new TreeNode(1);
    	TreeNode two = ks.new TreeNode(2);
    	TreeNode three = ks.new TreeNode(3);
    	TreeNode five = ks.new TreeNode(5);
    	root.left = two;
    	root.right = five;
    	two.left = one;
    	two.right = three;	
    	int k = 3;
    	int result = ks.kthSmallest2(root, k);
    	System.out.println(result);
    }
}







































https://leetcode.com/problems/kth-smallest-element-in-a-bst/

Given the root of a binary search tree, and an integer k, return the kth smallest value (1-indexed) of all the values of the nodes in the tree.

Example 1:


```
Input: root = [3,1,4,null,2], k = 1
Output: 1
```

Example 2:


```
Input: root = [5,3,6,2,4,null,null,1], k = 3
Output: 3
```

Constraints:
- The number of nodes in the tree is n.
- 1 <= k <= n <= 104
- 0 <= Node.val <= 104

Follow up: If the BST is modified often (i.e., we can do insert and delete operations) and you need to find the kth smallest frequently, how would you optimize?
---
Attempt 1: 2022-12-17

Solution 1: Recursive traversal (10 min)
```
/** 
 * Definition for a binary tree node. 
 * public class TreeNode { 
 *     int val; 
 *     TreeNode left; 
 *     TreeNode right; 
 *     TreeNode() {} 
 *     TreeNode(int val) { this.val = val; } 
 *     TreeNode(int val, TreeNode left, TreeNode right) { 
 *         this.val = val; 
 *         this.left = left; 
 *         this.right = right; 
 *     } 
 * } 
 */ 
class Solution { 
    int count = 0; 
    int result = Integer.MIN_VALUE; 
    public int kthSmallest(TreeNode root, int k) { 
        count = k; 
        helper(root); 
        return result; 
    } 
    private void helper(TreeNode root) { 
        if(root == null) { 
            return; 
        } 
        helper(root.left); 
        count--; 
        if(count == 0) { 
            result = root.val;
            // Find result early return
            return; 
        } 
        helper(root.right); 
    } 
}

Time Complexity : O(N)   
Space Complexity : O(N)
```

Solution 2: Iterative traversal  (10 min)
```
/** 
 * Definition for a binary tree node. 
 * public class TreeNode { 
 *     int val; 
 *     TreeNode left; 
 *     TreeNode right; 
 *     TreeNode() {} 
 *     TreeNode(int val) { this.val = val; } 
 *     TreeNode(int val, TreeNode left, TreeNode right) { 
 *         this.val = val; 
 *         this.left = left; 
 *         this.right = right; 
 *     } 
 * } 
 */ 
class Solution { 
    public int kthSmallest(TreeNode root, int k) { 
        int count = k; 
        Stack<TreeNode> stack = new Stack<TreeNode>(); 
        while(root != null || !stack.isEmpty()) { 
            while(root != null) { 
                stack.push(root); 
                root = root.left; 
            } 
            root = stack.pop(); 
            count--; 
            if(count == 0) { 
                return root.val; 
            } 
            root = root.right; 
        } 
        return Integer.MIN_VALUE; 
    } 
}

Time Complexity : O(N)   
Space Complexity : O(N)
```
