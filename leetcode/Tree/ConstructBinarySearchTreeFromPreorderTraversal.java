/**
 Refer to
 https://leetcode.com/problems/construct-binary-search-tree-from-preorder-traversal/
 Return the root node of a binary search tree that matches the given preorder traversal.

(Recall that a binary search tree is a binary tree where for every node, any descendant of 
node.left has a value < node.val, and any descendant of node.right has a value > node.val.  
Also recall that a preorder traversal displays the value of the node first, then traverses 
node.left, then traverses node.right.)

Example 1:
Input: [8,5,1,7,10,12]
Output: [8,5,10,1,7,null,12]
                8
           5         10
        1     7          12

Note:
1 <= preorder.length <= 100
The values of preorder are distinct.
*/
// Solution:
// Refer to
// https://leetcode.com/problems/construct-binary-search-tree-from-preorder-traversal/discuss/252232/JavaC++Python-O(N)-Solution/248260
// https://leetcode.com/problems/construct-binary-search-tree-from-preorder-traversal/discuss/252232/JavaC%2B%2BPython-O(N)-Solution
/**
 Give the function a bound the maximum number it will handle.
The left recursion will take the elements smaller than node.val
The right recursion will take the remaining elements smaller than bound
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
    int nodeIdx = 0;
    public TreeNode bstFromPreorder(int[] preorder) {
        int min = Integer.MIN_VALUE;
        int max = Integer.MAX_VALUE;
        return helper(preorder, min, max);
    }
    
    private TreeNode helper(int[] preorder, int min, int max) {
        if(nodeIdx == preorder.length || preorder[nodeIdx] < min || preorder[nodeIdx] > max) {
            return null;
        }
        int val = preorder[nodeIdx++];
        TreeNode root = new TreeNode(val);
        root.left = helper(preorder, min, val);
        root.right = helper(preorder, val, max);
        return root;
    }
}

// Re-work
// Solution 1: Recursive O(N^2)
// Refer to
// https://leetcode.com/problems/construct-binary-search-tree-from-preorder-traversal/discuss/589059/JAVA-EASIEST-SOLUTION-WITH-CLEAR-EXPLANATION-OF-LOGIC!
/**
so we are given an array which is the preorder traversal of the some tree!
we are used to traverse a tree a but are not privy to reconstruct the tree from the array!!
anyways!!!
so we are given an array whose first element is the root of out tree!!(because of preorder traversal)!
NOTE:this is not a linear solution!i have posted linear solutions here https://leetcode.com/problems/construct-binary-search-tree-from-preorder-traversal/discuss/589801/JAVA-3-WAYS-TO-DO-THE-PROBLEM!-O(N)-APPROACH
BUT i strongly suggest you go through this soution below so that you can get the gist of the logic and 
then move on to the more complex linear solutions i posted!

LETS DO THIS:

so we follow steps:
1>we create the node
2>we traverse the array for values which are less than the current node!-- these values will become our 
  left subtree.we stop whenever we get a value larger than the current root of the subtree!
3>we take the rest of the array(values whuch are greater than the value of the current root)-these are the 
  values which will make out right subtree!

so we make a root!
make the left subtree(recursively)
then make right subtree(recursively)
*/
class Solution {
    public TreeNode bstFromPreorder(int[] preorder) {
        return helper(preorder, 0, preorder.length - 1);
    }
    
    private TreeNode helper(int[] preorder, int start, int end) {
        if(start > end) {
            return null;
        }
        TreeNode node = new TreeNode(preorder[start]);
        int i;
        for(i = start; i <= end; i++) {
            if(preorder[i] > node.val) {
                break;
            }
        }
        node.left = helper(preorder, start + 1, i - 1);
        node.right = helper(preorder, i, end);
        return node;
    }
}


