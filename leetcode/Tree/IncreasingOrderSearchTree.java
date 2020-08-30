/**
 Refer to
 https://leetcode.com/problems/increasing-order-search-tree/
 Given a binary search tree, rearrange the tree in in-order so that the leftmost node in the tree is now the 
 root of the tree, and every node has no left child and only 1 right child.

Example 1:
Input: [5,3,6,2,4,null,8,1,null,null,null,7,9]

       5
      / \
    3    6
   / \    \
  2   4    8
 /        / \ 
1        7   9

Output: [1,null,2,null,3,null,4,null,5,null,6,null,7,null,8,null,9]

 1
  \
   2
    \
     3
      \
       4
        \
         5
          \
           6
            \
             7
              \
               8
                \
                 9  
 

Constraints:

The number of nodes in the given tree will be between 1 and 100.
Each node will have a unique integer value from 0 to 1000.
*/

// Solution 1: Approach 1: In-Order Traversal
// Refer to
// https://leetcode.com/problems/increasing-order-search-tree/solution/
/**
Intuition
The definition of a binary search tree is that for every node, all the values of the left branch are less than 
the value at the root, and all the values of the right branch are greater than the value at the root.
Because of this, an in-order traversal of the nodes will yield all the values in increasing order.

Algorithm
Once we have traversed all the nodes in increasing order, we can construct new nodes using those values to form the answer.
class Solution {    
    public TreeNode increasingBST(TreeNode root) {
        List<Integer> vals = new ArrayList();
        inorder(root, vals);
        TreeNode ans = new TreeNode(0), cur = ans;
        for (int v: vals) {
            cur.right = new TreeNode(v);
            cur = cur.right;
        }
        return ans.right;
    }

    public void inorder(TreeNode node, List<Integer> vals) {
        if (node == null) return;
        inorder(node.left, vals);
        vals.add(node.val);
        inorder(node.right, vals);
    }
}
*/
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
    public TreeNode increasingBST(TreeNode root) {
        List<Integer> list = new ArrayList<Integer>();
        inorder(root, list);
        TreeNode newRoot = new TreeNode(list.get(0));
        // Traverse with a new pointer, since need to return original 'newRoot', similar to linked list idea
        TreeNode tmp = newRoot;
        for(int i = 1; i < list.size(); i++) {
            tmp.right = new TreeNode(list.get(i));
            tmp = tmp.right;
        }
        return newRoot;
    }
    
    private void inorder(TreeNode node, List<Integer> list) {
        if(node == null) {
            return;
        }
        inorder(node.left, list);
        // Only record values instead of object since we finally need re-construct tree not move/change original tree
        list.add(node.val);
        inorder(node.right, list);
    }
}
