/**
 * Given a binary tree, check whether it is a mirror of itself (ie, symmetric around its center).
 * For example, this binary tree [1,2,2,3,4,4,3] is symmetric:
    1
   / \
  2   2
 / \ / \
3  4 4  3
 * But the following [1,2,2,null,3,null,3] is not:
    1
   / \
  2   2
   \   \
   3    3
 * Note:
 * Bonus points if you could solve it both recursively and iteratively.
 *
 * Refer to 
 * http://www.acmerblog.com/leetcode-solution-symmetric-tree-6291.html
 * https://github.com/lampardchelsea/hello-world/blob/master/leetcode/Queue/SymmetricTreeQueueSolution.java
*/
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
   public boolean isSymmetric(TreeNode root) {
      if(root == null) {
          return true;
      } 
      
      // This is very similar to the way use queue, queue is pop 2 items
      // and push 2 items every time, but on same queue, here we separate
      // these 2 items into two side stacks, every time push and pop for
      // 1 item at the same time, then compare its value, the compare
      // conditions are same as using queue.
      // Refer to 
      // https://github.com/lampardchelsea/hello-world/blob/master/leetcode/Queue/SymmetricTreeQueueSolution.java
      Stack<TreeNode> left = new Stack<TreeNode>();
      Stack<TreeNode> right = new Stack<TreeNode>();
      
      left.push(root.left);
      right.push(root.right);
      
      while(left.size() > 0 && right.size() > 0) {
          TreeNode p = left.pop();
          TreeNode q = right.pop();
          
          if(p == null && q == null) {
              continue;
          }
          
          if(p != null && q == null || p == null && q != null) {
              return false;
          }
          
          if(p.val != q.val) {
              return false;
          } 
          
          left.push(p.left);
          right.push(q.right);
          left.push(p.right);
          right.push(q.left);
      }
      
      return true;
   }
}
