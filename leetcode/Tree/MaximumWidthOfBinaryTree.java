/**
 Refer to
 https://leetcode.com/problems/maximum-width-of-binary-tree/
 Given a binary tree, write a function to get the maximum width of the given tree. The maximum width of a tree 
 is the maximum width among all levels.
 The width of one level is defined as the length between the end-nodes (the leftmost and right most non-null nodes 
 in the level, where the null nodes between the end-nodes are also counted into the length calculation.
 It is guaranteed that the answer will in the range of 32-bit signed integer.

 Example 1:
 Input: 

           1
         /   \
        3     2
       / \     \  
      5   3     9 

 Output: 4
 Explanation: The maximum width existing in the third level with the length 4 (5,3,null,9).

 Example 2:
 Input: 

          1
         /  
        3    
       / \       
      5   3     

 Output: 2
 Explanation: The maximum width existing in the third level with the length 2 (5,3).

 Example 3:
 Input: 

          1
         / \
        3   2 
       /        
      5      

 Output: 2
 Explanation: The maximum width existing in the second level with the length 2 (3,2).
 
 Example 4:
 Input: 

          1
         / \
        3   2
       /     \  
      5       9 
     /         \
    6           7
 Output: 8
 Explanation:The maximum width existing in the fourth level with the length 8 (6,null,null,null,null,null,null,7).
 
 Constraints:
 The given binary tree will have between 1 and 3000 nodes.
*/

// Wrong Solution:
//              2
//         1          4
//       3  null    5
// [2,1,4,3,null,5] -> expected 3, return 2
class Solution {
    int maxWidth = 0;
    public int widthOfBinaryTree(TreeNode root) {
        helper(root);
        return maxWidth;
    }
    
    private void helper(TreeNode node) {
        if(node == null) {
            return;
        }
        int leftDepth = -1;
        int rightDepth = -1;
        TreeNode leftNode = node;
        TreeNode rightNode = node;
        while(leftNode != null) {
            leftDepth++;
            leftNode = leftNode.left;
        }
        while(rightNode != null) {
            rightDepth++;
            rightNode = rightNode.right;
        }
        int commonDepth = Math.min(leftDepth, rightDepth); // perfect binary tree
        int curWidth = 1 << commonDepth;
        maxWidth = Math.max(maxWidth, curWidth);
        helper(node.left);
        helper(node.right);
    }
}

// Solution 1: Very simple dfs solution
// Refer to
// https://leetcode.com/problems/maximum-width-of-binary-tree/discuss/106654/JavaC%2B%2B-Very-simple-dfs-solution
/**
 We know that a binary tree can be represented by an array (assume the root begins from the position with index 1 in the array). 
 If the index of a node is i, the indices of its two children are 2*i and 2*i + 1. The idea is to use two arrays (start[] and end[]) 
 to record the the indices of the leftmost node and rightmost node in each level, respectively. For each level of the tree, the width 
 is end[level] - start[level] + 1. Then, we just need to find the maximum width.
*/
class Solution {
    int maxWidth = 0;
    public int widthOfBinaryTree(TreeNode root) {
        if(root == null) {
            return 0;
        }
        helper(root, 0, 0, new ArrayList<Integer>(), new ArrayList<Integer>());
        return maxWidth;
    }
    
    private void helper(TreeNode node, int index, int depth, List<Integer> start, List<Integer> end) {
        if(node == null) {
            return;
        }
        // Tricky, if 'start' size equal to depth means new index not insert onto array yet,
        // insert on 'start' and 'end' both then, but if 'start' size not equal to depth means
        // it just updated, then the next change should happen only on 'end'
        if(start.size() == depth) {
            start.add(index);
            end.add(index);
        } else {
            end.set(depth, index);
        }
        int curWidth = (end.get(depth) - start.get(depth)) + 1;
        maxWidth = Math.max(curWidth, maxWidth);
        helper(node.left, index * 2, depth + 1, start, end);
        helper(node.right, index * 2 + 1, depth + 1, start, end);
    }
}
