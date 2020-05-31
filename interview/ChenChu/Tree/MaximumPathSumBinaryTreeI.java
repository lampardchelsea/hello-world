/**
 Refer to
 https://hjweds.gitbooks.io/leetcode/maximum-path-sum-binary-tree-i.html
 https://www.geeksforgeeks.org/find-maximum-path-sum-two-leaves-binary-tree/
 Find the maximum path sum between two leaves of a binary tree
 Given a binary tree in which each node element contains a number. Find the maximum possible sum 
 from one leaf node to another.
 The maximum sum path may or may not go through root. For example, in the following binary tree, 
 the maximum sum is 27(3 + 6 + 9 + 0 – 1 + 10). Expected time complexity is O(n).
 If one side of root is empty, then function should return minus infinite (INT_MIN in case of C/C++)
         -15
        /    \
      5        6
     / \      / \
   -8   1    3   9
  /  \            \
 2    6            0
                  / \
                 4  -1
                    /
                   10
*/

// Solution 1:
// Refer to
// https://www.geeksforgeeks.org/find-maximum-path-sum-two-leaves-binary-tree/
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
        int result = q.maxPathSum(root);
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

    int max = Integer.MIN_VALUE;
    public int maxPathSum(TreeNode root) {
        if (root == null || root.left == null || root.right == null) {
            return max;
        }
        helper(root);
        return max;
    }

    private int helper(TreeNode root) {
        if (root == null) {
            return 0;
        }
        int left = helper(root.left);
        int right = helper(root.right);
        // left和right非空时才更新max 
        // (Solution: 注意是leaf to leaf，所以只有root.left != null && root.right != null时才更新max, 
        //注意recursion返回的是一支路径)
        if (root.left != null && root.right != null) {
            int sum = root.val + left + right;
            max = Math.max(max, sum);
            return Math.max(left, right) + root.val;
        }
        //注意不是return Math.max(left, right) + root.key; eg:  
        //     1
        //   /  \
        //      -1 应该返回非空的那支路径
        return root.left == null ? right + root.val : left + root.val;
    }
}
