/**
 * Refer to
 * http://blog.leanote.com/post/westcode/%5B%E5%88%B7%E9%A2%98%E7%AC%94%E8%AE%B0%5D-LeetCode-LintCode-3
 * Given a binary tree, find the subtree with minimum sum. Return the root of the subtree.
    Example
    Given a binary tree:

             1
           /   \
         -5     2
         / \   /  \
        0   2 -4  -5 
    return the node 1.
* 
* Solution
* http://www.jiuzhang.com/solutions/minimum-subtree/
* http://blog.leanote.com/post/westcode/%5B%E5%88%B7%E9%A2%98%E7%AC%94%E8%AE%B0%5D-LeetCode-LintCode-3
*/

// Solution 1: Traverse + Divide And Conquer
public class Solution {
    private TreeNode subTree = null;
    private int subSum = Integer.MAX_VALUE;
    /**
     * @param root the root of binary tree
     * @return the root of the minimum subtree
     */
    public TreeNode findSubtree(TreeNode root) {
        helper(root);
        return subTree;
    }
    
    // Actually as must return current sum value, not
    // exactly match the traverse requirement as
    // return void, so its combination of Traverse
    // and Divide & Conquer
    public int helper(TreeNode root) {
        // Base case
        if(root == null) {
            return root;
        }
        int sum = helper(root.left) + helper(root.right) + root.val;
        if(sum <= subSum) {
            subSum = sum;
            subTree = root;
        }
        // Must return current sum value as recursively calling inside helper
        // when calculate sum
        return sum; 
    }
    
}


// Solution 2: Pure Divide And Conquer
public class Solution {    
    public TreeNode findSubtree(TreeNode root) {
        ResultType result = helper(root);
        return result.minSubtree;
    }
    
    public ResultType helper(TreeNode node) {
        // Base case
        if(node == null) {
            return new ResultType(null, Integer.MAX_VALUE, 0);
        }
        // Divide
        ResultType leftResult = helper(root.left);
        ResultType rightResult = helper(root.right);
        // Merge
        ResultType result = new ResultType(node, leftResult.sum + rightResult.sum + node.val, leftResult.sum + rightResult.sum + node.val);
        if(leftResult.minSum <= result.minSum) {
            result.minSum = leftResult.minSum;
            result.minSubtree = leftResult.minSubtree;
        }
        if(rightResult.minSum <= result.minSum) {
            result.minSum = rightResult.minSum;
            result.minSubtree = rightResult.minSubtree;
        }
        return result;
    }
}
// Create helper class for returning multiple values
class ResultType {
    public TreeNode minSubtree;
    public int sum;
    public int minSum;
    public ResultType(TreeNode minSubtree, int minSum, int sum) {
        this.minSubtree = minSubtree;
        this.minSum = minSum;
        this.sum = sum;
    }
}



