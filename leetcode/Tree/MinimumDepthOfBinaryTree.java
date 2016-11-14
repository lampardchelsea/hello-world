/**
 * Given a binary tree, find its minimum depth.
 * The minimum depth is the number of nodes along the shortest path from the root node down to the nearest leaf node.
*/
// Solution 1: With helper method
// http://www.jiuzhang.com/solutions/minimum-depth-of-binary-tree/
/**
 * Definition for a binary tree node.
 * public class TreeNode {
 *     int val;
 *     TreeNode left;
 *     TreeNode right;
 *     TreeNode(int x) { val = x; }
 * }
 */
public class Solution {
    public static int minDepth(TreeNode root) {
        if(root == null) {
            return 0;
        }

        return helper(root);
    }
    
    public static int helper(TreeNode x) {
        if(x == null) {
            return Integer.MAX_VALUE;
        }
        
        if(x.left == null && x.right == null) {
            return 1;
        }
        
        int left = helper(x.left);
        int right = helper(x.right);
        
        return Math.min(left, right) + 1;
    }
}


// Solution 2: Without helper method
// http://www.geeksforgeeks.org/find-minimum-depth-of-a-binary-tree/
// https://algorithm.yuanbin.me/zh-tw/exhaustive_search/minimum_depth_of_binary_tree.html
/**
 * The idea is to traverse the given Binary Tree. For every node, check if it is a leaf node. 
 * If yes, then return 1. If not leaf node then if left subtree is NULL, then recur for right 
 * subtree. And if right subtree is NULL, then recur for left subtree. If both left and right 
 * subtrees are not NULL, then take the minimum of two heights
*/
public class Solution {
    public static int minDepth(TreeNode root) {
        // Corner case. Should never be hit unless the code is
        // called on root = NULL
        if(root == null) {
            return 0;
        }
        
        // Base case : Leaf Node. This accounts for height = 1.
        // but actually this case will be included in below two cases,
        // as test, this base case is NOT necessary
        if(root.left == null && root.right == null) {
            return 1;
        }
        
        int leftDepth = minDepth(root.left);
        int rightDepth = minDepth(root.right);
        
        // If left subtree is NULL, recur for right subtree
        if(root.left == null) {
            return rightDepth + 1;
        }
        
        // If left subtree is NULL, recur for right subtree
        if(root.right == null) {
            return leftDepth + 1; 
        }
        
        // If both left and right subtrees are not NULL, then take 
        // the minimum of two heights
        return Math.min(leftDepth, rightDepth) + 1;
    }
}


// Solution 3: With use of Queue
// Refer to 
// https://github.com/lampardchelsea/hello-world/blob/master/leetcode/Queue/MinimumDepthofBinaryTreeQueueSolution.java
public class Solution {
  public static int minDepth(TreeNode root){
     if(root == null) {
        return 0;
     }
     
     Queue<TreeNode> currentLevel = new LinkedList<TreeNode>();

     int depth = 1;
     currentLevel.add(root);
     while(!currentLevel.isEmpty()) {
	 Queue<TreeNode> nextLevel = new LinkedList<TreeNode>(); 
	 int size = currentLevel.size();
	 for(int i = 0; i < size; i++) {
	    TreeNode x = currentLevel.poll();
	    if(x.left != null) {
	        nextLevel.add(x.left);
	    }
	    if(x.right != null) {
		nextLevel.add(x.right);
	    }
	    if(x.left == null && x.right == null) {
	        return depth;
	    }
	 }
	 depth++;
	 currentLevel = nextLevel;
     }
     return depth;
  }
}
