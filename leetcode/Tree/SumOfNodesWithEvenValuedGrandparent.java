/**
 Refer to
 https://leetcode.com/problems/sum-of-nodes-with-even-valued-grandparent/
 Given a binary tree, return the sum of values of nodes with even-valued grandparent.  
 (A grandparent of a node is the parent of its parent, if it exists.)
 If there are no nodes with an even-valued grandparent, return 0.

 Example 1:
                6
             7     8
           2  7   1  3
         9   1 4      5

 Input: root = [6,7,8,2,7,1,3,9,null,1,4,null,null,null,5]
 Output: 18
 Explanation: The red nodes are the nodes with even-value grandparent while the blue nodes are the even-value grandparents.
 
 Constraints:
 The number of nodes in the tree is between 1 and 10^4.
 The value of nodes is between 1 and 100.
*/

// Solution 1: BFS
// Refer to
// https://leetcode.com/problems/sum-of-nodes-with-even-valued-grandparent/discuss/482991/Easy-BFS-solution-in-Java
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
    public int sumEvenGrandparent(TreeNode root) {
        if(root == null) {
            return 0;
        }
        int result = 0;
        Queue<TreeNode> q = new LinkedList<TreeNode>();
        q.offer(root);
        while(!q.isEmpty()) {
            TreeNode node = q.poll();
            if(node.left != null) {
                q.offer(node.left);
                if(node.val % 2 == 0) {
                    if(node.left.left != null) {
                        result += node.left.left.val;
                    }
                    if(node.left.right != null) {
                        result += node.left.right.val;
                    }
                }
            }
            if(node.right != null) {
                q.offer(node.right);
                if(node.val % 2 == 0) {
                    if(node.right.left != null) {
                        result += node.right.left.val;
                    }
                    if(node.right.right != null) {
                        result += node.right.right.val;
                    }
                }
            }
        }
        return result;
    }
}

// Solution 2: DFS
// Refer to
// https://leetcode.com/problems/sum-of-nodes-with-even-valued-grandparent/discuss/477095/Easy-DFS-solution
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
    int sum = 0;
    public int sumEvenGrandparent(TreeNode root) {
        helper(root, null, null);
        return sum;
    }
    
    private void helper(TreeNode curr, TreeNode parent, TreeNode grandParent) {
        if(curr == null) {
            return;
        }
        if(grandParent != null && grandParent.val % 2 == 0) {
            sum += curr.val;
        }
        helper(curr.left, curr, parent);
        helper(curr.right, curr, parent);
    }
}
