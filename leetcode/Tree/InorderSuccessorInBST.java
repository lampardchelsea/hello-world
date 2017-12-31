/**
 * Refer to
 * http://www.cnblogs.com/grandyang/p/5306162.html
 * Given a binary search tree and a node in it, find the in-order successor of that node in the BST.
 * Note: If the given node has no in-order successor in the tree, return null.
 * 
 * Solution
 * https://discuss.leetcode.com/topic/33228/java-5ms-short-code-with-explanations
 * https://www.youtube.com/watch?v=PyK4nl-MfNs
 * https://discuss.leetcode.com/topic/25076/share-my-java-recursive-solution
*/

// Solution 1: Iterative
// Refer to
// https://discuss.leetcode.com/topic/33228/java-5ms-short-code-with-explanations
/**
 * The idea is to compare root's value with p's value if root is not null, and consider the 
   following two cases:
   root.val > p.val. In this case, root can be a possible answer, so we store the root node first 
   and call it res. However, we don't know if there is anymore node on root's left that is larger 
   than p.val. So we move root to its left and check again.
   root.val <= p.val. In this case, root cannot be p's inorder successor, neither can root's left 
   child. So we only need to consider root's right child, thus we move root to its right and check 
   again.
   We continuously move root until exhausted. To this point, we only need to return the res in case 1.
*/
public TreeNode inorderSuccessor(TreeNode root, TreeNode p) {
    TreeNode result = null;
    while(root != null) {
        if(root.val > p.val) {
            result = root;
            root = root.left;
        } else {
            root = root.right;
        }
    }
    return result;
}
