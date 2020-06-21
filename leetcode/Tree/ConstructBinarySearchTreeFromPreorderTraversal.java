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

// Solution 2: Recursive lower and upper bound
// Refer to
// https://leetcode.com/problems/construct-binary-search-tree-from-preorder-traversal/discuss/589801/JAVA-3-WAYS-TO-DO-THE-PROBLEM!-O(N)-APPROACH
// https://www.techiedelight.com/build-binary-search-tree-from-preorder-sequence/
/**
We can reduce the time complexity to O(n) by following a different approach
that doesn't involve searching for index which separates the keys of left
and right sub-tree. We know that in a BST, each node has key which is greater
than all keys present in its left sub-tree and less than the keys presented in
the right sub-tree, the idea to pass the information regarding the valid range
of keys for current root node and its children in the recurrsion itself.
Start wby getting the range as [INT_MIN, INT_MAX] for the root node, this means
that the root node and any of its children can have keys in the range between
INT_MIN and INT_MAX, like previous approach, we construct the root node of BST
from the first item in the preorder sequence. Suppose the root node has value x,
we recur for right sub-tree with range (x, INT_MAX) and recur for the left sub-tree
with range [IN_MIN, x). To construct the complete BST, we recursively set the
range for each recursive call and simply return if next element of preorder
traversal is out of the valid range.
*/
class Solution {
    int i;
    public TreeNode bstFromPreorder(int[] preorder) {
        if(preorder == null) {
            return null;
        }
        i = 0;
        return helper(preorder, Integer.MIN_VALUE, Integer.MAX_VALUE);
    }
    
    private TreeNode helper(int[] preorder, int start, int end) {
        if(i == preorder.length || preorder[i] < start || preorder[i] > end) {
            return null;
        }
        int val = preorder[i];
        i++;
        TreeNode node = new TreeNode(val);
        node.left = helper(preorder, start, val);
        node.right = helper(preorder, val, end);
        return node;
    }
}

// Solution 3: Recursive only upper bound
// Refer to
// https://leetcode.com/problems/construct-binary-search-tree-from-preorder-traversal/discuss/589801/JAVA-3-WAYS-TO-DO-THE-PROBLEM!-O(N)-APPROACH
/**
Every node has an upper bound.
Left node is bounded by the parent node's value.
Right node is bounded by the ancestor's bound.
Using the example in the question:
The nodes [5, 1, 7] are all bounded by 8.
The node 1 is bounded by 5.
8 is the root node, but if you think deeper about it, it is bounded by Integer.MAX_VALUE. i.e. imagine there 
is a root parent node Integer.MAX_VALUE with left node being 8.
This also means that both 10 and 12 nodes, which are also right nodes, are also bounded by Integer.MAX_VALUE.
We use a recursive function together with an outer index variable i to traverse and construct the tree. When 
we create a tree node, we increment i to process the next element in the preorder array.

We don't need to care about lower bound. When we construct the tree, we try to create left node first. If the 
condition fails (i.e. current number is greater than the parent node value), then we try to create the right 
node which automatically satisfies the condition, hence no lower bound is needed

EXPLANATON-
"explanation- It is  possible to do this because when we construct the " left child " the upper bound will be 
the node value itself and no lower bound will be needed!
	-no lower bound is required for "right child" because we have arrived at this point of creating the right 
 child only because these elements failed to satisfy the left subtree conditions!"
*/

// https://leetcode.com/problems/construct-binary-search-tree-from-preorder-traversal/discuss/252232/JavaC%2B%2BPython-O(N)-Solution
/**
Give the function a bound the maximum number it will handle.
The left recursion will take the elements smaller than node.val
The right recursion will take the remaining elements smaller than bound
Complexity
bstFromPreorder is called exactly N times.
It's same as a preorder traversal.
Time O(N)
Space O(H)
*/
class Solution {
    int i = 0;
    public TreeNode bstFromPreorder(int[] preorder) {
        return helper(preorder, Integer.MAX_VALUE);
    }
    
    private TreeNode helper(int[] preorder, int bound) {
        if(i == preorder.length || preorder[i] > bound) {
            return null;
        }
        TreeNode root = new TreeNode(preorder[i]);
        i++;
        root.left = helper(preorder, root.val);
        root.right = helper(preorder, bound);
        return root;
    }
}



