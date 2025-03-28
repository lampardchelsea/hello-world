/**
 * Find the sum of all left leaves in a given binary tree.
 * Example:
    3
   / \
  9  20
    /  \
   15   7
 * There are two left leaves in the binary tree, with values 9 and 15 respectively. Return 24.
 * 
 * Solution: https://my.oschina.net/styshoo/blog/757251
 * 很多人不喜欢递归，认为性能不好，这里我再提供一种非递归的思路。一般来说，想用非递归的思路来实现递归的效果，就是使用栈（stack），
 * 因为递归的实现就是潜在地使用了栈的思路。这里，我们只需要使用深度优先的方式来遍历节点，并把所有节点放入栈（push）中，
 * 之后再取出（pop）即可。 这里列出别人给出的方法：
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
    public int sumOfLeftLeaves(TreeNode root) {
        if(root == null) {
           return 0;
        }
        
        int sum = 0;
        Stack<TreeNode> stack = new Stack<TreeNode>();
        stack.push(root);
        
        while(!stack.empty()) {
           TreeNode x = stack.pop();
            
           if(x.left != null) {
              if(x.left.left == null && x.left.right == null) {
                  sum += x.left.val;
              } else {
                  stack.push(x.left);
              }
           } 
            
           if(x.right != null) {
              if(x.right.left != null || x.right.right != null) {
                  stack.push(x.right);
              }
           }
        }
        
        return sum;
    }
}
