/**
 Refer to
 https://leetcode.com/problems/maximum-difference-between-node-and-ancestor/
 Given the root of a binary tree, find the maximum value V for which there exists different 
 nodes A and B where V = |A.val - B.val| and A is an ancestor of B.

(A node A is an ancestor of B if either: any child of A is equal to B, or any child of A is 
an ancestor of B.)

Example 1:
                  8
            3          10
          1   6           14
            4   7      13
Input: [8,3,10,1,6,null,14,null,null,4,7,13]
Output: 7
Explanation: 
We have various ancestor-node differences, some of which are given below :
|8 - 3| = 5
|3 - 7| = 4
|8 - 1| = 7
|10 - 13| = 3
Among all possible differences, the maximum value of 7 is obtained by |8 - 1| = 7.
 
Note:
The number of nodes in the tree is between 2 and 5000.
Each node will have value between 0 and 100000.
*/
// Solution 1:
// Refer to
// https://leetcode.com/problems/maximum-difference-between-node-and-ancestor/discuss/274610/JavaC%2B%2BPython-Top-Down
// We pass the minimum and maximum values to the children,
// At the leaf node, we return max - min through the path from the root to the leaf.
// And no need to worry about order required by (ancestor - current) because in the final calculation format
// every difference is calculated by |ancestor - current|, which means if 3 is ancestor and 8 is current,
// the difference can by found by |3 - 8| as |current - ancestor|, no order required since absolute
/**
 * Definition for a binary tree node.
 * public class TreeNode {
 *     int val;
 *     TreeNode left;
 *     TreeNode right;
 *     TreeNode(int x) { val = x; }
 * }
 */
class Solution {
    public int maxAncestorDiff(TreeNode root) {
        return helper(root, root.val, root.val);
    }
    // Top-down by using pre-order traverse
    private int helper(TreeNode root, int min, int max) {
        if(root == null) {
            return max - min;
        }
        min = Math.min(min, root.val);
        max = Math.max(max, root.val);
        int leftDiff = helper(root.left, min, max);
        int rightDiff = helper(root.right, min, max)
        return Math.max(leftDiff, rightDiff);
    }
}

// Solution 2:
// Refer to
// https://leetcode.com/problems/maximum-difference-between-node-and-ancestor/discuss/278183/Java-DFS
/**
 * Definition for a binary tree node.
 * public class TreeNode {
 *     int val;
 *     TreeNode left;
 *     TreeNode right;
 *     TreeNode(int x) { val = x; }
 * }
 */
class Solution {
    int result = 0;
    public int maxAncestorDiff(TreeNode root) {
        helper(root, root.val, root.val);
        return result;
    }
    
    private void helper(TreeNode root, int min, int max) {
        if(root == null) {
            return;
        }
        min = Math.min(min, root.val);
        max = Math.max(max, root.val);
        result = Math.max(result, max - min);
        helper(root.left, min, max);
        helper(root.right, min, max);
    }
}

// Testing
/**
                     3
                 /       \
                5         1
              /   \	    /   \
             6     2   0     8
                 /   \
                7     4
				
Top-Down pre-order traverse
root(3) -> min = 3, max = 3
  -> root(5) -> min = 3, max = 5
    -> root(6) -> min = 3, max = 6
      -> return 6 - 3 
      -> leftDiff = 3
      -> return 6 - 3
      -> rightDiff = 3
      -> return Math.max(3, 3)
    -> root(2) -> min = 2, max = 5
      -> root(7) -> min = 2, max = 7
        -> return 7 - 2
        -> leftDiff = 5
        -> return 7 - 2
        -> rightDiff = 5
        -> return Math.max(5, 5)
      -> root(4) -> min = 2, max = 5
        -> return 5 - 2
        -> leftDiff = 3
        -> return 5 - 2
        -> rightDiff = 3
        -> return Math.max(3, 3)
      -> return Math.max(5, 3)
    -> return Math.max(3, 5)
  -> root(1) -> min = 1, max = 3
    -> root(0) -> min = 0, max = 3
    -> return 3 - 0
    -> leftDiff = 3
    -> return 3 - 0
    -> rightDiff = 3
    -> return Math.max(3, 3)
    -> root(8) -> min = 1, max = 8
    -> return 8 - 1
    -> leftDiff = 7
    -> return 8 - 1
    -> rightDiff = 7
    -> return Math.max(7, 7)
  -> return Math.max(5, 7)
-> return 7
*/
public class Solution {
    public static void main(String[] args) {
        /**
         * Test with below binary tree
         * 
         *           3
         *       /       \
                5         1
              /   \	    /   \
             6     2   0     8
                 /   \
                7     4
         */
        Solution q = new Solution();
        TreeNode root = q.new TreeNode(3);
        root.left = q.new TreeNode(5);
        root.right = q.new TreeNode(1);
        root.left.left = q.new TreeNode(6);
        root.left.right = q.new TreeNode(2);
        root.left.right.left = q.new TreeNode(7);
        root.left.right.right = q.new TreeNode(4);
        root.right.left = q.new TreeNode(0);
        root.right.right = q.new TreeNode(8);
        int result = q.maxAncestorDiff(root);
        System.out.println(result);
    }

    class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;
        TreeNode() {}
        TreeNode(int val) {
            this.val = val;
        }
        TreeNode(int val, TreeNode left, TreeNode right) {
            this.val = val;
            this.left = left;
            this.right = right;
        }
    }

    public int maxAncestorDiff(TreeNode root) {
        return helper(root, root.val, root.val);
    }

    private int helper(TreeNode root, int min, int max) {
        if (root == null) {
            return max - min;
        }
        min = Math.min(min, root.val);
        max = Math.max(max, root.val);
        int leftDiff = helper(root.left, min, max);
        int rightDiff = helper(root.right, min, max);
        return Math.max(leftDiff, rightDiff);
    }
}
