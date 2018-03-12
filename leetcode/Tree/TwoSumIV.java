/**
 Refer to
 https://leetcode.com/problems/two-sum-iv-input-is-a-bst/description/
 Given a Binary Search Tree and a target number, return true if there exist two elements in the 
 BST such that their sum is equal to the given target.

    Example 1:
    Input: 
        5
       / \
      3   6
     / \   \
    2   4   7

    Target = 9

    Output: True
    Example 2:
    Input: 
        5
       / \
      3   6
     / \   \
    2   4   7

    Target = 28

    Output: False
 
 Solution
  https://leetcode.com/problems/two-sum-iv-input-is-a-bst/discuss/106059/JavaC++-Three-simple-methods-choose-one-you-like
  Method 1.
  This method also works for those who are not BSTs. The idea is to use a hashtable to save the values of the nodes 
  in the BST. Each time when we insert the value of a new node into the hashtable, we check if the hashtable 
  contains k - node.val.
  
  Time Complexity: O(n), Space Complexity: O(n).
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
    public boolean findTarget(TreeNode root, int k) {
        if(root == null) {
            return false;
        }
        Set<Integer> set = new HashSet<Integer>();
        return helper(root, k, set);
    }
    
    private boolean helper(TreeNode root, int k, Set<Integer> set) {
        if(root == null) {
            return false;
        }
        // Should not add first in case of k - root.val == root.val
        // e.g root = [1], k = 2
        // set.add(root.val);
        if(set.contains(k - root.val)) {
            return true;
        }
        set.add(root.val);
        return helper(root.left, k, set) || helper(root.right, k, set);
    }
}
