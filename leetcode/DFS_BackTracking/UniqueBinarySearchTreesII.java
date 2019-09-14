/**
 Refer to
 https://leetcode.com/problems/unique-binary-search-trees-ii/
 Given an integer n, generate all structurally unique BST's (binary search trees) that store values 1 ... n.

Example:
Input: 3
Output:
[
  [1,null,3,2],
  [3,2,null,1],
  [3,1,null,null,2],
  [2,1,3],
  [1,null,2,null,3]
]
Explanation:
The above output corresponds to the 5 unique BST's shown below:

   1         3     3      2      1
    \       /     /      / \      \
     3     2     1      1   3      2
    /     /       \                 \
   2     1         2                 3
*/

// Solution 1:
// Refer to
// https://leetcode.com/problems/unique-binary-search-trees-ii/discuss/31494/A-simple-recursive-solution
/**
 I start by noting that 1..n is the in-order traversal for any BST with nodes 1 to n. So if I pick i-th 
 node as my root, the left subtree will contain elements 1 to (i-1), and the right subtree will contain 
 elements (i+1) to n. I use recursive calls to get back all possible trees for left and right subtrees 
 and combine them in all possible ways with the root.
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
class Solution {
    public List<TreeNode> generateTrees(int n) {
        List<TreeNode> result = new ArrayList<TreeNode>();
        if(n == 0) {
            return result;
        }
        return helper(1, n);
    }
    
    private List<TreeNode> helper(int lo, int hi) {
        List<TreeNode> list = new ArrayList<TreeNode>();
        if(lo > hi) {
            list.add(null);
            return list;
        }
        for(int i = lo; i <= hi; i++) {
            List<TreeNode> left = helper(lo, i - 1);
            List<TreeNode> right = helper(i + 1, hi);
            for(TreeNode l : left) {
                for(TreeNode r : right) {
                    TreeNode root = new TreeNode(i);
                    root.left = l;
                    root.right = r;
                    list.add(root);
                }
            }
        }
        return list;
    }
}

// Solution 2: After promote it can become Solution 1
// Refer to
// https://www.youtube.com/watch?v=GZ0qvkTAjmw
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
    public List<TreeNode> generateTrees(int n) {
        List<TreeNode> result = new ArrayList<TreeNode>();
        if(n == 0) {
            return result;
        }
        return helper(1, n);
    }
    
    private List<TreeNode> helper(int lo, int hi) {
        List<TreeNode> list = new ArrayList<TreeNode>();
        // Base case
        // Refer to
        // https://www.youtube.com/watch?v=GZ0qvkTAjmw
        /**
          Why the base case is lo > hi ?
          Because when we reach to the leave node and you still call
          recursive helper() method, the boundary will become below:
          e.g i = 5 -> left = helper(5,4), right = helper(6,5)
          hence lo > hi always the terminate condition
        */
        if(lo > hi) {
            return list;
        }
        for(int i = lo; i <= hi; i++) {
            List<TreeNode> left = helper(lo, i - 1);
            List<TreeNode> right = helper(i + 1, hi);
            // Create root should in each block since for loop
            // in the block means create multiple root required
            // TreeNode root = new TreeNode(i);
            if(left.size() == 0 && right.size() == 0) {
                TreeNode root = new TreeNode(i);
                list.add(root);
            } else if(right.size() == 0) {
                for(TreeNode l : left) {
                    TreeNode root = new TreeNode(i);
                    root.left = l;
                    list.add(root);
                }
            } else if(left.size() == 0) {
                for(TreeNode r : right) {
                    TreeNode root = new TreeNode(i);
                    root.right = r;
                    list.add(root);
                }
            } else {
                for(TreeNode l : left) {
                    for(TreeNode r : right) {
                        TreeNode root = new TreeNode(i);
                        root.left = l;
                        root.right = r;
                        list.add(root);
                    }
                }   
            }
        }
        return list;
    }
}
