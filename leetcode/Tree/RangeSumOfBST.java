/**
 Refer to
 https://leetcode.com/problems/range-sum-of-bst/
 Given the root node of a binary search tree, return the sum of values of all nodes with value 
 between L and R (inclusive).
 The binary search tree is guaranteed to have unique values.
 
 Example 1:
 Input: root = [10,5,15,3,7,null,18], L = 7, R = 15
 Output: 32

 Example 2:
 Input: root = [10,5,15,3,7,13,18,1,null,6], L = 6, R = 10
 Output: 23
 
 Note:
 The number of nodes in the tree is at most 10000.
 The final answer is guaranteed to be less than 2^31.
*/

// Solution 1: Recursive
// Refer to
// https://leetcode.com/problems/range-sum-of-bst/discuss/192019/JavaPython-3-3-similar-recursive-and-1-iterative-methods-w-comment-and-analysis.
class Solution {
    int sum = 0;
    public int rangeSumBST(TreeNode root, int L, int R) {
        helper(root, L, R);
        return sum;
    }
    
    private void helper(TreeNode root, int L, int R) {
        if(root == null) {
            return;
        }
        helper(root.left, L, R);
        if(root.val >= L && root.val <= R) {
            sum += root.val;
        }
        helper(root.right, L, R);
    }
}

// Solution 2: Iterative
// Refer to
// https://leetcode.com/problems/range-sum-of-bst/discuss/192019/JavaPython-3-3-similar-recursive-and-1-iterative-methods-w-comment-and-analysis.
class Solution {
    public int rangeSumBST(TreeNode root, int L, int R) {
        Stack<TreeNode> stack = new Stack<TreeNode>();
        stack.push(root);
        int sum = 0;
        while(!stack.isEmpty()) {
            TreeNode node = stack.pop();
            if(node == null) {
                continue;
            }
            if(node.val > L) {
                stack.push(node.left); // left child is a possible candidate.
            }
            if(node.val < R) {
                stack.push(node.right); // right child is a possible candidate.
            }
            if(node.val >= L && node.val <= R) {
                sum += node.val; // count root in.
            }
        }
        return sum;
    }
}



