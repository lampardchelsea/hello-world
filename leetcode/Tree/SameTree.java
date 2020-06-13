/**
 * Given two binary trees, write a function to check if they are equal or not.
 * Two binary trees are considered equal if they are structurally identical and the nodes have the same value.
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
    public boolean isSameTree(TreeNode p, TreeNode q) {
        if(p == null && q == null) {
            return true;
        } else if((p == null && q != null) || (p != null && q == null)) {
            return false;
        }
        
        if(p.val == q.val) {
            return (isSameTree(p.left, q.left) && isSameTree(p.right, q.right));
        } else {
            return false;
        }
    }
}

// Re-work
// Refer to
// https://www.cnblogs.com/grandyang/p/4053384.html
// https://leetcode.com/problems/same-tree/solution/
// Solution 1: Pre-order recursive (DFS)
// Refer to
// https://leetcode.com/problems/same-tree/discuss/32687/Five-line-Java-solution-with-recursion/31600
/**
 public boolean isSameTree(TreeNode p, TreeNode q) {
    
    // Equal nullity denotes that this branch is the same (local equality)
    // This is a base case, but also handles being given two empty trees
    if (p == null && q == null) return true;
    
    // Unequal nullity denotes that the trees aren't the same
    // Note that we've already ruled out equal nullity above
    else if (p == null || q == null) return false;
        
    // Both nodes have values; descend iff those values are equal
    // "&&" here allows for any difference to overrule a local equality
    if (p.val == q.val) return isSameTree(p.left, q.left) && isSameTree(p.right, q.right);
    
    // If we're here, both nodes have values, and they're unequal, so the trees aren't the same
    return false;
}
*/
/**
 判断两棵树是否相同和之前的判断两棵树是否对称都是一样的原理，利用深度优先搜索 DFS 来递归
*/
class Solution {
    public boolean isSameTree(TreeNode p, TreeNode q) {
        // p and q are both null
        if(p == null && q == null) {
            return true;
        }
        // one of p and q is null
        if(p == null || q == null) {
            return false;
        }
        if(p.val != q.val) {
            return false;
        }
        return isSameTree(p.left, q.left) && isSameTree(p.right, q.right);
    }
}

// Solution 2: Pre-order iterative traverse
// Refer to
// Two queue version
// https://leetcode.com/problems/same-tree/discuss/32684/My-non-recursive-method
// One queue version
// https://leetcode.com/problems/same-tree/discuss/32684/My-non-recursive-method/119184
/**
  这道题还有非递归的解法，因为二叉树的四种遍历(层序，先序，中序，后序)
  均有各自的迭代和递归的写法，这里我们先来看先序的迭代写法，相当于同时
  遍历两个数，然后每个节点都进行比较, 可参见之间那道 Binary Tree Preorder Traversal
*/
class Solution {
    public boolean isSameTree(TreeNode p, TreeNode q) {
        Stack<TreeNode> stack_p = new Stack<TreeNode>();
        Stack<TreeNode> stack_q = new Stack<TreeNode>();
        if(p != null) {
            stack_p.push(p);            
        }
        if(q != null) {
            stack_q.push(q);            
        }
        while(!stack_p.isEmpty() && !stack_q.isEmpty()) {
            TreeNode pn = stack_p.pop();
            TreeNode qn = stack_q.pop();
            if(pn.val != qn.val) {
                return false;
            }
            if(pn.right != null) {
                stack_p.push(pn.right);
            }
            if(qn.right != null) {
                stack_q.push(qn.right);
            }
            if(stack_p.size() != stack_q.size()) {
                return false;
            }
            if(pn.left != null) {
                stack_p.push(pn.left);
            }
            if(qn.left != null) {
                stack_q.push(qn.left);
            }
            if(stack_p.size() != stack_q.size()) {
                return false;
            }
        }
        return stack_p.size() == stack_q.size();
    }
}
