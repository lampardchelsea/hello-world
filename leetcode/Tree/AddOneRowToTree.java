/**
 Refer to
 https://leetcode.com/problems/add-one-row-to-tree/
 Given the root of a binary tree, then value v and depth d, you need to add a row of nodes with value v 
 at the given depth d. The root node is at depth 1.
 The adding rule is: given a positive integer depth d, for each NOT null tree nodes N in depth d-1, create 
 two tree nodes with value v as N's left subtree root and right subtree root. And N's original left subtree 
 should be the left subtree of the new left subtree root, its original right subtree should be the right 
 subtree of the new right subtree root. If depth d is 1 that means there is no depth d-1 at all, then create 
 a tree node with value v as the new root of the whole original tree, and the original tree is the new root's 
 left subtree.

Example 1:
Input: 
A binary tree as following:
       4
     /   \
    2     6
   / \   / 
  3   1 5   

v = 1

d = 2

Output: 
       4
      / \
     1   1
    /     \
   2       6
  / \     / 
 3   1   5   

Example 2:
Input: 
A binary tree as following:
      4
     /   
    2    
   / \   
  3   1    

v = 1

d = 3

Output: 
      4
     /   
    2
   / \    
  1   1
 /     \  
3       1
Note:
The given d is in range [1, maximum depth of the given tree + 1].
The given binary tree has at least one tree node.
*/

// Solution 1: DFS Recursive
// Refer to
// https://leetcode.com/problems/add-one-row-to-tree/solution/
/**
If the given depth dd happens to be equal to 1, we can directly put the whole current tree as a left child of the newly added node. 
Otherwise, we need to put the new node at appropriate levels.
To do so, we make use of a recursive function insert(val,node,depth,n). Here, valval refers to the value of the new node to be inserted, 
depthdepth refers to the depth of the node currently considered, nodenode refers to the node calling the current function for its child 
subtrees and nn refers to the height at which the new node needs to be inserted.
For inserting the new node at appropriate level, we can start by making a call to insert with the root node and 1 as the current level. 
Inside every such call, we check if we've reached one level prior to the level where the new node needs to be inserted.
From this level, we can store the roots of the left and right subtrees of the current node temporarily, and insert the new node as the 
new left and right subchild of the current node, with the temporarily stored left and right subtrees as the left and right subtrees of 
the newly inserted left or right subchildren appropriately.
But, if we haven't reached the destined level, we keep on continuing the recursive calling process with the left and right children of 
the current node respectively. At every such call, we also incrmenet the depth of the current level to reflect the depth change appropriately.
*/
class Solution {
    public TreeNode addOneRow(TreeNode root, int v, int d) {
        if(d == 1) {
            TreeNode node = new TreeNode(v);
            node.left = root;
            return node;
        }
        helper(root, v, 1, d);
        return root;
    }
    
    private void helper(TreeNode node, int v, int currDepth, int d) {
        if(node == null) {
            return;
        }
        if(currDepth == d - 1) {
            TreeNode original_left = node.left;
            TreeNode original_right = node.right;
            TreeNode new_left = new TreeNode(v);
            TreeNode new_right = new TreeNode(v);
            node.left = new_left;
            new_left.left = original_left;
            node.right = new_right;
            new_right.right = original_right;
        }
        helper(node.left, v, currDepth + 1, d);
        helper(node.right, v, currDepth + 1, d);
    }
}


