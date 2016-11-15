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
 * Complexity Analysis
 * Because we traverse the entire input tree once, the total run time is O(n), where nn is the total number of nodes in the tree.
 * The number of recursive calls is bound by the height of the tree. In the worst case, 
 * the tree is linear and the height is in O(n). Therefore, space complexity due to recursive calls on the stack is O(n)
 * in the worst case.
*/
// Solution 1: 
// Refer to 
// https://leetcode.com/articles/symmetric-tree/
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
      return isSymmetric(root, root);
   }
   
   public boolean isSymmetric(TreeNode x, TreeNode y) {
       if(x == null && y == null) {
           return true;
       }
       
       if(x == null || y == null) {
           return false;
       }
      
       if(x.val != y.val) {
           return false;
       }
       
       return isSymmetric(x.left, y.right) && isSymmetric(x.right, y.left);
   }
}

// Solution 2: Use Stack
public class Solution {
   public boolean isSymmetric(TreeNode root) {
      Stack<TreeNode> left = new Stack<TreeNode>();
      Stack<TreeNode> right = new Stack<TreeNode>();
      left.push(root);
      right.push(root);
      
      while(left.size() > 0 && right.size() > 0) {
          TreeNode x = left.pop();
          TreeNode y = right.pop();
          
          // Continue is most tricky part for this code
          // Refer to below link to check why use Continue
          // https://github.com/lampardchelsea/hello-world/blob/master/leetcode/Queue/SymmetricTreeQueueSolution.java
          if(x == null && y == null) {
              continue;
          }
          
          if(x == null || y == null) {
              return false;
          }
          
          if(x.val != y.val) {
              return false;
          }
          
          left.push(x.left);
          right.push(y.right);
          left.push(x.right);
          right.push(y.left);
      } 
      
      return false;
   }
}

// Solution 3: Use Queue
public class Solution {
   public boolean isSymmetric(TreeNode root) {
      Queue<TreeNode> q = new LinkedList<TreeNode>();
      q.add(root); 
      q.add(root);
       
      while(!q.isEmpty()) {
         TreeNode x = q.poll();
         TreeNode y = q.poll();
         
         // Continue is most tricky part for this code
         // Refer to below link to check why use Continue
         // https://github.com/lampardchelsea/hello-world/blob/master/leetcode/Queue/SymmetricTreeQueueSolution.java 
         if(x == null && y == null) {
             continue;
         } 
          
         if(x == null || y == null) {
             return false;
         }
          
         if(x.val != y.val) {
             return false;
         }
          
         q.add(x.left);
         q.add(y.right);
         q.add(x.right);
         q.add(y.left); 
      } 
      
      return true; 
   }
}
