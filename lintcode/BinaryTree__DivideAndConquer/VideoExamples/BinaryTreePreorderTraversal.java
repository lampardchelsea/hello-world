/**
 * Refer to 
 * http://www.lintcode.com/en/problem/binary-tree-preorder-traversal/
 * Given a binary tree, return the preorder traversal of its nodes' values.
 * Have you met this question in a real interview?
   Example
   Given:
        1
       / \
      2   3
     / \
    4   5
   return [1,2,4,5,3].
 *
 * Solution
 * http://www.jiuzhang.com/solutions/binary-tree-preorder-traversal/
*/


// Solution 1: Traverse
/**
 * Definition of TreeNode:
 * public class TreeNode {
 *     public int val;
 *     public TreeNode left, right;
 *     public TreeNode(int val) {
 *         this.val = val;
 *         this.left = this.right = null;
 *     }
 * }
 */
public class Solution {
    /**
     * @param root: The root of binary tree.
     * @return: Preorder in ArrayList which contains node values.
     */
    public ArrayList<Integer> preorderTraversal(TreeNode root) {
        // Traverse
        ArrayList<Integer> result = new ArrayList<Integer>();
        traverse(result, root);
        return result;
    }
    
    // Definition: Create helper method to help adding 'node' 
    // based preorder sequence onto 'list', return as void,
    // pass in global variable 'list' as parameter
    public void traverse(ArrayList<Integer> list, TreeNode node) {
        // Base case
        // Note: Usually very limited chance to care about
        // leaf node cases, only handle root node as null
        // is fine
        if(node == null) {
            return;
        }
        // Divide into 3 parts: node/ left/ right
        list.add(node.val);
        // Call helper method itself recursively
        traverse(list, node.left);
        traverse(list, node.right);
    }
}

// Note: This problem not very suitable on Divide And Conquer, as
// required return ArrayList, the time complexity will increase
// Solution 2: Divide And Conquer
/**
 * Definition of TreeNode:
 * public class TreeNode {
 *     public int val;
 *     public TreeNode left, right;
 *     public TreeNode(int val) {
 *         this.val = val;
 *         this.left = this.right = null;
 *     }
 * }
 */
public class Solution {
    /**
     * @param root: The root of binary tree.
     * @return: Preorder in ArrayList which contains node values.
     */
    // Definition: Return preorder based on 'root', not like traverse
    // method return as null, not pass in global 'result' as parameter
    public ArrayList<Integer> preorderTraversal(TreeNode root) {
        // Divide and Conquer
        ArrayList<Integer> result = new ArrayList<Integer>();
        // Base case (null or leaf)
        if(root == null) {
            return result;
        }
        
        // Divide (Use preorderTraversal method itself, thearatically
        // separate task and handle by sub method and return sub result, 
        // not depend on helper method, also no need to pass global 
        // variable result in)
        ArrayList<Integer> left = preorderTraversal(root.left);
        ArrayList<Integer> right = preorderTraversal(root.right);
        
        // Merge (As required by preorder, adding order as 
        // root -> left -> right)
        result.add(root.val);
        result.addAll(left);
        result.addAll(right);
        return result;
    }
}


